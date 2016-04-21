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
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.GregorianCalendar;

import martell.com.vice.MainActivity;
import martell.com.vice.NotificationPublisher;
import martell.com.vice.dbHelper.DatabaseHelper;
import martell.com.vice.models.ArticleArray;
import martell.com.vice.services.NotificationIntentService;
import martell.com.vice.services.ViceAPIService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public String articleTitle;
    public int articleId;

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
            Log.i(TAG, "onResponse: " + response.body().getData().getItems()[0].getArticleId());
            Log.i(TAG, "onResponse: " + response.body().getData().getItems().length);
            for (int i = 0; i < response.body().getData().getItems().length; i++) {
                articleId = Integer.parseInt(response.body().getData().getItems()[i].getArticleId());
                articleTitle = response.body().getData().getItems()[i].getArticleTitle();
                Log.i(TAG, "onPerformSync: article Id " + articleId);
                Log.i(TAG, "onPerformSync: articleTitle: " + articleTitle);
                DatabaseHelper searchHelper = DatabaseHelper.getInstance(getContext());
                searchHelper.findArticles();
            }



            NotificationIntentService notificationService = new NotificationIntentService();
            //notificationService.showArticleTitle(author);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    //do we do this here and not in main??
//    public void setNotificationAlarmManager() {
//        Log.i(TAG, "onCreate: setAlarm was called");
//
//        Long alertTime = new GregorianCalendar().getTimeInMillis()+5000;
//
//        Intent alertIntent = new Intent(this, NotificationPublisher.class);
//
//        alertIntent.putExtra("TITLE_KEY", "test title that is too long so i can test format of notification");
//        alertIntent.putExtra("ID_KEY", "212318");
//
//        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);
//        tStackBuilder.addParentStack(MainActivity.class);
//        tStackBuilder.addNextIntent(alertIntent);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,
//                PendingIntent.getBroadcast(this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));
//        Log.i(TAG, "setAlarm: alarm manager should have been set");
//    }
}
