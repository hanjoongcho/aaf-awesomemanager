package me.blog.korn123.constant;

import android.os.Environment;

/**
 * Created by CHO HANJOONG on 2016-07-23.
 */
public class Constant {
    final static public String WORKING_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AAFactory/";
    final static public String LOG_DIRECTORY = WORKING_DIRECTORY + "log/";

    final static public int MASTER_DEVICE_MANAGEMENT_ID  = 1;

    final static public String CATEGORY_COACH  = "coach";
    final static public String CATEGORY_SCHOOL = "school";
    final static public String CATEGORY_GRADE  = "grade";
    final static public String CATEGORY_BRANCH = "branch";
    final static public String CATEGORY_EVENT  = "event";

    final static public String VERIFY_MESSAGE_COACH  = "교사를 선택하세요.";
    final static public String VERIFY_MESSAGE_SCHOOL  = "학교를 선택하세요.";
    final static public String VERIFY_MESSAGE_GRADE  = "학년을 선택하세요.";
    final static public String VERIFY_MESSAGE_BRANCH  = "소속지점을 선택하세요.";
    final static public String VERIFY_MESSAGE_PLAYGROUND  = "수업장소를 선택하세요.";
    final static public String VERIFY_MESSAGE_EVENT  = "종목을 선택하세요.";
    final static public String VERIFY_MESSAGE_GENDER  = "성별을 선택하세요.";
    final static public String VERIFY_MESSAGE_MAXIMUM_MEMBER  = "최대인원을 선택하세요.";
    final static public String VERIFY_MESSAGE_TEAM_NAME  = "팀 이름을 입력하세요.";
    final static public String VERIFY_MESSAGE_MEMBER_NAME  = "팀원 이름을 입력하세요.";
    final static public String VERIFY_MESSAGE_MEMBER_BIRTHDAY  = "팀원 이름을 입력하세요.";
    final static public String VERIFY_MESSAGE_ALARM_CELLPHONE  = "출결정보 알림용 핸드폰 번호를 입력하세요.";

    final static public String AAF_AWESOME_MANAGER_DATABASE_PATH  = "/data/data/me.blog.korn123.awesomemanager/databases/";
    final static public String AAF_AWESOME_MANAGER_DATABASE_FILENAME  = "AAF_AWESOME_MANAGER.DB";

    final static public String DATA_FILE_BASIC_INFORMATION = "BASIC_INFORMATION.aaf";
    final static public String DATA_FILE_PLAYGROUND = "PLAYGROUND.aaf";
    final static public String DATA_FILE_TEAM = "TEAM.aaf";
    final static public String DATA_FILE_MEMBER = "MEMBER.aaf";
    final static public String DATA_FILE_ATTENDANCE = "ATTENDANCE.aaf";
}
