/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author SONDAY
 */
import DAO.*;
import POJO.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CauHoi_DAO {
    //LayThongTinCauHoiTheoMaDeThi VÀ LayThongTin_CauHoi
    public static ArrayList<CauHoi_POJO> LayThongTinCauHoiTheoMaDeThi(String maDe) {
        ArrayList<CauHoi_POJO> dsch = new ArrayList<>();
        try {
            String sql = "SELECT * FROM NV001.CauHoi WHERE MaMH IN (SELECT MaMH FROM NV001.DeThi WHERE MaDeThi = '" + maDe + "')";
            DBConnect conn = new DBConnect();
            conn.GetConnect();           
            ResultSet rs = conn.executeQuery(sql);     
            while (rs.next()) {  
                String maCH = rs.getString("MaCauHoi");
                String cauHoi = rs.getString("CauHoi");
                String dapAn_A = rs.getString("DapAn_A");
                String dapAn_B = rs.getString("DapAn_B");
                String dapAn_C = rs.getString("DapAn_C");
                String dapAn_D = rs.getString("DapAn_D"); 
                String dapAnDung = rs.getString("DapAnDung");
                String maMH = rs.getString("MaMH");

                CauHoi_POJO cn = new CauHoi_POJO(maCH, cauHoi, dapAn_A, dapAn_B, dapAn_C, dapAn_D, dapAnDung, maMH);
                dsch.add(cn);
                System.out.println("Lay du lieu cau hoi theo ma de thanh cong!");
            }             
        } catch (Exception e) {
            System.out.println("Lay du lieu cau hoi that bai!");
            //e.printStackTrace();
        }
        return dsch;
    }
    public static ArrayList<CauHoi_POJO> LayThongTin_CauHoi() {
        ArrayList<CauHoi_POJO> ds_cauhoi = new ArrayList<CauHoi_POJO>();
        try {
            String sql = "SELECT * FROM NV001.CAUHOI";
            DBConnect conn = new DBConnect();
            conn.GetConnect();

            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
                String macauhoii = rs.getString("MaCauHoi");
                String cauhoii = rs.getString("CauHoi");
                String dapanaa = rs.getString("DapAn_A");
                String dapanab = rs.getString("DapAn_B");
                String dapanac = rs.getString("DapAn_C");
                String dapanad = rs.getString("DapAn_D");
                String dapandungg = rs.getString("DapAnDung");
                String mamhh = rs.getString("MaMH");

                Connection connection = conn.GetConnect(); // Lấy đối tượng Connection

                CauHoi_POJO cauhoi = new CauHoi_POJO(macauhoii, cauhoii, dapanaa, dapanab, dapanac, dapanad, dapandungg, mamhh);
                ds_cauhoi.add(cauhoi);
                System.out.println("Lay du lieu cau hoi thanh cong!");
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return ds_cauhoi;
    }

    public static ArrayList<String> get_CauHoiList_CauHoi() {
        ArrayList<String> maCauHoiList_CauHoi = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT MaCauHoi FROM NV001.CAUHOI";
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            ResultSet rs = conn.executeQuery(sql);
            while (rs.next()) {
                String maCH = rs.getString("MaCauHoi");
                if (maCH != null) {
                    maCauHoiList_CauHoi.add(maCH.trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu mã câu hỏi" + e.getMessage());
        }
        return maCauHoiList_CauHoi;
    }

    public static boolean KiemTraTonTaiMaCauHoi(String maCauHoi) throws SQLException {
        boolean tonTai = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            DBConnect conn = new DBConnect();
            connection = conn.GetConnect();

            // Đếm số lượng bản ghi của MaCauHoi
            String sql = "SELECT COUNT(*) FROM NV001.CAUHOI WHERE MACAUHOI = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maCauHoi);
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

    public static ArrayList<CauHoi_POJO> LayThongTin_CauHoi_Theo_MaDeThi(String madethi) {
        ArrayList<CauHoi_POJO> danhSachCauHoi = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnect.GetConnect();
            String query = "SELECT ch.* FROM NV001.CauHoi ch INNER JOIN NV001.ChiTietDeThi ctdt ON ch.MaCauHoi = ctdt.MaCauHoi WHERE ctdt.MaDeThi = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, madethi);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Đọc thông tin từ ResultSet và thêm vào danh sách
                CauHoi_POJO cauHoi = new CauHoi_POJO();
                cauHoi.setMACAUHOI(resultSet.getString("MaCauHoi"));
                cauHoi.setCAUHOI(resultSet.getString("CauHoi"));
                cauHoi.setDAPAN_A(resultSet.getString("DapAn_A"));
                cauHoi.setDAPAN_B(resultSet.getString("DapAn_B"));
                cauHoi.setDAPAN_C(resultSet.getString("DapAn_C"));
                cauHoi.setDAPAN_D(resultSet.getString("DapAn_D"));
                cauHoi.setDAPANDUNG(resultSet.getString("DapAnDung"));
                cauHoi.setMAMH(resultSet.getString("MaMH"));

                danhSachCauHoi.add(cauHoi);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            // Xử lý nếu có lỗi
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Đóng connection, preparedStatement và resultSet ở đây
        }

        return danhSachCauHoi;
    }

    public static String get_MaCH_CuoiCung() {
        String lastMaCH = null;
        try {
            /*
                - WHERE ROWNUM: Lựa chọn hàng đầu tiên từ kết quả của truy vấn con
                - Hàng đầu tiên sẽ là mã câu hỏi của câu hỏi cuối cùng vì chúng đã được sắp xếp theo thứ tự giảm dần.
            */
            String sql = "SELECT MaCauHoi FROM (SELECT MaCauHoi FROM NV001.CAUHOI ORDER BY MaCauHoi DESC) WHERE ROWNUM = 1";
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            ResultSet rs = conn.executeQuery(sql);
            if (rs.next()) {
                lastMaCH = rs.getString("MaCauHoi");
                if (lastMaCH != null) {
                    lastMaCH = lastMaCH.trim();
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể lấy dữ liệu mã câu hỏi cuối cùng: " + e.getMessage());
        }
        return lastMaCH;
    }

    public static int get_SoLuongCauHoi_Theo_MaDeThi(String maDeThi) {
        int soLuong = 0;
        Connection connection = null;
        try {
            // Thiết lập kết nối
            connection = DBConnect.conn; // Sử dụng kết nối đã được thiết lập

            // Viết câu truy vấn SQL để đếm số lượng câu hỏi của mã đề
            String sql = "SELECT COUNT(*) AS so_luong FROM NV001.CHITIETDETHI WHERE MaDeThi = ?";

            // Tạo PreparedStatement
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maDeThi);

            // Thực thi câu truy vấn
            ResultSet rs = ps.executeQuery();

            // Lấy kết quả trả về
            if (rs.next()) {
                soLuong = rs.getInt("so_luong");
            }

//            // Đóng kết nối và giải phóng tài nguyên
//            rs.close();
//            ps.close();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return soLuong;
    }

}
