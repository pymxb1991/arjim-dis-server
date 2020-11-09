package com.arjim.webserver.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class ImOfficeUserInfo implements Serializable {

	private static final long serialVersionUID = -1352549127908529764L;
	private String userId;// 好友ID
	private String username;// 好友昵称
	private String avatar;// 好友头像
	private String sign;// 签名
	private String status;

	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date loginDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
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

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
}
