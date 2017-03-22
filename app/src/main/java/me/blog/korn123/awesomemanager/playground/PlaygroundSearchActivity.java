package me.blog.korn123.awesomemanager.playground;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

public class PlaygroundSearchActivity extends AppCompatThemeActivity {

    private ListView mListViewPlayground;
    private List<PlaygroundDto> mListPlaygroundDto;
    private ArrayAdapter<PlaygroundDto> mPlaygroundAdapter;
    private SearchView mPlaygroundSearchView;

    @Override
    public void refresh() {
        refreshList(String.valueOf(mPlaygroundSearchView.getQuery()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground_search_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        refreshList(null);
        bindSearchViewEvent();
    }

    private void bindSearchViewEvent() {
        mPlaygroundSearchView = (SearchView) findViewById(R.id.playgroundSearchView);
        mPlaygroundSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        if (mListPlaygroundDto != null) {
            mListPlaygroundDto.clear();
            mListPlaygroundDto.addAll(PlaygroundDao.selectPlaygrounds(this, keyword));
            mPlaygroundAdapter.notifyDataSetChanged();
        } else {
            mListPlaygroundDto = PlaygroundDao.selectPlaygrounds(this, keyword);
            mPlaygroundAdapter = new PlaygroundArrayAdapter(this, this, R.layout.playground_array_adapter_item, this.mListPlaygroundDto);
            mListViewPlayground = (ListView) findViewById(R.id.listPlayground);
            mListViewPlayground.setAdapter(mPlaygroundAdapter);
            mListViewPlayground.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PlaygroundDto dto = (PlaygroundDto) parent.getAdapter().getItem(position);
                    Intent playgroundView = new Intent(PlaygroundSearchActivity.this, PlaygroundViewActivity.class);
                    playgroundView.putExtra("branch", dto.getBranch());
                    playgroundView.putExtra("etcInfo", dto.getEtcInfo());
                    playgroundView.putExtra("address", dto.getAddress());
                    playgroundView.putExtra("name", dto.getName());
                    playgroundView.putExtra("kind", dto.getKind());
                    playgroundView.putExtra("playgroundId", dto.getId());
                    startActivity(playgroundView);
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
