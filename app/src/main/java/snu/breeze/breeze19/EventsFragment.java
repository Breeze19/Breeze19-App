package snu.breeze.breeze19;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
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

import java.util.ArrayList;

public class EventsFragment extends Fragment {
    private static final String TAG = EventsFragment.class.getSimpleName();

   private FirebaseDatabase database;
   private DatabaseReference reference;

    private EventsAdapter adapter;
    private RecyclerView eventsView;
    private ArrayList<Object> eventData = new ArrayList<>();

    public EventsFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("/data/events/");
    }

    private ChildEventListener getChildEventListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    if(adapter == null){
                        adapter = new EventsAdapter(getActivity().getApplicationContext());
                        eventsView.setAdapter(adapter);
                    }
                    EventsData data = dataSnapshot.getValue(EventsData.class);
                    data.setKey(dataSnapshot.getKey());
                    adapter.addData(data);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    EventsData data = dataSnapshot.getValue(EventsData.class);
                    data.setKey(dataSnapshot.getKey());
                    adapter.update(data);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    EventsData data = dataSnapshot.getValue(EventsData.class);
                    data.setKey(dataSnapshot.getKey());
                    adapter.delete(data);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"DatabaseError:- Code: " + databaseError.getCode() + "Message: " + databaseError.getMessage());
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.events_fragment,container,false);
        Log.d(TAG,"Creating events fragment");
        eventsView = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        eventsView.setLayoutManager(manager);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.recyclyer_separator));
        eventsView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        reference.addChildEventListener(getChildEventListener());
        return view;
    }
}


