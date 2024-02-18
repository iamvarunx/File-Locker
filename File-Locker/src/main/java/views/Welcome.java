package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.sql.SQLException;
import java.util.Scanner;


public class Welcome {
    public void welcomeScreen(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the File Hider App");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signup");
        System.out.println("Press 0 to Exit");
        System.out.println("Enter Your Choice");
        int choice =sc.nextInt();
        switch (choice)
        {
            case 1 -> login();
            case 2 -> signUp();
            case 3 -> System.exit(0);
        }
    }
    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email:");
        String email=sc.nextLine();
        try{
            if(UserDAO.isExists(email))
            {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email,genOTP);
                System.out.println("Enter the OTP");
                String otp = sc.nextLine();
                if(otp.equals(genOTP)){
                    new UserView(email).home();

                } else {
                    System.out.println("Wrong OTP");
                }
            } else{
                System.out.println("User not found");
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name:");
        String name =sc.nextLine();
        System.out.println("Enter email");
        String email =sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email,genOTP);
        System.out.println("Enter the OTP");
        String otp = sc.nextLine();
        if(otp.equals(genOTP)){
            User user =new User(name,email);
            int response = UserService.saveUser(user);
            switch (response){
                case 0 -> System.out.println("User registered");
                case 1 -> System.out.println("User already exists");
            }
        } else {
            System.out.println("Wrong OTP");
        }
    }
}
