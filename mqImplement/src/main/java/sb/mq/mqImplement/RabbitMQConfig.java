package sb.mq.mqImplement;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}") private String host;
    @Value("${spring.rabbitmq.port}") private Integer port;
    @Value("${spring.rabbitmq.username}") private String username;
    @Value("${spring.rabbitmq.password}") private String password;

    @Bean("Common_CF")
    @Primary
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setHost(host);
        cf.setPort(port);
        cf.setUsername(username);
        cf.setPassword(password);
        return cf;
    }

    @Bean("MC")
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean("Common_RT")
    public RabbitTemplate rabbitTemplate(@Qualifier("Common_CF") ConnectionFactory cf, @Qualifier("MC") MessageConverter mc) {
        RabbitTemplate rt = new RabbitTemplate(cf);
        rt.setMessageConverter(mc);
        return rt;
    }

    @Bean("Common_LCF")
    public SimpleRabbitListenerContainerFactory lcf(@Qualifier("Common_CF") ConnectionFactory cf, @Qualifier("MC") MessageConverter mc) {
        SimpleRabbitListenerContainerFactory listener = new SimpleRabbitListenerContainerFactory();
        listener.setConnectionFactory(cf);
        listener.setMessageConverter(mc);
        return listener;
    }
}
