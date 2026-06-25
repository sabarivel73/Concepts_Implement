package sb.mq.mqImplement.DirectExchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenerDirect {
    @RabbitListener(queues = DirectExchangeConfig.queueName,concurrency = "3-5")
    public void fun(DataDirect data) {
        IO.println(data.id());
        IO.println(data.name());
        IO.println(data.email());
    }
    @RabbitListener(queues = DirectExchangeConfig.queueName_2)
    public void fun_1(DataDirect data) {
        IO.println("From Queue 2 : ");
        IO.println(data.id());
        IO.println(data.name());
        IO.println(data.email());
    }
}
