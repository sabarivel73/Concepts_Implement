package sb.mq.mqImplement.AckNack;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sb.mq.mqImplement.DirectExchange.DirectExchangeConfig;

@Service
public class DataServiceAN {
    private final RabbitTemplate rabbitTemplate;
    public DataServiceAN(@Qualifier("AN_RT") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void fun(String name, String email) {
        DataAN v1 = new DataAN(name, email);
        rabbitTemplate.convertAndSend(DirectExchangeConfig.exchangeName, ANConfig.exchangeKeyAN, v1);
    }
}
