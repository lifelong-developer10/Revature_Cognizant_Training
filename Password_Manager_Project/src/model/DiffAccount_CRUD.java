package model;

public class DiffAccount_CRUD {
    private int vaultId;


    private int userId;
    private String websiteName;
    private String username;
    private String encryptedPassword;


    public DiffAccount_CRUD() {
    }

    public DiffAccount_CRUD(int userId, String websiteName, String username, String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
        this.websiteName = websiteName;
        this.username = username;
        this.userId = userId;
    }
    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVaultId() {
        return vaultId;
    }

    public void setVaultId(int vaultId) {
        this.vaultId = vaultId;
    }

    public String getWebsiteName() {

        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }


}
