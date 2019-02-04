package snu.breeze.breeze19;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class flagshipFragment extends Fragment {
    private final String TAG = EventsPage.class.getSimpleName();

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private RecyclerView flagshipRecycler;
    private FlagshipEventAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("/data/pronights/");
    }


    private PronightData getPronightDataFromSnapshot(DataSnapshot snapshot) {
        PronightData data = snapshot.getValue(PronightData.class);
        data.setKey(snapshot.getKey());
        return data;
    }


    private ChildEventListener getChildEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    if (adapter == null) {
                        adapter = new FlagshipEventAdapter(getContext());
                        flagshipRecycler.setAdapter(adapter);
                    }
                    adapter.addData(getPronightDataFromSnapshot(dataSnapshot));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    adapter.update(getPronightDataFromSnapshot(dataSnapshot));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    adapter.delete(getPronightDataFromSnapshot(dataSnapshot));
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "DatabaseError:- Code: " + databaseError.getCode() + "Message: " + databaseError.getMessage());
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flagship, container, false);
        flagshipRecycler = (RecyclerView) view.findViewById(R.id.flagship_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        flagshipRecycler.setLayoutManager(manager);
        flagshipRecycler.setAdapter(adapter);
        reference.addChildEventListener(getChildEventListener());
        Log.d(TAG, "flagship page made");
        return view;
    }


}
