package com.playground.kinesis.spring.kinesis.producer;

import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

@Service
public class CustomProducer {

    @Autowired
    KinesisProducer producer;

    @Value("${kinesis.stream.name}")
    private String streamName;

    public UserRecordResult sendData(String eventKey, Object data) throws ExecutionException, InterruptedException, IOException {
        ByteBuffer objectData = ByteBuffer.wrap(serializeObject(data));
        return this.sendData(eventKey, objectData);
    }

    public UserRecordResult sendData(String eventKey, ByteBuffer data) throws ExecutionException, InterruptedException {
        ListenableFuture<UserRecordResult> result = producer.addUserRecord(streamName, eventKey, data);
        return result.get();
    }

    // Method to convert object to a byte array
    private static byte[] serializeObject(Object obj) throws IOException
    {
        return new ObjectMapper().writeValueAsString(obj).getBytes();
    }

}
