package com.arjim.webserver.user.dao;

import com.arjim.webserver.base.dao.BaseDao;
import com.arjim.webserver.user.model.UserMessageEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author arjim
 * @description
 * @date 2020-11-23 10:47:47
 */
public interface UserMessageDao extends BaseDao<UserMessageEntity> {
	/**
	 * 获取历史记录
	 * 
	 * @param map
	 * @return
	 */
	List<UserMessageEntity> getHistoryMessageList(Map<String, Object> map);
	
	/**
	 * 获取组历史记录
	 * 
	 * @param map
	 * @return
	 */
	List<UserMessageEntity> getGroupHistoryMessageList(Map<String, Object> map);

	/**
	 * 获取历史记录总条数
	 * 
	 * @param map
	 * @return
	 */
	int getHistoryMessageCount(Map<String, Object> map);
	
	/**
	 * 获取组历史记录总条数
	 * 
	 * @param map
	 * @return
	 */
	int getGroupHistoryMessageCount(Map<String, Object> map);

	/**
	 * 获取离线消息
	 * 
	 * @param map
	 * @return
	 */
	List<UserMessageEntity> getOfflineMessageList(Map<String, Object> map);

	/**
	 * 修改消息为已读状态
	 * 
	 * @param map
	 * @return
	 */
	int updatemsgstate(Map<String, Object> map);

}
