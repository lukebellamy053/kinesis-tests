package com.playground.kinesis.spring.controller;

import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.playground.kinesis.spring.kinesis.consumer.WorkerFactory;
import com.playground.kinesis.spring.kinesis.producer.CustomProducer;
import com.playground.kinesis.spring.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
public class KinesisController {

    private CustomProducer producer;

    @Autowired
    public KinesisController(CustomProducer producer, WorkerFactory factory, @Qualifier("applicationTaskExecutor") TaskExecutor executor) {
        this.producer = producer;
        executor.execute(factory.runConsumer());
    }

    @GetMapping("/")
    public String getLastEventData() {
        return "Nope";
    }

    @PostMapping("/")
    public UserRecordResult newEvent(@RequestParam String key, @RequestParam String data) throws ExecutionException, InterruptedException, IOException {
        final Message message = new Message("App1", "App2", data);
        return producer.sendData(key, message);
    }

}
