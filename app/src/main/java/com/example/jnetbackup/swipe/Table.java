package com.example.jnetbackup.swipe;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

public class Table extends AppCompatActivity {
    private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" },
            { "and", "a", "second", "test" } };
    ArrayList<String[]>  items;
    SimpleTableDataAdapter adapter;
    FeedReaderDbHelper mDbHelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        mDbHelper= new FeedReaderDbHelper(this);
        items=new ArrayList<String[]>();
        TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        int colorEvenRows = android.R.color.white;
        setTitle("Database");
        tableView.setColumnWeight(3,2);
        int colorOddRows = R.color.black;
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,new String[]{ "Temperature","Smoke","Humidity","Time"}));
    //    SimpleTableDataAdapter st=new SimpleTableDataAdapter(this, DATA_TO_SHOW);
        adapter= new SimpleTableDataAdapter(this,  items);
        tableView.setDataAdapter(adapter);
Select();

    }
    void insert(String Temp,String Smoke,String Humidity,String time)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,"12" );
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMPERATURE, Temp);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY, Smoke);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SOMKE, Humidity);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME, time);

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values);
        //tv.setText(""+newRowId);
    }
    void insertrow(int i,String temp,String Smoke,String Humidity,String time)
    {
        Log.d("Val",temp);
       items.add(new String[]{temp,Smoke,Humidity,time});
        adapter.notifyDataSetChanged();

    }
    void Select() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
      Delete();
// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,
                //  FeedReaderContract.FeedEntry.COLUMN_NAME_UPDATED,
        };
        String where = FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + "=?";
        String[] select = new String[]{"12"};
// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + " DESC";
        Cursor result = db.rawQuery("select * from " + FeedReaderContract.FeedEntry.TABLE_NAME, null);
        result.moveToFirst();
        int i = 0;
        if (result.moveToFirst()) {

            while (result.isAfterLast() == false) {
                String temp = result.getString(result
                        .getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMPERATURE
                        ));
                String temp1 = result.getString(result
                        .getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_SOMKE
                        ));
                String temp2= result.getString(result
                        .getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY
                        ));
                String temp3= result.getString(result
                        .getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME
                        ));
                insertrow(i++,temp,temp1,temp2,temp3);
                Log.d("time ", result.getString(result
                        .getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_INSERT_TIME
                        )));
                //
                // list.add(name);
                result.moveToNext();
            }
        }
    }

    private void Delete() {
        SQLiteDatabase db =  mDbHelper.getWritableDatabase();
        String sql = "DELETE FROM "+ FeedReaderContract.FeedEntry.TABLE_NAME+" WHERE "+ FeedReaderContract.FeedEntry.COLUMN_NAME_INSERT_TIME+" <= date('now','-1 day')";
       db.execSQL(sql);

    }

    void Select(int i) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TEMPERATURE,
                //  FeedReaderContract.FeedEntry.COLUMN_NAME_UPDATED,
        };
        String where = FeedReaderContract.FeedEntry.COLUMN_NAME_TEMPERATURE+"=?";
        String[] select = new String []{"12"};
// How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_TEMPERATURE + " DESC";

        Cursor c = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                where,                                // The columns for the WHERE clause
                select,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        long itemId = c.getLong(
                c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMPERATURE)
        );
      ///  tv.setText(""+itemId);
    }
}
