package twi.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {
    private boolean isBackButtonPressed;
    private static final int CONT_SPLASH_DURATION = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashScreenActivity.this.finish();
                if (!isBackButtonPressed) {
                    Intent objLoginintent = new Intent(SplashScreenActivity.this,
                            MainActivity.class);
                    SplashScreenActivity.this.startActivity(objLoginintent);
                }
            }
        }, CONT_SPLASH_DURATION);

    }
}
