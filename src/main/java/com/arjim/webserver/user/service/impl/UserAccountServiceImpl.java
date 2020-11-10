package com.arjim.webserver.user.service.impl;

import com.arjim.util.ImUtils;
import com.arjim.webserver.user.dao.UserAccountDao;
import com.arjim.webserver.user.model.*;
import com.arjim.webserver.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("userAccountServiceImpl")
public class UserAccountServiceImpl implements UserAccountService {
	public static ConcurrentHashMap<String, UserAccountEntity> loginUser = new ConcurrentHashMap<String, UserAccountEntity>();
	@Autowired
	private UserAccountDao userAccountDao;
//	@Autowired
//	private UserInfoService userInfoServiceImpl;

	@Override
	public UserAccountEntity queryObject(Long id) {
		return userAccountDao.queryObject(id);
	}

	@Override
	public UserAccountEntity selectById(String userId) {
		return userAccountDao.queryObject(userId);
	}

	@Override
	public List<UserAccountEntity> queryList(Map<String, Object> map) {
		return userAccountDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return userAccountDao.queryTotal(map);
	}

	@Override
	public void save(UserAccountEntity userAccount) {
		// 判断用户是否已注册
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("account", userAccount.getAccount());
//		UserAccountEntity user = queryObjectByAccount(map);
//		if (user == null || user.getId() < 1) {
//			userAccountDao.save(userAccount);
//			if (userAccount != null && userAccount.getId() != null) {
//				// 保存基本信息
//				UserInfoEntity userInfo = userAccount.getUserInfo();
//				userInfo.setUid(userAccount.getId());
//				userInfoServiceImpl.save(userInfo);
//			}
//		}
	}

	@Override
	public int update(UserAccountEntity userAccount) {
		return userAccountDao.update(userAccount);
	}

	@Override
	public int delete(Long id) {
		return userAccountDao.delete(id);
	}

	@Override
	public int deleteBatch(Long[] ids) {
		return userAccountDao.deleteBatch(ids);
	}

	@Override
	public UserAccountEntity queryObjectByAccount(Map<String, Object> map) {
		return userAccountDao.queryObjectByAccount(map);
	}

	@Override
	public UserAccountEntity validateUser(Map<String, Object> map) {
		UserAccountEntity user = queryObjectByAccount(map);
		if (user != null) {
			try {
				String file1 = user.getProfilephoto();
				String hostAddress = ImUtils.getHostAddress();
				String serverPort = ImUtils.getServerPort(false);
				String fileUrl = "http://"+ hostAddress+":"+serverPort + "/"+file1;
				user.setProfilephoto(fileUrl);
				return user;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public void addLoginUser(UserAccountEntity userAccount) {
		loginUser.put(userAccount.getId(), userAccount);
	}

	@Override
	public void removeLoginUser(String userId) {
		loginUser.remove(userId);
	}

	@Override
	public UserAccountEntity getLoginUser(String userId) {
		UserAccountEntity user = loginUser.get(userId);
		return user;
	}

	@Override
	public List<ImFriendUserInfoData> getGroupUser(ImFriendUserData imFriendUserData) {
		List<ImFriendUserInfoData> groupUsers = userAccountDao.getGroupUser(imFriendUserData);
		groupUsers.forEach(groupUser->{
			String file1 = groupUser.getAvatar();
			String hostAddress = null;
			String serverPort = null;
			try {
				hostAddress = ImUtils.getHostAddress();
				serverPort = ImUtils.getServerPort(false);
				String fileUrl = "http://"+ hostAddress+":"+serverPort + "/"+file1;
				groupUser.setAvatar(fileUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return groupUsers;
	}


}
