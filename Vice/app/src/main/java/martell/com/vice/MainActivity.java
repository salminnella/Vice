package martell.com.vice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.Arrays;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import martell.com.vice.models.Article;
import martell.com.vice.models.ArticleArray;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Article> articles;
    private RecyclerView articleRV;
    public ViceAPIService viceService;
    private String TAG = "Main";
    private ArticleAdapter articleAdapter;
    private AlphaInAnimationAdapter alphaAdapter;
    private Retrofit retrofit;
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articles = new ArrayList<>();
        retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        viceService = retrofit.create(ViceAPIService.class);
        displayLatestArticles();
    }

    private void displayLatestArticles(){
        Call<ArticleArray> call = viceService.latestArticles(0);
        call.enqueue(new Callback<ArticleArray>() {
            @Override
            public void onResponse(Call<ArticleArray> call, Response<ArticleArray> response) {
                Article[] articleArray = response.body().getData().getItems();
                articles = new ArrayList<>(Arrays.asList(articleArray));
                makeRV();
            }

            @Override
            public void onFailure(Call<ArticleArray> call, Throwable t) {
            }
        });
    }

    private void makeRV (){
        articleRV = (RecyclerView)findViewById(R.id.articleRV);
        articleAdapter = new ArticleAdapter(articles);
        alphaAdapter = new AlphaInAnimationAdapter(articleAdapter);
        alphaAdapter.setDuration(5000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        articleRV.setAdapter(alphaAdapter);
        RV_SpaceDecoration decoration = new RV_SpaceDecoration(16);
        articleRV.addItemDecoration(decoration);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        articleRV.setLayoutManager(gridLayoutManager);
        articleRV.setHasFixedSize(true);
    }

    private void showBigPictureStyle() {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.nature)).build();

        // update intent with appropriate fragment when fragments are merged
        Intent intent = new Intent(this, DetailFragment.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_notification);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");
        mBuilder.setContentIntent(pIntent);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigPictureStyle);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // NOTIFICATION_ID allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
