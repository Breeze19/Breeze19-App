package snu.breeze.breeze19;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainPage extends Fragment {
    private final String TAG = MainPage.class.getSimpleName();

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private RecyclerView liveRecyclerView;
    private LiveScoresAdapter adapter;
    private Button button;

    private Integer flag;
    private TextView text1;


    public MainPage(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("/data/livescores/");
    }

    private ChildEventListener getChildEventListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e(TAG,"1");
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if(snapshot.exists()){
                            LiveScoreData data = snapshot.getValue(LiveScoreData.class);
                            data.setKey(dataSnapshot.getKey()+"$"+snapshot.getKey());
                            if(data.getisLive() == 1){
                                Log.e("DATA",data.toString());
                                adapter.add(data);
                            }
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e(TAG,"Called");
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if(snapshot.exists()){
                            LiveScoreData data = snapshot.getValue(LiveScoreData.class);
                            data.setKey(dataSnapshot.getKey()+"$"+snapshot.getKey());
                            if(data.getisLive() == 1){
                                adapter.modify(data);
                            }
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"Error:- Code: " + databaseError.getCode() + " Message: " + databaseError.getMessage());
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_page_fragment,container,false);
        liveRecyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        liveRecyclerView.setLayoutManager(manager);
        LinearLayout layout = view.findViewById(R.id.layout3);
        LinearLayout layout2 = view.findViewById(R.id.layout2);
        text1 = view.findViewById(R.id.live);
        button = view.findViewById(R.id.admin_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminIntent = new Intent(getContext(),AdminActivity.class);
                startActivity(adminIntent);
            }
        });
       // GifImageView gif = new GifImageView(getContext());
        GifImageView gif2 = new GifImageView(getContext());
        flag = 0;
       // gif = view.findViewById(R.id.gif);
        gif2 = view.findViewById(R.id.gif2);
        final GifImageView finalGif = gif2;
        gif2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(flag){
                    case 0: finalGif.setImageResource(R.drawable.trump);
                            flag = 1;
                            break;
                    case 1: finalGif.setImageResource(R.drawable.prince);
                            flag = 2;
                            break;
                    case 2:finalGif.setImageResource(R.drawable.floss);
                            flag = 3;
                            break;
                    case 3:finalGif.setImageResource(R.drawable.breez);
                          flag = 4;
                        break;
                    case 4:finalGif.setImageResource(R.drawable.orangejustice);
                            flag =0;
                            break;
                    default: break;
                }
            }
        });
        List<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.neon_color1));
        colors.add(getResources().getColor(R.color.blue));
        colors.add(getResources().getColor(R.color.yellow));
        colors.add(getResources().getColor(R.color.neon_color3));
        GlitchTextEffect effect = new GlitchTextEffect(getContext(),colors,"Breeze '19");
        effect.setTextSize(59);
        effect.setFontFile("fonts/Atami-Display.otf");
        effect.setNoise(7);
        effect.start();
        layout.addView(effect,0);
        text1.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Atami-Display.otf"));
        adapter = new LiveScoresAdapter(getContext());
        liveRecyclerView.setAdapter(adapter);
        reference.addChildEventListener(getChildEventListener());
        return view;

    }

}
