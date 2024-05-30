package faang.school.achievement.config;

import faang.school.achievement.listener.CommentEventListener;
import faang.school.achievement.listener.FollowerEventListener;
import faang.school.achievement.listener.LikeEventListener;
import faang.school.achievement.listener.MentorshipEventListener;
import faang.school.achievement.listener.ProjectEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.channels.comment_channel.name}")
    private String commentTopic;
    @Value("${spring.data.redis.channels.follower_channel.name}")
    private String followerTopic;
    @Value("${spring.data.redis.channels.like_channel.name}")
    private String likeTopic;
    @Value("${spring.data.redis.channels.mentorship_channel.name}")
    private String mentorshipTopic;
    @Value("${spring.data.redis.channels.project_channel.name}")
    private String projectTopic;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public MessageListenerAdapter commentListener(CommentEventListener commentEventListener) {
        return new MessageListenerAdapter(commentEventListener);
    }

    @Bean
    public MessageListenerAdapter followerListener(FollowerEventListener followerEventListener) {
        return new MessageListenerAdapter(followerEventListener);
    }

    @Bean
    public MessageListenerAdapter likeListener(LikeEventListener likeEventListener) {
        return new MessageListenerAdapter(likeEventListener);
    }

    @Bean
    public MessageListenerAdapter mentorshipListener(MentorshipEventListener mentorshipEventListener) {
        return new MessageListenerAdapter(mentorshipEventListener);
    }

    @Bean
    public MessageListenerAdapter projectListener(ProjectEventListener projectEventListener) {
        return new MessageListenerAdapter(projectEventListener);
    }

    @Bean
    public ChannelTopic commentEventTopic() {
        return new ChannelTopic(commentTopic);
    }

    @Bean
    public ChannelTopic followerEventTopic() {
        return new ChannelTopic(followerTopic);
    }

    @Bean
    public ChannelTopic likeEventTopic() {
        return new ChannelTopic(likeTopic);
    }

    @Bean
    public ChannelTopic mentorshipEventTopic() {
        return new ChannelTopic(mentorshipTopic);
    }

    @Bean
    public ChannelTopic projectEventTopic() {
        return new ChannelTopic(projectTopic);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(MessageListenerAdapter commentListener,
                                                        MessageListenerAdapter followerListener,
                                                        MessageListenerAdapter likeListener,
                                                        MessageListenerAdapter mentorshipListener,
                                                        MessageListenerAdapter projectListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(commentListener, commentEventTopic());
        container.addMessageListener(followerListener, followerEventTopic());
        container.addMessageListener(likeListener, likeEventTopic());
        container.addMessageListener(mentorshipListener, mentorshipEventTopic());
        container.addMessageListener(projectListener, projectEventTopic());
        return container;
    }
}
