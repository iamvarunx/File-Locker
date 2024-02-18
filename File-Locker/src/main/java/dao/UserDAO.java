package dao;

import db.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean isExists(String email) throws SQLException {
        Connection con = MyConnection.getConnection();
        String query="Select email from users";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            String e = rs.getString(1);
            if(e.equals(email))
                return true;
        }
        return false;
    }
    public static int saveUser(User user) throws SQLException{
        Connection con = MyConnection.getConnection();
        String query ="Insert into users values(default,?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        return ps.executeUpdate();
    }
}
