package snu.breeze.breeze19;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class flagshipFragment extends Fragment {
    private final String TAG = EventsPage.class.getSimpleName();

    private TabLayout eventsTabLayout;
    private ViewPager eventsViewPager;
    private ViewPagerAdapter eventsViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_flagship,container,false);
        Log.d(TAG,"flagship page made");
        return view;
    }

}
