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
    String TAG = "NotificationBuilder ";



    @Override
    public void onReceive(Context context, Intent intent) {

        String code = intent.getStringExtra("KEY");
        Log.i(TAG, "onReceive: " + code);

        createNotification(context, "Article Title", "Other Content", "Alert");

    }

    public void createNotification(Context context, String appName, String articleTitle, String articleId) {

        Log.i(TAG, "onReceive: createNotification was called");

        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ArticleActivity.class), 0);

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
//    public static String NOTIFICATION_ID = "notification-id";
//    public static String NOTIFICATION = "notification";
//    public static final String EXTRA_TITLE = "TITLE_KEY";
//
//
//    public void onReceive(Context context, Intent intent) {
//
//        String title = intent.getStringExtra(EXTRA_TITLE);
//
//        Notification notification = intent.getParcelableExtra(NOTIFICATION);
//        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
//
//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(id, notification);
//    }


