package com.test.testapplication.database;

import android.support.annotation.NonNull;

/**
 * Created by moseskesavan on 11/21/17.
 *
 * This class holds the column names for tha info table as well as the create and drop query.
 */

public class AppInfoTable {

    public static final String TABLE_NAME = "AppInfo";

    public static final String COLUMN_TITLE = "Title";

    public static final String COLUMN_DESC = "Description";

    public static final String COLUMN_IMAGE_URL = "ImageUrl";

    /**
     * Gets create table query.
     *
     * @return the create table query
     */
    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE_NAME \""+ TABLE_NAME +"\"(\"" +
                COLUMN_TITLE+"\" Text,\"" +
                COLUMN_DESC+"\" Text,\"" +
                COLUMN_IMAGE_URL+"\" Text );";
    }

    /**
     *  Gets the drop table query
     * @return the drop table query.
     */
    public static String getDropQuery(){

        return "DROP TABLE \""+ TABLE_NAME +" IF EXIST";

    }
}
