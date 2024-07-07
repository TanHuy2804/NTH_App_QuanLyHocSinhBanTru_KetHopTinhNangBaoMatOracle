/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


/**
 *
 * @author TAN HUY
 */
public class DoiPass_DAO {
    public static int ThucThiChucNang(String sql) {
        int i = 0;
        try {

            DBConnect conn = new DBConnect();
            conn.GetConnect();
            i = conn.executeUpdate(sql);
            System.out.println("Chuc nang thuc hien thanh cong");
        } catch (Exception e) {
            System.out.println("Chuc nang thuc hien that bai");
        }
        return i;
    }
}
