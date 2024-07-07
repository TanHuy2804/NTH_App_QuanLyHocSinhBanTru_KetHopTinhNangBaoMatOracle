/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author TAN HUY
 */
public class DBA_OBJ_AUDIT_OPTS_POJO {
    private String OWNER;
    private String OBJECT_NAME;
    private String OBJECT_TYPE;
    private String DEL;
    private String INS;
    private String SEL;
    private String UPD;

    public DBA_OBJ_AUDIT_OPTS_POJO(String OWNER, String OBJECT_NAME, String OBJECT_TYPE, String DEL, String INS, String SEL, String UPD) {
        this.OWNER = OWNER;
        this.OBJECT_NAME = OBJECT_NAME;
        this.OBJECT_TYPE = OBJECT_TYPE;
        this.DEL = DEL;
        this.INS = INS;
        this.SEL = SEL;
        this.UPD = UPD;
    }

    public String getOWNER() {
        return OWNER;
    }

    public void setOWNER(String OWNER) {
        this.OWNER = OWNER;
    }

    public String getOBJECT_NAME() {
        return OBJECT_NAME;
    }

    public void setOBJECT_NAME(String OBJECT_NAME) {
        this.OBJECT_NAME = OBJECT_NAME;
    }

    public String getOBJECT_TYPE() {
        return OBJECT_TYPE;
    }

    public void setOBJECT_TYPE(String OBJECT_TYPE) {
        this.OBJECT_TYPE = OBJECT_TYPE;
    }

    public String getDEL() {
        return DEL;
    }

    public void setDEL(String DEL) {
        this.DEL = DEL;
    }

    public String getINS() {
        return INS;
    }

    public void setINS(String INS) {
        this.INS = INS;
    }

    public String getSEL() {
        return SEL;
    }

    public void setSEL(String SEL) {
        this.SEL = SEL;
    }

    public String getUPD() {
        return UPD;
    }

    public void setUPD(String UPD) {
        this.UPD = UPD;
    }
    
}
