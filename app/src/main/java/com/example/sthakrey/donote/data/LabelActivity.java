package com.example.sthakrey.donote.data;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.sthakrey.donote.NotesFragment;
import com.example.sthakrey.donote.R;
import com.example.sthakrey.donote.TodoFragment;

import java.util.ArrayList;
import java.util.List;

public class LabelActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_label);
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//
//    }
////
//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//        NotesFragment nf = new NotesFragment();
//        Bundle b = new Bundle();
//        b.putString("label", getIntent().getStringExtra("label"));
//        nf.setArguments(b);
//        adapter.addFragment(nf, "Notes");
//        TodoFragment tf = new TodoFragment();
//        tf.setArguments(b);
//        adapter.addFragment(tf, "Todo");
//        viewPager.setAdapter(adapter);
//
//    }
//
//
//





}
