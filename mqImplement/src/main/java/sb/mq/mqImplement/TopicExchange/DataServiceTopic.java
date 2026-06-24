package sb.mq.mqImplement.TopicExchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataServiceTopic {
    private final RabbitTemplate rabbitTemplate;
    public DataServiceTopic(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void fun(String name, String email) {
        DataTopic v1 = new DataTopic(name, email);
        rabbitTemplate.convertAndSend(TopicExchangeConfig.exchangeName, "queue_1.one", v1);
        rabbitTemplate.convertAndSend(TopicExchangeConfig.exchangeName, "queue_2.two", v1);
        rabbitTemplate.convertAndSend(TopicExchangeConfig.exchangeName, "queue_3.vanakam", v1);
    }
}
