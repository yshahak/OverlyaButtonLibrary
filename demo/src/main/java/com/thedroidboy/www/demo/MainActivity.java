package com.thedroidboy.www.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.thedroidboy.www.overlaybuttonlibrary.OverlayButton;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "button clicked!", Toast.LENGTH_SHORT).show();
            clickListener = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new OverlayButton.Builder(this)
                .setLayoutId(R.layout.my_button) //optionally define your own layout for the Button
                .setGravity(Gravity.BOTTOM | Gravity.END) //optionally define your desired Gravity
                .setClickListener(clickListener)
                .build()
                .show();
        finish();
    }
}
