package com.arjim.webserver.user.service.impl;

import com.arjim.webserver.user.dao.UserGroupDao;
import com.arjim.webserver.user.model.UserGroupEntity;
import com.arjim.webserver.user.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userGroupServiceImpl")
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupDao userGroupDao;

	@Override
	public UserGroupEntity selectById(String id) {
		return userGroupDao.selectById(id);
	}

	@Override
	public void save(UserGroupEntity userGroup) {
		userGroupDao.save(userGroup);
	}
}
