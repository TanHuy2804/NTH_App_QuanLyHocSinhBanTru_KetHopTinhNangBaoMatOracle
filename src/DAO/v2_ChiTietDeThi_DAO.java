/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.ChiTietDeThi_POJO;
import POJO.DeThi_POJO;
import POJO.v2_ChiTietDeThi_POJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

import java.sql.CallableStatement;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author SONDAY
 */
public class v2_ChiTietDeThi_DAO {
    public static ArrayList<v2_ChiTietDeThi_POJO> v2_LayThongTin_4ChiTietDeThi(String maDe) {
        ArrayList<v2_ChiTietDeThi_POJO> dsctdt = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = new DBConnect().GetConnect();
            pstmt = connection.prepareStatement("SELECT DISTINCT NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc FROM NV001.ChiTietDeThi WHERE MaDeThi = ?");

            // Kiểm tra maDe trước khi thực hiện truy vấn
            if (maDe == null || maDe.isEmpty()) {
                System.out.println("Mã đề không hợp lệ");
                return dsctdt;
            }

            pstmt.setString(1, maDe);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String ngayThi = rs.getString("NgayThi");
                String thoiGianThi = rs.getString("ThoiGianThi");
                String thoiGianBatDau = rs.getString("ThoiGianBatDau");
                String thoiGianKetThuc = rs.getString("ThoiGianKetThuc");

                v2_ChiTietDeThi_POJO cn = new v2_ChiTietDeThi_POJO(ngayThi, thoiGianThi, thoiGianBatDau, thoiGianKetThuc);
                dsctdt.add(cn);
                System.out.println("Lấy dữ liệu chi tiết đề thi thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lấy dữ liệu chi tiết đề thi thất bại!");
            //e.printStackTrace();
        } 
        return dsctdt;
    }

}
