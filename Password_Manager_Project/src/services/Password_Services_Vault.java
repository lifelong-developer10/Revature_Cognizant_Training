package services;

import DAO.Password_Vault;
import model.DiffAccount_CRUD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import security.Encryption_pass;
import security.PasswordHash;

import java.util.List;

public class Password_Services_Vault {
    public Password_Vault pv = new Password_Vault();
    private static final Logger log = LogManager.getLogger(Password_Services_Vault.class);

    public boolean addPassword(int userId,
                               String accountName,
                               String username,
                               String password) {

        String encrypted = Encryption_pass.encrypt(password);

        DiffAccount_CRUD entry =
                new DiffAccount_CRUD(userId, accountName, username, encrypted);

        return pv.addpass(entry);
    }

    public boolean deletepassword(int userId, String website) {
        return pv.deletepass(userId, website);

    }

    public void All_Passwords(int userId, String website2) {

        List<DiffAccount_CRUD> list = pv.getAllaccount(userId,website2);


        if (list.isEmpty()) {
            log.info("No passwords saved.");
            return;
        }

        System.out.println("\n Saved Accounts:");
        for (DiffAccount_CRUD entry : list) {
            System.out.println("- " + entry.getWebsiteName());
        }
    }
    public void All_accounts(int userId) {

        List<DiffAccount_CRUD> list = pv.getAllaccount(userId);


        if (list.isEmpty()) {
            log.info("No passwords saved.");
            return;
        }

        System.out.println("\n Saved Accounts:");
        for (DiffAccount_CRUD entry : list) {
            System.out.println("- " + entry.getWebsiteName());
        }
    }

    public void viewPasswordWithMasterCheck(int userId, String accountName, String masterPasswordHash, String enteredHash) {

        String hasedenteredpass = PasswordHash.hashPassword(enteredHash);

        if (!masterPasswordHash.equals(hasedenteredpass)) {
            log.error(" Master password incorrect!");
            return;
        }

        List<DiffAccount_CRUD> list = pv.getAllaccount(userId,accountName);


        for (DiffAccount_CRUD entry : list) {
            String decrypted = Encryption_pass.decrypt(entry.getEncryptedPassword());

            System.out.println("Username: " + entry.getUsername());
            System.out.println("Password: " + decrypted);

            return;
        }

        log.error("Account not found!");



    }
    public boolean updatePassword(int userId, String accountName, String newPassword) {
        String encrypted = Encryption_pass.encrypt(newPassword);
        return pv.updatePass(userId, accountName, encrypted);

    }

    public void searchPasswords(int userId, String keyword) {
        List<DiffAccount_CRUD> results = pv.serachpassbyuser(userId, keyword);

        if (results.isEmpty()) {
            log.error("No accounts found with keyword: " +keyword);
            return;
        }

        System.out.println("\nSearch Results:");
        for (DiffAccount_CRUD entry : results) {
            System.out.println("Website Name: " + entry.getWebsiteName());
        System.out.println("UserName :" + entry.getUsername() );
        String pass = Encryption_pass.decrypt(entry.getEncryptedPassword());
        System.out.println("Password:" +pass);
        }

    }
}

