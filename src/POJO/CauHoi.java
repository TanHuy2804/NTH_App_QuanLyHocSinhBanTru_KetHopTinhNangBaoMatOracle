/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author SONDAY
 */
public class CauHoi {

    public String getMaCH() {
        return maCH;
    }

    public void setMaCH(String maCH) {
        this.maCH = maCH;
    }

    public String getNoidungCH() {
        return noidungCH;
    }

    public void setNoidungCH(String noidungCH) {
        this.noidungCH = noidungCH;
    }

    public String getDapan_A() {
        return dapan_A;
    }

    public void setDapan_A(String dapan_A) {
        this.dapan_A = dapan_A;
    }

    public String getDapan_B() {
        return dapan_B;
    }

    public void setDapan_B(String dapan_B) {
        this.dapan_B = dapan_B;
    }

    public String getDapan_C() {
        return dapan_C;
    }

    public void setDapan_C(String dapan_C) {
        this.dapan_C = dapan_C;
    }

    public String getDapan_D() {
        return dapan_D;
    }

    public void setDapan_D(String dapan_D) {
        this.dapan_D = dapan_D;
    }

    public String getDapan_DUNG() {
        return dapan_DUNG;
    }

    public void setDapan_DUNG(String dapan_DUNG) {
        this.dapan_DUNG = dapan_DUNG;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public CauHoi(String maCH, String noidungCH, String dapan_A, String dapan_B, String dapan_C, String dapan_D, String dapan_DUNG, String maMH) {
        this.maCH = maCH;
        this.noidungCH = noidungCH;
        this.dapan_A = dapan_A;
        this.dapan_B = dapan_B;
        this.dapan_C = dapan_C;
        this.dapan_D = dapan_D;
        this.dapan_DUNG = dapan_DUNG;
        this.maMH = maMH;
    }

    public CauHoi() {
    }

    @Override
    public String toString() {
        return "CauHoi{" + "maCH=" + maCH + ", noidungCH=" + noidungCH + ", dapan_A=" + dapan_A + ", dapan_B=" + dapan_B + ", dapan_C=" + dapan_C + ", dapan_D=" + dapan_D + ", dapan_DUNG=" + dapan_DUNG + ", maMH=" + maMH + '}';
    }
    private String maCH, noidungCH, dapan_A, dapan_B, dapan_C, dapan_D, dapan_DUNG, maMH;
}
