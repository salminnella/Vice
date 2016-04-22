package martell.com.vice;

import android.accounts.Account;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
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
import martell.com.vice.services.NotificationIntentService;
import martell.com.vice.services.ViceAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {

    private static final String TAG = "ArticleActivity";
    Retrofit retrofit;
    ImageLoaderConfiguration config;
    String articleId;
    String articleTitleExtra;
    ViceAPIService viceService;
    int idNum;
    TextView articleTitleText;
    TextView articleBodyText;
    Article article;
    ImageView backDropImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String bookmarkId;

    // Content provider authority
    public static final String AUTHORITY = "martell.com.vice.sync_adapter.StubProvider";
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //receives article id that was clicked from main
        receiveIntent();
        initViews();

        retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        viceService = retrofit.create(ViceAPIService.class);
        config = new ImageLoaderConfiguration.Builder(this).build();

        idNum = Integer.parseInt(articleId);
        Log.i(TAG, "onCreate: " + idNum);

        Call<ArticleData> call = viceService.getArticle(idNum);
        call.enqueue(new Callback<ArticleData>() {
            @Override
            public void onResponse(Call<ArticleData> call, Response<ArticleData> response) {
                if (response.isSuccessful()) {
                    article = response.body().getData().getArticle();
                    articleTitleText.setText(article.getArticleTitle());

                    loadBody();
                    loadBackdrop();

                    Log.d(TAG, "onResponse: " + article.getArticleTitle());
                } else Log.i(TAG, "onResponse: failed");
            }

            @Override
            public void onFailure(Call<ArticleData> call, Throwable t) {
            }
        });

//        Intent intent = new Intent(this, NotificationIntentService.class);
//        intent.putExtra("TITLE_KEY", articleTitleExtra);
//        intent.putExtra("ID_KEY", articleId);
//        // put extra with article id here
//        startService(intent);

    }

    private void initViews() {
        articleTitleText = (TextView) findViewById(R.id.article_title_text);
        articleBodyText = (TextView) findViewById(R.id.article_body_text);
        backDropImage = (ImageView) findViewById(R.id.backdrop);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

    }

    private void receiveIntent() {
        Intent intent = getIntent();
        articleId = intent.getStringExtra("ID_KEY");
        articleTitleExtra = intent.getStringExtra("TITLE_KEY");
        Log.i(TAG, "onCreate: " + articleId);
    }

    private void loadBackdrop() {
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.init(config);
        Glide.with(this).load(article.getArticleImageURL()).centerCrop().into(backDropImage);
    }

    private void loadBody() {
        collapsingToolbarLayout.setTitle(article.getArticleCategory());
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
//        textView.setText(Html.fromHtml(htmlString.replaceAll("<img.+?>", "")));
        articleBodyText.setText(Html.fromHtml(article.getArticleBody().replaceAll("<img.+?>", "")));

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.bookmark_item_menu) {
            if (bookmarkId == null) {
                bookmarkId = String.valueOf(idNum);
                item.setIcon(R.drawable.ic_search);
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
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ArticleActivity.this);
        Cursor cursor = databaseHelper.findBookmarkById(articleId);
        if (cursor.getCount()>0) {
            menu.getItem(1).setIcon(R.drawable.ic_search);
            bookmarkId = String.valueOf(idNum);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(ArticleActivity.this);
        if (bookmarkId != null) {
            Log.d(TAG, "A BOOK MARK ID IS BEING ADDED " + bookmarkId);
            databaseHelper.insertBookmark(bookmarkId);
        } else if (bookmarkId == null) {
            Cursor cursor = databaseHelper.findBookmarkById(articleId);
            if (cursor.getCount()>0) {
                Log.d(TAG, "onDestroy: a book mark is being deleted" + bookmarkId);
                databaseHelper.deleteBookmarkById(articleId);
            }
        }
    }
}
