package com.example.jnetbackup.swipe;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WebService {
    //Namespace of the Webservice - It is http://tempuri.org for .NET webservice
    private final static String NAMESPACE = "https://jms.hopto.org:807";
    private final static String URL = "https://jms.hopto.org:805/JMS_Auth_WebService.asmx";
    private final static String SOAP_ACTION = "https://jms.hopto.org:807/GetListOfDevicesInBranch";
    private final static String SOAP_ACTION1 = "https://jms.hopto.org:807/GetUpdatedDataInDevice";
    private final static String METHOD_NAME = "AuthenticateUser";

    public static SoapObject invokeHelloWorldWS(String name, String Pass, String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SoapObject resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE,"GetUpdatedDataInDevice");

        // Property which holds input parameters
        PropertyInfo Branch_id = new PropertyInfo();
        Branch_id.setName("Device_id");
        Branch_id.setValue(1);
        Branch_id.setType(long.class);
        request.addProperty(Branch_id);
        PropertyInfo Token = new PropertyInfo();
        Token.setName("Token");
        Token.setValue("asdasd");
        Token.setType(String.class);
        request.addProperty(Token);



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
          //  Log.d("response",response.getPropertyAsString(0)+""+response.getPropertyCount());

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
          //  resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }
    public static SoapObject invokeHelloWorldWS(Long branch_id, String token, String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SoapObject resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, s);

        // Property which holds input parameters
        PropertyInfo Branch_id = new PropertyInfo();
        Branch_id.setName("Branch_id");
        Branch_id.setValue(1);
        Branch_id.setType(long.class);
        request.addProperty(Branch_id);
        PropertyInfo Token = new PropertyInfo();
        Token.setName("Token");
        Token.setValue("asdasd");
        Token.setType(String.class);
        request.addProperty(Token);



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
            SoapObject response=(SoapObject)envelope.getResponse();
            // Assign it to resTxt variable static variable
            resTxt = response;
            response.getPropertyCount();
         //   Log.d("response",response.getPropertyAsString(0)+""+response.getPropertyCount());

        } catch (Exception e) {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
        //    resTxt = "Error occured";
        }
        //Return resTxt to calling object
        return resTxt;
    }
    private static String convertToHex(byte[] data) {
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

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
}