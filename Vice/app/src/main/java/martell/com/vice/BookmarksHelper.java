package martell.com.vice;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import martell.com.vice.dbHelper.DatabaseHelper;
import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleData;
import martell.com.vice.services.ViceAPIService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adao1 on 4/20/2016.
 */
public class BookmarksHelper extends AsyncTask<Void,Void,ArrayList<Article>> {
    private static final String TAG = "BookmarkhHelper";
    ArrayList<Article> articleArrayList;
    BookmarksResponse bookmarksResponse;
    DatabaseHelper bookmarkDataBaseHelper;

    public BookmarksHelper(BookmarksResponse bookmarksResponse,DatabaseHelper bookmarkDataBaseHelper){
        this.bookmarksResponse = bookmarksResponse;
        this.bookmarkDataBaseHelper = bookmarkDataBaseHelper;
    }

    @Override
    protected ArrayList<Article> doInBackground(Void... params) {
       Cursor bookmarkCursor = bookmarkDataBaseHelper.findAllBookmarks();
        if (bookmarkCursor.getCount() == 0) {
            return null;
        }
        ArrayList<String> idList = new ArrayList<>();

        Log.d(TAG, "THIS IS THE IDLIST SIZE AFTER DATABASE CALL " + idList.size());


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ViceAPIService viceService = retrofit.create(ViceAPIService.class);
        articleArrayList = new ArrayList<>();
        ArticleData articleData;
        Article article;

        for (String id: idList) {
            Log.d(TAG, "THIS IS THE ID FROM IDLIST " + id);
        }

        for (String id : idList) {
            Log.d(TAG, "THIS IS INSIDE THE LOOP " + id);
            Call<ArticleData> call = viceService.getArticle(Integer.parseInt(id));

            try {
                articleData = call.execute().body();
                article = articleData.getData().getArticle();
                articleArrayList.add(article);
            }
            catch (IOException ioE) {
                Log.d(TAG, "IO EXCEPTION HAS BEEN THROWN");
            }
        }
        bookmarkCursor.close();
        return articleArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        if (articles.size() > 0) {
            super.onPostExecute(articles);
            Log.d(TAG, "THIS IS THE POST EXECUTE ARRAY LIST " + articles.get(3).getArticleTitle());
            bookmarksResponse.getResponse(articles);
        }
    }

    public interface BookmarksResponse {
        void getResponse (ArrayList<Article> articleArrayList);
    }
}
