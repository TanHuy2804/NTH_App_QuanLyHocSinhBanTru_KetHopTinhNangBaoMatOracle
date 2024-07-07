/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author SONDAY
 */
public class Lop {

    @Override
    public String toString() {
        return "Lop{" + "maLop=" + maLop + ", tenLop=" + tenLop + ", siSo=" + siSo + ", namHoc=" + namHoc + '}';
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public Integer getSiSo() {
        return siSo;
    }

    public void setSiSo(Integer siSo) {
        this.siSo = siSo;
    }

    public Integer getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(Integer namHoc) {
        this.namHoc = namHoc;
    }

    public Lop(String maLop, String tenLop, Integer siSo, Integer namHoc) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.siSo = siSo;
        this.namHoc = namHoc;
    }

    public Lop() {
    }
    private String maLop, tenLop;
    private Integer siSo, namHoc;
}
