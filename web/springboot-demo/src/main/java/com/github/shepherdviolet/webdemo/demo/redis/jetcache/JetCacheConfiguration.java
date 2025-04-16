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
import com.github.shepherdviolet.webdemo.demo.redis.lettuce.LettuceConfiguration;
import io.lettuce.core.AbstractRedisClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Value("${" + LettuceConfiguration.PROPERTY_PREFIX + "stat-interval-minutes:15}")
    private int statIntervalMinutes = 15;

    @Value("${" + LettuceConfiguration.PROPERTY_PREFIX + "local.expire-after-write-millis:-1}")
    private long localExpireAfterWriteMillis = -1;

    @Value("${" + LettuceConfiguration.PROPERTY_PREFIX + "local.limit:-1}")
    private int localLimit = -1;

    @Value("${" + LettuceConfiguration.PROPERTY_PREFIX + "key-prefix:}")
    private String keyPrefix;

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

}
