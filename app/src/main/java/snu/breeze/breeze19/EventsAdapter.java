package snu.breeze.breeze19;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private final String TAG = EventsAdapter.class.getSimpleName();

    private ArrayList<EventsData> eventsData;
    private Context Context;
    private String category;
    private ExpansionLayoutCollection expansionLayoutCollection = new ExpansionLayoutCollection();

    public EventsAdapter(Context context, String category){
        this.Context = context;
        this.category = category;
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
        expansionLayoutCollection.add(holder.getExpansionLayout());
    }

    @Override
    public int getItemCount() {
        return eventsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final String TAG = ViewHolder.class.getSimpleName();

        private FancyButton liveScores;
        private ExpansionLayout expansionLayout;
        private TextView eventName;
        private TextView eventDetails;
        private TextView eventContact;
        private TextView eventDate;
        private TextView eventVenue;
        private LinearLayout container;


        public ViewHolder(View view){
            super(view);
            expansionLayout = (ExpansionLayout) view.findViewById(R.id.expansion_layout);
            expansionLayoutCollection.openOnlyOne(false);
            // expansionLayout.setEnable(false);
            liveScores = (FancyButton) view.findViewById(R.id.live_scores_button);
            eventName = view.findViewById(R.id.event_name);
            eventDetails = (TextView) view.findViewById(R.id.event_details);
            eventVenue = (TextView) view.findViewById(R.id.event_venue);
            eventContact = view.findViewById(R.id.event_contact);
            eventDate = view.findViewById(R.id.event_date);
            container = view.findViewById(R.id.container);
        }

        public void bind(final EventsData data){
            float attendance = 0.0f;
            if("sports".equals(category)){
                liveScores.setVisibility(View.VISIBLE);
                liveScores.setCustomTextFont("Atami-Light.otf");
                liveScores.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent liveScoresIntent = new Intent(Context,LiveScoresActivity.class);
                        liveScoresIntent.putExtra(Constants.INTENT_KEY_SPORT_NAME,data.geteventsName().substring(0,data.geteventsName().indexOf('(')).trim().toLowerCase());
                        liveScoresIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Context.startActivity(liveScoresIntent);
                    }
                });

                container.setPadding(0,0,0,0);
                eventDate.setVisibility(View.GONE);
                eventDate.setVisibility(View.GONE);
                eventContact.setVisibility(View.GONE);
                eventDetails.setVisibility(View.GONE);
                eventVenue.setVisibility(View.GONE);
            }
            ArrayList<String> details_here;
            Typeface custom_font = Typeface.createFromAsset(Context.getAssets(), "fonts/Biko_Regular.otf");
            eventName.setTypeface(custom_font);
            eventDetails.setTypeface(custom_font);
            eventDate.setTypeface(custom_font);
            eventVenue.setTypeface(custom_font);
            eventContact.setTypeface(custom_font);
            eventName.setText(((EventsData) data).geteventsName());
            DisplayMetrics displayMetrics =Context.getResources().getDisplayMetrics();
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            eventName.setTextSize(height/125);
            eventDate.setText(((EventsData) data).geteventDate());
            eventContact.setText("Contact - " + ((EventsData) data).geteventContact());
            if(!((EventsData) data).geteventContact().equals(""))
            {
            String phoneNo1 = ((EventsData) data).geteventContact().split("\\(")[1];
            final String phoneNo = phoneNo1.split("\\)")[0];
            eventContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar.make(v, "Calling", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Context.startActivity(intent);
                }
            }); }
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
