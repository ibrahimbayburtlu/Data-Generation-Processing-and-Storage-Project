package com.bundle.ibrahimbayburtlu.consumer;

import com.bundle.ibrahimbayburtlu.entity.MongoEntity;
import com.bundle.ibrahimbayburtlu.repository.MongoDBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDBConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBConsumer.class);

    private final MongoDBRepository mongoDBRepository;

    @Autowired
    public MongoDBConsumer(MongoDBRepository mongoDBRepository) {
        this.mongoDBRepository = mongoDBRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name.mongo}"})
    public void consume(String message){

        logger.info("MongoDB Message : " + message);

        int hashValue = Integer.parseInt(message.substring(message.length() - 2), 16);
        MongoEntity mongoEntity = new MongoEntity();

        mongoEntity.setContent(message);
        // Available Hatası verdi nedeni Driver kütüphanesi güncel değil bakılacak.
        /*
        if (hashValue > 99) {
            List<MongoEntity> existingRecords = mongoDBRepository.findAll();
            if (!existingRecords.isEmpty()) {
                MongoEntity existingRecord = existingRecords.get(0);
                existingRecord.getNestedMessages().add(mongoEntity);
                mongoDBRepository.save(existingRecord);
            }

            System.out.println(existingRecords);
        }
        */
        mongoDBRepository.save(mongoEntity);
        logger.info("MongoDB message logged");
    }
}
