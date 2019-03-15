package irfan.sampling.pracitesqlite.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BookHelper extends SQLiteOpenHelper {

    final static String DBNAME = "samplebook.db";
    final static int DBVERSION = 1;

    public BookHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createtable = "Create table books(_id integer primary key autoincrement, " +
                "title text," +
                "author text);";
        sqLiteDatabase.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String droptable ="Drop table if exists books";
        sqLiteDatabase.execSQL(droptable);
        onCreate(sqLiteDatabase);
    }
}
