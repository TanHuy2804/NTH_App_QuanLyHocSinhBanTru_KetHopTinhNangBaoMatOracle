/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.DBConnect;
import DAO.NhanVien_DAO;
import POJO.NhanVien_POJO;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
/**
 *
 * @author Admin
 */
public final class NhanVien_GUI extends javax.swing.JFrame {
    Vector tblData = new Vector();
    Vector tblTitle = new Vector();
    DefaultTableModel tblModel;
    
    private Managanent_GUI qlc;
    static ArrayList<NhanVien_POJO> dsnv = NhanVien_DAO.LayThongTinNhanVien();
    /**
     * Creates new form NhanVien_GUI
     */
    public NhanVien_GUI() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Employee Manager");
        bangNhanVien();
        laydulieuNV(dsnv);  
        
        txt_quyen.setText(Login_GUI.chucvu_User);
        txt_quyen.setHorizontalAlignment(SwingConstants.CENTER);
        
        String quyenNV = txt_quyen.getText();
        if ("Quan Tri He Thong".equals(quyenNV)) {
            laydulieuNV(dsnv);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Bạn chỉ đủ quyền hạn để coi thông tin của mình");
        }
        if ("Quan Tri He Thong".equals(quyenNV)) {
            mn_phanquyenriengle.setVisible(true);
            mn_phannhomquyen.setVisible(true);
            mn_chinhsachFGA.setVisible(true);
            mn_giamSatThietBi.setVisible(true);
            mn_giamSatSA.setVisible(true);
            mn_giamSatTrigger.setVisible(true);
            mn_giamSatThietBi.setVisible(true);
            mn_standardAudit.setVisible(true);
            mn_backupdatabase.setVisible(true);
            
            mn_profileUser.setVisible(true);
            mn_kiemSoatDeThi.setVisible(true);
            mn_khoiphucDL.setVisible(true);
            mn_TaoRole.setVisible(true);

        } else {
            mn_phanquyenriengle.setVisible(false);
            mn_phannhomquyen.setVisible(false);
            mn_chinhsachFGA.setVisible(false);
            mn_giamSatThietBi.setVisible(false);
            mn_giamSatSA.setVisible(false);
            mn_giamSatTrigger.setVisible(false);
            mn_giamSatThietBi.setVisible(false);
            mn_standardAudit.setVisible(false);
            mn_backupdatabase.setVisible(false);
            
            mn_profileUser.setVisible(false);
            mn_kiemSoatDeThi.setVisible(false);
            mn_khoiphucDL.setVisible(false);
            mn_TaoRole.setVisible(false);
        }
        
        // Tạo mô hình cho cboTrangThaiCongNo
        DefaultComboBoxModel<String> modelChucVu = new DefaultComboBoxModel<>();

        // Thêm các trạng thái khác vào combobox
         modelChucVu.addElement("Quan Tri He Thong");
         modelChucVu.addElement("Giao Vien");
         modelChucVu.addElement("Quan Ly");
         modelChucVu.addElement("Nhan Vien Thu Ngan");

