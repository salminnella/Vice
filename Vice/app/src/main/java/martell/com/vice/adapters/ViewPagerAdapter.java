package martell.com.vice.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import martell.com.vice.fragment.CategoryFragment;

/**
 * Created by anthony on 4/19/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final String CATEGORY_TITLE_KEY = "Title";
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                CategoryFragment home = new CategoryFragment();
                Bundle bundleHome = new Bundle();
                bundleHome.putString(CATEGORY_TITLE_KEY, "Home");
                home.setArguments(bundleHome);
                Log.d("viewpageradapter", "home ");
                return home;
            case 1:
                CategoryFragment news = new CategoryFragment();
                Bundle bundleNews = new Bundle();
                bundleNews.putString(CATEGORY_TITLE_KEY, "News");
                news.setArguments(bundleNews);
                Log.d("viewpageradapter", "news ");

                return news;
            case 2:
                CategoryFragment music = new CategoryFragment();
                Bundle bundleMusic = new Bundle();
                bundleMusic.putString(CATEGORY_TITLE_KEY, "Music");
                music.setArguments(bundleMusic);
                Log.d("viewpageradapter", "music ");

                return music;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

//    public void addFragment(Fragment fragment, String title) {
//        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
//    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
        return String.valueOf(position);
    }
}