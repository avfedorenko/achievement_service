package faang.school.achievement.config;

import faang.school.achievement.listeners.ProjectEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channels.project_channel.name}")
    private String projectTopic;
    @Bean
    public JedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration redisConfig = new  RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    public MessageListenerAdapter projectListener(ProjectEventListener projectEventListener){
        return new MessageListenerAdapter(projectEventListener);
    }

    @Bean
    public ChannelTopic projectTopic(){
        return new ChannelTopic(projectTopic);
    }

    @Bean
    public RedisMessageListenerContainer container(MessageListenerAdapter projectListener){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(projectListener, projectTopic());
        return container;
    }

}
