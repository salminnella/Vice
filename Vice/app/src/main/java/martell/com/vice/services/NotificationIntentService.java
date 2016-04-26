package martell.com.vice.services;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import martell.com.vice.activity.ArticleActivity;
import martell.com.vice.activity.MainActivity;
import martell.com.vice.R;

/**
 * Created by stewartmcmillan on 4/20/16.
 */
public class NotificationIntentService extends IntentService{

    private static final String TAG = "NotificationService";
    public static final int NOTIFICATION_ID = 5453;
    public String articleIDExtra;
    public static final String EXTRA_ID = "ID_KEY";
    public static final String EXTRA_TITLE = "TITLE_KEY";
    public static final String EXTRA_PREVIEW = "PREVIEW_KEY";
    private String articleID;
    private String articleTitle;

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

        articleID = intent.getStringExtra(EXTRA_ID);
        articleTitle = intent.getStringExtra(EXTRA_TITLE);
        Log.i(TAG, "Title: " + articleTitle);
        Log.i(TAG, "id: " + articleID);

        showArticleTitle(articleID, articleTitle);
    }

    private void showArticleTitle(String id, String title) {

        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("ID_KEY", id);
        Log.i(TAG, "you clicked article id: " + id);

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
                .setContentText(title)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }

}