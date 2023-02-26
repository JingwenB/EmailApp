package com.beaconfire.rabbitmqconsumer.email;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

public class EmailMessage {
    private String to;
    private String subject;
    private String body;

    // getter and setter methods
}