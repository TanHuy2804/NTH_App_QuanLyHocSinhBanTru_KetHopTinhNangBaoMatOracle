/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author TAN HUY
 */
public class DeThi_POJO {
    private String maDeThi;
    private String ngaySoanDe;
    private String soluongCauHoi;
    private String trangThai;
    private String maNV;
    private String maMH;

    public DeThi_POJO(String maDeThi, String ngaySoanDe, String soluongCauHoi, String trangThai, String maNV, String maMH) {
        this.maDeThi = maDeThi;
        this.ngaySoanDe = ngaySoanDe;
        this.soluongCauHoi = soluongCauHoi;
        this.trangThai = trangThai;
        this.maNV = maNV;
        this.maMH = maMH;
    }

    public String getMaDeThi() {
        return maDeThi;
    }

    public String getNgaySoanDe() {
        return ngaySoanDe;
    }

    public String getSoluongCauHoi() {
        return soluongCauHoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getMaNV() {
        return maNV;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaDeThi(String maDeThi) {
        this.maDeThi = maDeThi;
    }

    public void setNgaySoanDe(String ngaySoanDe) {
        this.ngaySoanDe = ngaySoanDe;
    }

    public void setSoluongCauHoi(String soluongCauHoi) {
        this.soluongCauHoi = soluongCauHoi;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setMaNH(String maMH) {
        this.maMH = maMH;
    }
    
}
