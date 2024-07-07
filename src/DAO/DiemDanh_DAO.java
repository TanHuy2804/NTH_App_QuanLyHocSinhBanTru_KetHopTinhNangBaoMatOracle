/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author SONDAY
 */

import POJO.DiemDanh;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.sql.CallableStatement;
import java.sql.ResultSet;

public class DiemDanh_DAO {

    public static ArrayList<DiemDanh> LayThongTin_DiemDanh() {
        ArrayList<DiemDanh> ds_dd = new ArrayList<DiemDanh>();
        try {
            String sql = "SELECT * FROM NV001.DIEMDANH";
            //ConnectionDB conn = new ConnectionDB();
            DBConnect conn = new DBConnect();
            conn.GetConnect();

            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
                String ngaydiemdanhh = rs.getString("NgayDiemDanh");
                String mahss = rs.getString("MaHS");
                String tenhss = rs.getString("TenHS");
                String trangthaidiemdanhh = rs.getString("TrangThaiDiemDanh");
                String ghichuu = rs.getString("GhiChu");
                String manvv = rs.getString("MaNV");
                String malopp = rs.getString("MaLop");

                Connection connection = conn.GetConnect(); // Lấy đối tượng Connection

                DiemDanh dd = new DiemDanh(ngaydiemdanhh, mahss, tenhss, trangthaidiemdanhh, ghichuu, manvv, malopp);
                ds_dd.add(dd);
                System.out.println("Lay du lieu diem danh thanh cong!");
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return ds_dd;
    }

    // Thêm hàm để lấy thông tin học sinh theo lớp
    public static ArrayList<DiemDanh> LayThongTin_DiemDanhTheoLop_DiemDanh(String maLop) {
        ArrayList<DiemDanh> danhSachDiemDanh = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnect.GetConnect();
            String query = "SELECT * FROM NV001.DiemDanh WHERE MaLop = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maLop);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Đọc thông tin từ ResultSet và thêm vào danh sách
                DiemDanh dd = new DiemDanh();
                dd.setNgayDiemDanh(resultSet.getString("NgayDiemDanh"));
                dd.setMaHS(resultSet.getString("MaHS"));
                dd.setTenHS(resultSet.getString("TenHS"));
                dd.setTrangThaiDiemDanh(resultSet.getString("TrangThaiDiemDanh"));
                dd.setGhiChu(resultSet.getString("GhiChu"));
                dd.setMaNV(resultSet.getString("MaNV"));
                dd.setMaLop(resultSet.getString("MaLop"));

                danhSachDiemDanh.add(dd);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            // Xử lý nếu có lỗi
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Đóng connection, preparedStatement và resultSet ở đây
        }

        return danhSachDiemDanh;
    }

    public static ArrayList<DiemDanh> LayThongTin_DiemDanhTheoNgayVaLop_DiemDanh(String formattedDate, String maLop) {
        ArrayList<DiemDanh> dsDiemDanh = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnect.GetConnect();

            // Thêm điều kiện cho trường hợp "Tất cả lớp"
            String query;
            if ("Tất cả lớp".equals(maLop)) {
                query = "SELECT * FROM NV001.DiemDanh WHERE TO_CHAR(TRUNC(NgayDiemDanh), 'Mon dd, yyyy') = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, formattedDate);
            } else {
                query = "SELECT * FROM NV001.DiemDanh WHERE TO_CHAR(TRUNC(NgayDiemDanh), 'Mon dd, yyyy') = ? AND MaLop = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, formattedDate);
                preparedStatement.setString(2, maLop);
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Đọc thông tin từ ResultSet và thêm vào danh sách
                DiemDanh dd = new DiemDanh();
                dd.setNgayDiemDanh(resultSet.getString("NgayDiemDanh"));
                dd.setMaHS(resultSet.getString("MaHS"));
                dd.setTenHS(resultSet.getString("TenHS"));
                dd.setTrangThaiDiemDanh(resultSet.getString("TrangThaiDiemDanh"));
                dd.setGhiChu(resultSet.getString("GhiChu"));
                dd.setMaNV(resultSet.getString("MaNV"));
                dd.setMaLop(resultSet.getString("MaLop"));

                dsDiemDanh.add(dd);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return dsDiemDanh;
    }

    // lấy mã lớp từ mã học sinh
    public static String LayMaLop(String maHS) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{call NV001.LayMaLop(?, ?)}");

            callableStatement.setString(1, maHS);
            callableStatement.registerOutParameter(2, Types.VARCHAR);

            callableStatement.execute();

            String malop = callableStatement.getString(2);

            //callableStatement.close();
            return malop;
        } catch (Exception e) {
           //e.printStackTrace();
            return "Lỗi";
        }
    }

    // lấy tên hs từ mã học sinh
    public static String LayTenHS(String maHS) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{call NV001.LayTenHS(?, ?)}");

            callableStatement.setString(1, maHS);
            callableStatement.registerOutParameter(2, Types.VARCHAR);

            callableStatement.execute();

            String tenHS = callableStatement.getString(2);

            callableStatement.close();
            return tenHS;
        } catch (Exception e) {
            //e.printStackTrace();
            return "Lỗi";
        }
    }

    // thêm điểm danh
    public static int themDiemDanh_xacthuckhuonmat(String maHS, String tenHS, String ngayDiemDanh, String trangthai, String ghichu, String maLop, String manv) {
        // Chuyển đổi ngayDiemDanh từ String sang java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(ngayDiemDanh);
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{call NV001.ThemDiemDanh(?, ?, ?, ?, ?, ?, ?)}");

            // Thiết lập giá trị cho các tham số của stored procedure
            callableStatement.setDate(1, Date.valueOf(ngayDiemDanh));
            callableStatement.setString(2, maHS);
            callableStatement.setString(3, tenHS);
            callableStatement.setString(4, trangthai);
            callableStatement.setString(5, ghichu);
            callableStatement.setString(6, manv);
            callableStatement.setString(7, maLop);

            // Thực thi stored procedure
            callableStatement.execute();

            // Đóng kết nối và callableStatement
            callableStatement.close();
            return 1; // thành công
        } catch (Exception e) {
            //e.printStackTrace();
            return -1; // thất bại
        }
    }
}
