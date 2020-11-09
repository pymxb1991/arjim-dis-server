package com.arjim.webserver.user.model;

import org.apache.commons.lang.StringUtils;

public class ImGroupUserData {
	public String id;// 分组ID
	public String groupname;// 分组Name
	public String avatar;//
	public String members;// 分组用户数
	public String groupowner;// 群主ID(用户ID)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getAvatar() {
		return StringUtils.isNotEmpty(avatar) ? avatar : "layui/images/0.jpg";
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
