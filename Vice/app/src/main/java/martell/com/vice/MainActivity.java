package martell.com.vice;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import martell.com.vice.adapters.ViewPagerAdapter;
//import martell.com.vice.fragment.CategoryFragment;
import martell.com.vice.fragment.LatestNewFragment;
import martell.com.vice.models.Article;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG = "Main";

    public static final String CATEGORY_TITLE_KEY = "Title";
    private ViewPager viewPager;
    private ArrayList<Article> articles;
    public ViceAPIService viceService;
    private Retrofit retrofit;
    private LatestNewFragment category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category = new LatestNewFragment();
        articles = new ArrayList<>();
        retrofit = new Retrofit.Builder().baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        viceService = retrofit.create(ViceAPIService.class);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPagerOneFragment(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Log.d(TAG, String.valueOf(viewPager.getCurrentItem()));
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
    }

    private void setupViewPagerOneFragment(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        LatestNewFragment home = new LatestNewFragment();
        adapter.addFragment(home, "Home");

        LatestNewFragment news = new LatestNewFragment();
        adapter.addFragment(news, "News");

        LatestNewFragment music = new LatestNewFragment();
        adapter.addFragment(music, "Music");

        LatestNewFragment sports = new LatestNewFragment();
        adapter.addFragment(sports, "Sports");

        LatestNewFragment tech = new LatestNewFragment();
        adapter.addFragment(tech, "Tech");

        LatestNewFragment travel = new LatestNewFragment();
        adapter.addFragment(travel, "Travel");

        LatestNewFragment fashion = new LatestNewFragment();
        adapter.addFragment(fashion, "Fashion");

        LatestNewFragment guide = new LatestNewFragment();
        adapter.addFragment(guide, "Guide");

        LatestNewFragment bookmarks = new LatestNewFragment();
        adapter.addFragment(bookmarks, "Bookmarks");

        viewPager.setAdapter(adapter);
    }

}
