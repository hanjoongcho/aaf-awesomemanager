package me.blog.korn123.awesomemanager.team;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

public class MemberAddStep2Activity extends AppCompatThemeActivity {

    private int mTeamId;
    private TextView mCoachName;
    private TextView mEvent;
    private TextView mGender;
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

    final static int PICK_CONTACT = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_add_step2_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        initSpinner();
        setTeamData();
        mShowContactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 문자알림용 전화번호를 취득에 사용하는 intent를 변경함 2016.11.04 Hanjoong Cho
//                Intent contactView = new Intent(MemberAddStep2Activity.this, ContactsViewActivity.class);
//                startActivityForResult(contactView, 10);
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(new Intent(Intent.createChooser(pickContactIntent, "주소록 앱을 선택하세요.")), PICK_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String phoneNumber = "";
        String name = "";
        if (resultCode == -1 && requestCode == PICK_CONTACT) {
//            String phoneNumber = data.getStringExtra("phoneNumber");
//            mAlarmCellphoneInput.setText(phoneNumber);
            Uri contactData = data.getData();

            Cursor cursor =  getContentResolver().query(contactData, null, null, null, null);
            if (cursor.moveToFirst()) {
                String id =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String hasPhone =cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                            null, null);
                    phones.moveToFirst();
                    phoneNumber = phones.getString(phones.getColumnIndex("data1"));
                }
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
        }
        mAlarmCellphoneInput.setText(phoneNumber);
    }

    private void setTeamData() {
        mCoachName = (TextView) findViewById(R.id.coachName);
        mEvent = (TextView) findViewById(R.id.event);
        mGender = (TextView) findViewById(R.id.gender);
        mMaximumMember = (TextView) findViewById(R.id.maximumMember);
        mTeamInfoTitle = (TextView) findViewById(R.id.teamInfoTitle);
        mMemberNameInput = (EditText) findViewById(R.id.memberName);
        mAlarmCellphoneInput = (EditText) findViewById(R.id.alarmCellphone);
        mShowContactView = (TextView) findViewById(R.id.showContactView);

        mTeamId = getIntent().getIntExtra("id", -1);
        TeamDto dto = TeamDao.selectTeamBy(this, mTeamId);
        mCoachName.setText(dto.getCoach());
        mEvent.setText(dto.getEvent());
        mGender.setText(dto.getGender());
        mMaximumMember.setText(String.valueOf(dto.getMaximum()));
        mTeamInfoTitle.setText(MemberDao.countMemberBy(this, mTeamId) + "/" + dto.getMaximum() + "(현재인원/최대인원)");
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

    public void registerMember(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (verifyData()) {
                    MemberDto dto = new MemberDto(
                            -1,
                            Constant.MASTER_DEVICE_MANAGEMENT_ID,
                            mTeamId,
                            String.valueOf(mMemberNameInput.getText()),
                            mYearSpinner.getSelectedItem().toString() + mMonthSpinner.getSelectedItem().toString() + mDaySpinner.getSelectedItem().toString(),
                            mSchoolSpinner.getSelectedItem().toString(),
                            mGradeSpinner.getSelectedItem().toString(),
                            mGenderSpinner.getSelectedItem().toString(),
                            String.valueOf(mAlarmCellphoneInput.getText()),
                            -1
                    );
                    long result = MemberDao.insertMember(this, dto);
                    if (result == -1) {
                        CommonUtils.makeToast(this, "등록실패");
                    } else {
                        CommonUtils.makeToast(this, "등록성공");
                        finish();
                        startActivity(getIntent());
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean verifyData() {
        boolean result = false;
        if (CommonUtils.verifyInputData(MemberAddStep2Activity.this, MemberAddStep2Activity.this, mMemberNameInput, Constant.VERIFY_MESSAGE_MEMBER_NAME)
//                && CommonUtils.verifyInputData(MemberAddStep2Activity.this, MemberAddStep2Activity.this, mAlarmCellphoneInput, Constant.VERIFY_MESSAGE_GENDER)
                && CommonUtils.verifyInputData(MemberAddStep2Activity.this, MemberAddStep2Activity.this, mYearSpinner, Constant.VERIFY_MESSAGE_MEMBER_BIRTHDAY)
                && CommonUtils.verifyInputData(MemberAddStep2Activity.this, MemberAddStep2Activity.this, mMonthSpinner, Constant.VERIFY_MESSAGE_MEMBER_BIRTHDAY)
                && CommonUtils.verifyInputData(MemberAddStep2Activity.this, MemberAddStep2Activity.this, mDaySpinner, Constant.VERIFY_MESSAGE_MEMBER_BIRTHDAY)
                && CommonUtils.verifyInputData(MemberAddStep2Activity.this, MemberAddStep2Activity.this, mSchoolSpinner, Constant.VERIFY_MESSAGE_SCHOOL)
                && CommonUtils.verifyInputData(MemberAddStep2Activity.this, MemberAddStep2Activity.this, mGradeSpinner, Constant.VERIFY_MESSAGE_GRADE)
                && CommonUtils.verifyInputData(MemberAddStep2Activity.this, MemberAddStep2Activity.this, mGenderSpinner, Constant.VERIFY_MESSAGE_GENDER)
                ) {
            result = true;
        }
        return result;
    }

}
