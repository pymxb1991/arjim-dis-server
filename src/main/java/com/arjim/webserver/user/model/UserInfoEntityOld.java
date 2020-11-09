package com.arjim.webserver.user.model;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.arjim.webserver.base.model.BaseModel;

/**
 * 用户信息表
 * 
 * @author arjim
 * @description
 * @date 2020-11-27 09:38:52
 */
public class UserInfoEntityOld extends BaseModel {
	private static final long serialVersionUID = 1L;

	// 用户id
	private String uid;
	// 部门
	private String deptid;
	// 姓名
	private String name;
	// 昵称
	private String nickname;
	// 性别（0女 1男）
	private Integer sex;
	// 生日
	private String birthday;
	// 身份证
	private String cardid;
	// 签名
	private String signature;
	// 毕业院校
	private String school;
	// 学历
	private Integer education;
	// 现居住地址
	private String address;
	// 联系电话
	private String phone;
	// 手机
	private String mobile;
	// 邮箱
	private String email;
	// 备注
	private String remark;
	// 个人头像
	private String profilephoto;
	// SIP帐号
	private Long sipUserName;
	// SIP密码
	private String sipPassword;

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

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置：用户id
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * 获取：用户id
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * 设置：部门
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	/**
	 * 获取：部门
	 */
	public String getDeptid() {
		return deptid;
	}

	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 获取：昵称
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * 设置：性别（0女 1男）
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * 获取：性别（0女 1男）
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * 设置：生日
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取：生日
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * 设置：身份证
	 */
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	/**
	 * 获取：身份证
	 */
	public String getCardid() {
		return cardid;
	}

	/**
	 * 设置：签名
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * 获取：签名
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * 设置：毕业院校
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * 获取：毕业院校
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * 设置：学历
	 */
	public void setEducation(Integer education) {
		this.education = education;
	}

	/**
	 * 获取：学历
	 */
	public Integer getEducation() {
		return education;
	}

	/**
	 * 设置：现居住地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取：现居住地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：个人头像
	 */
	public void setProfilephoto(String profilephoto) {
		this.profilephoto = profilephoto;
	}

	/**
	 * 获取：个人头像
	 */
	public String getProfilephoto() {
		return StringUtils.isNotEmpty(profilephoto) ? profilephoto : "layui/images/0.jpg";// profilephoto;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
