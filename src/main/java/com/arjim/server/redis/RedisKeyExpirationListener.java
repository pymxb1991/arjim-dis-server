package com.arjim.server.redis;
/**
 *@program: arjim-dis-server
 *@description: 监听所有db的过期事件__keyevent@*__:expired"
 *@author: maoxb
 *@create: 2020-10-30 10:40
 */
import com.arjim.constant.Constants;
import com.arjim.webserver.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Autowired
    private UserAccountService userAccountService;
    /**
     * 针对 redis 数据失效事件，进行数据处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {

        // 获取到用户失效的 key，进行离线操作
        String expiredKey = message.toString();
        if(expiredKey.startsWith(Constants.USER_ONLINE_STATUS_KEY)){
            //TODO  进行设置用户离线操作
            String userId = expiredKey.split(":")[1];
            userAccountService.removeLoginUser(userId);
        }
        System.out.println(expiredKey);
    }


}