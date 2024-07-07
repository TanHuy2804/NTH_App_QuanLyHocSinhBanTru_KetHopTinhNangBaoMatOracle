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
public class DBA_FGA_AUDIT_TRAIL {
    private String DB_USER,USERHOST,OBJECT_NAME,SQL_TEXT,POLICY_NAME;
    private Timestamp TIMESTAMP;

    public DBA_FGA_AUDIT_TRAIL(String DB_USER, String USERHOST, String OBJECT_NAME, String SQL_TEXT, String POLICY_NAME, Timestamp TIMESTAMP) {
        this.DB_USER = DB_USER;
        this.USERHOST = USERHOST;
        this.OBJECT_NAME = OBJECT_NAME;
        this.SQL_TEXT = SQL_TEXT;
        this.POLICY_NAME = POLICY_NAME;
        this.TIMESTAMP = TIMESTAMP;
    }

    public DBA_FGA_AUDIT_TRAIL() {
    }

    public String getDB_USER() {
        return DB_USER;
    }

    public void setDB_USER(String DB_USER) {
        this.DB_USER = DB_USER;
    }

    public String getUSERHOST() {
        return USERHOST;
    }

    public void setUSERHOST(String USERHOST) {
        this.USERHOST = USERHOST;
    }

    public String getOBJECT_NAME() {
        return OBJECT_NAME;
    }

    public void setOBJECT_NAME(String OBJECT_NAME) {
        this.OBJECT_NAME = OBJECT_NAME;
    }

    public String getSQL_TEXT() {
        return SQL_TEXT;
    }

    public void setSQL_TEXT(String SQL_TEXT) {
        this.SQL_TEXT = SQL_TEXT;
    }

    public String getPOLICY_NAME() {
        return POLICY_NAME;
    }

    public void setPOLICY_NAME(String POLICY_NAME) {
        this.POLICY_NAME = POLICY_NAME;
    }

    public Timestamp getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(Timestamp TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

   
}
