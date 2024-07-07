/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author SONDAY
 */
import POJO.MonHoc;
import java.util.ArrayList;
import java.sql.ResultSet;

public class MonHoc_DAO {
    public static ArrayList<MonHoc> LayThongTinMonHoc() {
    ArrayList<MonHoc> dsch = new ArrayList<MonHoc>();
    try {
        String sql = "SELECT * FROM NV001.MONHOC";
        DBConnect conn = new DBConnect();
        conn.GetConnect();           
        ResultSet rs = conn.executeQuery(sql);     
        while (rs.next()) {  
            //System.out.println("Vào được trong while!");
            String maMH = rs.getString("MaMH");
            String tenMH = rs.getString("TenMH");
            String maLop = rs.getString("MaLop");

            MonHoc cn = new MonHoc(maMH, tenMH, maLop);
            
            dsch.add(cn);
            System.out.println("Lay du lieu mon hoc thanh cong!");
            }             
        } catch (Exception e) {
            System.out.println("Lay du lieu mon hoc that bai!");
            //e.printStackTrace();
        }
        return dsch;
    }
    public static ArrayList<String> get_MonHocList_MonHoc() {
        ArrayList<String> maMHList_MonHoc = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT MaMH FROM NV001.MONHOC";
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
                String maLop = rs.getString("MaMH");
                if (maLop != null) {
                    maMHList_MonHoc.add(maLop.trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu mã môn học" + e.getMessage());
        }
        return maMHList_MonHoc;
    }
}
