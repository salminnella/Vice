//package martell.com.vice.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import martell.com.vice.NavDrawerEntry;
//import martell.com.vice.NavigationDrawerAdapter;
//import martell.com.vice.R;
//
//
///**
// * Created by mstarace on 4/18/16.
// */
//public class NavigationDrawerFragment extends Fragment {
//    private static final String TAG_NAV_FRAG = "NavigationFragment";
//    private View navFragmentView;
//    private ActionBarDrawerToggle navDrawerToggle;
//    private RecyclerView navDrawerRecyclerView;
//    private DrawerLayout navDrawerLayout;
//    private NavigationDrawerAdapter navigationDrawerAdapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(TAG_NAV_FRAG, "THE ON CREATE VIEW HAS BEEN CALLED");
//        navFragmentView = inflater.inflate(R.layout.fragment_navigation_drawer_main,container,false);
//        return navFragmentView;
//    }
//
//    @Override
//    public void onPause() {
//        ArrayList<Boolean> isCheckedArray = navigationDrawerAdapter.getIsCheckedArray();
//        NotificationPreferences notificationPreferences = (NotificationPreferences)getActivity();
//        notificationPreferences.setNotificationPreferences(createNotificationString(isCheckedArray));
//        super.onPause();
//    }
//
//    public void initDrawer(DrawerLayout drawerLayout, final Toolbar toolbar, List<NavDrawerEntry> navDrawerEntryList,
//                           ArrayList<Boolean> booleanArrayList){
//        Log.d(TAG_NAV_FRAG,"initDrawer HAS BEEN CALLED IN THE NAVIGATION FRAGMENT");
//        navDrawerLayout = drawerLayout;
//        navDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar, R.string.drawer_open,
//                R.string.drawer_close) {
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                //getActivity().invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//
//                super.onDrawerClosed(drawerView);
//                //getActivity().invalidateOptionsMenu();
//            }
//        };
//
//
//
//        navDrawerLayout.addDrawerListener(navDrawerToggle);
//        navDrawerLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG_NAV_FRAG, "THE NAVDRAWERTOGGLE.SYNCSTATE HAS BEEN CALLED");
//                navDrawerToggle.syncState();
//            }
//        });
//
//        navDrawerRecyclerView = (RecyclerView) navFragmentView.findViewById(R.id.nav_list);
//        navigationDrawerAdapter = new NavigationDrawerAdapter(getActivity(), navDrawerEntryList,booleanArrayList);
//        navDrawerRecyclerView.setAdapter(navigationDrawerAdapter);
//        navDrawerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        navDrawerRecyclerView.setHasFixedSize(true);
//
//
//
//    }
//
//    /**
//     * Takes in an ArrayList of booleans and converts it to an ArrayList of strings
//     * with the names of user specified notification preferences. Puts the ArrayList of
//     * strings into a comma separated string that is returned. string resource array is used
//     * to make the conversion. Comma separated sting is intended to be used in SharedPreferences.
//     * @return
//     */
//    private String createNotificationString(ArrayList<Boolean> isCheckedArray){
//        String strNotificationPref = "";
//        String[] strArrayCategories = getResources().getStringArray(R.array.categories);
//        for (int i = 0; i < isCheckedArray.size(); i++){
//            if (isCheckedArray.get(i)){
//                strNotificationPref = strNotificationPref + strArrayCategories[i] + ",";
//            }
//        }
//        if (!strNotificationPref.equals("")) {
//            strNotificationPref = strNotificationPref.substring(0,strNotificationPref.length()-1);
//        }
//        return strNotificationPref;
//    }
//
//    public interface NotificationPreferences {
//        void setNotificationPreferences(String notificationPreferences);
//    }
//
//    //need absolute position for persistance within the recyler view
//    //need to save and restore shared prefernces when fragment is destroyed
//}
