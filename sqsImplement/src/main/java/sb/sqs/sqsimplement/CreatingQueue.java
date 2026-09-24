package sb.sqs.sqsimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class CreatingQueue {
    @Autowired
    private SqsClient sqsClient;

    public String queueUrl(String queueName) {
        String queueUrl = createQueue(queueName);
        String dlqUrl = createDLQQueue(queueName);
        String queueArn = getQueueArn(queueUrl);
        String dlqArn = getQueueArn(dlqUrl);
        configMainQueueRedrivePolicy(queueUrl, dlqArn);
        configDlqRedriveAllowPolicy(dlqUrl, queueArn);
        return queueUrl+"\n"+dlqUrl;
    }

    public String createQueue(String queueName){
        Map<QueueAttributeName, String> attributes = new HashMap<>();
        attributes.put(QueueAttributeName.VISIBILITY_TIMEOUT, "60");
        attributes.put(QueueAttributeName.MESSAGE_RETENTION_PERIOD, "345600");
        attributes.put(QueueAttributeName.DELAY_SECONDS, "0");
        attributes.put(QueueAttributeName.MAXIMUM_MESSAGE_SIZE, "262144");
        attributes.put(QueueAttributeName.RECEIVE_MESSAGE_WAIT_TIME_SECONDS, "10");
        attributes.put(QueueAttributeName.SQS_MANAGED_SSE_ENABLED, "true");
        //attributes.put(QueueAttributeName.FIFO_QUEUE, "true");
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(queueName)
                .attributes(attributes)
                .build();
        CreateQueueResponse createQueueResponse = sqsClient.createQueue(createQueueRequest);
        return  createQueueResponse.queueUrl();
    }

    public String createDLQQueue(String queueName){
        queueName = queueName+"-dlq";
        Map<QueueAttributeName, String> attributes = new HashMap<>();
        attributes.put(QueueAttributeName.VISIBILITY_TIMEOUT, "60");
        attributes.put(QueueAttributeName.MESSAGE_RETENTION_PERIOD, "1209600");
        attributes.put(QueueAttributeName.DELAY_SECONDS, "0");
        attributes.put(QueueAttributeName.MAXIMUM_MESSAGE_SIZE, "262144");
        attributes.put(QueueAttributeName.RECEIVE_MESSAGE_WAIT_TIME_SECONDS, "10");
        attributes.put(QueueAttributeName.SQS_MANAGED_SSE_ENABLED, "true");
        //attributes.put(QueueAttributeName.FIFO_QUEUE, "true");
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(queueName)
                .attributes(attributes)
                .build();
        CreateQueueResponse createQueueResponse = sqsClient.createQueue(createQueueRequest);
        return  createQueueResponse.queueUrl();
    }

    public String getQueueArn(String queueUrl) {
        GetQueueAttributesResponse response = sqsClient.getQueueAttributes(GetQueueAttributesRequest.builder()
                .queueUrl(queueUrl)
                .attributeNames(QueueAttributeName.QUEUE_ARN)
                .build());
        return response.attributes().get(QueueAttributeName.QUEUE_ARN);
    }

    public void configMainQueueRedrivePolicy(String queueUrl, String dlqArn) {
        String redrivePolicy = """
                {
                     "deadLetterTargetArn": "%s",
                     "maxReceiveCount": "3"
                }
                """.formatted(dlqArn);
        sqsClient.setQueueAttributes(
                builder ->  builder
                        .queueUrl(queueUrl)
                        .attributes(Map.of(QueueAttributeName.REDRIVE_POLICY, redrivePolicy))
        );
    }

    public void configDlqRedriveAllowPolicy(String dlqUrl, String queueArn) {
        String allowPolicy = """
                {
                     "redrivePermission": "byQueue",
                     "sourceQueueArns": [
                             "%s"
                     ]
                }
                """.formatted(queueArn);
        sqsClient.setQueueAttributes(
                builder ->  builder
                        .queueUrl(dlqUrl)
                        .attributes(Map.of(QueueAttributeName.REDRIVE_ALLOW_POLICY, allowPolicy))
        );
    }

    public String getQueue(String queueName){
        GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
                        .queueName(queueName)
                        .build());
        return  getQueueUrlResponse.queueUrl();
    }
}
