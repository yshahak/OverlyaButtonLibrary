package com.thedroidboy.www.overlaybuttonlibrary;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_CLOSE_ON_CLICK;
import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_ENABLE_DRAGGING;
import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_GRAVITY;
import static com.thedroidboy.www.overlaybuttonlibrary.OverlayButton.EXTRA_LAYOUT_ID;

public class PermissionReqActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 512;

    int layoutId, gravityId;
    private boolean enableDragging;
    private boolean closeOnClick;

    public static void startActivityFromService(Context context, int layoutId, int gravityId,boolean enableDragging,  boolean closeOnClick) {
        Intent intent = new Intent(context, PermissionReqActivity.class);
        intent.putExtra(EXTRA_LAYOUT_ID, layoutId);
        intent.putExtra(EXTRA_GRAVITY, gravityId);
        intent.putExtra(EXTRA_ENABLE_DRAGGING, enableDragging);
        intent.putExtra(EXTRA_CLOSE_ON_CLICK, closeOnClick);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_req);

        layoutId = getIntent().getIntExtra(EXTRA_LAYOUT_ID, R.layout.button);
        gravityId = getIntent().getIntExtra(EXTRA_GRAVITY, Gravity.TOP | Gravity.RIGHT);
        enableDragging = getIntent().getBooleanExtra(EXTRA_ENABLE_DRAGGING, false);
        closeOnClick = getIntent().getBooleanExtra(EXTRA_CLOSE_ON_CLICK, true);
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(getApplicationContext())) {
                OverlayButtonService.start(getApplicationContext(), layoutId, gravityId, enableDragging, closeOnClick);
            }
        }
        finish();
    }
}
