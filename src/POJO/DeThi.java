/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author SONDAY
 */
public class DeThi {

    private String maDeThi, ngaySoanDe, soluongCauHoi, trangThai, maNV, maNH;

    @Override
    public String toString() {
        return "DeThi{" + "maDeThi=" + maDeThi + ", ngaySoanDe=" + ngaySoanDe + ", soluongCauHoi=" + soluongCauHoi + ", trangThai=" + trangThai + ", maNV=" + maNV + ", maNH=" + maNH + '}';
    }

    public DeThi(String maDeThi, String ngaySoanDe, String soluongCauHoi, String trangThai, String maNV, String maNH) {
        this.maDeThi = maDeThi;
        this.ngaySoanDe = ngaySoanDe;
        this.soluongCauHoi = soluongCauHoi;
        this.trangThai = trangThai;
        this.maNV = maNV;
        this.maNH = maNH;
    }

    public DeThi() {
    }

    public String getMaDeThi() {
        return maDeThi;
    }

    public void setMaDeThi(String maDeThi) {
        this.maDeThi = maDeThi;
    }

    public String getNgaySoanDe() {
        return ngaySoanDe;
    }

    public void setNgaySoanDe(String ngaySoanDe) {
        this.ngaySoanDe = ngaySoanDe;
    }

    public String getSoluongCauHoi() {
        return soluongCauHoi;
    }

    public void setSoluongCauHoi(String soluongCauHoi) {
        this.soluongCauHoi = soluongCauHoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }
}
