/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Admin
 */
public class DBA_ROLE_PRIVS {
    private String GRANTEE, GRANTED_ROLE, ADMIN_OPTION, DELEGATE_OPTION, DEFAULT_ROLE, COMMON;

    public DBA_ROLE_PRIVS(String GRANTEE, String GRANTED_ROLE, String ADMIN_OPTION, String DELEGATE_OPTION, String DEFAULT_ROLE, String COMMON) {
        this.GRANTEE = GRANTEE;
        this.GRANTED_ROLE = GRANTED_ROLE;
        this.ADMIN_OPTION = ADMIN_OPTION;
        this.DELEGATE_OPTION = DELEGATE_OPTION;
        this.DEFAULT_ROLE = DEFAULT_ROLE;
        this.COMMON = COMMON;
    }

    public DBA_ROLE_PRIVS() {
    }

    public String getGRANTEE() {
        return GRANTEE;
    }

    public void setGRANTEE(String GRANTEE) {
        this.GRANTEE = GRANTEE;
    }

    public String getGRANTED_ROLE() {
        return GRANTED_ROLE;
    }

    public void setGRANTED_ROLE(String GRANTED_ROLE) {
        this.GRANTED_ROLE = GRANTED_ROLE;
    }

    public String getADMIN_OPTION() {
        return ADMIN_OPTION;
    }

    public void setADMIN_OPTION(String ADMIN_OPTION) {
        this.ADMIN_OPTION = ADMIN_OPTION;
    }

    public String getDELEGATE_OPTION() {
        return DELEGATE_OPTION;
    }

    public void setDELEGATE_OPTION(String DELEGATE_OPTION) {
        this.DELEGATE_OPTION = DELEGATE_OPTION;
    }

    public String getDEFAULT_ROLE() {
        return DEFAULT_ROLE;
    }

    public void setDEFAULT_ROLE(String DEFAULT_ROLE) {
        this.DEFAULT_ROLE = DEFAULT_ROLE;
    }

    public String getCOMMON() {
        return COMMON;
    }

    public void setCOMMON(String COMMON) {
        this.COMMON = COMMON;
    }

   
}