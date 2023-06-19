package com.example.a10120050sensor;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class ViewPager extends AppCompatActivity {

    //NIM : 10120050
    //Nama : Ari Syafri
    //Kelas : IF-2

    ViewPager2 viewPager;
    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        int[] images = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
        String[] headings = {"Hai!", "Aplikasi apa ini ?", "Fitur ?", "Silahkan Masuk"};
        String[] descriptions = {getString(R.string.desc_a),
                getString(R.string.desc_b),
                getString(R.string.desc_c),
                getString(R.string.desc_d)};

        viewPagerItemArrayList = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i], headings[i], descriptions[i]);
            viewPagerItemArrayList.add(viewPagerItem);
        }

        VPAdapter vpAdapter = new VPAdapter(this, viewPagerItemArrayList);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(vpAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(2);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
    }
}
