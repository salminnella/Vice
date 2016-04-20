//package martell.com.vice;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import martell.com.vice.fragment.NavigationDrawerFragment;
//
///**
// * Created by mstarace on 4/18/16.
// */
//public class MainFakeDrawer extends AppCompatActivity implements NavigationDrawerFragment.NotificationPreferences {
//    private static final String TAG_MAIN = "MainFake";
//    private static final String KEY_SHARED_PREF_NOTIF = "sharedPrefNotification";
//    private Toolbar toolbar;
//    private String notificationPreferences;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        SharedPreferences sharedPreferences = MainFakeDrawer.this.getPreferences(Context.MODE_PRIVATE);
//        String notificationFromSharedPref = sharedPreferences.getString(KEY_SHARED_PREF_NOTIF,"");
//        setNavigationDrawer(createBoolArrayList(notificationFromSharedPref));
//
//    }
//
//    private ArrayList<Boolean> createBoolArrayList(String notificationPreferences){
//        String[] categories = getResources().getStringArray(R.array.categories);
//        ArrayList<Boolean> isCheckedArray = new ArrayList<>();
//        String[] arrayNotificationPref = notificationPreferences.split(",");
//
//        for (int i = 0; i < categories.length; i++){
//            isCheckedArray.add(false);
//            for (String curNotification: arrayNotificationPref) {
//                if (categories[i].equals(curNotification)){
//                    isCheckedArray.set(i,true);
//                }
//            }
//        }
//
//        return isCheckedArray;
//    }
//
//    private void setNavigationDrawer(ArrayList<Boolean> isCheckedArray) {
//        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar()!= null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        } else {
//            Log.d(TAG_MAIN, "SUPPORT ACTION BAR IS NULL");
//        }
//        List<NavDrawerEntry> drawerEntries = new ArrayList<>();
//        drawerEntries.add(new NavDrawerItem("Categories"));
//        drawerEntries.add(new NavDrawerDivider());
//        drawerEntries.add(new NavDrawerToggle("ViceLand"));
//        drawerEntries.add(new NavDrawerToggle("Crime"));
//        drawerEntries.add(new NavDrawerToggle("Culture"));
//        drawerEntries.add(new NavDrawerToggle("LGBT"));
//        drawerEntries.add(new NavDrawerToggle("Election 2016"));
//        drawerEntries.add(new NavDrawerToggle("City Guides"));
//        drawerEntries.add(new NavDrawerToggle("Opinion"));
//        drawerEntries.add(new NavDrawerToggle("Environment"));
//
//        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.fragment_navigation_drawer);
//
//        drawerFragment.initDrawer((android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout_main),
//                toolbar, drawerEntries,isCheckedArray);
//        Log.d(TAG_MAIN, "THE initDrawer HAS BEEN CALLED ON MAIN");
//    }
//
//    @Override
//    public void setNotificationPreferences(String notificationPreferences) {
//        this.notificationPreferences = notificationPreferences;
//    }
//
//    @Override
//    protected void onDestroy() {
//        SharedPreferences sharedPreferences = MainFakeDrawer.this.getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(KEY_SHARED_PREF_NOTIF,notificationPreferences);
//        editor.commit();
//        super.onDestroy();
//    }
//
//}
