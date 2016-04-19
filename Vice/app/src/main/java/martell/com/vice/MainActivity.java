package martell.com.vice;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import martell.com.vice.fragment.LatestNewFragment;

public class MainActivity extends AppCompatActivity {
    private String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LatestNewFragment latestNewFragment = new LatestNewFragment();
        fragmentTransaction.add(R.id.fragment_container, latestNewFragment);
        fragmentTransaction.commit();
    }
}
