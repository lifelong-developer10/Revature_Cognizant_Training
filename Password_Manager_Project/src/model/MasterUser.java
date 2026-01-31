package model;

public class MasterUser {


    private int MastersId;


    public void setMastername(String mastername) {
        this.mastername = mastername;
    }

    public void setMasteremail(String masteremail) {
        this.masteremail = masteremail;
    }

    public void setMasterPasswordHash(String masterPasswordHash) {
        this.masterPasswordHash = masterPasswordHash;
    }

    public void setMastersId(int mastersId) {
        MastersId = mastersId;
    }

    private String mastername;

    private String masteremail;

    private String masterPasswordHash;

    public MasterUser() {
    }

    public MasterUser(String mastername, String masteremail, String masterPasswordHash) {
        this.mastername = mastername;
        this.masteremail = masteremail;
        this.masterPasswordHash = masterPasswordHash;
    }

    public MasterUser(int userId, String masterPasswordHash, String masteremail, String mastername) {
        this.MastersId = userId;
        this.masterPasswordHash = masterPasswordHash;
        this.masteremail = masteremail;
        this.mastername = mastername;
    }




    public int getUserId() {
        return MastersId;
    }

    public String getMasterPasswordHash() {
        return masterPasswordHash;
    }

    public String getMasteremail() {
        return masteremail;
    }

    public String getMastername() {
        return mastername;
    }

}
