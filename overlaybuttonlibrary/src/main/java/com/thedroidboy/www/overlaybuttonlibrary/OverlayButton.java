package com.thedroidboy.www.overlaybuttonlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Yaakov Shahak on 27/12/2016.
 */

/**
 * class for displaying a button on top of any other UI component in the device
 */

@SuppressWarnings("WeakerAccess")
@SuppressLint("RtlHardcoded")
public class OverlayButton {

    static final String EXTRA_LAYOUT_ID = "extraLayoutId";
    static final String EXTRA_GRAVITY = "extraGravity";
    static final String EXTRA_ENABLE_DRAGGING = "extraDragging";
    static final String EXTRA_CLOSE_ON_CLICK = "extraCloseOnClick";
    static final String EXTRA_CLOSE = "extraClose";

    private WeakReference<Context> mContext;
    private int layoutId = R.layout.button;
    private int gravity = Gravity.TOP | Gravity.RIGHT;
    private boolean enableDragging;
    private boolean closeOnClick;

    /**
     * constructor
     * @param builder to extract the data inserted by client
     */
    public OverlayButton(Builder builder) {
        this.layoutId = builder.layoutId;
        this.gravity = builder.gravity;
        this.enableDragging = builder.enableDragging;
        this.closeOnClick = builder.closeOnClick;
        this.mContext = builder.mContext;
    }

    /**
     * Builder to chain client calls
     */
    public static class Builder {

        WeakReference<Context> mContext;
        int layoutId = R.layout.button;
        int gravity = Gravity.TOP | Gravity.RIGHT;
        boolean enableDragging;
        boolean closeOnClick;

        public Builder(Context context) {
            mContext = new WeakReference<>(context);
        }

        /**
         * enable client supply layout to use as a button
         * @param layoutId the layoutId, like R.layout.my_button
         * @return builder
         */
        @SuppressWarnings("unused")
        public Builder setLayoutId(@LayoutRes int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        /**
         * enable client determine position for the button
         * @param gravity desire gravity can be {@link Gravity#LEFT}, {@link Gravity#RIGHT}, {@link Gravity#TOP}, {@link Gravity#BOTTOM}
         * @return builder
         */
        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * setting the OnClickListener to use when user clicked the button
         * @param listener any {@link android.view.View.OnClickListener}
         * @return builder
         */
        public Builder setClickListener(View.OnClickListener listener) {
            OverlayButtonService.listenerWeakReference = new WeakReference<>(listener);
            return this;
        }

        /**
         * set if button need to remove after user click
         * @param remove <b>true</b> if button need to be removed, <b>false</b> otherwise
         * @return builder
         */
        public Builder setRemoveOnClick(boolean remove){
            this.closeOnClick = remove;
            return this;
        }

        /**
         * enable dragging the button by user
         * @param enable <b>true</b> if enabled
         * @return builder
         */
        public Builder setEnableDragging(boolean enable){
            this.enableDragging = enable;
            return this;
        }

        /**
         * build the {@link OverlayButton} instance
         * @return OverlayButton instance
         */
        public OverlayButton build() {
            return new OverlayButton(this);
        }
    }

    /**
     * display the button on the screen
     */
    public void show() {
        Context context = mContext.get();
        if (context != null) {
            OverlayButtonService.start(context, layoutId, gravity, enableDragging, closeOnClick);
        }
    }

    /**
     * remove the display button from UI
     */
    public void removeButton(){
        Context context = mContext.get();
        if (context != null) {
            Intent intent = new Intent(context, OverlayButtonService.class);
            intent.putExtra(EXTRA_CLOSE, true);
            context.startService(intent);
        }
    }
}
