package martell.com.vice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import martell.com.vice.R;

/**
 * Created by anthony on 4/19/16.
 */
public class CategoryFragment  extends Fragment {

    private String title;

    public CategoryFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        Log.i("CategoryFragment", "onCreate: " + bundle.getString("Title"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }
}