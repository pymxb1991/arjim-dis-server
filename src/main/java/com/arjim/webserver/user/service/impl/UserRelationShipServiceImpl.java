package com.arjim.webserver.user.service.impl;

import com.arjim.webserver.user.dao.UserRelationShipDao;
import com.arjim.webserver.user.model.UserRelationShipEntity;
import com.arjim.webserver.user.service.UserRelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("userRelationShipServiceImpl")
public class UserRelationShipServiceImpl implements UserRelationShipService {

	@Autowired
	private UserRelationShipDao userRelationShipDao;

	@Override
	public UserRelationShipEntity queryObject(String id) {
		return userRelationShipDao.queryObject(id);
	}

	@Override
	public void deleteRelByGroupId(String id) {
		userRelationShipDao.deleteRelByGroupId(id);
	}

	@Override
	public void save(UserRelationShipEntity ccmUserRelationship) {
		ccmUserRelationship.setId(UUID.randomUUID().toString());
		ccmUserRelationship.setCreateDate(new Date());
		ccmUserRelationship.setUpdateDate(new Date());
		ccmUserRelationship.setDelFlag("0");
		userRelationShipDao.save(ccmUserRelationship);
	}

	@Override
	public List<String> selectByGroupId(String id) {
		return userRelationShipDao.findUserListByGroupId(id);
	}
}
