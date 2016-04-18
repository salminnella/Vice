package martell.com.vice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import martell.com.vice.NavDrawerEntry;
import martell.com.vice.NavigationDrawerAdapter;
import martell.com.vice.R;


/**
 * Created by mstarace on 4/18/16.
 */
public class NavigationDrawerFragment extends Fragment {
    private View navFragmentView;
    private ActionBarDrawerToggle navDrawerToggle;
    private RecyclerView navDrawerRecyclerView;
    private DrawerLayout navDrawerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        navFragmentView = inflater.inflate(R.layout.fragment_navigation_drawer_main,container,false);
        return navFragmentView;
    }

    public void initDrawer(DrawerLayout drawerLayout, final Toolbar toolbar, List<NavDrawerEntry> navDrawerEntryList){
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
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        navDrawerLayout.addDrawerListener(navDrawerToggle);
        navDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                navDrawerToggle.syncState();
            }
        });

        navDrawerRecyclerView= (RecyclerView) navFragmentView.findViewById(R.id.nav_list);
        navDrawerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        navDrawerRecyclerView.setHasFixedSize(true);

        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(getActivity(), navDrawerEntryList);
        navDrawerRecyclerView.setAdapter(adapter);
    }


}
