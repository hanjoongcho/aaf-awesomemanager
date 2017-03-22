package me.blog.korn123.awesomemanager.playground;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by CHO HANJOONG on 2016-10-05.
 */

public class PlaygroundDto {

    private int id;
    private int managementId;
    private String name;
    private String branch;
    private String kind;
    private String address;
    private String etcInfo;
    private int originalId;

    PlaygroundDto() {}

    public PlaygroundDto(int id, int managementId, String name, String branch, String kind, String address, String etcInfo, int originalId) {
        this.id = id;
        this.managementId = managementId;
        this.name = name;
        this.branch = branch;
        this.kind = kind;
        this.address = address;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }
}

