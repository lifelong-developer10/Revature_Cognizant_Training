package security;


import java.util.HashMap;
import java.util.Map;
public class OTPStorage {


        private static final Map<Integer, String> otpMap = new HashMap<>();

        public static void save(int userId, String otp) {
            otpMap.put(userId, otp);
        }

        public static boolean verify(int userId, String otp) {
            return otp.equals(otpMap.remove(userId));
        }
    }


