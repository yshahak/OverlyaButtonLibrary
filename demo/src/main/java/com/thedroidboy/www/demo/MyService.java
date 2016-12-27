package com.thedroidboy.www.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    @Override
//    public void onClick(View view) {
//        //here you can choose what to do when user click on the button, for example:
//        Toast.makeText(this, "button clicked!", Toast.LENGTH_SHORT).show();
//    }
}
