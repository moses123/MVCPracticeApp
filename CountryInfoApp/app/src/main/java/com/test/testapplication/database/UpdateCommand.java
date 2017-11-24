package com.test.testapplication.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * The Class UpdateCommand.
 * Helps to build update command to be executed in DB
 */
 class UpdateCommand extends ExecuteQuery {
	
	/** The table name. */
	private String mTableName;
	
	/** The _content values. */
	private ContentValues mContentValues;
	
	/** The _where clause. */
	private  String mWhereClause;

	/** The _where args. */
	private  String[] mWhereArgs;
	
	/**
	 * Instantiates a new update command.
	 *
	 * @param tableName the table name
	 * @param whereClause the where clause
	 */
	public UpdateCommand(String tableName, String whereClause)
	{
		this(tableName,whereClause, new ContentValues());
	}

	/**
	 * Instantiates a new update command.
	 *
	 * @param tableName the table name
	 * @param whereClause the where clause
	 * @param contentValues the content values
	 */
	public UpdateCommand(String tableName, String whereClause, ContentValues contentValues)
	{
		mTableName = tableName;
		mWhereClause = whereClause;
		mContentValues = contentValues;
		mWhereArgs = null;
	}

	/**
	 * Instantiates a new update command.
	 *
	 * @param tableName the table name
	 * @param whereClause the where clause
	 * @param contentValues the content values
	 */
	public UpdateCommand(String tableName, String whereClause,String[] whereArgs, ContentValues contentValues)
	{
		mTableName = tableName;
		mWhereClause = whereClause;
		mContentValues = contentValues;
		mWhereArgs = whereArgs;
	}

    /**
     * Method builds execut query instance
     * @param db Database in use
     * @return delete executed query
     */
	@Override
	long executeQuery(SQLiteDatabase db)
	{
		return db.update(mTableName, mContentValues, mWhereClause, mWhereArgs);
	}
}
