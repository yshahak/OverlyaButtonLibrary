package com.thedroidboy.www.overlaybuttonlibrary;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Yaakov Shahak on 27/12/2016.
 */

public class OverlayButton {

    public static final String EXTRA_LAYOUT_ID = "extraLayoutId";
    public static final String EXTRA_GRAVITY = "extraGravity";

    private WeakReference<Context> mContext;
    private int layoutId = R.layout.button;
    private int gravity = Gravity.TOP | Gravity.RIGHT;

    public OverlayButton(Builder builder) {
        this.layoutId = builder.layoutId;
        this.gravity = builder.gravity;
        this.mContext = builder.mContext;
    }

    public static class Builder {

        WeakReference<Context> mContext;
        int layoutId = R.layout.button;
        int gravity = Gravity.TOP | Gravity.RIGHT;

        public Builder(Context context) {
            mContext = new WeakReference<>(context);
        }

        public Builder setLayoutId(int id) {
            layoutId = id;
            return this;
        }

        public Builder setGravity(int grav) {
            gravity = grav;
            return this;
        }

        public Builder setClickListener(View.OnClickListener listener) {
            OverlayButtonService.listenerWeakReference = new WeakReference<>(listener);
            return this;
        }


        public OverlayButton build() {
            return new OverlayButton(this);
        }
    }

    public void show() {
        Context context = mContext.get();
        if (context != null) {
            OverlayButtonService.start(context, layoutId, gravity);
        }
    }


}
