package repository;

import Model.Reservation;
import util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private Connection connection;

    public ReservationRepository() throws SQLException {
        connection = DatabaseUtil.getConnection();
    }

    public boolean bookResource(String userId, String resourceId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {
        String checkAvailabilityQuery = "SELECT * FROM BookResource WHERE resource_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkAvailabilityQuery)) {
            preparedStatement.setString(1, resourceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }
        }

        String insertReservationQuery = "INSERT INTO Reservation (user_id, resource_id, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertReservationQuery)) {
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, resourceId);
            preparedStatement.setString(3, startTime.toString());
            preparedStatement.setString(4, endTime.toString());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public boolean cancelReservation(String reservationId) throws SQLException {
        String deleteReservationQuery = "DELETE FROM Reservation WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteReservationQuery)) {
            preparedStatement.setString(1, reservationId);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public List<Reservation> getReservations() throws SQLException {
        String getReservationsQuery = "SELECT * FROM Reservation";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getReservationsQuery)) {
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                reservations.add(mapResultSetToReservation(resultSet));
            }
            return reservations;
        }
    }

    public List<Reservation> getReservationsByUser(String userId) throws SQLException {
        String getReservationsByUserQuery = "SELECT * FROM Reservation WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getReservationsByUserQuery)) {
            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Reservation> reservations = new ArrayList<>();
                while (resultSet.next()) {
                    reservations.add(mapResultSetToReservation(resultSet));
                }
                return reservations;
            }
        }
    }

    private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getString("id"));
        reservation.setUserId(resultSet.getString("user_id"));
        reservation.setResourceId(resultSet.getString("resource_id"));
        reservation.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
        reservation.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());
        return reservation;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
