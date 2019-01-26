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
        colors.add(R.color.colorPrimaryDark);
        colors.add(R.color.colorAccent);
        colors.add(R.color.colorPrimary);
        colors.add(R.color.white);

        GlitchTextEffect effect = new GlitchTextEffect(getContext(),colors,"Breeze '19");
        effect.setTextSize(100);
        effect.setNoise(6);
        effect.start();
        layout.addView(effect);
        return view;

    }

}
