package com.example.a10120050sensor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    //NIM : 10120050
    //Nama : Ari Syafri
    //Kelas : IF-2

    private BottomNavigationView bottomNavigationView;
    private fragment_info fragment_Info = new fragment_info();
    private fragment_loc fragment_Loc = new fragment_loc();

    private MapsFragment2 fragment_Maps2 = new MapsFragment2();
    private fragment_user fragment_user = new fragment_user();

    private MapsFragment fragment_maps = new MapsFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnItemSelectedListener(this);
        //mengatur masuk lxangusng diawal
        bottomNavigationView.setSelectedItemId(R.id.infoApp);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.infoApp:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment_Info).commit();
                return true;
            case R.id.locApp:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment_maps).commit();
                return true;
            case R.id.userAPP:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment_user).commit();
                return true;
            case R.id.locApp2:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment_Maps2).commit();
                return true;

        }
        return false;
    }

}