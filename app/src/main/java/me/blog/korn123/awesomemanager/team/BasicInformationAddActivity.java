package me.blog.korn123.awesomemanager.team;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.constant.Constant;
import me.blog.korn123.support.AppCompatThemeActivity;
import me.blog.korn123.utils.CommonUtils;
import me.blog.korn123.utils.DialogUtils;
import me.blog.korn123.utils.SpinnerUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class BasicInformationAddActivity extends AppCompatThemeActivity {

    private TextView mCoachInputView;
    private TextView mSchoolInputView;
    private TextView mBranchInputView;
    private TextView mEventInputView;
    private TextView mGradeInputView;
    private TextView mCustomMessageInputView;
    private TextView mCustomMessageBodyInputView;

    private Spinner mCoachNameSpinner;
    private Spinner mSchoolSpinner;
    private Spinner mGradeSpinner;
    private Spinner mEventSpinner;
    private Spinner mBranchSpinner;
    private Spinner mCustomMessageSpinner;

    @Override
    public void refresh() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_information_add_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        initSpinner();
        initInputTextView();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initInputTextView() {
        mCoachInputView = (TextView) findViewById(R.id.coachInput);
        mSchoolInputView = (TextView) findViewById(R.id.schoolInput);
        mBranchInputView = (TextView) findViewById(R.id.branchInput);
        mEventInputView = (TextView) findViewById(R.id.eventInput);
        mGradeInputView = (TextView) findViewById(R.id.gradeInput);
        mCustomMessageInputView = (TextView) findViewById(R.id.customMessageInput);
        mCustomMessageBodyInputView = (TextView) findViewById(R.id.customMessageBodyInput);
    }

    private void initSpinner() {
        mCoachNameSpinner = (Spinner) findViewById(R.id.coachSpinner);
        mSchoolSpinner  = (Spinner) findViewById(R.id.schoolSpinner);
        mGradeSpinner = (Spinner) findViewById(R.id.gradeSpinner);
        mEventSpinner = (Spinner) findViewById(R.id.eventSpinner);
        mBranchSpinner = (Spinner) findViewById(R.id.branchSpinner);
        mCustomMessageSpinner = (Spinner) findViewById(R.id.customMessageSpinner);

        SpinnerUtils.initCoachNameSpinner(this, mCoachNameSpinner);
        SpinnerUtils.initSchoolSpinner(this, mSchoolSpinner);
        SpinnerUtils.initGradeSpinner(this, mGradeSpinner);
        SpinnerUtils.initBranchSpinner(this, mBranchSpinner);
        SpinnerUtils.initEventSpinner(this, mEventSpinner);
        SpinnerUtils.initCustomMessageSpinner(this, mCustomMessageSpinner);
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

    public void onClick(View v) {
        String basicInformationValue = null;
        switch (v.getId()) {
            case R.id.coachAdd:
                basicInformationValue = String.valueOf(mCoachInputView.getText());
                if (StringUtils.isEmpty(basicInformationValue)) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 입력되지 않았습니다.");
                } else {
                    BasicInformationDao.insertCategoryValue(this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_COACH, basicInformationValue);
                    SpinnerUtils.initCoachNameSpinner(this, mCoachNameSpinner);
                    mCoachInputView.setText("");
                    mCoachInputView.setHint("다음 항목을 입력하세요.");
                    CommonUtils.makeToast(this, "입력이 완료되었습니다.");
                }
                break;
            case R.id.schoolAdd:
                basicInformationValue = String.valueOf(mSchoolInputView.getText());
                if (StringUtils.isEmpty(basicInformationValue)) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 입력되지 않았습니다.");
                } else {
                    BasicInformationDao.insertCategoryValue(this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_SCHOOL, basicInformationValue);
                    SpinnerUtils.initSchoolSpinner(this, mSchoolSpinner);
                    mSchoolInputView.setText("");
                    mSchoolInputView.setHint("다음 항목을 입력하세요.");
                    CommonUtils.makeToast(this, "입력이 완료되었습니다.");
                }
                break;
            case R.id.branchAdd:
                basicInformationValue = String.valueOf(mBranchInputView.getText());
                if (StringUtils.isEmpty(basicInformationValue)) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 입력되지 않았습니다.");
                } else {
                    BasicInformationDao.insertCategoryValue(this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_BRANCH, basicInformationValue);
                    SpinnerUtils.initBranchSpinner(this, mBranchSpinner);
                    mBranchInputView.setText("");
                    mBranchInputView.setHint("다음 항목을 입력하세요.");
                    CommonUtils.makeToast(this, "입력이 완료되었습니다.");
                }
                break;
            case R.id.eventAdd:
                basicInformationValue = String.valueOf(mEventInputView.getText());
                if (StringUtils.isEmpty(basicInformationValue)) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 입력되지 않았습니다.");
                } else {
                    BasicInformationDao.insertCategoryValue(this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_EVENT, basicInformationValue);
                    SpinnerUtils.initEventSpinner(this, mEventSpinner);
                    mEventInputView.setText("");
                    mEventInputView.setHint("다음 항목을 입력하세요.");
                    CommonUtils.makeToast(this, "입력이 완료되었습니다.");
                }
                break;
            case R.id.gradeAdd:
                basicInformationValue = String.valueOf(mGradeInputView.getText());
                if (StringUtils.isEmpty(basicInformationValue)) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 입력되지 않았습니다.");
                } else {
                    BasicInformationDao.insertCategoryValue(this, Constant.MASTER_DEVICE_MANAGEMENT_ID, Constant.CATEGORY_GRADE, basicInformationValue);
                    SpinnerUtils.initGradeSpinner(this, mGradeSpinner);
                    mGradeInputView.setText("");
                    mGradeInputView.setHint("다음 항목을 입력하세요.");
                    CommonUtils.makeToast(this, "입력이 완료되었습니다.");
                }
                break;
            case R.id.customMessageAdd:
                basicInformationValue = String.valueOf(mCustomMessageInputView.getText());
                String customMessageBody = String.valueOf(mCustomMessageBodyInputView.getText());
                if (StringUtils.isEmpty(basicInformationValue)) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 입력되지 않았습니다.");
                } else if (StringUtils.isEmpty(customMessageBody)) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "메시지 본문이 입력되지 않았습니다.");
                } else {
                    CustomMessageDto customMessageDto = new CustomMessageDto(
                            -1,
                            basicInformationValue,
                            customMessageBody
                    );
                    CustomMessageDao.insertCustomMessage(this, customMessageDto);
                    SpinnerUtils.initCustomMessageSpinner(this, mCustomMessageSpinner);
                    mCustomMessageBodyInputView.setText("");
                    mCustomMessageInputView.setText("");
                    mCustomMessageInputView.setHint("다음 항목을 입력하세요.");
                    CommonUtils.makeToast(this, "입력이 완료되었습니다.");
                }
                break;
            case R.id.coachDelete:
                if (mCoachNameSpinner.getSelectedItem() == null) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 선택되지 않았습니다.");
                } else {
                    basicInformationValue = mCoachNameSpinner.getSelectedItem().toString();
                    BasicInformationDao.deleteCategoryValueBy(this, basicInformationValue);
                    SpinnerUtils.initCoachNameSpinner(this, mCoachNameSpinner);
                    CommonUtils.makeToast(this, "항목 '" + basicInformationValue + "' 삭제되었습니다.");
                }
                break;
            case R.id.schoolDelete:
                if (mSchoolSpinner.getSelectedItem() == null) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 선택되지 않았습니다.");
                } else {
                    basicInformationValue = mSchoolSpinner.getSelectedItem().toString();
                    BasicInformationDao.deleteCategoryValueBy(this, basicInformationValue);
                    SpinnerUtils.initSchoolSpinner(this, mSchoolSpinner);
                    CommonUtils.makeToast(this, "항목 '" + basicInformationValue + "' 삭제되었습니다.");
                }
                break;
            case R.id.gradeDelete:
                if (mGradeSpinner.getSelectedItem() == null) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 선택되지 않았습니다.");
                } else {
                    basicInformationValue = mGradeSpinner.getSelectedItem().toString();
                    BasicInformationDao.deleteCategoryValueBy(this, basicInformationValue);
                    SpinnerUtils.initGradeSpinner(this, mGradeSpinner);
                    CommonUtils.makeToast(this, "항목 '" + basicInformationValue + "' 삭제되었습니다.");
                }
                break;
            case R.id.branchDelete:
                if (mBranchSpinner.getSelectedItem() == null) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 선택되지 않았습니다.");
                } else {
                    basicInformationValue = mBranchSpinner.getSelectedItem().toString();
                    BasicInformationDao.deleteCategoryValueBy(this, basicInformationValue);
                    SpinnerUtils.initBranchSpinner(this, mBranchSpinner);
                    CommonUtils.makeToast(this, "항목 '" + basicInformationValue + "' 삭제되었습니다.");
                }
                break;
            case R.id.eventDelete:
                if (mEventSpinner.getSelectedItem() == null) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 선택되지 않았습니다.");
                } else {
                    basicInformationValue = mEventSpinner.getSelectedItem().toString();
                    BasicInformationDao.deleteCategoryValueBy(this, basicInformationValue);
                    SpinnerUtils.initEventSpinner(this, mEventSpinner);
                    CommonUtils.makeToast(this, "항목 '" + basicInformationValue + "' 삭제되었습니다.");
                }
                break;
            case R.id.customMessageDelete:
                if (mCustomMessageSpinner.getSelectedItem() == null) {
                    DialogUtils.showAlertDialogOnlyPositive(this, "", "항목이 선택되지 않았습니다.");
                } else {
                    basicInformationValue = mCustomMessageSpinner.getSelectedItem().toString();
                    CustomMessageDto customMessageDto = (CustomMessageDto) mCustomMessageSpinner.getSelectedItem();
                    CustomMessageDao.deleteCustomMessageBy(this, customMessageDto.getId());
                    SpinnerUtils.initCustomMessageSpinner(this, mCustomMessageSpinner);
                    CommonUtils.makeToast(this, "항목 '" + basicInformationValue + "' 삭제되었습니다.");
                }
                break;
            default:
                break;
        }
    }

}
