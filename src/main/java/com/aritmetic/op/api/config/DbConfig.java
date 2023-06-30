package com.aritmetic.op.api.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Value("${cloud.aws.credentials.secret-value}")
    private String secretValue;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.credentials.secret-name}")
    private String secretName;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.db.schema}")
    private String schema;

    @Bean
    public DataSource dataSource() {

        GetSecretValueResult getSecretValueResponse;

        try {
            getSecretValueResponse = AWSSecretsManagerClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(
                            new BasicAWSCredentials(secretKey, secretValue)))
                    .build().getSecretValue(new GetSecretValueRequest()
                            .withSecretId(secretName));
        } catch (Exception e) {
            throw e;
        }

        AwsSecrets awsSecrets = new Gson().fromJson(getSecretValueResponse.getSecretString(), AwsSecrets.class);
        return DataSourceBuilder
                .create()
                .url("jdbc:" + awsSecrets.getEngine() + "://" + awsSecrets.getHost() + ":" + awsSecrets.getPort() + "/" + schema)
                .username(awsSecrets.getUsername())
                .password(awsSecrets.getPassword()).build();
    }
}
