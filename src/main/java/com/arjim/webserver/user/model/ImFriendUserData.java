package com.arjim.webserver.user.model;

import java.util.List;

public class ImFriendUserData {
	public String id;// 分组ID
	public String groupname;// 好友分组Name
	public List<ImFriendUserInfoData> list;// 分组好友列表

	public ImFriendUserData(String id, String groupname, List<ImFriendUserInfoData> list) {
		super();
		this.id = id;
		this.groupname = groupname;
		this.list = list;
	}

	public ImFriendUserData() {
		super();
	}

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

	public List<ImFriendUserInfoData> getList() {
		return list;
	}

	public void setList(List<ImFriendUserInfoData> list) {
		this.list = list;
	}

}
