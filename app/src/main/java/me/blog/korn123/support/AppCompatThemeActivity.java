package me.blog.korn123.support;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import org.apache.commons.lang3.StringUtils;

import me.blog.korn123.awesomemanager.IndexActivity;
import me.blog.korn123.awesomemanager.LoginActivity;
import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.setting.SettingsActivity;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.utils.CommonUtils;

/**
 * Created by CHO HANJOONG on 2016-10-07.
 */

public abstract class AppCompatThemeActivity extends AppCompatActivity {

    @Override
    protected void onRestart() {
        super.onRestart();
//        startActivity(new Intent(this, this.getClass()));
//        overridePendingTransition(0, 0);
//        finish();
//        CommonUtils.makeToast(this, "onRestart");
    }

    public abstract void refresh();

    /**
     * activity가 다시 시작되면 저장된 currentTimeMillis 값을 로딩
     * 저장된값이 없으면 현재 currentTimeMillis 값에 60초를 더한 값을 로딩
     */
    @Override
    protected void onResume() {
        super.onResume();
        long currentMillis = System.currentTimeMillis();
        long pauseMillis = CommonUtils.loadLongPreference(this, "pause_millis") + (1000 * 60);
//        CommonUtils.makeToast(this, "onResume, " + ((currentMillis-CommonUtils.loadLongPreference(this, "pause_millis"))/1000));
        if (currentMillis > pauseMillis) { // activity pause 후 1분이 지난경우
            Intent login = new Intent(this, LoginActivity.class);
            CommonUtils.sessionClear(login);
            startActivity(login);
            finish();
        } else {
            refresh();
        }
    }

    /**
     * activity가 정지되면 currentTimeMillis 값을 저장
     */
    @Override
    protected void onPause() {
        super.onPause();
        CommonUtils.saveLongPreference(this, "pause_millis", System.currentTimeMillis());
//        CommonUtils.makeToast(this, "onPause");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        CommonUtils.makeToast(this, "onBackPressed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Activity activity = this;
//        CommonUtils.makeToast(this, "onDestroy, " + activity.isTaskRoot());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (StringUtils.equals("purple", CommonUtils.loadStringPreference(this, "app_theme", "basic"))) {
//            setTheme(R.style.CustomPurple);
//        }
//        CommonUtils.makeToast(this, "onCreate");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent setting = new Intent(this, SettingsActivity.class);
            startActivity(setting);
        } else if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.index) {
            Intent index = new Intent(this, IndexActivity.class);
            CommonUtils.sessionClear(index);
            startActivity(index);
            finish();
        }
        return true;
    }
}
