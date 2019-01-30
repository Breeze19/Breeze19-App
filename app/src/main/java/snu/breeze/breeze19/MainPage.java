package snu.breeze.breeze19;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends Fragment {
    private final String TAG = MainPage.class.getSimpleName();

    public MainPage(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_page_fragment,container,false);
        ConstraintLayout layout = view.findViewById(R.id.constraint);

        List<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.neon_color1));
        colors.add(getResources().getColor(R.color.blue));
        colors.add(getResources().getColor(R.color.yellow));
        colors.add(getResources().getColor(R.color.neon_color3));


        GlitchTextEffect effect = new GlitchTextEffect(getContext(),colors,"Breeze '19");
        effect.setTextSize(79);
        effect.setNoise(7);
        effect.start();
        layout.addView(effect);
        return view;

    }

}
