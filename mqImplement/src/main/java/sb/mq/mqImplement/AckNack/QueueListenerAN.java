package sb.mq.mqImplement.AckNack;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenerAN {
    @RabbitListener(queues = ANConfig.queueNameAN, containerFactory = "AN_LCF")
    public void fun(DataAN data) {
        IO.println("Data from AN : ");
        IO.println(data.name());
        IO.println(data.email());
    }
}
