package martell.com.vice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityStew extends AppCompatActivity {
    private ViceAPIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stew);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.vice.com/en_us/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ViceAPIService.class);
    }
}
