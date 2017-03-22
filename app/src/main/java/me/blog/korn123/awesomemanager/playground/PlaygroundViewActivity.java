package me.blog.korn123.awesomemanager.playground;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.support.AppCompatThemeActivity;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class PlaygroundViewActivity extends AppCompatThemeActivity {

    private TextView mBranch;
    private TextView mEtcInfo;
    private TextView mAddress;
    private TextView mName;
    private TextView mKind;
    private int mPlaygroundId;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground_view_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setPlaygroundData();
    }

    private void setPlaygroundData() {
        mBranch = (TextView) findViewById(R.id.branch);
        mEtcInfo = (TextView) findViewById(R.id.etcInfo);
        mAddress = (TextView) findViewById(R.id.address);
        mName = (TextView) findViewById(R.id.playgroundName);
        mKind = (TextView) findViewById(R.id.playgroundKind);
        mPlaygroundId = getIntent().getIntExtra("playgroundId", -1);
        PlaygroundDto playgroundDto = PlaygroundDao.selectPlaygroundBy(this, mPlaygroundId);
        mBranch.setText(playgroundDto.getBranch());
        mEtcInfo.setText(playgroundDto.getEtcInfo());
        mAddress.setText(playgroundDto.getAddress());
        mName.setText(playgroundDto.getName());
        mKind.setText(playgroundDto.getKind());

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
            case R.id.playgroundEdit:
                Intent playgroundEdit = new Intent(PlaygroundViewActivity.this, PlaygroundEditActivity.class);
                playgroundEdit.putExtra("playgroundId", mPlaygroundId);
                startActivity(playgroundEdit);
                finish();
                break;
            case R.id.playgroundDelete:
                PlaygroundDao.deletePlaygroundBy(this, mPlaygroundId);
                Intent playgroundSearch = new Intent(PlaygroundViewActivity.this, PlaygroundSearchActivity.class);
                startActivity(playgroundSearch);
                finish();
                break;
            default:
        }
    }

}
