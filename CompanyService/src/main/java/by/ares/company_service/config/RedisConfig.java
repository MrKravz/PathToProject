package by.ares.company_service.config;

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
                .prefixCacheNameWith("company_service:v1:");
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("cars", defaultConfig.entryTtl(Duration.ofMinutes(30)));
        cacheConfigurations.put("car_drivers", defaultConfig.entryTtl(Duration.ofMinutes(30)));
        cacheConfigurations.put("companies", defaultConfig.entryTtl(Duration.ofMinutes(60)));
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .transactionAware()
                .build();
    }

}
