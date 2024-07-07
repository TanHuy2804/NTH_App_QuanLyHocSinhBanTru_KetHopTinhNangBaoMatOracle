/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author ACER
 */
public class POJO_DangKy_KPA {

    /*
    MaHS VARCHAR2(50),
    NgayDangKyKP VARCHAR2(11),
    ThuTrongTuan int,
    Gia float,
    MaComBoMon VARCHAR2(50),
    QRCode VARCHAR2(100),
     */

    String MaHS, NgayDangKyKP;
    int ThuTrongTuan;
    float Gia;
    String MaComBoMon;
    String QRCode;

    public POJO_DangKy_KPA(String MaHS, String NgayDangKyKP, int ThuTrongTuan, float Gia, String MaComBoMon, String QRCode) {
        this.MaHS = MaHS;
        this.NgayDangKyKP = NgayDangKyKP;
        this.ThuTrongTuan = ThuTrongTuan;
        this.Gia = Gia;
        this.MaComBoMon = MaComBoMon;
        this.QRCode = QRCode;
    }

    public String getMaHS() {
        return MaHS;
    }

    public void setMaHS(String MaHS) {
        this.MaHS = MaHS;
    }

    public String getNgayDangKyKP() {
        return NgayDangKyKP;
    }

    public void setNgayDangKyKP(String NgayDangKyKP) {
        this.NgayDangKyKP = NgayDangKyKP;
    }

    public int getThuTrongTuan() {
        return ThuTrongTuan;
    }

    public void setThuTrongTuan(int ThuTrongTuan) {
        this.ThuTrongTuan = ThuTrongTuan;
    }

    public float getGia() {
        return Gia;
    }

    public void setGia(float Gia) {
        this.Gia = Gia;
    }

    public String getMaComBoMon() {
        return MaComBoMon;
    }

    public void setMaComBoMon(String MaComBoMon) {
        this.MaComBoMon = MaComBoMon;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    

}
