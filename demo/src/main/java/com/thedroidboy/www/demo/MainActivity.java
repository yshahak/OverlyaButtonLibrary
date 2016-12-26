package com.thedroidboy.www.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

import com.thedroidboy.www.overlaybuttonlibrary.OverlayButtonService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra(OverlayButtonService.EXTRA_GRAVITY, Gravity.BOTTOM | Gravity.LEFT);
        intent.putExtra(OverlayButtonService.EXTRA_LAYOUT_ID, R.layout.my_button);
        startService(intent);
        finish();
    }
}
