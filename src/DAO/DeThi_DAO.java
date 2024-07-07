/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author SONDAY
 */
import POJO.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import java.sql.CallableStatement;
import oracle.jdbc.OracleTypes;
public class DeThi_DAO {

    public static ArrayList<DeThi_POJO> LayThongTin_DeThi() {
        ArrayList<DeThi_POJO> ds_dethi = new ArrayList<DeThi_POJO>();
        try {
            String sql = "SELECT * FROM NV001.DETHI";
            DBConnect conn = new DBConnect();
            conn.GetConnect();

            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
                String madethii = rs.getString("MaDeThi");
                String ngaysoann = rs.getString("NgaySoanDe");
                String soluongcauhoii = rs.getString("SoLuongCauHoi");
                String trangthaii = rs.getString("TrangThai");
                String magvv = rs.getString("MaNV");
                String mamhh = rs.getString("MaMH");

                Connection connection = conn.GetConnect(); // Lấy đối tượng Connection

                DeThi_POJO dethi = new DeThi_POJO(madethii, ngaysoann, soluongcauhoii, trangthaii, magvv, mamhh);
                ds_dethi.add(dethi);
                System.out.println("Lay du lieu de thi thanh cong!");
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return ds_dethi;
    }

    public static boolean KiemTraTonTaiMaDeThi(String maDeThi) throws SQLException {
        boolean tonTai = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            DBConnect conn = new DBConnect();
            connection = conn.GetConnect();

            // Đếm số lượng bản ghi của MaDeThi
            String sql = "SELECT COUNT(*) FROM NV001.DETHI WHERE MADETHI = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maDeThi);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    tonTai = true;
                }
            }
        } finally {

        }

        return tonTai;
    }
    public static int LayThoiGianThi(String maDeThi) {
        int thoiGianThi = 0;
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            DBConnect conn = new DBConnect();
            connection = conn.GetConnect();

            callableStatement = connection.prepareCall("{CALL NV001.ThoiGianThi_Where_MaDeThi(?, ?)}");
            callableStatement.setString(1, maDeThi);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(2);

            if (resultSet.next()) {
                String thoiGianThiStr = resultSet.getString("ThoiGianThi");
                // Tách phần giờ và phút từ chuỗi
    //            String[] parts = thoiGianThiStr.split(":");
    //            int phut = Integer.parseInt(parts[0]);
    //            int giay = Integer.parseInt(parts[1]);
    //            thoiGianThi = phut; 
    //            // Trích xuất phần số từ chuỗi và loại bỏ phần "phút"
                String thoiGianThiStrWithoutMinute = thoiGianThiStr.replaceAll("[^\\d]", "");
                thoiGianThi = Integer.parseInt(thoiGianThiStrWithoutMinute);
            }



            System.out.println("Lấy thời gian thi thành công!");
        } catch (SQLException e) {
            System.out.println("Lấy thời gian thi thất bại: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
                //if (connection != null) connection.close();
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
        return thoiGianThi;
    }


    public static String get_MaDeThi_CuoiCung() {
        String lastMaDeThi = null;
        try {
            String sql = "SELECT MaDeThi FROM (SELECT MaDeThi FROM NV001.DETHI ORDER BY MaDeThi DESC) WHERE ROWNUM = 1";
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            ResultSet rs = conn.executeQuery(sql);
            if (rs.next()) {
                lastMaDeThi = rs.getString("MaDeThi");
                if (lastMaDeThi != null) {
                    lastMaDeThi = lastMaDeThi.trim();
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu mã đề thi cuối cùng: " + e.getMessage());
        }
        return lastMaDeThi;
    }

}
