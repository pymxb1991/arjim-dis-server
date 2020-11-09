package com.arjim.webserver.user.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ImFriendUserInfo {
	private String id;// 好友ID
	private String username;// 好友昵称
	private String avatar;// 好友头像
	private String sign;// 签名
	private String officeId;// 分组ID
	private String groupname;// 好友分组Name
	private Date loginDate;//最后登录时间
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return StringUtils.isNotEmpty(avatar) ? avatar : "layui/images/0.jpg";// avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
}
