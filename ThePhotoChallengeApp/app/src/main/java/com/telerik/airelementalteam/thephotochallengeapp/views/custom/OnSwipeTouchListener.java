package com.telerik.airelementalteam.thephotochallengeapp.views.custom;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import butterknife.OnTouch;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector detector;

    public OnSwipeTouchListener(Context context) {
        this.detector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            onTouchDown();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if(Math.abs(diffX) > Math.abs(diffY)){
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
            } catch (Exception e) {e.printStackTrace();}
            return result;
        }
    }
    public void onSwipeLeft() {
        System.out.println("swipe left");
    }

    public void onSwipeRight() {
        System.out.println("swipe right");
    }

    public void onTouchDown() {
        System.out.println("Down");
    }
}