        // Thiết lập mô hình cho combobox
        setComboBoxModel(cbo_chucvu, modelChucVu);
    }  

    // Hàm để thiết lập mô hình cho JComboBox
    private void setComboBoxModel(JComboBox<String> comboBox, DefaultComboBoxModel<String> model) {
        comboBox.setModel(model);
    }
    public void bangNhanVien() {
        tblTitle.add("Mẫ nhân viên");
        tblTitle.add("Tên nhân viên");
        tblTitle.add("Giới tính");
        tblTitle.add("Ngày sinh");
        tblTitle.add("Chức vụ");
        tblTitle.add("Địa chỉ");
        tblTitle.add("Email");
        tblTitle.add("Số điện thoại");
        tblTitle.add("Trình độ");
        tblTitle.add("Chuyên môn");
        tblTitle.add("Nơi đào tạo");
        tblTitle.add("Năm tốt nghiệp");
        tblTitle.add("Hình ảnh");
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
        tb_danhSachNhanVien.setModel(new DefaultTableModel(tblData, tblTitle));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        txt_timKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_danhSachNhanVien = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnTroLai = new javax.swing.JButton();
        txt_quyen = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_maNV = new javax.swing.JTextField();
        txt_tenNV = new javax.swing.JTextField();
        txt_gioiTinh = new javax.swing.JTextField();
        btn_suaNV = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_ngaySinh = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_diaChi = new javax.swing.JTextField();
        txt_chuyenmon = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_noidaotao = new javax.swing.JTextField();
        txt_namtotnghiep = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_trinhdo = new javax.swing.JTextField();
        txt_mailnguoidung = new javax.swing.JTextField();
        txt_sodienthoai = new javax.swing.JTextField();
        btn_themNhanVien = new javax.swing.JButton();
        btn_xoaNhanVien = new javax.swing.JButton();
        cbo_chucvu = new javax.swing.JComboBox<>();
        btn_taiLai = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mn_phanquyenriengle = new javax.swing.JMenuItem();
        mn_phannhomquyen = new javax.swing.JMenuItem();
        mn_profileUser = new javax.swing.JMenuItem();
        mn_TaoRole = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mn_giamSatTrigger = new javax.swing.JMenuItem();
        mn_giamSatSA = new javax.swing.JMenuItem();
        mn_standardAudit = new javax.swing.JMenuItem();
        mn_giamSatThietBi = new javax.swing.JMenuItem();
        mn_chinhsachFGA = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        mn_backupdatabase = new javax.swing.JMenuItem();
        mn_khoiphucDL = new javax.swing.JMenuItem();
        mn_kiemSoatDeThi = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tb_danhSachNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_danhSachNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_danhSachNhanVien);

        btnTimKiem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản Lý Nhân Viên");

        btnTroLai.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTroLai.setText("Trở lại");
        btnTroLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroLaiActionPerformed(evt);
            }
        });

        txt_quyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_quyen.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnTroLai, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(292, 292, 292)
                .addComponent(txt_quyen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTroLai, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_quyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Mã nhân viên:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Tên nhân viên:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Giới Tính:");

        btn_suaNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_suaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/view edit delete product.png"))); // NOI18N
        btn_suaNV.setText("Sửa");
        btn_suaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaNVActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Email:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Trình độ:");
        jLabel7.setToolTipText("");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Ngày sinh:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Số điện thoại:");
        jLabel9.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Địa chỉ:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Chuyên môn:");
        jLabel11.setToolTipText("");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Nơi đào tạo:");
        jLabel12.setToolTipText("");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Năm tốt nghiệp:");
        jLabel13.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Chức vụ:");

        btn_themNhanVien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_themNhanVien.setText("Thêm nhân viên");
        btn_themNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themNhanVienActionPerformed(evt);
            }
        });

        btn_xoaNhanVien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_xoaNhanVien.setText("Xóa");
        btn_xoaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_namtotnghiep))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel8))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_maNV)
                                            .addComponent(txt_tenNV, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                            .addComponent(txt_gioiTinh, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbo_chucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_ngaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel10))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_diaChi)
                                    .addComponent(txt_chuyenmon)
                                    .addComponent(txt_noidaotao)
                                    .addComponent(txt_trinhdo)
                                    .addComponent(txt_mailnguoidung)
                                    .addComponent(txt_sodienthoai)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_themNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_suaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_xoaNhanVien)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_maNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_tenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_gioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ngaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cbo_chucvu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_diaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_mailnguoidung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_sodienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_trinhdo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_chuyenmon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_noidaotao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txt_namtotnghiep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_themNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_suaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_xoaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_taiLai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_taiLai.setText("Tải lại");
        btn_taiLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taiLaiActionPerformed(evt);
            }
        });

        jMenu1.setText("Phân quyền");

        mn_phanquyenriengle.setText("Phân quyền riêng lẻ");
        mn_phanquyenriengle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_phanquyenriengleActionPerformed(evt);
            }
        });
        jMenu1.add(mn_phanquyenriengle);

        mn_phannhomquyen.setText("Phân nhóm quyền");
        mn_phannhomquyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_phannhomquyenActionPerformed(evt);
            }
        });
        jMenu1.add(mn_phannhomquyen);

        mn_profileUser.setText("ProfileUser");
        mn_profileUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_profileUserActionPerformed(evt);
            }
        });
        jMenu1.add(mn_profileUser);

        mn_TaoRole.setText("Tạo role");
        mn_TaoRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_TaoRoleActionPerformed(evt);
            }
        });
        jMenu1.add(mn_TaoRole);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Giám sát ");

        mn_giamSatTrigger.setText("Ghi lại hoạt động đăng nhập đăng xuất");
        mn_giamSatTrigger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_giamSatTriggerActionPerformed(evt);
            }
        });
        jMenu2.add(mn_giamSatTrigger);

        mn_giamSatSA.setText("Dữ liệu giám sát Standard Audit");
        mn_giamSatSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_giamSatSAActionPerformed(evt);
            }
        });
        jMenu2.add(mn_giamSatSA);

        mn_standardAudit.setText("Xây dựng chính sách giám sát trên người dùng hoặc trên bảng");
        mn_standardAudit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_standardAuditActionPerformed(evt);
            }
        });
        jMenu2.add(mn_standardAudit);

        mn_giamSatThietBi.setText("Theo dõi thiết bị truy cập cơ sở dữ liệu");
        mn_giamSatThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_giamSatThietBiActionPerformed(evt);
            }
        });
        jMenu2.add(mn_giamSatThietBi);

        mn_chinhsachFGA.setText("Xây dựng chính sách giám sát 1 người dùng cụ thể trên bảng cụ thể ");
        mn_chinhsachFGA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_chinhsachFGAActionPerformed(evt);
            }
        });
        jMenu2.add(mn_chinhsachFGA);

        jMenuBar1.add(jMenu2);

        jMenu6.setText("Sao lưu và Phục hồi ");

        mn_backupdatabase.setText("Sao lưu và phục hồi cơ sở dữ liệu");
        mn_backupdatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_backupdatabaseActionPerformed(evt);
            }
        });
        jMenu6.add(mn_backupdatabase);

        mn_khoiphucDL.setText("Khôi phục dữ liệu");
        mn_khoiphucDL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_khoiphucDLActionPerformed(evt);
            }
        });
        jMenu6.add(mn_khoiphucDL);

        mn_kiemSoatDeThi.setText("Kiểm soát truy cập đề thi");
        mn_kiemSoatDeThi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mn_kiemSoatDeThiActionPerformed(evt);
            }
        });
        jMenu6.add(mn_kiemSoatDeThi);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 335, Short.MAX_VALUE)
                        .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnTimKiem)
                        .addGap(50, 50, 50)
                        .addComponent(btn_taiLai, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(221, 221, 221))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 957, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTimKiem)
                            .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_taiLai))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 35, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tb_danhSachNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_danhSachNhanVienMouseClicked
        int i = tb_danhSachNhanVien.getSelectedRow();
        String manv = tb_danhSachNhanVien.getValueAt(i, 0).toString().trim();
        String tennv = tb_danhSachNhanVien.getValueAt(i, 1).toString().trim();
        String gioitinh = tb_danhSachNhanVien.getValueAt(i, 2).toString().trim();
        String ngaysinh = tb_danhSachNhanVien.getValueAt(i, 3).toString().trim();
        String chucvu = tb_danhSachNhanVien.getValueAt(i, 4).toString().trim();
        String diachi = tb_danhSachNhanVien.getValueAt(i, 5).toString().trim();
        String email = tb_danhSachNhanVien.getValueAt(i, 6).toString().trim();
        String sdt = tb_danhSachNhanVien.getValueAt(i, 7).toString().trim();
        String trinhdo = tb_danhSachNhanVien.getValueAt(i, 8).toString().trim();
        String chuyenmon = tb_danhSachNhanVien.getValueAt(i, 9).toString().trim();
        String noidaotao = tb_danhSachNhanVien.getValueAt(i, 10).toString().trim();
        String namtotnghiep = tb_danhSachNhanVien.getValueAt(i, 11).toString().trim();


        txt_maNV.setText(manv);
        txt_tenNV.setText(tennv);
        txt_diaChi.setText(diachi);
        txt_gioiTinh.setText(gioitinh);
        txt_ngaySinh.setText(ngaysinh);
        //txt_chucvu.setText(chucvu);
        cbo_chucvu.setSelectedItem(chucvu);
        txt_mailnguoidung.setText(email);
        txt_sodienthoai.setText(sdt);
        txt_trinhdo.setText(trinhdo);
        txt_chuyenmon.setText(chuyenmon);
        txt_noidaotao.setText(noidaotao);
        txt_namtotnghiep.setText(namtotnghiep);
    }//GEN-LAST:event_tb_danhSachNhanVienMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String manvs = txt_timKiem.getText();
        boolean found = false; // Biến để kiểm tra xem có kết quả tìm kiếm hay không

        if (manvs.equals("")) {
            NhanVien_DAO.LayThongTinNhanVien();
            dsnv = NhanVien_DAO.LayThongTinNhanVien(); // Cập nhật danh sách dsnv
            laydulieuNV(dsnv);
        } else {
            NhanVien_DAO.TimKiemNhanVien(manvs);
            dsnv = NhanVien_DAO.TimKiemNhanVien(manvs);

            // Kiểm tra xem có kết quả tìm kiếm hay không
            if (dsnv != null && !dsnv.isEmpty()) {
                found = true;
                laydulieuNV(dsnv);
            }
        }
        
        if (!found) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy : " + manvs, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnTroLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTroLaiActionPerformed
        Managanent_GUI qlc = new Managanent_GUI();
        qlc.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnTroLaiActionPerformed

    private void btn_suaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaNVActionPerformed
        String username = txt_maNV.getText().trim();
        String hoTen = txt_tenNV.getText().trim();
        String gioiTinh = txt_gioiTinh.getText().trim();
        String ngaySinh = txt_ngaySinh.getText().trim();
        //String chucvu = txt_chucvu.getText().trim();
        String chucvu = cbo_chucvu.getSelectedItem().toString().trim();
        String diaChi = txt_diaChi.getText().trim();
        String email = txt_mailnguoidung.getText().trim();
        String sdt = txt_sodienthoai.getText().trim();
        String trinhdo = txt_trinhdo.getText().trim();
        String chuyenmon = txt_chuyenmon.getText().trim();
        String noidaotao = txt_noidaotao.getText().trim();
        String namtotnghiep = txt_namtotnghiep.getText().trim();
        String maUser = Login_GUI.loggedInUser;
        try {
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục
            String sql = "{call NV001.CapNhatNhanVien(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            // Tạo PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Chuyển đổi ngayDiemDanh từ String sang java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(ngaySinh);

                // Đặt các tham số cho câu lệnh SQL
                
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hoTen);
                preparedStatement.setString(3, gioiTinh);
                preparedStatement.setDate(4, sqlDate);
                preparedStatement.setString(5, chucvu);
                preparedStatement.setString(6, diaChi);
                preparedStatement.setString(7, email);
                preparedStatement.setString(8, sdt);
                preparedStatement.setString(9, trinhdo);
                preparedStatement.setString(10, chuyenmon);
                preparedStatement.setString(11, noidaotao);
                preparedStatement.setInt(12, Integer.parseInt(namtotnghiep));
                preparedStatement.setString(13, maUser);
                // Thực thi câu lệnh SQL
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            // Xử lý lỗi nếu có
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Bạn đang muốn sửa thông tin này?", "Lời chú ý", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_suaNVActionPerformed

    private void btn_taiLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taiLaiActionPerformed
        // TODO add your handling code here:
        ArrayList<NhanVien_POJO> ds = NhanVien_DAO.LayThongTinNhanVien(); // Cập nhật danh sách dsnv
        laydulieuNV(ds);
    }//GEN-LAST:event_btn_taiLaiActionPerformed

    private void mn_phanquyenriengleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_phanquyenriengleActionPerformed
        // TODO add your handling code here:
        PhanQuyen_GUI pq = new PhanQuyen_GUI();
        pq.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_phanquyenriengleActionPerformed

    private void mn_phannhomquyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_phannhomquyenActionPerformed
        // TODO add your handling code here:
        PhanNhomQuyen_GUI pnq = new PhanNhomQuyen_GUI();
        pnq.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_phannhomquyenActionPerformed

    private void mn_chinhsachFGAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_chinhsachFGAActionPerformed
        // TODO add your handling code here:
        FGA_GUI fgaNV = new FGA_GUI();
        fgaNV.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_chinhsachFGAActionPerformed

    private void mn_giamSatThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_giamSatThietBiActionPerformed
        // TODO add your handling code here:
        GiamSatThietBiTruyCap_GUI gstbtc = new GiamSatThietBiTruyCap_GUI();
        gstbtc.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_giamSatThietBiActionPerformed

    private void mn_giamSatSAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_giamSatSAActionPerformed
        // TODO add your handling code here:
        GiamSatSA_GUI gsSA = new GiamSatSA_GUI();
        gsSA.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_giamSatSAActionPerformed

    private void mn_giamSatTriggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_giamSatTriggerActionPerformed
        // TODO add your handling code here:
        GiamSatTrigger_GUI gs = new GiamSatTrigger_GUI();
        gs.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_giamSatTriggerActionPerformed

    private void mn_standardAuditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_standardAuditActionPerformed
        // TODO add your handling code here:
        GiamSatNhanVienBangSA_GUI gssdSA = new GiamSatNhanVienBangSA_GUI();
        gssdSA.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_standardAuditActionPerformed

    private void mn_backupdatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_backupdatabaseActionPerformed
        // TODO add your handling code here:
        Backup_GUI Backup = new Backup_GUI();
        Backup.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_backupdatabaseActionPerformed

    private void mn_profileUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_profileUserActionPerformed
        // TODO add your handling code here:
        Profile_GUI profile = new Profile_GUI();
        profile.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_profileUserActionPerformed

    private void mn_kiemSoatDeThiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_kiemSoatDeThiActionPerformed
        // TODO add your handling code here:
        KiemSoatDeThi ksdt = new KiemSoatDeThi();
        ksdt.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_kiemSoatDeThiActionPerformed

    private void mn_khoiphucDLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_khoiphucDLActionPerformed
        // TODO add your handling code here:
        KhoiPhucCSDL_GUI kp = new KhoiPhucCSDL_GUI();
        kp.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_khoiphucDLActionPerformed

    private void mn_TaoRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mn_TaoRoleActionPerformed
        // TODO add your handling code here:
        TaoRoleCapQuyenChoRoleNew rl = new TaoRoleCapQuyenChoRoleNew();
        rl.setVisible(true);
        dispose();
    }//GEN-LAST:event_mn_TaoRoleActionPerformed

    private void btn_themNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themNhanVienActionPerformed
        // TODO add your handling code here:
        String maNV = txt_maNV.getText().trim();
        String tenNV = txt_tenNV.getText().trim();
        String gioiTinh = txt_gioiTinh.getText().trim();
        String ngaySinh = txt_ngaySinh.getText().trim();
        //String chucVu = txt_chucvu.getText().trim();
        String chucVu = cbo_chucvu.getSelectedItem().toString().trim();
        String diaChi = txt_diaChi.getText().trim();
        String email = txt_mailnguoidung.getText().trim();
        String sdt = txt_sodienthoai.getText().trim();
        String trinhDo = txt_trinhdo.getText().trim();
        String chuyenMon = txt_chuyenmon.getText().trim();
        String noiDaoTao = txt_noidaotao.getText().trim();
        String namTotNghiep = txt_namtotnghiep.getText().trim();
        String maUser = Login_GUI.loggedInUser;

        try {
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục
            String sql = "{call NV001.ThemNhanVien(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            // Tạo PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Chuyển đổi ngaySinh từ String sang java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(ngaySinh);

                // Đặt các tham số cho câu lệnh SQL
                preparedStatement.setString(1, maNV);
                preparedStatement.setString(2, tenNV);
                preparedStatement.setString(3, gioiTinh);
                preparedStatement.setDate(4, sqlDate);
                preparedStatement.setString(5, chucVu);
                preparedStatement.setString(6, diaChi);
                preparedStatement.setString(7, email);
                preparedStatement.setString(8, sdt);
                preparedStatement.setString(9, trinhDo);
                preparedStatement.setString(10, chuyenMon);
                preparedStatement.setString(11, noiDaoTao);
                preparedStatement.setInt(12, Integer.parseInt(namTotNghiep));
                preparedStatement.setString(13, maUser);

                // Thực thi câu lệnh SQL
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            // Xử lý lỗi nếu có
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm nhân viên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_themNhanVienActionPerformed

    private void btn_xoaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaNhanVienActionPerformed
        // TODO add your handling code here:
        String manv = txt_maNV.getText();
        String sql = "DELETE FROM NV001.NhanVien WHERE MaNV='" + manv + "'";
        String sql2 = "{CALL NV001.XoaUser('"+ manv +"')}";
        if (NhanVien_DAO.ThemXoaSuaNhanVien(sql) > 0 && NhanVien_DAO.ThemXoaSuaNhanVien(sql2) == 0) {
            dsnv = NhanVien_DAO.LayThongTinNhanVien();
            JOptionPane.showMessageDialog(this,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);
            laydulieuNV(dsnv);
        }
    }//GEN-LAST:event_btn_xoaNhanVienActionPerformed
    
    private byte[] encryptWithPublicKey(String data, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
        InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }
    // Hàm lấy khóa công khai từ chuỗi Base64
    private PublicKey getPublicKey(String publicKeyBase64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
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
            java.util.logging.Logger.getLogger(NhanVien_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVien_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVien_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVien_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVien_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTroLai;
    private javax.swing.JButton btn_suaNV;
    private javax.swing.JButton btn_taiLai;
    private javax.swing.JButton btn_themNhanVien;
    private javax.swing.JButton btn_xoaNhanVien;
    private javax.swing.JComboBox<String> cbo_chucvu;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem mn_TaoRole;
    private javax.swing.JMenuItem mn_backupdatabase;
    private javax.swing.JMenuItem mn_chinhsachFGA;
    private javax.swing.JMenuItem mn_giamSatSA;
    private javax.swing.JMenuItem mn_giamSatThietBi;
    private javax.swing.JMenuItem mn_giamSatTrigger;
    private javax.swing.JMenuItem mn_khoiphucDL;
    private javax.swing.JMenuItem mn_kiemSoatDeThi;
    private javax.swing.JMenuItem mn_phannhomquyen;
    private javax.swing.JMenuItem mn_phanquyenriengle;
    private javax.swing.JMenuItem mn_profileUser;
    private javax.swing.JMenuItem mn_standardAudit;
    private javax.swing.JTable tb_danhSachNhanVien;
    private javax.swing.JTextField txt_chuyenmon;
    private javax.swing.JTextField txt_diaChi;
    private javax.swing.JTextField txt_gioiTinh;
    private javax.swing.JTextField txt_maNV;
    private javax.swing.JTextField txt_mailnguoidung;
    private javax.swing.JTextField txt_namtotnghiep;
    private javax.swing.JTextField txt_ngaySinh;
    private javax.swing.JTextField txt_noidaotao;
    private javax.swing.JTextField txt_quyen;
    private javax.swing.JTextField txt_sodienthoai;
    private javax.swing.JTextField txt_tenNV;
    private javax.swing.JTextField txt_timKiem;
    private javax.swing.JTextField txt_trinhdo;
    // End of variables declaration//GEN-END:variables

}
