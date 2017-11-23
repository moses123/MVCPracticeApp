package com.test.testapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.testapplication.model.Information;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import java.util.ArrayList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by moseskesavan on 11/21/17.
 *
 * Test class for DbHelper.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DBHelper.class, SQLiteOpenHelper.class,SQLiteDatabase.class})
public class DbHelperTest {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase=mock(SQLiteDatabase.class);
    private Cursor mCursor;

    @Before
    public void setUp() throws Exception {
        Context context=mock(Context.class);
        dbHelper=PowerMockito.spy(new DBHelper(context));
        Whitebox.setInternalState(dbHelper,"sSqliteDatabase",sqLiteDatabase); //setting private sqldatabase to have mock database
        mCursor = mock(Cursor.class);
    }

    @Test
    public void onCreate() throws Exception {
        dbHelper.onCreate(sqLiteDatabase);
    }


    @Test
    public void dropTables() throws Exception {
        dbHelper.dropTables();
    }

    @Test
    public void onUpgrade() throws Exception {
        dbHelper.onUpgrade(sqLiteDatabase,7,25);
    }


    @Test
    public void fillData() throws Exception {
        String query="";
        dbHelper.fillData(query);
        verify(sqLiteDatabase).rawQuery(anyString(),any(String[].class));
    }

    @Test
    public void fillData1() throws Exception {
        String query="";
        when(sqLiteDatabase.rawQuery(anyString(),any(String[].class))).thenReturn(mCursor);
        dbHelper.fillData(query);
        verify(sqLiteDatabase).rawQuery(anyString(),any(String[].class));
    }

    @Test
    public void fetchInfoDataFromDb() throws Exception{
        ArrayList list = new ArrayList();
        PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn(list);
        ISelectCommand mockSelectCommand = mock(ISelectCommand.class);
        when(dbHelper.getSelectCommand(list)).thenReturn(mockSelectCommand);
        String query = "Select * From " + AppInfoTable.TABLE_NAME + "";
        Cursor mockCursor = mock(Cursor.class);
        when(sqLiteDatabase.rawQuery(query,null)).thenReturn(mockCursor);
        dbHelper.fetchInfoDataFromDb();

        verify(mockSelectCommand).fillData(mockCursor);
        verify(mockCursor).close();
    }

    @Test
    public void testGetSelectCommandFillData() throws Exception{
        ArrayList list = new ArrayList();
        Cursor mockCursor = mock(Cursor.class);
        when(mockCursor.moveToFirst()).thenReturn(true);
        when(mockCursor.moveToNext()).thenReturn(true,false);
        Information information1 = new Information();
        Information information2 = new Information();

        PowerMockito.whenNew(Information.class).withNoArguments().thenReturn(information1,information2);

        when(mockCursor.getColumnIndex(AppInfoTable.COLUMN_TITLE)).thenReturn(1);
        when(mockCursor.getString(1)).thenReturn("Title","Title 2");
        when(mockCursor.getColumnIndex(AppInfoTable.COLUMN_DESC)).thenReturn(2);
        when(mockCursor.getString(2)).thenReturn("Description","Desc 2");
        when(mockCursor.getColumnIndex(AppInfoTable.COLUMN_IMAGE_URL)).thenReturn(3);
        when(mockCursor.getString(3)).thenReturn("Url");
        dbHelper.getSelectCommand(list).fillData(mockCursor);
        assertEquals(information1.getTitle(),"Title");
        assertEquals(information1.getDescription(),"Description");
        assertEquals(information1.getImageUrl(),"Url");
        assertEquals(information2.getTitle(),"Title 2");
        assertEquals(information2.getDescription(),"Desc 2");
        assertEquals(information2.getImageUrl(),"Url");
        assertEquals(list.size(),2);
    }

}