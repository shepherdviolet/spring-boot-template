package com.github.shepherdviolet.webdemo.demo.redis.lettuce;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Lettuce配置</p>
 *
 * @author S.Violet
 */
@Configuration
@ConditionalOnProperty(name = "redis.enabled", havingValue = "true")
public class LettuceConfiguration {

    public static final String PROPERTY_PREFIX = "redis.";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Stand-alone:
     * redis://127.0.0.1:6379
     * <br>
     *
     * Sentinel:
     * redis-sentinel://host1:26379,host2:26379,host3:26379/?sentinelMasterId=mymaster
     * redis-sentinel://password@host1:26379,host2:26379,host3:26379/?sentinelMasterId=mymaster
     * <br>
     *
     * Cluster:
     * redis://host1:6379,redis://host2:6379,redis://host3:6379
     * redis://password@host1:6379,redis://password@host2:6379,redis://password@host3:6379
     */
    @Value("${" + PROPERTY_PREFIX + "uri}")
    private String uri;

    /**
     * Lettuce客户端
     * 避免Redis集群拓扑变化时报出错误: Connection to ?:? not allowed. This connection point is not known in the cluster view
     * 开启集群拓扑刷新(topologyRefreshOptions): 当服务端拓扑发生变化时, 短时间内还会出现连接错误, 刷新后才恢复
     * 快速失败(disconnectedBehavior REJECT_COMMANDS): 当网络波动/服务端拓扑变化, 设置快速失败时会抛出异常, 不设置快速失败会挂起等待超时
     */
    @Bean(name = "lettuceRedisClient")
    @ConditionalOnMissingBean(name = "lettuceRedisClient")
    public AbstractRedisClient lettuceRedisClient(){
        List<RedisURI> uriList = parseUri(uri);
        if (uriList.size() == 1) {
            RedisClient client = RedisClient.create(uriList.get(0));
            client.setOptions(ClientOptions.builder()
//                    .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS) // 配了这个Sentinel故障转移后不会重连!!!
                    .build());
            return client;
        } else {
            RedisClusterClient client = RedisClusterClient.create(uriList);
            client.setOptions(ClusterClientOptions.builder()
//                    .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS) // 配了这个故障转移后不会重连!!!
                    .topologyRefreshOptions(ClusterTopologyRefreshOptions.builder()
                            .enablePeriodicRefresh(Duration.ofSeconds(15)) // 开启定期刷新, 默认关闭; 更新间隔15秒, 默认60秒
                            .enableAllAdaptiveRefreshTriggers() // 启用全部拓扑刷新定时器
                            .build())
                    .build());
            return client;
        }
    }

    /**
     * 解析URI
     */
    private List<RedisURI> parseUri(String uri){
        if (uri == null || (uri = uri.trim()).length() == 0) {
            throw new RuntimeException("Lettuce | property '" + PROPERTY_PREFIX + "uri' is required");
        }
        logger.info("Lettuce | URIs: " + uri);

        // sentinel
        if (uri.startsWith("redis-sentinel://")) {
            logger.info("Lettuce | URI: " + uri + "  (sentinel-mode)");
            return Collections.singletonList(RedisURI.create(uri));
        }

        // stand-alone or cluster
        String[] array = uri.split(",");
        List<String> uriList = new ArrayList<>(array.length);
        for (String u : array) {
            u = u.trim();
            if (u.length() > 0) {
                uriList.add(u);
                logger.info("Lettuce | URI: " + u);
            }
        }
        if (uriList.size() == 0) {
            throw new RuntimeException("Lettuce | property '" + PROPERTY_PREFIX + "uri' is invalid, no valid uri contains");
        }
        return uriList.stream()
                .map(RedisURI::create)
                .collect(Collectors.toList());
    }

    /**
     * Redis手动操作方式 API入口, 里面的命令实例是线程安全的, 直接使用即可.
     * 命令用法请参考Lettuce官方文档.
     */
    @Bean(name = "redisCommands")
    @ConditionalOnMissingBean(name = "redisCommands")
    public RedisCommands redisCommands(@Qualifier("lettuceRedisClient") AbstractRedisClient lettuceRedisClient){
        return new RedisCommandsImpl(lettuceRedisClient);
    }

}
