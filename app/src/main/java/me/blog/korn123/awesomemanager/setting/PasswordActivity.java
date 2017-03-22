package me.blog.korn123.awesomemanager.setting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import me.blog.korn123.awesomemanager.LoginActivity;
import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DialogUtils;

/**
 * Created by CHO HANJOONG on 2016-09-22.
 */
public class PasswordActivity extends AppCompatThemeActivity {

    private EditText mCurrentInput;
    private EditText mNewInput;
    private EditText mVerifyInput;
    private String mCurrentPassword;
    private String mNewPassword;
    private String mVerifyPassword;


    @Override
    public void refresh() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_password_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        initData();
    }

    private void initData() {
        mCurrentInput = (EditText) findViewById(R.id.currentInput);
        mNewInput = (EditText) findViewById(R.id.newInput);
        mVerifyInput = (EditText) findViewById(R.id.verifyInput);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void changePassword(View view) {
        switch (view.getId()) {
            case R.id.submit:
                mCurrentPassword = String.valueOf(mCurrentInput.getText());
                mNewPassword = String.valueOf(mNewInput.getText());
                mVerifyPassword = String.valueOf(mVerifyInput.getText());
                String deviceId = CommonUtils.loadStringPreference(PasswordActivity.this, "login_device_id", "");
                String savedHash = CommonUtils.loadStringPreference(PasswordActivity.this, "login_hash", "");
                String currentHash = CommonUtils.getLoginHash(deviceId, mCurrentPassword);
                // TODO 패스워드 변경 프로세스
                if (!StringUtils.equals(savedHash, currentHash)) {  // 현재비밀번호 일치하지 않음
                    DialogUtils.showAlertDialogOnlyPositive(PasswordActivity.this, "", "현재 설정된 잠금번호가 일치하지 않습니다.");
                } else if (StringUtils.isEmpty(mNewPassword) || StringUtils.isEmpty(mVerifyPassword)) {       // 비밀번호를 입력하지 않음
                    DialogUtils.showAlertDialogOnlyPositive(PasswordActivity.this, "", "잠금번호 또는 검증번호를 입력하지 않았습니다.");
                } else if (!StringUtils.equals(mNewPassword, mVerifyPassword)) {
                    DialogUtils.showAlertDialogOnlyPositive(PasswordActivity.this, "", "설정 하려는 잠금번호 검증에 실패했습니다.");
                } else {
                    CommonUtils.saveStringPreference(this, "login_hash", CommonUtils.getLoginHash(deviceId, mNewPassword));
                    DialogUtils.passwordChangeConfirm(PasswordActivity.this, PasswordActivity.this, "", "잠금번호가 변경되었습니다.");
                }
                break;
            default:
                break;
        }
    }
}
