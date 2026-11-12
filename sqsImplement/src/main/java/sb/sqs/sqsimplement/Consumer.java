package sb.sqs.sqsimplement;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

import javax.crypto.IllegalBlockSizeException;

import static java.lang.System.err;

@Component
public class Consumer {
    @SqsListener("order-queue")
    public void getQueue(String message) throws IllegalBlockSizeException {
        if(message!=null && message.length()>10){
            err.println("Message length exceed 10");
            throw new IllegalBlockSizeException();
        }
        IO.println("Message Received Successfully "+message);
    }
    @SqsListener("order-queue-dlq")
    public void getDLQQueue(String message) throws IllegalBlockSizeException {
        IO.println("Message Received DLQ Successfully "+message);
    }
}
