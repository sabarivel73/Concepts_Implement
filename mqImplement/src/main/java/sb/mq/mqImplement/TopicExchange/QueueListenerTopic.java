package sb.mq.mqImplement.TopicExchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenerTopic {
    @RabbitListener(queues = TopicExchangeConfig.queueName_1)
    public void fun1(DataTopic data) {
        IO.println("Queue_1 One Data List: ");
        IO.println(data.name());
        IO.println(data.email());
    }
    @RabbitListener(queues = TopicExchangeConfig.queueName_2)
    public void fun2(DataTopic data) {
        IO.println("Queue_2 Two Data List: ");
        IO.println(data.name());
        IO.println(data.email());
    }
    @RabbitListener(queues = TopicExchangeConfig.queueName_3)
    public void fun3(DataTopic data) {
        IO.println("Queue_3 Vanakam Data List: ");
        IO.println(data.name());
        IO.println(data.email());
    }
}
