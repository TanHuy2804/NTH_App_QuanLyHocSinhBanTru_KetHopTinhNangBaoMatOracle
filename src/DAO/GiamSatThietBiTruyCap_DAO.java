/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import POJO.GiamSatThietBiTruyCap_POJO;
import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author Admin
 */
public class GiamSatThietBiTruyCap_DAO {
    public static ArrayList<GiamSatThietBiTruyCap_POJO> getDSThietBiGiamSat() {
        ArrayList<GiamSatThietBiTruyCap_POJO> dstbgs = new ArrayList<>();
        try {
            String sql = "SELECT SCHEMANAME, MACHINE, PROGRAM, TYPE, MODULE, SYS_CONTEXT('USERENV', 'IP_ADDRESS') AS IP_ADDRESS, LOGON_TIME FROM v$session WHERE TYPE = 'USER'";
            DBConnect cn = new DBConnect();
            Connection connection = cn.GetConnect();
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                String SCHEMANAME = rs.getString("SCHEMANAME").trim();
                String MACHINE = rs.getString("MACHINE").trim();
                String PROGRAM = rs.getString("PROGRAM").trim();
                String TYPE = rs.getString("TYPE").trim();
                String MODULE = rs.getString("MODULE").trim();
                String IP_ADDRESS = rs.getString("IP_ADDRESS").trim();
                Timestamp LOGON_TIME = rs.getTimestamp("LOGON_TIME");
                GiamSatThietBiTruyCap_POJO gstb = new GiamSatThietBiTruyCap_POJO(SCHEMANAME, MACHINE,PROGRAM,TYPE,MODULE, IP_ADDRESS, LOGON_TIME);
                         dstbgs.add(gstb);
                     }
                 } catch (SQLException e) {
                     System.err.println("Không thể lấy dữ liệu " + e.getMessage());
                 }
                 return dstbgs;
             }
}
