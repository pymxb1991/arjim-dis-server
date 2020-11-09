package com.arjim.webserver.user.model;

import java.util.List;

public class ImGroupUserResult {
	private ImFriendUserInfoData mine;
	private List<ImFriendUserData> friend;	//群组下frien
	private List<ImGroupUserData> group;
	
	public List<ImGroupUserData> getGroup() {
		return group;
	}
	public void setGroup(List<ImGroupUserData> group) {
		this.group = group;
	}
	public ImFriendUserInfoData getMine() {
		return mine;
	}
	public void setMine(ImFriendUserInfoData mine) {
		this.mine = mine;
	}
	public List<ImFriendUserData> getFriend() {
		return friend;
	}
	public void setFriend(List<ImFriendUserData> friend) {
		this.friend = friend;
	}
	
	public ImGroupUserResult(ImFriendUserInfoData mine, List<ImFriendUserData> friend, List<ImGroupUserData> group) {
		super();
		this.mine = mine;
		this.friend = friend;
		this.group = group;
	}
	public ImGroupUserResult() {
		super();
	}
}
