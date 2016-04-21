package martell.com.vice.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import martell.com.vice.Main2Activity;
import martell.com.vice.MainActivity;
import martell.com.vice.R;
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
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_PREVIEW = "preview";
    private String articleID;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
            .addConverterFactory(GsonConverterFactory.create()).build();
    ViceAPIService viceService = retrofit.create(ViceAPIService.class);

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

        //TODO: api call to get article id and title for notification
//        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
//                .addConverterFactory(GsonConverterFactory.create()).build();
//        ViceAPIService viceService = retrofit.create(ViceAPIService.class);

//        String id = intent.getStringExtra("KEY");
//        int idNum = Integer.parseInt(id);
//        Log.i(TAG, "onCreate: " + idNum);
//        Call<ArticleData> call = viceService.getArticle(idNum);
//        call.enqueue(new Callback<ArticleData>() {
//            @Override
//            public void onResponse(Call<ArticleData> call, Response<ArticleData> response) {
//                if (response.isSuccessful()) {
//                    Article article = response.body().getData().getArticle();
//                    articleID = article.getArticleId();
//
//                    Log.d(TAG, "onResponse: " + article.getArticleTitle());
//                }
//                else Log.i(TAG, "onResponse: failed");
//            }
//
//            @Override
//            public void onFailure(Call<ArticleData> call, Throwable t) {
//            }
//        });

        String text = intent.getStringExtra(articleID);

        showArticleTitle(text);
    }

    public void showArticleTitle(final String text) {

        Intent intent = new Intent(this, Main2Activity.class);

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
                .setContentText("Article Title")
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

        Log.i("NotifIntentService", "showText: " + articleIDExtra);
    }

}
