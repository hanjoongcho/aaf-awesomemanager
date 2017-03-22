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
import me.blog.korn123.support.AppCompatThemeActivity;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class MemberViewActivity extends AppCompatThemeActivity {

    private int mTeamId;
    private int mMemberId;
    private TextView mMemberName;
    private TextView mBirthday;
    private TextView mSchool;
    private TextView mGrade;
    private TextView mMemberGender;
    private TextView mTeamGender;
    private TextView mTeamName;
    private TextView mCoachName;
    private TextView mEvent;
    private TextView mMaximumMember;
    private TextView mAlarmCellphone;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_view_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        setData();
    }

    private void setData() {
        // member info
        mMemberGender = (TextView) findViewById(R.id.memberGender);
        mMemberName = (TextView) findViewById(R.id.memberName);
        mBirthday = (TextView) findViewById(R.id.birthday);
        mSchool = (TextView) findViewById(R.id.school);
        mGrade = (TextView) findViewById(R.id.grade);
        mAlarmCellphone = (TextView) findViewById(R.id.alarmCellphone);

        // team info
        mTeamName = (TextView) findViewById(R.id.teamName);
        mCoachName = (TextView) findViewById(R.id.coachName);
        mEvent = (TextView) findViewById(R.id.event);
        mTeamGender = (TextView) findViewById(R.id.teamGender);
        mMaximumMember = (TextView) findViewById(R.id.maximumMember);

        mTeamId = getIntent().getIntExtra("teamId", -1);
        mMemberId = getIntent().getIntExtra("memberId", -1);
        TeamDto teamDto = TeamDao.selectTeamBy(this, mTeamId);
        MemberDto memberDto = MemberDao.selectMemberBy(this, mMemberId);
        mMemberGender.setText(memberDto.getGender());
        mMemberName.setText(memberDto.getName());
        mBirthday.setText(memberDto.getBirth());
        mSchool.setText(memberDto.getSchool());
        mGrade.setText(memberDto.getGrade());
        mAlarmCellphone.setText(memberDto.getAlarmCellphone());

        mTeamName.setText(teamDto.getName());
        mCoachName.setText(teamDto.getCoach());
        mEvent.setText(teamDto.getEvent());
        mTeamGender.setText(teamDto.getGender());
        mMaximumMember.setText(String.valueOf(teamDto.getMaximum()));
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.memberEdit:
                Intent memberEdit = new Intent(MemberViewActivity.this, MemberEditActivity.class);
                memberEdit.putExtra("teamId", mTeamId);
                memberEdit.putExtra("memberId", mMemberId);
                startActivity(memberEdit);
                finish();
                break;
        }
    }

}
