package com.thedroidboy.www.overlaybuttonlibrary;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_GRAVITY;
import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_LAYOUT_ID;

/**
 * Created by yaakov shahak on 03/11/2015.
 */
public class OverlayButtonService extends Service {

    public static WeakReference<View.OnClickListener> listenerWeakReference;

    public static void start(Context context, int layoutId, int gravity) {
        Intent intent = new Intent(context, OverlayButtonService.class);
        intent.putExtra(EXTRA_LAYOUT_ID, layoutId);
        intent.putExtra(EXTRA_GRAVITY, gravity);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int layoutId = intent.getIntExtra(EXTRA_LAYOUT_ID, R.layout.button);
        int gravityId = intent.getIntExtra(EXTRA_GRAVITY, Gravity.TOP | Gravity.START);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getApplicationContext())) {
            PermissionReqActivity.startActivityFromService(getApplicationContext(), layoutId, gravityId);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        layoutParams.gravity = gravityId;
        layoutParams.x = 50;
        layoutParams.y = 50;

        @SuppressLint("InflateParams")
        View btn = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(layoutId, null);
        final WindowManager windowManager = ((WindowManager) getSystemService(WINDOW_SERVICE));


        windowManager.addView(btn, layoutParams);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerWeakReference.get() != null) {
                    listenerWeakReference.get().onClick(view);
                }
                listenerWeakReference = null;
                close(view);
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    public void close(View view) {
        try {
            final WindowManager windowManager = ((WindowManager) getSystemService(WINDOW_SERVICE));
            windowManager.removeView(view);
        } catch (Exception e) {
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
