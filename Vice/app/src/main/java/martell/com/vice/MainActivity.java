package martell.com.vice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import martell.com.vice.models.Article;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Article> articles;
    private RecyclerView articleRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articleRV = (RecyclerView)findViewById(R.id.articleRV);
        articles = Article.createArticleList(50);
        ArticleAdapter articleAdapter = new ArticleAdapter(articles);
        articleRV.setAdapter(articleAdapter);
        RV_SpaceDecoration decoration = new RV_SpaceDecoration(16);
        articleRV.addItemDecoration(decoration);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        articleRV.setLayoutManager(gridLayoutManager);
        articleRV.setHasFixedSize(true);

        //articleRV.setItemAnimator();

    }
}
