/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.FGAAuditTrail_DAO;
import DAO.GiamSatSA_DAO;
import DAO.NhanVien_DAO;
import static GUI.FGA_GUI.tenTable;
import POJO.DBA_AUDIT_TRAIL;
import POJO.DBA_OBJ_AUDIT_OPTS_POJO;
import POJO.NhanVien_POJO;
import POJO.Table_POJO;
import POJO.TaiKhoan_POJO;
import POJO.dba_stmt_audit_opts_POJO;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class GiamSatNhanVienBangSA_GUI extends javax.swing.JFrame {
    Vector tblData1 = new Vector();
    Vector tblTitle1 = new Vector();
    
    Vector tblData2 = new Vector();
    Vector tblTitle2 = new Vector();
    
    Vector tblData3 = new Vector();
    Vector tblTitle3 = new Vector();
    DefaultTableModel tblModel2;
            
    static ArrayList<NhanVien_POJO> dsnv = NhanVien_DAO.LayThongTinNhanVien();
    static ArrayList<DBA_AUDIT_TRAIL> dat = GiamSatSA_DAO.LayThongTinGiamSat();
    static ArrayList<dba_stmt_audit_opts_POJO> dl = GiamSatSA_DAO.LayThongTin_dba_stmt_audit_opts();
    static ArrayList<DBA_OBJ_AUDIT_OPTS_POJO> DLTB = GiamSatSA_DAO.LayThongTin_DBA_OBJ_AUDIT_OPTS();
    /**
     * Creates new form frm_GiamSatSuDungSA
     */
    public GiamSatNhanVienBangSA_GUI() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        loadComboBoxUserName_TK();
        loadComboBoxLayTable();
        
        bangGiamSatNV();
        layDuLieuGiamSat(dat);
        
        dba_stmt_audit_opts();
        layDuLieu_dba_stmt_audit_opts(dl);
        
        DBA_OBJ_AUDIT_OPTS();
        layDuLieu_DBA_OBJ_AUDIT_OPTS(DLTB);
        
        txt_quyen.setText(Login_GUI.chucvu_User);
        txt_quyen.setHorizontalAlignment(SwingConstants.CENTER);
    }
    public void loadComboBoxUserName_TK() {
        ArrayList<TaiKhoan_POJO> dsdt = GiamSatSA_DAO.UserName_TK_SelectAll();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (TaiKhoan_POJO dt : dsdt) {
            model.addElement(dt.getUserName_TK());
        }
        cbo_maUser.setModel(model);
        cbo_maUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox cb = (JComboBox) evt.getSource();
            }
        });
    }
