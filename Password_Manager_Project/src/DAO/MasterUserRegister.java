package DAO;

import model.MasterUser;
import security.Encryption_pass;
import security.PasswordHash;
import services.MasterService;
import util.Database_Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MasterUserRegister{

    private static final Logger logger =
            LogManager.getLogger(MasterUserRegister.class);
    public boolean registerMaster(MasterUser user){
        try {
            logger.info("Saving data to database");

            String query = "INSERT INTO users (name,email,master_password_hash) VALUES (?,?,?)";


            Connection con = Database_Connection.getConnection();
            PreparedStatement set1 = con.prepareStatement(query);
            set1.setString(1, user.getMastername());
            set1.setString(2, user.getMasteremail());
            set1.setString(3, user.getMasterPasswordHash());
            int rows = set1.executeUpdate();

            return rows > 0;



    }catch(Exception e){
            logger.error("Error :", e);
            return false;


        }
    }

    public void save(int userId, String question, String answer) {
        String sql = "INSERT INTO security_questions (user_id, question, answer) VALUES (?, ?, ?)";

        try (Connection con = Database_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String hashedAnswer = PasswordHash.hashPassword(answer.trim().toLowerCase());

            ps.setInt(1, userId);
            ps.setString(2, question);
            ps.setString(3, hashedAnswer);
            ps.executeUpdate();

        } catch (Exception e) {
            logger.error("Error", e);
        }
    }


    public MasterUser findMaster(String msemail){

         try{
             String find = "SELECT * FROM users WHERE email = ?";
             Connection con = Database_Connection.getConnection();
             PreparedStatement pst = con.prepareStatement(find);
             pst.setString(1,msemail);
             ResultSet rs = pst.executeQuery();
             if(rs.next()){
                 MasterUser user = new MasterUser();
                 user.setMastersId(rs.getInt("user_id"));
                  user.setMastername(rs.getString("name"));
                  user.setMasteremail(rs.getString("email"));
                 user.setMasterPasswordHash(rs.getString("master_password_hash"));

 
                 return user;
             }

         }catch(Exception e){
             logger.error("message", e);
         }
        return null;
    }


     public int getUserByEmail(String enteremail){
         String sql = "SELECT user_id FROM users WHERE email=?";

         try (Connection con = Database_Connection.getConnection();
              PreparedStatement ps = con.prepareStatement(sql)) {

             ps.setString(1, enteremail);
             ResultSet rs = ps.executeQuery();

             if (rs.next()) {

                 return rs.getInt("user_id");
             }
         } catch (Exception e) {
             logger.error("message", e);
         }
         return 0;
     }



    public String getSecurityQuestion(int userId) {
        String sql = "SELECT question FROM security_questions WHERE user_id=?";

        try (Connection con = Database_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("question");
            }
        } catch (Exception e) {
            logger.error("message", e);
        }
        return null;
    }

    public boolean verifySecurityAnswer(int userId, String answer) {
        String sql = "SELECT answer FROM security_questions WHERE user_id=?";

        try (Connection con = Database_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("answer");
                String inputHash = PasswordHash.hashPassword(answer.trim().toLowerCase());

                return storedHash.equals(inputHash);
            }
        } catch (Exception e) {
            logger.error("message", e);
        }
        return false;
    }


}
