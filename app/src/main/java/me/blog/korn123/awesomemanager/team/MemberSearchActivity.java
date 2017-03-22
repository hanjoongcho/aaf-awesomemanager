package me.blog.korn123.awesomemanager.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.support.AppCompatThemeActivity;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class MemberSearchActivity extends AppCompatThemeActivity {

    private ListView mListViewMember;
    private List<MemberDto> mListMemberDto;
    private ArrayAdapter<MemberDto> mMemberAdapter;
    private SearchView mMemberSearchView;

    @Override
    public void refresh() {
        refreshList(String.valueOf(mMemberSearchView.getQuery()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_search_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        refreshList(null);
        bindSearchViewEvent();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void bindSearchViewEvent() {
        mMemberSearchView = (SearchView) findViewById(R.id.memberSearchView);
        mMemberSearchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
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
        if (mListMemberDto != null) {
            mListMemberDto.clear();
            mListMemberDto.addAll(MemberDao.selectMembersBy(this, keyword));
            mMemberAdapter.notifyDataSetChanged();
        } else {
            mListMemberDto = MemberDao.selectMembersBy(this, null);
            mMemberAdapter = new MemberSearchInfoArrayAdapter(this, this, R.layout.member_search_info_array_adapter_item, this.mListMemberDto);
            mListViewMember = (ListView) findViewById(R.id.listMember);
            mListViewMember.setAdapter(mMemberAdapter);
            mListViewMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    MemberDto dto = (MemberDto) adapterView.getAdapter().getItem(position);
                    Intent memberView = new Intent(MemberSearchActivity.this, MemberViewActivity.class);
                    memberView.putExtra("teamId", dto.getTeamId());
                    memberView.putExtra("memberId", dto.getMemberId());
                    startActivity(memberView);
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
