package app;

import DAO.MasterUserRegister;
import DAO.Password_Generator;
import DAO.Store_GeneratedOTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.MasterService;
import services.Password_Services_Vault;
import util.Database_Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import static app.MasterMenu.masterMenu;

public class ProfileSecurityMenu {
    private static boolean back = false;
    private static final Logger log = LogManager.getLogger(Password_Services_Vault.class);


    private static Scanner sc = new Scanner(System.in);
    private static MasterService userService = new MasterService();
    private static Password_Services_Vault pv = new Password_Services_Vault();
    private static int logInMasterId = -1;
    private static String logInMasterPass = null;
    private static MasterUserRegister userDAO = new MasterUserRegister();
    private static Store_GeneratedOTP otp = new Store_GeneratedOTP();
    private static Password_Generator pg = new Password_Generator();
    private static MasterMenu mn = new MasterMenu();
    private static Object e;

    public static void profilesecuritymenu(int userId, String MastrPass) {
        logInMasterId = userId;
        logInMasterPass=MastrPass;

        while (!back) {

            System.out.println("\n------- PROFILE & SECURITY -------");
            System.out.println("1. Update Profile");
            System.out.println("2. Manage Security Questions");
            System.out.println("3. Reset Master Password");
            System.out.println("4. Back");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> updateMasterprofile(logInMasterId);
                case 2 ->   mangeQuestion(logInMasterId);
                case 3 -> {
                    System.out.println("Enter the New Password:");

                    String newpass = new Scanner(System.in).next();

                    boolean reset = otp.resetPassword(userId, newpass);
                    if (reset) {
                        System.out.println("Password Reset Successfull! Now you can login");
                        masterMenu();
                    }

                 else{
                    System.out.println(" Cannot reset password.");
                }
                break;
            }


                case 4 -> back = true;
                default -> System.out.println("Invalid option");
            }
        }
    }

    public static boolean updateMasterprofile(int logInMasterId){
             System.out.println("Enter the New UserName:");
             String name = sc.next();
             System.out.println("Enter the new Email");
             String email = sc.next();
        try{
            Connection con = Database_Connection.getConnection();
            String query = "UPDATE users SET name=?, email=? WHERE user_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,name);
            pst.setString(2,email);
            pst.setInt(3,logInMasterId);
           int rest = pst.executeUpdate();
           if(rest>0){
              System.out.println("Updated Profile Sucessfully");
           }else{
             System.out.println("Profile not updated");

           }
      } catch (Exception ex) {
            log.error("Something Went Wrong !",e);

      }
        return false;
    }

    private static void mangeQuestion(int logInMasterId){
       System.out.println("Update the Master question and answer");
 try{
     Connection con = Database_Connection.getConnection();
     String query = "DELETE FROM security_questions WHERE user_id = ?";
     PreparedStatement pst = con.prepareStatement(query);

     pst.setInt(1,logInMasterId);
     int rest = pst.executeUpdate();
     if(rest>0){
         userService.setupSecurityQuestion(logInMasterId);
         log.info("Questions Updated Sucessfully");
     }else{
        log.error("Profile not updated");

     }
 }
 catch (Exception e){
     log.error("Something Went Wrong !",e);
 }
    }
}