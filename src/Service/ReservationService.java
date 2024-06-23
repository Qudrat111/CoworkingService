package Service;

import Model.Reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    private List<Reservation> reservations = new ArrayList<>();
    static int id = 0;

    public boolean bookResource(String userId, String resourceId, LocalDateTime startTime, LocalDateTime endTime) {
        for(Reservation res : reservations) {
            if(res.getResourceId().equals(resourceId) &&
                    (res.getStartTime().isBefore(endTime) && res.getEndTime().isAfter(startTime))) {
                return false; // Conflict
            }
        }
        reservations.add(new Reservation(String.valueOf(id++), userId, resourceId, startTime, endTime));
        return true;
    }

    public boolean cancelReservation(String reservationId) {
        return reservations.removeIf(res -> res.getId().equals(reservationId));
    }



    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<Reservation> getReservationsByUser(String userId) {
        return reservations.stream().filter(res -> res.getUserId().equals(userId)).collect(Collectors.toList());
    }

}