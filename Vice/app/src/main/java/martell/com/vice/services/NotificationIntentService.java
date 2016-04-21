package martell.com.vice.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;

import martell.com.vice.ArticleActivity;
import martell.com.vice.Main2Activity;
import martell.com.vice.MainActivity;
import martell.com.vice.R;
import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by stewartmcmillan on 4/20/16.
 */
public class NotificationIntentService extends IntentService{

    private static final String TAG = "NotificationService";
    public static final String ARTICLE_ID = "Article ID: ";
    public static final int NOTIFICATION_ID = 5453;
    public String articleIDExtra;
    public static final String EXTRA_TITLE = "TITLE_KEY";
    public static final String EXTRA_PREVIEW = "preview";
    private String text;

    public NotificationIntentService() {
        super("NotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
                wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        text = intent.getStringExtra(EXTRA_TITLE);
        Log.i(TAG, "onResponse: " + text);

        showArticleTitle(text);
    }

    private void showArticleTitle(final String text) {

        Intent intent = new Intent(this, ArticleActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle("Vice News")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setContentText(text)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

        Log.i("NotifIntentService", "showText: " + text);
    }

}
