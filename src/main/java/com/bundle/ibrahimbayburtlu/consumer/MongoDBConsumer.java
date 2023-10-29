package com.bundle.ibrahimbayburtlu.consumer;

import com.bundle.ibrahimbayburtlu.entity.MongoEntity;
import com.bundle.ibrahimbayburtlu.repository.MongoDBRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDBConsumer {

    private final MongoDBRepository mongoDBRepository;

    @Autowired
    public MongoDBConsumer(MongoDBRepository mongoDBRepository) {
        this.mongoDBRepository = mongoDBRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name.mongo}"})
    public void consume(String message){

        System.out.println("MongoDB Message : " + message);

        int hashValue = Integer.parseInt(message.substring(message.length() - 2), 16);
        MongoEntity mongoEntity = new MongoEntity();

        mongoEntity.setContent(message);

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
    }
}
