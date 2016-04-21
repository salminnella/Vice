package martell.com.vice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleArray;
import martell.com.vice.models.ArticleData;
import martell.com.vice.models.Data;
import martell.com.vice.services.ViceAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2";
    TextView textView;
    TextView bodyView;
    ImageView imageView;
    Button button;
    ImageLoaderConfiguration config;
    ViceAPIService viceService;
    String id;
    ShareButton shareButton;
    Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = (TextView)findViewById(R.id.textTEST);
        bodyView = (TextView)findViewById(R.id.bodyTEST);
        imageView = (ImageView)findViewById(R.id.imageTEST);
        button = (Button)findViewById(R.id.button);
        testButton = (Button)findViewById(R.id.testButton);
        Intent intent = getIntent();
        id = intent.getStringExtra("KEY");
        Log.i(TAG, "onCreate: " + id);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        viceService = retrofit.create(ViceAPIService.class);
        config = new ImageLoaderConfiguration.Builder(this).build();

        shareButton = (ShareButton)findViewById(R.id.facebookbutton);
        getLoadArticle();
        testCategoryAPICall();
        shareButtonListener();
//        testBookmarkHelper();
    }

    private void getLoadArticle(){

        Call<ArticleData> call = viceService.getArticle(Integer.parseInt(id));
        call.enqueue(new Callback<ArticleData>() {
            @Override
            public void onResponse(Call<ArticleData> call, Response<ArticleData> response) {
                if (response.isSuccessful()) {
                    Article article = response.body().getData().getArticle();
                    textView.setText(article.getArticleTitle());

                    //bodyView.setText(article.getArticleBody());
                    bodyView.setText(Html.fromHtml(article.getArticleBody()));
                    ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
                    imageLoader.init(config);
                    imageLoader.displayImage(article.getArticleImageURL(), imageView);
                    ShareLinkContent content = new ShareLinkContent.Builder().setContentUrl(Uri.parse(article.getArticleURL())).build();
                    shareButton.setShareContent(content);

                    Log.d(TAG, "onResponse: " + article.getArticleTitle());
                } else Log.i(TAG, "onResponse: failed");
            }
            @Override
            public void onFailure(Call<ArticleData> call, Throwable t) {
            }
        });
    }
    private void testCategoryAPICall(){
        Call<ArticleArray> callCategory = viceService.getArticlesByCategory("sports",1);
        callCategory.enqueue(new Callback<ArticleArray>() {
            @Override
            public void onResponse(Call<ArticleArray> call, Response<ArticleArray> response) {
                ArticleArray articleArray = response.body();
                Data data = articleArray.getData();
                for(Article article: data.getItems()){
                    Log.i(TAG, "onResponse: "+article.getArticleTitle());
                }
            }

            @Override
            public void onFailure(Call<ArticleArray> call, Throwable t) {

            }
        });
    }
    private void shareButtonListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Share article with ... "));
            }
        });
    }
//    private void testBookmarkHelper(){
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ArrayList<String> idList = new ArrayList<String>();
//                ArrayList<Article> articleList = new ArrayList<Article>();
//                idList.add("195491");
//                idList.add("188277");
//                idList.add("188184");
//                idList.add("188187");
//                Log.i(TAG, "onClick: ");
//                articleList = BookmarksHelper.getBookmarkArticles(idList);
//                for (Article article : articleList){
//                    Log.i(TAG, "onClick: "+article.getArticleTitle());
//                }
//            }
//        });
//    }
}
