package com.arjim.webserver.user.service;

import com.arjim.webserver.user.model.*;

import java.util.List;
import java.util.Map;

/**
 * 部门
 * 
 * @author arjim
 * @description
 * @date 2020-11-27 09:38:52
 */
public interface UserDepartmentService {

	UserDepartmentEntity queryObject(Long id);

	List<UserDepartmentEntity> queryList(Map<String, Object> map);

	List<ImFriendUserInfo> queryGroupAndUser();
	
	List<ImGroupUserData> queryOffice();

	int queryTotal(Map<String, Object> map);

	void save(UserDepartmentEntity userDepartment);

	int update(UserDepartmentEntity userDepartment);

	int delete(Long id);

	int deleteBatch(Long[] ids);
	
	List<ImFriendUserInfoData> findAllUserByOffice(ImGroupUserData group);
	
	List<ImFriendUserInfoData> findUserByOffice(ImGroupUserData group);
	
	int updateSign(ImFriendUserInfo imFriendUserInfo);
	
	List<ImGroupUserData> getGroup(UserAccountEntity loginUser);

	public List<ImOfficeUser> queryOfficeUser();

	public ImGroupUserData findGroupById(String id);
}
