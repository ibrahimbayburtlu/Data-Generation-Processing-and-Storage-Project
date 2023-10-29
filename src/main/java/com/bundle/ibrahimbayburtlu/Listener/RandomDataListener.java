package com.bundle.ibrahimbayburtlu.Listener;

import com.bundle.ibrahimbayburtlu.publisher.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class RandomDataListener implements Runnable {

    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public RandomDataListener(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    public void run() {
        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Socket dinleniyor...");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    System.out.println("Socket içine girdi");
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
                    System.out.println("File data eklendi!!!");
                    writeToFile(data);
                }
            } catch (NumberFormatException e) {
                System.err.println("Hata: Sayısal değere dönüştürme başarısız.");
            }
        }
    }

    private void sendMessageToQueue(String data) {
        System.out.println("Veri message queue'ye gönderiliyor: " + data);
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
