package sb.sns.snsimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.Map;
import java.util.UUID;

@Service
public class SNSPublisher {

    @Autowired
    private SnsClient snsClient;

    public String publish(String message) {
        PublishRequest request = PublishRequest.builder()
                .topicArn("topic-arn")
                .message(message)
                .messageAttributes(
                        Map.of("routingKey", MessageAttributeValue.builder()
                                        .dataType("String")
                                        .stringValue("order.done")
                                        .build()))
                .messageGroupId("order-1")
                .messageDeduplicationId(UUID.randomUUID().toString())
                .build();
        PublishResponse response = snsClient.publish(request);
        return response.messageId();
    }

}
