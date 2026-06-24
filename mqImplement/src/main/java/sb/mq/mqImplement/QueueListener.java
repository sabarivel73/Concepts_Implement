package sb.mq.mqImplement;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListener {
    @RabbitListener(queues = RabbitMQConfig.queueName)
    public void fun(Data data) {
        IO.println(data.id());
        IO.println(data.name());
        IO.println(data.email());
    }
}
