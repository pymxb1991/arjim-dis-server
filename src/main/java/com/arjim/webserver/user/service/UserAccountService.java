package com.arjim.webserver.user.service;

import com.arjim.webserver.user.model.ImFriendUserData;
import com.arjim.webserver.user.model.ImFriendUserInfoData;
import com.arjim.webserver.user.model.UserAccountEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户帐号
 * 
 * @author arjim
 * @description
 * @date 2017-11-27 09:38:52
 */
public interface UserAccountService {

	UserAccountEntity queryObject(Long id);

	UserAccountEntity queryObjectByAccount(Map<String, Object> map);

	UserAccountEntity validateUser(Map<String, Object> map);

	List<UserAccountEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(UserAccountEntity userAccount);

	int update(UserAccountEntity userAccount);

	int delete(Long id);

	int deleteBatch(Long[] ids);
	
	void addLoginUser(UserAccountEntity userAccount);

	void removeLoginUser(String userId);
	
	UserAccountEntity getLoginUser(String userId);

	List<ImFriendUserInfoData> getGroupUser(ImFriendUserData imFriendUserData);

	UserAccountEntity selectById(String userId);
}
