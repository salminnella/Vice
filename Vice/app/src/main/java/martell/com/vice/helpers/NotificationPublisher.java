package martell.com.vice.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import martell.com.vice.R;
import martell.com.vice.activity.ArticleActivity;

/**
 * Created by stewartmcmillan on 4/20/16.
 * This class receives an intent, along with article id and title data, from setNotificationAlarmManager()
 * method in MainActivity and pushes notifications that will take user directly to Vice's most popular
 * article at the time of most recent sync with API.
 */
public class NotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String notificationArticleId = intent.getStringExtra("ID_KEY");
        String notificationArticleTitle = intent.getStringExtra("TITLE_KEY");
        createNotification(context, "What's New on Vice", notificationArticleTitle, notificationArticleId);

    }

    public void createNotification(Context context, String header, String articleTitle, String articleId) {

        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ArticleActivity.class).putExtra("ID_KEY", articleId).putExtra("TITLE_KEY", articleTitle), 0);

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


