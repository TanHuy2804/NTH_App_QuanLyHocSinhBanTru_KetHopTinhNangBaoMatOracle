/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
/**
 *
 * @author Admin
 */
public class DBConnect {
    public static String url = "jdbc:oracle:thin:@";
    
    public static String localhost = "";
    public static String port = "";
    public static String database = "";
    public static String user = "";
    public static String pass = "";
    public static Connection conn;
    private static DBConnect instance = new DBConnect();
    public static DBConnect getInstance() {
        return instance;
    }
    
    public static boolean Connect(){
        try {
            if(user.equals("sys")||user.equals("SYS")){
                user +=" as sysdba";
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url+localhost+":"+port+":"+database,user,pass);
            System.out.println("Thanh cong");
            return true;
        } catch (Exception e) {
            System.out.println("That bai");
            return false;
        }
    }
    
    public void Disconnect() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                //e.printStackTrace();
            }
    }
    
    public static Connection GetConnect() {
        if(conn == null){
            Connect();
        }
        return conn;
    }  
    
    public void close() {
        try {
            this.conn.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            Statement stm = conn.createStatement();
            rs = stm.executeQuery(sql);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String sql) {
        int i = -1;
        try {
            Statement stm = conn.createStatement();
            i = stm.executeUpdate(sql);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return i;
    }
}
