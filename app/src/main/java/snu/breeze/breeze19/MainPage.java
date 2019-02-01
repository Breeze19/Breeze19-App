package snu.breeze.breeze19;

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

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainPage extends Fragment {
    private final String TAG = MainPage.class.getSimpleName();
    private Integer flag;


    public MainPage(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_page_fragment,container,false);
        LinearLayout layout = view.findViewById(R.id.constraint);
        GifImageView gif = new GifImageView(getContext());
        flag = 0;
        gif = view.findViewById(R.id.gif);
        final GifImageView finalGif = gif;
        gif.setOnClickListener(new View.OnClickListener() {
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
       // GifDrawable gifFromResource = new GifDrawable( getResources(), R.raw.g );
        GlitchTextEffect effect = new GlitchTextEffect(getContext(),colors,"Breeze '19");
        effect.setTextSize(79);
        effect.setNoise(7);
        effect.start();
        layout.addView(effect);
        return view;

    }

}
