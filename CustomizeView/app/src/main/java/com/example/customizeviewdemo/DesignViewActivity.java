package com.example.customizeviewdemo;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customizeviewdemo.View.ColorCircleView;

public class DesignViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contain);
        getSupportActionBar().setTitle(getResources().getString(R.string.bt_view));
        ColorCircleView circleView = new ColorCircleView(this, null);
        LinearLayout layout = findViewById(R.id.layout_id);
        layout.addView(circleView);
    }
}
