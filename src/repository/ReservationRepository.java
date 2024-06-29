package repository;

import Model.Reservation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepository {
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASSWORD = "1234";
    Connection connection;
    public ReservationRepository() throws SQLException {
        connection = DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public boolean bookResource(String userId, String resourceId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from BookResource where resource_id = ? ");
        preparedStatement.setString(1, resourceId);
        ResultSet resultSet = preparedStatement.executeQuery();
      while(resultSet.next()) {
         return false;
      }
        PreparedStatement preparedStatement1 = connection.prepareStatement("insert into (user_id,resource_id,start_time,end_time) values (?,?,?,?)");
      preparedStatement1.setString(1, userId);
      preparedStatement1.setString(2, resourceId);
      preparedStatement1.setString(3, startTime.toString());
      preparedStatement1.setString(4, endTime.toString());
      return preparedStatement1.execute();
    }

    public boolean cancelReservation(String reservationId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from Reservation where id = ?");
        preparedStatement.setString(1, reservationId);
        return preparedStatement.execute();
    }



    public List<Reservation> getReservations() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Reservation");
        List<Reservation> reservations = new ArrayList<>();
        while(resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(resultSet.getString("id"));
            reservation.setUserId(resultSet.getString("user_id"));
            reservation.setResourceId(resultSet.getString("resource_id"));
            reservation.setStartTime(resultSet.getTime("start_time"));
            reservation.setEndTime(resultSet.getTime("end_time"));
            reservations.add(reservation);
        }
        return reservations;
    }

    public List<Reservation> getReservationsByUser(String userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Reservation where user_id = ? ");
        preparedStatement.setString(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Reservation> reservations = new ArrayList<>();
        while(resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(resultSet.getString("id"));
            reservation.setUserId(resultSet.getString("user_id"));
            reservation.setResourceId(resultSet.getString("resource_id"));
            reservation.setStartTime(resultSet.getTime("start_time"));
            reservation.setEndTime(resultSet.getTime("end_time"));
            reservations.add(reservation);
        }
        return reservations;
    }
}
