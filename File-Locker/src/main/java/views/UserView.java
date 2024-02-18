package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;

    public UserView(String email) {
        this.email = email;
    }

    public void home(){
        while(true)
        {
            System.out.println("Welcome "+this.email);
            System.out.println("Press 1 to show hidden file");
            System.out.println("press 2 to hide a new file");
            System.out.println("press 3 to unhide a file");
            System.out.println("press 0 to exit");
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your choice");
            int choice =Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1 ->{
                    try {
                        List<Data> files = DataDAO.getAllFiles(email);
                        System.out.println("ID  -  File Name");
                        for(Data file : files){
                            System.out.println(file.getId()+" - "+file.getFileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 ->{
                    System.out.println("Enter the file path");
                    String path =sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0,f.getName(),path,this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 ->{
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(email);

                    System.out.println("ID  -  File Name");
                    for(Data file : files){
                        System.out.println(file.getId()+" - "+file.getFileName());
                    }
                    System.out.println("Enter the id of file to unHide");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean isValidId =false;
                    for(Data file:files)
                    {
                        if(file.getId() == id)
                        {
                            isValidId=true;
                            break;
                        }

                    }
                            System.out.print(isValidId);
                    if(isValidId){
                        try {
                            DataDAO.unhide(id);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        System.out.println("Wrong ID");
                    }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 0 ->{
                    System.exit(0);
                }
            }
        }
    }
}
