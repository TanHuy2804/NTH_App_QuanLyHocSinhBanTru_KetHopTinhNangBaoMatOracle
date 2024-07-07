/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.DBConnect;
import DAO.FGAAuditTrail_DAO;
import DAO.NhanVien_DAO;
import DAO.GiamSatSA_DAO;
import static GUI.FGA_GUI.tenTable;
import POJO.DBA_AUDIT_TRAIL;
import POJO.NhanVien_POJO;
import POJO.Table_POJO;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

/**
 *
 * @author Admin
 */
public class GiamSatSA_GUI extends javax.swing.JFrame {
    Vector tblData = new Vector();
    Vector tblTitle = new Vector();
    
    Vector tblData2 = new Vector();
    Vector tblTitle2 = new Vector();
    
    static ArrayList<NhanVien_POJO> dsnv = NhanVien_DAO.LayThongTinNhanVien();
    static ArrayList<DBA_AUDIT_TRAIL> dat = GiamSatSA_DAO.LayThongTinGiamSat();

    /**
     * Creates new form frm_GiamSatSA
     */
    public GiamSatSA_GUI() {
        initComponents();    
        this.setLocationRelativeTo(null);       
      
        bangNhanVien();
        laydulieuNV(dsnv);
        
        bangGiamSatNV();
        layDuLieuGiamSat(dat);
        
        loadComboBoxLayTable();
        
        txt_quyen.setText(Login_GUI.chucvu_User);
        txt_quyen.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void loadComboBoxLayTable() {
        ArrayList<Table_POJO> dsdt = FGAAuditTrail_DAO.LayTable();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (Table_POJO dt : dsdt) {
            model.addElement(dt.getTABLE_NAME());
        }
        cbo_tenBang.setModel(model);
        cbo_tenBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox cb = (JComboBox) evt.getSource();
            }
        });
    }
    
    public void bangNhanVien() {
        tblTitle.add("MANV");
        tblTitle.add("TENNV");
        tblTitle.add("GIOITINH");
        tblTitle.add("NGAYSINH");
        tblTitle.add("CHUCVU");
        tblTitle.add("DIACHI");
        tblTitle.add("EMAIL");
        tblTitle.add("SODT");
        tblTitle.add("TRINHDO");
        tblTitle.add("CHUYENMON");
        tblTitle.add("NOIDAOTAO");
        tblTitle.add("NAMTOTNGHIEP");
        tblTitle.add("HINHANH");
    }
    public void laydulieuNV(ArrayList<NhanVien_POJO> dsnv) {
        tblData.removeAllElements();
        for (NhanVien_POJO b : dsnv) {
            Vector v = new Vector();
            v.add(b.getMANV());
            v.add(b.getTENNV());
            v.add(b.getGIOITINH());
            v.add(b.getNGAYSINH());
            v.add(b.getCHUCVU());
            v.add(b.getDIACHI());
            v.add(b.getEMAIL());
            v.add(b.getSODT());
            v.add(b.getTRINHDO());
            v.add(b.getCHUYENMON());
            v.add(b.getNOIDAOTAO());
            v.add(b.getNAMTOTNGHIEP());
            v.add(b.getHINHANH());
            tblData.add(v);
        }
        tb_NhanVien.setModel(new DefaultTableModel(tblData, tblTitle));
    }
    
    public void bangGiamSatNV() {
        tblTitle2.add("Tên người dùng");
        tblTitle2.add("Thời gian");
        tblTitle2.add("Đối tượng bảng");
        tblTitle2.add("Hành động truy cập");
    }
    public void layDuLieuGiamSat(ArrayList<DBA_AUDIT_TRAIL> dat) {
        tblData2.removeAllElements();
        for (DBA_AUDIT_TRAIL a : dat) {
            Vector v = new Vector();
            v.add(a.getUsername());
            v.add(a.getTimestamp());
            v.add(a.getObj_name());
            v.add(a.getAction_name());
            tblData2.add(v);
        }
        tb_giamSat.setModel(new DefaultTableModel(tblData2, tblTitle2));
    }
    public void layDuLieuGiamSatNV(ArrayList<DBA_AUDIT_TRAIL> LoadDuLieuGiamSatNV) {
        tblData2.removeAllElements();
        for (DBA_AUDIT_TRAIL a : LoadDuLieuGiamSatNV) {
            Vector v = new Vector();
            v.add(a.getUsername());
            v.add(a.getTimestamp());
            v.add(a.getObj_name());
            v.add(a.getAction_name());
            tblData2.add(v);
        }
        tb_giamSat.setModel(new DefaultTableModel(tblData2, tblTitle2));
    }
    
    public ArrayList<DBA_AUDIT_TRAIL> LoadDuLieuGiamSatNV() {
        ArrayList<DBA_AUDIT_TRAIL> dat2 = new ArrayList<DBA_AUDIT_TRAIL>();
        String manv = txt_maNV.getText();
        String tenBang = cbo_tenBang.getSelectedItem().toString();
        try {
            String sql = "SELECT username, TO_CHAR(timestamp, 'YYYY-MM-DD HH24:MI:SS') AS formatted_timestamp, obj_name, action_name "
                    + "FROM dba_audit_trail WHERE username = '"+manv+"' AND obj_name = '"+tenBang+"' "
                    + "GROUP BY username, timestamp, obj_name, action_name";
            DBConnect conn = new DBConnect();
            conn.GetConnect();           
            ResultSet rs = conn.executeQuery(sql);           
            while (rs.next()) {  
            String username = rs.getString("username");
            Timestamp timestamp = rs.getTimestamp("formatted_timestamp");
            String obj_name = rs.getString("obj_name");
            String action_name = rs.getString("action_name");

            DBA_AUDIT_TRAIL ds = new DBA_AUDIT_TRAIL(username, timestamp, obj_name, action_name);
            dat2.add(ds);
            System.out.println("Lay du lieu nhan vien thanh cong!");
        }             
        } catch (Exception e) {
            //System.err.println("Lay du lieu nhan vien that bai!");
            e.printStackTrace();
        }
        return dat2;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_troVe = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_maNV = new javax.swing.JTextField();
        btn_GiamSat = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt_quyen = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_NhanVien = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_giamSat = new javax.swing.JTable();
        cbo_tenBang = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_troVe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_troVe.setText("Trở về");
        btn_troVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_troVeActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Mã nhân viên:");

        btn_GiamSat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_GiamSat.setText("TÌM DỮ LIỆU");
        btn_GiamSat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GiamSatActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tên bảng:");

        txt_quyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_quyen.setEnabled(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin nhân viên"));

        tb_NhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_NhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_NhanVien);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dữ liệu giám sát"));

        jScrollPane2.setViewportView(tb_giamSat);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txt_quyen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(btn_GiamSat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_troVe))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_maNV, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbo_tenBang, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_quyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_maNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(cbo_tenBang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_troVe)
                            .addComponent(btn_GiamSat))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void tb_NhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_NhanVienMouseClicked
        int i = tb_NhanVien.getSelectedRow();
        String manv = tb_NhanVien.getValueAt(i, 0).toString().trim();
         
        txt_maNV.setText(manv);
    }//GEN-LAST:event_tb_NhanVienMouseClicked

    private void btn_troVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_troVeActionPerformed
        // TODO add your handling code here:
        Managanent_GUI qlnv = new Managanent_GUI();
        qlnv.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_troVeActionPerformed

    private void btn_GiamSatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GiamSatActionPerformed
        // TODO add your handling code here:
        layDuLieuGiamSatNV(LoadDuLieuGiamSatNV());
    }//GEN-LAST:event_btn_GiamSatActionPerformed

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
            java.util.logging.Logger.getLogger(GiamSatSA_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GiamSatSA_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GiamSatSA_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GiamSatSA_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GiamSatSA_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_GiamSat;
    private javax.swing.JButton btn_troVe;
    private javax.swing.JComboBox<String> cbo_tenBang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tb_NhanVien;
    private javax.swing.JTable tb_giamSat;
    private javax.swing.JTextField txt_maNV;
    private javax.swing.JTextField txt_quyen;
    // End of variables declaration//GEN-END:variables
}
