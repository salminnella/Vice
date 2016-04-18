package martell.com.vice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import martell.com.vice.Models.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityStew extends AppCompatActivity {
    private ViceAPIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stew);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ViceAPIService.class);
    }

    private void getLatestArticles(){
        Call<Article> call = service.getLatestArticles(0);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                Log.d("MainActivity", "onResponse");

                // update code below to build list of articles
                // code below gets tags just to test connection
                Article article = response.body();
                if (article == null){
                    return;
                }
                for (String tag : article.getTags()) {
                    Log.d("MainActivity", "Market: " + tag);

                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.d("MainActivity", "onFailure");
            }
        });
    }
}
