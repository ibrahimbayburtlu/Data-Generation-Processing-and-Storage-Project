package com.bundle.ibrahimbayburtlu;

import com.bundle.ibrahimbayburtlu.Generator.RandomDataGenerator;
import com.bundle.ibrahimbayburtlu.Listener.RandomDataListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BundleApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BundleApplication.class, args);

		// Start data generation thread
		Thread dataGeneratorThread = new Thread(context.getBean(RandomDataGenerator.class));
		dataGeneratorThread.start();

		// Start data listening thread
		Thread dataListenerThread = new Thread(context.getBean(RandomDataListener.class));
		dataListenerThread.start();
	}
}
