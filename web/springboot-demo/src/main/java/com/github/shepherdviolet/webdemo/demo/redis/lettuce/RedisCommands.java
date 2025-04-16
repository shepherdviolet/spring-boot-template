package com.github.shepherdviolet.webdemo.demo.redis.lettuce;

import io.lettuce.core.cluster.api.async.RedisClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;

/**
 * Lettuce Redis Client 命令, 里面的命令单例是线程安全的, 直接使用即可
 * 命令用法请参考Lettuce官方文档
 *
 * @author S.Violet
 */
public interface RedisCommands {

    /**
     * Key/Value为String的Redis同步操作命令, 命令单例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    RedisClusterCommands<String, String> stringCommands();

    /**
     * Key/Value为byte[]的Redis同步操作命令, 命令单例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    RedisClusterCommands<byte[], byte[]> bytesCommands();

    /**
     * Key/Value为String的Redis异步操作命令, 命令单例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    RedisClusterAsyncCommands<String, String> asyncStringCommands();

    /**
     * Key/Value为byte[]的Redis异步操作命令, 命令单例是线程安全的, 直接使用即可
     * 命令用法请参考Lettuce官方文档
     */
    RedisClusterAsyncCommands<byte[], byte[]> asyncBytesCommands();

}
