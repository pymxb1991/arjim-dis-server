package com.arjim.webserver.user.model;

public class ImGroupUserListData extends ImFriendUserData{

    public String avatar;//
    public String members;// 分组用户数
    public String groupowner;// 群主ID(用户ID)

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getGroupowner() {
        return groupowner;
    }

    public void setGroupowner(String groupowner) {
        this.groupowner = groupowner;
    }
}
