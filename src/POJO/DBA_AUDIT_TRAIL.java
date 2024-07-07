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
public class DBA_AUDIT_TRAIL {
    private String username,obj_name,action_name;
    private Timestamp timestamp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getObj_name() {
        return obj_name;
    }

    public void setObj_name(String obj_name) {
        this.obj_name = obj_name;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public DBA_AUDIT_TRAIL(String username, Timestamp timestamp, String obj_name, String action_name) {
        this.username = username;
        this.timestamp = timestamp;
        this.obj_name = obj_name;
        this.action_name = action_name;
    }

    public DBA_AUDIT_TRAIL() {
    }

}
