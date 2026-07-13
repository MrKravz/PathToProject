package by.ares.path_list_service.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisSerializer<Object> serializer = RedisSerializer.json();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext
                .SerializationPair.fromSerializer(serializer);
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair)
                .disableCachingNullValues()
                .entryTtl(Duration.ofMinutes(30))
                .prefixCacheNameWith("path_list_service:v1:");
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("path_lists", defaultConfig.entryTtl(Duration.ofMinutes(30)));
        cacheConfigurations.put("series", defaultConfig.entryTtl(Duration.ofMinutes(30)));
        cacheConfigurations.put("series_all", defaultConfig.entryTtl(Duration.ofMinutes(30)));
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .transactionAware()
                .build();
    }
}

