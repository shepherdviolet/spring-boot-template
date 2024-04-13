package com.github.shepherdviolet.webdemo.demo.redis.cache;

import com.alicp.jetcache.anno.*;
import com.github.shepherdviolet.glacimon.java.misc.DateTimeUtils;
import com.github.shepherdviolet.webdemo.demo.redis.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserCache {

    //声明获取方法
    @Cached(
            //缓存名称
            name="users-",
            //Key, args[0]表示该方法第一个参数, 即id
            key="args[0]",
            //使用二级缓存
            cacheType = CacheType.BOTH,
            //远端缓存有效期10分钟
            expire = 10 * 60,
            //本地缓存有效期10秒
            localExpire = 10,
            //本地缓存上线100
            localLimit = 100
    )
    @CacheRefresh(
            //每隔60秒从数据源刷新数据
            refresh = 60,
            //刷新锁超时60秒(这个是redis分布式锁的超时时间, 用来保证每次只有一个进程执行刷新)
            refreshLockTimeout = 60,
            //10分钟内没有访问暂停刷新任务
            stopRefreshAfterLastAccess = 10 * 60
    )
    public User get(String id){
        // 实现从数据源获取数据
        // 当缓存命中时, 此处的实现是不会被调用的(代理拦截). 只有当缓存未命中或自动刷新时, 才会调用此处的代码, 从数据源获取数据

        // 这里测试直接造一个
        User user = new User();
        user.setId(id);
        user.setName("Tester");
        user.setTime(DateTimeUtils.currentDateTimeString());

        return user;
    }

    //声明修改方法
    @CacheUpdate(
            //缓存名称
            name="users-",
            //Key, 支持EL, args[0].id表示该方法第一个参数的成员变量id, 即user.id
            key="args[0].id",
            //Value, args[0]表示该方法第一个参数, 即bean
            value="args[0]"
    )
    public void update(Bean bean){
        //无需实现
    }

    //声明删除方法
    @CacheInvalidate(
            //缓存名称
            name="users-",
            //Key, args[0]表示该方法第一个参数, 即id
            key="args[0]"
    )
    public void delete(String id){
        //无需实现
    }

}