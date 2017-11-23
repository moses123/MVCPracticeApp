package com.test.testapplication.database;


import android.database.sqlite.SQLiteDatabase;

/**
 * The Class DeleteCommand.
 * Helps to build delete command to be executed in DB
 */
public class DeleteCommand extends ExecuteQuery
{
	
	/** The table name. */
	private String mTableName;
	
	/** The where clause. */
	private  String mWhereClause;

	/** The where clause. */
	private  String[] mWhereArgs;
	
	/**
	 * Instantiates a new delete command.
	 *
	 * @param tableName the table name
	 * @param whereClause the where clause
	 */
	public DeleteCommand(String tableName, String whereClause)
	{
		mTableName = tableName;
		mWhereClause = whereClause;
		mWhereArgs = null;
	}

	/**
	 * Instantiates a new delete command.
	 *
	 * @param tableName the table name
	 * @param whereClause the where clause
	 */
	public DeleteCommand(String tableName, String whereClause,String[] whereArgs)
	{
		mTableName = tableName;
		mWhereClause = whereClause;
		mWhereArgs = whereArgs;
	}

    /**
     *  Method builds execute query instance
     * @param db Database in use
     * @return delete executed query
     */
	@Override
	long executeQuery(SQLiteDatabase db)
	{
		return db.delete(mTableName, mWhereClause, mWhereArgs);
	}

}
