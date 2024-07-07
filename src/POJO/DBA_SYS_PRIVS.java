/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Admin
 */
public class DBA_SYS_PRIVS {
    private String GRANTEE, OWNER, TABLE_NAME, GRANTOR, PRIVILEGE;

    public DBA_SYS_PRIVS(String GRANTEE, String OWNER, String TABLE_NAME, String GRANTOR, String PRIVILEGE) {
        this.GRANTEE = GRANTEE;
        this.OWNER = OWNER;
        this.TABLE_NAME = TABLE_NAME;
        this.GRANTOR = GRANTOR;
        this.PRIVILEGE = PRIVILEGE;
    }

    public DBA_SYS_PRIVS() {
    }

    public String getGRANTEE() {
        return GRANTEE;
    }

    public void setGRANTEE(String GRANTEE) {
        this.GRANTEE = GRANTEE;
    }

    public String getOWNER() {
        return OWNER;
    }

    public void setOWNER(String OWNER) {
        this.OWNER = OWNER;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getGRANTOR() {
        return GRANTOR;
    }

    public void setGRANTOR(String GRANTOR) {
        this.GRANTOR = GRANTOR;
    }

    public String getPRIVILEGE() {
        return PRIVILEGE;
    }

    public void setPRIVILEGE(String PRIVILEGE) {
        this.PRIVILEGE = PRIVILEGE;
    }

    

}