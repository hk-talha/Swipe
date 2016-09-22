package com.example.jnetbackup.swipe;

/**
 * Created by jnetbackup on 6/14/2016.
 */
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.ksoap2.serialization.SoapObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

@SuppressLint("ValidFragment")
public class Android extends Fragment {
    HttpClient httpClient;
    HttpPost httpPost;
    private Button b;
    String Time;
    private TextView tv,tv1,tv2;
    ArrayList<String> Values;
public static String ip1;
    ArrayList<String> Branch_id = new ArrayList<String>();
    ArrayList<String> Branch_name = new ArrayList<String>();
    String name;
    FeedReaderDbHelper mDbHelper ;
    ArrayAdapter<String> Device_adapter;
    ArrayList<String> Device_id,Device_name;
    Context context;
    DonutProgress progress,progress1,progress2;
    String username;
    long branchId,deviceId;
    private static final String STATE_ITEMS ="device_id";
    private String branchname;
    private ProgressDialog pDialog;

    public Android(String ip,String name,ArrayList<String> t,ArrayList<String> t1,Context c,String username) {
        ip1=ip;
        this.name=name;
        Branch_id=t;
        Branch_name=t1;
        context=c;
        Values= new ArrayList<String>();
        Device_id=new ArrayList<String>();
        Device_name=new ArrayList<String>();
    this.username=username;
    }
    public Android(){}





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDbHelper = new FeedReaderDbHelper(getActivity());
      //  assert savedInstanceState != null;
    //    Device_id=savedInstanceState.getStringArrayList("device_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View androi = inflater.inflate(R.layout.design_final, container, false);

        final Spinner spinner = (Spinner) androi.findViewById(R.id.spinner);
        final Spinner spinner1 = (Spinner) androi.findViewById(R.id.spinner2);
        progress= (DonutProgress) androi.findViewById(R.id.donut_progress);
        progress1= (DonutProgress) androi.findViewById(R.id.donut_progress1);
        progress2= (DonutProgress) androi.findViewById(R.id.donut_progress2);
        final String[] ip =new String[]{"119.157.128.3:2000","119.157.128.3:2000","119.157.128.3:2030"};
        final ArrayList<String> list = new ArrayList();
        list.add("Choose data centre");
        list.add("Karim Chamber");
        list.add("Bahadurabad");
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
              //  Toast.makeText(getActivity(),""+tab.getPosition(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };
       // PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Create an ArrayAdapter using the string array and a default spinner layout

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.spinner     ,Branch_name);
        Device_adapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.spinner    ,Device_name);
        Device_adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        try {
            spinner1.setAdapter(Device_adapter);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
//Device Spinner
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(Device_id.get(position)=="No Devices"||Device_name.get(position)=="Choose Device")
                {Log.d("device_id",""+Device_id.get(position));
                    b.setEnabled(false);
                }
                else
                {
                    Log.d("device_id",""+Device_id.get(position));
                deviceId = Long.parseLong(Device_id.get(position));
                    b.performClick();
            b.setEnabled(true);
                }}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

// Specify the layout to use when the list of choices appears
        //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
       spinner.setAdapter(adapter);

        //Branch Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Log.d("container", (String) container.getTag())
                // ;
             //   int spinnerPosition = adapter.getPosition(name);
               // spinner.setSelection(spinnerPosition);

                    branchId = Long.parseLong(Branch_id.get(position));
                    Async_Ksoap soap = new Async_Ksoap();
                    soap.execute();

if(list.get(position)=="Choose data centre")
{
    Log.d("ip",ip1);
}
                else
{
    Log.d("ip 1",ip1);
                ip1=ip[position];}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Find the root view


        // Set the color
        Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(),
                "digital.ttf");
        this.b = (Button) androi.findViewById(R.id.button);
       // tv = (TextView) androi.findViewById(R.id.textView);
       // tv.setTypeface(myTypeface);
      ///  tv1 = (TextView) androi.findViewById(R.id.textView2);
       // tv1.setTypeface(myTypeface);
        //tv2 = (TextView) androi.findViewById(R.id.textView3);
       // tv2.setTypeface(myTypeface);

        //Fetch Button
        this.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async_Ksoap_Device device = new Async_Ksoap_Device();
                device.execute();
                //  fetchtemp(ip1);
                //fetchtempa(ip1);
              //  Sql sql = new Sql();
             //   insert("15","55","50");
              //  AsyncTaskRunner runner = new AsyncTaskRunner();
            //    runner.execute(ip1);8020187300016

              //  Select();
                //runner.get();
