package faang.school.achievement.config.context.redis;

import faang.school.achievement.service.listener.MentorshipEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.channels.name.mentorship_channel}")
    private String mentorshipEventTopic;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }
    @Bean
    MessageListenerAdapter mentorshipListener(MentorshipEventListener mentorshipEventListener) {
        return new MessageListenerAdapter(mentorshipEventListener);
    }

    @Bean
    ChannelTopic mentorshipEventTopic() {
        return new ChannelTopic(mentorshipEventTopic);
    }
    @Bean
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter mentorshipEventListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(mentorshipEventListener, mentorshipEventTopic());
        return container;
    }

}