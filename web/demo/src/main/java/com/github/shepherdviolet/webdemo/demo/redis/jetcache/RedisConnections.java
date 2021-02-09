package com.github.shepherdviolet.webdemo.demo.redis.jetcache;

import io.lettuce.core.api.StatefulConnection;

/**
 * Lettuce Redis Client 连接管理器, 里面的连接单例是线程安全的, 请勿手动关闭连接(保持连接)
 *
 * @author S.Violet
 */
public interface RedisConnections {

    /**
     * 连接单例是线程安全的, 直接用就好了, 请勿手动关闭连接(保持连接)
     */
    StatefulConnection<String, String> stringConnection();

    /**
     * 连接单例是线程安全的, 直接用就好了, 请勿手动关闭连接(保持连接)
     */
    StatefulConnection<byte[], byte[]> bytesConnection();

}
