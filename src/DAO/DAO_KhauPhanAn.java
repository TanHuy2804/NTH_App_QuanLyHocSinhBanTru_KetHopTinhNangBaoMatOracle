/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

//import POJO.*;
import POJO.*;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import javax.swing.JTextField;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author ACER
 */
public class DAO_KhauPhanAn {

    //------------------------------------------//
    // LOAD LÊN TABLE                           //
    //------------------------------------------//
    // USER: Quản lí
    // Load bảng combo món ăn
    public static ArrayList<POJO_Combomonan> getDSComBoMonAN() {
        ArrayList<POJO_Combomonan> dscb = new ArrayList<POJO_Combomonan>();
        try {

            String sql = "select * from NV001.ComBoMonAn";

            DBConnect conn = new DBConnect();
            conn.GetConnect();
            ResultSet rs = conn.executeQuery(sql);

            while (rs.next()) {
                POJO_Combomonan cb = new POJO_Combomonan(rs.getString("MaComBoMon").trim(), rs.getString("TenMon1").trim(),
                        rs.getString("TenMon2").trim(), rs.getString("TenMon3").trim(),
                        rs.getString("TenMon4").trim(), rs.getString("LoaiComBoMon").trim(),
                        rs.getInt("ThuTrongTuan"), rs.getFloat("GiaTien"), rs.getString("QRCode").trim());
                dscb.add(cb);
                Connection connection = conn.GetConnect();
            }

        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu...");
        }
        return dscb;
    }

    // Load bảng combo món ăn hiển thị
    public static ArrayList<POJO_Combomonan> getDSComBoMonAnHienThi() {
        ArrayList<POJO_Combomonan> dscb = new ArrayList<POJO_Combomonan>();
        try {

            String sql = "SELECT * from NV001.ComBoMonAnHienThi";
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            ResultSet rs = conn.executeQuery(sql);

            while (rs.next()) {
                POJO_Combomonan cb = new POJO_Combomonan(rs.getString("MaComBoMon").trim(), rs.getString("TenMon1").trim(),
                        rs.getString("TenMon2").trim(), rs.getString("TenMon3").trim(), rs.getString("TenMon4").trim(),
                        rs.getString("LoaiComBoMon").trim(), rs.getInt("ThuTrongTuan"), rs.getFloat("GiaTien"),
                        rs.getString("QRCode").trim());
                dscb.add(cb);
                Connection connection = conn.GetConnect();
            }

        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu...");
        }
        return dscb;
    }

