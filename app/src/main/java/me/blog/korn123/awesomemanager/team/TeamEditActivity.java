package me.blog.korn123.awesomemanager.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.playground.PlaygroundDto;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.SpinnerUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class TeamEditActivity extends AppCompatThemeActivity {

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

    private TeamDto teamDto;

    private int mTeamId;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_edit_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        mTeamId = getIntent().getIntExtra("teamId", -1);
        teamDto = TeamDao.selectTeamBy(this, mTeamId);
        initSpinner();
        setTeamData();
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

    private void setTeamData() {
        mTeamNameInput = (EditText) findViewById(R.id.teamNameInput);
        mEtcInfo = (EditText) findViewById(R.id.etcInfo);
        mTeamNameInput.setText(teamDto.getName());
        mEtcInfo.setText(teamDto.getEtcInfo());

        SpinnerUtils.selectByString(mCoachNameSpinner, teamDto.getCoach());
        SpinnerUtils.selectByString(mSchoolSpinner, teamDto.getSchool());
        SpinnerUtils.selectByString(mGradeSpinner, teamDto.getGrade());
        SpinnerUtils.selectByInt(mGroundSpinner, teamDto.getPlaygroundId());
        SpinnerUtils.selectByString(mEventSpinner, teamDto.getEvent());
        SpinnerUtils.selectByString(mGenderSpinner, teamDto.getGender());
        SpinnerUtils.selectByString(mBranchSpinner, teamDto.getBranch());
        SpinnerUtils.selectByString(mMaximumMemberSpinner, String.valueOf(teamDto.getMaximum()));
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

    public void editTeam(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (verifyData()) {
                    TeamDto dto = new TeamDto(
                            mTeamId,
                            -1,
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
                    long result = TeamDao.updateTeamBy(this, dto);
                    if (result == 0) {
                        CommonUtils.makeToast(this, "수정실패");
                    } else  if (result == 1) {
                        CommonUtils.makeToast(this, "수정성공");
                        Intent teamView = new Intent(TeamEditActivity.this, TeamViewActivity.class);
                        teamView.putExtra("teamId", mTeamId);
                        startActivity(teamView);
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
        if (CommonUtils.verifyInputData(TeamEditActivity.this, TeamEditActivity.this, mTeamNameInput, Constant.VERIFY_MESSAGE_TEAM_NAME)
                && CommonUtils.verifyInputData(TeamEditActivity.this, TeamEditActivity.this, mCoachNameSpinner, Constant.VERIFY_MESSAGE_COACH)
                && CommonUtils.verifyInputData(TeamEditActivity.this, TeamEditActivity.this, mSchoolSpinner, Constant.VERIFY_MESSAGE_COACH)
                && CommonUtils.verifyInputData(TeamEditActivity.this, TeamEditActivity.this, mGradeSpinner, Constant.VERIFY_MESSAGE_GRADE)
                && CommonUtils.verifyInputData(TeamEditActivity.this, TeamEditActivity.this, mGroundSpinner, Constant.VERIFY_MESSAGE_PLAYGROUND)
                && CommonUtils.verifyInputData(TeamEditActivity.this, TeamEditActivity.this, mEventSpinner, Constant.VERIFY_MESSAGE_EVENT)
                && CommonUtils.verifyInputData(TeamEditActivity.this, TeamEditActivity.this, mGenderSpinner, Constant.VERIFY_MESSAGE_GENDER)
                && CommonUtils.verifyInputData(TeamEditActivity.this, TeamEditActivity.this, mBranchSpinner, Constant.VERIFY_MESSAGE_BRANCH)
                && CommonUtils.verifyInputData(TeamEditActivity.this, TeamEditActivity.this, mMaximumMemberSpinner, Constant.VERIFY_MESSAGE_MAXIMUM_MEMBER)
                ) {
            result = true;
        }
        return result;
    }
}
