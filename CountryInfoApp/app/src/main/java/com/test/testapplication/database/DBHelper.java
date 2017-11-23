package com.test.testapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.test.testapplication.logger.AppLog;
import com.test.testapplication.model.Information;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by moseskesavan on 11/21/17.
 * <p>
 * This class is a helper class which provides a SQLIte db for the CRUD operation
 * we will be using in our application.
 */

public class DBHelper extends SQLiteOpenHelper {

    /*Constants holding db name and db version*/
    private static final String DB_NAME = "App.db";
    private static final int DB_VERSION = 1;

    /**
     * The lock object Used to synchronize the db calls.
     */
    private final Object mLockObject;

    /**
     * Database Instance *
     */
    private static DBHelper sInstance = null;

    /**
     * SqliteDatabase Object *
     */
    private static SQLiteDatabase sSqliteDatabase;


    /**
     * Instantiates a new DB helper.
     *
     * @param context the context
     */
    protected DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);
        mLockObject = new Object();
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
            if (sSqliteDatabase != null) {
                sSqliteDatabase.enableWriteAheadLogging();
            }
        }
        if (sSqliteDatabase == null) {
            sSqliteDatabase = getWritableDB();
            if (sSqliteDatabase != null) {
                sSqliteDatabase.enableWriteAheadLogging();
            }
        }

        return sInstance;
    }

    private static SQLiteDatabase getWritableDB() {
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
     * This method is used for dropping the tables.
     */
    void dropTables() {
        synchronized (mLockObject) {
            sSqliteDatabase.execSQL(AppInfoTable.getDropQuery());
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

    }

                                                 /* All the data manipulation operation goes here: */

    /**
     *  Insert new data in the app data table.
     * @param information list of Information object.s
     */
    public synchronized void insertInfoData(List<Information> information) {
        if (information != null && information.size() > 0) {
            ArrayList<InsertCommand> insertCommands = new ArrayList<>();
            for (Information singleInfo :
                    information) {
                InsertCommand command = new InsertCommand(AppInfoTable.TABLE_NAME);
                command.insert(AppInfoTable.COLUMN_TITLE, singleInfo.getTitle());
                command.insert(AppInfoTable.COLUMN_DESC, singleInfo.getDescription());
                command.insert(AppInfoTable.COLUMN_IMAGE_URL, singleInfo.getImageUrl());
                insertCommands.add(command);
            }
            executeBatchQuery(insertCommands);
        }
    }

    /**
     *  This method clears all the data in the db related to info.
     */
    public synchronized void clearAppInfoData(){
        DeleteCommand command = new DeleteCommand(AppInfoTable.TABLE_NAME,null);
        executeQuery(command);
    }

    /**
     *  Fetch all the app info from the database.
     * @return list of Information.
     */
    public synchronized List<Information> fetchInfoDataFromDb(){
        final List<Information> infoList = new ArrayList<>();
        String query = "Select * From " + AppInfoTable.TABLE_NAME + "";

        fillData(query, getSelectCommand(infoList), false);
        return infoList;
    }

    /**
     *  Returns the ISelect command instance with cursor data.
     * @param infoList list fo info object
     * @return ISelectCommand
     */
    @NonNull
    protected ISelectCommand getSelectCommand(final List<Information> infoList) {
        return new ISelectCommand() {
            @Override
            public void fillData(Cursor cursor) {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Information info = fillAppInfoFromCursor(cursor);
                        if (info != null) {
                            infoList.add(info);
                        }
                    } while (cursor.moveToNext());

                }
            }
        };
    }

    /**
     *  This method gets the cursor and fills the object with the required information.
     * @param cursor cursor returned from query
     * @return the Information object.
     */
    private Information fillAppInfoFromCursor(Cursor cursor) {
        Information appInfo = null;
        try {
            appInfo = new Information();
            appInfo.setTitle(cursor.getString(cursor.getColumnIndex(AppInfoTable.COLUMN_TITLE)));
            appInfo.setDescription(cursor.getString(cursor.getColumnIndex(AppInfoTable.COLUMN_DESC)));
            appInfo.setImageUrl(cursor.getString(cursor.getColumnIndex(AppInfoTable.COLUMN_IMAGE_URL)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appInfo;
    }



                                                  /* All the CRUD operation goes here: */

    /**
     * This method fills data cursor.
     *
     * @param query the query
     * @return the cursor
     */
    protected synchronized Cursor fillData(String query) {
        synchronized (mLockObject) {
            Cursor cursor = null;
            try {
                cursor = sSqliteDatabase.rawQuery(query, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return cursor;
        }
    }

    /**
     * Executes a list of Database commands.
     *
     * @param commands commands to execute
     * @return result id return value
     */
    private synchronized long executeBatchQuery(ArrayList<? extends ExecuteQuery> commands) {
        long value;

        synchronized (mLockObject) {

            try {
                sSqliteDatabase.beginTransactionNonExclusive();
                try {
                    value = 1;

                    for (ExecuteQuery item : commands) {
                        if (item.executeQuery(sSqliteDatabase) == 0) {
                            value = -1;
                            AppLog.e("Insert", "Insert failed for item" + item.toString());
                        }
                    }
                    sSqliteDatabase.setTransactionSuccessful();

                } finally {
                    sSqliteDatabase.endTransaction();
                }

            } catch (SQLiteException ex) {
                ex.printStackTrace();
                return -1;
            }
        }
        return value;
    }

    /**
     * Executes a Database command.
     *
     * @param query command to execute
     */
    private synchronized void executeQuery(ExecuteQuery query) {
        synchronized (mLockObject) {
            try {query.executeQuery(sSqliteDatabase);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Selecting data from Database.
     *
     * @param query         query for the database
     * @param selectCommand selection command object
     * @param close     the dont close
     */
    private synchronized void fillData(String query, ISelectCommand selectCommand, boolean close) {
        synchronized (mLockObject) {
            Cursor cursor = null;
            try {
                cursor = sSqliteDatabase.rawQuery(query, null);
                selectCommand.fillData(cursor);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (cursor != null && !close) {
                    cursor.close();
                }
            }
        }
    }
}
