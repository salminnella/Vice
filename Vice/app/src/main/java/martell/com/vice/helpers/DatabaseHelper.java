package martell.com.vice.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *  The database Builder, and search helper. Creates The Articles Database,
 *  its table, and all its columns.
 *
 *  Also performs the search queries and returns a cursor
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String BOOKMARK_CATEGORY_LABEL = "bookmark";
    public static final String DATABASE_NAME = "ArticleNotices";
    public static final String TAG = "DatabaseHelper: ";

    Cursor cursor;
    SQLiteDatabase dbWrite = getWritableDatabase();
    SQLiteDatabase dbRead = getReadableDatabase();

    // table and columns
    public static final String ARTICLES_TABLE_NAME = "articles";
    public static final String COL_ID = "_id";
    public static final String COL_ARTICLE_ID = "article_id";
    public static final String COL_ARTICLE_NAME = "name";
    public static final String COL_ARTICLE_CATEGORY = "category";
    public static final String COL_ARTICLE_TIMESTAMP = "timestamp";

    // builds all columns in one array for queries later on
    public static final String[] COLUMNS = {COL_ID, COL_ARTICLE_ID, COL_ARTICLE_NAME,
            COL_ARTICLE_CATEGORY, COL_ARTICLE_TIMESTAMP};

    // the actual sql statement to create the table
    public static final String CREATE_ARTICLES_TABLE = "CREATE TABLE IF NOT EXISTS " + ARTICLES_TABLE_NAME +
            " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COL_ARTICLE_ID + " INT, " +
            COL_ARTICLE_NAME + " TEXT, " +
            COL_ARTICLE_CATEGORY + " TEXT, " +
            COL_ARTICLE_TIMESTAMP + " TEXT );";

    // makes sure there is only one instance of the database
    // if there isn't one, make it, otherwise return the one instance
    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }

        return instance;
    }

    // database constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_ARTICLES_TABLE);
        this.onCreate(db);
    }

    /**
     * Inserts records to the articles table in database requiring 4 parameters.
     *
     * @param articleId  int
     * @param articleTitle  String
     * @param articleCategory String
     * @param articleTimeStamp String
     */
    public void insertArticles(int articleId, String articleTitle, String articleCategory, String articleTimeStamp) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ARTICLE_ID, articleId);
        values.put(COL_ARTICLE_NAME, articleTitle);
        values.put(COL_ARTICLE_CATEGORY, articleCategory);
        values.put(COL_ARTICLE_TIMESTAMP, articleTimeStamp);

        db.insert(ARTICLES_TABLE_NAME, null, values);
    }

    /**
     * Returns a cursor containing all articles containing category name.
     *
     * @param category String
     * @return Cursor
     */
    public Cursor findByCategory(String category) {
        cursor = dbRead.query(ARTICLES_TABLE_NAME, COLUMNS,
                COL_ARTICLE_CATEGORY + " = ?",
                new String[]{category},
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    /**
     * Inserts record to articles table with bookmark as the category.
     * Used to keep track of articles that should be displayed in the Bookmarks
     * tab.
     *
     * @param articleId String
     */
    public void insertBookmark(String articleId) {
        ContentValues values = new ContentValues();
        values.put(COL_ARTICLE_ID, articleId);
        values.put(COL_ARTICLE_CATEGORY, BOOKMARK_CATEGORY_LABEL);

        dbWrite.insert(ARTICLES_TABLE_NAME, null, values);
    }

    /**
     * Finds all the articles that have the bookmark category.
     * Used to keep track of articles that should be displayed in the Bookmarks
     * tab.
     *
     * @return Cursor
     */
    public Cursor findAllBookmarks() {
        cursor = dbRead.query(ARTICLES_TABLE_NAME, COLUMNS,
                COL_ARTICLE_CATEGORY + " = ?" ,
                new String[]{BOOKMARK_CATEGORY_LABEL},
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    /**
     * Performs find in articles table for matching article id
     *
     * @param articleId String
     * @return Cursor
     */
    public Cursor findBookmarkById(String articleId) {
        cursor = dbRead.query(ARTICLES_TABLE_NAME, COLUMNS,
                COL_ARTICLE_ID + " = ? AND " + COL_ARTICLE_CATEGORY + " = ?",
                new String[]{articleId, BOOKMARK_CATEGORY_LABEL},
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    /**
     * Removes record from articles table with bookmark as the category, and matching article ID.
     * Used to keep track of articles that should be displayed in the Bookmarks
     * tab.
     *
     * @param articleId
     */
    public void deleteBookmarkById(String articleId) {

        dbWrite.delete(ARTICLES_TABLE_NAME,
                COL_ARTICLE_ID + " = ? AND " + COL_ARTICLE_CATEGORY + " = ?",
                new String[]{articleId, BOOKMARK_CATEGORY_LABEL});
    }
}
