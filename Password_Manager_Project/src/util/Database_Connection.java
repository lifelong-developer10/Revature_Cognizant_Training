package util;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database_Connection

{

    public static  Connection getConnection() throws Exception  {

  Class.forName("com.mysql.cj.jdbc.Driver");

        String URL = "jdbc:mysql://localhost:3306/password_manager" +
                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String User = "root";
        String PASSWORD = "password";
        try{
        Connection  con = DriverManager.getConnection(URL,User,PASSWORD);

               return con;

    } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        }
}
