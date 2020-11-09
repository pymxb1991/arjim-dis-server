package com.arjim.webserver.user.service;

import com.arjim.webserver.user.model.UserRelationShipEntity;

import java.util.List;

/**
 * 用户帐号
 * 
 * @author maoxb
 * @description
 * @date 2017-11-27 09:38:52
 */
public interface UserRelationShipService {

	UserRelationShipEntity queryObject(String id);

	void  deleteRelByGroupId(String id);

	void save(UserRelationShipEntity userRelationShip);

	List<String> selectByGroupId(String id);
}
