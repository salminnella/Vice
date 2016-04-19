package martell.com.vice;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import martell.com.vice.adapters.ViewPagerAdapter;
import martell.com.vice.fragment.CategoryFragment;
import martell.com.vice.models.Article;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG = "Main";
    private static final String CATEGORY_TITLE_KEY = "Title";

    private ArrayList<Article> articles;
    private RecyclerView articleRV;
    public ViceAPIService viceService;
    private ArticleAdapter articleAdapter;
    private AlphaInAnimationAdapter alphaAdapter;
    private Retrofit retrofit;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articles = new ArrayList<>();
        retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        viceService = retrofit.create(ViceAPIService.class);
        //displayLatestArticles();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPagerOneFragment(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: " + tab.getText());
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Log.d(TAG, "onTabSelected: " + String.valueOf(R.id.container_fragment));
//                fragmentTransaction.replace(R.id.container_fragment, adapter.getItem(tab.getPosition()));
//
//                fragmentTransaction.commit();
                
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPagerOneFragment(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        CategoryFragment news = new CategoryFragment();
        Bundle bundleNews = new Bundle();
        bundleNews.putString(CATEGORY_TITLE_KEY, "News");
        news.setArguments(bundleNews);

        CategoryFragment music = new CategoryFragment();
        Bundle bundleMusic = new Bundle();
        bundleMusic.putString(CATEGORY_TITLE_KEY, "Music");
        music.setArguments(bundleMusic);

//        CategoryFragment music = new CategoryFragment("Music");
//        CategoryFragment sports = new CategoryFragment("Sports");
//        CategoryFragment tech = new CategoryFragment("Tech");
//        CategoryFragment travel = new CategoryFragment("Travel");
//        CategoryFragment fashion = new CategoryFragment("Fashion");
//        CategoryFragment guide = new CategoryFragment("Guide");

        adapter.addFragment(news, bundleNews.getString(CATEGORY_TITLE_KEY));
        adapter.addFragment(music, bundleMusic.getString(CATEGORY_TITLE_KEY));
//        adapter.addFragment(sports, sports.getTitle());
//        adapter.addFragment(tech, tech.getTitle());
//        adapter.addFragment(travel, travel.getTitle());
//        adapter.addFragment(fashion, fashion.getTitle());
//        adapter.addFragment(guide, guide.getTitle());

        viewPager.setAdapter(adapter);
    }

//    private void displayLatestArticles(){
//        Call<ArticleArray> call = viceService.latestArticles(0);
//        call.enqueue(new Callback<ArticleArray>() {
//            @Override
//            public void onResponse(Call<ArticleArray> call, Response<ArticleArray> response) {
//                Article[] articleArray = response.body().getData().getItems();
//                articles = new ArrayList<>(Arrays.asList(articleArray));
//                makeRV();
//            }
//            @Override
//            public void onFailure(Call<ArticleArray> call, Throwable t) {
//            }
//        });
//    }

//    private void makeRV (){
//        articleRV = (RecyclerView)findViewById(R.id.articleRV);
//        articleAdapter = new ArticleAdapter(articles);
//        alphaAdapter = new AlphaInAnimationAdapter(articleAdapter);
//        alphaAdapter.setDuration(5000);
//        alphaAdapter.setInterpolator(new OvershootInterpolator());
//        articleRV.setAdapter(alphaAdapter);
//        RV_SpaceDecoration decoration = new RV_SpaceDecoration(16);
//        articleRV.addItemDecoration(decoration);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
//        articleRV.setLayoutManager(gridLayoutManager);
//        articleRV.setHasFixedSize(true);
//    }

}
