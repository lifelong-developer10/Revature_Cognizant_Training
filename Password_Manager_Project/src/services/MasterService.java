package services;

import DAO.MasterUserRegister;
import DAO.Password_Vault;
import app.MasterMenu;
import model.MasterUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import security.PasswordHash;
import util.Database_Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.Scanner;


public class MasterService {

    private MasterUserRegister userDAO = new MasterUserRegister();
    private static final Logger log = LogManager.getLogger(MasterService.class);


    public boolean register(String name, String email, String password) {
        String hashedPassword = PasswordHash.hashPassword(password);
        MasterUser user = new MasterUser(name, email, hashedPassword);

      return userDAO.registerMaster(user);

    }

    public boolean login(String email, String password) {
        MasterUser master = userDAO.findMaster(email);
        if (master == null) {
            System.out.println("User Not Found !");
            return false;
        }

        String hashedPassword = PasswordHash.hashPassword(password);
        if (hashedPassword.equals(master.getMasterPasswordHash())) {
            System.out.println("Login successful! Welcome, " + master.getMastername());
            return true;
        } else {
            System.out.println("Incorrect password!");
            return false;
        }
    }


    public void setupSecurityQuestion(int userId) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Set your security question:");

        System.out.println("1. What is your favourite color?");
        System.out.println("2. What is your pet name?");
        System.out.println("3. What is your birth city?");

        int choice = sc.nextInt();
        sc.nextLine();

        String question = switch (choice) {
            case 1 -> "What is your favourite color?";
            case 2 -> "What is your pet name?";
            case 3 -> "What is your birth city?";
            default -> null;
        };

        if (question == null) {
            System.out.println("Invalid choice");
            return;
        }

        System.out.print("Answer: ");
        String answer = sc.nextLine().toLowerCase();


        userDAO.save(userId, question, answer);

        System.out.println("Security question saved successfully");
    }



    public MasterUser loginAndGetUser(String email, String password) {
        MasterUser user = userDAO.findMaster(email);

        if (user == null) {
            return null;
        }

        String hashedInput = PasswordHash.hashPassword(password);

        if (hashedInput.equals(user.getMasterPasswordHash())) {
            return user;
        }
        return null;
    }


}