package com.dataProcessProject.ibrahimbayburtlu.Generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Random;

@Service
public class RandomDataGenerator implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RandomDataGenerator.class);

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            int randomNumber = new Random().nextInt(101);
            String dataToHash = timestamp.toString() + randomNumber;
            String md5Hash = calculateMD5Hash(dataToHash);
            String lastTwoCharacters = md5Hash.substring(md5Hash.length() - 2);

            String data = "Timestamp: " + timestamp + ", Random Integer: " + randomNumber + ", Last 2 Characters: " + lastTwoCharacters;

            logger.info(data);
            writeToSocket(data);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String calculateMD5Hash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(data.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, messageDigest);
            String hash = number.toString(16);

            while (hash.length() < 32) {
                hash = "0" + hash;
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeToSocket(String data) {
        try {
            Socket socket = new Socket("localhost", 12345);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
