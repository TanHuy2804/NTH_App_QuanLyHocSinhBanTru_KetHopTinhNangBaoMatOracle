/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 *
 * @author SONDAY
 */
public class DAO_DiemDanhKhuonMat {

    public static String LayTenHocSinh(String ma_hocsinh) {
        String v_TenHS = "";
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ ? = call v2_LayThongTinMaHocSinh(?, ?)}");
            // Đặt giá trị cho tham số đầu vào
            callableStatement.setString(1, ma_hocsinh);

            // Đăng ký các tham số đầu ra
            callableStatement.registerOutParameter(2, Types.VARCHAR); // v_TenHS

            // Thực thi stored procedure
            callableStatement.execute();

            // Lấy giá trị trả về từ các tham số đầu ra
            v_TenHS = callableStatement.getString(2);

            callableStatement.close();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        return v_TenHS;
    }

    public static String LayMaLopHocSinh(String ma_hocsinh) {
        String v_MaLop = "";
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ ? = call v2_LayThongTinMaLop(?, ?)}");
            // Đặt giá trị cho tham số đầu vào
            callableStatement.setString(1, ma_hocsinh);

            // Đăng ký các tham số đầu ra
            callableStatement.registerOutParameter(2, Types.VARCHAR); // v_TenHS

            // Thực thi stored procedure
            callableStatement.execute();

            // Lấy giá trị trả về từ các tham số đầu ra
            v_MaLop = callableStatement.getString(2);

            callableStatement.close();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        return v_MaLop;
    }

}
