/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import oracle.jdbc.OracleTypes;
/**
 *
 * @author ACER
 */
public class DAO_XacThuc {
    
    public static String giaiMaPass(String encryptedText) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ ? = call NV001.DES_DECRYPT(?)}");
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(2, encryptedText);
            callableStatement.execute();

            // Lấy giá trị giải mã từ kết quả trả về
            String decryptedText = callableStatement.getString(1);
            // Đóng kết nối
            callableStatement.close();
            return decryptedText;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }   
    
    public static String LayThongTinPass(String str) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement cst = con.prepareCall("{ ? = call NV001.LayPassWord (?) }");
            cst.registerOutParameter(1, Types.VARCHAR);
            cst.setString(2, str);
            cst.execute();

            // Lấy giá trị giải mã từ kết quả trả về
            String kq = cst.getString(1);
            // Đóng kết nối
            cst.close();
            return kq;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}
