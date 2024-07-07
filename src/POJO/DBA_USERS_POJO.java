/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author TAN HUY
 */
public class DBA_USERS_POJO {
     private String USERNAME, PROFILE;

    public DBA_USERS_POJO(String USERNAME, String PROFILE) {
        this.USERNAME = USERNAME;
        this.PROFILE = PROFILE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPROFILE() {
        return PROFILE;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public void setPROFILE(String PROFILE) {
        this.PROFILE = PROFILE;
    }
     
}
