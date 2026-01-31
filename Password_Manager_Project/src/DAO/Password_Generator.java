package DAO;


    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;

    import java.security.SecureRandom;

    public class Password_Generator {

        private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String NUMBERS = "0123456789";
        private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{};:,.<>?";

        private static final String ALL_CHARS = LOWERCASE + UPPERCASE + NUMBERS + SPECIAL_CHARS;

        private static final SecureRandom random = new SecureRandom();
        private static final Logger logger =
                LogManager.getLogger(MasterUserRegister.class);

        public static String generateStrongPassword() {
            int length = 12;
            StringBuilder password = new StringBuilder(length);


            password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
            password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
            password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
            password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));


            for (int i = 4; i < length; i++) {
                password.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
            }
            return shuffleString(password.toString());
        }

        private static String shuffleString(String input) {
            char[] chars = input.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int j = random.nextInt(chars.length);
                char temp = chars[i];
                chars[i] = chars[j];
                chars[j] = temp;
            }
            return new String(chars);
        }


    }


