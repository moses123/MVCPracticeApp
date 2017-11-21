package com.test.testapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by moseskesavan on 11/21/17.
 *
 * This class is a helper class which provides a SQLIte db for the CRUD operation
 *  we will be using in our application.
 */

public class DBHelper extends SQLiteOpenHelper{

    /*Constants holding db name and db version*/
    private static final String DB_NAME = "App.db";
    private static final int DB_VERSION = 1;

    /**
     * The _lock object Used to synchronize the db calls.
     */
    private final Object _lockObject;

    /**
     * Database Instance *
     */
    private static DBHelper sInstance = null;

    /**
     * SqliteDatabase Object *
     */
    private static SQLiteDatabase sSqliteDatabase;


    private Context mContext;

    /**
     * Instantiates a new DB helper.
     *
     * @param context      the context
     */
    protected DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);
        mContext = context;
        _lockObject = new Object();
    }

    /**
     * Database Singleton instance
     *
     * @param context the context
     * @return database instance
     * @throws SQLiteException the sq lite exception
     */
    public static synchronized DBHelper getInstance(Context context) throws SQLiteException {
        if (sInstance == null) {

            sInstance = new DBHelper(context);
            sSqliteDatabase = getWritableDB();
            if(sSqliteDatabase!=null) {
                sSqliteDatabase.enableWriteAheadLogging();
            }
        }
        if (sSqliteDatabase == null) {
            sSqliteDatabase = getWritableDB();
            if(sSqliteDatabase!=null) {
                sSqliteDatabase.enableWriteAheadLogging();
            }
        }

        return sInstance;
    }

    public static SQLiteDatabase getWritableDB() {
        return sInstance.getWritableDatabase();
    }


    /**
     * On creation of Database.
     *
     * @param db the db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(AppInfoTable.getCreateTableQuery());
    }


    /**
     * Method used for creating all the tables
     */
    void reCreateTables() {
        synchronized (_lockObject){

        }
    }

    /**
     * This method is used for dropping the tables.
     */
    void dropTables() {
        synchronized (_lockObject) {
            getWritableDB().execSQL(AppInfoTable.getDropQuery());
        }
    }

    /**
     * On upgrade of database.
     *
     * @param db         the db
     * @param oldVersion the old version
     * @param newVersion the new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            //TODO: here goes the code to update the table:
        }
    }


    /**
     * This method executes a Statement Based command
     *
     * @return sqlite database
     */
    public synchronized SQLiteDatabase getDatabase() {
        try {
            return sSqliteDatabase;
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        return sSqliteDatabase;
    }

    /**
     * This method fills data cursor.
     *
     * @param query the query
     * @return the cursor
     */
    synchronized Cursor fillData(String query) {
        synchronized (_lockObject) {
            Cursor cursor = null;
            try {
                cursor = sSqliteDatabase.rawQuery(query, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return cursor;
        }
    }

}
