package com.thedroidboy.www.demo;

import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.thedroidboy.www.overlaybuttonlibrary.OverlayButtonService;

public class MyService extends OverlayButtonService {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onClick(View view) {
        //here you can choose what to do when user click on the button, for example:
        Toast.makeText(this, "button clicked!", Toast.LENGTH_SHORT).show();
    }
}
