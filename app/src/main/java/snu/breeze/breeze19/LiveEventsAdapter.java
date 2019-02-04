package snu.breeze.breeze19;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LiveEventsAdapter extends RecyclerView.Adapter<LiveEventsAdapter.ViewHolder> {
    private final String TAG = LiveEventsAdapter.class.getSimpleName();

    public interface ClickListener{
        void sendNotification(LiveEventsData data);
        void edit(LiveEventsData data);
        void delete(LiveEventsData data);
    }

    private Context context;
    private ArrayList<LiveEventsData> liveEventsData;
    private ClickListener listener;

    public LiveEventsAdapter(Context context,ClickListener listener){
        this.context = context;
        this.listener = listener;
        liveEventsData = new ArrayList<LiveEventsData>();
    }

    public void addData(LiveEventsData data){
        if(find(data) == -1){
            liveEventsData.add(data);
            notifyDataSetChanged();
        }
    }

    public void update(LiveEventsData data){
        int index = find(data);
        if(index != -1){
            liveEventsData.remove(index);
            liveEventsData.add(index,data);
        }
        notifyDataSetChanged();
    }

    public void delete(LiveEventsData data){
        int index = find(data);
        if(index != -1) {
            liveEventsData.remove(index);
            notifyDataSetChanged();
        }
    }

    private int find(LiveEventsData data){
        for(int i=0;i<liveEventsData.size();i++){
            if(liveEventsData.get(i).getKey().equals(data.getKey())){
                return i;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.content_card,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(liveEventsData.get(i));
    }

    @Override
    public int getItemCount() {
        return liveEventsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final String TAG = ViewHolder.class.getSimpleName();

        private LinearLayout parentLayout;
        private LinearLayout liveDataParentLayout;
        private TextView liveView;
        private TextView sportView;
        private TextView headingView;
        private TextView contentView;

        private ImageButton sendNotification;
        private ImageButton edit;
        private CardView card;
        private ImageButton delete;

        public ViewHolder(View view){
            super(view);
            edit = view.findViewById(R.id.edit_image_button);
            delete= view.findViewById(R.id.delete_image_button);
            sendNotification = view.findViewById(R.id.send_notification_image_button);
            parentLayout = (LinearLayout) view.findViewById(R.id.parent_layout);
            liveDataParentLayout = (LinearLayout) view.findViewById(R.id.live_data_parent_layout);
            liveView = (TextView) view.findViewById(R.id.live);
            sportView = (TextView) view.findViewById(R.id.sportname);
            headingView = (TextView) view.findViewById(R.id.heading);
            contentView = (TextView) view.findViewById(R.id.content);
            card = view.findViewById(R.id.card);
        }

        public void bind(final LiveEventsData data){
            parentLayout.setVisibility(View.GONE);
            liveDataParentLayout.setVisibility(View.VISIBLE);
            liveView.setVisibility(View.GONE);
            sportView.setText("Event");
            sportView.setTextColor(context.getResources().getColor(R.color.white));
            card.setCardBackgroundColor(context.getResources().getColor(R.color.blue));
            headingView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Biko_Regular.otf"));
            contentView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Biko_Regular.otf"));
            sportView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dance1,0,0,0);
            headingView.setText(data.getHeading());
            contentView.setText(data.getContent());
            if(listener != null) {
                sendNotification.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                sendNotification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.sendNotification(data);
                    }
                });
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.edit(data);
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.delete(data);
                    }
                });
            }
        }
    }

}
