package sb.sns.sesimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

@Service
public class service {

    @Autowired
    private SesV2Client sesClient;

    public String sendEmail(String from, String to, String content) {
        Destination destination = Destination.builder()
                .toAddresses(to).build();
        Content subject = Content.builder()
                .data("SES Mail Testing")
                .charset("UTF-8")
                .build();
        Content emailContent = Content.builder()
                .data(content)
                .charset("UTF-8")
                .build();
        Body body = Body.builder()
                .text(emailContent)
                .build();
        Message message = Message.builder()
                .subject(subject)
                .body(body)
                .build();
        EmailContent emailContent1 = EmailContent.builder()
                .simple(message)
                .build();
        SendEmailRequest.Builder request = SendEmailRequest.builder()
                .fromEmailAddress(from)
                .destination(destination)
                .content(emailContent1);
        SendEmailResponse response = sesClient.sendEmail(request.build());
        return "Email Sent Successfully and the message ID: " + response.messageId();
    }
}
