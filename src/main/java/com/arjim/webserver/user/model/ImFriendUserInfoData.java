package com.arjim.webserver.user.model;

import org.apache.commons.lang.StringUtils;

public class ImFriendUserInfoData {
	private String id;// 好友ID
	private String username;// 好友昵称
	private String avatar;// 好友头像
	private String sign;// 签名
	private String groupname;
	private String status;
	private Long sipUserName; // SIP帐号
	private String sipPassword; // SIP密码

	public Long getSipUserName() {
		return sipUserName;
	}

	public void setSipUserName(Long sipUserName) {
		this.sipUserName = sipUserName;
	}

	public String getSipPassword() {
		return sipPassword;
	}

	public void setSipPassword(String sipPassword) {
		this.sipPassword = sipPassword;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
