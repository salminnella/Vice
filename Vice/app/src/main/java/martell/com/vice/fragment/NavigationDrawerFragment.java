package martell.com.vice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import martell.com.vice.NavDrawerEntry;
import martell.com.vice.NavigationDrawerAdapter;
import martell.com.vice.R;


/**
 * Created by mstarace on 4/18/16.
 */
public class NavigationDrawerFragment extends Fragment {
    private static final String TAG_NAV_FRAG = "Navigation_Fragment";
    private View navFragmentView;
    private ActionBarDrawerToggle navDrawerToggle;
    private RecyclerView navDrawerRecyclerView;
    private DrawerLayout navDrawerLayout;
    private NavigationDrawerAdapter navigationDrawerAdapter;
    private ArrayList<Boolean> isCheckedArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG_NAV_FRAG, "THE ON CREATE VIEW HAS BEEN CALLED");
        navFragmentView = inflater.inflate(R.layout.fragment_navigation_drawer_main,container,false);
        return navFragmentView;
    }

    public void initDrawer(DrawerLayout drawerLayout, final Toolbar toolbar, List<NavDrawerEntry> navDrawerEntryList){
        Log.d(TAG_NAV_FRAG,"initDrawer HAS BEEN CALLED IN THE NAVIGATION FRAGMENT");
        navDrawerLayout = drawerLayout;
        navDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar, R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isCheckedArray = new ArrayList<>();
                isCheckedArray = navigationDrawerAdapter.getIsCheckedArray();
                Log.d(TAG_NAV_FRAG,"This is the isChecked array from the adapater: " + isCheckedArray.get(2));
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        navDrawerLayout.addDrawerListener(navDrawerToggle);
        navDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG_NAV_FRAG, "THE NAVDRAWERTOGGLE.SYNCSTATE HAS BEEN CALLED");
                navDrawerToggle.syncState();
            }
        });

        navDrawerRecyclerView = (RecyclerView) navFragmentView.findViewById(R.id.nav_list);
        navigationDrawerAdapter = new NavigationDrawerAdapter(getActivity(), navDrawerEntryList);
        navDrawerRecyclerView.setAdapter(navigationDrawerAdapter);
        navDrawerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        navDrawerRecyclerView.setHasFixedSize(true);

    }

}
