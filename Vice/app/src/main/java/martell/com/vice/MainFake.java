package martell.com.vice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import martell.com.vice.fragment.NavigationDrawerFragment;

/**
 * Created by mstarace on 4/18/16.
 */
public class MainFake extends AppCompatActivity {
    private static final String TAG_MAIN = "MainFake";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<NavDrawerEntry> drawerEntries = new ArrayList<>();
        drawerEntries.add(new NavDrawerItem("Categories"));
        drawerEntries.add(new NavDrawerDivider());
        drawerEntries.add(new NavDrawerToggle("ViceLand"));
        drawerEntries.add(new NavDrawerToggle("Crime"));
        drawerEntries.add(new NavDrawerToggle("Culture"));
        drawerEntries.add(new NavDrawerToggle("LGBT"));
        drawerEntries.add(new NavDrawerToggle("Election 2016"));
        drawerEntries.add(new NavDrawerToggle("City Guides"));
        drawerEntries.add(new NavDrawerToggle("Opinion"));
        drawerEntries.add(new NavDrawerToggle("Environment"));

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.initDrawer((android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout_main),
                toolbar, drawerEntries);
        Log.d(TAG_MAIN, "THE initDrawer HAS BEEN CALLED ON MAIN");
    }
}
