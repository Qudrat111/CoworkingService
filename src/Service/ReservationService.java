package Service;

import Model.Reservation;
import repository.ReservationRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationService {
    ReservationRepository reservationRepository = new ReservationRepository();

    static int id = 0;

    public ReservationService() throws SQLException {
    }

    public boolean bookResource(String userId, String resourceId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {
        boolean b = reservationRepository.bookResource(userId, resourceId, startTime, endTime);
        return b;
    }

    public boolean cancelReservation(String reservationId) throws SQLException {
        boolean b = reservationRepository.cancelReservation(reservationId);
        return b;
    }

    public List<Reservation> getReservations() throws SQLException {
       return reservationRepository.getReservations();
    }

    public List<Reservation> getReservationsByUser(String userId) throws SQLException {
        return reservationRepository.getReservationsByUser(userId);
    }

}