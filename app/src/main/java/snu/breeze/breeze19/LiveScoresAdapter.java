package snu.breeze.breeze19;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
        if(index != -1) {
            liveScoreData.remove(index);
            liveScoreData.add(index, data);
            notifyDataSetChanged();
        }
    }

    public void delete(LiveScoreData data){
        liveScoreData.remove(isPresent(data));
        notifyDataSetChanged();
    }

    private int isPresent(LiveScoreData data){
        for(int i=0;i<liveScoreData.size();i++){
            if(liveScoreData.get(i).getKey().equals(data.getKey())){
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

        private TextView sportName;
        private TextView teamname1;
        private TextView teamname2;
        private Odometer score1;
        private Odometer score2;

        public ViewHolder(View view){
            super(view);
            sportName = (TextView) view.findViewById(R.id.sportname);
            teamname1 = (TextView) view.findViewById(R.id.team1);
            teamname2 = (TextView) view.findViewById(R.id.team2);
            score1 = (Odometer) view.findViewById(R.id.score1);
            score2 = (Odometer) view.findViewById(R.id.score2);
            adapter1 = new ScoreAdapter();
            adapter2 = new ScoreAdapter();
        }

        public void bind(LiveScoreData data){
            DisplayMetrics dimensions = context.getResources().getDisplayMetrics();
            int width = dimensions.widthPixels;
            int height = dimensions.heightPixels;
            switch(data.getSportName()){
                case "football" :
                    sportName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_footbalal,0,0,0);
                    break;

                case "basketball" :
                    sportName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_basketball,0,0,0);
                    break;
                case "volleyball" :
                    sportName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_volleyball,0,0,0);
                    break;

                case "tennis" :
                    sportName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tennis,0,0,0);
                    break;

                case "badminton" :
                    sportName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_badminton,0,0,0);
                    break;
            }
            sportName.setText(data.getSportName().substring(0,1).toUpperCase() + data.getSportName().substring(1));
            teamname1.setWidth(width/5);
            teamname2.setWidth(width/5);
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
