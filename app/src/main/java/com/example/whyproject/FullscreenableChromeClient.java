package com.example.whyproject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;

public class FullscreenableChromeClient extends WebChromeClient {
    private Activity mActivity = null;

    private View mCustomView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private int mOriginalOrientation;

    private FrameLayout mFullscreenContainer;

    private static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    public FullscreenableChromeClient(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mOriginalOrientation = mActivity.getRequestedOrientation();
            FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
            mFullscreenContainer = new FullscreenHolder(mActivity);
            mFullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
            decor.addView(mFullscreenContainer, COVER_SCREEN_PARAMS);
            mCustomView = view;
            setFullscreen(true);
            mCustomViewCallback = callback;
            // TODO : added 20190409 화면이 안나오는대, 전환하면 나오는 경우가 있어서 강제로 전환시켜줌 추가
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }

        super.onShowCustomView(view, callback);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {
        this.onShowCustomView(view, callback);
    }

    @Override
    public void onHideCustomView() {
        if (mCustomView == null) {
            return;
        }

        setFullscreen(false);
        FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
        decor.removeView(mFullscreenContainer);
        mFullscreenContainer = null;
        mCustomView = null;
        mCustomViewCallback.onCustomViewHidden();
        mActivity.setRequestedOrientation(mOriginalOrientation);
    }

    private void setFullscreen(boolean enabled) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (enabled) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
            if (mCustomView != null) {
                mCustomView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
        win.setAttributes(winParams);
    }

    private static class FullscreenHolder extends FrameLayout {
        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ContextCompat.getColor(ctx, android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }
}
