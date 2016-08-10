package com.example.jnetbackup.swipe;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Sql extends AppCompatActivity {
    FeedReaderDbHelper mDbHelper ;
    TextView tv,tv1,tv2;
    private TextView tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        mDbHelper= new FeedReaderDbHelper(this);

        Select();
        // tv = (TextView) findViewById(R.id.textView6);

      //  FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);

 //   Select();
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
       tv.setText(""+newRowId);
   }
    void insertrow(int i,String temp,String Smoke,String Humidity,String time)
    {
        TableLayout tb = (TableLayout) findViewById(R.id.table);
        TableRow row= new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,  1);
        row.setLayoutParams(lp);


        tv = new TextView(this);
       // tv.getLayout();

//tv.setBackground(getResources().getDrawable(R.drawable.cell_shape));

        tv.setText(temp);
        tv1 = new TextView(this);
        tv1.setText(Smoke);
        tv2 = new TextView(this);
        tv2.setText(Humidity);
        tv4 = new TextView(this);
        // tv.getLayout();

        tv4.setText(time);

        row.addView(tv);
        row.addView(tv1);
        row.addView(tv2);
        row.addView(tv4);

        tb.addView(row,i);
    }
     void Select() {
                    SQLiteDatabase db = mDbHelper.getReadableDatabase();

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
                            Log.d("Cursor ", "" + temp);
                            //
                            // list.add(name);
                            result.moveToNext();
            }
        }
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
tv.setText(""+itemId);
    }
}
