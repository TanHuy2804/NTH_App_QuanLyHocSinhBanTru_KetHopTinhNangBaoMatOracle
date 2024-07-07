/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import POJO.DBA_PROFILES;
import POJO.DBA_ROLE_PRIVS;
import java.sql.*;
import java.util.ArrayList;
import POJO.NhanVien_POJO;
import POJO.DBA_SYS_PRIVS;
import POJO.DBA_USERS_POJO;
import POJO.GRANTED_ROLE_POJO;
import POJO.NguoiDung_POJO;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

import oracle.jdbc.OracleTypes;

/**
 *
 * @author Admin
 */
public class NhanVien_DAO {
      public static ArrayList<NhanVien_POJO> LayThongTinNhanVien() {
        ArrayList<NhanVien_POJO> dsnv = new ArrayList<NhanVien_POJO>();

        try {
            String sql = "SELECT * FROM NV001.NHANVIEN";
            DBConnect conn = new DBConnect();
            conn.GetConnect();           
            ResultSet rs = conn.executeQuery(sql);           
            while (rs.next()) {  
                String maNV = rs.getString("MaNV");
                String tenNV = rs.getString("TenNV");
                String gioiTinh = rs.getString("GioiTinh");
                Date ngaySinh = rs.getDate("NgaySinh");
                String chucvu = rs.getString("ChucVu");
                String diaChi = rs.getString("DiaChi");
                String email = rs.getString("Email"); 
                String sdt = rs.getString("SoDT");
                String trinhdo = rs.getString("TrinhDo");
                String chuyenmon = rs.getString("ChuyenMon");
                String noidaotao = rs.getString("NoiDaoTao");
                int namtotnghiep = rs.getInt("NamTotNghiep");
                byte[] hinhanh = rs.getBytes("HinhAnh");

                NhanVien_POJO nv = new NhanVien_POJO(maNV, tenNV, gioiTinh, ngaySinh.toString(), chucvu, diaChi, email, sdt, trinhdo, chuyenmon, noidaotao, String.valueOf(namtotnghiep), Base64.getEncoder().encodeToString(hinhanh));
                dsnv.add(nv);
                System.out.println("Lay du lieu nhan vien thanh cong!");
            }             
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return dsnv;
    }
      
     public static ArrayList<NhanVien_POJO> TimKiemNhanVien(String manv) {
        ArrayList<NhanVien_POJO> dsnv = new ArrayList<NhanVien_POJO>();

        try {
            String sql = "SELECT * FROM NV001.NHANVIEN WHERE UPPER(MaNV) = UPPER('" + manv + "')";

            DBConnect conn = new DBConnect();
            conn.GetConnect();           
            ResultSet rs = conn.executeQuery(sql);           
            while (rs.next()) {  
                String maNV = rs.getString("MaNV");
                String tenNV = rs.getString("TenNV");
                String gioiTinh = rs.getString("GioiTinh");
                Date ngaySinh = rs.getDate("NgaySinh");
                String chucvu = rs.getString("ChucVu");
                String diaChi = rs.getString("DiaChi");
                String email = rs.getString("Email"); 
                String sdt = rs.getString("SoDT");
                String trinhdo = rs.getString("TrinhDo");
                String chuyenmon = rs.getString("ChuyenMon");
                String noidaotao = rs.getString("NoiDaoTao");
                int namtotnghiep = rs.getInt("NamTotNghiep");
                byte[] hinhanh = rs.getBytes("HinhAnh");

                NhanVien_POJO nv = new NhanVien_POJO(maNV, tenNV, gioiTinh, ngaySinh.toString(), chucvu, diaChi, email, sdt, trinhdo, chuyenmon, noidaotao, String.valueOf(namtotnghiep), Base64.getEncoder().encodeToString(hinhanh));
                dsnv.add(nv);
                System.out.println("Lay du lieu nhan vien thanh cong!");
            }             
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return dsnv;
    }

    
//    public static ArrayList<DBA_SYS_PRIVS> LayThongTinNhanVien2() {
//        ArrayList<DBA_SYS_PRIVS> dsp = new ArrayList<DBA_SYS_PRIVS>();
//        try {          
//            String sql = "SELECT GRANTEE, OWNER, TABLE_NAME, GRANTOR, PRIVILEGE FROM USER_TAB_PRIVS WHERE GRANTEE != 'SYS' AND PRIVILEGE IN ('SELECT', 'UPDATE', 'DELETE', 'INSERT')";
//            DBConnect conn = new DBConnect();
//            conn.GetConnect();           
//            ResultSet rs = conn.executeQuery(sql);           
//            while (rs.next()) {  
//            String GRANTEE = rs.getString("GRANTEE");
//            String OWNER = rs.getString("OWNER");
//            String TABLE_NAME = rs.getString("TABLE_NAME");
//            String GRANTOR = rs.getString("GRANTOR");
//            String PRIVILEGE = rs.getString("PRIVILEGE");
//
//            DBA_SYS_PRIVS ds = new DBA_SYS_PRIVS(GRANTEE, OWNER, TABLE_NAME, GRANTOR, PRIVILEGE);
//            dsp.add(ds);
//            System.out.println("Lay du lieu nhan vien thanh cong!");
//        }             
//        } catch (Exception e) {
//            //System.err.println("Lay du lieu nhan vien that bai!");
//            //e.printStackTrace();
//        }
//        return dsp;
//    }
    
    public static ArrayList<DBA_SYS_PRIVS> LayThongTinNhanVien2() {
        ArrayList<DBA_SYS_PRIVS> dsp = new ArrayList<DBA_SYS_PRIVS>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            DBConnect dbConnect = new DBConnect();
            conn = dbConnect.GetConnect();

            String sql = "{CALL NV001.USER_TAB_PRIVS_SelectAll(?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);

            // Thực thi thủ tục
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(1);

            // Lặp qua các kết quả trả về từ con trỏ
            while (rs.next()) {
                String GRANTEE = rs.getString("GRANTEE");
                String OWNER = rs.getString("OWNER");
                String TABLE_NAME = rs.getString("TABLE_NAME");
                String GRANTOR = rs.getString("GRANTOR");
                String PRIVILEGE = rs.getString("PRIVILEGE");

                DBA_SYS_PRIVS ds = new DBA_SYS_PRIVS(GRANTEE, OWNER, TABLE_NAME, GRANTOR, PRIVILEGE);
                dsp.add(ds);
                System.out.println("Lay du lieu USER_TAB_PRIVS thanh cong!");
            }
        } catch (Exception e) {
            System.err.println("Lay du lieu USER_TAB_PRIVS that bai!");
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dsp;
    }

    public static ArrayList<DBA_ROLE_PRIVS> LayThongTinQuyen() {
        ArrayList<DBA_ROLE_PRIVS> dsp = new ArrayList<DBA_ROLE_PRIVS>();
        try {          
            String sql = "SELECT * FROM DBA_ROLE_PRIVS WHERE (GRANTEE LIKE 'NV%' OR GRANTEE LIKE 'HS%') AND GRANTEE != 'HS_ADMIN_ROLE'";
            DBConnect conn = new DBConnect();
            conn.GetConnect();           
            ResultSet rs = conn.executeQuery(sql);           
            while (rs.next()) {  
            String GRANTEE = rs.getString("GRANTEE");
            String GRANTED_ROLE = rs.getString("GRANTED_ROLE");
            String ADMIN_OPTION = rs.getString("ADMIN_OPTION");
            String DELEGATE_OPTION = rs.getString("DELEGATE_OPTION");
            String DEFAULT_ROLE = rs.getString("DEFAULT_ROLE");
            String COMMON = rs.getString("COMMON");

            DBA_ROLE_PRIVS ds = new DBA_ROLE_PRIVS(GRANTEE, GRANTED_ROLE, ADMIN_OPTION, DELEGATE_OPTION, DEFAULT_ROLE,COMMON);
            dsp.add(ds);
            System.out.println("Lay du lieu DBA_ROLE_PRIVS thanh cong!");
        }             
        } catch (Exception e) {
            //System.err.println("Lay du lieu nhan vien that bai!");
            //e.printStackTrace();
        }
        return dsp;
    }
    public static int ThemXoaSuaNhanVien(String sql) {
        int i = 0;
        try {

            DBConnect conn = new DBConnect();
            conn.GetConnect();
            i = conn.executeUpdate(sql);
//            conn.close();
            System.out.println("Chuc nang thuc hien thanh cong");
        } catch (Exception e) {
            System.out.println("Chuc nang thuc hien that bai");
        }
        return i;
    }
        // Giải mã dữ liệu bằng khóa riêng tư
    public static String decrypt_RSA(String encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
    // Phân tích khóa riêng tư từ chuỗi Base64
    public static PrivateKey decodePrivateKey_RSA(String privateKeyBase64) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
    
    public static void capQuyen(String action, String tenBang, String manv) {
        String sql = "{CALL NV001.CapQuyenUser('" + tenBang + "','" + manv + "','" + action + "')}";
        if (ThemXoaSuaNhanVien(sql) == 1) {
            System.out.println("Chuc nang thuc hien thanh cong");
        }
    }
    public static void thuHoiQuyen(String action, String tenBang, String manv) {
        String sql = "{CALL NV001.ThuHoiQuyenUser('" + tenBang + "','" + manv + "','" + action + "')}";
        if (ThemXoaSuaNhanVien(sql) == 1) {
            System.out.println("Chuc nang thuc hien thanh cong");
        }
    }
    public static void CreateProfile(String profileName, int failedLoginAttempts, int sessionsPerUser, int connectTime, int idleTime, int passwordLockTime) {
        String sql = "{CALL NV001.CreateProfile('" + profileName + "','" + failedLoginAttempts + "','" + sessionsPerUser + "','" + connectTime + "','" + idleTime + "','" + passwordLockTime + "')}";
        if (ThemXoaSuaNhanVien(sql) == 1) {
            System.out.println("Chuc nang thuc hien thanh cong");
        }
    }
    public static ArrayList<DBA_PROFILES> LayThongTinProfile() {
        ArrayList<DBA_PROFILES> dsProfile = new ArrayList<DBA_PROFILES>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.GetConnect();
            callableStatement = connection.prepareCall("{CALL NV001.SelectAll_Profile(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                String PROFILE = resultSet.getString("PROFILE");
                String RESOURCE_NAME = resultSet.getString("RESOURCE_NAME");
                String RESOURCE_TYPE = resultSet.getString("RESOURCE_TYPE");
                String LIMIT = resultSet.getString("LIMIT");

                DBA_PROFILES profile = new DBA_PROFILES(PROFILE, RESOURCE_NAME, RESOURCE_TYPE, LIMIT);
                dsProfile.add(profile);
            }

            System.out.println("Lay du lieu profile thanh cong!");
        } catch (Exception e) {
            System.out.println("Lay du lieu profile that bai!");
            //e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
                //if (connection != null) connection.close();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return dsProfile;
    }
    public static ArrayList<DBA_USERS_POJO> LayThongTinUserProfile() {
        ArrayList<DBA_USERS_POJO> dsUserProfile = new ArrayList<DBA_USERS_POJO>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.GetConnect();
            callableStatement = connection.prepareCall("{CALL NV001.SelectAll_User_Profile(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                String USERNAME = resultSet.getString("USERNAME");
                String PROFILE = resultSet.getString("PROFILE");

                DBA_USERS_POJO userprofile = new DBA_USERS_POJO(USERNAME, PROFILE);
                dsUserProfile.add(userprofile);
            }

            System.out.println("Lay du lieu userprofile thanh cong!");
        } catch (Exception e) {
            System.out.println("Lay du lieu userprofile that bai!");
            //e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
                //if (connection != null) connection.close();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return dsUserProfile;
    }
    public static ArrayList<DBA_PROFILES> LayThongTinWhereProfile(String tenProfile) {
        ArrayList<DBA_PROFILES> dsWhereProfile = new ArrayList<DBA_PROFILES>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.GetConnect();
            callableStatement = connection.prepareCall("{CALL NV001.SelectWhereProfile(?,?)}");
            callableStatement.setString(1, tenProfile);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
            
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(2); 
            while (resultSet.next()) {
                String PROFILE = resultSet.getString("PROFILE");
                String RESOURCE_NAME = resultSet.getString("RESOURCE_NAME");
                String RESOURCE_TYPE = resultSet.getString("RESOURCE_TYPE");
                String LIMIT = resultSet.getString("LIMIT");

                DBA_PROFILES whereprofile = new DBA_PROFILES(PROFILE, RESOURCE_NAME, RESOURCE_TYPE, LIMIT);
                dsWhereProfile.add(whereprofile);
            }

            System.out.println("Lay du lieu where profile thanh cong!");
        } catch (Exception e) {
            System.out.println("Lay du lieu where profile that bai!");
            //e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
                //if (connection != null) connection.close();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return dsWhereProfile;
    }
    
    public static ArrayList<DBA_SYS_PRIVS> LoadDuLieuQuyenNV(String maUser) {
        ArrayList<DBA_SYS_PRIVS> dsp = new ArrayList<DBA_SYS_PRIVS>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            DBConnect dbConnect = new DBConnect();
            conn = dbConnect.GetConnect();

            String sql = "{CALL NV001.USER_TAB_PRIVS_Where_MaUser(?, ?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, maUser);
            cstmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);

            // Thực thi thủ tục
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(2);

            // Lặp qua các kết quả trả về từ con trỏ
            while (rs.next()) {
                String GRANTEE = rs.getString("GRANTEE");
                String OWNER = rs.getString("OWNER");
                String TABLE_NAME = rs.getString("TABLE_NAME");
                String GRANTOR = rs.getString("GRANTOR");
                String PRIVILEGE = rs.getString("PRIVILEGE");

                DBA_SYS_PRIVS ds = new DBA_SYS_PRIVS(GRANTEE, OWNER, TABLE_NAME, GRANTOR, PRIVILEGE);
                dsp.add(ds);
                System.out.println("Lay du lieu nhan vien thanh cong!");
            }
        } catch (Exception e) {
            System.err.println("Lay du lieu nhan vien that bai!");
            //e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return dsp;
    }
    public static ArrayList<GRANTED_ROLE_POJO> LoadDuLieuGRANTED_ROLE() {
        ArrayList<GRANTED_ROLE_POJO> roles = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnect.GetConnect(); // Assuming this method returns a Connection object
            callableStatement = connection.prepareCall("{CALL SYS.ROLE_SelectAll(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                String grantedRole = resultSet.getString("GRANTED_ROLE");

                GRANTED_ROLE_POJO role = new GRANTED_ROLE_POJO(grantedRole);
                roles.add(role);
            }

            System.out.println("Lay du lieu granted roles thanh cong!");
        } catch (SQLException e) {
            System.out.println("Lay du lieu granted roles that bai!");
            //e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
        return roles;
    }

    public static ArrayList<DBA_ROLE_PRIVS> LayThongTinQuyenTheoUser(String user) {
        ArrayList<DBA_ROLE_PRIVS> dsp = new ArrayList<DBA_ROLE_PRIVS>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {          
            String sql = "{CALL SYS.DBA_ROLE_PRIVS_Where_MaUser(?, ?)}";
            DBConnect dbConnect = new DBConnect();
            conn = dbConnect.GetConnect();
            stmt = conn.prepareCall(sql);
            stmt.setString(1, user);
            stmt.registerOutParameter(2, Types.REF_CURSOR);
            stmt.execute();
            rs = (ResultSet) stmt.getObject(2);

            while (rs.next()) {
                String GRANTEE = rs.getString("GRANTEE");
                String GRANTED_ROLE = rs.getString("GRANTED_ROLE");
                String ADMIN_OPTION = rs.getString("ADMIN_OPTION");
                String DELEGATE_OPTION = rs.getString("DELEGATE_OPTION");
                String DEFAULT_ROLE = rs.getString("DEFAULT_ROLE");
                String COMMON = rs.getString("COMMON");

                DBA_ROLE_PRIVS ds = new DBA_ROLE_PRIVS(GRANTEE, GRANTED_ROLE, ADMIN_OPTION, DELEGATE_OPTION, DEFAULT_ROLE, COMMON);
                dsp.add(ds);
            }             
            System.out.println("Lay du lieu DBA_ROLE_PRIVS thanh cong!");
        } catch (Exception e) {
            System.err.println("Lay du lieu DBA_ROLE_PRIVS that bai!");
            //e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
        return dsp;
    }
    
    public static ArrayList<NguoiDung_POJO> LayThongTinNGUOIDUNG() {
        ArrayList<NguoiDung_POJO> user = new ArrayList<NguoiDung_POJO>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            DBConnect dbConnect = new DBConnect();
            conn = dbConnect.GetConnect();

            String sql = "{CALL NV001.ThongtinNguoidung_SelectAll(?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);

            // Thực thi thủ tục
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(1);

            // Lặp qua các kết quả trả về từ con trỏ
            while (rs.next()) {
                String USERNAME = rs.getString("USERNAME");
                String ACCOUNT_STATUS = rs.getString("ACCOUNT_STATUS");
                String CREATED = rs.getString("CREATED");

                NguoiDung_POJO ds = new NguoiDung_POJO(USERNAME, ACCOUNT_STATUS, CREATED);
                user.add(ds);
                System.out.println("Lay du lieu NGUOIDUNG thanh cong!");
            }
        } catch (Exception e) {
            System.err.println("Lay du lieu NGUOIDUNG that bai!");
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
