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
    public static final String queueName = "order_queue_1";

    @Bean public DirectExchange exchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Queue createQueue() {
        return new Queue(queueName, true);
    }

    @Bean public Binding binding() {
        return BindingBuilder
                .bind(createQueue())
                .to(exchange())
                .with(exchangeKey);
    }
}
