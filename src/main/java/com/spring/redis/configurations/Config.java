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
//        LettuceConnectionFactory connectionFactory=new LettuceConnectionFactory
//                (new RedisStandaloneConfiguration("localhost",6379));
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration("redis-11491.c84.us-east-1-2.ec2.cloud.redislabs.com",11491);
        redisStandaloneConfiguration.setPassword("2IQV76YTemPEOjitABVxTiRgLm6DHKNV");


        LettuceConnectionFactory connectionFactory =new LettuceConnectionFactory(redisStandaloneConfiguration);

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
