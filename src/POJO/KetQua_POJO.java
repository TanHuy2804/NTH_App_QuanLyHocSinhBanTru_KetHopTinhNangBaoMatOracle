/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.sql.Timestamp;

/**
 *
 * @author TAN HUY
 */
public class KetQua_POJO {
    private String MAHOCSINH;
    private String MADETHI;
    private String SOCAUDUNG;
    private String DIEMSO;
    private Timestamp THOIGIANLUCNOP;

    public KetQua_POJO(String MAHOCSINH, String MADETHI, String SOCAUDUNG, String DIEMSO, Timestamp THOIGIANLUCNOP) {
        this.MAHOCSINH = MAHOCSINH;
        this.MADETHI = MADETHI;
        this.SOCAUDUNG = SOCAUDUNG;
        this.DIEMSO = DIEMSO;
        this.THOIGIANLUCNOP = THOIGIANLUCNOP;
    }

    public String getMAHOCSINH() {
        return MAHOCSINH;
    }

    public String getMADETHI() {
        return MADETHI;
    }

    public String getSOCAUDUNG() {
        return SOCAUDUNG;
    }

    public String getDIEMSO() {
        return DIEMSO;
    }

    public Timestamp getTHOIGIANLUCNOP() {
        return THOIGIANLUCNOP;
    }

    public void setMAHOCSINH(String MAHOCSINH) {
        this.MAHOCSINH = MAHOCSINH;
    }

    public void setMADETHI(String MADETHI) {
        this.MADETHI = MADETHI;
    }

    public void setSOCAUDUNG(String SOCAUDUNG) {
        this.SOCAUDUNG = SOCAUDUNG;
    }

    public void setDIEMSO(String DIEMSO) {
        this.DIEMSO = DIEMSO;
    }

    public void setTHOIGIANLUCNOP(Timestamp THOIGIANLUCNOP) {
        this.THOIGIANLUCNOP = THOIGIANLUCNOP;
    }
    
    
}
