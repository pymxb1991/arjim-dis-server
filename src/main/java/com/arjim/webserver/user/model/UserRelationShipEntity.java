/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjim.webserver.user.model;


import java.io.Serializable;
import java.util.Date;

/**
 * 用户关系Entity
 * @author mao
 * @version 2018-03-08
 */
public class UserRelationShipEntity implements Serializable {

	private static final long serialVersionUID = 30155447570394328L;
	private UserAccountEntity user;		// 用户A
	private String groupId;		// 分组id
	private String id;
	private String userId;
	private String relationType;		// 用户AB的关系
	
	private UserGroupEntity userGroup;		// 分组
	
//	private List<String> userIdList;
	private String remarks;
	private String createBy;
	private Date createDate;
	private String updateBy;
	private Date updateDate;
	private String delFlag;

	public UserAccountEntity getUser() {
		return user;
	}

	public void setUser(UserAccountEntity user) {
		this.user = user;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public UserGroupEntity getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroupEntity userGroup) {
		this.userGroup = userGroup;
	}

//	public List<String> getUserIdList() {
//		return userIdList;
//	}
//
//	public void setUserIdList(List<String> userIdList) {
//		this.userIdList = userIdList;
//	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}