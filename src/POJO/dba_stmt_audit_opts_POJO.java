/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author TAN HUY
 */
public class dba_stmt_audit_opts_POJO {
    private String user_name;
    private String audit_option;
    private String success;
    private String failure;

    public dba_stmt_audit_opts_POJO(String user_name, String audit_option, String success, String failure) {
        this.user_name = user_name;
        this.audit_option = audit_option;
        this.success = success;
        this.failure = failure;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAudit_option() {
        return audit_option;
    }

    public void setAudit_option(String audit_option) {
        this.audit_option = audit_option;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }
    
}
