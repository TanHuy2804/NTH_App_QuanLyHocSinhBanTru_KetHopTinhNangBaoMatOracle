/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class GiamSatUserTheoLogin_POJO {
    private String key_id,username,hoat_dong;
    private Timestamp login_time;

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHoat_dong() {
        return hoat_dong;
    }

    public void setHoat_dong(String hoat_dong) {
        this.hoat_dong = hoat_dong;
    }

    public Timestamp getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Timestamp login_time) {
        this.login_time = login_time;
    }

    public GiamSatUserTheoLogin_POJO(String key_id, String username, Timestamp login_time, String hoat_dong) {
        this.key_id = key_id;
        this.username = username;
        this.hoat_dong = hoat_dong;
        this.login_time = login_time;
    }

    public GiamSatUserTheoLogin_POJO() {
    }
}
