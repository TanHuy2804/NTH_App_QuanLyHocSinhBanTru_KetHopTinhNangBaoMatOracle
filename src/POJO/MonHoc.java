/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author SONDAY
 */
public class MonHoc {

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public MonHoc() {
    }

    public MonHoc(String maMH, String tenMH, String tenLop) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.tenLop = tenLop;
    }

    @Override
    public String toString() {
        return "MonHoc{" + "maMH=" + maMH + ", tenMH=" + tenMH + ", tenLop=" + tenLop + '}';
    }
    private String maMH, tenMH, tenLop;
}
