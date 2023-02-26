package com.beaconfire.rabbitmqconsumer.message;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemMessage implements Serializable{
    private String itemName;
    private Integer quantity;
}
