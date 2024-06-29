package repository;

import Model.MeetingRoom;
import Model.WorkPlace;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceRepository {
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "postgres";
    private final String PASSWORD = "1234";
    private Connection connection;
    public ResourceRepository() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connection.close();
        }

    }

    public boolean addWorkplace(String id, String location) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into WorkPlace(id,location) values (?,?)");
        try {
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,location);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            preparedStatement.close();
        }
    }

    public boolean addMeetingRoom(String id, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into MeetingRoom(id,location) values (?,?)");
        try {
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            preparedStatement.close();
        }
    }

    public Map<String, WorkPlace> getAllWorkplaces() {
        Map<String, WorkPlace> workplaces = new HashMap<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from WorkPlace where 1 = 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                workplaces.put(resultSet.getString("id"),new WorkPlace(resultSet.getString("id"),resultSet.getString("location")));
            }
            return workplaces;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Map<String, MeetingRoom> getAllMeetingRooms() {
        Map<String, MeetingRoom> meetingRooms = new HashMap<>();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select * from MeetingRooms where 1 = 1");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    meetingRooms.put(resultSet.getString("id"),new MeetingRoom(resultSet.getString("id"),resultSet.getString("name")));
                }
                return meetingRooms;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
