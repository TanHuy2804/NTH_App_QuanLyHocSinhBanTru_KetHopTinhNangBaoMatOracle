/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author SONDAY
 */
public class HocSinh {

    @Override
    public String toString() {
        return "HocSinh{" + "maHS=" + maHS + ", tenHS=" + tenHS + ", gioiTinh=" + gioiTinh + ", ngaySinh=" + ngaySinh + ", diaChi=" + diaChi + ", ngayVaoTruong=" + ngayVaoTruong + ", tinhTrangSucKhoe=" + tinhTrangSucKhoe + ", maLop=" + maLop + ", maPH=" + maPH + '}';
    }

    public String getMaHS() {
        return maHS;
    }

    public void setMaHS(String maHS) {
        this.maHS = maHS;
    }

    public String getTenHS() {
        return tenHS;
    }

    public void setTenHS(String tenHS) {
        this.tenHS = tenHS;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayVaoTruong() {
        return ngayVaoTruong;
    }

    public void setNgayVaoTruong(String ngayVaoTruong) {
        this.ngayVaoTruong = ngayVaoTruong;
    }

    public String getTinhTrangSucKhoe() {
        return tinhTrangSucKhoe;
    }

    public void setTinhTrangSucKhoe(String tinhTrangSucKhoe) {
        this.tinhTrangSucKhoe = tinhTrangSucKhoe;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMaPH() {
        return maPH;
    }

    public void setMaPH(String maPH) {
        this.maPH = maPH;
    }

    public HocSinh(String maHS, String tenHS, String gioiTinh, String ngaySinh, String diaChi, String ngayVaoTruong, String tinhTrangSucKhoe, String maLop, String maPH) {
        this.maHS = maHS;
        this.tenHS = tenHS;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.ngayVaoTruong = ngayVaoTruong;
        this.tinhTrangSucKhoe = tinhTrangSucKhoe;
        this.maLop = maLop;
        this.maPH = maPH;
    }

    public HocSinh() {
    }
    private String maHS, tenHS, gioiTinh, ngaySinh, diaChi, ngayVaoTruong, tinhTrangSucKhoe, maLop, maPH;
}
