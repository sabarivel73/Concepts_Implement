package sb.mq.mqImplement.FanoutExchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenerFanout {
    @RabbitListener(queues = FanoutExchangeConfig.queueName_1)
    public void fun1(DataFanout data) {
        IO.println("Data from Queue-1 : ");
        IO.println(data.name());
        IO.println(data.email());
    }
    @RabbitListener(queues = FanoutExchangeConfig.queueName_2)
    public void fun2(DataFanout data) {
        IO.println("Data from Queue-2 : ");
        IO.println(data.name());
        IO.println(data.email());
    }
    @RabbitListener(queues = FanoutExchangeConfig.queueName_3)
    public void fun3(DataFanout data) {
        IO.println("Data from Queue-3 : ");
        IO.println(data.name());
        IO.println(data.email());
    }
}
