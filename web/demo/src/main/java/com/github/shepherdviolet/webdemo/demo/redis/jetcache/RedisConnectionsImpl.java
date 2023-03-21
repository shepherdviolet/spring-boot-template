package com.github.shepherdviolet.webdemo.demo.redis.jetcache;

import com.github.shepherdviolet.glacimon.java.misc.CloseableUtils;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Lettuce Redis Client 连接管理器, 里面的连接单例是线程安全的, 请勿手动关闭连接(保持连接)
 *
 * @author S.Violet
 */
public class RedisConnectionsImpl implements RedisConnections, InitializingBean, DisposableBean {

    private AbstractRedisClient redisClient;
    private StatefulConnection<String, String> stringConnection;
    private StatefulConnection<byte[], byte[]> bytesConnection;

    /**
     * redisClient is required
     */
    public RedisConnectionsImpl() {
    }

    public RedisConnectionsImpl(AbstractRedisClient redisClient) {
        this.redisClient = redisClient;
    }

    /**
     * 连接单例是线程安全的, 直接用就好了, 请勿手动关闭连接(保持连接)
     */
    @Override
    public StatefulConnection<String, String> stringConnection() {
        return stringConnection;
    }

    /**
     * 连接单例是线程安全的, 直接用就好了, 请勿手动关闭连接(保持连接)
     */
    @Override
    public StatefulConnection<byte[], byte[]> bytesConnection() {
        return bytesConnection;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (redisClient == null) {
            throw new IllegalArgumentException("redisClient is null");
        }
        //connect
        if (redisClient instanceof RedisClient) {
            stringConnection = ((RedisClient) redisClient).connect(StringCodec.UTF8);
            bytesConnection = ((RedisClient) redisClient).connect(ByteArrayCodec.INSTANCE);
        } else if (redisClient instanceof RedisClusterClient) {
            stringConnection = ((RedisClusterClient) redisClient).connect(StringCodec.UTF8);
            bytesConnection = ((RedisClusterClient) redisClient).connect(ByteArrayCodec.INSTANCE);
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
