package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(50) NOT NULL , " +
            "last_name VARCHAR(50) NOT NULL , " +
            "age INT NOT NULL )";
    private final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private final String SAVE_USER = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private final String DEL_USER_FOR_ID = "DELETE FROM users WHERE id = ?";
    private final String GET_ALL_USER = "SELECT * FROM users";
    private final String CLEAN_TABLE = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(DROP_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pstm = Util.getConnection().prepareStatement(SAVE_USER)) {
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pstm = Util.getConnection().prepareStatement(DEL_USER_FOR_ID)) {
            pstm.setLong(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(GET_ALL_USER);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(CLEAN_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
