package repository;

import Model.User;
import util.DatabaseUtil;

import java.sql.*;

public class UserRepository {
    Connection connection;
    public UserRepository () throws SQLException {
        connection = DatabaseUtil.getConnection();
    }

    public boolean addUser(String username, String password, boolean isAdmin) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into User(username,password,isAdmin) values (?,?,?)");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.setBoolean(3,isAdmin);
          return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User loginUser(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from User where user_name = ? and password = ?");
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return new User(resultSet.getString("username"),resultSet.getString("password"),resultSet.getBoolean("isAdmin"));
        }
        return null;
    }
}
