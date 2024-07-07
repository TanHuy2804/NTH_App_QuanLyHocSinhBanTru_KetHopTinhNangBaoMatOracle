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
public class GiamSatThietBiTruyCap_POJO {
    private String schemaname, machine, program, type, module, ip_address;
    private Timestamp logon_time ;

    public String getSchemaname() {
        return schemaname;
    }

    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public Timestamp getLogon_time() {
        return logon_time;
    }

    public void setLogon_time(Timestamp logon_time) {
        this.logon_time = logon_time;
    }

    

    

    public GiamSatThietBiTruyCap_POJO(String schemaname, String machine, String program, String type, String module, String ip_address, Timestamp logon_time) {
        this.schemaname = schemaname;
        this.machine = machine;
        this.program = program;
        this.type = type;
        this.module = module;
        this.ip_address = ip_address;
        this.logon_time = logon_time;
    }

    public GiamSatThietBiTruyCap_POJO() {
    }
    
}
