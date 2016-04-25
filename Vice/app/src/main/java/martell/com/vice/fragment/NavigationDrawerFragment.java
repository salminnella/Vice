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

import java.util.ArrayList;
import java.util.List;

import martell.com.vice.NavDrawerEntry;
import martell.com.vice.NavigationDrawerAdapter;
import martell.com.vice.R;


/**
 * This fragment holds the navigation drawer items.
 *
 * Created by mstarace on 4/18/16.
 */
public class NavigationDrawerFragment extends Fragment {
    // region Member Variables
    private View navFragmentView;
    private ActionBarDrawerToggle navDrawerToggle;
    private RecyclerView navDrawerRecyclerView;
    private DrawerLayout navDrawerLayout;
    private NavigationDrawerAdapter navigationDrawerAdapter;
    // endregion Member Variables

    /**
     * inflates NavigatonDrawerView
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        navFragmentView = inflater.inflate(R.layout.fragment_navigation_drawer_main,container,false);
        return navFragmentView;
    }

    /**
     * Converts an array of booleans that represent the toggle switches in the Nav Drawer to
     * a ',' seperated string and passes to the main activity
     */
    @Override
    public void onPause() {
        ArrayList<Boolean> isCheckedArray = navigationDrawerAdapter.getIsCheckedArray();
        NotificationPreferences notificationPreferences = (NotificationPreferences)getActivity();
        notificationPreferences.setNotificationPreferences(createNotificationString(isCheckedArray));
        super.onPause();
    }

    /**
     * initializes the NavDrawer layout
     * onDrawerClosed
     * Converts an array of booleans that represent the toggle switches in the Nav Drawer to
     * a ',' seperated string and passes to the main activity
     * @param drawerLayout DrawerLayout
     * @param toolbar Toolbar
     * @param navDrawerEntryList List
     * @param booleanArrayList ArrayList
     */
    public void initDrawer(DrawerLayout drawerLayout, final Toolbar toolbar, List<NavDrawerEntry> navDrawerEntryList,
                           ArrayList<Boolean> booleanArrayList){
        navDrawerLayout = drawerLayout;
        navDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar, R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                ArrayList<Boolean> isCheckedArray = navigationDrawerAdapter.getIsCheckedArray();
                NotificationPreferences notificationPreferences = (NotificationPreferences)getActivity();
                notificationPreferences.setNotificationPreferences(createNotificationString(isCheckedArray));
                super.onDrawerClosed(drawerView);
            }
        };

        navDrawerLayout.addDrawerListener(navDrawerToggle);
        navDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                navDrawerToggle.syncState();
            }
        });

        navDrawerRecyclerView = (RecyclerView) navFragmentView.findViewById(R.id.nav_list);
        navigationDrawerAdapter = new NavigationDrawerAdapter(getActivity(), navDrawerEntryList,booleanArrayList);
        navDrawerRecyclerView.setAdapter(navigationDrawerAdapter);
        navDrawerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        navDrawerRecyclerView.setHasFixedSize(true);
    }

    /**
     * Takes in an ArrayList of booleans and converts it to an ArrayList of strings
     * with the names of user specified notification preferences. Puts the ArrayList of
     * strings into a comma separated string that is returned. string resource array is used
     * to make the conversion. Comma separated sting is intended to be used in SharedPreferences.
     * @return String
     */
    private String createNotificationString(ArrayList<Boolean> isCheckedArray){
        String strNotificationPref = "";
        String[] strArrayCategories = getResources().getStringArray(R.array.categories);
        for (int i = 0; i < isCheckedArray.size(); i++){
            if (isCheckedArray.get(i)){
                strNotificationPref = strNotificationPref + strArrayCategories[i] + ",";
            }
        }
        if (!strNotificationPref.equals("")) {
            strNotificationPref = strNotificationPref.substring(0,strNotificationPref.length()-1);
        }
        return strNotificationPref;
    }

    /**
     * interface for passing notifications to the main activity
     */
    public interface NotificationPreferences {
        void setNotificationPreferences(String notificationPreferences);
    }
}
