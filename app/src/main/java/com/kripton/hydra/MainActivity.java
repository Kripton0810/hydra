package com.kripton.hydra;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private FrameLayout frameLayout;
    private final int HOME_FRAGMENT = 1;

    int LOCAION_REQUEST=100;
    private static int CURRENT_FRAGEMENT;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_frist);
        frameLayout = findViewById(R.id.framelayout);
        DrawerLayout layout = findViewById(R.id.layout);
        navigationView = findViewById(R.id.nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,layout,toolbar,R.string.Open,R.string.Close);
        toggle.setDrawerIndicatorEnabled(true);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setCheckedItem(R.id.home);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            //Permission not granted
            requestLoactionPermission();
        }
        else
        {
            //all done
            Toast.makeText(MainActivity.this,"Premission Granted",Toast.LENGTH_SHORT).show();
        }
        setFragement(new homeFramgemnt_2(), HOME_FRAGMENT);


    }

    private void requestLoactionPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
        {
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This permission is needed to find your weather report")
                        .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCAION_REQUEST))
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .create().show();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCAION_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCAION_REQUEST)
        {
            if((grantResults.length>0) && (grantResults[0]==PackageManager.PERMISSION_GRANTED))
            {
                Toast.makeText(MainActivity.this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        DrawerLayout layout = findViewById(R.id.layout);
        if(layout.isDrawerOpen(GravityCompat.START))
        {
            layout.closeDrawer(GravityCompat.START);
        }
        else if(CURRENT_FRAGEMENT== HOME_FRAGMENT)
        {
            super.onBackPressed();
        }else
        {
            setFragement(new homeFramgemnt_2(), HOME_FRAGMENT);
            navigationView.getMenu().getItem(0).setCheckable(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CURRENT_FRAGEMENT =0;
    }





    private void setFragement(Fragment fragement, int fragement_number) {
        if(fragement_number!=CURRENT_FRAGEMENT)
        {
            CURRENT_FRAGEMENT = fragement_number;
            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
            transaction.replace(frameLayout.getId(),fragement);
            transaction.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();
        int WEATHER_FRAGEMENT = 2;
        int AIR_CHECKER = 3;
        int ABOUT_ELEMENT = 4;
        switch (id)
        {
            case R.id.weather:
                weather_fragement fragments = new weather_fragement();
                navigationView.setCheckedItem(R.id.weather);
                setFragement(fragments, WEATHER_FRAGEMENT);
                break;
            case R.id.home:
                navigationView.setCheckedItem(R.id.weather);
                setFragement(new homeFramgemnt_2(), HOME_FRAGMENT);
                break;
            case R.id.check_air_qulity:
                navigationView.setCheckedItem(R.id.check_air_qulity);
                setFragement(new airQulityChecker(), AIR_CHECKER);
                break;
            case R.id.about:
                navigationView.setCheckedItem(R.id.about);
                setFragement(new aboutElements(), ABOUT_ELEMENT);
                break;
            case R.id.exit:
                CURRENT_FRAGEMENT=0;
                super.onBackPressed();
                break;
        }
        DrawerLayout layout = findViewById(R.id.layout);
        layout.closeDrawer(GravityCompat.START);
        return true;
    }

}