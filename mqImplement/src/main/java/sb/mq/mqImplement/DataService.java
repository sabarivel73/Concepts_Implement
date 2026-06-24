package sb.mq.mqImplement;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    private final RabbitTemplate rabbitTemplate;
    public DataService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void fun(Integer id, String name, String email) {
        Data v1 = new Data(id, name, email);
        rabbitTemplate.convertAndSend(RabbitMQConfig.exchangeName, RabbitMQConfig.exchangeKey, v1);
    }
}
