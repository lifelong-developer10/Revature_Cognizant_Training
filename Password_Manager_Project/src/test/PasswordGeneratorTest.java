package test;
import static org.junit.jupiter.api.Assertions.*;

import DAO.Password_Generator;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {

    @Test
    void testGeneratedPasswordLength() {
        String password = Password_Generator.generateStrongPassword();
        assertEquals(12, password.length());
    }

    @Test
    void testPasswordNotNull() {
        String password = Password_Generator.generateStrongPassword();
        assertNotNull(password);
    }

    @Test
    void testPasswordContainsMixedCharacters() {
        String password = Password_Generator.generateStrongPassword();
        assertTrue(password.matches(".*[A-Z].*"));
        assertTrue(password.matches(".*[a-z].*"));
        assertTrue(password.matches(".*[0-9].*"));
        assertTrue(password.matches(".*[!@#$%^&*()].*"));
    }
}
