package com.test.testapplication.database;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * The Class InsertCommand.
 * Helps to build update command to be executed in DB
 */
 class InsertCommand extends ExecuteQuery {
	
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
	 * Insert.
	 *
	 * @param column the column
	 * @param value the value
	 */
	public void insert(String column, String value){
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
