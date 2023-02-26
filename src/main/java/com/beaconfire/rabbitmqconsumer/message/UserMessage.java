package com.beaconfire.rabbitmqconsumer.message;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage implements Serializable{
    private String userName;
    private String userEmail;
    private List<OrderMessage> orderMessageList;
}
