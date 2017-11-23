package com.test.testapplication.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * The Class ExecuteQuery.
 */
abstract class ExecuteQuery
{
    
    /**
     * Execute Query constructor.
     */
	ExecuteQuery()
	{
		
	}

    /**
     * Executes the query on the Database.
     *
     * @param db Database in use
     * @return change
     */
	abstract long executeQuery(SQLiteDatabase db);
}
