package martell.com.vice.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by adao1 on 4/18/2016.
 */
public class Article {
    private String title;
    private String body;
    private boolean isOnScreen;

    public Article(String title, String body, boolean isOnScreen) {
        this.title = title;
        this.body = body;
        this.isOnScreen = isOnScreen;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public boolean isOnScreen() {
        return isOnScreen;
    }

    /**
     * Temporary makes Article list
     */
    private static int lastArticleId = 0;
    public static ArrayList<Article> createArticleList(int numArticles){
        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 1; i <= numArticles; i++){
            articles.add(new Article("Article " + ++lastArticleId,"Body",false));
        }
        return articles;
    }

}
