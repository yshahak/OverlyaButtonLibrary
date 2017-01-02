package com.thedroidboy.www.overlaybuttonlibrary;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PermissionReqActivity extends AppCompatActivity {

    private static final String EXTRA_LAYOUT_ID = "EXTRA_LAYOUT_ID";
    private static final String EXTRA_GRAVITY_ID = "EXTRA_GRAVITY_ID";

    private static final int REQUEST_CODE = 512;

    int layoutId, gravityId;

    public static void startActivityFromService(Context context, int layoutId, int gravityId) {
        Intent intent = new Intent(context, PermissionReqActivity.class);
        Log.d("ZAQ", "2. layoutId: " + layoutId);
        intent.putExtra(EXTRA_LAYOUT_ID, layoutId);
        intent.putExtra(EXTRA_GRAVITY_ID, gravityId);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_req);

        layoutId = getIntent().getIntExtra(EXTRA_LAYOUT_ID, R.layout.button);
        gravityId = getIntent().getIntExtra(EXTRA_GRAVITY_ID, Gravity.TOP | Gravity.RIGHT);

        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(getApplicationContext())) {
                OverlayButtonService.start(getApplicationContext(), layoutId, gravityId);
            }
        }
        finish();
    }
}
