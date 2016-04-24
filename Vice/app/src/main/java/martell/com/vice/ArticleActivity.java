package martell.com.vice;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import martell.com.vice.dbHelper.DatabaseHelper;
import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleData;
import martell.com.vice.services.ViceAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {

    // region Constants
    public static final String VICE_BASE_URL = "http://www.vice.com/en_us/api/";
    public static final String ARTICLE_ID_KEY = "ID_KEY";
    // endregion Constants
    // region Member Variables
    private int idNum;
    private TextView articleTitleText;
    private TextView articleBodyText;
    private TextView articleAuthorText;
    private TextView articleDateText;
    private String articleId;
    private String bookmarkId;
    private ImageView backDropImage;
    private ViceAPIService viceService;
    private Article article;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private DatabaseHelper databaseHelper;
    // endregion Member Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHelper = DatabaseHelper.getInstance(ArticleActivity.this);
        // receives article id that was clicked from main
        receiveIntent();
        // initialize views
        initViews();
        // initialize retrofit builder
        buildRetrofit();
        // calls vice for article data to display
        insertArticleDetails();
    }

    private void initViews() {
        articleTitleText = (TextView) findViewById(R.id.article_title_text);
        articleBodyText = (TextView) findViewById(R.id.article_body_text);
        backDropImage = (ImageView) findViewById(R.id.backdrop);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        articleAuthorText = (TextView) findViewById(R.id.article_author_text);
        articleDateText = (TextView) findViewById(R.id.article_date_text);
    }

    private void receiveIntent() {
        Intent intent = getIntent();
        articleId = intent.getStringExtra(ARTICLE_ID_KEY);
        idNum = Integer.parseInt(articleId);
    }

    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(VICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        viceService = retrofit.create(ViceAPIService.class);
    }

    private void insertArticleDetails() {
        Call<ArticleData> call = viceService.getArticle(idNum);
        call.enqueue(new Callback<ArticleData>() {
            @Override
            public void onResponse(Call<ArticleData> call, Response<ArticleData> response) {
                if (response.isSuccessful()) {
                    article = response.body().getData().getArticle();
                    // loads image in collapsing toolbar
                    fillToolbarItems();
                    // sets title in toolbar layout, and sets article body
                    fillArticleBody();
                }
            }

            @Override
            public void onFailure(Call<ArticleData> call, Throwable t) {
            }
        });
    }

    private void fillToolbarItems() {
        collapsingToolbarLayout.setTitle(article.getArticleCategory());
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.init(config);
        Glide.with(ArticleActivity.this).load(article.getArticleImageURL()).centerCrop().into(backDropImage);
    }

    private void fillArticleBody() {
        articleTitleText.setText(article.getArticleTitle());
        articleAuthorText.setText(article.getArticleAuthor());
        articleDateText.setText(article.getArticlePubDate());
        articleBodyText.setText(Html.fromHtml(article.getArticleBody().replaceAll("<img.+?>", "")));
    }

    private boolean isBookmarkAlreadySaved() {
        Cursor bookmarkCursor = databaseHelper.findBookmarkById(articleId);
        return bookmarkCursor.getCount() != 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.bookmark_item_menu) {
            if (bookmarkId == null) {
                bookmarkId = String.valueOf(idNum);
                item.setIcon(R.drawable.bookmark_selected);
            } else {
                bookmarkId = null;
                item.setIcon(R.drawable.bookmark);
            }
        }
        if (id == R.id.share_item_menu){
            String message = article.getArticleURL();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);

            startActivity(Intent.createChooser(share, "Share article with ... "));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isBookmarkAlreadySaved()) {
            menu.getItem(1).setIcon(R.drawable.bookmark_selected);
            bookmarkId = String.valueOf(idNum);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookmarkId != null) {
            if (!isBookmarkAlreadySaved()) {
                databaseHelper.insertBookmark(bookmarkId);
            }
        } else if (bookmarkId == null) {
            if (isBookmarkAlreadySaved()) {
                databaseHelper.deleteBookmarkById(articleId);
            }
        }
    }
}
