package com.hoangnv97.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.OrientationEventListener;

public class OrientationManager extends OrientationEventListener {
    private ScreenOrientation screenOrientation;
    private final OnOrientationChangeListener mListener;

    public OrientationManager(Context context, OnOrientationChangeListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (orientation == ORIENTATION_UNKNOWN) {
            Log.d("OrientationManager", "The device orientation can not be determined");
            mListener.onOrientationChangeUnavailable();
            return;
        }

        ScreenOrientation newOrientation = null;
        if (orientation >= 70 && orientation <= 110) {
            newOrientation = ScreenOrientation.RIGHT;
        } else if (orientation >= 160 && orientation <= 200) {
            newOrientation = ScreenOrientation.REVERSE;
        } else if (orientation >= 250 && orientation <= 290) {
            newOrientation = ScreenOrientation.LEFT;
        } else if (orientation >= 340 || orientation < 20) {
            newOrientation = ScreenOrientation.NORMAL;
        }

        if (mListener != null && newOrientation != null && newOrientation != screenOrientation) {
            screenOrientation = newOrientation;
            mListener.onOrientationChanged(newOrientation);
        }
    }

    public interface OnOrientationChangeListener {
        void onOrientationChanged(ScreenOrientation screenOrientation);

        void onOrientationChangeUnavailable();
    }

    public enum ScreenOrientation {
        NORMAL,
        LEFT,
        RIGHT,
        REVERSE
    }
}

