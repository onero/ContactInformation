package example.mathias.contactinformation.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import example.mathias.contactinformation.Database.ContactDBSchema.ContactTable;
/**
 * Created by Mathias on 26/03/2018.
 */

public class ContactBaseHelper extends SQLiteOpenHelper {

    // Sets the current version.
    private static final int VERSION = 3;
    // The name on the DB file.
    private static final String DATABASE_NAME = "contactBase.db";

    public ContactBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // SQL query.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ContactTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ContactTable.Cols.UUID + ", " +
                ContactTable.Cols.NAME + ", " +
                ContactTable.Cols.ADDRESS + ", " +
                ContactTable.Cols.LOCATION + ", " +
                ContactTable.Cols.PHONE + ", " +
                ContactTable.Cols.MAIL + ", " +
                ContactTable.Cols.WEBSITE + ", " +
                ContactTable.Cols.PICTURE + ", " +
                ContactTable.Cols.BIRTHDAY + ")"
        );
    }

    /***
     * If a newer version is detected of the DB, we will drop the table likes it's hot!
     * Then recreate the DB
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContactTable.NAME);
        onCreate(db);
    }
}
