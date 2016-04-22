package martell.com.vice.sync_adapter;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import martell.com.vice.dbHelper.DatabaseHelper;
import martell.com.vice.dbHelper.NotificationDBHelper;
import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleArray;
import martell.com.vice.services.NotificationIntentService;
import martell.com.vice.services.ViceAPIService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HEAD;

/**
 * Created by anthony on 4/19/16.
 *
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private ViceAPIService viceService;
    private Retrofit retrofit;
    private static final String TAG = "SyncAdapter";
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    public int rowId;
    public int articleId;
    public String articleTitle;
    public String articleCategory;
    public String articleTimeStamp;
    NotificationDBHelper notificationHelper;
    public ArrayList<Article> articlesArray;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }

    /**
     * This is where the magic background sync actually happens
     * @param account
     * @param extras
     * @param authority
     * @param provider
     * @param syncResult
     */
    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider, //Should this reference the StubProvider?
            SyncResult syncResult) {

        Log.i(TAG, "onPerformSync: =========");
        retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        viceService = retrofit.create(ViceAPIService.class);

        //get a response from vice
        try {
            Response<ArticleArray> response = viceService.latestArticles(0).execute();
            Article articleList[] = response.body().getData().getItems().clone();
            Log.i(TAG, "onResponse: " + response.body().getData().getItems()[0].getArticleId());
            Log.i(TAG, "number of Articles: " + response.body().getData().getItems().length);

            articleId = Integer.parseInt(articleList[0].getArticleId());
            articleTitle = articleList[0].getArticleTitle();
            articleCategory = articleList[0].getArticleCategory();
            articleTimeStamp = articleList[0].getArticleTimeStamp();

            Log.i(TAG, "onPerformSync: articleTitle is: " + articleTitle);
            Log.i(TAG, "onPerformSync: article Id " + articleId);

            notificationHelper = NotificationDBHelper.getInstance(getContext());
            notificationHelper.insertArticles(0, articleId, articleTitle, articleCategory, articleTimeStamp);
            
            Log.i(TAG, "onPerformSync: Retrofit title is: " + articleTitle);
            Log.i(TAG, "onPerformSync: sql db title is: " + notificationHelper.getLatestArticleTitle(0));

            Log.i(TAG, "onPerformSync: Retrofit firstArticleId: " + articleList[0].getArticleId());
            Log.i(TAG, "onPerformSync: sql DB firstArticleId: " + notificationHelper.getLatestArticleId(0));

//            for (int i = 0; i < articleList.length; i++) {
//
//                articleId = Integer.parseInt(articleList[i].getArticleId());
//                articleTitle = articleList[i].getArticleTitle();
//                articleCategory = articleList[i].getArticleCategory();
//                articleTimeStamp = articleList[i].getArticleTimeStamp();
//
//                Log.i(TAG, "onPerformSync: articleTitle is: " + articleTitle);
//                Log.i(TAG, "onPerformSync: article Id " + articleId);
//
//                notificationHelper = NotificationDBHelper.getInstance(getContext());
//                notificationHelper.insertArticles(i, articleId, articleTitle, articleCategory, articleTimeStamp);
//                String latestArticleTitle = notificationHelper.getLatestArticleTitle(0);
//                Log.i(TAG, "onPerformSync: Retrofit title is: " + articleTitle);
//                Log.i(TAG, "onPerformSync: sql db title is: " + notificationHelper.getLatestArticleTitle(0));
//
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        //get a response from vice
//        try {
//            Response<ArticleArray> response = viceService.latestArticles(1).execute();
//            Log.i(TAG, "onResponse: " + response.body().getData().getItems()[0].getArticleId());
//            Log.i(TAG, "onResponse: " + response.body().getData().getItems().length);
//            for (int i = 0; i < response.body().getData().getItems().length; i++) {
//                int id = Integer.parseInt(response.body().getData().getItems()[i].getArticleId());
//                Log.i(TAG, "onPerformSync: article id " + id);
//                DatabaseHelper searchHelper = DatabaseHelper.getInstance(getContext());
//                searchHelper.findArticles();
//            }


//
//            NotificationIntentService notificationService = new NotificationIntentService();
//            //notificationService.showArticleTitle(author);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
