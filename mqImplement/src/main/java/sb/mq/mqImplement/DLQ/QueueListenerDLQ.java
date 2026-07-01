package sb.mq.mqImplement.DLQ;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenerDLQ {
    @RabbitListener(queues = DLQConfig.normalQueue, containerFactory = "AN_LCF")
    public void fun1(DataDLQ data, Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            if(data.email().endsWith("@mail...com")) throw new IllegalArgumentException("Wrong mail domain");
            IO.println("Data from Normal Queue : ");
            IO.println(data.name());
            IO.println(data.email());
            channel.basicAck(tag, false);
        } catch (Exception ex) {
            IO.println("Data sent to DLQ Queue");
            channel.basicNack(tag, false, false);
        }
    }
    @RabbitListener(queues = DLQConfig.dlqQueue, containerFactory = "AN_LCF")
    public void fun2(DataDLQ data) {
        IO.println("Data from DLQ Queue : ");
        IO.println(data.name());
        IO.println(data.email());
    }
}
