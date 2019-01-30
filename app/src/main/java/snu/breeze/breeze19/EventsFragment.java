package snu.breeze.breeze19;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    private static final String TAG = "EventsFragment";
    private EventsAdapter adapter;
    private RecyclerView eventsView;
    private ArrayList<Object> eventData = new ArrayList<>();

    public EventsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.events_fragment,container,false);
        EventsData event = new EventsData("a","a","a","a","a");
        EventsData event2 = new EventsData("a","a","a","a","a");
        eventData.add(event);
        eventData.add(event2);
        eventsView = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        eventsView.setLayoutManager(manager);
        adapter = new EventsAdapter(eventData, getActivity().getApplicationContext());
        eventsView.setAdapter(adapter);
        return view;

    }
}


