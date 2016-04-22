package martell.com.vice.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adao1 on 4/19/2016.
 */
public class Article {
    @SerializedName("id")
    String articleId;
    @SerializedName("title")
    String articleTitle;
    @SerializedName("tags")
    String [] articleTags;
    @SerializedName("thumb")
    String articleThumbURL;
    @SerializedName("image")
    String articleImageURL;
    @SerializedName("preview")
    String articlePreview;
    @SerializedName("body")
    String articleBody;
    @SerializedName("author")
    String articleAuthor;
    @SerializedName("category")
    String articleCategory;
    @SerializedName("url")
    String articleURL;
    @SerializedName("pubTimestamp")
    String articleTimeStamp;
    @SerializedName("pubDate")
    String articlePubDate;


    public String getArticleTimeStamp() {
        return articleTimeStamp;
    }
    public String getArticleURL() {
        return articleURL;
    }

    public String getArticleCategory() {
        return articleCategory;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String[] getArticleTags() {
        return articleTags;
    }

    public String getArticleThumbURL() {
        return articleThumbURL;
    }

    public String getArticleImageURL() {
        return articleImageURL;
    }

    public String getArticlePreview() {
        return articlePreview;
    }

    public String getArticleBody() {
        return articleBody;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public String getArticlePubDate() {
        return articlePubDate;
    }
}

