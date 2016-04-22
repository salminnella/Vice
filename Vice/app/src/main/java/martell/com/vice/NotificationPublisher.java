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

import martell.com.vice.dbHelper.NotificationDBHelper;

/**
 * Created by stewartmcmillan on 4/20/16.
 */
public class NotificationPublisher extends BroadcastReceiver {
    String TAG = "NotificationPublisher: ";
    NotificationDBHelper dbHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive method called");

        String notificationArticleId = intent.getStringExtra("ID_KEY");
        String notificationArticleTitle = intent.getStringExtra("TITLE_KEY");
        Log.i(TAG, "notificationArticleId: " + notificationArticleId);
        Log.i(TAG, "notificationArticleTitle: " + notificationArticleTitle);

        createNotification(context, "What's New on Vice", notificationArticleTitle, notificationArticleId);

    }

    public void createNotification(Context context, String header, String articleTitle, String articleId) {
        Log.i(TAG, "onReceive: " + articleId + " " + articleTitle);

        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class).putExtra("ID_KEY", articleId).putExtra("TITLE_KEY", articleTitle), 0);
        Log.i(TAG, "createNotification: " + articleId);

        Notification.Builder mBuilder = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle(header)
                .setContentText(articleTitle);

        mBuilder.setContentIntent(notificationIntent);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }

}


