/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author SONDAY
 */
public class HocSinh_DAO {

    public static ArrayList<HocSinh> LayThongTin_HocSinh() {
        ArrayList<HocSinh> ds_hs = new ArrayList<HocSinh>();
        try {
            String sql = "SELECT * FROM NV001.HOCSINH";
            DBConnect conn = new DBConnect();
            conn.GetConnect();

            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
                String mahss = rs.getString("MaHS");
                String tenhss = rs.getString("TenHS");
                String gioitinhh = rs.getString("GioiTinh");
                String ngaysinhh = rs.getString("NgaySinh");
                String diachii = rs.getString("DiaChi");
                String ngayvaotruongg = rs.getString("NgayVaoTruong");
                String ttskk = rs.getString("TinhTrangSucKhoe");
                String malopp = rs.getString("MaLop");
                String maphh = rs.getString("MaPH");

                Connection connection = conn.GetConnect(); // Lấy đối tượng Connection

                HocSinh hs = new HocSinh(mahss, tenhss, gioitinhh, ngaysinhh, diachii, ngayvaotruongg, ttskk, malopp, maphh);
                ds_hs.add(hs);
                System.out.println("Lay du lieu hoc sinh thanh cong!");
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return ds_hs;
    }

    public static ArrayList<String> get_MaHSList_HocSinh() {
        ArrayList<String> maHSList_HocSinh = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT MaHS FROM NV001.HOCSINH";
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
                String maHS = rs.getString("MaHS");
                if (maHS != null) {
                    maHSList_HocSinh.add(maHS.trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu mã học sinh" + e.getMessage());
        }
        return maHSList_HocSinh;
    }

    // Thêm hàm để lấy thông tin học sinh theo lớp
    public static ArrayList<HocSinh> LayThongTin_HocSinhTheoLop_HocSinh(String maLop) {
        ArrayList<HocSinh> danhSachHocSinh = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnect.GetConnect();
            String query = "SELECT * FROM NV001.HocSinh WHERE MaLop = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maLop);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Đọc thông tin từ ResultSet và thêm vào danh sách
                HocSinh hs = new HocSinh();
                hs.setMaHS(resultSet.getString("MaHS"));
                hs.setTenHS(resultSet.getString("TenHS"));
                hs.setGioiTinh(resultSet.getString("GioiTinh"));
                hs.setNgaySinh(resultSet.getString("NgaySinh"));
                hs.setDiaChi(resultSet.getString("DiaChi"));
                hs.setNgayVaoTruong(resultSet.getString("NgayVaoTruong"));
                hs.setTinhTrangSucKhoe(resultSet.getString("TinhTrangSucKhoe"));
                hs.setMaLop(resultSet.getString("MaLop"));
                hs.setMaPH(resultSet.getString("MaPH"));

                danhSachHocSinh.add(hs);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        } finally {
            // Đóng connection, preparedStatement và resultSet ở đây
        }

        return danhSachHocSinh;
    }
    
    public static boolean KiemTraTonTaiMaHocSinh(String maHocSinh) throws SQLException {
        boolean tonTai = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            DBConnect conn = new DBConnect();
            connection = conn.GetConnect();

            // Đếm số lượng bản ghi của MaDeThi
            String sql = "SELECT COUNT(*) FROM NV001.HOCSINH WHERE MAHS = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maHocSinh);
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

}
