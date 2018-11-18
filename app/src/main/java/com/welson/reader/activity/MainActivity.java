package com.welson.reader.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;

import com.welson.reader.R;
import com.welson.reader.view.TriangleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mainToolbar;
    private TabLayout mainTab;
    private TriangleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(mainToolbar);
    }

    private void initView(){
        mainToolbar = findViewById(R.id.main_toolbar);
        //mainTab = findViewById(R.id.main_tab);
        indicator = findViewById(R.id.indicator);
        initTab();
    }

    private void initTab(){
        int mainColor = getResources().getColor(R.color.colorMain);
        int[] colors = {mainColor,mainColor,mainColor,mainColor,mainColor};
        int[][] states = new int[5][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        ColorStateList colorStateList = new ColorStateList(states,colors);
        List<String> titles =  Arrays.asList(getResources().getStringArray(R.array.main_tab_text_arr)) ;
        /*mainTab.addTab(mainTab.newTab().setText(tabText[0]));
        mainTab.addTab(mainTab.newTab().setText(tabText[1]));
        mainTab.addTab(mainTab.newTab().setText(tabText[2]));
        mainTab.setTabTextColors(getResources().getColor(R.color.colorTabBefore),getResources().getColor(R.color.colorTabSelect));
        mainTab.setTabRippleColor(colorStateList);
        mainTab.setSelectedTabIndicator(getResources().getDrawable(R.drawable.indicator));*/
        indicator.setTabArray(titles);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
