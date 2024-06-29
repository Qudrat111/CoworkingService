import Model.*;
import Service.ReservationService;
import Service.ResourceService;
import Service.UserService;
import Utility.Authenticator;
import resources.ReservationResource;
import resources.ResourcesResource;
import resources.UserResource;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CoworkingServiceApp {
    private static UserResource userResource = new UserResource();
    private static ResourcesResource resourcesResource;

    static {
        try {
            resourcesResource = new ResourcesResource();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ReservationResource reservationResource;

    static {
        try {
            reservationResource = new ReservationResource();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
                        ApiResponse register = userResource.register(new User(username, password, isAdmin));
                        System.out.println(register.getMessage());
                        System.out.println(register.getCode());
                    } else if (choice == 2) {
                        System.out.println("Enter username:");
                        String username = scanner2.nextLine();
                        System.out.println("Enter password:");
                        String password = scanner2.nextLine();
                        ApiResponse login = userResource.login(username, password);
                        System.out.println(login.getMessage());
                        System.out.println(login.getCode());
                        if (login.getCode() == 200) {
                            loggedInUser = (User) login.getData();
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
                            ApiResponse allWorkplaces = resourcesResource.getAllWorkplaces();
                            System.out.println(allWorkplaces.getMessage());
                            System.out.println(allWorkplaces.getCode());
                            if (allWorkplaces.getCode() == 200) {
                                Map<String, WorkPlace> data = (Map)allWorkplaces.getData();
                                data.values().forEach(System.out::println);
                            }
                            break;
                        case 2:
                            ApiResponse allMeetingRooms = resourcesResource.getAllMeetingRooms();
                            System.out.println(allMeetingRooms.getMessage());
                            System.out.println(allMeetingRooms.getCode());
                            if (allMeetingRooms.getCode() == 200) {
                                Map<String, MeetingRoom> data = (Map)allMeetingRooms.getData();
                                data.values().forEach(System.out::println);
                            }
                            break;
                        case 3:
                            System.out.println("Enter workplace ID:");
                            String wpId = scanner2.nextLine();
                            System.out.println("Enter start time (yyyy-MM-ddTHH:mm):");
                            LocalDateTime wpStart = LocalDateTime.parse(scanner2.nextLine());
                            System.out.println("Enter end time (yyyy-MM-ddTHH:mm):");
                            LocalDateTime wpEnd = LocalDateTime.parse(scanner2.nextLine());
                            ApiResponse apiResponse = reservationResource.bookResource(loggedInUser.getUsername(), wpId, wpStart, wpEnd);
                            System.out.println(apiResponse.getMessage());
                            System.out.println(apiResponse.getCode());
                            break;
                        case 4:
                            System.out.println("Enter meeting room ID:");
                            String mrId = scanner2.nextLine();
                            System.out.println("Enter start time (yyyy-MM-ddTHH:mm):");
                            LocalDateTime mrStart = LocalDateTime.parse(scanner2.nextLine());
                            System.out.println("Enter end time (yyyy-MM-ddTHH:mm):");
                            LocalDateTime mrEnd = LocalDateTime.parse(scanner2.nextLine());
                            ApiResponse apiResponse1 = reservationResource.bookResource(loggedInUser.getUsername(), mrId, mrStart, mrEnd);
                            System.out.println(apiResponse1.getMessage());
                            System.out.println(apiResponse1.getCode());
                            break;
                        case 5:
                            System.out.println("Enter reservation ID to cancel:");
                            String resId = scanner2.nextLine();
                            ApiResponse apiResponse2 = reservationResource.cancelReservation(resId);
                            System.out.println(apiResponse2.getMessage());
                            System.out.println(apiResponse2.getCode());
                            break;
                        case 6:
                            ApiResponse reservationsByUser = reservationResource.getReservationsByUser(loggedInUser.getUsername());
                            System.out.println(reservationsByUser.getMessage());
                            System.out.println(reservationsByUser.getCode());
                            if (reservationsByUser.getCode() == 200) {
                                List<User> data = (List<User>) reservationsByUser.getData();
                                data.forEach(System.out::println);
                            }
                            break;
                        case 7:
                            if (loggedInUser.isAdmin()) {
                                System.out.println("Enter workplace ID:");
                                String newWpId = scanner2.nextLine();
                                System.out.println("Enter workplace location:");
                                String location = scanner2.nextLine();
                                ApiResponse apiResponse3 = resourcesResource.addWorkplace(newWpId, location);
                                System.out.println(apiResponse3.getMessage());
                                System.out.println(apiResponse3.getCode());
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
                                ApiResponse apiResponse3 = resourcesResource.addMeetingRoom(newMrId, name);
                                System.out.println(apiResponse3.getMessage());
                                System.out.println(apiResponse3.getCode());
                            } else {
                                System.out.println("Access denied.");
                            }
                            break;
                        case 9:
                            ApiResponse reservations = reservationResource.getReservations();
                            System.out.println(reservations.getMessage());
                            System.out.println(reservations.getCode());
                            if (reservations.getCode() == 200) {
                                List<Reservation> data = (List<Reservation>) reservations.getData();
                                data.forEach(System.out::println);
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