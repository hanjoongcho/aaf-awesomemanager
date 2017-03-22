package me.blog.korn123.awesomemanager.team;

/**
 * Created by CHO HANJOONG on 2016-10-25.
 */

public class CustomMessageDto {
    private int id;
    private String customMessageName;
    private String customMessageBody;

    public CustomMessageDto(int id, String customMessageName, String customMessageBody) {
        this.id = id;
        this.customMessageName = customMessageName;
        this.customMessageBody = customMessageBody;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomMessageName() {
        return customMessageName;
    }

    public void setCustomMessageName(String customMessageName) {
        this.customMessageName = customMessageName;
    }

    public String getCustomMessageBody() {
        return customMessageBody;
    }

    public void setCustomMessageBody(String customMessageBody) {
        this.customMessageBody = customMessageBody;
    }

    @Override
    public String toString() {
        return customMessageName;
    }
}
