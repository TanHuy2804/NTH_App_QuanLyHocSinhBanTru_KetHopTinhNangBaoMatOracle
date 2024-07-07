/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.KiemSoatDeThi_POJO;
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
public class KiemSoatDeThi_DAO {
    public static ArrayList<KiemSoatDeThi_POJO> LayThongTinDeThi() {
        ArrayList<KiemSoatDeThi_POJO> dsch = new ArrayList<KiemSoatDeThi_POJO>();
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnect.GetConnect();
            callableStatement = connection.prepareCall("{CALL NV001.DeThi_SelectAll(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                String MaDeThi = resultSet.getString("MaDeThi");
                String NgaySoanDe = resultSet.getString("NgaySoanDe");
                String SoLuongCauHoi = resultSet.getString("SoLuongCauHoi");
                String TrangThai = resultSet.getString("TrangThai");
                String MaNV = resultSet.getString("MaNV");
                String MaMH = resultSet.getString("MaMH");
                String TrangThaiTruyCapDe = resultSet.getString("TrangThaiTruyCapDe");

                KiemSoatDeThi_POJO ketQua = new KiemSoatDeThi_POJO(MaDeThi, NgaySoanDe, SoLuongCauHoi, TrangThai, MaNV, MaMH, TrangThaiTruyCapDe);
                dsch.add(ketQua);
            }

            System.out.println("Lay du lieu de thi thanh cong!");
        } catch (Exception e) {
            System.out.println("Lay du lieu de thi that bai!");
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
}
