package martell.com.vice;

import martell.com.vice.models.ArticleArray;
import martell.com.vice.models.ArticleData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by stewartmcmillan on 4/18/16.
 */
public interface ViceAPIService {

    @GET("getlatest/{page}")
    Call<ArticleArray> latestArticles(@Path("page") int latestArticlesPage);

    //http://vice.com/api/getlatest/category/<:category>/<:page>
    @GET("getlatest/category/{category}/{page}")
    Call<ArticleArray> getArticlesByCategory(@Path("category") String category, @Path("page") int page);

    //http://vice.com/api/article/<:id>
    @GET("article/{id}")
    Call<ArticleData> getArticle(@Path("id") int articleID);
}
