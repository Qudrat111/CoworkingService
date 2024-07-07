package resources;

import Model.ApiResponse;
import Model.Reservation;
import Service.ReservationService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationResource {
    ReservationService reservationService = new ReservationService();

    public ReservationResource() throws SQLException {
    }

    public ApiResponse bookResource(String userId, String resourceId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {
        boolean b = reservationService.bookResource(userId, resourceId, startTime, endTime);
        if (b) {
            return new ApiResponse("booked resource successfully",200,b);
        }
        return new ApiResponse("booking failed",400,b);
    }

    public ApiResponse cancelReservation(String reservationId) throws SQLException {
        boolean b = reservationService.cancelReservation(reservationId);
        if (b) {
             return new ApiResponse("cancelled resource successfully",200,b);
        }
        return new ApiResponse("cancelled resource failed",400,b);
    }

    public ApiResponse getReservations() throws SQLException {
        List<Reservation> reservations = reservationService.getReservations();
        if (reservations.isEmpty()) {
            return new ApiResponse("no reservations found",404,reservations);
        }
        return new ApiResponse("found reservations",200,reservations);
    }

    public ApiResponse getReservationsByUser(String userId) throws SQLException {
        List<Reservation> reservationsByUser = reservationService.getReservationsByUser(userId);
        if (reservationsByUser.isEmpty()) {
            return new ApiResponse("no reservations found",404,reservationsByUser);
        }
        return new ApiResponse("found reservations",200,reservationsByUser);
    }
}
