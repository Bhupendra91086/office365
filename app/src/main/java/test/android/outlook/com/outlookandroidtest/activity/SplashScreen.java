package test.android.outlook.com.outlookandroidtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import test.android.outlook.com.outlookandroidtest.R;
import test.android.outlook.com.outlookandroidtest.splashanimation.SplashScreenListener;


public class SplashScreen extends Activity implements SplashScreenListener {
    private TextView splashText;

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        init();


    }

    private void init() {
        splashText = (TextView) findViewById(R.id.splashTextView);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fead_in);
        splashText.startAnimation(animation);


        doSplashScreenAnimation();
    }

    private void doSplashScreenAnimation() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                Intent intent = new Intent(SplashScreen.this, HomeScreen.class);

                startActivity(intent);

                SplashScreen.this.finish();


            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);

    }

    @Override
    public void onAnimationStart() {

    }

    @Override
    public void onAnimationComplete() {

        goToHomeScreen();
    }


    @Override
    public void onAnimationCancel() {

    }

    @Override
    public void onAnimationPause() {

    }

    @Override
    public void onAnimationResume() {

    }


    private void goToHomeScreen() {

    }

    @Override
    public void onBackPressed() {
    }
}
