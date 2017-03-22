package me.blog.korn123.awesomemanager.playground;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.commons.lang3.StringUtils;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DialogUtils;
import me.blog.korn123.utils.SpinnerUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class PlaygroundEditActivity extends AppCompatThemeActivity {

    private Spinner mBranchSpinner;
    private Spinner mPlaygroundKindSpinner;
    private EditText mAddressInput;
    private EditText mPlaygroundNameInput;
    private EditText mEtcInfoInput;
    private int mPlaygroundId;
    private PlaygroundDto playgroundDto;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground_edit_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        mPlaygroundId = getIntent().getIntExtra("playgroundId", -1);
        playgroundDto = PlaygroundDao.selectPlaygroundBy(this, mPlaygroundId);
        initSpinner();
        setPlaygroundData();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initSpinner() {
        mPlaygroundKindSpinner = (Spinner) findViewById(R.id.playgroundKindSpinner);
        mBranchSpinner = (Spinner) findViewById(R.id.branchSpinner);
        SpinnerUtils.initBranchSpinner(this, mBranchSpinner);
        SpinnerUtils.initPlaygroundKindSpinner(this, mPlaygroundKindSpinner);
    }

    private void setPlaygroundData() {
        mAddressInput = (EditText) findViewById(R.id.addressInput);
        mPlaygroundNameInput = (EditText) findViewById(R.id.playgroundNameInput);
        mEtcInfoInput = (EditText) findViewById(R.id.etcInfoInput);
        SpinnerUtils.selectByString(mBranchSpinner, getIntent().getStringExtra("branch"));
        SpinnerUtils.selectByString(mPlaygroundKindSpinner, getIntent().getStringExtra("kind"));
        mEtcInfoInput.setText(playgroundDto.getEtcInfo());
        mAddressInput.setText(playgroundDto.getAddress());
        mPlaygroundNameInput.setText(playgroundDto.getName());

        SpinnerUtils.selectByString(mBranchSpinner, playgroundDto.getBranch());
        SpinnerUtils.selectByString(mPlaygroundKindSpinner, playgroundDto.getKind());
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

    public void editPlayground(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (verifyData()) {
                    PlaygroundDto dto = new PlaygroundDto(
                            mPlaygroundId,
                            -1,
                            String.valueOf(mPlaygroundNameInput.getText()),
                            mBranchSpinner.getSelectedItem().toString(),
                            mPlaygroundKindSpinner.getSelectedItem().toString(),
                            String.valueOf(mAddressInput.getText()),
                            String.valueOf(mEtcInfoInput.getText()),
                            -1
                    );
                    long result = PlaygroundDao.updatePlaygroundBy(this, dto);
                    if (result == -1) {
                        CommonUtils.makeToast(this, "수정실패");
                    } else {
                        CommonUtils.makeToast(this, "수정성공");
                        Intent playgroundView = new Intent(PlaygroundEditActivity.this, PlaygroundViewActivity.class);
                        playgroundView.putExtra("playgroundId", mPlaygroundId);
                        startActivity(playgroundView);
                        finish();
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
            DialogUtils.showAlertDialogOnlyPositive(PlaygroundEditActivity.this, "", "소속지점을 선택하세요.");
        } else if (StringUtils.isEmpty(playgroundKind)) {
            DialogUtils.showAlertDialogOnlyPositive(PlaygroundEditActivity.this, "", "수업장소 형태를 선택하세요.");
        } else if (StringUtils.isEmpty(playgroundName)) {
            DialogUtils.showAlertDialogOnlyPositive(PlaygroundEditActivity.this, "", "수업장소 이름을 입력하세요.");
        } else if (StringUtils.isEmpty(address)) {
            DialogUtils.showAlertDialogOnlyPositive(PlaygroundEditActivity.this, "", "주소를 입력하세요.");
        } else {
            result = true;
        }
        return result;
    }

}
