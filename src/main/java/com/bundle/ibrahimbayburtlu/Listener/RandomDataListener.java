package com.bundle.ibrahimbayburtlu.Listener;

import com.bundle.ibrahimbayburtlu.consumer.MongoDBConsumer;
import com.bundle.ibrahimbayburtlu.publisher.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class RandomDataListener implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RandomDataListener.class);

    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public RandomDataListener(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    public void run() {
        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Socket is resting...");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String data = reader.readLine();
                    processAndSendToQueueOrFile(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processAndSendToQueueOrFile(String data) {
        String[] parts = data.split(",");

        if (parts.length >= 2) {
            String secondField = parts[1].trim().replaceAll("[^0-9]", "");

            try {
                int secondFieldValue = Integer.parseInt(secondField);
                if (secondFieldValue > 90) {
                    sendMessageToQueue(data);
                } else {
                    logger.info("File data added!!!");
                    writeToFile(data);
                }
            } catch (NumberFormatException e) {
                logger.info("Error: Conversion to numeric value failed.");
            }
        }
    }

    private void sendMessageToQueue(String data) {
        logger.info("Data is being sent to the message queue: " + data);
        rabbitMQProducer.sendMessage(data);
    }

    private void writeToFile(String data) {
        try (FileWriter writer = new FileWriter("filtered_data.txt", true)) {
            writer.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
