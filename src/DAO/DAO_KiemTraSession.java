/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;

/**
 *
 * @author TAN HUY
 */
public class DAO_KiemTraSession {
    public static boolean KiemTraSession(String username) {
        try {
            DBConnect conn = new DBConnect();
            Connection con = conn.GetConnect();

            // Tạo callable statement để gọi procedure DemSessionsUser
            CallableStatement callableStatement = con.prepareCall("{call sys.DemSessionsUser(?, ?)}");
            callableStatement.setString(1, username); // Thiết lập tham số p_username
            callableStatement.registerOutParameter(2, java.sql.Types.BOOLEAN); // Thiết lập tham số p_session_exists là OUT
            callableStatement.execute();
            
            // Lấy kết quả từ OUT parameter p_session_exists
            boolean sessionExists = callableStatement.getBoolean(2);

            //callableStatement.close();

            return sessionExists;
        } catch (Exception e) {
            //e.printStackTrace();
            return false; // Trả về false để biểu thị lỗi trong trường hợp không thành công
        }
    }
}
