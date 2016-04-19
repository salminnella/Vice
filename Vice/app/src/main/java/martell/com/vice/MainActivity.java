package martell.com.vice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleArray;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Article> articles;
    private RecyclerView articleRV;
    public ViceAPIService viceService;
    private String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articleRV = (RecyclerView)findViewById(R.id.articleRV);
        //articles = Article.createArticleList(50);
        ArticleAdapter articleAdapter = new ArticleAdapter(articles);
        articleRV.setAdapter(articleAdapter);
        RV_SpaceDecoration decoration = new RV_SpaceDecoration(16);
        articleRV.addItemDecoration(decoration);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        articleRV.setLayoutManager(gridLayoutManager);
        articleRV.setHasFixedSize(true);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        viceService = retrofit.create(ViceAPIService.class);

        Call<ArticleArray> call = viceService.latestArticles(0);

        call.enqueue(new Callback<ArticleArray>() {
            @Override
            public void onResponse(Call<ArticleArray> call, Response<ArticleArray> response) {
                Article[] articles = response.body().getData().getItems();

                for (Article article : articles) {

                    Log.i(TAG, "onResponse: " + article.getArticleTitle());
                }

                Log.i(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<ArticleArray> call, Throwable t) {

            }
        });


        //articleRV.setItemAnimator();

    }
}
