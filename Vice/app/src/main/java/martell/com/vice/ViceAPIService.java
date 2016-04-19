package martell.com.vice;

import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleArray;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by stewartmcmillan on 4/18/16.
 */
public interface ViceAPIService {

    @GET("getlatest/{page}")
    Call<ArticleArray> latestArticles(
            @Path("page") int latestArticlesPage);

}
