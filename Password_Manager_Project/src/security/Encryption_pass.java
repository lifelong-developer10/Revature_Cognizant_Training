package security;

import java.util.Base64;

public class Encryption_pass {


        public static String encrypt(String password) {
            return Base64.getEncoder().encodeToString(password.getBytes());
        }

        public static String decrypt(String encryptedPassword) {
            return new String(Base64.getDecoder().decode(encryptedPassword));
        }
    }


