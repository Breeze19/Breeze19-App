package snu.breeze.breeze19;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LiveEventsAdapter extends RecyclerView.Adapter<LiveEventsAdapter.ViewHolder> {
    private final String TAG = LiveEventsAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<LiveEventsData> liveEventsData;

    public LiveEventsAdapter(Context context){
        this.context = context;
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
        liveEventsData.remove(index);
        notifyDataSetChanged();
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

        public ViewHolder(View view){
            super(view);
            parentLayout = (LinearLayout) view.findViewById(R.id.parent_layout);
            liveDataParentLayout = (LinearLayout) view.findViewById(R.id.live_data_parent_layout);
            liveView = (TextView) view.findViewById(R.id.live);
            sportView = (TextView) view.findViewById(R.id.sportname);
            headingView = (TextView) view.findViewById(R.id.heading);
            contentView = (TextView) view.findViewById(R.id.content);
        }

        public void bind(LiveEventsData data){
            parentLayout.setVisibility(View.GONE);
            liveDataParentLayout.setVisibility(View.VISIBLE);
            liveView.setVisibility(View.GONE);
            sportView.setVisibility(View.GONE);
            headingView.setText(data.getHeading());
            contentView.setText(data.getContent());
        }
    }

}
