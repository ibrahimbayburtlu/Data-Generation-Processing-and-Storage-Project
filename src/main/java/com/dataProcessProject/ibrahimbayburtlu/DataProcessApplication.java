package com.dataProcessProject.ibrahimbayburtlu;

import com.dataProcessProject.ibrahimbayburtlu.Generator.RandomDataGenerator;
import com.dataProcessProject.ibrahimbayburtlu.Listener.RandomDataListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DataProcessApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DataProcessApplication.class, args);

		// Start data generation thread
		Thread dataGeneratorThread = new Thread(context.getBean(RandomDataGenerator.class));
		dataGeneratorThread.start();

		// Start data listening thread
		Thread dataListenerThread = new Thread(context.getBean(RandomDataListener.class));
		dataListenerThread.start();
	}
}
