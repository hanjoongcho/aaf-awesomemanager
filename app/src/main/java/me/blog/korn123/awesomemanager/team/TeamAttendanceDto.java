package me.blog.korn123.awesomemanager.team;

/**
 * Created by CHO HANJOONG on 2016-10-13.
 */

public class TeamAttendanceDto {
    private int attendanceId;
    private int managementId;
    private int memberId;
    private int teamId;
    private String registerDate;
    private int flag;
    private int originalId;
    private String teamName;
    private String memberName;
    private boolean enableSpinner;
    private int tempNo;

    public TeamAttendanceDto(int attendanceId, String registerDate, int flag) {
        this(attendanceId, -1, registerDate, flag);
    }

    public TeamAttendanceDto(int attendanceId, int teamId, String registerDate, int flag) {
        this.attendanceId = attendanceId;
        this.teamId = teamId;
        this.registerDate = registerDate;
        this.flag = flag;
    }

    public TeamAttendanceDto(int attendanceId, int managementId, int memberId, int teamId, String registerDate, int flag, int originalId, String teamName, String memberName) {
        this.attendanceId = attendanceId;
        this.managementId = managementId;
        this.memberId = memberId;
        this.teamId = teamId;
        this.registerDate = registerDate;
        this.flag = flag;
        this.originalId = originalId;
        this.teamName = teamName;
        this.memberName = memberName;
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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTempNo() {
        return tempNo;
    }

    public void setTempNo(int tempNo) {
        this.tempNo = tempNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public boolean isEnableSpinner() {
        return enableSpinner;
    }

    public void setEnableSpinner(boolean enableSpinner) {
        this.enableSpinner = enableSpinner;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
