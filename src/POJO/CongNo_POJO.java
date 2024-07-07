/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author TAN HUY
 */
public class CongNo_POJO {
    private String MACONGNO;
    private String MAHS;
    private String NGAYTAOCONGNO;
    private String TIENHOCPHI;
    private String TIENAN;
    private String TIENPHUTHU;
    private String TONGCONGNO;
    private String TRANGTHAI;
    
    public CongNo_POJO (String MACONGNO, String MAHS, String NGAYTAOCONGNO, String TIENHOCPHI,  String TIENAN, String TIENPHUTHU, String TONGCONGNO, String TRANGTHAI) {
        this.MACONGNO = MACONGNO;
        this.MAHS = MAHS;
        this.NGAYTAOCONGNO = NGAYTAOCONGNO;
        this.TIENHOCPHI = TIENHOCPHI;
        this.TIENAN = TIENAN;
        this.TIENPHUTHU = TIENPHUTHU;
        this.TONGCONGNO = TONGCONGNO;
        this.TRANGTHAI = TRANGTHAI;
    }
    
    public CongNo_POJO () {
    }

    public String getMACONGNO() {
        return MACONGNO;
    }

    public void setMACONGNO(String MACONGNO) {
        this.MACONGNO = MACONGNO;
    }

    public String getMAHS() {
        return MAHS;
    }

    public void setMAHS(String MAHS) {
        this.MAHS = MAHS;
    }

    public String getNGAYTAOCONGNO() {
        return NGAYTAOCONGNO;
    }

    public void setNGAYTAOCONGNO(String NGAYTAOCONGNO) {
        this.NGAYTAOCONGNO = NGAYTAOCONGNO;
    }

    public String getTIENHOCPHI() {
        return TIENHOCPHI;
    }

    public void setTIENHOCPHI(String TIENHOCPHI) {
        this.TIENHOCPHI = TIENHOCPHI;
    }

    public String getTIENAN() {
        return TIENAN;
    }

    public void setTIENAN(String TIENAN) {
        this.TIENAN = TIENAN;
    }

    public String getTIENPHUTHU() {
        return TIENPHUTHU;
    }

    public void setTIENPHUTHU(String TIENPHUTHU) {
        this.TIENPHUTHU = TIENPHUTHU;
    }

    public String getTONGCONGNO() {
        return TONGCONGNO;
    }

    public void setTONGCONGNO(String TONGCONGNO) {
        this.TONGCONGNO = TONGCONGNO;
    }

    public String getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(String TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }  
}
