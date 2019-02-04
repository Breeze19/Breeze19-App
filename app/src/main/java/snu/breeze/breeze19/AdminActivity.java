package snu.breeze.breeze19;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AdminActivity extends AppCompatActivity {
    private final String TAG = AdminActivity.class.getSimpleName();

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;

    private RecyclerView eventsList;
    private LiveEventsAdapter adapter;
    private Button sendNotificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("/data/liveevents/");
        sendNotificationButton = (Button) findViewById(R.id.send_notification);
        eventsList = (RecyclerView) findViewById(R.id.events_list);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        eventsList.setLayoutManager(manager);
        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                LinearLayout layout = new LinearLayout(AdminActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final TextView headingView = new TextView(AdminActivity.this);
                final TextView contentView = new TextView(AdminActivity.this);
                layout.addView(headingView);
                layout.addView(contentView);
                builder.setView(layout);
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String heading = headingView.getText().toString().trim();
                        String content = contentView.getText().toString().trim();
                        if (heading.length() > 0 && content.length() > 0) {
                            OkHttpClient client = new OkHttpClient();
                            JSONObject object = new JSONObject();
                            try {
                                object.put("heading", heading);
                                object.put("content", content);
                                object.put("api_key", getString(R.string.firebase_cf_api_key));
                                Request request = new Request.Builder()
                                        .url(getResources().getString(R.string.firebase_cf_url))
                                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString()))
                                        .build();
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException exception) {
                                        Log.d(TAG, "Failure: " + exception.getMessage());
                                    }

                                    @Override
                                    public void onResponse(final Response response) {
                                        Handler handler = new Handler(getMainLooper());
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(AdminActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                });
                            } catch (JSONException exception) {
                                Log.d(TAG, "Exception: " + exception.getMessage());
                            }
                        }
                    }

                });
                builder.show();
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (childEventListener == null) {
            childEventListener = getChildEventListener();
        }
        reference.addChildEventListener(childEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (childEventListener != null) {
            reference.removeEventListener(childEventListener);
        }
    }

    private LiveEventsData getEventsDataFromSnapshot(DataSnapshot snapshot) {
        LiveEventsData data = snapshot.getValue(LiveEventsData.class);
        data.setKey(snapshot.getKey());
        return data;
    }

    private ChildEventListener getChildEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    if (adapter == null) {
                        adapter = new LiveEventsAdapter(getApplicationContext());
                        eventsList.setAdapter(adapter);
                    }
                    adapter.addData(getEventsDataFromSnapshot(dataSnapshot));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    adapter.update(getEventsDataFromSnapshot(dataSnapshot));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    adapter.delete(getEventsDataFromSnapshot(dataSnapshot));
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Error:- Code: " + databaseError.getCode() + " Message: " + databaseError.getMessage());
            }
        };
    }
}
