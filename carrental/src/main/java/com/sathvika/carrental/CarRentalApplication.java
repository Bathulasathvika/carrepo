package com.sathvika.carrental;

import com.sathvika.carrental.model.User;
import com.sathvika.carrental.service.CarService;
import com.sathvika.carrental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@SpringBootApplication
@RequestMapping("/")
public class CarRentalApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(CarRentalApplication.class, args);
		CarService carService = context.getBean(CarService.class);
		UserService userService = context.getBean(UserService.class);

		Scanner scanner = new Scanner(System.in);

		System.out.println("Hey! Welcome to the Car Rental System!");
		System.out.print("Please Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Please enter the Password: ");
		String password = scanner.nextLine();

		User currentUser = userService.authenticateUser(username, password);
		if (currentUser == null) {
			System.out.println("Invalid credentials. No user exists!");
			return;
		}

		if (currentUser.isAdmin()) {
			adminMenu(currentUser, scanner, carService, userService);
		} else {
			publicUserMenu(currentUser, scanner, carService);
		}

		System.out.println("Goodbye!");
	}

	private static void adminMenu(User user, Scanner scanner, CarService carService, UserService userService) {
		while (true) {
			System.out.println("Here is the Menu for Admin:");
			System.out.println("1. View all cars");
			System.out.println("2. Set car price");
			System.out.println("3. Check user-car map");
			System.out.println("4. Logout");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();

			switch (choice) {
				case 1:
					carService.getAllCars().forEach(car -> System.out.println(car.getName() + " - " + (car.isAvailable() ? "Available" : "Not Available") + " - Price: " + car.getPrice()));
					break;

				case 2:
					System.out.print("Enter the Car ID to set price: ");
					int carId = scanner.nextInt();
					System.out.print("Enter new price: ");
					double newPrice = scanner.nextDouble();
					if (carService.setCarPrice(carId, newPrice)) {
						System.out.println("Price updated successfully!");
					} else {
						System.out.println("Invalid Car ID. Try again.");
					}
					break;

				case 3:
					userService.getAllUsers().forEach(u -> {
						if (!u.isAdmin() && u.getRentedCar() != null) {
							System.out.println(u.getUsername() + " has rented " + u.getRentedCar().getName());
						}
					});
					break;
				case 4:
					return;
				default:
					System.out.println("Invalid choice. Please Try again....");
			}
		}
	}

	private static void publicUserMenu(User user, Scanner scanner, CarService carService) {
		while (true) {
			System.out.println("Public User Menu:");
			System.out.println("1. View all cars");
			System.out.println("2. Rent a car");
			System.out.println("3. Return a car");
			System.out.println("4. Logout");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();

			switch (choice) {
				case 1:
					carService.getAllCars().forEach(car -> System.out.println(car.getName() + " - " + (car.isAvailable() ? "Available" : "Not Available") + " - Price: " + car.getPrice()));
					break;

				case 2:
					System.out.print("Enter the Car ID to rent: ");
					int carId = scanner.nextInt();
					if (carService.rentCar(user, carId)) {
						System.out.println("Car rented successfully!");
					} else {
						System.out.println("Car is not available or invalid ID.");
					}
					break;
				case 3:
					System.out.print("Enter the Car ID to return: ");
					int returnCarId = scanner.nextInt();
					if (carService.returnCar(user, returnCarId)) {
						System.out.println("Car returned successfully!");
					} else {
						System.out.println("Invalid Car ID or car not rented by you.");
					}
					break;

				case 4:
					return;
				default:
					System.out.println("Invalid choice. Try again.");
			}
		}
	}
}
