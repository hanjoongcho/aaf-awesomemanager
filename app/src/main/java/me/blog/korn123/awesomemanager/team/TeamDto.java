package me.blog.korn123.awesomemanager.team;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by CHO HANJOONG on 2016-10-04.
 */

public class TeamDto {
    private int id;
    private int managementId;
    private int playgroundId;
    private String name;
    private String coach;
    private String school;
    private String grade;
    private String branch;
    private String event;
    private String gender;
    private int maximum;
    private String etcInfo;
    private int originalId;

    TeamDto() {}

    public TeamDto(int id, int managementId, int playgroundId, String name, String coach, String school, String grade, String branch, String event, String gender, int maximum, String etcInfo, int originalId) {
        this.id = id;
        this.managementId = managementId;
        this.playgroundId = playgroundId;
        this.name = name;
        this.coach = coach;
        this.school = school;
        this.grade = grade;
        this.branch = branch;
        this.event = event;
        this.gender = gender;
        this.maximum = maximum;
        this.etcInfo = etcInfo;
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

    public int getPlaygroundId() {
        return playgroundId;
    }

    public void setPlaygroundId(int playgroundId) {
        this.playgroundId = playgroundId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public String getEtcInfo() {
        if (StringUtils.isEmpty(etcInfo)) {
            return "추가정보 없음";
        }
        return etcInfo;
    }

    public void setEtcInfo(String etcInfo) {
        this.etcInfo = etcInfo;
    }

}
