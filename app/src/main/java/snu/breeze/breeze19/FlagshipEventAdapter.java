package snu.breeze.breeze19;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FlagshipEventAdapter extends RecyclerView.Adapter<FlagshipEventAdapter.ViewHolder> {
    private final String TAG = FlagshipEventAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<PronightData> pronightData;

    public FlagshipEventAdapter(Context context){
        this.context = context;
        this.pronightData = new ArrayList<PronightData>();
    }

    public void addData(PronightData data){
        if(find(data) == -1){
            pronightData.add(data);
            notifyDataSetChanged();
        }
    }

    public void update(PronightData data){
        int index = find(data);
        if(index != -1){
            pronightData.remove(index);
            pronightData.add(index,data);
        }
        notifyDataSetChanged();
    }

    public void delete(PronightData data){
        int index = find(data);
        if(index != -1){
            pronightData.remove(index);
            notifyDataSetChanged();
        }
    }

    private int find(PronightData data){
        for(int i=0;i<pronightData.size();i++){
            if(pronightData.get(i).getKey().equals(data.getKey())){
                return i;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.flagship_event_component,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(pronightData.get(i));
    }

    @Override
    public int getItemCount() {
        return pronightData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final String TAG = ViewHolder.class.getSimpleName();

        private ImageView image;
        private TextView text;
        private TextView date;

        public ViewHolder(View view){
            super(view);
            this.image = (ImageView) view.findViewById(R.id.image);
            this.text = (TextView) view.findViewById(R.id.text);
            this.date = view.findViewById(R.id.date);
        }

        public void bind(PronightData data){
            text.setText(data.getName());
            date.setText(data.getDate());
            Typeface custom_font2 = Typeface.createFromAsset(context.getAssets(), "fonts/Atami-Display.otf");
            Typeface custom_font3 = Typeface.createFromAsset(context.getAssets(), "fonts/Atami-Light.otf");
            text.setTypeface(custom_font2);
            date.setTypeface(custom_font3);
            switch (data.getBackgroundNo()){
                case 1:
                    image.setImageDrawable(context.getResources().getDrawable(R.drawable.app3));
                    break;

                case 2:
                    image.setImageDrawable(context.getResources().getDrawable(R.drawable.app2));
                    break;

                case 3:
                    image.setImageDrawable(context.getResources().getDrawable(R.drawable.app4));
                    break;

                case 4:
                    image.setImageDrawable(context.getResources().getDrawable(R.drawable.app3));
                    break;
            }
        }
    }
}
