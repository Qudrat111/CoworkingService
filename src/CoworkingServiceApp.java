import Model.User;
import Service.ReservationService;
import Service.ResourceService;
import Service.UserService;
import Utility.Authenticator;

import java.time.LocalDateTime;
import java.util.Scanner;

public class CoworkingServiceApp {
    private static UserService userService = new UserService();
    private static ResourceService resourceService = new ResourceService();
    private static ReservationService reservationService = new ReservationService();
    private static Authenticator authenticator = new Authenticator(userService);
    private static User loggedInUser;

    public static void main(String[] args) {
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);

        while(true) {
            try {

                if (loggedInUser == null) {
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    int choice = scanner1.nextInt();

                    if (choice == 1) {
                        System.out.println("Enter username:");
                        String username = scanner2.nextLine();
                        System.out.println("Enter password:");
                        String password = scanner2.nextLine();
                        System.out.println("Are you an admin? (true/false):");
                        boolean isAdmin = scanner1.nextBoolean();
                        if (userService.registerUser(username, password, isAdmin)) {
                            System.out.println("User registered successfully!");
                        } else {
                            System.out.println("User registration failed. Username might already be taken.");
                        }
                    } else if (choice == 2) {
                        System.out.println("Enter username:");
                        String username = scanner2.nextLine();
                        System.out.println("Enter password:");
                        String password = scanner2.nextLine();
                        User user = authenticator.login(username, password);
                        if (user != null) {
                            loggedInUser = user;
                            System.out.println("Login successful!");
                        } else {
                            System.out.println("Invalid credentials.");
                        }
                    }
                } else {
                    System.out.println("1. View available workplaces");
                    System.out.println("2. View available meeting rooms");
                    System.out.println("3. Book a workplace");
                    System.out.println("4. Book a meeting room");
                    System.out.println("5. Cancel a reservation");
                    System.out.println("6. View my reservations");
                    if (loggedInUser.isAdmin()) {
                        System.out.println("7. Add a workplace");
                        System.out.println("8. Add a meeting room");
                        System.out.println("9. View all reservations");
                    }
                    System.out.println("10. Logout");
                    int choice = scanner1.nextInt();
                    switch (choice) {
                        case 1:
                            resourceService.getAllWorkplaces().values().forEach(System.out::println);
                            break;
                        case 2:
                            resourceService.getAllMeetingRooms().values().forEach(System.out::println);
                            break;
                        case 3:
                            System.out.println("Enter workplace ID:");
                            String wpId = scanner2.nextLine();
                            System.out.println("Enter start time (yyyy-MM-ddTHH:mm):");
                            LocalDateTime wpStart = LocalDateTime.parse(scanner2.nextLine());
                            System.out.println("Enter end time (yyyy-MM-ddTHH:mm):");
                            LocalDateTime wpEnd = LocalDateTime.parse(scanner2.nextLine());
                            if (reservationService.bookResource(loggedInUser.getUsername(), wpId, wpStart, wpEnd)) {
                                System.out.println("Booking successful!");
                            } else {
                                System.out.println("Booking failed. Time slot might be unavailable.");
                            }
                            break;
                        case 4:
                            System.out.println("Enter meeting room ID:");
                            String mrId = scanner2.nextLine();
                            System.out.println("Enter start time (yyyy-MM-ddTHH:mm):");
                            LocalDateTime mrStart = LocalDateTime.parse(scanner2.nextLine());
                            System.out.println("Enter end time (yyyy-MM-ddTHH:mm):");
                            LocalDateTime mrEnd = LocalDateTime.parse(scanner2.nextLine());
                            if (reservationService.bookResource(loggedInUser.getUsername(), mrId, mrStart, mrEnd)) {
                                System.out.println("Booking successful!");
                            } else {
                                System.out.println("Booking failed. Time slot might be unavailable.");
                            }
                            break;
                        case 5:
                            System.out.println("Enter reservation ID to cancel:");
                            String resId = scanner2.nextLine();
                            if (reservationService.cancelReservation(resId)) {
                                System.out.println("Cancellation successful!");
                            } else {
                                System.out.println("Cancellation failed. Reservation ID might be invalid.");
                            }
                            break;
                        case 6:
                            reservationService.getReservationsByUser(loggedInUser.getUsername())
                                    .forEach(System.out::println);
                            break;
                        case 7:
                            if (loggedInUser.isAdmin()) {
                                System.out.println("Enter workplace ID:");
                                String newWpId = scanner2.nextLine();
                                System.out.println("Enter workplace location:");
                                String location = scanner2.nextLine();
                                resourceService.addWorkplace(newWpId, location);
                                System.out.println("Workplace added successfully!");
                            } else {
                                System.out.println("Access denied.");
                            }
                            break;
                        case 8:
                            if (loggedInUser.isAdmin()) {
                                System.out.println("Enter meeting room ID:");
                                String newMrId = scanner2.nextLine();
                                System.out.println("Enter meeting room name:");
                                String name = scanner2.nextLine();
                                resourceService.addMeetingRoom(newMrId, name);
                                System.out.println("Meeting room added successfully!");
                            } else {
                                System.out.println("Access denied.");
                            }
                            break;
                        case 9:
                            if (loggedInUser.isAdmin()) {
                                reservationService.getReservations()
                                        .forEach(System.out::println);
                            } else {
                                System.out.println("Access denied.");
                            }
                            break;
                        case 10:
                            loggedInUser = null;
                            System.out.println("Logged out successfully.");
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}