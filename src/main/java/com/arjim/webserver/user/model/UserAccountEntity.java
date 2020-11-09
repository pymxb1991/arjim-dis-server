package com.arjim.webserver.user.model;

import com.arjim.webserver.base.model.BaseModel;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 用户帐号 （用户可能属于多个部门）
 * 
 * @author maoxb
 * @description
 * @date 2020-11-27 09:38:52
 */
public class UserAccountEntity extends BaseModel {
	private static final long serialVersionUID = 1L;
	// 帐号
	private String account;
	// 密码
	private String password;
	// 禁用状态（0 启用 1 禁用）
	private Integer disablestate;
	// 是否删除（0未删除1已删除）
	private Integer isdel;
	// 用户基本信息
	// 部门
	private String deptId;
	private String deptName;
	private String salt;
	private String name;
	private Integer userType;
	private String userCode;
	private Date loginDateTime;
	private String loginIP;
	private String phone;
	private String mobile;
	private String email;
	private String profilephoto;
	private String address;
	private Object roles;
	private String status;
	private Integer isEnable;
	// 签名
	private String signature;
	/**
	 * 设置：帐号
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取：帐号
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置：禁用状态（0 启用 1 禁用）
	 */
	public void setDisablestate(Integer disablestate) {
		this.disablestate = disablestate;
	}

	/**
	 * 获取：禁用状态（0 启用 1 禁用）
	 */
	public Integer getDisablestate() {
		return disablestate;
	}

	/**
	 * 设置：是否删除（0未删除1已删除）
	 */
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	/**
	 * 获取：是否删除（0未删除1已删除）
	 */
	public Integer getIsdel() {
		return isdel;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Date getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(Date loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfilephoto() {
		return profilephoto;
	}

	public void setProfilephoto(String profilephoto) {
		this.profilephoto = profilephoto;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Object getRoles() {
		return roles;
	}

	public void setRoles(Object roles) {
		this.roles = roles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
