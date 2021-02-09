package com.github.shepherdviolet.webdemo.demo.redis.jetcache;

import com.alicp.jetcache.anno.support.GlobalCacheConfig;

/**
 * JetCache GlobalCacheConfig配置调整器
 */
public interface GlobalCacheConfigCustomizer {

    /**
     * 调整配置
     */
    void customize(GlobalCacheConfig config);

}
