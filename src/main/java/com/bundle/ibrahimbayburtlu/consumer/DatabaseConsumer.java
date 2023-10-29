package com.bundle.ibrahimbayburtlu.consumer;

import com.bundle.ibrahimbayburtlu.entity.MessageEntity;
import com.bundle.ibrahimbayburtlu.repository.MessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConsumer {

    private final MessageRepository messageRepository;

    @Autowired
    public DatabaseConsumer(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){
        System.out.println("Received a message from RabbitMQ: " + message);

        // Mesajı veritabanına kaydet :)
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message);
        messageRepository.save(messageEntity);
        System.out.println("Mesaj database kaydetildi :)");
    }
}