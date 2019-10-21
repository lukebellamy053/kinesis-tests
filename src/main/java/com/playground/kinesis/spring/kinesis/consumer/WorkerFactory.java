package com.playground.kinesis.spring.kinesis.consumer;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerFactory {

    @Autowired
    private IRecordProcessorFactory consumer;
    @Autowired
    private KinesisClientLibConfiguration config;


    public Runnable runConsumer() {
       return getWorker();
    }

    private Worker getWorker() {
        return new Worker.Builder()
                .recordProcessorFactory(consumer)
                .config(config)
                .build();
    }
}
