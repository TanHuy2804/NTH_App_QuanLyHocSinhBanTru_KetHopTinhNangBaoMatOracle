/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.DBA_AUDIT_TRAIL;
import POJO.DBA_OBJ_AUDIT_OPTS_POJO;
import POJO.TaiKhoan_POJO;
import POJO.dba_stmt_audit_opts_POJO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.ResultSet;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Admin
 */
public class GiamSatSA_DAO {
    public static ArrayList<DBA_AUDIT_TRAIL> LayThongTinGiamSat() {
        ArrayList<DBA_AUDIT_TRAIL> dat = new ArrayList<DBA_AUDIT_TRAIL>();
        try {          
            String sql = "SELECT username, timestamp, obj_name, action_name FROM DBA_AUDIT_TRAIL WHERE obj_name IN ( SELECT object_name FROM all_objects WHERE owner = 'NV001')";
            DBConnect conn = new DBConnect();
            conn.GetConnect();           
            ResultSet rs = conn.executeQuery(sql);           
            while (rs.next()) {  
            String username = rs.getString("username");
            Timestamp timestamp = rs.getTimestamp("timestamp");
            String obj_name = rs.getString("obj_name");
            String action_name = rs.getString("action_name");

            DBA_AUDIT_TRAIL ds = new DBA_AUDIT_TRAIL(username, timestamp, obj_name, action_name);
            dat.add(ds);
            System.out.println("Lay du lieu giam sat thanh cong!");
        }             
        } catch (Exception e) {
            //System.err.println("Lay du lieu nhan vien that bai!");
            //e.printStackTrace();
        }
        return dat;
    }
    public static ArrayList<TaiKhoan_POJO> UserName_TK_SelectAll() {
        ArrayList<TaiKhoan_POJO> dstb = new ArrayList<TaiKhoan_POJO>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.GetConnect();
            callableStatement = connection.prepareCall("{CALL NV001.UserName_TK_SelectAll(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                String maUser = resultSet.getString("UserName_TK");

                TaiKhoan_POJO table = new TaiKhoan_POJO(maUser);
                dstb.add(table);
            }

            System.out.println("Lay du lieu UserName_TK thanh cong!");
        } catch (Exception e) {
            System.out.println("Lay du lieu UserName_TK that bai!");
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
        return dstb;
    }
     public static ArrayList<dba_stmt_audit_opts_POJO> LayThongTin_dba_stmt_audit_opts() {
        ArrayList<dba_stmt_audit_opts_POJO> dsch = new ArrayList<dba_stmt_audit_opts_POJO>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.GetConnect();
            callableStatement = connection.prepareCall("{CALL NV001.dba_stmt_audit_opts_SelectAll(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                String user_name = resultSet.getString("user_name");
                String audit_option = resultSet.getString("audit_option");
                String success = resultSet.getString("success");
                String failure = resultSet.getString("failure");

                dba_stmt_audit_opts_POJO DL = new dba_stmt_audit_opts_POJO(user_name, audit_option, success, failure);
                dsch.add(DL);
            }

            System.out.println("Lay du lieu dba_stmt_audit_opts thanh cong!");
        } catch (Exception e) {
            System.out.println("Lay du lieu dba_stmt_audit_opts that bai!");
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
        return dsch;
    }
     public static ArrayList<DBA_OBJ_AUDIT_OPTS_POJO> LayThongTin_DBA_OBJ_AUDIT_OPTS() {
        ArrayList<DBA_OBJ_AUDIT_OPTS_POJO> dsTB = new ArrayList<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnect.GetConnect();
            callableStatement = connection.prepareCall("{call SYS.DBA_OBJ_AUDIT_OPTS_SelectAll(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();

            // Lấy kết quả trả về từ stored procedure
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                String owner = resultSet.getString("OWNER");
                String objectName = resultSet.getString("OBJECT_NAME");
                String objectType = resultSet.getString("OBJECT_TYPE");
                String del = resultSet.getString("DEL");
                String ins = resultSet.getString("INS");
                String sel = resultSet.getString("SEL");
                String upd = resultSet.getString("UPD");

                DBA_OBJ_AUDIT_OPTS_POJO pojo = new DBA_OBJ_AUDIT_OPTS_POJO(owner, objectName, objectType, del, ins, sel, upd);
                dsTB.add(pojo);
            }

            System.out.println("Lấy dữ liệu DBA_OBJ_AUDIT_OPTS thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy dữ liệu DBA_OBJ_AUDIT_OPTS: " + e.getMessage());
        } finally {
            // Đóng các kết nối và giải phóng tài nguyên
            try {
                if (resultSet != null) resultSet.close();
                if (callableStatement != null) callableStatement.close();
            } catch (Exception e) {
                System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
        return dsTB;
    }
}
