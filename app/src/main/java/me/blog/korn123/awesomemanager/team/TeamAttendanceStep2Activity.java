package me.blog.korn123.awesomemanager.team;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.playground.PlaygroundDao;
import me.blog.korn123.awesomemanager.playground.PlaygroundDto;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.log.AAFLogger;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DateUtils;
import me.blog.korn123.utils.DialogUtils;
import me.blog.korn123.utils.SpinnerUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class TeamAttendanceStep2Activity extends AppCompatThemeActivity {

    private ListView mListViewMember;
    private List<MemberDto> mListMemberDto;
    private ArrayAdapter<MemberDto> mMemberAdapter;
    private Spinner mCustomMessageSpinner;
    private int mTeamId;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_attendance_step2_main);
        mCustomMessageSpinner = (Spinner) findViewById(R.id.customMessageSpinner);
        SpinnerUtils.initCustomMessageSpinner(this, mCustomMessageSpinner);
        mTeamId = getIntent().getIntExtra("teamId", -1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        refreshList();
    }

    public void refreshList() {
        if (mListMemberDto != null) {
            mListMemberDto.clear();
            // TODO 조회목롤을 리스트에 추가해야 함
        } else {
            mListMemberDto = MemberDao.selectMembersBy(this, mTeamId);
            mMemberAdapter = new MemberAttendanceAddArrayAdapter(this, this, R.layout.member_attendance_add_array_adapter_item, this.mListMemberDto);
            mListViewMember = (ListView) findViewById(R.id.listMember);
            mListViewMember.setAdapter(mMemberAdapter);
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

    public void registerAttendance(View view) {
        switch (view.getId()) {
            case R.id.submit:
                // TODO 출결플로우 구현
                // 1. adapter를 순회하여 필요정보 추출
                int total = mMemberAdapter.getCount();
                int successCount = 0;
                StringBuilder attendanceInfo = new StringBuilder("출석자 목록\n");
                StringBuilder nonAttendanceInfo = new StringBuilder("결석자 목록\n");
                for (int i = 0; i < mMemberAdapter.getCount(); i++) {

                    LinearLayout layout = (LinearLayout) mMemberAdapter.getView(i, null, null);
                    String info = String.valueOf(((TextView) layout.findViewById(R.id.text1)).getText());
//                    AAFLogger.info("TeamAttendanceStep2Activity-registerAttendance INFO: " + info, getClass());
                    Spinner spinner = (Spinner) layout.findViewById(R.id.spinner);
//                    AAFLogger.info("TeamAttendanceStep2Activity-registerAttendance INFO: " + spinner.getSelectedItem().toString() + ", " + spinner.getSelectedItemId(), getClass());
//                    TeamAttendanceDto(int memberId, int teamId, String registerDate, int flag)
                    // index 0 is true
                    // index 1 is false
                    String alarmCellphone = mMemberAdapter.getItem(i).getAlarmCellphone();
                    int flag = 0;
                    if (spinner.getSelectedItemId() == 0) {
                        flag = 1;
                        attendanceInfo.append(mMemberAdapter.getItem(i).getName() + "\n");
                    } else {
                        nonAttendanceInfo.append(mMemberAdapter.getItem(i).getName() + "\n");
                    }

                    TeamAttendanceDto dto = new TeamAttendanceDto(
                            -1,
                            Constant.MASTER_DEVICE_MANAGEMENT_ID,
                            mMemberAdapter.getItem(i).getMemberId(),
                            mTeamId,
                            DateUtils.getCurrentDateAsString(DateUtils.DATE_TIME_PATTERN),
                            flag,
                            -1,
                            null,
                            null
                    );
                    long result = TeamAttendanceDao.insertAttendance(this, dto);

                    // TODO SMS 최대전송 가능 사이즈 넘어가지 않게 보완필요 (140byte)
                    if (!StringUtils.isEmpty(alarmCellphone)) {
                        StringBuilder messageBody = new StringBuilder();
                        String temp = "결석";
                        if (flag == 1) {
                            temp = "출석";
                        }
                        PlaygroundDto playgroundDto = PlaygroundDao.selectPlaygroundBy(this, mMemberAdapter.getItem(i));
                        CustomMessageDto customMessageDto = (CustomMessageDto) mCustomMessageSpinner.getSelectedItem();
                        String replaceMessage = customMessageDto.getCustomMessageBody()
                                        .replaceAll("!팀원!",mMemberAdapter.getItem(i).getName())
                                        .replaceAll("!출결정보!",temp)
                                        .replaceAll("!수업장소!", playgroundDto.getName());
                        messageBody.append(replaceMessage);
//                        AAFLogger.info("TeamAttendanceStep2Activity-registerAttendance INFO: message body" + replaceMessage , getClass());
//                        messageBody.append("위치: " + playgroundDto.getAddress() + "\n");
                        try {
//                            AAFLogger.info("TeamAttendanceStep2Activity-registerAttendance INFO: message length" + messageBody.toString().length() , getClass());
//                            AAFLogger.info("TeamAttendanceStep2Activity-registerAttendance INFO: message length" + replaceMessage.getBytes("EUC-KR").length , getClass());
                            SmsManager smsManager = SmsManager.getDefault();
//                            PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
//                            registerReceiver(new BroadcastReceiver() {
//                                @Override
//                                public void onReceive(Context context, Intent intent) {
//                                    switch(getResultCode()){
//                                        case Activity.RESULT_OK:
//                                            // 전송 성공
//                                            Toast.makeText(TeamAttendanceStep2Activity.this, "전송 완료", Toast.LENGTH_SHORT).show();
//                                            break;
//                                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                                            // 전송 실패
//                                            Toast.makeText(TeamAttendanceStep2Activity.this, "전송 실패", Toast.LENGTH_SHORT).show();
//                                            break;
//                                        case SmsManager.RESULT_ERROR_NO_SERVICE:
//                                            // 서비스 지역 아님
//                                            Toast.makeText(TeamAttendanceStep2Activity.this, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
//                                            break;
//                                        case SmsManager.RESULT_ERROR_RADIO_OFF:
//                                            // 무선 꺼짐
//                                            Toast.makeText(TeamAttendanceStep2Activity.this, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
//                                            break;
//                                        case SmsManager.RESULT_ERROR_NULL_PDU:
//                                            // PDU 실패
//                                            Toast.makeText(TeamAttendanceStep2Activity.this, "PDU Null", Toast.LENGTH_SHORT).show();
//                                            break;
//                                    }
//                                }
//                            }, new IntentFilter("SMS_SENT_ACTION"));
//                            smsManager.sendTextMessage(alarmCellphone, null, messageBody.toString(), sentIntent, null);
                            smsManager.sendTextMessage(alarmCellphone, null, messageBody.toString(), null, null);
                        } catch (Exception e) {
                            AAFLogger.error("TeamAttendanceStep2Activity-registerAttendance ERROR: " + e.getMessage(), getClass());
                            CommonUtils.makeToast(this, e.getMessage());
                        }
                    }

                    if (result > -1) successCount++;
                }

                if (successCount == total) {
                    attendanceInfo.append("\n" + nonAttendanceInfo.toString());
                    DialogUtils.showAlertDialogOnlyPositive(this, "출결처리 결과", attendanceInfo.toString());
                } else {
                    CommonUtils.makeToast(this, "등록실패");
                }
                break;
            default:
                break;
        }
    }

}
