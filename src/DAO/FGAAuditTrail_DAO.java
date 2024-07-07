/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import POJO.ColumnNameTable_POJO;
import POJO.DBA_FGA_AUDIT_TRAIL;
import POJO.DBA_AUDIT_POLICIES;
import POJO.Table_POJO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
/**
 *
 * @author Admin
 */
public class FGAAuditTrail_DAO {
    public static ArrayList<DBA_FGA_AUDIT_TRAIL> LayThongTinGiamSatFGA() {
        ArrayList<DBA_FGA_AUDIT_TRAIL> dfat = new ArrayList<DBA_FGA_AUDIT_TRAIL>();
        try {          
            String sql = "SELECT DB_USER, USERHOST, OBJECT_NAME, SQL_TEXT,POLICY_NAME, TIMESTAMP FROM sys.DBA_FGA_AUDIT_TRAIL";
            DBConnect conn = new DBConnect();
            conn.GetConnect();           
            ResultSet rs = conn.executeQuery(sql);           
            while (rs.next()) {  
            String DB_USER = rs.getString("DB_USER");
            String USERHOST = rs.getString("USERHOST");
            Timestamp TIMESTAMP = rs.getTimestamp("TIMESTAMP");
            String SQL_TEXT = rs.getString("SQL_TEXT");
            String OBJECT_NAME = rs.getString("OBJECT_NAME");
            String POLICY_NAME = rs.getString("POLICY_NAME");

            DBA_FGA_AUDIT_TRAIL ds = new DBA_FGA_AUDIT_TRAIL(DB_USER, USERHOST, OBJECT_NAME, SQL_TEXT,POLICY_NAME, TIMESTAMP);
            dfat.add(ds);
            System.out.println("Lay du lieu nhan vien thanh cong!");
        }             
        } catch (Exception e) {
            //System.err.println("Lay du lieu nhan vien that bai!");
            //e.printStackTrace();
        }
        return dfat;
    }
    
    public static ArrayList<DBA_AUDIT_POLICIES> LayThongTinChinhSachFGA() {
        ArrayList<DBA_AUDIT_POLICIES> dap = new ArrayList<DBA_AUDIT_POLICIES>();
        try {          
            String sql = "SELECT OBJECT_SCHEMA, OBJECT_NAME, POLICY_OWNER, POLICY_NAME, POLICY_TEXT, POLICY_COLUMN, SEL, INS, UPD, DEL FROM SYS.dba_audit_policies";
            DBConnect conn = new DBConnect();
            conn.GetConnect();           
            ResultSet rs = conn.executeQuery(sql);           
            while (rs.next()) {  
            String OBJECT_SCHEMA = rs.getString("OBJECT_SCHEMA");    
            String OBJECT_NAME = rs.getString("OBJECT_NAME");
            String POLICY_OWNER = rs.getString("POLICY_OWNER");
            String POLICY_NAME = rs.getString("POLICY_NAME");
            String POLICY_TEXT = rs.getString("POLICY_TEXT");
            String POLICY_COLUMN = rs.getString("POLICY_COLUMN");
            String SEL = rs.getString("SEL");
            String INS = rs.getString("INS");
            String UPD = rs.getString("UPD");
            String DEL = rs.getString("DEL");

            DBA_AUDIT_POLICIES ds = new DBA_AUDIT_POLICIES(OBJECT_SCHEMA, OBJECT_NAME, POLICY_OWNER, POLICY_NAME, POLICY_TEXT, POLICY_COLUMN, SEL, INS, UPD, DEL);
            dap.add(ds);
            System.out.println("Lay du lieu thanh cong!");
        }             
        } catch (Exception e) {
            //System.err.println("Lay du lieu nhan vien that bai!");
            //e.printStackTrace();
        }
        return dap;
    }
    
//    public static ArrayList<DBA_AUDIT_POLICIES> LayThongTinChinhSachFGA2() {
//        ArrayList<DBA_AUDIT_POLICIES> dap = new ArrayList<DBA_AUDIT_POLICIES>();
//        try {          
//            String sql = "SELECT OBJECT_SCHEMA, OBJECT_NAME, POLICY_OWNER, POLICY_NAME, POLICY_COLUMN FROM SYS.dba_audit_policies where OBJECT_NAME = 'KHACHHANG'";
//            DBConnect conn = new DBConnect();
//            conn.GetConnect();           
//            ResultSet rs = conn.executeQuery(sql);           
//            while (rs.next()) {  
//            String OBJECT_SCHEMA = rs.getString("OBJECT_SCHEMA");    
//            String OBJECT_NAME = rs.getString("OBJECT_NAME");
//            String POLICY_OWNER = rs.getString("POLICY_OWNER");
//            String POLICY_NAME = rs.getString("POLICY_NAME");
//            String POLICY_COLUMN = rs.getString("POLICY_COLUMN");
//
//            DBA_AUDIT_POLICIES ds = new DBA_AUDIT_POLICIES(OBJECT_SCHEMA, OBJECT_NAME, POLICY_OWNER, POLICY_NAME, POLICY_COLUMN);
//            dap.add(ds);
//            System.out.println("Lay du lieu nhan vien thanh cong!");
//        }             
//        } catch (Exception e) {
//            //System.err.println("Lay du lieu nhan vien that bai!");
//            //e.printStackTrace();
//        }
//        return dap;
//    }
    
    public static int ThucHienChucNang(String sql) {
        int i = 0;
        try {

            DBConnect conn = new DBConnect();
            conn.GetConnect();
            i = conn.executeUpdate(sql);
//            conn.close();
            System.out.println("Chức năng thực hiện thành công");
        } catch (Exception e) {
            System.out.println("Chức năng thực hiện thất bại");
        }
        return i;
    }
    public static ArrayList<Table_POJO> LayTable() {
        ArrayList<Table_POJO> dstb = new ArrayList<Table_POJO>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.GetConnect();
            callableStatement = connection.prepareCall("{CALL NV001.Table_SelectAll(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                String tenBang = resultSet.getString("TABLE_NAME");

                Table_POJO table = new Table_POJO(tenBang);
                dstb.add(table);
            }

            System.out.println("Lay du lieu table thanh cong!");
        } catch (Exception e) {
            System.out.println("Lay du lieu table that bai!");
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
    public static ArrayList<ColumnNameTable_POJO> LayCotTheoTable(String tenTable) {
        ArrayList<ColumnNameTable_POJO> dsCot = new ArrayList<>();
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            connection = new DBConnect().GetConnect();
            cstmt = connection.prepareCall("{CALL NV001.Cot_Where_Table(?, ?)}");
            cstmt.setString(1, tenTable);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);

            cstmt.execute();

            rs = (ResultSet) cstmt.getObject(2);
            while (rs.next()) {
                String columnName = rs.getString("Column_Name");

                ColumnNameTable_POJO cot = new ColumnNameTable_POJO(columnName);
                dsCot.add(cot);
            }
        } catch (SQLException e) {
            System.out.println("Lấy dữ liệu cot tu bang thất bại!");
            //e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
                // Không đóng kết nối ở đây để duy trì kết nối
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
        return dsCot;
    }
}
