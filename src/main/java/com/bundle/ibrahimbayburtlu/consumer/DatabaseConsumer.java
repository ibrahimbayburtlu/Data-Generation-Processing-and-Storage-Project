package com.bundle.ibrahimbayburtlu.consumer;

import com.bundle.ibrahimbayburtlu.entity.MessageEntity;
import com.bundle.ibrahimbayburtlu.repository.MessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DatabaseConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConsumer.class);

    private final MessageRepository messageRepository;

    @Autowired
    public DatabaseConsumer(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){
        logger.info("mySQL Consumer Received a message from RabbitMQ: " + message);
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message);
        messageRepository.save(messageEntity);
        logger.info("Message saved in mySQL database :) ");
    }
}