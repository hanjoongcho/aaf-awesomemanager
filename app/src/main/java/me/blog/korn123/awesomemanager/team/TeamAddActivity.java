package me.blog.korn123.awesomemanager.team;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.playground.PlaygroundAddActivity;
import me.blog.korn123.awesomemanager.playground.PlaygroundDao;
import me.blog.korn123.awesomemanager.playground.PlaygroundDto;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DialogUtils;
import me.blog.korn123.utils.SpinnerUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class TeamAddActivity extends AppCompatThemeActivity {

    private Spinner mCoachNameSpinner;
    private Spinner mSchoolSpinner;
    private Spinner mGradeSpinner;
    private Spinner mGroundSpinner;
    private Spinner mEventSpinner;
    private Spinner mGenderSpinner;
    private Spinner mBranchSpinner;
    private Spinner mMaximumMemberSpinner;

    private EditText mTeamNameInput;
    private EditText mEtcInfo;
    private TextView mInfoView;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_add_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        initSpinner();
        initInputTextView();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initSpinner() {
        mCoachNameSpinner = (Spinner) findViewById(R.id.coachSpinner);
        SpinnerUtils.initCoachNameSpinner(this, mCoachNameSpinner);

        mSchoolSpinner = (Spinner) findViewById(R.id.schoolSpinner);
        SpinnerUtils.initSchoolSpinner(this, mSchoolSpinner);

        mGradeSpinner = (Spinner) findViewById(R.id.gradeSpinner);
        SpinnerUtils.initGradeSpinner(this, mGradeSpinner);

        mGroundSpinner = (Spinner) findViewById(R.id.groundSpinner);
        SpinnerUtils.initGroundSpinner(this, mGroundSpinner);

        mEventSpinner = (Spinner) findViewById(R.id.eventSpinner);
        SpinnerUtils.initEventSpinner(this, mEventSpinner);

        mGenderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        SpinnerUtils.initGenderSpinner(this, mGenderSpinner);

        mBranchSpinner = (Spinner) findViewById(R.id.branchSpinner);
        SpinnerUtils.initBranchSpinner(this, mBranchSpinner);

        mMaximumMemberSpinner = (Spinner) findViewById(R.id.maximumMemberSpinner);
        SpinnerUtils.initMaximumMemberSpinner(this, mMaximumMemberSpinner);
    }

    private void initInputTextView() {
        mTeamNameInput = (EditText) findViewById(R.id.teamNameInput);
        mEtcInfo = (EditText) findViewById(R.id.etcInfo);
        mInfoView = (TextView) findViewById(R.id.infoView);
        refreshInfoView();
    }

    private void refreshInfoView() {
        int totalRow = TeamDao.selectTeamsBy(this, null).size();
        mInfoView.setText("현재까지 등록된 팀 " + totalRow + "건");
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

    public void registerTeam(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (verifyData()) {
                    TeamDto dto = new TeamDto(
                            -1,
                            Constant.MASTER_DEVICE_MANAGEMENT_ID,
                            ((PlaygroundDto) mGroundSpinner.getSelectedItem()).getId(),
                            String.valueOf(mTeamNameInput.getText()),
                            mCoachNameSpinner.getSelectedItem().toString(),
                            mSchoolSpinner.getSelectedItem().toString(),
                            mGradeSpinner.getSelectedItem().toString(),
                            mBranchSpinner.getSelectedItem().toString(),
                            mEventSpinner.getSelectedItem().toString(),
                            mGenderSpinner.getSelectedItem().toString(),
                            Integer.valueOf(mMaximumMemberSpinner.getSelectedItem().toString()),
                            String.valueOf(mEtcInfo.getText()),
                            -1
                    );
                    long result = TeamDao.insertTeam(this, dto);
                    if (result == -1) {
                        CommonUtils.makeToast(this, "등록실패");
                    } else {
                        CommonUtils.makeToast(this, "등록성공");
//                        mTeamNameInput.setText("");
//                        mEtcInfo.setText("");
//                        findViewById(R.id.mainScrollView).scrollTo(0, 0);
//                        mTeamNameInput.requestFocus();
//                        refreshInfoView();
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
        if (CommonUtils.verifyInputData(TeamAddActivity.this, TeamAddActivity.this, mTeamNameInput, Constant.VERIFY_MESSAGE_TEAM_NAME)
                && CommonUtils.verifyInputData(TeamAddActivity.this, TeamAddActivity.this, mCoachNameSpinner, Constant.VERIFY_MESSAGE_COACH)
                && CommonUtils.verifyInputData(TeamAddActivity.this, TeamAddActivity.this, mSchoolSpinner, Constant.VERIFY_MESSAGE_COACH)
                && CommonUtils.verifyInputData(TeamAddActivity.this, TeamAddActivity.this, mGradeSpinner, Constant.VERIFY_MESSAGE_GRADE)
                && CommonUtils.verifyInputData(TeamAddActivity.this, TeamAddActivity.this, mGroundSpinner, Constant.VERIFY_MESSAGE_PLAYGROUND)
                && CommonUtils.verifyInputData(TeamAddActivity.this, TeamAddActivity.this, mEventSpinner, Constant.VERIFY_MESSAGE_EVENT)
                && CommonUtils.verifyInputData(TeamAddActivity.this, TeamAddActivity.this, mGenderSpinner, Constant.VERIFY_MESSAGE_GENDER)
                && CommonUtils.verifyInputData(TeamAddActivity.this, TeamAddActivity.this, mBranchSpinner, Constant.VERIFY_MESSAGE_BRANCH)
                && CommonUtils.verifyInputData(TeamAddActivity.this, TeamAddActivity.this, mMaximumMemberSpinner, Constant.VERIFY_MESSAGE_MAXIMUM_MEMBER)
                ) {
            result = true;
        }
        return result;
    }

}
