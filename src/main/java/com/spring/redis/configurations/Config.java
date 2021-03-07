package com.spring.redis.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class Config {

    @Bean
    LettuceConnectionFactory getfactory()
    {
        LettuceConnectionFactory connectionFactory=new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost",6379));
        return connectionFactory;

    }
    @Bean
    RedisTemplate<String,Object> getTemplate()
    {
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();

//        redisTemplate.afterPropertiesSet();
        RedisSerializer<String> stringRedisSerializer=new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);

        JdkSerializationRedisSerializer jdkSerializationRedisSerializer=new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);

        redisTemplate.setHashKeySerializer(jdkSerializationRedisSerializer);

        redisTemplate.setConnectionFactory(this.getfactory());
        return redisTemplate;

    }
}
