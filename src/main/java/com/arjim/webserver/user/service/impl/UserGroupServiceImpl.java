package com.arjim.webserver.user.service.impl;

import com.arjim.webserver.user.dao.UserGroupDao;
import com.arjim.webserver.user.model.UserAccountEntity;
import com.arjim.webserver.user.model.UserGroupEntity;
import com.arjim.webserver.user.service.UserAccountService;
import com.arjim.webserver.user.service.UserGroupService;
import com.arjim.webserver.user.service.UserRelationShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userGroupServiceImpl")
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupDao userGroupDao;

	@Autowired
	private UserRelationShipService userRelationShipServiceImpl;

	@Autowired
	private UserAccountService userAccountService;

	@Override
	public UserGroupEntity selectById(String id) {
		return userGroupDao.selectById(id);
	}

	@Override
	public void save(UserGroupEntity userGroup) {
		userGroupDao.save(userGroup);
	}

	/**
	 *  更新群主信息，返回新的群主；
	 * @param userId
	 * @param groupId
	 * @return
	 */
	@Override
	public String updateGroupOwenId(String userId, String groupId) {
		String data = null;
		//判断当前用户是不是群主
		UserGroupEntity userGroup = userGroupDao.selectById(groupId);
		UserAccountEntity user = null;
		if (userGroup != null){
			String groupOwnerId = userGroup.getGroupOwnerId();
			//如果是群主，则找出群中其它用户，随机指定一人为群主；
			if (userId.equals(groupOwnerId)){
				List<String> groupUserList = userRelationShipServiceImpl.selectByGroupId(groupId);
				groupUserList.removeIf(item -> userId.equals(item));
				int index = (int) (Math.random()* groupUserList.size());
				String groupOwenRadom = groupUserList.get(index);
				userGroup.setGroupOwnerId(groupOwenRadom);//设置群主
				user = userAccountService.selectById(groupOwenRadom);
				data = user.getName();
				userGroupDao.update(userGroup);
			}else{
				final int i = userRelationShipServiceImpl.deleteByGroupIdAndUserId(userId, groupId);
				data = i+"";
			}
		}
		return  data;
	}


}
