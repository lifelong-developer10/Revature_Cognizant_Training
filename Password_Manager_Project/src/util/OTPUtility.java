package util;
import java.util.Random;
public class OTPUtility {

        public static String generateOTP() {

            return String.valueOf(100000 + new Random().nextInt(900000));
        }
    }


