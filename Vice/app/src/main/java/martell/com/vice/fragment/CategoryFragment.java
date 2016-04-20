package martell.com.vice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import martell.com.vice.R;

/**
 * Created by anthony on 4/19/16.
 */
public class CategoryFragment  extends Fragment {
    TextView textView;

    public CategoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("CategoryFragment", "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        textView = (TextView) view.findViewById(R.id.text_view_from_fragment);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        textView.setText("some text");
        super.onViewCreated(view, savedInstanceState);
    }

    public static void getTitle(String title) {
        Log.d("CategoryFragment", "getTitle: " + title);

    }
}