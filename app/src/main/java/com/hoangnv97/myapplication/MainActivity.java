package com.hoangnv97.myapplication;

import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.hoangnv97.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private OrientationManager orientationManager;
    private Point p;
    private View webView1;
    private View webView2;
    private ImageView fullScreenButton;
    private boolean isFullScreen;
    private CountDownTimer countDownTimer;
    private final long totalTimeInMillis = 2000;
    private final long intervalInMillis = 1000;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        p = new Point();
        disp.getSize(p);

        countDownTimer = new CountDownTimer(totalTimeInMillis, intervalInMillis) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                hideFullScreenButton();
            }
        };

        orientationManager = new OrientationManager(this, new OrientationManager.OnOrientationChangeListener() {
            @Override
            public void onOrientationChanged(OrientationManager.ScreenOrientation screenOrientation) {
                if (screenOrientation == OrientationManager.ScreenOrientation.NORMAL) {
                    handleWhenScreenOrientationNormal();
                } else if (screenOrientation == OrientationManager.ScreenOrientation.REVERSE) {
                    handleWhenScreenOrientationReverse();
                } else if (screenOrientation == OrientationManager.ScreenOrientation.LEFT) {
                    handleWhenScreenOrientationLeft();
                } else if (screenOrientation == OrientationManager.ScreenOrientation.RIGHT) {
                    handleWhenScreenOrientationRight();
                }
            }

            @Override
            public void onOrientationChangeUnavailable() {
            }
        });

        initView();

        fullScreenButton.setOnClickListener(view -> {
            countDownTimer.cancel();
            countDownTimer.start();
            if (isFullScreen) {
                handleWhenScreenOrientationNormal();
            } else {
                handleWhenScreenOrientationLeft();
            }
        });

        webView2.setOnClickListener(view -> {
            displayFullScreenButton();
        });
    }

    private void initView() {
        webView1 = new View(this);
        RelativeLayout.LayoutParams layoutParamsWebView1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        webView1.setBackgroundResource(R.color.teal_200);
        webView1.setLayoutParams(layoutParamsWebView1);
        binding.container.addView(webView1);

        webView2 = new View(this);
        RelativeLayout.LayoutParams layoutParamsWebView2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                p.x / 16 * 9
        );
        webView2.setBackgroundResource(R.color.purple_200);
        layoutParamsWebView2.topMargin = 50;
        webView2.setLayoutParams(layoutParamsWebView2);
        binding.container.addView(webView2);

        fullScreenButton = new ImageView(this);
        fullScreenButton.setImageResource(R.drawable.full_screen);
        RelativeLayout.LayoutParams lpFullScreenButton = new RelativeLayout.LayoutParams(
                50,
                50
        );
        lpFullScreenButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        lpFullScreenButton.rightMargin = 20;
        lpFullScreenButton.topMargin = p.x / 16 * 9 - 20;
        fullScreenButton.setLayoutParams(lpFullScreenButton);
        binding.container.addView(fullScreenButton);

        fullScreenButton.setVisibility(View.INVISIBLE);
    }

    private void handleWhenScreenOrientationNormal() {
        isFullScreen = false;
        if (webView2.getAnimation() != null) {
            webView2.getAnimation().cancel();
        }
        ViewPropertyAnimator animatorWebView2 = webView2.animate().rotation(0f);
        animatorWebView2.translationX(0);
        animatorWebView2.translationY(0f);
        RelativeLayout.LayoutParams lpWebView2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                p.x / 16 * 9
        );
        lpWebView2.topMargin = 50;
        webView2.setLayoutParams(lpWebView2);

        if (webView1.getAnimation() != null) {
            webView1.getAnimation().cancel();
        }
        ViewPropertyAnimator animatorWebView1 = webView1.animate().rotation(0f);
        animatorWebView1.translationX(0f);
        animatorWebView1.translationY(0f);
        RelativeLayout.LayoutParams lpWebView1 = (RelativeLayout.LayoutParams) webView1.getLayoutParams();
        lpWebView1.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        lpWebView1.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        webView1.setLayoutParams(lpWebView1);

        RelativeLayout.LayoutParams lpFullScreenButton = (RelativeLayout.LayoutParams) fullScreenButton.getLayoutParams();
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
        lpFullScreenButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpFullScreenButton.rightMargin = 20;
        lpFullScreenButton.topMargin = p.x / 16 * 9 - 20;
        fullScreenButton.setLayoutParams(lpFullScreenButton);

        binding.container.removeAllViews();
        binding.container.addView(webView1);
        binding.container.addView(webView2);
        binding.container.addView(fullScreenButton);
    }

    private void handleWhenScreenOrientationReverse() {
        isFullScreen = false;
        if (webView2.getAnimation() != null) {
            webView2.getAnimation().cancel();
        }
        ViewPropertyAnimator animatorWebView2 = webView2.animate().rotation(180f);
        animatorWebView2.translationX(0);
        animatorWebView2.translationY(0f);
        RelativeLayout.LayoutParams lpWebView2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                p.x / 16 * 9
        );
        lpWebView2.topMargin = 50;
        webView2.setLayoutParams(lpWebView2);

        if (webView1.getAnimation() != null) {
            webView1.getAnimation().cancel();
        }
        ViewPropertyAnimator animatorWebView1 = webView1.animate().rotation(180f);
        animatorWebView1.translationX(0f);
        animatorWebView1.translationY(0f);
        RelativeLayout.LayoutParams lpWebView1 = (RelativeLayout.LayoutParams) webView1.getLayoutParams();
        lpWebView1.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        lpWebView1.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        webView1.setLayoutParams(lpWebView1);

        RelativeLayout.LayoutParams lpFullScreenButton = (RelativeLayout.LayoutParams) fullScreenButton.getLayoutParams();
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
        lpFullScreenButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpFullScreenButton.rightMargin = 20;
        lpFullScreenButton.topMargin = p.x / 16 * 9 - 20;
        fullScreenButton.setLayoutParams(lpFullScreenButton);

        binding.container.removeAllViews();
        binding.container.addView(webView1);
        binding.container.addView(webView2);
        binding.container.addView(fullScreenButton);
    }

    private void handleWhenScreenOrientationLeft() {
        isFullScreen = true;
        if (webView2.getAnimation() != null) {
            webView2.getAnimation().cancel();
        }
        ViewPropertyAnimator animatorWebView2 = webView2.animate().rotation(90f);
        animatorWebView2.translationY((float) (p.x - p.x / 16 * 9) / 2);
        RelativeLayout.LayoutParams lpWebView2 = (RelativeLayout.LayoutParams) webView2.getLayoutParams();
        lpWebView2.setMargins(0, 0, 0, 0);
        lpWebView2.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        lpWebView2.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        webView2.setLayoutParams(lpWebView2);
        webView2.animate().rotation(0f);
        animatorWebView2.translationY(0);

        if (webView1.getAnimation() != null) {
            webView1.getAnimation().cancel();
        }
        ViewPropertyAnimator animatorWebView1 = webView1.animate().rotation(90f);
        animatorWebView1.translationX((float) ((-p.y) / 2 - (p.x / 3.5)));
        RelativeLayout.LayoutParams lpWebView1 = (RelativeLayout.LayoutParams) webView1.getLayoutParams();
        webView1.setLayoutParams(lpWebView1);
        webView1.setClickable(true);
        webView1.setFocusable(true);

        RelativeLayout.LayoutParams lpFullScreenButton = (RelativeLayout.LayoutParams) fullScreenButton.getLayoutParams();
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpFullScreenButton.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpFullScreenButton.leftMargin = 20;
        lpFullScreenButton.bottomMargin = 20;
        fullScreenButton.setLayoutParams(lpFullScreenButton);

        binding.container.removeAllViews();
        binding.container.addView(webView2);
        binding.container.addView(webView1);
        binding.container.addView(fullScreenButton);
    }

    private void handleWhenScreenOrientationRight() {
        isFullScreen = true;
        if (webView2.getAnimation() != null) {
            webView2.getAnimation().cancel();
        }
        ViewPropertyAnimator animatorWebView2 = webView2.animate().rotation(-90f);
        animatorWebView2.translationX(0);
        animatorWebView2.translationY((float) (p.x - p.x / 16 * 9) / 2);
        RelativeLayout.LayoutParams lpWebView2 = (RelativeLayout.LayoutParams) webView2.getLayoutParams();
        lpWebView2.setMargins(0, 0, 0, 0);
        lpWebView2.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        lpWebView2.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        webView2.setLayoutParams(lpWebView2);
        webView2.animate().rotation(0f);
        animatorWebView2.translationY(0);

        if (webView1.getAnimation() != null) {
            webView1.getAnimation().cancel();
        }
        ViewPropertyAnimator animatorWebView1 = webView1.animate().rotation(-90f);
        animatorWebView1.translationX((float) ((p.y) / 2 + (p.x / 3.5)));
        RelativeLayout.LayoutParams lpWebView1 = (RelativeLayout.LayoutParams) webView1.getLayoutParams();
        webView1.setLayoutParams(lpWebView1);
        webView1.setClickable(true);
        webView1.setFocusable(true);

        RelativeLayout.LayoutParams lpFullScreenButton = (RelativeLayout.LayoutParams) fullScreenButton.getLayoutParams();
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lpFullScreenButton.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
        lpFullScreenButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpFullScreenButton.rightMargin = 20;
        lpFullScreenButton.topMargin = 20;
        fullScreenButton.setLayoutParams(lpFullScreenButton);

        binding.container.removeAllViews();
        binding.container.addView(webView2);
        binding.container.addView(webView1);
        binding.container.addView(fullScreenButton);
    }

    private void displayFullScreenButton() {
        if (fullScreenButton.getVisibility() == View.VISIBLE) return;
        countDownTimer.cancel();
        countDownTimer.start();
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(500);
        fullScreenButton.setVisibility(View.VISIBLE);
        fullScreenButton.startAnimation(fadeIn);
    }

    private void hideFullScreenButton() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);
        fullScreenButton.setVisibility(View.INVISIBLE);
        fullScreenButton.startAnimation(fadeOut);
    }

    @Override
    protected void onResume() {
        super.onResume();
        orientationManager.enable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        orientationManager.disable();
        countDownTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}