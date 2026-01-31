package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import model.DiffAccount_CRUD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import security.Encryption_pass;
import services.Password_Services_Vault;

public class PasswordVaultServiceTest {

    private Password_Services_Vault vaultService;
    private int testUserId = 1;

    @BeforeEach
    void setup() {
        vaultService = new Password_Services_Vault();

        vaultService.pv.deletepass(testUserId, "JUnit-Google");

        boolean saved = vaultService.addPassword(
                testUserId,
                "JUnit-Google",
                "junit_user",
                "Test@123"
        );

        assertTrue(saved, "Test entry should be added successfully");
    }


    private String getEncryptedPasswordFromVault(String accountName) {
        List<DiffAccount_CRUD> list = vaultService.pv.getAllaccount(testUserId,accountName);
        for (DiffAccount_CRUD entry : list) {
            if (entry.getWebsiteName().equals(accountName)) {
                return entry.getEncryptedPassword();
            }
        }
        return null;
    }

    @Test
    void testUpdatePassword() {
        String newPass = "New@123";

        boolean updated = vaultService.updatePassword(testUserId, "JUnit-Google", newPass);
        assertTrue(updated, "Password should be updated successfully");

        String encryptedFromDB = getEncryptedPasswordFromVault("JUnit-Google");
        String decrypted = Encryption_pass.decrypt(encryptedFromDB);

        assertEquals(newPass, decrypted, "Decrypted password should match the updated password");
    }

    @Test
    void testDeletePassword() {
        boolean deleted = vaultService.deletepassword(testUserId, "JUnit-Google");
        assertTrue(deleted, "Password entry should be deleted");

        String encryptedFromDB = getEncryptedPasswordFromVault("JUnit-Google");
        assertNull(encryptedFromDB, "Deleted entry should return null");
    }

}
