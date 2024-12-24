package com.example.demo;

import com.example.demo.grpc.InspectedPersonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) throws InterruptedException {
		// Запускаем Spring Boot приложение
		SpringApplication.run(ClientApplication.class, args);

		// Создаем объект клиента
		InspectedPersonClient client = new InspectedPersonClient();

		// Используем CountDownLatch для синхронизации
		CountDownLatch latch = new CountDownLatch(1);

		// Вызываем метод для получения всех проверенных лиц
		client.fetchAllInspectedPersons(latch);

		// Ожидаем завершения асинхронных запросов
		latch.await();

		// Завершаем работу программы
		System.out.println("gRPC ended.");
	}
}
