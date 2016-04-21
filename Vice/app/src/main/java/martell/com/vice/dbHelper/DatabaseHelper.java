package martell.com.vice.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anthony on 4/20/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ArticleNotices";

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

    public void insertArticles(int articleId, String articleTitle, String articleCategory, String articleTimeStamp) {
        ContentValues values = new ContentValues();
        values.put(COL_ARTICLE_ID, articleId);
        values.put(COL_ARTICLE_NAME, articleTitle);
        values.put(COL_ARTICLE_CATEGORY, articleCategory);
        values.put(COL_ARTICLE_TIMESTAMP, articleTimeStamp);

        dbWrite.insert(ARTICLES_TABLE_NAME, null, values);
    }

    public void updateArticles() {

    }

    public void findArticles() {

    }

    public Cursor getPlacesList() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ARTICLES_TABLE_NAME, // a. table
                COLUMNS, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        return cursor;
    }

    public void deleteArticles() {

    }

    public void insertBookmark(String articleId) {
        ContentValues values = new ContentValues();
        values.put(COL_ARTICLE_ID, articleId);
        values.put(COL_ARTICLE_CATEGORY, "bookmark");

        dbWrite.insert(ARTICLES_TABLE_NAME, null, values);

    }

    public Cursor findAllBookmarks() {
        cursor = dbRead.query(ARTICLES_TABLE_NAME, COLUMNS,
                COL_ARTICLE_CATEGORY + " = ?" ,
                new String[]{"bookmark"},
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor findBookmarkById(String articleId) {
        cursor = dbRead.query(ARTICLES_TABLE_NAME, COLUMNS,
                COL_ARTICLE_ID + " = " + articleId,
                null,
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        return cursor;
    }
}
