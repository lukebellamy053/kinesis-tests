package com.playground.kinesis.spring.kinesis.consumer;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import org.springframework.stereotype.Service;

@Service
public class KinesisConsumer implements IRecordProcessorFactory {


    @Override
    public IRecordProcessor createProcessor() {
        return new CustomProcessor();
    }

}
