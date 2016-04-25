package martell.com.vice.helpers;

import android.database.Cursor;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleData;
import martell.com.vice.services.ViceAPIService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Runs an asynctask in order to loop through Vice api calls for an arraylist of article ids
 *
 * Created by adao1 on 4/20/2016.
 */
public class BookmarksHelper extends AsyncTask<Void,Void,ArrayList<Article>> {
    public static final String VICE_BASE_URL = "http://www.vice.com/en_us/api/";
    ArrayList<Article> articleArrayList;
    BookmarksResponse bookmarksResponse;
    DatabaseHelper bookmarkDataBaseHelper;

    /**
     * Constructor needs and instance of BookmarkResponse (an interface defined in LatestNewFragment)
     * and an instance of the DatabaseHelper
     * @param bookmarksResponse BookmardsResponse
     * @param bookmarkDataBaseHelper DatabaseHelper
     */
    public BookmarksHelper(BookmarksResponse bookmarksResponse,DatabaseHelper bookmarkDataBaseHelper){
        this.bookmarksResponse = bookmarksResponse;
        this.bookmarkDataBaseHelper = bookmarkDataBaseHelper;
    }

    /**
     * doInBackground
     * pulls all the article ids from the database where Category = bookmarks
     * uses the list of ids to make a looping call to the Vice api
     * on postExecute
     * uses an interface to pass article data back to LatestNewFragment
     * @param params Void
     * @return ArrayList
     */
    @Override
    protected ArrayList<Article> doInBackground(Void... params) {
       Cursor bookmarkCursor = bookmarkDataBaseHelper.findAllBookmarks();
        if (bookmarkCursor.getCount() == 0) {
            return null;
        }
        bookmarkCursor.moveToFirst();
        ArrayList<String> idList = new ArrayList<>();

        idList.add(bookmarkCursor.getString(bookmarkCursor.getColumnIndex(DatabaseHelper.COL_ARTICLE_ID)));
        while (bookmarkCursor.moveToNext()) {
            idList.add(bookmarkCursor.getString(bookmarkCursor.getColumnIndex(DatabaseHelper.COL_ARTICLE_ID)));
        }


        Retrofit retrofit = new Retrofit.Builder().baseUrl(VICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ViceAPIService viceService = retrofit.create(ViceAPIService.class);
        articleArrayList = new ArrayList<>();
        ArticleData articleData;
        Article article;

        for (String id : idList) {
            Call<ArticleData> call = viceService.getArticle(Integer.parseInt(id));
            try {
                articleData = call.execute().body();
                article = articleData.getData().getArticle();
                articleArrayList.add(article);
            }
            catch (IOException ioE) {
                ioE.printStackTrace();
            }
        }
        bookmarkCursor.close();
        return articleArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        if (articles != null) {
            if (articles.size() > 0) {
                super.onPostExecute(articles);
                bookmarksResponse.getResponse(articles);
            }
        }
    }

    /**
     * interface to pass Article data to LatestNewsFragment
     */
    public interface BookmarksResponse {
        void getResponse (ArrayList<Article> articleArrayList);
    }
}
