package snu.breeze.breeze19;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainPage extends Fragment {
    private final String TAG = MainPage.class.getSimpleName();
    private Integer flag;
    private TextView text1;


    public MainPage(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_page_fragment,container,false);
        LinearLayout layout = view.findViewById(R.id.layout3);
        LinearLayout layout2 = view.findViewById(R.id.layout2);
        text1 = view.findViewById(R.id.live);
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
        List<Integer> colors2 = new ArrayList<>();
        colors.add(getResources().getColor(R.color.neon_color1));
        colors.add(getResources().getColor(R.color.blue));
        colors.add(getResources().getColor(R.color.yellow));
        colors.add(getResources().getColor(R.color.neon_color3));
        colors2.add(getResources().getColor(R.color.yellow));
        colors2.add(getResources().getColor(R.color.light_black));
        GlitchTextEffect effect = new GlitchTextEffect(getContext(),colors,"Breeze '19");
        effect.setTextSize(59);
        effect.setFontFile("fonts/Atami-Display.otf");
        effect.setNoise(7);
        effect.start();
        GlitchTextEffect effect2 = new GlitchTextEffect(getContext(),colors2,"Break the internet!");
        effect2.setTextSize(17);
        effect2.setFontFile("fonts/Atami-Display.otf");
        effect2.setNoise(4);
        effect2.start();
        layout.addView(effect,0);
       // layout2.addView(effect2,0);
        text1.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Atami-Display.otf"));
        return view;

    }

}
