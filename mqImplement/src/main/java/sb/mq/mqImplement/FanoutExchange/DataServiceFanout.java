package sb.mq.mqImplement.FanoutExchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataServiceFanout {
    private final RabbitTemplate rabbitTemplate;
    public DataServiceFanout(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void fun(String name, String email) {
        DataFanout v1 = new DataFanout(name, email);
        rabbitTemplate.convertAndSend(FanoutExchangeConfig.exchangeName, "", v1);
    }
}
