package com.thedroidboy.www.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.thedroidboy.www.overlaybuttonlibrary.OverlayButton;

public class DemoService extends Service {

    private OverlayButton overlayButton;
    int count;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "button clicked!", Toast.LENGTH_SHORT).show();
            if (count > 2) { // we will close the button after few clicks
                clickListener = null;
                overlayButton.removeButton();//remove the button
                stopSelf();
            }
            count++;
        }
    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        overlayButton = new OverlayButton.Builder(this)
                .setLayoutId(R.layout.button) //optionally define your own layout for the Button
                .setGravity(Gravity.BOTTOM | Gravity.END) //optionally define your desired Gravity
                .setClickListener(clickListener) //add optional View.OnClickListener
                .setEnableDragging(true)         //whether enable dragging. default is false
                .setRemoveOnClick(false)          //whether remove button after click. default is true
                .build();
        overlayButton.show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
