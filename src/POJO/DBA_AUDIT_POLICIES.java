/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Admin
 */
public class DBA_AUDIT_POLICIES {
    String OBJECT_SCHEMA, OBJECT_NAME, POLICY_OWNER, POLICY_NAME, POLICY_TEXT, POLICY_COLUMN, SEL, INS, UPD, DEL;

    public DBA_AUDIT_POLICIES(String OBJECT_SCHEMA, String OBJECT_NAME, String POLICY_OWNER, String POLICY_NAME, String POLICY_TEXT, String POLICY_COLUMN, String SEL, String INS, String UPD, String DEL) {
        this.OBJECT_SCHEMA = OBJECT_SCHEMA;
        this.OBJECT_NAME = OBJECT_NAME;
        this.POLICY_OWNER = POLICY_OWNER;
        this.POLICY_NAME = POLICY_NAME;
        this.POLICY_TEXT = POLICY_TEXT;
        this.POLICY_COLUMN = POLICY_COLUMN;
        this.SEL = SEL;
        this.INS = INS;
        this.UPD = UPD;
        this.DEL = DEL;
    }

    public String getOBJECT_SCHEMA() {
        return OBJECT_SCHEMA;
    }

    public void setOBJECT_SCHEMA(String OBJECT_SCHEMA) {
        this.OBJECT_SCHEMA = OBJECT_SCHEMA;
    }

    public String getOBJECT_NAME() {
        return OBJECT_NAME;
    }

    public void setOBJECT_NAME(String OBJECT_NAME) {
        this.OBJECT_NAME = OBJECT_NAME;
    }

    public String getPOLICY_OWNER() {
        return POLICY_OWNER;
    }

    public void setPOLICY_OWNER(String POLICY_OWNER) {
        this.POLICY_OWNER = POLICY_OWNER;
    }

    public String getPOLICY_NAME() {
        return POLICY_NAME;
    }

    public void setPOLICY_NAME(String POLICY_NAME) {
        this.POLICY_NAME = POLICY_NAME;
    }

    public String getPOLICY_TEXT() {
        return POLICY_TEXT;
    }

    public void setPOLICY_TEXT(String POLICY_TEXT) {
        this.POLICY_TEXT = POLICY_TEXT;
    }

    public String getPOLICY_COLUMN() {
        return POLICY_COLUMN;
    }

    public void setPOLICY_COLUMN(String POLICY_COLUMN) {
        this.POLICY_COLUMN = POLICY_COLUMN;
    }

    public String getSEL() {
        return SEL;
    }

    public void setSEL(String SEL) {
        this.SEL = SEL;
    }

    public String getINS() {
        return INS;
    }

    public void setINS(String INS) {
        this.INS = INS;
    }

    public String getUPD() {
        return UPD;
    }

    public void setUPD(String UPD) {
        this.UPD = UPD;
    }

    public String getDEL() {
        return DEL;
    }

    public void setDEL(String DEL) {
        this.DEL = DEL;
    }
    
    
}
