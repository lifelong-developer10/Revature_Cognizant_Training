package app;

import DAO.MasterUserRegister;
import DAO.Password_Generator;
import DAO.Store_GeneratedOTP;
import model.MasterUser;
import security.PasswordHash;
import services.MasterService;
import services.Password_Services_Vault;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static MasterService userService = new MasterService();
    private static Password_Services_Vault pv = new Password_Services_Vault();
    private static int logInMasterId = -1;
    private static String logInMasterPass = null;
    private static  MasterUserRegister userDAO = new MasterUserRegister();
    private static Store_GeneratedOTP otp = new Store_GeneratedOTP();
    private static Password_Generator  pg = new Password_Generator();
     private static Password_Vault_Menu pm = new Password_Vault_Menu();
 private static MasterMenu mm = new MasterMenu();
    private static boolean isRunning = true;
    private static boolean isLoggedIn = false;
    private static int loggedInUserId = -1;

    public  static void main(String args[]){
        while (isRunning) {
            if (isLoggedIn) {
                pm.passwordVaultMenu(loggedInUserId,logInMasterPass);
            } else {
                mm.masterMenu();
            }
        }
    }

}