//tv.setText("hi");
            }
        });
       // iv = (ImageView) android.findViewById(com.example.jnetbackup.temperature.R.id.imageView);
        return androi;
    }
    private ArrayList<String> fetchtempa(String ip) {

        try {
            HttpParams httpParameters = new BasicHttpParams();
// Set the timeout in milliseconds until a connection is established.
// The default value is zero, that means the timeout is not used.
            int timeoutConnection = 3000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
// Set the default socket timeout (SO_TIMEOUT)
// in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            httpClient = new DefaultHttpClient(httpParameters);
            // Creating HTTP Post
            InetSocketAddress address = new InetSocketAddress(ip,2000);
            httpPost = new HttpPost(
                    "http://"+ip);
        }
        catch (Exception e){
            Log.e("error",e.toString());}
        // Building post parameters
        // key and value pair
        //List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        //nameValuePair.add(new BasicNameValuePair("email", "user@gmail.com"));
        //nameValuePair.add(new BasicNameValuePair("message",
        // "Hi, trying Android HTTP post!"));

        // Url Encoding the POST parameters
  /*      try {
          //  httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // writing error to Log
            e.printStackTrace();
        }*/

        // Making HTTP Request

        try {
            HttpResponse response = httpClient.execute(httpPost);

            // writing response to log
            Log.d("Http Response:", response.toString());

            String responseStr = EntityUtils.toString(response.getEntity());
            //  Toast.makeText(getApplicationContext(),responseStr,Toast.LENGTH_LONG).show();
            Log.e("response",response.toString());
            int pointstart= responseStr.indexOf("TEMPERATURE START");
            String a=responseStr.substring(pointstart+18,pointstart+20);
            int pointstart1= responseStr.indexOf("HUMIDITY START");
            String a1=responseStr.substring(pointstart1+15,pointstart1+17);
            int pointstart2= responseStr.indexOf("SMOKE START");
            String a2=responseStr.substring(pointstart2+13,pointstart2+16);
            if(a2.contains(" "))
            {
                a2=responseStr.substring(pointstart2+13,pointstart2+15);
            }
            // String[] temp= responseStr.split(" ");
            //  Toast.makeText(getApplicationContext(),"Page Refreshed",Toast.LENGTH_LONG).show();
            // tv.setText("Temperature: "+a+"C");
            // tv1.setText("Humidity: "+a1+"%");
            // tv2.setText("Smoke: "+a2+"ppm");
            ArrayList<String> list=new ArrayList<>();
            list.add(a);
            list.add(a1);
            list.add(a2);
            return list;
        } catch (ClientProtocolException e) {
            writetofile(e.toString());
            // writing exception to log
            e.printStackTrace();
            Log.e("error",e.toString());
        } catch (IOException e) {
            writetofile(e.toString());
            // writing exception to log
            e.printStackTrace();
            Log.e("error",e.toString());
        }

        return null;
    }
    private class AsyncTaskRunner extends AsyncTask<String, String, ArrayList<String>> {

        private String resp;

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            publishProgress("Sleeping...");
            // Calls onProgressUpdate()
            ArrayList<String> list = new ArrayList<>();
            list = fetchtempa(params[0]);
            return list;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            // execution of result of Long time consuming operation
            // finalResult.setText(result);
            try {
                tv.setText(result.get(0) + "C");
                if (Integer.parseInt(result.get(0)) > 30)
                    tv.setTextColor(Color.parseColor("#ff0000"));
                else if (Integer.parseInt(result.get(0)) < 30)
                    tv.setTextColor(Color.parseColor("#FFFFFF"));
                tv1.setText(result.get(1) + "%");
                if (Integer.parseInt(result.get(1)) > 50)
                    tv1.setTextColor(Color.parseColor("#ff0000"));
                else if (Integer.parseInt(result.get(1)) < 50)
                    tv1.setTextColor(Color.parseColor("#FFFFFF"));
                tv2.setText(result.get(2) + "ppm");
                if (Integer.parseInt(result.get(2)) > 1000)
                    tv2.setTextColor(Color.parseColor("#ff0000"));
                else if (Integer.parseInt(result.get(2)) < 1000)
                    tv2.setTextColor(Color.parseColor("#FFFFFF"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            //  finalResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }

    //insert device values into sql lite db
    void insert(String Temp,String Smoke,String Humidity,String time,String branchname)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID,"12" );
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMPERATURE, Temp);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY, Smoke);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SOMKE, Humidity);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TIME, time);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_INSERT_TIME,getDateTime());
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Branch,branchname);

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values);
        //tv.setText(""+newRowId);
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
        if (result.moveToFirst()) {

            while (result.isAfterLast() == false) {
                String temp = result.getString(result
                        .getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TEMPERATURE
                        ));
                Log.d("Cursor ", "" + temp);
                //
                // list.add(name);
                result.moveToNext();
            }
        }
    }

    // Fetch Device List From the Server
    class Async_Ksoap extends AsyncTask<String, String, SoapObject> {
        SoapObject result;

        @Override
        protected void onPreExecute() {
            Device_id.clear();
            Device_name.clear();
            Device_name.add("Choose Device");
            Device_id.add("0");
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Fetching Device List......");
            pDialog.isIndeterminate();
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected SoapObject doInBackground(String... params) {
            WebService ws = new WebService();
            try {
                Log.d("branchid", "" + branchId);
                result = ws.invokeHelloWorldWS(branchId, gettoken(), username, "GetListOfDevicesInBranch");
            } catch (UnsupportedEncodingException e) {
                writetofile(e.toString());
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                writetofile(e.toString());
                e.printStackTrace();
            }
            Log.d("result", result.toString());
            return result;
        }

        @Override
        protected void onPostExecute(SoapObject s) {
            pDialog.dismiss();
            try {
                b.setEnabled(true);
                SoapObject o = (SoapObject) s.getProperty(0);
                Log.d("token Status", o.getProperty("Token_Status").toString());
                if (o.getProperty("Token_Status").toString().equals("Verified.")) {

                    for (int i = 0; i < s.getPropertyCount(); i++) {
                        SoapObject temp = (SoapObject) s.getProperty(i);
                        Device_id.add(temp.getProperty(1).toString());
                        Device_name.add(temp.getProperty(2).toString());
                        Device_adapter.notifyDataSetChanged();


                    }
                    deviceId= Long.parseLong(Device_id.get(0));
                    Log.d("Device_id",""+deviceId);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Token Expired Login Again", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), Login.class));
                }
                //    Log.d("Value",""+s.getPropertyCount()+""+a.getProperty(0));
            } catch (Exception e) {
                writetofile(e.toString());
                e.printStackTrace();
              //  Device_id=new ArrayList<String>();
                Device_id.add("No Devices");
                Device_name.add("No Device");
                b.setEnabled(false);
                Device_adapter.notifyDataSetChanged();
            }
        }
    }
    //Get Token From Firebase Cloud
    private String gettoken() {
        final String[] token = new String[1];

                 token[0] =   FirebaseInstanceId.getInstance().getToken();



        return token[0];
    }
// Fetch Device Infromation From the Server
    class Async_Ksoap_Device extends AsyncTask<String, String, SoapObject>
    {
        SoapObject result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Fetching data......");
            pDialog.isIndeterminate();
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected SoapObject doInBackground(String... params) {
            WebService ws = new WebService();
            try {
                Log.d("id",""+deviceId);
                result= ws.invokeHelloWorldWS(username,deviceId,gettoken(),"GetUpdatedDataInDevice");
            } catch (UnsupportedEncodingException e) {
                writetofile(e.toString());
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                writetofile(e.toString());
                e.printStackTrace();
            }
            Log.d("result",result.toString());
            return result;
        }

        @Override
        protected void onPostExecute(SoapObject s) {
            parseobject(s);
            pDialog.dismiss();
           // s.getPropertyCount();
            //SoapObject a= (SoapObject) s.getProperty(0);
            //Log.d("Value",""+s.getPropertyCount()+""+a.getProperty(0));
            try{
            progress.refreshDrawableState();
            progress.setProgress(0);
            progress.setMax(1000);
            Log.d("Text ",progress.getPrefixText());
            Log.d("Text ",progress.getSuffixText());
            progress.setSuffixText("ppm");

            ObjectAnimator animation = ObjectAnimator.ofInt(progress, "progress", Integer.parseInt(getint(Values.get(2))));

            animation.setDuration(500); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
                progress1.refreshDrawableState();
                progress1.setProgress(0);
                progress1.setMax(100);
                Log.d("Text ",progress.getPrefixText());
                Log.d("Text ",progress.getSuffixText());
                progress1.setSuffixText("%");

                ObjectAnimator animation1 = ObjectAnimator.ofInt(progress1, "progress", Integer.parseInt(getint(Values.get(1))));

                animation1.setDuration(500); // 0.5 second
                animation1.setInterpolator(new DecelerateInterpolator());
                animation1.start();
                progress2.refreshDrawableState();
                progress2.setProgress(0);
                progress2.setMax(100);



                Log.d("Text ",progress.getPrefixText());
                Log.d("Text ",progress.getSuffixText());
                progress2.setSuffixText("C");

                ObjectAnimator animation2 = ObjectAnimator.ofInt(progress2, "progress", Integer.parseInt(getint(Values.get(0))));

                animation2.setDuration(500); // 0.5 second
                animation2.setInterpolator(new DecelerateInterpolator());
                animation2.start();

              /*  tv.setText(Values.get(0) + "C");
                if (Double.parseDouble(Values.get(0)) > 30)
                    tv.setTextColor(Color.parseColor("#ff0000"));
                else if (Double.parseDouble(Values.get(0)) < 30)
                    tv.setTextColor(Color.parseColor("#FFFFFF"));
                tv1.setText(Values.get(1) + "%");
                if (Double.parseDouble(Values.get(1)) > 50)
                    tv1.setTextColor(Color.parseColor("#ff0000"));
                else if (Double.parseDouble(Values.get(1)) < 50)
                    tv1.setTextColor(Color.parseColor("#FFFFFF"));
                tv2.setText(Values.get(2) + "ppm");
                if (Double.parseDouble(Values.get(2)) > 1000)
                    tv2.setTextColor(Color.parseColor("#ff0000"));
                else if (Double.parseDouble(Values.get(2)) < 1000)
                    tv2.setTextColor(Color.parseColor("#FFFFFF"));*/

            Sql sql = new Sql();
            insert(Values.get(0),Values.get(1),Values.get(2),Time,branchname);
            Values= new ArrayList<String>();
            } catch (Exception e) {
                e.printStackTrace();
                pDialog.dismiss();
            }
        }
    }
// Convert a double into int
    private String getint(String s) {
        StringTokenizer tokens = new StringTokenizer(s, ".");
        String first = tokens.nextToken();// this will contain "Fruit"
        String second = tokens.nextToken();
        Log.d("first",first);
        return first;
    }
// Parse a Soap Object
    private void parseobject(SoapObject s) {
        try {
            SoapObject o= (SoapObject) s.getProperty(0);
            Log.d("token Status",o.getProperty("Token_Status").toString());
            if(o.getProperty("Token_Status").toString().equals("Verified")) {
                for (int i = 0; i < s.getPropertyCount(); i++) {
                    SoapObject temp = (SoapObject) s.getProperty(i);
                    Log.d("Values", temp.getProperty(5).toString());
                    Values.add(temp.getProperty(5).toString());
                    Time = temp.getProperty(6).toString();

                    int h=Branch_id.indexOf(temp.getProperty(3).toString());
               branchname=Branch_name.get(h);
                }
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(),"Token Expired Login Again",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(),Login.class));
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            writetofile(e.toString());
            Toast.makeText(getActivity().getApplicationContext(),"No Data to Show",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            writetofile(e.toString());
            e.printStackTrace();
        }
    }
    // Date and time when a data is fetched from server
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public void writetofile(String data)
    {
        try {


            FileOutputStream fOut = getActivity().openFileOutput("log.txt",getActivity().MODE_WORLD_READABLE);
            fOut.write(data.getBytes());
            fOut.close();
            //Toast.makeText(getBaseContext(), "file saved", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

