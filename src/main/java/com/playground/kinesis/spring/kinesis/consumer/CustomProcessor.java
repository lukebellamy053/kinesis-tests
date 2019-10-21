package com.playground.kinesis.spring.kinesis.consumer;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.kinesis.spring.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.List;

@Slf4j
public class CustomProcessor implements IRecordProcessor {

    @Override
    public void initialize(String shardId) {
        log.info("New consumer created");
    }

    @Override
    public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
        for(Record record: records) {
            try {
                Message message = new ObjectMapper().readValue(record.getData().array(), Message.class);
                log.info(String.format("New Record found with name %s and value %s", record.getPartitionKey(), message.getMessage()));
            } catch (ClassCastException err) {
                log.error("Failed to cast message");
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {

    }

}
