package me.blog.korn123.awesomemanager.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.support.AppCompatThemeActivity;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class TeamAttendanceStep1Activity extends AppCompatThemeActivity {

    private ListView mListViewTeam;
    private List<TeamDto> mListTeamDto;
    private ArrayAdapter<TeamDto> mTeamAdapter;
    private SearchView mTeamAttendanceSearchView;

    @Override
    public void refresh() {
        refreshList(String.valueOf(mTeamAttendanceSearchView.getQuery()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_attendance_step1_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        refreshList(null);
        bindSearchViewEvent();
    }

    private void bindSearchViewEvent() {
        mTeamAttendanceSearchView = (SearchView) findViewById(R.id.teamAttendanceSearchView);
        mTeamAttendanceSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                refreshList(keyword);
                return false;
            }
        });
    }

    public void refreshList(String keyword) {
        if (mListTeamDto != null) {
            mListTeamDto.clear();
            mListTeamDto.addAll(TeamDao.selectTeamsBy(this, keyword));
            mTeamAdapter.notifyDataSetChanged();
        } else {
            mListTeamDto = TeamDao.selectTeamsBy(this, null);
            mTeamAdapter = new TeamArrayAdapter(this, this, R.layout.team_array_adapter_item, this.mListTeamDto);
            mListViewTeam = (ListView) findViewById(R.id.listTeam);
            mListViewTeam.setAdapter(mTeamAdapter);
            mListViewTeam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    TeamDto dto = (TeamDto) adapterView.getAdapter().getItem(position);
                    Intent attendanceStep2 = new Intent(TeamAttendanceStep1Activity.this, TeamAttendanceStep2Activity.class);
                    attendanceStep2.putExtra("teamId", dto.getId());
                    startActivity(attendanceStep2);
                }
            });
        }
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

}
