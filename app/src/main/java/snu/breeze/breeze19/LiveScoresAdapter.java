package snu.breeze.breeze19;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.pinaki.android.wheelticker.Odometer;
import xyz.pinaki.android.wheelticker.OdometerAdapter;

public class LiveScoresAdapter extends RecyclerView.Adapter<LiveScoresAdapter.ViewHolder> {
    private final String TAG = LiveScoresAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<LiveScoreData> liveScoreData;

    public LiveScoresAdapter(Context context){
        this.context = context;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.content_card,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(liveScoreData.get(i));
    }

    @Override
    public int getItemCount() {
        return liveScoreData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final String TAG = ViewHolder.class.getSimpleName();

        private ScoreAdapter adapter1;
        private ScoreAdapter adapter2;

        private TextView teamname1;
        private TextView teamname2;
        private Odometer score1;
        private Odometer score2;

        public ViewHolder(View view){
            super(view);
            teamname1 = (TextView) view.findViewById(R.id.team1);
            teamname2 = (TextView) view.findViewById(R.id.team2);
            score1 = (Odometer) view.findViewById(R.id.score1);
            score2 = (Odometer) view.findViewById(R.id.score2);
            adapter1 = new ScoreAdapter();
            adapter2 = new ScoreAdapter();
        }

        public void bind(LiveScoreData data){
            teamname1.setText(data.getTeam1());
            teamname2.setText(data.getTeam2());
            score1.setAdapter(adapter1);
            score2.setAdapter(adapter2);
            adapter1.setScore(Integer.parseInt(data.getScore1()));
            adapter2.setScore(Integer.parseInt(data.getScore2()));
        }
    }

    public class ScoreAdapter extends OdometerAdapter{
        private final String TAG = ScoreAdapter.class.getSimpleName();

        private int score = 0;

        @Override
        public int getNumber(){
            return score;
        }

        public void setScore(int score){
            this.score = score;
            notifyDataSetChanged();
        }
    }

}
