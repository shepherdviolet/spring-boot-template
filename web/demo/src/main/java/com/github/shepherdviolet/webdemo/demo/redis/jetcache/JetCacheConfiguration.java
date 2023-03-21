package com.github.shepherdviolet.webdemo.demo.redis.jetcache;

import com.alicp.jetcache.AbstractCacheBuilder;
import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.alicp.jetcache.support.FastjsonKeyConvertor;
import com.alicp.jetcache.support.KryoValueDecoder;
import com.alicp.jetcache.support.KryoValueEncoder;
import com.github.shepherdviolet.glacimon.spring.helper.jetcache.lettuce.SyncRedisLettuceCacheBuilder;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>JetCache配置</p>
 *
 * <p>不用Yaml配置是因为使用Yaml配置在使用Apollo配置中心时会出问题,
 * 而且自己加载的Yaml文件中也不能配置JetCache(无效), 这和JetCache装载yaml配置的机制有关系, 所以保险起见还是直接手动配置了.</p>
 *
 * <p>内容:</p>
 *
 * <p>1.配置RedisCluster集群拓扑刷新, 解决主从切换时无法连接的问题 (也可以选择禁用集群节点验证). </p>
 * <p>2.Redis操作改为同步方式(默认是异步), 避免业务开发人员踩坑. </p>
 * <p>3.针对复杂业务场景, 允许访问底层Lettuce客户端 (请注入'RedisCommands'使用). </p>
 *
 * <p>注意: 如果依赖了JetCache的jar包, 却不设置它(yaml配置 / 自定义GlobalCacheConfig), 应用启动就会报错.
 * 所以如果"redis.enabled"不设置为"true", 就会无法启动, 所以必须在BootApplication中排除JetCache自动配置类"JetCacheAutoConfiguration",
 * 排除方法有四种, 这里举个栗子: <code> @SpringBootApplication(exclude = {JetCacheAutoConfiguration.class}) </code></p>
 *
 * @author S.Violet
 */
@Configuration
@ConditionalOnProperty(name = "redis.enabled", havingValue = "true")
@EnableCreateCacheAnnotation
public class JetCacheConfiguration {

    private static final String PROPERTY_PREFIX = "redis.";

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

    @Value("${" + PROPERTY_PREFIX + "stat-interval-minutes:15}")
    private int statIntervalMinutes = 15;

    @Value("${" + PROPERTY_PREFIX + "local.expire-after-write-millis:-1}")
    private long localExpireAfterWriteMillis = -1;

    @Value("${" + PROPERTY_PREFIX + "local.limit:-1}")
    private int localLimit = -1;

    @Value("${" + PROPERTY_PREFIX + "key-prefix:}")
    private String keyPrefix;

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
            throw new RuntimeException("JetCache | property '" + PROPERTY_PREFIX + "uri' is required");
        }
        logger.info("JetCache | URIs: " + uri);

        // sentinel
        if (uri.startsWith("redis-sentinel://")) {
            logger.info("JetCache | URI: " + uri + "  (sentinel-mode)");
            return Collections.singletonList(RedisURI.create(uri));
        }

        // stand-alone or cluster
        String[] array = uri.split(",");
        List<String> uriList = new ArrayList<>(array.length);
        for (String u : array) {
            u = u.trim();
            if (u.length() > 0) {
                uriList.add(u);
                logger.info("JetCache | URI: " + u);
            }
        }
        if (uriList.size() == 0) {
            throw new RuntimeException("JetCache | property '" + PROPERTY_PREFIX + "uri' is invalid, no valid uri contains");
        }
        return uriList.stream()
                .map(RedisURI::create)
                .collect(Collectors.toList());
    }

    @Bean(name = "springConfigProvider")
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider();
    }

    @Bean(name = "jetCacheLocalBuilder")
    @ConditionalOnMissingBean(name = "jetCacheLocalBuilder")
    public AbstractCacheBuilder jetCacheLocalBuilder(){
        CaffeineCacheBuilder builder = CaffeineCacheBuilder
                .createCaffeineCacheBuilder()
                .keyConvertor(FastjsonKeyConvertor.INSTANCE);
        if (localExpireAfterWriteMillis > 0) {
            builder.expireAfterWrite(localExpireAfterWriteMillis, TimeUnit.MILLISECONDS);
        }
        if (localLimit > 0) {
            builder.limit(localLimit);
        }
        return builder;
    }

    @Bean(name = "jetCacheRemoteBuilder")
    @ConditionalOnMissingBean(name = "jetCacheRemoteBuilder")
    public AbstractCacheBuilder jetCacheRemoteBuilder(@Qualifier("lettuceRedisClient") AbstractRedisClient lettuceRedisClient){
        //异步操作
//        RedisLettuceCacheBuilder builder = RedisLettuceCacheBuilder.createRedisLettuceCacheBuilder()
//                .keyConvertor(FastjsonKeyConvertor.INSTANCE)
//                .valueEncoder(KryoValueEncoder.INSTANCE)
//                .valueDecoder(KryoValueDecoder.INSTANCE)
//                .redisClient(lettuceRedisClient);
        //同步操作(牺牲性能, 防误用), 这个类在glacispring-helper库中, 也可以参考下文
        SyncRedisLettuceCacheBuilder builder = SyncRedisLettuceCacheBuilder.createSyncRedisLettuceCacheBuilder()
                .keyConvertor(FastjsonKeyConvertor.INSTANCE)
                .valueEncoder(KryoValueEncoder.INSTANCE)
                .valueDecoder(KryoValueDecoder.INSTANCE)
                .redisClient(lettuceRedisClient);
        if (keyPrefix != null && keyPrefix.length() > 0) {
            builder.keyPrefix(keyPrefix);
        }
        return builder;
    }

    @Bean(name = "globalCacheConfig")
    public GlobalCacheConfig globalCacheConfig(
            @Qualifier("springConfigProvider") SpringConfigProvider springConfigProvider,
            @Qualifier("jetCacheLocalBuilder") AbstractCacheBuilder jetCacheLocalBuilder,
            @Qualifier("jetCacheRemoteBuilder") AbstractCacheBuilder jetCacheRemoteBuilder,
            ObjectProvider<GlobalCacheConfigCustomizer[]> configCustomizers){

        Map<String, CacheBuilder> localBuilders = new HashMap<>(16);
        localBuilders.put(CacheConsts.DEFAULT_AREA, jetCacheLocalBuilder);

        Map<String, CacheBuilder> remoteBuilders = new HashMap<>(16);
        remoteBuilders.put(CacheConsts.DEFAULT_AREA, jetCacheRemoteBuilder);

        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        globalCacheConfig.setConfigProvider(springConfigProvider);
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        globalCacheConfig.setStatIntervalMinutes(statIntervalMinutes);
        globalCacheConfig.setAreaInCacheName(false);

        GlobalCacheConfigCustomizer[] customizers = configCustomizers.getIfAvailable();
        if (customizers != null) {
            for (GlobalCacheConfigCustomizer customizer : customizers) {
                customizer.customize(globalCacheConfig);
            }
        }

        return globalCacheConfig;
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
