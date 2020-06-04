package com.example.hp.major;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetInfoActivity extends AppCompatActivity {
    EditText heightt, weightt;
    Button save;
    private DatabaseReference reff;
    private FirebaseAuth auth;
    ProgressBar progressBar;
    User user;
    public static float mSeries = 0f;
    public static float mSeries1 = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        heightt = findViewById(R.id.heightt);
        weightt = findViewById(R.id.weightt);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        reff = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();


    }

    public void a(View view) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user = auth.getCurrentUser();
        String ax = user.getUid();
        reff.child("Users").child(ax).child("name").setValue(heightt.getText().toString());
        reff.child("Users").child(ax).child("number").setValue(weightt.getText().toString());
        reff.child("Users").child(ax).child("number").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSeries = Float.parseFloat(String.valueOf(dataSnapshot.getValue()));
                Log.d("mSeries", (String.valueOf(mSeries)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        reff.child("Users").child(ax).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSeries = Float.parseFloat(String.valueOf(dataSnapshot.getValue()));
                Log.d("mSeries", (String.valueOf(mSeries)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Toast.makeText(GetInfoActivity.this, "data inserted sucessfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DashActivity.class);
        startActivity(intent);
    }
}
