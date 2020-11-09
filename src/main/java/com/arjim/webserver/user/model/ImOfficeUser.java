package com.arjim.webserver.user.model;

import java.io.Serializable;
import java.util.List;

public class ImOfficeUser  implements Serializable {

	private static final long serialVersionUID = -8470462598419850340L;
	public String officeParentId;//父ID
	public String officeId;//父ID
	public String officeType;//部门类型
	public String officeName;// 好友分组Name
	public List<ImOfficeUserInfo> userList;// 分组好友列表

	public String getOfficeParentId() {
		return officeParentId;
	}

	public void setOfficeParentId(String officeParentId) {
		this.officeParentId = officeParentId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public List<ImOfficeUserInfo> getUserList() {
		return userList;
	}

	public void setUserList(List<ImOfficeUserInfo> userList) {
		this.userList = userList;
	}
}
