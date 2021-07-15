package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private String username = "root";
    private String password = "root";
    private String connectionToUrl = "jdbc:mysql://localhost:3306/javamentor";
    Connection connection;

    {
        try {
            connection = DriverManager.getConnection(connectionToUrl, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public UserServiceImpl() {

    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS UsersTable (id MEDIUMINT NOT NULL AUTO_INCREMENT, "
                    + "name CHAR(40) NOT NULL, "
                    + "lastname CHAR(40) NOT NULL, "
                    + "age int(127) NOT NULL, primary key (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS userstable");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO userstable(name, lastName, age) VALUES( ?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.setAutoCommit(false);
            connection.commit();
            System.out.println("User  именем - " + name + " был добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM userstable WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM userstable");
             ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM userstable")) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
                connection.commit();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE userstable");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
