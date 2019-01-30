package snu.breeze.breeze19;

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
                    fragment = new MapViewFragment();
                    break;
                case 1:
                    fragment = new MainPage();
                    break;
                case 2:
                    fragment = new EventsPage();
                    break;
            }
        } else {
            switch (position) {
                default:
                    fragment = new EventsFragment();
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
                    title = "Map";
                    break;
                case 1:
                    title = "Main";
                    break;
                case 2:
                    title = "Events";
                    break;
            }
        } else {
            switch (position) {
                default:
                    title = "Events";
            }
        }
        return title;
    }

}
