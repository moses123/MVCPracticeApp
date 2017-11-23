package com.test.testapplication.database;

import android.database.Cursor;

/**
 * Created by moseskesavan on 11/22/17.
 *
 * The Interface ISelectCommand.
 */

public interface ISelectCommand {

        /**
         * Fill data.
         *
         * @param cursor the cursor
         */
        void fillData(Cursor cursor);
}
