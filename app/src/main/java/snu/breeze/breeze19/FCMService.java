package snu.breeze.breeze19;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FCMService extends FirebaseMessagingService {
    private final String TAG = FCMService.class.getSimpleName();

    @Override
    public void onNewToken(String token){
        super.onNewToken(token);
        String key = "";
        SharedPreferences preferences = getSharedPreferences("breeze19",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("fcm_token",token);
        key = preferences.getString("key","");
        if(key.length() == 0){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/data/fcm/token").push();
            key = reference.getKey();
            editor.putString("fcm_key",reference.getKey());
        }
        editor.apply();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("/data/fcm/token/" + key);
        reference.setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"FCM token saved");
                }
            }
        });
    }
}
