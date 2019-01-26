package snu.breeze.breeze19;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private FirebaseDatabase database;

    private ConnectivityManager connectivityManager;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        preferences = getSharedPreferences("breeze19",0);
        saveFCMToken();
    }

    private void saveFCMToken(){
        if(isConnected()) {
            if (preferences.getString(Constants.FCM_TOKEN_FIREBASE_KEY, "").length() == 0) {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        DatabaseReference reference = database.getReference(Constants.FCM_TOKEN_FIREBASE_PATH).push();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Constants.FCM_TOKEN_KEY, instanceIdResult.getToken());
                        editor.apply();
                        reference.setValue(instanceIdResult.getToken()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "FCM token saved to DB");
                                }
                            }
                        });
                    }
                });
            } else {
                Log.d(TAG, "FCM token already saved to DB");
            }
        }
    }

    private boolean isConnected(){
        boolean connected = false;
        if(connectivityManager != null){
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            for(NetworkInfo info : networkInfo){
                if((info.getTypeName().equalsIgnoreCase("WIFI") ||
                        info.getTypeName().equalsIgnoreCase("MOBILE")) &&
                        info.isConnected() && info.isAvailable()){
                    connected = true;
                }
            }
        }
        return connected;
    }

}
