package test;


import static org.junit.jupiter.api.Assertions.*;

import DAO.MasterUserRegister;
import app.ProfileSecurityMenu;
import org.junit.jupiter.api.Test;

import services.MasterService;

public class MasterUserServiceTest {

    MasterService masterService = new MasterService();
    MasterUserRegister msdao = new MasterUserRegister();
ProfileSecurityMenu psMenu = new ProfileSecurityMenu();
    @Test
    void testCreateAccountSuccess() {
        boolean result = masterService.register(
                "JUnit User",
                "junit_user@test.com",
                "Test@123"
        );

        assertTrue(result, "User  saved successfully");
    }

    @Test
    void testLoginWithWrongPassword() {
        boolean result = masterService.login(
                "junit_user@test.com",
                "WrongPass"
        );

        assertFalse(result, "Login should fail with wrong password");
    }

}
