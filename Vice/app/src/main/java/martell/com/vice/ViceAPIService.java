package martell.com.vice;

import martell.com.vice.models.Article;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by stewartmcmillan on 4/18/16.
 */
public interface ViceAPIService {

    @GET("getlatest/{pageNum}")
     Call<Article> getLatestArticles(@Path("pageNum") int resultPage);

//    from RetrofitExample project:
//    @GET("albums/{id}")
//    Call<SpotifyAlbum> getAlbumForId(@Path("id") String albumId);

}
