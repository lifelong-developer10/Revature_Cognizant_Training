package DAO;

import model.DiffAccount_CRUD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Database_Connection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Password_Vault {

    private static final Logger logger =
            LogManager.getLogger(MasterUserRegister.class);


    public boolean addpass(DiffAccount_CRUD crud) {
        String addquery = "INSERT INTO password_CRUD_Vault (user_id, account_Websitename, username, passEncrption) VALUES (?,?,?,?)";

        try (Connection con = Database_Connection.getConnection();
             PreparedStatement pst = con.prepareStatement(addquery)) {

            pst.setInt(1, crud.getUserId());
            pst.setString(2, crud.getWebsiteName());
            pst.setString(3, crud.getUsername());
            pst.setString(4, crud.getEncryptedPassword());
            System.out.println("Inserting -> Website: " + crud.getWebsiteName() + ", Username: " + crud.getUsername());

            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Account Created successfully! " + " Website: " + crud.getWebsiteName() + " / " + "UserName : " + crud.getUsername());
            }

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<DiffAccount_CRUD> getAllaccount(int userId, String website) {
        List<DiffAccount_CRUD> list = new ArrayList<>();
        String selquery = "SELECT * FROM password_CRUD_Vault WHERE user_Id = ? AND account_Websitename=?";
        try {
            Connection con = Database_Connection.getConnection();
            PreparedStatement pt = con.prepareStatement(selquery);
            pt.setInt(1, userId);
            pt.setString(2,website);
            ResultSet set3 = pt.executeQuery();
            while (set3.next()) {
                DiffAccount_CRUD crud = new DiffAccount_CRUD(
                        set3.getInt("user_Id"),
                        set3.getString("account_Websitename"),
                        set3.getString("username"),
                        set3.getString("passEncrption")


                );
                list.add(crud);

            }
        } catch (Exception e) {
            logger.error("Invalid Data");
        }
        return list;

    }
    public List<DiffAccount_CRUD> getAllaccount(int userId) {
        List<DiffAccount_CRUD> list = new ArrayList<>();
        String selquery = "SELECT * FROM password_CRUD_Vault WHERE user_Id = ? ";
        try {
            Connection con = Database_Connection.getConnection();
            PreparedStatement pt = con.prepareStatement(selquery);
            pt.setInt(1, userId);
            ResultSet set3 = pt.executeQuery();
            while (set3.next()) {
                DiffAccount_CRUD crud = new DiffAccount_CRUD(
                        set3.getInt("user_Id"),
                        set3.getString("account_Websitename"),
                        set3.getString("username"),
                        set3.getString("passEncrption")


                );
                list.add(crud);

            }
        } catch (Exception e) {
            logger.error("Invalid Data");
        }
        return list;

    }

    public boolean deletepass(int userId, String website) {

        String delqry = "DELETE FROM password_CRUD_Vault WHERE user_id = ? AND account_Websitename = ?";
        try {
            Connection con = Database_Connection.getConnection();
            PreparedStatement pr = con.prepareStatement(delqry);
            pr.setInt(1, userId);
            pr.setString(2, website);
            int res = pr.executeUpdate();
            return res > 0;

        } catch (Exception e) {
            logger.warn("Website Not Found..");
            return false;
        }

    }


  public boolean updatePass( int userId, String website, String newpass){
     String upd = "UPDATE password_CRUD_Vault SET passEncrption = ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ? AND account_Websitename = ? ";
    try{
        Connection con  = Database_Connection.getConnection();
        PreparedStatement pst = con.prepareStatement(upd);
        pst.setString(1,newpass);
        pst.setInt(2,userId);
        pst.setString(3,website);
        return pst.executeUpdate() >0;

    }catch(Exception e){
        logger.error("Invalid Data..",e);
        return false;
    }

    }


    public List<DiffAccount_CRUD> serachpassbyuser(int userId, String username){
        List<DiffAccount_CRUD> crud = new ArrayList<>();
        String pass = "SELECT * FROM password_CRUD_Vault WHERE user_id = ? AND username = ?";
        try{
         Connection con = Database_Connection.getConnection();
         PreparedStatement ps = con.prepareStatement(pass);
              ps.setInt(1,userId);
            ps.setString(2,  username );

 ResultSet res = ps.executeQuery();
 while(res.next()){
     DiffAccount_CRUD dc = new DiffAccount_CRUD(

            res.getInt("user_id"),
             res.getString("account_Websitename"),
             res.getString("username"),
             res.getString("passEncrption")
     );
     crud.add(dc);

 }
        }catch(Exception e){
            logger.error("Error while searching", e);

        }
        return crud;

    }


}
