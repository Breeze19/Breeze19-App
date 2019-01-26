package snu.breeze.breeze19;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

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
        //Display Notifications here
    }
}
