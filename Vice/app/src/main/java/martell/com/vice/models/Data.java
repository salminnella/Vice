package martell.com.vice.models;

/**
 * Model to hold article data from JSON response from VICE
 * Created by stewartmcmillan on 4/19/16.
 */
public class Data {
    Article[] items;
    Article article;

    public Article getArticle() {
        return article;
    }

    public Article[] getItems() {
        return items;
    }
}
