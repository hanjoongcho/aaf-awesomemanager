package me.blog.korn123.awesomemanager.team;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.playground.PlaygroundDao;
import me.blog.korn123.awesomemanager.playground.PlaygroundDto;
import me.blog.korn123.support.AppCompatThemeActivity;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class TeamViewActivity extends AppCompatThemeActivity {

    private int mTeamId;
    private TextView mCoachName;
    private TextView mSchool;
    private TextView mGrade;
    private TextView mGround;
    private TextView mEvent;
    private TextView mGender;
    private TextView mBranch;
    private TextView mMaximumMember;
    private TextView mTeamName;
    private TextView mEtcInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_view_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        setTeamData();
    }

    private ListPopupWindow mListMemberPopup;
    private void setTeamData() {
        mCoachName = (TextView) findViewById(R.id.coachName);
        mSchool = (TextView) findViewById(R.id.school);
        mGrade = (TextView) findViewById(R.id.grade);
        mGround = (TextView) findViewById(R.id.ground);
        mEvent = (TextView) findViewById(R.id.event);
        mGender = (TextView) findViewById(R.id.gender);
        mBranch = (TextView) findViewById(R.id.branch);
        mEtcInfo = (TextView) findViewById(R.id.etcInfo);
        mMaximumMember = (TextView) findViewById(R.id.maximumMember);
        mTeamName = (TextView) findViewById(R.id.teamName);
        mTeamId = getIntent().getIntExtra("teamId", -1);
        TeamDto teamDto = TeamDao.selectTeamBy(this, mTeamId);
        PlaygroundDto playgroundDto = PlaygroundDao.selectPlaygroundBy(this, teamDto.getPlaygroundId());
        mCoachName.setText(teamDto.getCoach());
        mSchool.setText(teamDto.getSchool());
        mGrade.setText(teamDto.getGrade());
        // TODO PlaygroundDto mapping 해야함
        mGround.setText(playgroundDto.getName());
        mEvent.setText(teamDto.getEvent());
        mGender.setText(teamDto.getGender());
        mBranch.setText(teamDto.getBranch());
        mMaximumMember.setText(String.valueOf(teamDto.getMaximum()));
        mTeamName.setText(teamDto.getName());
        mEtcInfo.setText(teamDto.getEtcInfo());
    }

    @Override
    public void refresh() {}

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
            case R.id.teamEdit:
                Intent teamEdit = new Intent(TeamViewActivity.this, TeamEditActivity.class);
                teamEdit.putExtra("teamId", mTeamId);
                startActivity(teamEdit);
                finish();
            case R.id.showMember:
                mListMemberPopup = new ListPopupWindow(this);
                List<MemberDto> listMemberDto = MemberDao.selectMembersBy(this, mTeamId);
                ArrayAdapter<MemberDto> memberAdapter = new ArrayAdapter<MemberDto>(this, android.R.layout.simple_list_item_1, listMemberDto);
                mListMemberPopup.setAdapter(memberAdapter);
                mListMemberPopup.setAnchorView(view);
                ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFFFF"));
                mListMemberPopup.setBackgroundDrawable(colorDrawable);
                mListMemberPopup.show();
                break;
        }
    }

}
