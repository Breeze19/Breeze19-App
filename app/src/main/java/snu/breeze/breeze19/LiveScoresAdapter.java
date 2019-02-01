package snu.breeze.breeze19;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class LiveScoresAdapter extends RecyclerView.Adapter<LiveScoresAdapter.ViewHolder> {
    private final String TAG = LiveScoresAdapter.class.getSimpleName();

    private ArrayList<LiveScoreData> liveScoreData;

    public LiveScoresAdapter(){
        liveScoreData = new ArrayList<LiveScoreData>();
    }

    public void add(LiveScoreData data){
        if(isPresent(data) == -1){
            liveScoreData.add(data);
            notifyDataSetChanged();
        }
    }

    public void modify(LiveScoreData data){
        int index = isPresent(data);
        liveScoreData.remove(index);
        liveScoreData.add(index,data);
        notifyDataSetChanged();
    }

    public void delete(LiveScoreData data){
        liveScoreData.remove(isPresent(data));
        notifyDataSetChanged();
    }

    private int isPresent(LiveScoreData data){
        for(int i=0;i<liveScoreData.size();i++){
            if(liveScoreData.get(i).getKey() == data.getKey()){
                return i;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final String TAG = ViewHolder.class.getSimpleName();

        public ViewHolder(View view){
            super(view);
        }

        public void bind(LiveScoreData data){

        }
    }

}
