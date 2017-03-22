package me.blog.korn123.awesomemanager.team;

/**
 * Created by CHO HANJOONG on 2016-10-17.
 */

public class BasicInformationDto {
    private int id;
    private int managementId;
    private String category;
    private String basicInformationValue;

    public BasicInformationDto(int managementId, String category, String basicInformationValue) {
        this(0, managementId, category, basicInformationValue);
    }

    public BasicInformationDto(int id, int managementId, String category, String basicInformationValue) {
        this.id = id;
        this.managementId = managementId;
        this.category = category;
        this.basicInformationValue = basicInformationValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagementId() {
        return managementId;
    }

    public void setManagementId(int managementId) {
        this.managementId = managementId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBasicInformationValue() {
        return basicInformationValue;
    }

    public void setBasicInformationValue(String basicInformationValue) {
        this.basicInformationValue = basicInformationValue;
    }
}
