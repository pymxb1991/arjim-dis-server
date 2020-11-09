package com.arjim.webserver.user.dao;

import com.arjim.webserver.base.dao.BaseDao;
import com.arjim.webserver.user.model.UserRelationShipEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户组
 * 
 * @author mao'x'b
 * @description
 * @date 2020-11-27 09:38:52
 */
@Repository
public interface UserRelationShipDao extends BaseDao<UserRelationShipEntity> {

//    void insert(UserRelationShipEntity userRelationShip);

    void deleteByGroupAndUser(UserRelationShipEntity userRelationshipEntity);

    int deleteByGroupIdAndUserId(@Param("userId")  String userId , @Param("groupId")  String groupId );

    List<String> findUserListByGroupId(@Param("id")  String id);

    void deleteRelByGroupId(@Param("id") String id);


}
