/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author TAN HUY
 */
public class CauHoi_POJO {
    private String MACAUHOI;
    private String CAUHOI;
    private String DAPAN_A;
    private String DAPAN_B;
    private String DAPAN_C;
    private String DAPAN_D;
    private String DAPANDUNG;
    private String MAMH;

    public CauHoi_POJO(String MACAUHOI, String CAUHOI, String DAPAN_A, String DAPAN_B, String DAPAN_C, String DAPAN_D, String DAPANDUNG, String MAMH) {
        this.MACAUHOI = MACAUHOI;
        this.CAUHOI = CAUHOI;
        this.DAPAN_A = DAPAN_A;
        this.DAPAN_B = DAPAN_B;
        this.DAPAN_C = DAPAN_C;
        this.DAPAN_D = DAPAN_D;
        this.DAPANDUNG = DAPANDUNG;
        this.MAMH = MAMH;
    }
    public CauHoi_POJO() {
    }
    public String getMACAUHOI() {
        return MACAUHOI;
    }

    public String getCAUHOI() {
        return CAUHOI;
    }

    public String getDAPAN_A() {
        return DAPAN_A;
    }

    public String getDAPAN_B() {
        return DAPAN_B;
    }

    public String getDAPAN_C() {
        return DAPAN_C;
    }

    public String getDAPAN_D() {
        return DAPAN_D;
    }

    public String getDAPANDUNG() {
        return DAPANDUNG;
    }

    public String getMAMH() {
        return MAMH;
    }

    public void setMACAUHOI(String MACAUHOI) {
        this.MACAUHOI = MACAUHOI;
    }

    public void setCAUHOI(String CAUHOI) {
        this.CAUHOI = CAUHOI;
    }

    public void setDAPAN_A(String DAPAN_A) {
        this.DAPAN_A = DAPAN_A;
    }

    public void setDAPAN_B(String DAPAN_B) {
        this.DAPAN_B = DAPAN_B;
    }

    public void setDAPAN_C(String DAPAN_C) {
        this.DAPAN_C = DAPAN_C;
    }

    public void setDAPAN_D(String DAPAN_D) {
        this.DAPAN_D = DAPAN_D;
    }

    public void setDAPANDUNG(String DAPANDUNG) {
        this.DAPANDUNG = DAPANDUNG;
    }

    public void setMAMH(String MAMH) {
        this.MAMH = MAMH;
    }
    @Override
    public String toString() {
        return "CauHoi_POJO{" + "MACAUHOI=" + MACAUHOI + ", CAUHOI=" + CAUHOI + ", DAPAN_A=" + DAPAN_A + ", DAPAN_B=" + DAPAN_B + ", DAPAN_C=" + DAPAN_C + ", DAPAN_D=" + DAPAN_D + ", DAPANDUNG=" + DAPANDUNG + ", MAMH=" + MAMH + '}';
    }
    
}
