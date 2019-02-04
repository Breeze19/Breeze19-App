package snu.breeze.breeze19;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

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
        RecyclerView listView= view.findViewById(R.id.list);
        dataModels = new ArrayList<ContactPersonData>();
        dataModels.add(new ContactPersonData("Vignesh Shridhar", "Public Relations", "9677030542",R.drawable.vignesh));
        //dataModels.add(new ContactPersonData("Arjun Soni","Events Manager","9891306018",R.drawable.arjun));
        dataModels.add(new ContactPersonData("Anmol Mahajan","Sports Co-ordinator","9560954626",R.drawable.anmol));
        dataModels.add(new ContactPersonData("Sanjana Gautam","Sports Co-ordinator","9015378953",R.drawable.sanjana));
        dataModels.add(new ContactPersonData("Bhavya Agarwal", "Accommodation", " 9956040085",R.drawable.bhavya));
        dataModels.add(new ContactPersonData("Aishwarya Sathya", "Accommodation", "9990900700",R.drawable.aishwarya));
        dataModels.add(new ContactPersonData("Vedansh Gupta ", "Accommodation", "7523830194",R.drawable.vedansh));
        dataModels.add(new ContactPersonData("Jashwant G ", "Hospitality", "9618655777",R.drawable.jaswant));
        dataModels.add(new ContactPersonData("Malvika Singh","Hospitality","8965956461",R.drawable.malvika));
        dataModels.add(new ContactPersonData("Uroos", "Hospitality", "9582929614",R.drawable.uroos));
        dataModels.add(new ContactPersonData("Balaji", "Transport", "7042049395",R.drawable.balaji));
        dataModels.add(new ContactPersonData("Kushagr Rastogi", "Transport", "9971507820",R.drawable.kushaghra));
        dataModels.add(new ContactPersonData("Sai Teja", "Transport", "9971139455",R.drawable.sai_teja));
        dataModels.add(new ContactPersonData("Ashwin Johnson", "Security", "9599156452",R.drawable.ashwin));
        dataModels.add(new ContactPersonData("Ayush Khatri", "Security", "8130712208",R.drawable.ayush));

        adapter= new CustomAdapter(dataModels,getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(manager);
        listView.setAdapter(adapter);
        return view;
    }

}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<ContactPersonData> dataSet;
    Context mContext;

    public CustomAdapter(ArrayList<ContactPersonData> dataSet,Context context){
        this.dataSet = dataSet;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.contact_row_item,viewGroup,false),mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(dataSet.get(i));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Context context;
        TextView Name;
        TextView Role;
        ImageView callButton;
        CircleImageView photo;

        ViewHolder(View view,Context context){
            super(view);
            this.context = context;
            Name = (TextView) view.findViewById(R.id.name);
            Role = (TextView) view.findViewById(R.id.role);
            callButton = view.findViewById(R.id.call_button);
            photo = view.findViewById(R.id.picture);
        }
        public void bind(final ContactPersonData contactPersonData){
            Name.setText(contactPersonData.getName());
            Role.setText(contactPersonData.getRole());
            photo.setImageResource(contactPersonData.getPhoto());
            callButton.setColorFilter(Color.GREEN);
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Calling", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactPersonData.getPhoneNo()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