//    private GiamSatNhanVienBangSA_GUI() {
//    }
    public void bangGiamSatNV() {
        tblTitle2.add("username");
        tblTitle2.add("timestamp");
        tblTitle2.add("obj_name");
        tblTitle2.add("action_name");
    }
    public void dba_stmt_audit_opts() {
        tblTitle1.add("user_name");
        tblTitle1.add("audit_option");
        tblTitle1.add("success");
        tblTitle1.add("failure");
    }
    public void DBA_OBJ_AUDIT_OPTS() {
        tblTitle3.add("OWNER");
        tblTitle3.add("OBJECT_NAME");
        tblTitle3.add("OBJECT_TYPE");
        tblTitle3.add("DEL");
        tblTitle3.add("INS");
        tblTitle3.add("SEL");
        tblTitle3.add("UPD");
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
        tb_GiamSatNV.setModel(new DefaultTableModel(tblData2, tblTitle2));
    }
    public void layDuLieu_dba_stmt_audit_opts(ArrayList<dba_stmt_audit_opts_POJO> dl) {
        tblData1.removeAllElements();
        for (dba_stmt_audit_opts_POJO h : dl) {
            Vector v1 = new Vector();
            v1.add(h.getUser_name());
            v1.add(h.getAudit_option());
            v1.add(h.getSuccess());
            v1.add(h.getFailure());
            tblData1.add(v1);
        }
        tb_dba_stmt_audit_opts.setModel(new DefaultTableModel(tblData1, tblTitle1));
    }
    public void layDuLieu_DBA_OBJ_AUDIT_OPTS(ArrayList<DBA_OBJ_AUDIT_OPTS_POJO> TBDL) {
        tblData3.removeAllElements();
        for (DBA_OBJ_AUDIT_OPTS_POJO C : TBDL) {
            Vector v2 = new Vector();
            v2.add(C.getOWNER());
            v2.add(C.getOBJECT_NAME());
            v2.add(C.getOBJECT_TYPE());
            v2.add(C.getDEL());
            v2.add(C.getINS());
            v2.add(C.getSEL());
            v2.add(C.getUPD());
            tblData3.add(v2);
        }
        tb_bang.setModel(new DefaultTableModel(tblData3, tblTitle3));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_quyen = new javax.swing.JTextField();
        btn_troVe = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        cbo_maUser = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btn_ApDungTheoUser = new javax.swing.JButton();
        btn_boGiamSat = new javax.swing.JButton();
        ckb_selectTB = new javax.swing.JCheckBox();
        ckb_updateTB = new javax.swing.JCheckBox();
        ckb_InsertTB = new javax.swing.JCheckBox();
        ckb_deleteTB = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_dba_stmt_audit_opts = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_GiamSatNV = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        ckb_auditSelect = new javax.swing.JCheckBox();
        ckb_auditInsert = new javax.swing.JCheckBox();
        ckb_auditUpdate = new javax.swing.JCheckBox();
        ckh_auditDelete = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        cbo_tenbang = new javax.swing.JComboBox<>();
        btn_apDungTheoBang = new javax.swing.JButton();
        btn_boApDungTheoBang = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_bang = new javax.swing.JTable();
        btn_taiDL = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txt_quyen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_quyen.setEnabled(false);

        btn_troVe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_troVe.setText("Trở về");
        btn_troVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_troVeActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GIÁM SÁT NGƯỜI DÙNG CỤ THỂ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Mã người dùng:");

        btn_ApDungTheoUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_ApDungTheoUser.setText("Áp dụng giám sát");
        btn_ApDungTheoUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ApDungTheoUserActionPerformed(evt);
            }
        });

        btn_boGiamSat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_boGiamSat.setText("Bỏ giám sát");
        btn_boGiamSat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_boGiamSatActionPerformed(evt);
            }
        });

        ckb_selectTB.setText("Select table");

        ckb_updateTB.setText("Update table");

        ckb_InsertTB.setText("Insert table");

        ckb_deleteTB.setText("Delete table");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(ckb_InsertTB, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(ckb_deleteTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(99, 99, 99)))
                                .addComponent(btn_ApDungTheoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btn_boGiamSat, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbo_maUser, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(11, 11, 11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ckb_selectTB, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ckb_updateTB, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbo_maUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ckb_selectTB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckb_updateTB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckb_InsertTB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckb_deleteTB)
                        .addContainerGap(37, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(btn_ApDungTheoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_boGiamSat)
                        .addGap(10, 10, 10))))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đối tượng người dùng đang bị giám sát", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jScrollPane2.setViewportView(tb_dba_stmt_audit_opts);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dữ liệu giám sát từ chính sách Audit"));

        tb_GiamSatNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_GiamSatNVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_GiamSatNV);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GIÁM SÁT TRÊN BẢNG CỤ THỂ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        ckb_auditSelect.setText("Audit select");

        ckb_auditInsert.setText("Audit insert");

        ckb_auditUpdate.setText("Audit update");

        ckh_auditDelete.setText("Audit delete");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tên bảng cụ thể:");

        btn_apDungTheoBang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_apDungTheoBang.setText("Áp dụng giám sát");
        btn_apDungTheoBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_apDungTheoBangActionPerformed(evt);
            }
        });

        btn_boApDungTheoBang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_boApDungTheoBang.setText("Bỏ giám sát");
        btn_boApDungTheoBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_boApDungTheoBangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ckb_auditInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(ckb_auditSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ckh_auditDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ckb_auditUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_boApDungTheoBang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_apDungTheoBang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbo_tenbang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckb_auditSelect)
                    .addComponent(jLabel2)
                    .addComponent(cbo_tenbang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ckb_auditInsert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ckb_auditUpdate)
                    .addComponent(btn_apDungTheoBang))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckh_auditDelete)
                    .addComponent(btn_boApDungTheoBang))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đối tượng bảng đang bị giám sát", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jScrollPane3.setViewportView(tb_bang);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_taiDL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_taiDL.setText("Tải lại dữ liệu giám sát");
        btn_taiDL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taiDLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_quyen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btn_taiDL)
                                        .addGap(53, 53, 53)
                                        .addComponent(btn_troVe)
                                        .addGap(9, 9, 9)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_quyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_troVe)
                            .addComponent(btn_taiDL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void loadComboBoxLayTable() {
        ArrayList<Table_POJO> dsdt = FGAAuditTrail_DAO.LayTable();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (Table_POJO dt : dsdt) {
            model.addElement(dt.getTABLE_NAME());
        }
        cbo_tenbang.setModel(model);
        cbo_tenbang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JComboBox cb = (JComboBox) evt.getSource();
            }
        });
    }
    private void btn_ApDungTheoUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ApDungTheoUserActionPerformed
        // TODO add your handling code here:
        String maNV = cbo_maUser.getSelectedItem().toString();
        //String table = txt_tenBang.getText().trim();
        
        if(ckb_selectTB.isSelected()==true){
            String sql = "AUDIT SELECT TABLE BY "+maNV+"";         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){                        
            }
        }
        if(ckb_updateTB.isSelected()==true){
            String sql = "AUDIT UPDATE TABLE BY "+maNV+"";         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){                         
            }
        }
        if(ckb_InsertTB.isSelected()==true){
            String sql = "AUDIT INSERT TABLE BY "+maNV+"";         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){                         
            }
        }
        if(ckb_deleteTB.isSelected()==true){
            String sql = "AUDIT DELETE TABLE BY "+maNV+"";         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){                        
            }
        }
        JOptionPane.showMessageDialog(this,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);
        ArrayList<dba_stmt_audit_opts_POJO> dl1 = GiamSatSA_DAO.LayThongTin_dba_stmt_audit_opts();
        layDuLieu_dba_stmt_audit_opts(dl1);
    }//GEN-LAST:event_btn_ApDungTheoUserActionPerformed

    private void btn_troVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_troVeActionPerformed
        // TODO add your handling code here:
        Managanent_GUI qlnv = new Managanent_GUI();
        qlnv.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_troVeActionPerformed

    private void tb_GiamSatNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_GiamSatNVMouseClicked
        int i = tb_GiamSatNV.getSelectedRow();
        String manv = tb_GiamSatNV.getValueAt(i, 0).toString().trim();

        cbo_maUser.setSelectedItem(manv);
    }//GEN-LAST:event_tb_GiamSatNVMouseClicked

    private void btn_boGiamSatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_boGiamSatActionPerformed
        // TODO add your handling code here:
        String maNV = cbo_maUser.getSelectedItem().toString();
        //String table = txt_tenBang.getText().trim();
        
        if(ckb_selectTB.isSelected()==true){
            String sql = "NOAUDIT SELECT TABLE BY "+maNV+"";         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        if(ckb_updateTB.isSelected()==true){
            String sql = "NOAUDIT UPDATE TABLE BY "+maNV+"";         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        if(ckb_InsertTB.isSelected()==true){
            String sql = "NOAUDIT INSERT TABLE BY "+maNV+"";         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        if(ckb_deleteTB.isSelected()==true){
            String sql = "NOAUDIT DELETE TABLE BY "+maNV+"";         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        JOptionPane.showMessageDialog(this,"Thành công","Thông báo", JOptionPane.INFORMATION_MESSAGE);
        ArrayList<dba_stmt_audit_opts_POJO> dl2 = GiamSatSA_DAO.LayThongTin_dba_stmt_audit_opts();
        layDuLieu_dba_stmt_audit_opts(dl2);
    }//GEN-LAST:event_btn_boGiamSatActionPerformed

    private void btn_apDungTheoBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_apDungTheoBangActionPerformed
        // TODO add your handling code here:
        String tenBang = cbo_tenbang.getSelectedItem().toString();
        String fullTableName = "NV001." + tenBang;  // Thêm tiền tố NV001. vào tên bảng

        if(ckb_auditSelect.isSelected() == true){
            String sql = "AUDIT SELECT ON " + fullTableName;         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        if(ckb_auditInsert.isSelected() == true){
            String sql = "AUDIT INSERT ON " + fullTableName;         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        if(ckb_auditUpdate.isSelected() == true){
            String sql = "AUDIT UPDATE ON " + fullTableName;         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        if(ckh_auditDelete.isSelected() == true){
            String sql = "AUDIT DELETE ON " + fullTableName;         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        ArrayList<DBA_OBJ_AUDIT_OPTS_POJO> DLTB1 = GiamSatSA_DAO.LayThongTin_DBA_OBJ_AUDIT_OPTS();
        layDuLieu_DBA_OBJ_AUDIT_OPTS(DLTB1);
    }//GEN-LAST:event_btn_apDungTheoBangActionPerformed

    private void btn_boApDungTheoBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_boApDungTheoBangActionPerformed
        // TODO add your handling code here:
        String tenBang = cbo_tenbang.getSelectedItem().toString();
        String fullTableName = "NV001." + tenBang;  // Thêm tiền tố NV001. vào tên bảng

        if(ckb_auditSelect.isSelected()==true){
            String sql = "NOAUDIT SELECT ON " + fullTableName;         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        if(ckb_auditInsert.isSelected()==true){
            String sql = "NOAUDIT INSERT ON " + fullTableName;         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        if(ckb_auditUpdate.isSelected()==true){
            String sql = "NOAUDIT UPDATE ON " + fullTableName;         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        if(ckh_auditDelete.isSelected()==true){
            String sql = "NOAUDIT DELETE ON " + fullTableName;         
            if(NhanVien_DAO.ThemXoaSuaNhanVien(sql) == 0 ){            
                //JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);             
            }
        }
        JOptionPane.showMessageDialog(this, "Thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        ArrayList<DBA_OBJ_AUDIT_OPTS_POJO> DLTB1 = GiamSatSA_DAO.LayThongTin_DBA_OBJ_AUDIT_OPTS();
        layDuLieu_DBA_OBJ_AUDIT_OPTS(DLTB1);
    }//GEN-LAST:event_btn_boApDungTheoBangActionPerformed

    private void btn_taiDLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taiDLActionPerformed
        // TODO add your handling code here:
        ArrayList<DBA_AUDIT_TRAIL> dlgs = GiamSatSA_DAO.LayThongTinGiamSat();
        layDuLieuGiamSat(dlgs);
    }//GEN-LAST:event_btn_taiDLActionPerformed

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
            java.util.logging.Logger.getLogger(GiamSatNhanVienBangSA_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GiamSatNhanVienBangSA_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GiamSatNhanVienBangSA_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GiamSatNhanVienBangSA_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GiamSatNhanVienBangSA_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ApDungTheoUser;
    private javax.swing.JButton btn_apDungTheoBang;
    private javax.swing.JButton btn_boApDungTheoBang;
    private javax.swing.JButton btn_boGiamSat;
    private javax.swing.JButton btn_taiDL;
    private javax.swing.JButton btn_troVe;
    private javax.swing.JComboBox<String> cbo_maUser;
    private javax.swing.JComboBox<String> cbo_tenbang;
    private javax.swing.JCheckBox ckb_InsertTB;
    private javax.swing.JCheckBox ckb_auditInsert;
    private javax.swing.JCheckBox ckb_auditSelect;
    private javax.swing.JCheckBox ckb_auditUpdate;
    private javax.swing.JCheckBox ckb_deleteTB;
    private javax.swing.JCheckBox ckb_selectTB;
    private javax.swing.JCheckBox ckb_updateTB;
    private javax.swing.JCheckBox ckh_auditDelete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tb_GiamSatNV;
    private javax.swing.JTable tb_bang;
    private javax.swing.JTable tb_dba_stmt_audit_opts;
    private javax.swing.JTextField txt_quyen;
    // End of variables declaration//GEN-END:variables
}
