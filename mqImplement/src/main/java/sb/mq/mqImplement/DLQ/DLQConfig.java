package sb.mq.mqImplement.DLQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DLQConfig {

    public static final String normalExchange = "exchange.normal.queues";
    public static final String dlqExchange = "exchange.dlq.queues";
    public static final String normalQueue = "queue.normal";
    public static final String dlqQueue = "queue.dlq";
    public static final String normalKey = "exchange.normal.key";
    public static final String dlqKey = "exchange.dlq.key";

    @Bean public DirectExchange normalExchange() {
        return new DirectExchange(normalExchange);
    }

    @Bean public DirectExchange dlqExchange() {
        return new DirectExchange(dlqExchange);
    }

    @Bean public Queue normalQueue() {
        return QueueBuilder.durable(normalQueue).deadLetterExchange(dlqExchange).deadLetterRoutingKey(dlqKey).build();
    }

    @Bean public Queue dlqQueue() {
        return new Queue(dlqQueue, true);
    }

    @Bean public Binding normalBind(Queue normalQueue, DirectExchange normalExchange) {
        return BindingBuilder
                .bind(normalQueue)
                .to(normalExchange)
                .with(normalKey);
    }

    @Bean public Binding dlqBind(Queue dlqQueue, DirectExchange dlqExchange) {
        return BindingBuilder
                .bind(dlqQueue)
                .to(dlqExchange)
                .with(dlqKey);
    }
}
