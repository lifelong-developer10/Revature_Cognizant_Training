package DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import security.PasswordHash;
import util.Database_Connection;
import util.OTPUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Store_GeneratedOTP {
    private static final Logger logger =
            LogManager.getLogger(MasterUserRegister.class);
    public String saveOTP(int userId, String otp) {
        String sql = """
        
                REPLACE INTO password_reset_otp 
        (user_id, otp, expiry_time) 
        VALUES (?, ?, NOW() + INTERVAL 5 MINUTE)
        """;

        try (Connection con = Database_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, otp);
            ps.executeUpdate();
        } catch (Exception e) {

            logger.error("Something went wrong",e);
        }
        return "Error while Storing OTP";
    }



    public boolean verifyOTP(int userId, String enteredOtp) {
        String sql = """
        SELECT otp FROM password_reset_otp
        WHERE user_id=? AND otp=? AND expiry_time > NOW()
        """;

        try (Connection con = Database_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, enteredOtp);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
           logger.error("OTP not generated. Something went wrong!");

        }
        return false;
    }

    public boolean resetPassword(int userId, String newPassword) {
        String sql = "UPDATE users SET master_password_hash=? WHERE user_id=?";

        try (Connection con = Database_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String updatedpassword = PasswordHash.hashPassword(newPassword);
            ps.setString(1, updatedpassword); // encrypt if needed
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error("User not logged In");
        }
        return false;
    }

    public void recoverUsingOTP(int userId) {
        Scanner sc = new Scanner(System.in);

        String otp = OTPUtility.generateOTP();
        saveOTP(userId, otp);

        System.out.println("📩 OTP sent: " + otp);

        System.out.print("Enter OTP: ");
        String enteredOtp = sc.next();

        if (!verifyOTP(userId, enteredOtp)) {
            logger.warn(" Invalid or expired OTP");
            return;
        } else {
            logger.info(" OTP verified");
       if (userId == 0) {
           logger.warn(" User not found");
            return;
       }
            System.out.println("Enter New Password:");
            String newpass = new Scanner(System.in).next();

             resetPassword(userId, newpass);

        }

    }
}
