package model;

public class UserLoginData {
    private UserType userType;
    private String username;
    private String password;
    public UserLoginData(String username, String password, UserType userType) {
        this.username=username;
        this.password=password;
        this.userType=userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
