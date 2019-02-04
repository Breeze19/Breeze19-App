package snu.breeze.breeze19;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FCMService extends FirebaseMessagingService {
    private final String TAG = FCMService.class.getSimpleName();

    @Override
    public void onNewToken(String token){
        super.onNewToken(token);
        SharedPreferences preferences = getSharedPreferences("breeze19",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.FCM_TOKEN_KEY,token);
        String key = preferences.getString(Constants.FCM_TOKEN_FIREBASE_KEY,"");
        if(key.length() == 0){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FCM_TOKEN_FIREBASE_PATH).push();
            key = reference.getKey();
            editor.putString(Constants.FCM_TOKEN_FIREBASE_KEY,reference.getKey());
        }
        editor.apply();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FCM_TOKEN_FIREBASE_PATH + "/" + key);
        reference.setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"FCM token saved");
                }
            }
        });
    }

    @Override
    public void onMessageReceived(RemoteMessage message){
        if(message.getData().size() > 0){
            Log.d(TAG,"Notification data received");
            sendNotification(message.getData());
        }
    }

    private void sendNotification(Map<String,String> data){
        Bundle bundle = new Bundle();
        for(Map.Entry<String,String> entry : data.entrySet()){
            Log.e(TAG,entry.getValue());
            bundle.putString(entry.getKey(),entry.getValue());
        }
        Intent startAppIntent = new Intent(this,MainActivity.class);
        startAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,startAppIntent,PendingIntent.FLAG_ONE_SHOT);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("fcm_channel", "Snuhacks_fcm", importance);
            mChannel.setDescription("Notification channel from Snuhacks");
            mChannel.enableLights(true);
            mChannel.setLightColor(R.color.red);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100,100});
            mNotificationManager.createNotificationChannel(mChannel);
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_layout);
        contentView.setTextViewText(R.id.title,(bundle.getString("heading")));
        contentView.setTextViewText(R.id.text, bundle.getString("content").substring(0,Math.min(bundle.getString("content").length(),40)));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"fcm_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{100,100})
                .setSound(alarmSound)
                .setContent(contentView);

        mNotificationManager.notify(0,builder.build());
    }
}
