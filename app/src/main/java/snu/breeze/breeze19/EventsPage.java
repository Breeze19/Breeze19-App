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

import com.google.firebase.database.FirebaseDatabase;

public class EventsPage extends Fragment {
    private final String TAG = EventsPage.class.getSimpleName();

    private TabLayout eventsTabLayout;
    private ViewPager eventsViewPager;
    private ViewPagerAdapter eventsViewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.events_page,container,false);
            Log.d(TAG,"Events page made");
             eventsTabLayout = (TabLayout) view.findViewById(R.id.events_tab_layout);
             eventsViewPager = (ViewPager) view.findViewById(R.id.events_view_pager);
             eventsTabLayout.setupWithViewPager(eventsViewPager);
        eventsViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),false);

        int limit = (eventsViewPagerAdapter.getCount() > 1 ? eventsViewPagerAdapter.getCount() - 1 : 1);
        eventsViewPager.setAdapter(eventsViewPagerAdapter);
        eventsViewPager.setOffscreenPageLimit(limit);
            return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
