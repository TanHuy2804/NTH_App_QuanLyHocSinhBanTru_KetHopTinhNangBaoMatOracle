/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.KetQua_POJO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.ResultSet;

import java.sql.CallableStatement;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author TAN HUY
 */
public class KetQua_DAO {
    public static ArrayList<KetQua_POJO> LayThongTinKetQua() {
        ArrayList<KetQua_POJO> dsch = new ArrayList<KetQua_POJO>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.GetConnect();
            callableStatement = connection.prepareCall("{CALL NV001.KetQua_SelectAll(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                String maHS = resultSet.getString("MaHocSinh");
                String maDeThi = resultSet.getString("MaDeThi");
                String soCauDung = resultSet.getString("SoCauDung");
                String diemSo = resultSet.getString("DiemSo");
                Timestamp thoiGianLucNop = resultSet.getTimestamp("ThoiGianLucNop");

                KetQua_POJO ketQua = new KetQua_POJO(maHS, maDeThi, soCauDung, diemSo, thoiGianLucNop);
                dsch.add(ketQua);
            }

            System.out.println("Lay du lieu ket qua thanh cong!");
        } catch (Exception e) {
            System.out.println("Lay du lieu ket qua that bai!");
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
    
    
    public static int ThemXoaSuaKetQua(String sql) {
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

}
