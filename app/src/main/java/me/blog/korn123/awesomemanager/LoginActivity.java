package me.blog.korn123.awesomemanager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

import me.blog.korn123.awesomemanager.help.ScreenSlidePagerActivity;
import me.blog.korn123.awesomemanager.management.ManagementDao;
import me.blog.korn123.awesomemanager.setting.SettingsActivity;
import me.blog.korn123.awesomemanager.team.BasicInformationDao;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DialogUtils;
import me.blog.korn123.utils.PermissionUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class LoginActivity extends AppCompatThemeActivity {

    private TextView mDeviceIdView;
    private EditText mPasswordInputView;
    private Spinner mLoginTypeSpinner;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (StringUtils.equals("purple", CommonUtils.loadStringPreference(this, "app_theme", "basic"))) {
//            setTheme(R.style.CustomPurple);
//        }
        setContentView(R.layout.login_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PermissionUtils.confirmPermission(LoginActivity.this, LoginActivity.this);
        mPasswordInputView = (EditText) findViewById(R.id.passwordInput);
        mDeviceIdView = (TextView) findViewById(R.id.deviceId);
//        mPasswordInputView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                AAFLogger.info("LoginActivity-onCreate INFO: " + event.getAction() + ", " + keyCode, getClass());
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    verifyPassword();
//                }
//                return false;
//            }
//        });
        initSpinner();
        registerPassword();
        initDeviceId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.help) {
            Intent screenSlidePager = new Intent(this, ScreenSlidePagerActivity.class);
            startActivity(screenSlidePager);
        }
        return true;
    }

    public void setGlobalFont(ViewGroup root){
        for(int i=0; i< root.getChildCount(); i++){
            View child = root.getChildAt(i);
            if(child instanceof TextView)((TextView)child).setTypeface(Typeface.DEFAULT);
            else if(child instanceof ViewGroup) setGlobalFont((ViewGroup)child);
        }
    }

    private void initSpinner() {
        mLoginTypeSpinner = (Spinner) findViewById(R.id.loginTypeSpinner);
        ArrayAdapter loginTypeAdapter = ArrayAdapter.createFromResource(this, R.array.login_type_arrays, R.layout.spinner_login_type_item);
        loginTypeAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        mLoginTypeSpinner.setAdapter(loginTypeAdapter);
        mLoginTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getAdapter().getItemId(position) > 0 ) {
                    CommonUtils.makeToast(LoginActivity.this, "현재는 교사 로그인만 가능합니다.");
                    mLoginTypeSpinner.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initDeviceId() {
        String deviceId = CommonUtils.getDeviceId(LoginActivity.this);
        mDeviceIdView.setText(deviceId);
    }

    private void registerPassword() {
        String password = CommonUtils.loadStringPreference(LoginActivity.this, "login_hash", "");
        if (StringUtils.isEmpty(password)) {
            Button submit = (Button) findViewById(R.id.submit);
            submit.setText("어플리케이션 잠금번호 설정하기");
            mPasswordInputView.setHint("잠금번호를 설정하세요.");
        }
    }

    public void initInBasicInformation(String deviceId) {

        // 디바이스 아이디저장
        // 1번로우에는 항상 현재사용중인 디바이스 아이디가 저장되어야 함
        // 그외의 로우에는 데이터통합시 데이터를 추출한 디바이스의 아이디가 저장됨
        ManagementDao.insertManagement(this, deviceId);

        File workingDirectory = new File(Constant.WORKING_DIRECTORY);
        if (!workingDirectory.exists()) workingDirectory.mkdirs();
        List<String> list = CommonUtils.getStringList(LoginActivity.this, R.array.event_arrays);
        for (String event : list) {
            BasicInformationDao.insertCategoryValue(LoginActivity.this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_EVENT, event);
        }

        list = CommonUtils.getStringList(LoginActivity.this, R.array.school_grade_arrays);
        for (String grade : list) {
            BasicInformationDao.insertCategoryValue(LoginActivity.this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_GRADE, grade);
        }


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (PermissionUtils.checkPermission(LoginActivity.this)) {
                    verifyPassword();
                } else {
                    DialogUtils.showAlertDialogOnlyPositive(LoginActivity.this, LoginActivity.this, "", "어플리케이션 실해을 위한 권한이 허용되지 않았습니다.");
                }
                break;
            default:
                break;
        }
    }

    private void verifyPassword() {
//        AAFLogger.info("LoginActivity-verifyPassword INFO: " + CommonUtils.loadStringPreference(LoginActivity.this, "login_hash", ""), getClass());
//        AAFLogger.info("LoginActivity-verifyPassword INFO: " + CommonUtils.loadStringPreference(LoginActivity.this, "login_device_id", ""), getClass());
        String deviceId = String.valueOf(mDeviceIdView.getText());
        String inputPassword = String.valueOf(mPasswordInputView.getText());
//        AAFLogger.info("LoginActivity-verifyPassword INFO: " + CommonUtils.loadStringPreference(LoginActivity.this, "login_hash", ""), getClass());
        // 잠금번호 입력하지 않은경우
        if (StringUtils.isEmpty(inputPassword)) {
            DialogUtils.showAlertDialogOnlyPositive(LoginActivity.this, "", "잠금번호를 입력하지 않았습니다.");
         // 잠금번호 설정되지 않은경우(최초 로그인 시도)
        } else if (StringUtils.isEmpty(CommonUtils.loadStringPreference(LoginActivity.this, "login_hash", ""))) {
            String loginHash = CommonUtils.getLoginHash(deviceId, inputPassword);
            CommonUtils.saveStringPreference(this, "login_device_id", deviceId);
            CommonUtils.saveStringPreference(this, "login_hash", loginHash);
            initInBasicInformation(deviceId);
            Intent index = new Intent(this, IndexActivity.class);
            startActivity(index);
            finish();
        // 설정된 잠금번호 일치하는 경우
        } else if (StringUtils.equals(CommonUtils.getLoginHash(deviceId, inputPassword) , CommonUtils.loadStringPreference(LoginActivity.this, "login_hash", ""))) {
            Intent index = new Intent(this, IndexActivity.class);
            startActivity(index);
            finish();
        // 설정된 잠금번호 일치하는 않는 경우
        } else {
            DialogUtils.showAlertDialogOnlyPositive(LoginActivity.this, "", "잠금번호가 일치하지 않습니다.");
        }
    }

}
