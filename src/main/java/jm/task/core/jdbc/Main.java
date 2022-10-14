package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanovich", (byte) 44);
        userService.saveUser("Petr", "Smirnov", (byte) 24);
        userService.saveUser("Oleg", "Ivanov", (byte) 23);
        userService.saveUser("Olga", "Petrova", (byte) 19);

        userService.removeUserById(1);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
