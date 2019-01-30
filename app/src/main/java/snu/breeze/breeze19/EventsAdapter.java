package snu.breeze.breeze19;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;



public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private final String TAG = EventsAdapter.class.getSimpleName();

    private ArrayList<EventsData> eventsData;
    private Context Context;

    private ExpansionLayoutCollection expansionLayoutCollection = new ExpansionLayoutCollection();

    public EventsAdapter(Context context){
        this.Context = context;
        this.eventsData = new ArrayList<EventsData>();
    }

    public void addData(EventsData data){
        if(find(data) == -1){
            eventsData.add(data);
            notifyDataSetChanged();
        }
    }

    public void update(EventsData data){
        int index = find(data);
        if(index != -1){
            eventsData.remove(index);
            eventsData.add(index,data);
        }
        notifyDataSetChanged();
    }

    public void delete(EventsData data){
        int index = find(data);
        eventsData.remove(index);
        notifyDataSetChanged();
    }

    private int find(EventsData data){
        for(int i=0;i<eventsData.size();i++){
            if(eventsData.get(i).getKey().equals(data.getKey())){
                return i;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.expansion_layout_component,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(eventsData.get(position));
        Log.d("bind", eventsData.get(position).getAllDetails().toString());
        expansionLayoutCollection.add(holder.getExpansionLayout());
    }

    @Override
    public int getItemCount() {
        return eventsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final String TAG = ViewHolder.class.getSimpleName();

        private ExpansionLayout expansionLayout;
        private TextView eventName;
        private TextView eventDetails;
        private TextView eventContact;
        private TextView eventDate;
        private TextView eventVenue;


        public ViewHolder(View view){
            super(view);
            expansionLayout = (ExpansionLayout) view.findViewById(R.id.expansion_layout);
            expansionLayoutCollection.openOnlyOne(false);
            // expansionLayout.setEnable(false);
            eventName = view.findViewById(R.id.event_name);
            eventDetails = (TextView) view.findViewById(R.id.event_details);
            eventVenue = (TextView) view.findViewById(R.id.event_venue);
            eventContact = view.findViewById(R.id.event_contact);
            eventDate = view.findViewById(R.id.event_date);
        }

        public void bind(Object data){
            float attendance = 0.0f;
            Log.d(TAG,"happing");
            ArrayList<String> details_here;
            eventName.setText(((EventsData) data).geteventsName());
            Log.d("TAG",((EventsData) data).geteventsName());
            DisplayMetrics displayMetrics =Context.getResources().getDisplayMetrics();
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            eventName.setTextSize(height/125);
            eventDate.setText(((EventsData) data).geteventDate());
            eventContact.setText("Contact - " + ((EventsData) data).geteventContact());
            eventContact.setText(((EventsData) data).geteventContact());
            eventDetails.setText(((EventsData) data).geteventsDetails());
            eventVenue.setText("Venue - " + ((EventsData) data).geteventVenue());
            expansionLayout.collapse(true);
        }

        public ExpansionLayout getExpansionLayout(){
            return expansionLayout;
        }
    }

}
