/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;

public class DAO_TaoNguoiDung {     
    public static int taoNguoiDung(String id, String pass) {
        try {
            
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            CallableStatement callableStatement = con.prepareCall("{ call NV001.create_user_proc(?, ?) }");
            callableStatement.setString(1, id);
            callableStatement.setString(2, pass);
            callableStatement.execute();

            // Đóng kết nối
            callableStatement.close();
            return 1;           
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }       
    }      
}
