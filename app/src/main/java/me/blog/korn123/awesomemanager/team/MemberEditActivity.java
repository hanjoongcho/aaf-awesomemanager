package me.blog.korn123.awesomemanager.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.ContactsViewActivity;
import me.blog.korn123.utils.SpinnerUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class MemberEditActivity extends AppCompatThemeActivity {

    private int mTeamId;
    private int mMemberId;
    private TextView mCoachName;
    private TextView mEvent;
    private TextView mTeamGender;
    private TextView mMaximumMember;
    private TextView mTeamInfoTitle;

    private EditText mMemberNameInput;
    private EditText mAlarmCellphoneInput;
    private TextView mShowContactView;

    private Spinner mSchoolSpinner;
    private Spinner mGenderSpinner;
    private Spinner mYearSpinner;
    private Spinner mMonthSpinner;
    private Spinner mDaySpinner;
    private Spinner mGradeSpinner;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_edit_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        initSpinner();
        setData();
        mShowContactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactView = new Intent(MemberEditActivity.this, ContactsViewActivity.class);
                startActivityForResult(contactView, 10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            String phoneNumber = data.getStringExtra("phoneNumber");
            mAlarmCellphoneInput.setText(phoneNumber);
        }
    }

    private void setData() {
        mTeamId = getIntent().getIntExtra("teamId", -1);
        mMemberId = getIntent().getIntExtra("memberId", -1);
        TeamDto teamDto = TeamDao.selectTeamBy(this, mTeamId);
        MemberDto memberDto = MemberDao.selectMemberBy(this, mMemberId);

        // title info
        mTeamInfoTitle = (TextView) findViewById(R.id.teamInfoTitle);

        // member info
        mMemberNameInput = (EditText) findViewById(R.id.memberName);
        mAlarmCellphoneInput = (EditText) findViewById(R.id.alarmCellphone);
        mShowContactView = (TextView) findViewById(R.id.showContactView);

        // team info
        mCoachName = (TextView) findViewById(R.id.coachName);
        mEvent = (TextView) findViewById(R.id.event);
        mTeamGender = (TextView) findViewById(R.id.teamGender);
        mMaximumMember = (TextView) findViewById(R.id.maximumMember);

        mTeamInfoTitle.setText(MemberDao.selectMembersBy(this, null).size() + "/" + teamDto.getMaximum() + "(현재인원/최대인원)");
        mCoachName.setText(teamDto.getCoach());
        mEvent.setText(teamDto.getEvent());
        mTeamGender.setText(teamDto.getGender());
        mMaximumMember.setText(String.valueOf(teamDto.getMaximum()));

        mMemberNameInput.setText(memberDto.getName());
        mAlarmCellphoneInput.setText(memberDto.getAlarmCellphone());
        SpinnerUtils.selectByString(mGradeSpinner, memberDto.getGrade());
        SpinnerUtils.selectByString(mGenderSpinner, memberDto.getGender());
        SpinnerUtils.selectByString(mYearSpinner, CommonUtils.getYearBy(memberDto.getBirth()));
        SpinnerUtils.selectByString(mMonthSpinner, CommonUtils.getMonthBy(memberDto.getBirth()));
        SpinnerUtils.selectByString(mDaySpinner, CommonUtils.getDayBy(memberDto.getBirth()));
        SpinnerUtils.selectByString(mSchoolSpinner, CommonUtils.getDayBy(memberDto.getSchool()));
    }

    private void initSpinner() {
        mSchoolSpinner = (Spinner) findViewById(R.id.schoolSpinner);
        SpinnerUtils.initSchoolSpinner(this, mSchoolSpinner);

        mGenderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        SpinnerUtils.initGenderSpinner(this, mGenderSpinner);

        mYearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        SpinnerUtils.initYearSpinner(this, mYearSpinner);

        mMonthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        SpinnerUtils.initMonthSpinner(this, mMonthSpinner);

        mDaySpinner = (Spinner) findViewById(R.id.daySpinner);
        SpinnerUtils.initDaySpinner(this, mDaySpinner);

        mGradeSpinner = (Spinner) findViewById(R.id.gradeSpinner);
        SpinnerUtils.initGradeSpinner(this, mGradeSpinner);
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

    public void editMember(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (verifyData()) {
                    MemberDto dto = new MemberDto(
                            mMemberId,
                            -1,
                            mTeamId,
                            String.valueOf(mMemberNameInput.getText()),
                            mYearSpinner.getSelectedItem().toString() + mMonthSpinner.getSelectedItem().toString() + mDaySpinner.getSelectedItem().toString(),
                            mSchoolSpinner.getSelectedItem().toString(),
                            mGradeSpinner.getSelectedItem().toString(),
                            mGenderSpinner.getSelectedItem().toString(),
                            String.valueOf(mAlarmCellphoneInput.getText()),
                            -1
                    );
                    long result = MemberDao.updateMemberBy(this, dto);
                    if (result == -1) {
                        CommonUtils.makeToast(this, "등록실패");
                    } else {
                        CommonUtils.makeToast(this, "등록성공");
                        Intent memberView = new Intent(MemberEditActivity.this, MemberViewActivity.class);
                        memberView.putExtra("teamId", mTeamId);
                        memberView.putExtra("memberId", mMemberId);
                        startActivity(memberView);
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
        if (CommonUtils.verifyInputData(MemberEditActivity.this, MemberEditActivity.this, mMemberNameInput, Constant.VERIFY_MESSAGE_MEMBER_NAME)
                && CommonUtils.verifyInputData(MemberEditActivity.this, MemberEditActivity.this, mYearSpinner, Constant.VERIFY_MESSAGE_MEMBER_BIRTHDAY)
                && CommonUtils.verifyInputData(MemberEditActivity.this, MemberEditActivity.this, mMonthSpinner, Constant.VERIFY_MESSAGE_MEMBER_BIRTHDAY)
                && CommonUtils.verifyInputData(MemberEditActivity.this, MemberEditActivity.this, mDaySpinner, Constant.VERIFY_MESSAGE_MEMBER_BIRTHDAY)
                && CommonUtils.verifyInputData(MemberEditActivity.this, MemberEditActivity.this, mSchoolSpinner, Constant.VERIFY_MESSAGE_SCHOOL)
                && CommonUtils.verifyInputData(MemberEditActivity.this, MemberEditActivity.this, mGradeSpinner, Constant.VERIFY_MESSAGE_GRADE)
                && CommonUtils.verifyInputData(MemberEditActivity.this, MemberEditActivity.this, mGenderSpinner, Constant.VERIFY_MESSAGE_GENDER)
                ) {
            result = true;
        }
        return result;
    }

}
