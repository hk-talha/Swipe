package com.example.jnetbackup.swipe;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Haseeb on 8/3/2016.
 */
public class FireBaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token=FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEn",token);
   //registertoken(token);
    }

    private void registertoken(String token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();
        try {
        Request request = new Request.Builder()
                .url("http://innovation.ezweb.com.pk/register.php")
                .post(body)
                .build();


            client.newCall(request).execute();
        }
        catch ( Exception e)
        {
            Log.d("Error",e.toString());
        }

    }
}
