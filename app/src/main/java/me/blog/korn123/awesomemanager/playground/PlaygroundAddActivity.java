package me.blog.korn123.awesomemanager.playground;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Text;

import me.blog.korn123.awesomemanager.LoginActivity;
import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DialogUtils;
import me.blog.korn123.utils.SpinnerUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class PlaygroundAddActivity extends AppCompatThemeActivity {

    private Spinner mBranchSpinner;
    private Spinner mPlaygroundKindSpinner;

    private EditText mAddressInput;
    private EditText mPlaygroundNameInput;
    private EditText mEtcInfoInput;
    private TextView mInfoView;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground_add_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        initInputTextView();
        initSpinner();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initInputTextView() {
        mAddressInput = (EditText) findViewById(R.id.addressInput);
        mPlaygroundNameInput = (EditText) findViewById(R.id.playgroundNameInput);
        mEtcInfoInput = (EditText) findViewById(R.id.etcInfoInput);
        mInfoView = (TextView) findViewById(R.id.infoView);
        refreshInfoView();
    }

    private void refreshInfoView() {
        int totalRow = PlaygroundDao.selectPlaygrounds(this, null).size();
        mInfoView.setText("현재까지 등록된 수업장소 " + totalRow + "건");
    }

    private void initSpinner() {
        mBranchSpinner = (Spinner) findViewById(R.id.branchSpinner);
        mPlaygroundKindSpinner = (Spinner) findViewById(R.id.playgroundKindSpinner);
        SpinnerUtils.initBranchSpinner(this, mBranchSpinner);
        SpinnerUtils.initPlaygroundKindSpinner(this, mPlaygroundKindSpinner);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void registerPlayground(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (verifyData()) {
                    PlaygroundDto dto = new PlaygroundDto(
                            -1,
                            Constant.MASTER_DEVICE_MANAGEMENT_ID,
                            String.valueOf(mPlaygroundNameInput.getText()),
                            mBranchSpinner.getSelectedItem().toString(),
                            mPlaygroundKindSpinner.getSelectedItem().toString(),
                            String.valueOf(mAddressInput.getText()),
                            String.valueOf(mEtcInfoInput.getText()),
                            -1
                    );
                    long result = PlaygroundDao.insertPlayground(this, dto);
                    if (result == -1) {
                        CommonUtils.makeToast(this, "등록실패");
                    } else {
                        CommonUtils.makeToast(this, "등록성공");
//                        mAddressInput.setText("");
//                        mPlaygroundNameInput.setText("");
//                        mEtcInfoInput.setText("");
//                        refreshInfoView();
//                        findViewById(R.id.mainScrollView).scrollTo(0, 0);
//                        mPlaygroundNameInput.requestFocus();
                        // activity refresh 방법을 변경 함 2016.10.19 16:03 Hanjoong Cho
                        finish();
                        startActivity(getIntent());
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean verifyData() {
        boolean result = false;
        String branchName = null;
        if (mBranchSpinner.getCount() > 0) {
            branchName = mBranchSpinner.getSelectedItem().toString();
        }
        String playgroundKind = mPlaygroundKindSpinner.getSelectedItem().toString();
        String address = String.valueOf(mAddressInput.getText());
        String playgroundName = String.valueOf(mPlaygroundNameInput.getText());
        String etcInfo = String.valueOf(mEtcInfoInput.getText());
        if (StringUtils.isEmpty(branchName)) {
            DialogUtils.showAlertDialogOnlyPositive(PlaygroundAddActivity.this, "", "소속지점을 선택하세요.");
        } else if (StringUtils.isEmpty(playgroundKind)) {
            DialogUtils.showAlertDialogOnlyPositive(PlaygroundAddActivity.this, "", "수업장소 형태를 선택하세요.");
        } else if (StringUtils.isEmpty(playgroundName)) {
            DialogUtils.showAlertDialogOnlyPositive(PlaygroundAddActivity.this, "", "수업장소 이름을 입력하세요.");
        } else if (StringUtils.isEmpty(address)) {
            DialogUtils.showAlertDialogOnlyPositive(PlaygroundAddActivity.this, "", "주소를 입력하세요.");
        } else {
            result = true;
        }
        return result;
    }

}
