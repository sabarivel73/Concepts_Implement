package sb.sns.sesimplement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.SesV2Client;

@Configuration
public class Config {
    @Value("${spring.cloud.aws.credentials.access-key}") String accessKey;
    @Value("${spring.cloud.aws.credentials.secret-key}") String secretKey;
    @Value("${spring.cloud.aws.region.static}") String region;

    @Bean
    public SesV2Client sesClient() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return SesV2Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
