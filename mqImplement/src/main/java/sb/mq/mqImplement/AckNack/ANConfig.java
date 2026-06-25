package sb.mq.mqImplement.AckNack;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import sb.mq.mqImplement.DirectExchange.DirectExchangeConfig;

@Configuration
@PropertySource("classpath:AN.properties")
public class ANConfig {

    @Value("${spring.AN.rabbitmq.host}") private String host;
    @Value("${spring.AN.rabbitmq.port}") private Integer port;
    @Value("${spring.AN.rabbitmq.username}") private String username;
    @Value("${spring.AN.rabbitmq.password}") private String password;

    public static final String exchangeKeyAN = "exchange_key_AN";
    public static final String queueNameAN = "queue_AN";
    @Bean("AN_CF")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setHost(host);
        cf.setPort(port);
        cf.setUsername(username);
        cf.setPassword(password);
        return cf;
    }
    @Bean public Queue queueAN() {
        return new Queue(queueNameAN, true);
    }
    @Bean public Binding bindingAN() {
        return BindingBuilder
                .bind(queueAN())
                .to(DirectExchangeConfig.exchange())
                .with(exchangeKeyAN);
    }
    @Bean("AN_RT") public RabbitTemplate rabbitTemplate(@Qualifier("AN_CF") ConnectionFactory cf, @Qualifier("MC")MessageConverter ms) {
        RabbitTemplate rt = new RabbitTemplate(cf);
        rt.setMessageConverter(ms);
        return rt;
    }

    @Bean("AN_LCF")
    public SimpleRabbitListenerContainerFactory lcf(@Qualifier("AN_CF") ConnectionFactory cf, @Qualifier("MC")MessageConverter ms) {
        SimpleRabbitListenerContainerFactory listener = new SimpleRabbitListenerContainerFactory();
        listener.setConnectionFactory(cf);
        listener.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        listener.setPrefetchCount(10);
        listener.setMessageConverter(ms);
        return listener;
    }
}
