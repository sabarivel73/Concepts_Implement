package sb.mq.mqImplement.FanoutExchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class FanoutExchangeConfig {

    public static final String exchangeName = "Data_fanoutExchange";
    public static final String queueName_1 = "queue_1";
    public static final String queueName_2 = "queue_2";
    public static final String queueName_3 = "queue_3";

    @Bean public FanoutExchange fanoutExchange() { return new FanoutExchange(exchangeName, true, false); }

    @Bean public Queue queue_1Fanout() { return new Queue(queueName_1, true); }

    @Bean public Queue queue_2Fanout() { return new Queue(queueName_2, true); }

    @Bean public Queue queue_3Fanout() { return new Queue(queueName_3, true); }

    @Bean public Binding queue_1Bind() {
        return BindingBuilder
                .bind(queue_1Fanout())
                .to(fanoutExchange());
    }

    @Bean public Binding queue_2Bind() {
        return BindingBuilder
                .bind(queue_2Fanout())
                .to(fanoutExchange());
    }

    @Bean public Binding queue_3Bind() {
        return BindingBuilder
                .bind(queue_3Fanout())
                .to(fanoutExchange());
    }
}
