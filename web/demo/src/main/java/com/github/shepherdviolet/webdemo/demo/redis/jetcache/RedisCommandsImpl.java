package com.github.shepherdviolet.webdemo.demo.redis.jetcache;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import org.springframework.beans.factory.InitializingBean;

/**
 * Lettuce Redis Client 命令, 里面的命令单例是线程安全的, 直接使用即可
 * 命令用法请参考Lettuce官方文档
 *
 * @author S.Violet
 */
public class RedisCommandsImpl implements RedisCommands, InitializingBean {

    private RedisConnections redisConnections;

    private RedisClusterCommands<String, String> stringCommands;
    private RedisClusterCommands<byte[], byte[]> bytesCommands;
    private RedisClusterAsyncCommands<String, String> asyncStringCommands;
    private RedisClusterAsyncCommands<byte[], byte[]> asyncBytesCommands;

    /**
     * redisConnections is required
     */
    public RedisCommandsImpl() {
    }

    public RedisCommandsImpl(RedisConnections redisConnections) {
        this.redisConnections = redisConnections;
    }

    /**
     * Key/Value为String的Redis同步操作命令, 命令单例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    @Override
    public RedisClusterCommands<String, String> stringCommands() {
        return stringCommands;
    }

    /**
     * Key/Value为byte[]的Redis同步操作命令, 命令单例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    @Override
    public RedisClusterCommands<byte[], byte[]> bytesCommands() {
        return bytesCommands;
    }

    /**
     * Key/Value为String的Redis异步操作命令, 命令单例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    @Override
    public RedisClusterAsyncCommands<String, String> asyncStringCommands() {
        return asyncStringCommands;
    }

    /**
     * Key/Value为byte[]的Redis异步操作命令, 命令单例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    @Override
    public RedisClusterAsyncCommands<byte[], byte[]> asyncBytesCommands() {
        return asyncBytesCommands;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (redisConnections == null) {
            throw new IllegalArgumentException("redisConnections is null");
        }
        //get commands
        if (redisConnections.stringConnection() instanceof StatefulRedisConnection) {
            stringCommands = ((StatefulRedisConnection<String, String>) redisConnections.stringConnection()).sync();
            asyncStringCommands = ((StatefulRedisConnection<String, String>) redisConnections.stringConnection()).async();
        } else if (redisConnections.stringConnection() instanceof StatefulRedisClusterConnection) {
            stringCommands = ((StatefulRedisClusterConnection<String, String>) redisConnections.stringConnection()).sync();
            asyncStringCommands = ((StatefulRedisClusterConnection<String, String>) redisConnections.stringConnection()).async()
;        } else {
            throw new IllegalStateException("Illegal string connection type " +
                    redisConnections.stringConnection().getClass().getName());
        }
        if (redisConnections.bytesConnection() instanceof StatefulRedisConnection) {
            bytesCommands = ((StatefulRedisConnection<byte[], byte[]>) redisConnections.bytesConnection()).sync();
            asyncBytesCommands = ((StatefulRedisConnection<byte[], byte[]>) redisConnections.bytesConnection()).async();
        } else if (redisConnections.stringConnection() instanceof StatefulRedisClusterConnection) {
            bytesCommands = ((StatefulRedisClusterConnection<byte[], byte[]>) redisConnections.bytesConnection()).sync();
            asyncBytesCommands = ((StatefulRedisClusterConnection<byte[], byte[]>) redisConnections.bytesConnection()).async();
        } else {
            throw new IllegalStateException("Illegal string connection type " +
                    redisConnections.bytesConnection().getClass().getName());
        }
    }

    public void setRedisConnections(RedisConnections redisConnections) {
        this.redisConnections = redisConnections;
    }

}
