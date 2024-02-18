package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection con=null;
    public static Connection getConnection() {
         final String URL = "jdbc:mysql://localhost:3306/filehider";
         final String USER = "root";
         final String PSW = "#Varun@2003&25";
        try {
            return con=DriverManager.getConnection(URL,USER, PSW);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void closeConnection()  {
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
