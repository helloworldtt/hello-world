package com.example.customizeviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toView(View view) {
        startActivity(new Intent(MainActivity.this, DesignViewActivity.class));
    }

    public void toGroup(View view) {
        startActivity(new Intent(MainActivity.this, DesignViewGroupActivity.class));
    }
}
