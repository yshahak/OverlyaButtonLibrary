package com.thedroidboy.www.overlaybuttonlibrary;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by yaakov shahak on 03/11/2015.
 */
public abstract class OverlayButtonService extends Service implements View.OnClickListener {

    public static final String EXTRA_LAYOUT_ID = "extraLayoutId";
    public static final String EXTRA_GRAVITY = "extraGravity";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        layoutParams.gravity = intent.getIntExtra(EXTRA_GRAVITY, Gravity.TOP | Gravity.START);
        layoutParams.x = 50;
        layoutParams.y = 50;
        int layoutId = intent.getIntExtra(EXTRA_LAYOUT_ID, R.layout.button);
        @SuppressLint("InflateParams")
        View btn = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
                .inflate(layoutId, null);
        final WindowManager windowManager = ((WindowManager) getSystemService(WINDOW_SERVICE));
        windowManager.addView(btn, layoutParams);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OverlayButtonService.this.onClick(view);
                close(view);
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    public void close(View view){
        try {
            final WindowManager windowManager = ((WindowManager) getSystemService(WINDOW_SERVICE));
            windowManager.removeView(view);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            stopSelf();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
