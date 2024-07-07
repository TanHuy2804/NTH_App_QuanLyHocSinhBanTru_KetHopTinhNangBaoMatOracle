/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author SONDAY
 */
public class CongNo {

    public String getMaCongNo() {
        return maCongNo;
    }

    public void setMaCongNo(String maCongNo) {
        this.maCongNo = maCongNo;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public String getNgayTaoCongNo() {
        return ngayTaoCongNo;
    }

    public void setNgayTaoCongNo(String ngayTaoCongNo) {
        this.ngayTaoCongNo = ngayTaoCongNo;
    }

    public String getTienHocPhi() {
        return tienHocPhi;
    }

    public void setTienHocPhi(String tienHocPhi) {
        this.tienHocPhi = tienHocPhi;
    }

    public String getTienAn() {
        return tienAn;
    }

    public void setTienAn(String tienAn) {
        this.tienAn = tienAn;
    }

    public String getTienPhuThu() {
        return tienPhuThu;
    }

    public void setTienPhuThu(String tienPhuThu) {
        this.tienPhuThu = tienPhuThu;
    }

    public String getTongCongNo() {
        return tongCongNo;
    }

    public void setTongCongNo(String tongCongNo) {
        this.tongCongNo = tongCongNo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public CongNo() {
    }

    public CongNo(String maCongNo, String maHocSinh, String ngayTaoCongNo, String tienHocPhi, String tienAn, String tienPhuThu, String tongCongNo, String trangThai) {
        this.maCongNo = maCongNo;
        this.maHocSinh = maHocSinh;
        this.ngayTaoCongNo = ngayTaoCongNo;
        this.tienHocPhi = tienHocPhi;
        this.tienAn = tienAn;
        this.tienPhuThu = tienPhuThu;
        this.tongCongNo = tongCongNo;
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "CongNo{" + "maCongNo=" + maCongNo + ", maHocSinh=" + maHocSinh + ", ngayTaoCongNo=" + ngayTaoCongNo + ", tienHocPhi=" + tienHocPhi + ", tienAn=" + tienAn + ", tienPhuThu=" + tienPhuThu + ", tongCongNo=" + tongCongNo + ", trangThai=" + trangThai + '}';
    }
    private String maCongNo, maHocSinh, ngayTaoCongNo, tienHocPhi, tienAn, tienPhuThu, tongCongNo, trangThai;
}
