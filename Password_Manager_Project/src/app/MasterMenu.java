package app;

import DAO.MasterUserRegister;
import DAO.Password_Generator;
import DAO.Store_GeneratedOTP;
import model.MasterUser;
import services.MasterService;
import services.Password_Services_Vault;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;


public class MasterMenu {

    private static final Logger log = LogManager.getLogger(MasterMenu.class);

    private static Scanner sc = new Scanner(System.in);
    private static MasterService userService = new MasterService();
    private static Password_Services_Vault pv = new Password_Services_Vault();
    private static int logInMasterId = -1;
    private static String logInMasterPass = null;
    private static MasterUserRegister userDAO = new MasterUserRegister();
    private static Store_GeneratedOTP otp = new Store_GeneratedOTP();
    private static Password_Generator pg = new Password_Generator();

    private static boolean isRunning = true;
    private static boolean isLoggedIn = false;


    static void masterMenu() {
           System.out.println( "=====================================");
            System.out.println("        SECURE PASSWORD MANAGER");
        System.out.println("=====================================");
        System.out.println("1. Create New Account");
        System.out.println("2. Add Security Question For Recovery");
        System.out.println("3. Login");
       System.out.println("4. Forgot Master Password");
       System.out.println("5. Exit");
        System.out.print("Choose option: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> registerMethod();
            case 2 -> {
                System.out.println("Add Security Questions For Account Recovery");
                System.out.println("Enter your email Id :");
                String masteremail = sc.next();
                int  userId = userDAO.getUserByEmail(masteremail);
                if (userId == 0) {
                    System.out.println("User not found");
                    return;
                }
                userService.setupSecurityQuestion(userId);
                break;
            }
            case 3 -> loginMaster();
            case 4 -> forgotPasswordMenu();
            case 5 -> isRunning = false;
            default -> System.out.println("Invalid option");
        }

    }



    //register method
    private static void registerMethod() {
        System.out.println("Enter Your Name:");
        String name = sc.nextLine();

        System.out.println("Enter Your Email:");
        String email = sc.nextLine();
        System.out.println("\nChoose Password add method:");
        System.out.println("1. Enter Manually");
        System.out.println("2. Generate Strong Password");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch(choice){
            case 1:{
                System.out.println("Enter Master Password:");
                String password = sc.nextLine();

                boolean register = userService.register(name,email,password);

                if(register) {
                   System.out.println("Registered successfully");
                    break;

                } else {
                    System.out.println(" Registration failed!");
                    break;
                }
            }
            case 2:{

                String strongPass = pg.generateStrongPassword();
                System.out.println("Generated Strong Password: " +strongPass);

                boolean register = userService.register(name,email,strongPass);
                if (register) {
                    System.out.println("Registered successfully");
                    break;

                } else {
                    System.out.println(" Registration failed!");
                    break;

                }
            }
        }


    }

//login master method
    public static void loginMaster(){
        System.out.println("Enter the Email :");
        String mail = sc.next();
        System.out.println("Enter the Password");
        String pass = sc.next();

        boolean log = userService.login(mail,pass);
        MasterUser user = userService.loginAndGetUser(mail, pass);


        if (user != null) {
            logInMasterId = user.getUserId();
            logInMasterPass= user.getMasterPasswordHash();

            System.out.println(" Login successful!");
            Password_Vault_Menu.passwordVaultMenu(logInMasterId,logInMasterPass);




        }
        else {
            System.out.println("Login failed!");
            System.out.println("Enter Correct Data");
            masterMenu();


        }

    }


    //forgot password method
    private static void forgotPasswordMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter mail Id: ");
        String msemail = sc.next();

        int userId = userDAO.getUserByEmail(msemail);
        if (userId == 0) {
            System.out.println("User not found");
            return;
        }

        System.out.println("\nChoose recovery method:");
        System.out.println("1. Security Question");
        System.out.println("2. OTP");
        System.out.print("Choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                String question = userDAO.getSecurityQuestion(userId);
                if(question== null){
                    log.warn("You have not added any security questions");
                    break;
                }

                System.out.println("The quetion is :" + question);

                System.out.println("Enter Your Answer:");
                sc.nextLine();
                String ans = sc.nextLine();

                boolean check = userDAO.verifySecurityAnswer(userId, ans);
                if (check) {
                    System.out.println("Correct answer! You can reset your password.");
                    System.out.println("Enter the New Password:");
                    String newpass = sc.next();
                    boolean reset = otp.resetPassword(userId, newpass);
                    if (reset) {
                        System.out.println("Password Reset Successfull! Now you can login");
                    }

                } else {
                    System.out.println("Wrong answer! Cannot reset password.");
                }


                break;
            case 2:
                otp.recoverUsingOTP(userId);
                break;
        }



    }


    }

