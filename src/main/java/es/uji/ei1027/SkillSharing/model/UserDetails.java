package es.uji.ei1027.SkillSharing.model;

public class UserDetails {
    String username;
    String password;
    boolean isSkp;

    public boolean isSkp() {
        return isSkp;
    }

    public void setSkp(boolean skp) {
        isSkp = skp;
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