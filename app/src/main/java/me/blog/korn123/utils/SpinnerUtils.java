package me.blog.korn123.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.playground.PlaygroundDao;
import me.blog.korn123.awesomemanager.playground.PlaygroundDto;
import me.blog.korn123.awesomemanager.team.BasicInformationDao;
import me.blog.korn123.awesomemanager.team.CustomMessageDao;
import me.blog.korn123.awesomemanager.team.CustomMessageDto;
import me.blog.korn123.constant.Constant;

/**
 * Created by CHO HANJOONG on 2016-10-08.
 */

public class SpinnerUtils {

    public class SpinnerDto {
        String key;
        String value;

        public SpinnerDto(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static void initCoachNameSpinner(Context context, Spinner spinner) {
//        ArrayAdapter coachNameAdapter = ArrayAdapter.createFromResource(this, R.array.coach_name_arrays, R.layout.spinner_team_add_item);
        ArrayAdapter coachNameAdapter = new ArrayAdapter(
                context,
                R.layout.spinner_team_add_item,
                BasicInformationDao.selectCategoryValuesBy(context, Constant.CATEGORY_COACH));
        coachNameAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(coachNameAdapter);
    }

    public static void initSchoolSpinner(Context context, Spinner spinner) {
        ArrayAdapter schoolAdapter = new ArrayAdapter(
                context,
                R.layout.spinner_team_add_item,
                BasicInformationDao.selectCategoryValuesBy(context, Constant.CATEGORY_SCHOOL));
        schoolAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(schoolAdapter);
//        spinner.setPopupBackgroundResource(R.drawable.rectangle_with_radius_type_e);
    }

    public static void initGradeSpinner(Context context, Spinner spinner) {
        ArrayAdapter gradeAdapter = new ArrayAdapter(
                context,
                R.layout.spinner_team_add_item,
                BasicInformationDao.selectCategoryValuesBy(context, Constant.CATEGORY_GRADE));
        gradeAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(gradeAdapter);
    }

    public static void initGroundSpinner(Context context, Spinner spinner) {
        List<PlaygroundDto> listDto = PlaygroundDao.selectPlaygrounds(context, null);
//        List<String> listValue = new ArrayList<>();
//        for (PlaygroundDto dto : listDto) {
//            listValue.add(dto.getName());
//        }
        ArrayAdapter groundAdapter = new ArrayAdapter(
                context,
                R.layout.spinner_team_add_item,
                listDto);
        groundAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(groundAdapter);
    }

    public static void initBranchSpinner(Context context, Spinner spinner) {
        ArrayAdapter branchAdapter = new ArrayAdapter(
                context,
                R.layout.spinner_team_add_item,
                BasicInformationDao.selectCategoryValuesBy(context, Constant.CATEGORY_BRANCH));
        branchAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(branchAdapter);
    }

    public static void initEventSpinner(Context context, Spinner spinner) {
        ArrayAdapter branchAdapter = new ArrayAdapter(
                context,
                R.layout.spinner_team_add_item,
                BasicInformationDao.selectCategoryValuesBy(context, Constant.CATEGORY_EVENT));
        branchAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(branchAdapter);
    }

    public static void initCustomMessageSpinner(Context context, Spinner spinner) {
        // TODO 기본제공 출결문자가 없는 경우 insert
        List<CustomMessageDto> listCustomMessageDto = CustomMessageDao.selectCustomMessages(context);
        if (listCustomMessageDto.size() == 0) {
            StringBuilder basicSmsBuilder = new StringBuilder();
            basicSmsBuilder.append("[Awesome Manager]\n");
            basicSmsBuilder.append("!팀원! 팀원이 !출결정보!처리 되었습니다.\n");
            basicSmsBuilder.append("수업장소: !수업장소!\n");

            CustomMessageDto customMessageDto = new CustomMessageDto(
                    -1,
                    "기본제공 출결문자",
                    basicSmsBuilder.toString()
            );
            CustomMessageDao.insertCustomMessage(context, customMessageDto);
            listCustomMessageDto = CustomMessageDao.selectCustomMessages(context);
        }

        ArrayAdapter customMessageAdapter = new ArrayAdapter(
                context,
                R.layout.spinner_team_add_item,
                listCustomMessageDto);
        customMessageAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(customMessageAdapter);
    }

    public static void initMaximumMemberSpinner(Context context, Spinner spinner) {
        ArrayAdapter maximumMemberAdapter = ArrayAdapter.createFromResource(context, R.array.maximumMember_arrays, R.layout.spinner_team_add_item);
        maximumMemberAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(maximumMemberAdapter );
    }

    public static void initPlaygroundKindSpinner(Context context, Spinner spinner) {
        ArrayAdapter playgroundKindAdapter = ArrayAdapter.createFromResource(context, R.array.playground_kind_arrays, R.layout.spinner_team_add_item);
        playgroundKindAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(playgroundKindAdapter);
    }

    public static void initGenderSpinner(Context context, Spinner spinner) {
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(context, R.array.gender_arrays, R.layout.spinner_team_add_item);
        genderAdapter .setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(genderAdapter);
    }

    public static void initYearSpinner(Context context, Spinner spinner) {
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(context, R.array.year_arrays, R.layout.spinner_team_add_item);
        yearAdapter.setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(yearAdapter);
    }

    public static void initMonthSpinner(Context context, Spinner spinner) {
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(context, R.array.month_arrays, R.layout.spinner_team_add_item);
        monthAdapter .setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(monthAdapter);
    }

    public static void initDaySpinner(Context context, Spinner spinner) {
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(context, R.array.day_arrays, R.layout.spinner_team_add_item);
        dayAdapter .setDropDownViewResource(R.layout.spinner_team_add_dropdown_item);
        spinner.setAdapter(dayAdapter );
    }

    public static void selectByString(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            String selectedValue = spinner.getAdapter().getItem(i).toString();
            if (StringUtils.equals(selectedValue, value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static void selectByInt(Spinner spinner, int value) {
        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            int selectedKey = ((PlaygroundDto) spinner.getAdapter().getItem(i)).getId();
            if (selectedKey == value) {
                spinner.setSelection(i);
                break;
            }
        }
    }

}
