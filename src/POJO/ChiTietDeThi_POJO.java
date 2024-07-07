/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author TAN HUY
 */
public class ChiTietDeThi_POJO {
    private String THOIGIANTHI;
    private String THOIGIANBATDAU;
    private String THOIGIANKETTHUC;

    public ChiTietDeThi_POJO(String THOIGIANTHI, String THOIGIANBATDAU, String THOIGIANKETTHUC) {
        
        this.THOIGIANTHI = THOIGIANTHI;
        this.THOIGIANBATDAU = THOIGIANBATDAU;
        this.THOIGIANKETTHUC = THOIGIANKETTHUC;
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
