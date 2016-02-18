package com.safewaychinay.toolbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.safewaychinay.toolbar.widget.Toolbar;
import com.safewaychinay.toolbar.widget.ToolbarDrawerToggle;

public class MainActivity extends Activity {


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        drawerLayout.setScrimColor(Color.parseColor("#66000000"));
        toolbar.setTitle("营养");
        toolbar.addMenu(R.menu.menu_main);
        toolbar.setOnMenuClickLisnter(new Toolbar.OnMenuClickLisnter() {
            @Override
            public void onMenuClick(int id) {
                Toast.makeText(getApplicationContext(), "adsadad", Toast.LENGTH_SHORT).show();
            }
        });
        ToolbarDrawerToggle toggle = new ToolbarDrawerToggle(drawerLayout, toolbar);
        toggle.syncState();

    }


}
