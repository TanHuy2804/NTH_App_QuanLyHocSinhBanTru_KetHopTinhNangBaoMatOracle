/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author TAN HUY
 */
public class DBA_PROFILES {
    private String PROFILE,RESOURCE_NAME,RESOURCE_TYPE,LIMIT;

    public DBA_PROFILES(String PROFILE, String RESOURCE_NAME, String RESOURCE_TYPE, String LIMIT) {
        this.PROFILE = PROFILE;
        this.RESOURCE_NAME = RESOURCE_NAME;
        this.RESOURCE_TYPE = RESOURCE_TYPE;
        this.LIMIT = LIMIT;
    }

    public String getPROFILE() {
        return PROFILE;
    }

    public String getRESOURCE_NAME() {
        return RESOURCE_NAME;
    }

    public String getRESOURCE_TYPE() {
        return RESOURCE_TYPE;
    }

    public String getLIMIT() {
        return LIMIT;
    }

    public void setPROFILE(String PROFILE) {
        this.PROFILE = PROFILE;
    }

    public void setRESOURCE_NAME(String RESOURCE_NAME) {
        this.RESOURCE_NAME = RESOURCE_NAME;
    }

    public void setRESOURCE_TYPE(String RESOURCE_TYPE) {
        this.RESOURCE_TYPE = RESOURCE_TYPE;
    }

    public void setLIMIT(String LIMIT) {
        this.LIMIT = LIMIT;
    }
    
}
