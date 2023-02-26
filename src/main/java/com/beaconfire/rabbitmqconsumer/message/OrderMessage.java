package com.beaconfire.rabbitmqconsumer.message;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage implements Serializable{
    private Double totalPrice;
    private Timestamp date;
    private List<ItemMessage> itemMessageList;
}
