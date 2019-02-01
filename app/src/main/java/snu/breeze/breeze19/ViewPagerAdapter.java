package snu.breeze.breeze19;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final String TAG = ViewPagerAdapter.class.getSimpleName();

    private boolean isMain;

    public ViewPagerAdapter(FragmentManager fragmentManager, boolean isMain) {
        super(fragmentManager);
        this.isMain = isMain;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (isMain) {
            switch (position) {
                case 0:
                    fragment = new MainPage();
                    break;
                case 1:
                    fragment = new MapViewFragment();
                    break;
                case 2:
                    fragment = new EventsPage();
                    break;
                case 3:
                    fragment = new ContactPage();
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    Bundle bundle = new Bundle();
                    String myMessage = "flagship";
                    bundle.putString("category", myMessage );
                    fragment = new flagshipFragment();
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    bundle = new Bundle();
                    myMessage = "sports";
                    bundle.putString("category", myMessage );
                    fragment = new EventsFragment();
                    fragment.setArguments(bundle);
                    break;
                case 2:
                        bundle = new Bundle();
                        myMessage = "cultural";
                        bundle.putString("category", myMessage );
                    fragment = new EventsFragment();
                        fragment.setArguments(bundle);
                        break;
                 case 3:
                        bundle = new Bundle();
                        myMessage = "technical";
                        bundle.putString("category", myMessage );
                     fragment = new EventsFragment();
                        fragment.setArguments(bundle);
                        break;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Constants.BOTTOM_TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        if (isMain) {
            switch (position) {
                case 0:
                    title = "Home";
                    break;
                case 1:
                    title = "Map";
                    break;
                case 2:
                    title = "Events";
                    break;
                case 3:
                    title = "Main";
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    title = "Flagship";
                    break;
                case 1:
                    title = "Sports";
                    break;
                case 2:
                    title = "Cultural";
                    break;
                case 3:
                    title = "Technical";
                    break;
            }
        }
        return title;
    }

}
