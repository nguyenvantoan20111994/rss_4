package framgia.vn.voanews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import framgia.vn.voanews.R;
import framgia.vn.voanews.constant.Constant;
import framgia.vn.voanews.data.service.NewsRepository;
import io.realm.Realm;

/**
 * Created by Adm on 4/11/2016.
 */
public class SplashActivity extends AppCompatActivity {

    private Realm mRealm;
    private NewsRepository mNewsRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mRealm = Realm.getDefaultInstance();
        mNewsRepository = new NewsRepository(mRealm);
        mNewsRepository.deleteOldData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, Constant.TIME_DELAY);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
