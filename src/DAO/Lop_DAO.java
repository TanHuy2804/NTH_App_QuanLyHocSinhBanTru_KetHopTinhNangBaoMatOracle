/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author SONDAY
 */
import POJO.Lop;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lop_DAO {

//    public static ArrayList<String> get_MaLopList_Lop() {
//        ArrayList<String> maLopList_Lop = new ArrayList<>();
//        try {
//            String sql = "SELECT DISTINCT MaLop FROM NV001.LOP";
//            ConnectionDB conn = new ConnectionDB();
//            conn.GetConnect();
//            ResultSet rs = conn.executeQuery(sql);
//            while (rs.next()) {
//                String maLop = rs.getString("MaLop").trim();
//                maLopList_Lop.add(maLop);
//            }
//        } catch (Exception e) {
//            System.err.println("Không thể lấy dữ liệu mã lớp" + e.getMessage());
//        }
//        return maLopList_Lop;
//    }
    public static ArrayList<String> get_MaLopList_Lop() {
        ArrayList<String> maLopList_Lop = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT MaLop FROM NV001.LOP";
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
                String maLop = rs.getString("MaLop");
                if (maLop != null) {
                    maLopList_Lop.add(maLop.trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu mã lớp" + e.getMessage());
        }
        return maLopList_Lop;
    }

}
