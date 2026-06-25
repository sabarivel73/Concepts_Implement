package sb.mq.mqImplement.DirectExchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataServiceDirect {
    private final RabbitTemplate rabbitTemplate;
    public DataServiceDirect(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void fun(Integer id, String name, String email) {
        DataDirect v1 = new DataDirect(id, name, email);
        rabbitTemplate.convertAndSend(DirectExchangeConfig.exchangeName, DirectExchangeConfig.exchangeKey, v1);
        rabbitTemplate.convertAndSend(DirectExchangeConfig.exchangeName, DirectExchangeConfig.exchangeKey_2, v1);
    }
}
