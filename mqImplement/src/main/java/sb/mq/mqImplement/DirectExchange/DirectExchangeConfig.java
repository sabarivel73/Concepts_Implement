package sb.mq.mqImplement.DirectExchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {
    public static final String exchangeName = "exchange";
    public static final String exchangeKey = "exchange_key";
    public static final String exchangeKey_2 = "exchange_key_2";
    public static final String queueName = "order_queue_1";
    public static final String queueName_2 = "order_queue_2";

    @Bean public DirectExchange exchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Queue createQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue createQueue_2() {
        return new Queue(queueName_2, true);
    }

    @Bean public Binding binding() {
        return BindingBuilder
                .bind(createQueue())
                .to(exchange())
                .with(exchangeKey);
    }

    @Bean public Binding binding_2() {
        return BindingBuilder
                .bind(createQueue_2())
                .to(exchange())
                .with(exchangeKey_2);
    }
}
