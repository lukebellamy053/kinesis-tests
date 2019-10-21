package com.playground.kinesis.spring.kinesis.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.playground.kinesis.spring.kinesis.consumer.WorkerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class KinesisConfig {

    @Value("${kinesis.stream.name}")
    private String streamName;

    @Bean
    public KinesisProducerConfiguration getProducerConfig() {
        // There are many configurable parameters in the KPL. See the javadocs
        // on each each set method for details.
        KinesisProducerConfiguration config = new KinesisProducerConfiguration();
        config.setRegion("eu-west-1");
        config.setCredentialsProvider(new DefaultAWSCredentialsProviderChain());
        config.setKinesisEndpoint("localhost");
        config.setKinesisPort(4568);
        config.setVerifyCertificate(false);
        config.setMaxConnections(5);
        config.setRequestTimeout(60000);
        config.setRecordMaxBufferedTime(2000);
        return config;
    }

    @Bean
    public KinesisProducer getProducer(KinesisProducerConfiguration configuration) {
        return new KinesisProducer(configuration);
    }

    @Bean
    public KinesisClientLibConfiguration getKinesisClientConfig(DefaultAWSCredentialsProviderChain credentials) {
       return new KinesisClientLibConfiguration(
                "KinesisProducerLibSampleConsumer",
                streamName,
                credentials,
                "KinesisProducerLibSampleConsumer")
                .withDynamoDBEndpoint("localhost:4569")
                .withKinesisEndpoint("localhost:4568")
                .withRegionName("eu-west-1")
                .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON);
    }

    @Bean
    public DefaultAWSCredentialsProviderChain getAWSCredentials() {
        return new DefaultAWSCredentialsProviderChain();
    }

}
