package com.example.hp.major;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CropActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    TextView soil, temp, hum, textView4, desc, desc1;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        soil = findViewById(R.id.croppre);
        temp = findViewById(R.id.tempre);
        hum = findViewById(R.id.humpre);
        desc = findViewById(R.id.desc);
        desc1 = findViewById(R.id.desc1);
        textView4 = findViewById(R.id.suggcrop);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() != null) {
                reff.child("DHT11").child("Soil_Moisture").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userID = String.valueOf(dataSnapshot.child("value").getValue());
                        Float val = Float.parseFloat(userID);
                        if (val >= 0 && val <= 300) {
                            soil.setText("VERY WET");
                        } else if (val >= 301 && val <= 500) {
                            soil.setText("WET");
                        } else if (val >= 501 && val <= 800) {
                            soil.setText("NORMAL");
                        } else if (val >= 801 && val <= 1024) {
                            soil.setText("DRY");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                reff.child("DHT11").child("Temperature").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userID = String.valueOf(dataSnapshot.child("value").getValue());
                        Float val = Float.parseFloat(userID);
                        if (val >= 00.00 && val <= 10.00) {
                            temp.setText("VERY COLD");
                        } else if (val >= 10.01 && val <= 20.00) {
                            temp.setText("COLD");
                        } else if (val >= 20.01 && val <= 30.00) {
                            temp.setText("NORMAL");
                        } else if (val >= 30.01 && val <= 40.00) {
                            temp.setText("WARM");
                        } else if (val >= 40.01 && val <= 50.00) {
                            temp.setText("HOT");
                        } else if (val > 50.01 && val < 60.00) {
                            temp.setText("EXTREMELY HOT");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                reff.child("DHT11").child("Humidity").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userID = String.valueOf(dataSnapshot.child("value").getValue());
                        Float val = Float.parseFloat(userID);
                        if (val <= 40) {
                            hum.setText("LOW");
                        } else if (val >= 41 && val <= 55) {
                            hum.setText("MEDIUM LOW");
                        } else if (val >= 56 && val <= 70) {
                            hum.setText("MEDIUM HIGH");
                        } else if (val >= 71) {
                            hum.setText("HIGH");
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                reff.child("DHT11").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (temp.getText().toString().equals("NORMAL") && soil.getText().toString().equals("WET") && hum.getText().toString().equals("MEDIUM LOW")) {
                            textView4.setText("COMMON WHEAT , BARLEY");
                            desc.setText(R.string.desc1);
                            desc1.setText(R.string.desc2);
                        } else if (temp.getText().toString().equals("COLD") && soil.getText().toString().equals("NORMAL") && hum.getText().toString().equals("MEDIUM LOW")) {
                            textView4.setText("CAULIFLOWER , CABBAGE , BROCCOLI , BEETROOT");
                            desc.setText(R.string.cauliflower);
                            desc1.setText(R.string.beetu);
                        } else if (temp.getText().toString().equals("COLD") && soil.getText().toString().equals("WET") && hum.getText().toString().equals("LOW")) {
                            textView4.setText("FIELD MUSTARD , RADISH");
                            desc.setText(R.string.mustard);
                        } else if (temp.getText().toString().equals("NORMAL") && soil.getText().toString().equals("VERY WET") && hum.getText().toString().equals("LOW")) {
                            textView4.setText("RADDISH");
                            desc.setText(R.string.abc);

                        }
                        else if(temp.getText().toString().equals("NORMAL")&&soil.getText().toString().equals("WET")&&hum.getText().toString().equals("LOW")){
                            textView4.setText("MAIZE");
                            desc.setText(R.string.maize);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}

