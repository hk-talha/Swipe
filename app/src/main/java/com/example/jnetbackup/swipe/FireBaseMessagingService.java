package com.example.jnetbackup.swipe;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Haseeb on 8/3/2016.
 */
public class FireBaseMessagingService extends FirebaseMessagingService {
    int ia=0;
    private int Counter1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        shownotification(remoteMessage.getData().get("message"));
    }

    private void shownotification(String data) {
       Intent i = new Intent(this,Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
i.putExtra("com.example.jnetbackup.swipe.message",data);
     //   Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
       // Intent i = new Intent();
      //  i.setClassName(this, BReceiver.class.getName());
      //  i.setAction("Test");
      //  i.putExtra("message",data);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Alert")
                .setContentText(data)
                .setSmallIcon(R.drawable.warning)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Counter1 = Global.Counter1.incrementAndGet();

        manager.notify(Counter1,builder.build());
    }
    }


