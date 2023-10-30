package com.bundle.ibrahimbayburtlu.consumer;

import com.bundle.ibrahimbayburtlu.entity.MongoEntity;
import com.bundle.ibrahimbayburtlu.repository.MongoDBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoDBConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBConsumer.class);

    private final MongoDBRepository mongoDBRepository;

    @Autowired
    public MongoDBConsumer(MongoDBRepository mongoDBRepository) {
        this.mongoDBRepository = mongoDBRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name.mongo}"})
    public void consume(String message) {
        logger.info("MongoDB Message: " + message);

        int hashValue = Integer.parseInt(message.substring(message.length() - 2), 16);

        MongoEntity mongoEntity = new MongoEntity();
        mongoEntity.setContent(message);

        List<MongoEntity> existingRecords = mongoDBRepository.findAll();
        if (!existingRecords.isEmpty()) {
            MongoEntity lastRecord = existingRecords.get(existingRecords.size() - 1);

            int lastHashValue = Integer.parseInt(lastRecord.getContent().substring(lastRecord.getContent().length() - 2), 16);

            if (hashValue > 99 && lastHashValue > 99) {
                lastRecord.addNestedMessage(mongoEntity);
                mongoDBRepository.save(lastRecord);
            } else {
                MongoEntity newRecord = new MongoEntity();
                newRecord.setContent(message);
                mongoDBRepository.save(newRecord);
            }
        } else {
            MongoEntity newRecord = new MongoEntity();
            newRecord.setContent(message);
            mongoDBRepository.save(newRecord);
        }

        logger.info("MongoDB message logged");
    }
}
