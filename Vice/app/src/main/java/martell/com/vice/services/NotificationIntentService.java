package martell.com.vice.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import martell.com.vice.activity.ArticleActivity;
import martell.com.vice.activity.MainActivity;
import martell.com.vice.R;

/**
 * Created by stewartmcmillan on 4/20/16.
 */
public class NotificationIntentService extends IntentService{

    // region Constants
    public static final int NOTIFICATION_ID = 5453;
    public static final String EXTRA_ID = "ID_KEY";
    public static final String EXTRA_TITLE = "TITLE_KEY";
    public static final String EXTRA_PREVIEW = "PREVIEW_KEY";
    public static final String ARTICLE_ID_KEY = "ID_KEY";
    public static final String VICE_NEWS_TITLE = "Vice News";
    public static final String NOTIFICATION_INTENT_SERVICE = "NotificationIntentService";
    // endregion Constants

    public NotificationIntentService() {
        super(NOTIFICATION_INTENT_SERVICE);
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

        String articleID = intent.getStringExtra(EXTRA_ID);
        String articleTitle = intent.getStringExtra(EXTRA_TITLE);
        showArticleTitle(articleID, articleTitle);
    }

    private void showArticleTitle(String id, String title) {

        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ARTICLE_ID_KEY, id);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle(VICE_NEWS_TITLE)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setContentText(title)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