    // USER: Học sinh
    // Load bảng đăng kí
    public static ArrayList<POJO_DangKy_KPA> getBangDangKyMonAn() {
        ArrayList<POJO_DangKy_KPA> ds = new ArrayList<POJO_DangKy_KPA>();
        try {

            String sql = "select * from NV001.dangkykhauphanan";

            DBConnect conn = new DBConnect();
            conn.Connect();
            ResultSet rs = conn.executeQuery(sql);

            while (rs.next()) {
                POJO_DangKy_KPA dk = new POJO_DangKy_KPA(rs.getString("MaHS"), rs.getString("NgayDangKyKP"),
                        rs.getInt("ThuTrongTuan"), rs.getFloat("Gia"), rs.getString("MaComBoMon"),
                        rs.getString("QRCode").trim());
                ds.add(dk);
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu...");
        }
        return ds;
    }

    // Load dữ liệu bảng combo món ăn hiển thị chay
    public static ArrayList<POJO_Combomonan> getDSComBoMonAnHienThi_Chay() {
        ArrayList<POJO_Combomonan> dscb = new ArrayList<POJO_Combomonan>();
        try {

            String sql = "SELECT * from NV001.ComBoMonAnHienThi where LoaiComBoMon = 'Chay' ";

            DBConnect conn = new DBConnect();
            conn.Connect();
            ResultSet rs = conn.executeQuery(sql);

            while (rs.next()) {
                POJO_Combomonan cb = new POJO_Combomonan(rs.getString("MaComBoMon").trim(), rs.getString("TenMon1").trim(),
                        rs.getString("TenMon2").trim(), rs.getString("TenMon3").trim(),
                        rs.getString("TenMon4").trim(), rs.getString("LoaiComBoMon").trim(),
                        rs.getInt("ThuTrongTuan"), rs.getFloat("GiaTien"), rs.getString("QRCode").trim());
                dscb.add(cb);
            }

        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu...");
        }
        return dscb;
    }

    // Load dữ liệu bảng combo món ăn hiển thị mặn
    public static ArrayList<POJO_Combomonan> getDSComBoMonAnHienThi_Man() {
        ArrayList<POJO_Combomonan> dscb = new ArrayList<POJO_Combomonan>();
        try {

            String sql = "SELECT * from NV001.ComBoMonAnHienThi where LoaiComBoMon = 'Mặn' ";

            DBConnect conn = new DBConnect();
            conn.Connect();
            ResultSet rs = conn.executeQuery(sql);

            while (rs.next()) {
                POJO_Combomonan cb = new POJO_Combomonan(rs.getString("MaComBoMon").trim(),
                        rs.getString("TenMon1").trim(), rs.getString("TenMon2").trim(),
                        rs.getString("TenMon3").trim(), rs.getString("TenMon4").trim(),
                        rs.getString("LoaiComBoMon").trim(), rs.getInt("ThuTrongTuan"),
                        rs.getFloat("GiaTien"), rs.getString("QRCode").trim());
                dscb.add(cb);
            }

        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu...");
        }
        return dscb;
    }

    //-----------------------------------------------------------------------------------------------------
    //------------------------------------------//
    // QUẢN LÍ KHẨU PHẦN ĂN                     //
    //------------------------------------------//
    // xóa dữ liệu bảng combo món ăn -DA XU LI
    public static int xoaDuLieu_ComBoMonAn(String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.XoaThongTinComBoMonAn(?)}");
            callableStatement.setString(1, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    // thêm dữ liệu bảng combo món ăn -DA XU LY
    public static int themDuLieu_ComBoMonAn(String mon1, String mon2, String mon3, String mon4, String loai, int thu, float gia, String QR, String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.ThemDuLieuVaoComBoMonAn (?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            callableStatement.setString(1, mon1);
            callableStatement.setString(2, mon2);
            callableStatement.setString(3, mon3);
            callableStatement.setString(4, mon4);
            callableStatement.setString(5, loai);
            callableStatement.setInt(6, thu);
            callableStatement.setFloat(7, gia);
            callableStatement.setString(8, QR);
            callableStatement.setString(9, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    // update dữ liệu bảng combo món ăn -DA XU LY
    public static int upDateDuLieu_ComBoMonAn(String ma, String mon1, String mon2, String mon3, String mon4, String loai, int thu, float gia, String QR, String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.UpdateComBoMonAn (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            callableStatement.setString(1, ma);
            callableStatement.setString(2, mon1);
            callableStatement.setString(3, mon2);
            callableStatement.setString(4, mon3);
            callableStatement.setString(5, mon4);
            callableStatement.setString(6, loai);
            callableStatement.setInt(7, thu);
            callableStatement.setFloat(8, gia);
            callableStatement.setString(9, QR);
            callableStatement.setString(10, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    // thêm dữ liệu bảng combo món ăn hiển thị -DA XU LY
    public static int themDuLieu_ComBoMonAnHienThi(String macb, String mon1, String mon2, String mon3, String mon4, String loai, int thu, float gia, String QR, String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.ThemDuLieuVaoComBoMonAnHienThi (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            callableStatement.setString(1, macb);
            callableStatement.setString(2, mon1);
            callableStatement.setString(3, mon2);
            callableStatement.setString(4, mon3);
            callableStatement.setString(5, mon4);
            callableStatement.setString(6, loai);
            callableStatement.setInt(7, thu);
            callableStatement.setFloat(8, gia);
            callableStatement.setString(9, QR);
            callableStatement.setString(10, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    //xoa tất cả bảng combo món ăn hiển thị -DA XU LY
    public static int xoaDuLieu_ComBoMonAnHienThi(String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.XoaThongTinComBoMonAnHienThi(?)}");
            callableStatement.setString(1, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    // xóa 1 khẩu phẩn của bảng combo món ăn XoaMotKP_ComBoMonHienThi --DA XU LY
    public static int xoaMotKP_ComBoMonAnHienThi(String macb, String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.XoaMotKP_ComBoMonHienThi (?, ?)}");
            callableStatement.setString(1, macb);
            callableStatement.setString(2, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    // xóa 1 khẩu phẩn của bảng combo món ăn hiển thị --DA XU LY
    public static int xoaMotKP_ComBoMonAn(String macb, String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.XoaMotKP_ComBoMon (?,?)}");
            callableStatement.setString(1, macb);
            callableStatement.setString(2, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    //cập nhật bảng combo món ăn hiển thị -DA XU LY
    public static int upDateDuLieu_ComBoMonAnHienThi(String ma, String mon1, String mon2, String mon3, String mon4, String loai,
            int thu, float gia, String QR, String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.UpdateComBoMonAnHienThi (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
            callableStatement.setString(1, ma);
            callableStatement.setString(2, mon1);
            callableStatement.setString(3, mon2);
            callableStatement.setString(4, mon3);
            callableStatement.setString(5, mon4);
            callableStatement.setString(6, loai);
            callableStatement.setInt(7, thu);
            callableStatement.setFloat(8, gia);
            callableStatement.setString(9, QR);
            callableStatement.setString(10, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    //-----------------------------------------------------------------------------------------------------
    //------------------------------------------//
    // ĐĂNG KÍ KHẨU PHẦN ĂN                     //
    //------------------------------------------//
    // đăng kí khẩu phần ăn --DA XU LY
    public static int dangKi_KPA(String maHS, String maCB, String ngayDK, int thu, float gia, String QR, String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.InsertDangKyKhauPhanAn (?, ?, ?, ?, ?, ?, ?) }");
            callableStatement.setString(1, maHS);
            callableStatement.setString(2, ngayDK);
            callableStatement.setInt(3, thu);
            callableStatement.setFloat(4, gia);
            callableStatement.setString(5, maCB);
            callableStatement.setString(6, QR);
            callableStatement.setString(7, manv);
            
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    //kiểm tra user đang thực thi 
    public static String kiemTraUser() {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.GetUserName (?) }");
            callableStatement.registerOutParameter(1, Types.VARCHAR);

            callableStatement.execute();

            String username = callableStatement.getString(1);
            // Đóng kết nối
            callableStatement.close();
            return username;
        } catch (Exception e) {
            //e.printStackTrace();
            return "Lỗi";
        }
    }

    //xóa thông tin đăng kí -DA XU LI
    public static int xoaDuLieu_dangki(String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.XoaThongTinDangKi(?)");
            callableStatement.setString(1, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    //xóa 1 khẩu phần đăng kí -DA XU LI
    public static int xoaDangAKi_MotKhauPhan(String ma, String ngay, int thu, String manv) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.XoaDangKy_MotKhauPhanAn (?, ?, ?, ?)");
            callableStatement.setString(1, ma);
            callableStatement.setString(2, ngay);
            callableStatement.setInt(3, thu);
            callableStatement.setString(4, manv);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }
    public static ArrayList<POJO_DangKy_KPA> getBangDangKyMonAn_TheoMaHS(String mahs) {
        ArrayList<POJO_DangKy_KPA> ds = new ArrayList<POJO_DangKy_KPA>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {

            String sql = "select * from NV001.dangkykhauphanan where MAHS = ?";

            connection = DBConnect.GetConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mahs);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                POJO_DangKy_KPA dk = new POJO_DangKy_KPA(rs.getString("MaHS"), rs.getString("NgayDangKyKP"),
                        rs.getInt("ThuTrongTuan"), rs.getFloat("Gia"), rs.getString("MaComBoMon"),
                        rs.getString("QRCode").trim());
                ds.add(dk);
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu...");
        }
        return ds;
    }
    public static ArrayList<POJO_DangKy_KPA> getBangDangKyMonAn_TheoNgay(String ngay) {
        ArrayList<POJO_DangKy_KPA> ds = new ArrayList<POJO_DangKy_KPA>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {

            String sql = "select * from NV001.dangkykhauphanan where NGAYDANGKYKP = ?";

            connection = DBConnect.GetConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ngay);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                POJO_DangKy_KPA dk = new POJO_DangKy_KPA(rs.getString("MaHS"), rs.getString("NgayDangKyKP"),
                        rs.getInt("ThuTrongTuan"), rs.getFloat("Gia"), rs.getString("MaComBoMon"),
                        rs.getString("QRCode").trim());
                ds.add(dk);
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu...");
        }
        return ds;
    }
}
