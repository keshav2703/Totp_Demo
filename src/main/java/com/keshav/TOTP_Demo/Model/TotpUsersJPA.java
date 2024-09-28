package com.keshav.TOTP_Demo.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class TotpUsersJPA {
    @Id
    String userName;
    String password;
    String date;
    String userStatus;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public TotpUsersJPA() {
    }

    @Override
    public String toString() {
        return "TotpUsersJPA{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", date='" + date + '\'' +
                ", userStatus='" + userStatus + '\'' +
                '}';
    }

    public TotpUsersJPA(String userName, String password, String date, String userStatus) {
        this.userName = userName;
        this.password = password;
        this.date = date;
        this.userStatus = userStatus;
    }
}
