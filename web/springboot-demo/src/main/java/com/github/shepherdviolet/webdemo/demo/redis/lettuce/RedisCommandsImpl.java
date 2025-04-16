package com.github.shepherdviolet.webdemo.demo.redis.lettuce;

import com.github.shepherdviolet.glacimon.java.misc.CloseableUtils;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Lettuce Redis Client 命令, 里面的命令实例是线程安全的, 直接使用即可.
 * 命令用法请参考Lettuce官方文档.
 *
 * @author S.Violet
 */
public class RedisCommandsImpl implements RedisCommands, InitializingBean, DisposableBean {

    private AbstractRedisClient redisClient;
    private StatefulConnection<String, String> stringConnection;
    private StatefulConnection<byte[], byte[]> bytesConnection;
    private RedisClusterCommands<String, String> stringCommands;
    private RedisClusterCommands<byte[], byte[]> bytesCommands;
    private RedisClusterAsyncCommands<String, String> asyncStringCommands;
    private RedisClusterAsyncCommands<byte[], byte[]> asyncBytesCommands;

    /**
     * redisClient is required
     */
    public RedisCommandsImpl() {
    }

    /**
     * redisClient is required
     */
    public RedisCommandsImpl(AbstractRedisClient redisClient) {
        this.redisClient = redisClient;
    }

    /**
     * Key/Value为String的Redis同步操作命令, 命令实例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    @Override
    public RedisClusterCommands<String, String> stringCommands() {
        return stringCommands;
    }

    /**
     * Key/Value为byte[]的Redis同步操作命令, 命令实例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    @Override
    public RedisClusterCommands<byte[], byte[]> bytesCommands() {
        return bytesCommands;
    }

    /**
     * Key/Value为String的Redis异步操作命令, 命令实例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    @Override
    public RedisClusterAsyncCommands<String, String> asyncStringCommands() {
        return asyncStringCommands;
    }

    /**
     * Key/Value为byte[]的Redis异步操作命令, 命令实例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    @Override
    public RedisClusterAsyncCommands<byte[], byte[]> asyncBytesCommands() {
        return asyncBytesCommands;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (redisClient == null) {
            throw new IllegalArgumentException("redisClient is null");
        }
        if (redisClient instanceof RedisClient) {
            // connection
            stringConnection = ((RedisClient) redisClient).connect(StringCodec.UTF8);
            bytesConnection = ((RedisClient) redisClient).connect(ByteArrayCodec.INSTANCE);
            // string
            stringCommands = ((StatefulRedisConnection<String, String>) stringConnection).sync();
            asyncStringCommands = ((StatefulRedisConnection<String, String>) stringConnection).async();
            // byte[]
            bytesCommands = ((StatefulRedisConnection<byte[], byte[]>) bytesConnection).sync();
            asyncBytesCommands = ((StatefulRedisConnection<byte[], byte[]>) bytesConnection).async();
        } else if (redisClient instanceof RedisClusterClient) {
            // connection
            stringConnection = ((RedisClusterClient) redisClient).connect(StringCodec.UTF8);
            bytesConnection = ((RedisClusterClient) redisClient).connect(ByteArrayCodec.INSTANCE);
            // string
            stringCommands = ((StatefulRedisClusterConnection<String, String>) stringConnection).sync();
            asyncStringCommands = ((StatefulRedisClusterConnection<String, String>) stringConnection).async();
            // byte[]
            bytesCommands = ((StatefulRedisClusterConnection<byte[], byte[]>) bytesConnection).sync();
            asyncBytesCommands = ((StatefulRedisClusterConnection<byte[], byte[]>) bytesConnection).async();
        } else {
            throw new IllegalStateException("Illegal redisClient type " + redisClient.getClass().getName());
        }
    }

    @Override
    public void destroy() throws Exception {
        CloseableUtils.closeQuiet(stringConnection);
        CloseableUtils.closeQuiet(bytesConnection);
    }

    public void setRedisClient(AbstractRedisClient redisClient) {
        this.redisClient = redisClient;
    }

}
