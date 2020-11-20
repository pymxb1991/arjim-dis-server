package com.arjim.webserver.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjim.webserver.user.dao.UserMessageDao;
import com.arjim.webserver.user.model.UserMessageEntity;
import com.arjim.webserver.user.service.UserMessageService;

@Service("userMessageServiceImpl")
public class UserMessageServiceImpl implements UserMessageService {
	@Autowired
	private UserMessageDao userMessageDao;

	@Override
	public UserMessageEntity queryObject(Long id) {
		return userMessageDao.queryObject(id);
	}

	@Override
	public List<UserMessageEntity> queryList(Map<String, Object> map) {
		return userMessageDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return userMessageDao.queryTotal(map);
	}

	@Override
	public void save(UserMessageEntity userMessage) {
		userMessageDao.save(userMessage);
	}

	@Override
	public int update(UserMessageEntity userMessage) {
		return userMessageDao.update(userMessage);
	}

	@Override
	public int delete(Long id) {
		return userMessageDao.delete(id);
	}

	@Override
	public int deleteBatch(Long[] ids) {
		return userMessageDao.deleteBatch(ids);
	}

	@Override
	public List<UserMessageEntity> getHistoryMessageList(Map<String, Object> map) {
		return userMessageDao.getHistoryMessageList(map);
	}

	@Override
	public int getHistoryMessageCount(Map<String, Object> map) {
		return userMessageDao.getHistoryMessageCount(map);
	}

	@Override
	public List<UserMessageEntity> getOfflineMessageList(Map<String, Object> map) {
		List<UserMessageEntity> result = userMessageDao.getOfflineMessageList(map);
		// 更新状态为已读状态
		userMessageDao.updatemsgstate(map);
		return result;
	}

	@Override
	public List<UserMessageEntity> getGroupOfflinemsg(Map<String, Object> map) {
		List<String> groupIds = userMessageDao.selectUserGroups(map);
		List<UserMessageEntity> result = new ArrayList<>();
		if(groupIds != null && groupIds.size()> 0) {
			for (String groupId : groupIds){
				map.put("groupid", groupId);
				map.put("isread", 0);
				List<UserMessageEntity> groupHistoryMessageList = userMessageDao.getGroupHistoryMessageList(map);
				if(groupHistoryMessageList!= null && groupHistoryMessageList.size()> 0)
					result.addAll(groupHistoryMessageList);
			}

		}
		return result;
	}

	@Override
	public List<UserMessageEntity> getGroupHistoryMessageList(Map<String, Object> map) {
		return userMessageDao.getGroupHistoryMessageList(map);
	}

	@Override
	public int getGroupHistoryMessageCount(Map<String, Object> map) {
		return userMessageDao.getGroupHistoryMessageCount(map);
	}

}
