package com.example.hp.major;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    TextView texttemp, texthum, textsoil, txt_email;
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth.AuthStateListener authListener;
    ViewFlipper v_flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        txt_email = header.findViewById(R.id.emailadd);
        int images[] = {R.drawable.slidea, R.drawable.slideb, R.drawable.slidec, R.drawable.slided};
        v_flipper = findViewById(R.id.v_flipper);
        for (int image : images) {
            flipperimages(image);
        }
        texttemp = findViewById(R.id.temp);
        texthum = findViewById(R.id.hum);
        textsoil = findViewById(R.id.soil);
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(DashActivity.this, WelcomeActivity.class));
                    finish();
                }
            }
        };


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (auth.getCurrentUser() != null) {
            FirebaseUser firebaseUser = auth.getCurrentUser();
            String a = firebaseUser.getEmail();
            txt_email.setText("" + a);
            reff.child("DHT11").child("Humidity").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = String.valueOf(dataSnapshot.child("value").getValue());
                    texthum.setText(value+"%");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            reff.child("DHT11").child("Temperature").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userID = String.valueOf(dataSnapshot.child("value").getValue());

                    texttemp.setText(userID+"°C");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            reff.child("DHT11").child("Soil_Moisture").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userID = String.valueOf(dataSnapshot.child("value").getValue());
                    if (userID.equals("1024")) {
                        textsoil.setText("DRY");
                        textsoil.setTextColor(Color.RED);


                    } else {
                        textsoil.setText("WET");
                        textsoil.setTextColor(Color.BLUE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


    }

    public void flipperimages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://github.com/hkush8289/Smart-Kisan");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_send) {
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void youtube(View view) {
        Intent intent = new Intent(this, YoutubeActivity.class);
        startActivity(intent);
    }

    public void news(View view) {
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
    }

    public void temperature(View view) {
        Intent intent = new Intent(this, TemperatureActivity.class);
        startActivity(intent);
    }

    public void humidity(View view) {
        Intent intent = new Intent(this, HumidityActivity.class);
        startActivity(intent);
    }

    public void moisture(View view) {
        Intent intent = new Intent(this, MoistureActivity.class);
        startActivity(intent);
    }

    public void shop(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    public void cloud(View view) {
        Intent intent = new Intent(this, CloudActivity.class);
        startActivity(intent);
    }

    public void setting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
    public void crop(View view) {
        Intent intent = new Intent(this, CropActivity.class);
        startActivity(intent);
    }
}
