package Collection;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;

public class User implements Serializable {

    public User() {
    }

    private String login;
    private String password;
    private boolean online;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String MD5Hash(){
        String md5Hex = DigestUtils.md5Hex(password).toUpperCase();
        return md5Hex;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", online=" + online +
                ", id=" + id +
                '}';
    }

}
