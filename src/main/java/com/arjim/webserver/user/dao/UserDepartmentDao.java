package com.arjim.webserver.user.dao;

import com.arjim.webserver.base.dao.BaseDao;
import com.arjim.webserver.user.model.*;

import java.util.List;

/**
 * 部门
 * 
 * @author arjim
 * @description
 * @date 2020-11-27 09:38:52
 */
public interface UserDepartmentDao extends BaseDao<UserDepartmentEntity> {

	//new
	public List<ImGroupUserData> queryOffice();

	public List<ImFriendUserInfo> queryGroupAndUser();
	public List<ImFriendUserInfoData> findUserByOffice(ImGroupUserData group);
	public List<ImFriendUserInfoData> findAllUserByOffice(ImGroupUserData group);
	public int updateSign(ImFriendUserInfo imFriendUserInfo);
	public List<ImGroupUserData> getGroup(UserAccountEntity loginUser);
	public List<ImOfficeUser> queryOfficeUser();
	public ImGroupUserData findGroupById(String id);
}
