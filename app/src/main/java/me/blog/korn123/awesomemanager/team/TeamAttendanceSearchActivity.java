package me.blog.korn123.awesomemanager.team;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.ListPreference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Text;

import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DateUtils;
import me.blog.korn123.utils.DialogUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class TeamAttendanceSearchActivity extends AppCompatThemeActivity {

    private ListView mListViewAttendance;
    private List<TeamAttendanceDto> mListAttendanceDto;
    private ArrayAdapter<TeamAttendanceDto> mAttendanceAdapter;
    private Context context;
    private Activity activity;
    private SearchView mMemberSearchView;
    private SearchView mTeamSearchView;
    private TextView mStartDateInput;
    private TextView mEndDateInput;
    private String mMemberName;
    private String mTeamName;
    private String mStartDate;
    private String mEndDate;
    private TextView mFilterInfo;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_attendance_search_main);
        mStartDateInput = (TextView) findViewById(R.id.startDateInput);
        mEndDateInput = (TextView) findViewById(R.id.endDateInput);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        mFilterInfo = (TextView) findViewById(R.id.filterInfo);

        mStartDate = mStartYear +"-" + (mStartMonth) + "-" + mStartDay;
        mStartDateInput.setText(mStartDate);
        mEndDate = mEndYear +"-" + (mEndMonth) + "-" + mEndDay;
        mEndDateInput.setText(mEndDate);

        refreshList();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        bindSearchViewEvent();

    }

    private void bindSearchViewEvent() {
        mMemberSearchView = (SearchView) findViewById(R.id.memberSearchView);
        mMemberSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                mMemberName = keyword;
                refreshList();
                return false;
            }
        });

        mTeamSearchView = (SearchView) findViewById(R.id.teamSearchView);
        mTeamSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                mTeamName = keyword;
                refreshList();
                return false;
            }
        });
    }

    public void refreshList() {
        if (mListAttendanceDto != null) {
            mListAttendanceDto.clear();
            List<TeamAttendanceDto> listTemp = TeamAttendanceDao.selectAttendancesBy(
                    this,
                    StringUtils.stripToEmpty(mMemberName),
                    StringUtils.stripToEmpty(mTeamName),
                    CommonUtils.appendPaddingToDate(mStartDate),
                    CommonUtils.appendPaddingToDate(mEndDate));
            mListAttendanceDto.addAll(listTemp);
            mAttendanceAdapter.notifyDataSetChanged();
        } else {
//            Context context, String memberName, String teamName, String startDate, String endDate
            mListAttendanceDto = TeamAttendanceDao.selectAttendancesBy(
                    this,
                    StringUtils.stripToEmpty(mMemberName),
                    StringUtils.stripToEmpty(mTeamName),
                    CommonUtils.appendPaddingToDate(mStartDate),
                    CommonUtils.appendPaddingToDate(mEndDate));
            mAttendanceAdapter = new MemberAttendanceInfoArrayAdapter(this, this, R.layout.member_attendance_info_array_adapter_item, this.mListAttendanceDto);
            mListViewAttendance = (ListView) findViewById(R.id.listMember);
            mListViewAttendance.setAdapter(mAttendanceAdapter);
        }
        mFilterInfo.setText("검색필터(조회결과 " + mListAttendanceDto.size() + "건)");
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

    private PopupWindow popupWindow;
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggleSearchArea:
                LinearLayout searchArea = (LinearLayout) findViewById(R.id.searchArea);
                if (searchArea.getVisibility() == View.GONE) {
                    searchArea.setVisibility(View.VISIBLE);
                    ((Button) view).setText("검색필터 닫기");
                } else {
                    searchArea.setVisibility(View.GONE);
                    ((Button) view).setText("검색필터 열기");
                }
                break;
            case R.id.enableEdit:
                for (TeamAttendanceDto dto : mListAttendanceDto) {
                    dto.setEnableSpinner(true);
                }
                mAttendanceAdapter.notifyDataSetChanged();
                break;
            case R.id.save:
//                context = TeamAttendanceSearchActivity.this;
//                activity = this;
//                DialogUtils.showAlertDialogInAttendanceEdit(context, activity, "변경정보 저장", "변경된 출결정보를 저장 하시겠습니까?", mAttendanceAdapter);

                int total = mAttendanceAdapter.getCount();
                int successCount = 0;
                for (int i = 0; i < mAttendanceAdapter.getCount(); i++) {

                    LinearLayout layout = (LinearLayout) mAttendanceAdapter.getView(i, null, null);
                    Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
//                    TeamAttendanceDto(int memberId, int teamId, String registerDate, int flag)
                    // index 0 is true
                    // index 1 is false
                    int flag = 0;
                    if (spinner.getSelectedItemId() == 0) {
                        flag = 1;
                    }
                    TeamAttendanceDto dto = new TeamAttendanceDto(
                            mAttendanceAdapter.getItem(i).getAttendanceId(),
                            DateUtils.getCurrentDateAsString(DateUtils.DATE_TIME_PATTERN),
                            flag
                    );
                    long result = TeamAttendanceDao.updateAttendanceBy(this, dto);
                    if (result > -1) successCount++;
                }

                if (successCount == total) {
                    CommonUtils.makeToast(this, "수정성공");
                } else {
                    CommonUtils.makeToast(this, "수정실패");
                }
                break;
            case R.id.startDateInput:
                if (mStartDatePickerDialog == null) {
                    mStartDatePickerDialog = new DatePickerDialog(this, mStartDateListener, mStartYear, mStartMonth - 1, mStartDay);
                }
                mStartDatePickerDialog.show();
                break;
            case R.id.endDateInput:
                if (mEndDatePickerDialog == null) {
                    mEndDatePickerDialog = new DatePickerDialog(this, mEndDateListener, mEndYear, mEndMonth -1, mEndDay);
                }
                mEndDatePickerDialog.show();
                break;
            default:
                break;
        }
    }

    private int mStartYear = Integer.valueOf(DateUtils.getCurrentDateAsString(DateUtils.YEAR_PATTERN));
    private int mStartMonth = Integer.valueOf(DateUtils.getCurrentDateAsString(DateUtils.MONTH_PATTERN));
    private int mStartDay = Integer.valueOf(DateUtils.getCurrentDateAsString(DateUtils.DAY_PATTERN));
    private DatePickerDialog mStartDatePickerDialog;
    DatePickerDialog.OnDateSetListener mStartDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            mStartYear = year;
//            mStartMonth = month;
//            mStartDay = dayOfMonth;
            mStartDate = year +"-" + (month + 1) + "-" + dayOfMonth;
            mStartDateInput.setText(mStartDate);
            refreshList();
//            AAFLogger.info("TeamAttendanceSearchActivity-onDateSet INFO: " + mStartDate, getClass());
        }
    };

    private int mEndYear = Integer.valueOf(DateUtils.getCurrentDateAsString(DateUtils.YEAR_PATTERN));
    private int mEndMonth = Integer.valueOf(DateUtils.getCurrentDateAsString(DateUtils.MONTH_PATTERN));
    private int mEndDay = Integer.valueOf(DateUtils.getCurrentDateAsString(DateUtils.DAY_PATTERN));
    private DatePickerDialog mEndDatePickerDialog;
    DatePickerDialog.OnDateSetListener mEndDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            mEndYear = year;
//            mEndMonth = month;
//            mEndDay = dayOfMonth;
            mEndDate = year +"-" + (month + 1) + "-" + dayOfMonth;
            mEndDateInput.setText(mEndDate);
            refreshList();
//            AAFLogger.info("TeamAttendanceSearchActivity-onDateSet INFO: " + mEndDate, getClass());
        }
    };

}
