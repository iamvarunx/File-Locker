package dao;

import db.MyConnection;
import model.Data;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {
      public static List<Data> getAllFiles(String email) throws SQLException {
          Connection con = MyConnection.getConnection();
          String query="Select * from data where email =?;";
          PreparedStatement ps = con.prepareStatement(query);
          ps.setString(1,email);
          ResultSet rs = ps.executeQuery();
          List<Data> files = new ArrayList<>();
          while(rs.next()){
              int id = rs.getInt(1);
              String name =rs.getString(2);
              String path = rs.getString(3);
              files.add(new Data(id,name,path));
          }
          return files;
      }

      public static int hideFile(Data file) throws  SQLException, IOException {
          Connection con = MyConnection.getConnection();
          String query = "Insert into data(filename,path,email,bin_data) values(?,?,?,?);";
          PreparedStatement ps = con.prepareStatement(query);
          ps.setString(1, file.getFileName());
          ps.setString(2, file.getPath());
          ps.setString(3,file.getEmail());
          File f = new File(file.getPath());
          FileInputStream fr = new FileInputStream(f);
          ps.setBinaryStream(4, fr, (int) f.length());
          int ans = ps.executeUpdate();
          fr.close();
          f.delete();
          return ans;
      }
    public static void unhide(int id) throws SQLException, IOException {
        Connection con = MyConnection.getConnection(); // Assuming you have a method to get connection
        String query = "SELECT path, bin_data FROM data WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String path = rs.getString("path");
            InputStream binaryStream = rs.getBinaryStream("bin_data");

            // Write the binary stream to a file
            FileOutputStream fos = new FileOutputStream(path);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = binaryStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();

            // Close the binary stream
            binaryStream.close();

            // Delete the row from the database
            ps = con.prepareStatement("DELETE FROM data WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Successfully Unhidden");
        } else {
            System.out.println("No data found for id: " + id);
        }

        // Close resources
        rs.close();
        ps.close();
        con.close();
    }
}
