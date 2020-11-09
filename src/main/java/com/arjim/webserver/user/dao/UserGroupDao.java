package com.arjim.webserver.user.dao;

import com.arjim.webserver.base.dao.BaseDao;
import com.arjim.webserver.user.model.UserGroupEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户组
 * 
 * @author mao'x'bmaoxb
 * @description
 * @date 2020-11-27 09:38:52
 */
@Repository
public interface UserGroupDao extends BaseDao<UserGroupEntity> {

    //void insert(UserGroupEntity userGroup);
    List<UserGroupEntity> findListByUserId(String userId);

    UserGroupEntity selectById(String id);
}
