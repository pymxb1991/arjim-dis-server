package com.arjim.webserver.user.service;

import com.arjim.webserver.user.model.UserGroupEntity;

/**
 * 用户群组
 * 
 * @author mao'x'bmaoxb
 * @description
 * @date 2017-11-27 09:38:52
 */
public interface UserGroupService {

    UserGroupEntity selectById(String id);

    void save(UserGroupEntity userGroup);

    String updateGroupOwenId(String userId, String groupId);
}
