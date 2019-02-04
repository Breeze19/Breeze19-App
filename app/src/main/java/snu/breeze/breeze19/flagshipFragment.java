package snu.breeze.breeze19;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.w3c.dom.Text;

public class flagshipFragment extends Fragment {
    private final String TAG = EventsPage.class.getSimpleName();

   private TextView text1;
   private TextView text2;
   private TextView text3;
   private FirebaseDatabase database;
   private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("/data/pronights/");

    }
    private ChildEventListener getChildEventListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    pronightData data = dataSnapshot.getValue(pronightData.class);
                    data.setKey(dataSnapshot.getKey());
                    //add here
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            /*    if(dataSnapshot.exists()){
                    EventsData data = dataSnapshot.getValue(EventsData.class);
                    data.setKey(dataSnapshot.getKey());
                    adapter.update(data);
                } */
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                /*
                if(dataSnapshot.exists()){
                    EventsData data = dataSnapshot.getValue(EventsData.class);
                    data.setKey(dataSnapshot.getKey());
                    adapter.delete(data);
                } */
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"DatabaseError:- Code: " + databaseError.getCode() + "Message: " + databaseError.getMessage());
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
               View view = inflater.inflate(R.layout.fragment_flagship,container,false);
        text1 = view.findViewById(R.id.newsletter_text);
        text3 = view.findViewById(R.id.newsletter_text2);
        text2 = view.findViewById(R.id.newsletter_text1);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Atami-Display.otf");

        String text = "<font color=#FFFFFF>February</font><font color=#0b0b0b>8th</font>";
        String textt = "<font color=#0b0b0b>February</font><font color=#FFFFFF>8th</font>";
        String texttt = "<font color=#000000>February7</font><font color=#FFFFFF>th</font>";
        text1.setText(Html.fromHtml(text));
        text2.setText(Html.fromHtml(textt));
        text3.setText(Html.fromHtml(texttt));
        text1.setTypeface(custom_font);
        text2.setTypeface(custom_font);
        text3.setTypeface(custom_font);

        Log.d(TAG,"flagship page made");
        return view;
    }




}
