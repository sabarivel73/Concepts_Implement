package sb.mq.mqImplement.DLQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DataServiceDLQ {

    private final RabbitTemplate rabbitTemplate;

    public DataServiceDLQ(@Qualifier("AN_RT") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void fun(String name, String email) {
        DataDLQ v1 = new DataDLQ(name, email);
        rabbitTemplate.convertAndSend(DLQConfig.normalExchange, DLQConfig.normalKey, v1);
    }
}
