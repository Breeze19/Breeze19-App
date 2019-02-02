package snu.breeze.breeze19;

import android.graphics.Typeface;
import android.os.Bundle;
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

import org.w3c.dom.Text;

public class flagshipFragment extends Fragment {
    private final String TAG = EventsPage.class.getSimpleName();

   private TextView text1;
   private TextView text2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_flagship,container,false);
        text1 = view.findViewById(R.id.newsletter_text);
        text2 = view.findViewById(R.id.newsletter_text1);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Westmeath.ttf");

        String text = "<font color=#FFFFFF>February</font><font color=#0b0b0b>8th</font>";
        String textt = "<font color=#0b0b0b>February</font><font color=#FFFFFF>8th</font>";
        text1.setText(Html.fromHtml(text));
        text2.setText(Html.fromHtml(textt));
        text1.setTypeface(custom_font);
        text2.setTypeface(custom_font);
        Log.d(TAG,"flagship page made");
        return view;
    }




}
