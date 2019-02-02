package snu.breeze.breeze19;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

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

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    MenuItem prevMenuItem;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        preferences = getSharedPreferences("breeze19",0);
        viewPager =  findViewById(R.id.view_pager);
        saveFCMToken();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.maps:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.events:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.contact:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),true);
        viewPager.setAdapter(pagerAdapter);
    }

    private void saveFCMToken(){
        Log.e(TAG,"Saving FCM");

        if(true) {
            if (preferences.getString(Constants.FCM_TOKEN_FIREBASE_KEY, "").length() == 0) {
                Log.e(TAG, "FCM token saving to DB");
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Log.e(TAG, "FCM token saving here to DB");
                        DatabaseReference reference = database.getReference(Constants.FCM_TOKEN_FIREBASE_PATH).push();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Constants.FCM_TOKEN_KEY, instanceIdResult.getToken());
                        editor.putString(Constants.FCM_TOKEN_FIREBASE_KEY,reference.getKey());
                        editor.apply();
                        Log.e(TAG, Constants.FCM_TOKEN_KEY);
                        reference.setValue(instanceIdResult.getToken()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.e(TAG, "FCM token saving1 to DB");
                                if (task.isSuccessful()) {
                                    Log.e(TAG, "FCM token saved to DB");
                                }
                                else{
                                    Log.e(TAG,"Task unsuccessful");
                                }
                            }
                        });
                    }
                });
            } else {
                Log.e(TAG, "FCM token already saved to DB");
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
