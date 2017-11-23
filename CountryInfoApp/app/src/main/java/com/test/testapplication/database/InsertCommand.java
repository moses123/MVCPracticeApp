package com.test.testapplication.database;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * The Class InsertCommand.
 * Helps to build update command to be executed in DB
 */
public class InsertCommand extends ExecuteQuery
{
	
	/** The table name. */
	private String mTableName;
	
	/** The content values. */
	private ContentValues mContentValues;

	/**
	 * Instantiates a new insert command.
	 *
	 * @param tableName the table name
	 */
	public InsertCommand(String tableName)
	{
		mTableName = tableName;
		mContentValues = new ContentValues();
	}
	
	/**
	 * Instantiates a new insert command.
	 *
	 * @param tableName the table name
	 * @param contentValues the content values
	 */
	public InsertCommand(String tableName, ContentValues contentValues)
	{
		mTableName = tableName;
		mContentValues = contentValues;
	}
	
	/**
	 * Insert.
	 *
	 * @param column the column
	 * @param value the value
	 */
	public void insert(String column, String value){
			mContentValues.put(column, value);

	}
	
	/**
	 * Insert.
	 *
	 * @param column the column
	 * @param value the value
	 */
	public void insert(String column, int value)
	{
		mContentValues.put(column, value);
	}
	
	/**
	 * Insert.
	 *
	 * @param column the column
	 * @param value the value
	 */
	public void insert(String column, long value)
	{
		mContentValues.put(column, value);
	}
	
	/**
	 * Insert.
	 *
	 * @param column the column
	 * @param value the value
	 */
	public void insert(String column, float value)
	{
		mContentValues.put(column, value);
	}
	
	/**
	 * Insert.
	 *
	 * @param column the column
	 * @param value the value
	 */
	public void insert(String column, double value)
	{
		mContentValues.put(column, value);
	}
	
	/**
	 * Insert.
	 *
	 * @param column the column
	 * @param value the value
	 */
	public void insert(String column, boolean value)
	{
		mContentValues.put(column, value);
	}

	/**
	 *Method builds execute query instance
	 * @param db Database in use
	 * @return executed query
	 */
	long executeQuery(SQLiteDatabase db)
	{
		return db.insertWithOnConflict(mTableName, null, mContentValues, SQLiteDatabase.CONFLICT_REPLACE);
	}
}
