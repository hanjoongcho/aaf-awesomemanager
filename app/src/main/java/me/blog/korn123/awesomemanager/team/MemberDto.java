package me.blog.korn123.awesomemanager.team;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class MemberDto {
    private int memberId;
    private int managementId;
    private int teamId;
    private String name;
    private String birth;
    private String school;
    private String grade;
    private String gender;
    private String alarmCellphone;
    private int tempNo;
    private boolean isAttendance = true;
    private boolean enableSpinner;
    private int originalId;

    public MemberDto(int memberId, int managementId, int teamId, String name, String birth, String school, String grade, String gender, String alarmCellphone, int tempNo, boolean isAttendance, boolean enableSpinner) {
        this.memberId = memberId;
        this.managementId = managementId;
        this.teamId = teamId;
        this.name = name;
        this.birth = birth;
        this.school = school;
        this.grade = grade;
        this.gender = gender;
        this.alarmCellphone = alarmCellphone;
        this.tempNo = tempNo;
        this.isAttendance = isAttendance;
        this.enableSpinner = enableSpinner;
    }

    public MemberDto(int memberId, int managementId, int teamId, String name, String birth, String school, String grade, String gender, String alarmCellphone, int originalId) {
        this.memberId = memberId;
        this.managementId = managementId;
        this.teamId = teamId;
        this.name = name;
        this.birth = birth;
        this.school = school;
        this.grade = grade;
        this.gender = gender;
        this.alarmCellphone = alarmCellphone;
        this.originalId = originalId;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public int getManagementId() {
        return managementId;
    }

    public void setManagementId(int managementId) {
        this.managementId = managementId;
    }

    public String getAlarmCellphone() {
//        if (StringUtils.isEmpty(alarmCellphone)) {
//            return "미등록";
//        }
        return alarmCellphone;
    }

    public void setAlarmCellphone(String alarmCellphone) {
        this.alarmCellphone = alarmCellphone;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getTempNo() {
        return tempNo;
    }

    public void setTempNo(int tempNo) {
        this.tempNo = tempNo;
    }

    public boolean isAttendance() {
        return isAttendance;
    }

    public void setAttendance(boolean attendance) {
        isAttendance = attendance;
    }

    public boolean isEnableSpinner() {
        return enableSpinner;
    }

    public void setEnableSpinner(boolean enableSpinner) {
        this.enableSpinner = enableSpinner;
    }

    @Override
    public String toString() {
        return name;
    }
}
