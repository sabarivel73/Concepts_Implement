package sb.mq.mqImplement.AckNack;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:AN.properties")
public class PublisherACK {
    private final RabbitTemplate rabbitTemplate;
    public PublisherACK(@Qualifier("AN_RT") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                IO.println("Message sent to RabbitMQ broker");
            }
            else {
                IO.println("Message not sent to RabbitMQ broker because of this cause " + cause);
            }
        });
        rabbitTemplate.setReturnsCallback(returned -> {
            IO.println("Message returned " + returned.getReplyText());
            IO.println("Exchange method " + returned.getExchange());
            IO.println("RoutingKey " + returned.getRoutingKey());
        });
    }
}
