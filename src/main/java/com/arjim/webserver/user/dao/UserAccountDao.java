package com.arjim.webserver.user.dao;

import com.arjim.webserver.base.dao.BaseDao;
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
 * @date 2020-11-27 09:38:52
 */
public interface UserAccountDao extends BaseDao<UserAccountEntity> {
	public UserAccountEntity queryObjectByAccount(Map<String, Object> map);
	public List<ImFriendUserInfoData> getGroupUser(ImFriendUserData imFriendUserData);
}
