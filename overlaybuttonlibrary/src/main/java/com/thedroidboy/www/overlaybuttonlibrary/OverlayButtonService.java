package com.thedroidboy.www.overlaybuttonlibrary;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_CLOSE;
import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_CLOSE_ON_CLICK;
import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_ENABLE_DRAGGING;
import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_GRAVITY;
import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_LAYOUT_ID;

/**
 * Created by yaakov shahak on 03/11/2015.
 */
public class OverlayButtonService extends Service implements View.OnDragListener, View.OnLongClickListener {

    public static WeakReference<View.OnClickListener> listenerWeakReference;
    private WindowManager.LayoutParams layoutParams;
    private WindowManager windowManager;
    private View btn;

    public static void start(Context context, int layoutId, int gravity, boolean enableDragging, boolean closeOnClick) {
        Intent intent = new Intent(context, OverlayButtonService.class);
        intent.putExtra(EXTRA_LAYOUT_ID, layoutId);
        intent.putExtra(EXTRA_GRAVITY, gravity);
        intent.putExtra(EXTRA_ENABLE_DRAGGING, enableDragging);
        intent.putExtra(EXTRA_CLOSE_ON_CLICK, closeOnClick);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra(EXTRA_CLOSE, false)){
            close(btn);
        } else {
            int layoutId = intent.getIntExtra(EXTRA_LAYOUT_ID, R.layout.button);
            int gravityId = intent.getIntExtra(EXTRA_GRAVITY, Gravity.TOP | Gravity.START);
            boolean enableDragging = intent.getBooleanExtra(EXTRA_ENABLE_DRAGGING, false);
            final boolean closeOnClick = intent.getBooleanExtra(EXTRA_CLOSE_ON_CLICK, true);
            //We have to check permission for API > 22
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getApplicationContext())) {
                PermissionReqActivity.startActivityFromService(getApplicationContext(), layoutId, gravityId, enableDragging, closeOnClick);
                stopSelf();
                return super.onStartCommand(intent, flags, startId);
            }
            layoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PRIORITY_PHONE, //this will enable showing the button in the front
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //we want to catch touches outside
                    PixelFormat.TRANSLUCENT);
            layoutParams.gravity = gravityId;
            //let's add some gravity
            layoutParams.x = 50;
            layoutParams.y = 50;

            btn = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(layoutId, null);
            windowManager = ((WindowManager) getSystemService(WINDOW_SERVICE));
            windowManager.addView(btn, layoutParams);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listenerWeakReference.get() != null) {
                        listenerWeakReference.get().onClick(view);
                    }
                    if (closeOnClick) {
                        close(view);
                    }
                }
            });
            if (enableDragging) {
                btn.setOnLongClickListener(this);
            }
        }
        return START_NOT_STICKY;
    }

    public void close(View view) {
        listenerWeakReference = null;
        try {
            if (view != null) {
                final WindowManager windowManager = ((WindowManager) getSystemService(WINDOW_SERVICE));
                windowManager.removeView(view);
            }
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


    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()){
            case DragEvent.ACTION_DROP:
                View btn = (View) view.getTag();
                layoutParams.x = view.getWidth () - (int) dragEvent.getX() + btn.getWidth() / 2;
                layoutParams.y = view.getHeight () - (int) dragEvent.getY() - btn.getHeight() / 2;
                windowManager.updateViewLayout(btn, layoutParams);
                btn.setVisibility(View.VISIBLE);
                view.setTag(null);
                windowManager.removeView(view);
                break;
            case DragEvent.ACTION_DRAG_ENDED:

                break;
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onLongClick(View view) {
        @SuppressLint("InflateParams")
        View dragLayout = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drag_latout, null);
        windowManager.addView(dragLayout, layoutParams);
        dragLayout.setOnDragListener(this);
        view.setVisibility(View.INVISIBLE);
        ClipData dragData = ClipData.newPlainText("dragging", "now");
        dragLayout.setTag(view);
        view.startDrag(dragData,
                new View.DragShadowBuilder(view),
                null,
                0);
        return false;
    }
}
