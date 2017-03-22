package me.blog.korn123.awesomemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.blog.korn123.awesomemanager.playground.PlaygroundAddActivity;
import me.blog.korn123.awesomemanager.playground.PlaygroundSearchActivity;
import me.blog.korn123.awesomemanager.setting.SettingsActivity;
import me.blog.korn123.awesomemanager.team.BasicInformationAddActivity;
import me.blog.korn123.awesomemanager.team.MemberAddStep1Activity;
import me.blog.korn123.awesomemanager.team.MemberSearchActivity;
import me.blog.korn123.awesomemanager.team.TeamAddActivity;
import me.blog.korn123.awesomemanager.team.TeamAttendanceSearchActivity;
import me.blog.korn123.awesomemanager.team.TeamAttendanceStep1Activity;
import me.blog.korn123.awesomemanager.team.TeamSearchActivity;
import me.blog.korn123.support.AppCompatThemeActivity;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class IndexActivity extends AppCompatThemeActivity {

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teamAdd:
                Intent teamAdd = new Intent(this, TeamAddActivity.class);
                startActivity(teamAdd);
                break;
            case R.id.teamSearch:
                Intent teamSearch = new Intent(this, TeamSearchActivity.class);
                startActivity(teamSearch);
                break;
            case R.id.teamAttendanceAdd:
                Intent attendanceStep1 = new Intent(this, TeamAttendanceStep1Activity.class);
                startActivity(attendanceStep1);
                break;
            case R.id.teamAttendanceSearch:
                Intent attendanceSearch = new Intent(this, TeamAttendanceSearchActivity.class);
                startActivity(attendanceSearch);
                break;
            case R.id.memberAdd:
                Intent memberAdd = new Intent(this, MemberAddStep1Activity.class);
                startActivity(memberAdd);
                break;
            case R.id.memberSearch:
                Intent memberSearch = new Intent(this, MemberSearchActivity.class);
                startActivity(memberSearch);
                break;
            case R.id.playgroundAdd:
                Intent playgroundAdd = new Intent(this, PlaygroundAddActivity.class);
                startActivity(playgroundAdd);
                break;
            case R.id.playgroundSearch:
                Intent playgroundSearch = new Intent(this, PlaygroundSearchActivity.class);
                startActivity(playgroundSearch);
                break;
            case R.id.basicInformationAdd:
                Intent basicInformationAdd = new Intent(this, BasicInformationAddActivity.class);
                startActivity(basicInformationAdd);
                break;
            default:
                break;
        }
    }

}
