/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author ACER
 */
public class POJO_Combomonan {
    /** CSDL 
    TenMon1 VARCHAR2(100),
    TenMon2 VARCHAR2(100),
    TenMon3 VARCHAR2(100),
    TenMon4 VARCHAR2(100),
    LoaiComBoMon VARCHAR2(100),
    ThuTrongTuan int,
    GiaTien FLOAT,
    QRCode VARCHAR2(100
    **/
    
    String MaComBoMon, TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon;
    int ThuTrongTuan;
    float GiaTien;
    String QRCode;

    public POJO_Combomonan(String MaComBoMon, String TenMon1, String TenMon2, String TenMon3, String TenMon4, String LoaiComBoMon, int ThuTrongTuan, float GiaTien, String QRCode) {
        this.MaComBoMon = MaComBoMon;
        this.TenMon1 = TenMon1;
        this.TenMon2 = TenMon2;
        this.TenMon3 = TenMon3;
        this.TenMon4 = TenMon4;
        this.LoaiComBoMon = LoaiComBoMon;
        this.ThuTrongTuan = ThuTrongTuan;
        this.GiaTien = GiaTien;
        this.QRCode = QRCode;
    }

    public String getMaComBoMon() {
        return MaComBoMon;
    }

    public void setMaComBoMon(String MaComBoMon) {
        this.MaComBoMon = MaComBoMon;
    }

    public String getTenMon1() {
        return TenMon1;
    }

    public void setTenMon1(String TenMon1) {
        this.TenMon1 = TenMon1;
    }

    public String getTenMon2() {
        return TenMon2;
    }

    public void setTenMon2(String TenMon2) {
        this.TenMon2 = TenMon2;
    }

    public String getTenMon3() {
        return TenMon3;
    }

    public void setTenMon3(String TenMon3) {
        this.TenMon3 = TenMon3;
    }

    public String getTenMon4() {
        return TenMon4;
    }

    public void setTenMon4(String TenMon4) {
        this.TenMon4 = TenMon4;
    }

    public String getLoaiComBoMon() {
        return LoaiComBoMon;
    }

    public void setLoaiComBoMon(String LoaiComBoMon) {
        this.LoaiComBoMon = LoaiComBoMon;
    }

    public int getThuTrongTuan() {
        return ThuTrongTuan;
    }

    public void setThuTrongTuan(int ThuTrongTuan) {
        this.ThuTrongTuan = ThuTrongTuan;
    }

    public float getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(float GiaTien) {
        this.GiaTien = GiaTien;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }
    
    

    
}
