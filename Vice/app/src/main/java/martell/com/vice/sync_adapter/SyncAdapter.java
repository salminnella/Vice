package martell.com.vice.sync_adapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import java.io.IOException;

import martell.com.vice.helpers.NotificationDBHelper;
import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleArray;
import martell.com.vice.services.ViceAPIService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anthony on 4/19/16.
 *
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    // region Constants
    private static final String TAG = "SyncAdapter";
    public static final String VICE_BASE_URL = "http://www.vice.com/en_us/api/";
    // endregion Constants
    // region Member Variables
    public int rowId;
    public int articleId;
    public String articleTitle;
    public String articleCategory;
    public String articleTimeStamp;
    private ViceAPIService viceService;
    private Retrofit retrofit;
    ContentResolver mContentResolver;
    NotificationDBHelper notificationHelper;
    public Context context;
    // endregion Member Variables
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
        this.context = context;
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
     * @param account Acount
     * @param extras Bundle
     * @param authority String
     * @param provider ContentProviderClient
     * @param syncResult SyncResult
     */
    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {

        retrofit = new Retrofit.Builder().baseUrl(VICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        viceService = retrofit.create(ViceAPIService.class);

        //get a response from vice
        try {
            Response<ArticleArray> response = viceService.popularArticles(0).execute();
            Article articleList[] = response.body().getData().getItems().clone();
            articleId = Integer.parseInt(articleList[0].getArticleId());
            articleTitle = articleList[0].getArticleTitle();
            articleCategory = articleList[0].getArticleCategory();
            articleTimeStamp = articleList[0].getArticleTimeStamp();
            notificationHelper = NotificationDBHelper.getInstance(getContext());

            if (notificationHelper == null) {
                notificationHelper.insertArticles(0, articleId, articleTitle, articleCategory, articleTimeStamp);
            } else {
                notificationHelper.deleteArticle(0);
                notificationHelper.insertArticles(0, articleId, articleTitle, articleCategory, articleTimeStamp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
