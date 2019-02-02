package snu.breeze.breeze19;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LiveScoresActivity extends AppCompatActivity {
    private final String TAG = LiveScoresActivity.class.getSimpleName();

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener liveScoresChildEventListener;

    private RecyclerView liveScoresView;
    private LiveScoresAdapter adapter;

    private String sportName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_scores);
        if(getIntent().getExtras() != null){
            sportName = getIntent().getExtras().getString(Constants.INTENT_KEY_SPORT_NAME);
        }
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("/data/livescores/" + sportName + "/");
        Log.e("AGAGAG",sportName);
        liveScoresView = (RecyclerView) findViewById(R.id.live_scores_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        liveScoresView.setLayoutManager(manager);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(liveScoresChildEventListener == null){
            liveScoresChildEventListener = getChildEventListener();
        }
        reference.addChildEventListener(liveScoresChildEventListener);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(liveScoresChildEventListener != null){
            reference.removeEventListener(liveScoresChildEventListener);
        }
    }

    public ChildEventListener getChildEventListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()) {
                    if (adapter == null) {
                        adapter = new LiveScoresAdapter(getApplicationContext());
                        liveScoresView.setAdapter(adapter);
                    }
                    LiveScoreData data = dataSnapshot.getValue(LiveScoreData.class);
                    data.setKey(reference.getKey());
                    adapter.add(data);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    LiveScoreData data = dataSnapshot.getValue(LiveScoreData.class);
                    data.setKey(reference.getKey());
                    adapter.modify(data);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    LiveScoreData data = dataSnapshot.getValue(LiveScoreData.class);
                    data.setKey(reference.getKey());
                    adapter.delete(data);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"Error:- Code: " + databaseError.getCode() + " Message: " + databaseError.getMessage());
            }
        };
    }
}
