/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author SONDAY
 */
public class DiemDanh {

    @Override
    public String toString() {
        return "DiemDanh{" + "ngayDiemDanh=" + ngayDiemDanh + ", maHS=" + maHS + ", tenHS=" + tenHS + ", trangThaiDiemDanh=" + trangThaiDiemDanh + ", ghiChu=" + ghiChu + ", maNV=" + maNV + ", maLop=" + maLop + '}';
    }

    public String getNgayDiemDanh() {
        return ngayDiemDanh;
    }

    public void setNgayDiemDanh(String ngayDiemDanh) {
        this.ngayDiemDanh = ngayDiemDanh;
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

    public String getTrangThaiDiemDanh() {
        return trangThaiDiemDanh;
    }

    public void setTrangThaiDiemDanh(String trangThaiDiemDanh) {
        this.trangThaiDiemDanh = trangThaiDiemDanh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public DiemDanh(String ngayDiemDanh, String maHS, String tenHS, String trangThaiDiemDanh, String ghiChu, String maNV, String maLop) {
        this.ngayDiemDanh = ngayDiemDanh;
        this.maHS = maHS;
        this.tenHS = tenHS;
        this.trangThaiDiemDanh = trangThaiDiemDanh;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.maLop = maLop;
    }

    public DiemDanh() {
    }

    private String ngayDiemDanh, maHS, tenHS, trangThaiDiemDanh, ghiChu, maNV, maLop;
}
