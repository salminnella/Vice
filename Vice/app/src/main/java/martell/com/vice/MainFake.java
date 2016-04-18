package martell.com.vice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import martell.com.vice.fragment.NavigationDrawerFragment;

/**
 * Created by mstarace on 4/18/16.
 */
public class MainFake extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<NavDrawerEntry> drawerEntries = new ArrayList<>();
        drawerEntries.add(new NavDrawerItem("Settings"));
        drawerEntries.add(new NavDrawerDivider());
        drawerEntries.add(new NavDrawerToggle("Vice Land"));

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.initDrawer((android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout_main),
                toolbar, drawerEntries);
    }
}
