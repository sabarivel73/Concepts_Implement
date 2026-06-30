package sb.mq.mqImplement.AckNack;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenerAN {
    @RabbitListener(queues = ANConfig.queueNameAN, containerFactory = "AN_LCF")
    public void fun(DataAN data, Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try{
            IO.println("Data from AN : ");
            IO.println(data.name());
            IO.println(data.email());
            channel.basicAck(tag, false);
        } catch (Exception ex) {
            channel.basicNack(tag, false, false);
        }
    }
}
