package martell.com.vice;

import android.util.Log;

import java.util.ArrayList;

import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleArray;
import martell.com.vice.models.ArticleData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adao1 on 4/20/2016.
 */
public class BookmarksHelper {
    private static final String TAG = "BookmarkshHelper";

    public static ArrayList<Article> getBookmarkArticles(ArrayList<String> bookmarkIDs){
        Log.d(TAG, "getBookmarkArticles: ");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ViceAPIService viceService = retrofit.create(ViceAPIService.class);
        final ArrayList<Article> articleArrayList = new ArrayList<>();
        for (String id : bookmarkIDs){
            Call<ArticleData> call = viceService.getArticle(Integer.parseInt(id));
            call.enqueue(new Callback<ArticleData>() {
                @Override
                public void onResponse(Call<ArticleData> call, Response<ArticleData> response) {
                    Article article = response.body().getData().getArticle();
                    articleArrayList.add(article);
                    Log.i(TAG, "onResponse: "+article.getArticleTitle());

                }

                @Override
                public void onFailure(Call<ArticleData> call, Throwable t) {

                }
            });
        }
        return articleArrayList;
    }

}
