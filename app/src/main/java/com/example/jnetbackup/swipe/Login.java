package com.example.jnetbackup.swipe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.gms.auth.GoogleAuthUtil;

//import com.firebase.client.AuthData;
//import com.firebase.client.Firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Login extends AppCompatActivity {

    private DefaultHttpClient httpClient;
    private HttpPost httpPost;
    EditText et,et1;
    ArrayList<String> Branch_name=new ArrayList<String>();
    ArrayList<String> Branch_id=new ArrayList<String>();
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        et = (EditText) findViewById(R.id.editText);
        et1 = (EditText) findViewById(R.id.editText2);
        CheckBox cb  = (CheckBox) findViewById(R.id.checkBox);
        Button b= (Button) findViewById(R.id.button2);
      //  authentication();
        Branch_name=new ArrayList<String>();
        Branch_id=new ArrayList<String>();
        assert cb != null;
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            et1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        else
        {
            et1.setInputType(129);
        }
    }
});
        assert b != null;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Aync_login soap=new Aync_login();
                soap.execute(et.getText().toString(),et1.getText().toString());

//                AsyncTaskRunner runner = new AsyncTaskRunner();
//                runner.execute("testsql.ezweb.com.pk/select1.php");
//                String result = null;
//                try {
//                   result= runner.get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//Log.d("response",result);
//                try {
//                  String[] t=  result.split("<script");
//                    jsonresult(t[0]);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    private void authentication() {
       // GoogleAuthUtil.getToken()

    }
    class Aync_login extends AsyncTask<String, String, String>
    {String result;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
            Log.d("Return",s.toString());

                if (s.equals("true")) {
                    FirebaseMessaging.getInstance().subscribeToTopic("test");
                    token = FirebaseInstanceId.getInstance().getToken();
                    // EditText et = (EditText) findViewById(R.id.editText);
                    //   et.setText(token);
                    Log.d("token", token);
                    Async_Ksoap soap = new Async_Ksoap();
                    soap.execute(token, et.getText().toString(), "");
                    Global.Counter1.set(0);
                    Global.Counter2.set(0);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();
                }
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            WebServic ws = new WebServic();
            try {
                result= ws.invokeHelloWorldWS(params[0],params[1]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return result;

        }
    }
class Async_Ksoap extends AsyncTask<String,String,SoapObject>
{
    SoapObject result;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected SoapObject doInBackground(String... params) {
        WebServic ws = new WebServic();
        try {
           result= ws.invokeHelloWorldWS(params[0],params[1],"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(SoapObject s) {
        super.onPostExecute(s);
        char t[] = new char[50];
int count=0;
        try {


            Log.d("Return", s.toString());
            if (s != null) {
                for (int i = 0; i < s.getPropertyCount(); i++) {
                    String temp = s.getProperty(i).toString();
                    Log.d("Return1", temp);
                    if (temp.contains("|")) {
                        Log.d("Found", "Yes");
                    }
// for(int j  = 0 ;j<temp.length();j++)
// {
//     if(temp.charAt(i)=='|')
//     {
//         Log.d("Char",t.toString());
//     }
//     else
//     {
//         t[count++]=temp.charAt(i);
//     }
// }
                    //   temp="JBS_01||Blue Area Branch";
                    StringTokenizer tokens = new StringTokenizer(temp, "|");


                    //   Log.d("Return2",tokens.nextToken()+tokens.nextToken());
                    Branch_id.add(tokens.nextToken());
                    tokens.nextToken();
                    Branch_name.add(tokens.nextToken());
                }

            }
        }
        catch (Exception e)
        {
            Log.d("Exception",e.toString());
        }
Intent i =new Intent(getBaseContext(),MainActivity.class);
        Bundle b =new Bundle();
        b.putStringArrayList("Branch_id",Branch_id);
        b.putStringArrayList("Branch_name",Branch_name);
        b.putString("username",et.getText().toString());
        b.putString("token",token);
        Log.d("Branchid and BRanch name",Branch_id.toString());
       // b.putStringArray("Branch_id",Branch_id);
        //i.putExtra("Branch_id",Branch_id);
        i.putExtras(b);
        Branch_name=new ArrayList<String>();
        Branch_id=new ArrayList<String>();
        startActivity(i);
        finish();

    }
}
    private void jsonresult(String result) throws JSONException {
        JSONObject object = new JSONObject(result
        );
        Toast.makeText(getApplication(), (CharSequence) object.get("re"),Toast.LENGTH_LONG).show();
     if(object.get("re").equals("success"))
     {
         startActivity(new Intent(getBaseContext(),MainActivity.class));
     }
        else
     {
         Toast.makeText(getApplication(),"Invalid Username Or Password",Toast.LENGTH_LONG).show();
     }
    }

    private String fetchtempa(String ip) {

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
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("user",et.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("pass",et1.getText().toString()));
        // "Hi, trying Android HTTP post!"));

        // Url Encoding the POST parameters
       try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // writing error to Log
            e.printStackTrace();
        }

        // Making HTTP Request

        try {
            HttpResponse response = httpClient.execute(httpPost);

            // writing response to log
            Log.d("Http Response:", response.toString());

            String responseStr = EntityUtils.toString(response.getEntity());
            //  Toast.makeText(getApplicationContext(),responseStr,Toast.LENGTH_LONG).show();

            return responseStr;
        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();
            Log.e("error",e.toString());
        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
            Log.e("error",e.toString());
        }

        return null;
    }
    public class WebServic {
        //Namespace of the Webservice - It is http://tempuri.org for .NET webservice
        private final static String NAMESPACE = "https://jms.hopto.org:807";
        private final static String URL = "https://jms.hopto.org:805/JMS_Auth_WebService.asmx";
        private final static String SOAP_ACTION = "https://jms.hopto.org:807/AuthenticateUser";
        private final static String SOAP_ACTION1 =     "https://jms.hopto.org:807/SaveTokenAndGetBranches";
        private final static String METHOD_NAME = "SaveTokenAndGetBranches";
        private final static String METHOD_NAME1 = "AuthenticateUser";

        public SoapObject invokeHelloWorldWS(String token, String userName, String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            SoapObject resTxt = null;
            // Create request
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            // Property which holds input parameters
            PropertyInfo CountryName = new PropertyInfo();
            CountryName.setName("token");
            CountryName.setValue(token);
            CountryName.setType(String.class);
            request.addProperty(CountryName);
            PropertyInfo CityName = new PropertyInfo();
            CityName.setName("userName");
            CityName.setValue(userName);
            CityName.setType(String.class);
            request.addProperty(CityName);



            // Create envelope
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            //Set envelope as dotNet
            envelope.dotNet = true;
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            // Create HTTP call object
            SSlconnection.allowAllSSL();
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                // Get the response
                SoapObject response=(SoapObject)envelope.getResponse();
                // Assign it to resTxt variable static variable
                resTxt = response;
                response.getPropertyCount();
             //   Log.d("response",response.getPropertyAsString(0)+""+response.getPropertyCount());

            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
               // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return resTxt;
        }
        public String invokeHelloWorldWS(String name, String Pass) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            String resTxt = null;
            // Create request
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);

            // Property which holds input parameters
            PropertyInfo CountryName = new PropertyInfo();
            CountryName.setName("username");
            CountryName.setValue(name);
            CountryName.setType(String.class);
            request.addProperty(CountryName);
            PropertyInfo CityName = new PropertyInfo();
            CityName.setName("password");
            CityName.setValue(SHA1(Pass));
            CityName.setType(String.class);
            request.addProperty(CityName);



            // Create envelope
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            //Set envelope as dotNet
            envelope.dotNet = true;
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            // Create HTTP call object
            SSlconnection.allowAllSSL();
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                // Invoke web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                // Get the response
                Log.d("Response",envelope.getResponse().toString());
                String response=  envelope.getResponse().toString();
                // Assign it to resTxt variable static variable
                resTxt = response;
                //response.getPropertyCount();
                //   Log.d("response",response.getPropertyAsString(0)+""+response.getPropertyCount());

            } catch (Exception e) {
                //Print error
                e.printStackTrace();
                //Assign error message to resTxt
                // resTxt = "Error occured";
            }
            //Return resTxt to calling object
            return resTxt;
        }
        private  String convertToHex(byte[] data) {
            StringBuilder buf = new StringBuilder();
            for (byte b : data) {
                int halfbyte = (b >>> 4) & 0x0F;
                int two_halfs = 0;
                do {
                    buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                    halfbyte = b & 0x0F;
                } while (two_halfs++ < 1);
            }
            return buf.toString();
        }

        public  String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            byte[] sha1hash = md.digest();
            return convertToHex(sha1hash);
        }
    }
}
