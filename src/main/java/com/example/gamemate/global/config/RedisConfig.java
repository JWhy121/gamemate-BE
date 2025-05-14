package com.example.gamemate.global.config;


import com.example.gamemate.domain.newChat.RedisMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisMessageListener redisMessageListener;


    public RedisConfig(RedisConnectionFactory redisConnectionFactory, RedisMessageListener redisMessageListener) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisMessageListener = redisMessageListener;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // String 타입 key serializer 설정
        template.setKeySerializer(new StringRedisSerializer());

        // Jackson2JsonRedisSerializer로 value serializer 설정
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }



    @Bean
    public RedisMessageListenerContainer messageListenerContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        container.addMessageListener(redisMessageListener, new PatternTopic("chatroom:*"));

        return container;
    }
}
