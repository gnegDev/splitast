package com.gnegdev.splitast.service.s3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Slf4j
@Configuration
public class S3ClientConfig {

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.url}")
    private String url;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        S3Client s3Client = S3Client.builder()
                .endpointOverride(URI.create(url))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();

        try {
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucket)
                    .build();

            s3Client.createBucket(createBucketRequest);

            log.info("Created bucket {}", bucket);
        } catch (Exception exception) {
            log.error("Error creating bucket: ", exception);
        }

        return s3Client;
    }

//    @Bean
//    public S3Presigner s3Presigner() {
//        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
//
//        return S3Presigner.builder()
//                .endpointOverride(URI.create(url))
//                .credentialsProvider(StaticCredentialsProvider.create(credentials))
//                .region(Region.of(region))
//                .serviceConfiguration(S3Configuration.builder()
//                        .pathStyleAccessEnabled(true)
//                        .build())
//                .build();
//    }
}