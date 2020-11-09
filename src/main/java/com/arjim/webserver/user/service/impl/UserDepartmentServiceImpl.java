package com.arjim.webserver.user.service.impl;

import com.arjim.server.session.impl.SessionManagerImpl;
import com.arjim.webserver.user.dao.UserDepartmentDao;
import com.arjim.webserver.user.model.*;
import com.arjim.webserver.user.service.UserDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userDepartmentServiceImpl")
public class UserDepartmentServiceImpl implements UserDepartmentService {
	@Autowired
	private UserDepartmentDao userDepartmentDao;
	@Autowired
	private SessionManagerImpl sessionManager;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public UserDepartmentEntity queryObject(Long id) {
		return userDepartmentDao.queryObject(id);
	}

	@Override
	public List<UserDepartmentEntity> queryList(Map<String, Object> map) {
		return userDepartmentDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return userDepartmentDao.queryTotal(map);
	}

	@Override
	public void save(UserDepartmentEntity userDepartment) {
		userDepartmentDao.save(userDepartment);
	}

	@Override
	public int update(UserDepartmentEntity userDepartment) {
		return userDepartmentDao.update(userDepartment);
	}

	@Override
	public int delete(Long id) {
		return userDepartmentDao.delete(id);
	}

	@Override
	public int deleteBatch(Long[] ids) {
		return userDepartmentDao.deleteBatch(ids);
	}

	@Override
	public List<ImFriendUserInfo> queryGroupAndUser() {
		List<ImFriendUserInfo> friendgroup = userDepartmentDao.queryGroupAndUser();
		for (ImFriendUserInfo bean : friendgroup) {
			boolean exist = sessionManager.exist(bean.getId().toString());
			if (exist) {
				bean.setStatus("online");
			}
//			List<ImFriendUserInfoData> friends = fg.getList();
//			if (friends != null && friends.size() > 0) {
//				for (ImFriendUserInfoData fr : friends) {
//					boolean exist = sessionManager.exist(fr.getId().toString());
//					if (exist)
//						fr.setStatus("online");
//				}
//			}
		}
		return friendgroup;
	}

	@Override
	public List<ImGroupUserData> queryOffice() {
		return userDepartmentDao.queryOffice();
	}

	@Override
	public List<ImFriendUserInfoData> findAllUserByOffice(ImGroupUserData group) {
		return userDepartmentDao.findAllUserByOffice(group);
	}
	
	@Override
	public List<ImFriendUserInfoData> findUserByOffice(ImGroupUserData group) {
	//	List<ImFriendUserInfoData> userList = new ArrayList<>();
		List<ImFriendUserInfoData> userList = userDepartmentDao.findUserByOffice(group);
//		for (ImFriendUserInfoData imFriendUserInfoData:userListPo ){
//			final Object object = redisTemplate.opsForValue().get("USER-ONLINE-FLAG:" + imFriendUserInfoData.getId());
//			if(object != null){
//				imFriendUserInfoData.setStatus("online");
//			}
//			userList.add(imFriendUserInfoData);
//		}
		return userList;
	}

	@Override
	public int updateSign(ImFriendUserInfo imFriendUserInfo) {
		return userDepartmentDao.updateSign(imFriendUserInfo);
	}

	@Override
	public List<ImGroupUserData> getGroup(UserAccountEntity loginUser) {
		return userDepartmentDao.getGroup(loginUser);
	}

	@Override
	public List<ImOfficeUser> queryOfficeUser() {
		return userDepartmentDao.queryOfficeUser();
	}

	@Override
	public ImGroupUserData findGroupById(String id) {
		return userDepartmentDao.findGroupById(id);
	}

}
