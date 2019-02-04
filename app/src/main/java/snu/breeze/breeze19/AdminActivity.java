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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class AdminActivity extends AppCompatActivity implements LiveEventsAdapter.ClickListener, LiveScoresAdapter.ClickListener {
    private final String TAG = AdminActivity.class.getSimpleName();

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;

    private RecyclerView eventsList;
    private LiveEventsAdapter adapter;
    private LiveScoresAdapter adapter1;
    private Button addEvent;

    private String sportName;
    private boolean isSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        database = FirebaseDatabase.getInstance();
        addEvent = (Button) findViewById(R.id.add_event);
        eventsList = (RecyclerView) findViewById(R.id.events_list);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        eventsList.setLayoutManager(manager);
        if (getIntent().getExtras() == null) {
            reference = database.getReference("/data/liveevents/");
            isSport = false;
            addEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    LinearLayout layout = new LinearLayout(AdminActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    final EditText headingView = new EditText(AdminActivity.this);
                    final EditText contentView = new EditText(AdminActivity.this);
                    headingView.setHint("Heading here");
                    contentView.setHint("Content here");
                    layout.addView(headingView);
                    layout.addView(contentView);
                    builder.setView(layout);
                    builder.setTitle("Add event");
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            reference.push().setValue(new LiveEventsData(headingView.getText().toString(), contentView.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AdminActivity.this, "Event added", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                }
                            });
                        }
                    });
                    builder.show();
                }
            });
        } else {
            sportName = getIntent().getExtras().getString(Constants.INTENT_KEY_SPORT_NAME);
            isSport = true;
            reference = database.getReference("/data/livescores/" + sportName);
            addEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    LinearLayout layout = new LinearLayout(AdminActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    final EditText team1View = new EditText(AdminActivity.this);
                    final EditText team2View = new EditText(AdminActivity.this);
                    final EditText score1View = new EditText(AdminActivity.this);
                    final EditText score2View = new EditText(AdminActivity.this);
                    team1View.setHint("Team 1 here");
                    team2View.setHint("Team 2 here");
                    score1View.setHint("Score for team 1 here");
                    score2View.setHint("Score for team 2 here");
                    layout.addView(team1View);
                    layout.addView(score1View);
                    layout.addView(team2View);
                    layout.addView(score2View);
                    builder.setView(layout);
                    builder.setTitle("Add score");
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            reference.push().setValue(new LiveScoreData(team1View.getText().toString(), team2View.getText().toString()
                                    , score1View.getText().toString(), score2View.getText().toString(),sportName)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AdminActivity.this, "Score added", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                }
                            });
                        }
                    });
                    builder.show();
                }
            });
        }
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

    private LiveScoreData getScoresDataFromSnapshot(DataSnapshot snapshot) {
        LiveScoreData data = snapshot.getValue(LiveScoreData.class);
        data.setKey(snapshot.getKey());
        return data;
    }

    private ChildEventListener getChildEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    if (isSport && adapter1 == null) {
                        adapter1 = new LiveScoresAdapter(getApplicationContext(), AdminActivity.this);
                        eventsList.setAdapter(adapter1);
                    } else if (!isSport && adapter == null) {
                        adapter = new LiveEventsAdapter(getApplicationContext(), AdminActivity.this);
                        eventsList.setAdapter(adapter);
                    }

                    if (isSport) {
                        adapter1.add(getScoresDataFromSnapshot(dataSnapshot));
                    } else {
                        adapter.addData(getEventsDataFromSnapshot(dataSnapshot));
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    if (isSport) {
                        adapter1.modify(getScoresDataFromSnapshot(dataSnapshot));
                    } else {
                        adapter.update(getEventsDataFromSnapshot(dataSnapshot));
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (isSport) {
                        adapter1.delete(getScoresDataFromSnapshot(dataSnapshot));
                    } else {
                        adapter.delete(getEventsDataFromSnapshot(dataSnapshot));
                    }
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

    @Override
    public void sendNotification(final LiveEventsData data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        builder.setTitle("Send notification?");
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                String heading = data.getHeading().trim();
                String content = data.getContent().trim();
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
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void edit(final LiveEventsData data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        LinearLayout layout = new LinearLayout(AdminActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText headingView = new EditText(AdminActivity.this);
        final EditText contentView = new EditText(AdminActivity.this);
        headingView.setText(data.getHeading());
        contentView.setText(data.getContent());
        layout.addView(headingView);
        layout.addView(contentView);
        builder.setView(layout);
        builder.setTitle("Edit event");
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (!headingView.getText().toString().equals(data.getHeading()) ||
                        !contentView.getText().toString().equals(data.getContent())) {
                    reference.child(data.getKey()).setValue(new LiveEventsData(headingView.getText().toString(), contentView.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminActivity.this, "Event edited", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void delete(final LiveEventsData data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        builder.setTitle("Confirm deletion");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int which) {
                reference.child(data.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminActivity.this, "Event deleted", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "Deletion cancelled");
                dialog.dismiss();
            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }

    @Override
    public void edit(final LiveScoreData data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        LinearLayout layout = new LinearLayout(AdminActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText team1View = new EditText(AdminActivity.this);
        final EditText team2View = new EditText(AdminActivity.this);
        final EditText score1View = new EditText(AdminActivity.this);
        final EditText score2View = new EditText(AdminActivity.this);
        team1View.setText(data.getTeam1());
        team2View.setText(data.getTeam2());
        score1View.setText(data.getScore1());
        score2View.setText(data.getScore2());
        layout.addView(team1View);
        layout.addView(score1View);
        layout.addView(team2View);
        layout.addView(score2View);
        builder.setView(layout);
        builder.setTitle("Edit score");
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (!team1View.getText().toString().equals(data.getTeam1()) ||
                        !team2View.getText().toString().equals(data.getTeam1()) ||
                        !score1View.getText().toString().equals(data.getScore1()) ||
                        !score2View.getText().toString().equals(data.getScore2())) {
                    reference.child(data.getKey()).setValue(new LiveScoreData(team1View.getText().toString(), team2View.getText().toString(),
                            score1View.getText().toString(), score2View.getText().toString(),sportName)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminActivity.this, "Score edited", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
