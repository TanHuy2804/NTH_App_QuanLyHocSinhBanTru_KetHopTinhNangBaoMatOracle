/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.ChiTietDeThi_POJO;
import POJO.DeThi_POJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

import java.sql.CallableStatement;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author TAN HUY
 */
public class ChiTietDeThi_DAO {  
    public static ArrayList<ChiTietDeThi_POJO> LayThongTinChiTietDeThi(String maDe) {
        ArrayList<ChiTietDeThi_POJO> dsctdt = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = new DBConnect().GetConnect();
            pstmt = connection.prepareStatement("SELECT DISTINCT ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc FROM NV001.ChiTietDeThi WHERE MaDeThi = ?");

            // Kiểm tra maDe trước khi thực hiện truy vấn
            if (maDe == null || maDe.isEmpty()) {
                System.out.println("Mã đề không hợp lệ");
                return dsctdt;
            }

            pstmt.setString(1, maDe);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String thoiGianThi = rs.getString("ThoiGianThi");
                String thoiGianBatDau = rs.getString("ThoiGianBatDau");
                String thoiGianKetThuc = rs.getString("ThoiGianKetThuc");

                ChiTietDeThi_POJO cn = new ChiTietDeThi_POJO(thoiGianThi, thoiGianBatDau, thoiGianKetThuc);
                dsctdt.add(cn);
                System.out.println("Lấy dữ liệu chi tiết đề thi thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Lấy dữ liệu chi tiết đề thi thất bại!");
            //e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                // Không đóng connection ở đây để duy trì kết nối
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
        return dsctdt;
    }
    public static ArrayList<DeThi_POJO> LayDeThiTheoMaMonHoc(String maMonHoc) {
        ArrayList<DeThi_POJO> dsDeThi = new ArrayList<>();
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            connection = new DBConnect().GetConnect();
            cstmt = connection.prepareCall("{CALL NV001.DeThi_Where_MaMonHoc(?, ?)}");
            cstmt.setString(1, maMonHoc);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);

            cstmt.execute();

            rs = (ResultSet) cstmt.getObject(2);
            while (rs.next()) {
                String maDeThi = rs.getString("MADETHI");
                String ngaySoanDe = rs.getString("NGAYSOANDE");
                String soluongCauHoi = rs.getString("SOLUONGCAUHOI");
                String trangThai = rs.getString("TRANGTHAI");
                String maNV = rs.getString("MANV");
                String maMH = rs.getString("MAMH");

                DeThi_POJO deThi = new DeThi_POJO(maDeThi, ngaySoanDe, soluongCauHoi, trangThai, maNV, maMH);
                dsDeThi.add(deThi);
            }
        } catch (SQLException e) {
            System.out.println("Lấy dữ liệu đề thi thất bại!");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
                // Không đóng kết nối ở đây để duy trì kết nối
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsDeThi;
    }

}
