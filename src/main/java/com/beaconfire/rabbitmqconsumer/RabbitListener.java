package com.beaconfire.rabbitmqconsumer;

import com.beaconfire.rabbitmqconsumer.message.UserMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class RabbitListener implements MessageListener {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onMessage(Message message) {


        System.out.println("New message received from "
                + message.getMessageProperties().getConsumerQueue()
                + ": "
                + new String(message.getBody()));

        String jsonString = new String(message.getBody());

        try {
            UserMessage userMessage = new ObjectMapper().readValue(jsonString, UserMessage.class);

            AtomicInteger i = new AtomicInteger();
            String text = "Here is " + userMessage.getUserName() + " daily order history report";

            text +=  userMessage.getOrderMessageList().stream().map(
                    orderMessage -> {
                        i.getAndIncrement();
                        String items = orderMessage.getItemMessageList()
                                .toString()
                                .replace("[", "")
                                .replace("]", "")
                                .replace("ItemMessage(", "")
                                .replace("), ", "\n                            ")
                                .replace(")", "\n                         ");
                        return "\n\nOrder number:" + i + "\n" +
                                "            order date:" + orderMessage.getDate() + "\n" +
                                "            total order price:" + orderMessage.getTotalPrice() + "\n" +
                                "            order Items: "  + items;
                                    })
                            .collect(Collectors.joining( "\n" ) );
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(userMessage.getUserEmail());
            mail.setSubject("Daily report for "+ userMessage.getUserName());

            mail.setText(text);
            javaMailSender.send(mail);



        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
