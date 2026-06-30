package sb.mq.mqImplement.TopicExchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class TopicExchangeConfig {
    public static final String exchangeName = "Data_Topic_Exchange";
    public static final String queueName_1 = "Data_Queue_1";
    public static final String queueName_2 = "Data_Queue_2";
    public static final String queueName_3 = "Data_Queue_3";

    @Bean public TopicExchange topicExchange() { return new TopicExchange(exchangeName, true, false); }

    @Bean public Queue queue_1Topic() { return new Queue(queueName_1, true); }

    @Bean public Queue queue_2Topic() { return new Queue(queueName_2, true); }

    @Bean public Queue queue_3Topic() { return new Queue(queueName_3, true); }

    @Bean public Binding bindingQueue_1(Queue queue_1Topic, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue_1Topic)
                .to(topicExchange)
                .with("queue_1.one");
    }

    @Bean public Binding bindingQueue_2(Queue queue_2Topic, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue_2Topic)
                .to(topicExchange)
                .with("queue_2.two");
    }

    @Bean public Binding bindingQueue_3(Queue queue_3Topic, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue_3Topic)
                .to(topicExchange)
                .with("queue_3.*");
    }
}
