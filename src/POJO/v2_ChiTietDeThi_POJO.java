/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author SONDAY
 */
public class v2_ChiTietDeThi_POJO {
    private String NGAYTHI;
    private String THOIGIANTHI;
    private String THOIGIANBATDAU;
    private String THOIGIANKETTHUC;

    public v2_ChiTietDeThi_POJO(String NGAYTHI, String THOIGIANTHI, String THOIGIANBATDAU, String THOIGIANKETTHUC) {
        this.NGAYTHI = NGAYTHI;
        this.THOIGIANTHI = THOIGIANTHI;
        this.THOIGIANBATDAU = THOIGIANBATDAU;
        this.THOIGIANKETTHUC = THOIGIANKETTHUC;
    }

    public String getNGAYTHI() {
        return NGAYTHI;
    }
    
    public String getTHOIGIANTHI() {
        return THOIGIANTHI;
    }

    public String getTHOIGIANBATDAU() {
        return THOIGIANBATDAU;
    }

    public String getTHOIGIANKETTHUC() {
        return THOIGIANKETTHUC;
    }

    public void setNGAYTHI(String NGAYTHI) {
        this.NGAYTHI = NGAYTHI;
    }
    
    public void setTHOIGIANTHI(String THOIGIANTHI) {
        this.THOIGIANTHI = THOIGIANTHI;
    }

    public void setTHOIGIANBATDAU(String THOIGIANBATDAU) {
        this.THOIGIANBATDAU = THOIGIANBATDAU;
    }

    public void setTHOIGIANKETTHUC(String THOIGIANKETTHUC) {
        this.THOIGIANKETTHUC = THOIGIANKETTHUC;
    }
    
}
