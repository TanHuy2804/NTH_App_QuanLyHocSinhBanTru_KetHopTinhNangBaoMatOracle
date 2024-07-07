/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.DAO_KhauPhanAn;
import POJO.*;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class frm_ThongTinDangKy extends javax.swing.JFrame {

    /**
     * Creates new form frm_ThongTinDangKy
     */
    Vector tblData = new Vector();
    Vector tblTitle = new Vector();
    DefaultTableModel tblModel;

    static ArrayList<POJO_DangKy_KPA> ds;

    public frm_ThongTinDangKy() {
        initComponents();
        tblModel = new DefaultTableModel();
        tblTitle.add("Mã học sinh");
        tblTitle.add("Ngày đăng kí");
        tblTitle.add("Thứ");
        tblTitle.add("Giá");
        tblTitle.add("Mã Combo");
        tblTitle.add("QRCode");

        showDuLieu(ds);
        //cho chon trên lưới, không cho chỉnh sửa
        jTable_dangki.setDefaultEditor(Object.class, null);
        jTable_dangki.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);                  
    }
    
    //lấy đường dẫn hình ảnh
    public String duongDanHinhAnh(String tenHinh){
        // Chuyển đổi đường dẫn tương đối thành đối tượng File
        String tenThuMucHinhAnh = "src\\IMG_QRCode";
        File imageDirectory = new File(tenThuMucHinhAnh);

        // Lấy đường dẫn tuyệt đối của thư mục hình ảnh
        String thuMucHinhAnh_ddTuyetDoi = imageDirectory.getAbsolutePath();
        System.out.println("dđ: " + thuMucHinhAnh_ddTuyetDoi);

        // Tạo đường dẫn tuyệt đối đến hình ảnh
        String imgPath = thuMucHinhAnh_ddTuyetDoi + File.separator + tenHinh;
        System.out.println("dd: "  + imgPath);
        
        return imgPath;
    }

    // load ds 
    public void showDuLieu(ArrayList<POJO_DangKy_KPA> ds) {
        ds = DAO_KhauPhanAn.getBangDangKyMonAn();

        tblData.removeAllElements();
        for (POJO_DangKy_KPA b : ds) {
            Vector v = new Vector();
            v.add(b.getMaHS());
            v.add(b.getNgayDangKyKP());
            v.add(b.getThuTrongTuan());
            v.add(b.getGia());
            v.add(b.getMaComBoMon());
            v.add(b.getQRCode());
            tblData.add(v);
        }
        jTable_dangki.setModel(new DefaultTableModel(tblData, tblTitle));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_dangki = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btn_xoa_all = new javax.swing.JButton();
        btn_xoa1KP = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_ngaydk = new javax.swing.JTextField();
        txt_thu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_gia = new javax.swing.JTextField();
        txt_macb = new javax.swing.JTextField();
        txt_mahs = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel_qr = new javax.swing.JLabel();
        btn_quaylai = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THÔNG TIN ĐĂNG KÍ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(255, 0, 0))); // NOI18N

        jTable_dangki.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable_dangki.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_dangkiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_dangki);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thao tác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12), new java.awt.Color(255, 0, 51))); // NOI18N

        btn_xoa_all.setBackground(new java.awt.Color(204, 204, 204));
        btn_xoa_all.setText("Xóa tất cả khẩu phần");
        btn_xoa_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoa_allActionPerformed(evt);
            }
        });

        btn_xoa1KP.setBackground(new java.awt.Color(204, 204, 204));
        btn_xoa1KP.setText("Xóa khẩu phần");
        btn_xoa1KP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoa1KPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_xoa_all, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_xoa1KP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_xoa_all)
                .addGap(18, 18, 18)
                .addComponent(btn_xoa1KP)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12), new java.awt.Color(255, 0, 51))); // NOI18N

        jLabel2.setText("Mã combo:");

        jLabel3.setText("Ngày đăng kí:");

        txt_ngaydk.setEnabled(false);

        txt_thu.setEnabled(false);

        jLabel4.setText("Thứ:");

        jLabel5.setText("Giá:");

        txt_gia.setEnabled(false);

        txt_macb.setEnabled(false);

        txt_mahs.setEnabled(false);

        jLabel1.setText("Mã HS: ");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "QR Code", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        jLabel_qr.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel_qr, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_qr, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_mahs)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_macb, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txt_ngaydk, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_gia, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(txt_thu))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_mahs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_macb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(txt_thu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_gia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txt_ngaydk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_quaylai.setBackground(new java.awt.Color(204, 204, 204));
        btn_quaylai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_quaylai.setForeground(new java.awt.Color(255, 51, 0));
        btn_quaylai.setText("QUAY LẠI");
        btn_quaylai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quaylaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_quaylai)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_quaylai, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_quaylaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quaylaiActionPerformed
        // TODO add your handling code here:
        frm_HocSinh_KPA fr = new frm_HocSinh_KPA();
        fr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_quaylaiActionPerformed

    private void jTable_dangkiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_dangkiMouseClicked
        // TODO add your handling code here:
        int row = jTable_dangki.getSelectedRow();

        String mahs = jTable_dangki.getValueAt(row, 0).toString();
        String ngaydk = jTable_dangki.getValueAt(row, 1).toString();
        String thu = jTable_dangki.getValueAt(row, 2).toString();
        String gia = jTable_dangki.getValueAt(row, 3).toString();
        String macb = jTable_dangki.getValueAt(row, 4).toString();
        String QR = jTable_dangki.getValueAt(row, 5).toString();
               
        txt_ngaydk.setText(ngaydk);
        txt_macb.setText(macb);
        txt_gia.setText(gia);
        txt_thu.setText(thu);
        txt_mahs.setText(mahs);

        String imagePath = duongDanHinhAnh(QR);

        // Tạo ImageIcon từ ảnh
        ImageIcon originalIcon = new ImageIcon(imagePath);

        // Lấy kích thước của JLabel
        int labelWidth = 90;
        int labelHeight = 90;

        // Chuyển đổi ImageIcon thành Image để điều chỉnh kích thước
        Image originalImage = originalIcon.getImage();

        // Điều chỉnh kích thước của ảnh
        Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        // Tạo JLabel để hiển thị ảnh
        jLabel_qr.setIcon(scaledIcon);

    }//GEN-LAST:event_jTable_dangkiMouseClicked

    private void btn_xoa_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoa_allActionPerformed
        // TODO add your handling code here:
        String maNV = Login_GUI.loggedInUser;
        int chon = JOptionPane.showConfirmDialog(null, "Xóa danh sách khẩu phẩn ăn !",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            int tam = DAO_KhauPhanAn.xoaDuLieu_dangki(maNV);
            if (tam == 1) {
                JOptionPane.showMessageDialog(rootPane, "Đã xóa dữ liệu khẩu phần cũ...");
                showDuLieu(ds);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Xóa dữ liệu khẩu phần cũ thất bại...");
            }
        }
    }//GEN-LAST:event_btn_xoa_allActionPerformed

    private void btn_xoa1KPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoa1KPActionPerformed
        // TODO add your handling code here:
        String ngayDK = txt_ngaydk.getText();
        int thu = Integer.parseInt(txt_thu.getText());
        String mahs = txt_mahs.getText();
        String maNV = Login_GUI.loggedInUser;
        
        int tam = DAO_KhauPhanAn.xoaDangAKi_MotKhauPhan(mahs, ngayDK, thu, maNV);
        if (tam == 1) {
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");
            showDuLieu(ds);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Bạn chỉ được thay đổi thông tin đăng kí từ 2 - thứ 6!");
        }
    }//GEN-LAST:event_btn_xoa1KPActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_ThongTinDangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_ThongTinDangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_ThongTinDangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_ThongTinDangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_ThongTinDangKy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_quaylai;
    private javax.swing.JButton btn_xoa1KP;
    private javax.swing.JButton btn_xoa_all;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel_qr;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_dangki;
    private javax.swing.JTextField txt_gia;
    private javax.swing.JTextField txt_macb;
    private javax.swing.JTextField txt_mahs;
    private javax.swing.JTextField txt_ngaydk;
    private javax.swing.JTextField txt_thu;
    // End of variables declaration//GEN-END:variables
}
