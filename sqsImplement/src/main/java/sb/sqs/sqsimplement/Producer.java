package sb.sqs.sqsimplement;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private final SqsTemplate sqsTemplate;
    private final String queueName;

    public Producer(SqsTemplate sqsTemplate, CreatingQueue queue) {
        this.sqsTemplate = sqsTemplate;
        queueName = "queue_1";
        queue.queueUrl(queueName);
    }

    public void sendMessage(String message) {
        sqsTemplate.send(to -> to
        .queue(queueName).payload(message));
    }

}
