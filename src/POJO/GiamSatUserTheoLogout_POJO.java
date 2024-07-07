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
public class GiamSatUserTheoLogout_POJO {
    private String key_id,username,hoat_dong;
    private Timestamp logout_time;

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

    public Timestamp getLogout_time() {
        return logout_time;
    }

    public void setLogout_time(Timestamp logout_time) {
        this.logout_time = logout_time;
    }

    public GiamSatUserTheoLogout_POJO(String key_id, String username, Timestamp logout_time, String hoat_dong) {
        this.key_id = key_id;
        this.username = username;
        this.hoat_dong = hoat_dong;
        this.logout_time = logout_time;
    }

    public GiamSatUserTheoLogout_POJO() {
    }
}
