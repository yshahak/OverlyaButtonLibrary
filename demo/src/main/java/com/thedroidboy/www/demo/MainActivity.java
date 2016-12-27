package com.thedroidboy.www.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.thedroidboy.www.overlaybuttonlibrary.OverlayButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OverlayButton button =  new OverlayButton.Builder(this)
                .setLayoutId(R.layout.my_button)
                .setGravity(Gravity.BOTTOM | Gravity.LEFT)
                .setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       Toast.makeText(view.getContext(), "button clicked!", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        button.show();
        finish();
    }
}
