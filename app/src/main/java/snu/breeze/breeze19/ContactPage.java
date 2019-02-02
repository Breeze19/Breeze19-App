package snu.breeze.breeze19;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactPage extends Fragment {
    private final String TAG = MainPage.class.getSimpleName();
    ArrayList<ContactPersonData> dataModels;
    private static CustomAdapter adapter;



    public ContactPage(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.contact_page_fragment,container,false);
        ConstraintLayout layout = view.findViewById(R.id.constraint);
        ListView listView=(ListView) view.findViewById(R.id.list);
        dataModels = new ArrayList<ContactPersonData>();
        dataModels.add(new ContactPersonData("Vignesh Shridhar", "Public Relations", "9677030542",R.drawable.vignesh));
        dataModels.add(new ContactPersonData("Bhavya Agarwal", "Accommodation Lead", " 9956040085",R.drawable.ic_arrow));
        dataModels.add(new ContactPersonData("Aishwarya Sathya", "Accommodation Lead", "9990900700",R.drawable.ic_arrow));
        dataModels.add(new ContactPersonData("Vedansh Gupta ", "Accommodation Lead", "7523830194",R.drawable.ic_arrow));
        dataModels.add(new ContactPersonData("Jashwant G ", "Hospitality", "9618655777",R.drawable.ic_arrow));
        dataModels.add(new ContactPersonData("Malvika Singh","Hospitality","8965956461",R.drawable.ic_arrow));
        dataModels.add(new ContactPersonData("Uroos", "Hospitality", "9582929614",R.drawable.ic_arrow));
        dataModels.add(new ContactPersonData("Balaji", "Transport", "7042049395",R.drawable.balaji));
        dataModels.add(new ContactPersonData("Kushagr Rastogi", "Transport", "9971507820",R.drawable.kushaghra));
        dataModels.add(new ContactPersonData("Sai Teja", "Transport", "9971139455",R.drawable.sai_teja));
        dataModels.add(new ContactPersonData("Ashwin Johnson", "Security", "9599156452",R.drawable.ic_arrow));
        dataModels.add(new ContactPersonData("Ayush Khatri", "Security", "8130712208",R.drawable.ayush));

        adapter= new CustomAdapter(dataModels,getContext());
        listView.setAdapter(adapter);
        return view;
    }

}

class CustomAdapter extends ArrayAdapter<ContactPersonData> implements View.OnClickListener{

    private ArrayList<ContactPersonData> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView Name;
        TextView Role;
        ImageView callButton;
        CircleImageView photo;
    }

    public CustomAdapter(ArrayList<ContactPersonData> data, Context context) {
        super(context, R.layout.contact_row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ContactPersonData dataModel=(ContactPersonData) object;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ContactPersonData dataModel = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contact_row_item, parent, false);
            viewHolder.Name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.Role = (TextView) convertView.findViewById(R.id.role);
            viewHolder.callButton = convertView.findViewById(R.id.call_button);
            viewHolder.photo = convertView.findViewById(R.id.picture);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.Name.setText(dataModel.getName());
        viewHolder.Role.setText(dataModel.getRole());
        viewHolder.photo.setImageResource(dataModel.getPhoto());
        viewHolder.callButton.setColorFilter(Color.GREEN);
        viewHolder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "Calling", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dataModel.getPhoneNo()));
                getContext().startActivity(intent);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
