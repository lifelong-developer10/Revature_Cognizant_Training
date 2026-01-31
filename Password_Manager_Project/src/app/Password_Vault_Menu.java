package app;

import DAO.MasterUserRegister;
import DAO.Password_Generator;
import DAO.Store_GeneratedOTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.MasterService;
import services.Password_Services_Vault;

import java.util.Scanner;

public class Password_Vault_Menu {
    private static boolean back = false;
    private static final Logger log = LogManager.getLogger(Password_Services_Vault.class);

    private static Scanner sc = new Scanner(System.in);
    private static MasterService userService = new MasterService();
    private static Password_Services_Vault pv = new Password_Services_Vault();
    private static String logInMasterPass = null;
    private static MasterUserRegister userDAO = new MasterUserRegister();
    private static Store_GeneratedOTP otp = new Store_GeneratedOTP();
    private static Password_Generator pg = new Password_Generator();

    private static int logInMasterId ;

       private static String website2;

    static void passwordVaultMenu(int userId,String MasterPass) {
        logInMasterId = userId;
        logInMasterPass = MasterPass;

         while (!back)

         {

             System.out.println("\n----------- PASSWORD VAULT -----------");
        System.out.println("1. Create Diffrent Account ");
        System.out.println("2. View All Created Accounts");
        System.out.println("3. View Password");
        System.out.println("4. Update Password");
        System.out.println("5. Delete Password");
        System.out.println("6. Search Account");
        System.out.println("7.  Profile Settings");
        System.out.println("8. Logout");


        System.out.print("Choose option: ");


        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> addDiffAccounts();
            case 2 -> viewAllAccounts();
            case 3 -> viewPassword();
            case 4 -> updatePassword();
            case 5 -> deletepass();
            case 6 -> search();
            case 7 ->ProfileSecurityMenu.profilesecuritymenu(logInMasterId,logInMasterPass);
            case 8 -> logout();

            default -> System.out.println(" Invalid option");
        }

    }


    }
    private static void addDiffAccounts() {
        System.out.println("Enter the Site where You want to create Account:");
        website2 = sc.next();
        System.out.println("Enter the Username:");
        String user = sc.next();
        System.out.println("Enter the Password :");
        String pass = sc.next();

        boolean crud = pv.addPassword(logInMasterId, website2, user, pass);
        if(crud){
            log.info("Account Created Successfully");
        }
        else{
            log.error("Error while creating account");
        }
    }

    private static void viewAllAccounts(){

        pv.All_accounts(logInMasterId);
    }

    private static void viewPassword(){
        System.out.println("Enter the Website Name:");
        String website = sc.next();
        System.out.println("Enter the Master Password :");
        String pass = sc.next();

        pv.viewPasswordWithMasterCheck(logInMasterId, website,logInMasterPass,pass);

    }



    private static void deletepass(){
        System.out.println("Enter The Name of Website on which you want to delete your account:");
        String website = sc.next();

        boolean res =  pv.deletepassword(logInMasterId,website);
        if(res){
            log.info("Account is Deleted");
        }
        else{
            log.error("Something went wrong");
        }
    }


    private static void updatePassword(){
        System.out.println("Enter the Website name where you want to change pass:");
        String web = sc.next();
        System.out.println("Enter the new Password :");
        String newpass = sc.next();
        boolean update = pv.updatePassword(logInMasterId,web,newpass);
        if(update){
           System.out.println("Password Updated !");
        }
        else{
            System.out.println("Password Not Updated");
        }
    }

    private static void search(){
        System.out.println("Enter the Username  ");
        String user = sc.next();
        pv.searchPasswords(logInMasterId,user);
    }

    private static void logout() {
        logInMasterId= -1;
        logInMasterPass = null;
        log.info("Logged out successfully");
        MasterMenu.masterMenu();


    }
}