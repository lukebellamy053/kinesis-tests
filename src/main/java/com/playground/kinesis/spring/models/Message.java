package com.playground.kinesis.spring.models;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message implements Serializable {
    private String from;
    private String to;
    private String message;
}
