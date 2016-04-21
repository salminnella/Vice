package martell.com.vice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by stewartmcmillan on 4/20/16.
 */
public class NotificationPublisher extends BroadcastReceiver {
    String TAG = "NotificationPublisher: ";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive method called");

        String articleID = intent.getStringExtra("ID_KEY");
        String articleTitle = intent.getStringExtra("TITLE_KEY");

        createNotification(context, "Vice News", articleTitle, articleID);

    }

    public void createNotification(Context context, String appName, String articleTitle, String articleId) {
        Log.i(TAG, "onReceive: " + articleId + articleTitle);

        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ArticleActivity.class).putExtra("ID_KEY", articleId), 0);

        Notification.Builder mBuilder = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle(appName)
                .setTicker(articleId)
                .setContentText(articleTitle);

        mBuilder.setContentIntent(notificationIntent);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }

}


