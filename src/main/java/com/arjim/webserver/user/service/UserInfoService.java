package com.arjim.webserver.user.service;

import com.arjim.webserver.user.model.UserInfoEntityOld;

import java.util.List;
import java.util.Map;

/**
 * 用户信息表
 * 
 * @author arjim
 * @description
 * @date 2020-11-27 09:38:52
 */
public interface UserInfoService {

	UserInfoEntityOld queryObject(Long id);

	List<UserInfoEntityOld> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void save(UserInfoEntityOld userInfo);

	int update(UserInfoEntityOld userInfo);

	int delete(Long id);

	int deleteBatch(Long[] ids);
}
