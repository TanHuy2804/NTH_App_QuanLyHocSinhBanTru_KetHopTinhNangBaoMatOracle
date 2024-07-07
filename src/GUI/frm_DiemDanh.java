/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

/**
 *
 * @author SONDAY
 */
import DAO.DBConnect;
import DAO.HocSinh_DAO;
import DAO.Lop_DAO;
import DAO.DiemDanh_DAO;
import POJO.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Vector;

public class frm_DiemDanh extends javax.swing.JFrame {

    /**
     * Creates new form frm_DiemDanh
     */
    Vector tblData_HocSinh = new Vector();
    Vector tblTitle_HocSinh = new Vector();
    DefaultTableModel tblModel_HocSinh;

    Vector tblData_DiemDanh = new Vector();
    Vector tblTitle_DiemDanh = new Vector();
    DefaultTableModel tblModel_DiemDanh;

    static ArrayList<HocSinh> ds_hs = HocSinh_DAO.LayThongTin_HocSinh();
    static ArrayList<DiemDanh> ds_dd = DiemDanh_DAO.LayThongTin_DiemDanh();

    public frm_DiemDanh() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Điểm danh");

        // Đặt giá trị mặc định cho txt_ngay_diemdanh là SYSDATE
        txt_ngay_diemdanh.setText(getCurrentDate());

        txt_manv.setText(Login_GUI.loggedInUser);

        hienthi_hocsinh();
        laydulieu_hocsinh(ds_hs);

        hienthi_diemdanh();
        laydulieu_diemdanh(ds_dd);

        this.txt_tenchucvu.setText(Login_GUI.chucvu_User);
        this.txt_tenchucvu.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_tenchucvu.setEditable(false);

        // Tạo mô hình cho cboTrangThaiDiemDanh
        DefaultComboBoxModel<String> modelTrangThaiDiemDanh = new DefaultComboBoxModel<>();
        //addNullItemToComboBox(modelTrangThaiDiemDanh);
        // Thêm các trạng thái khác vào combobox
        modelTrangThaiDiemDanh.addElement("Vắng");
        modelTrangThaiDiemDanh.addElement("Có mặt");

        // Thiết lập mô hình cho combobox
        setComboBoxModel(cbo_trangthai_diemdanh, modelTrangThaiDiemDanh);
        cbo_trangthai_diemdanh.setSelectedItem("Có mặt");

        ArrayList<String> maLopList = Lop_DAO.get_MaLopList_Lop();
        DefaultComboBoxModel<String> model_LopList = new DefaultComboBoxModel<>(maLopList.toArray(new String[0]));
        model_LopList.insertElementAt("Tất cả lớp", 0);
        cbo_danhsach_lop.setModel(model_LopList);
        cbo_danhsach_lop.setSelectedItem("Tất cả lớp");

        // Thêm ListSelectionListener cho bảng table_danhsach_diemdanh
        table_danhsach_hocsinh.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Xử lý sự kiện khi dòng được chọn thay đổi
                table_danhsach_hocsinhMouseClicked(null); // Gọi lại phương thức xử lý sự kiện khi dòng được chọn
            }
        });

        txt_ngay_diemdanh.setEditable(false);
        txt_mahs.setEditable(false);
        txt_tenhs.setEditable(false);
        txt_malop.setEditable(false);
        txt_manv.setEditable(false);

        txt_ngay_diemdanh.setHorizontalAlignment(SwingConstants.CENTER);
        txt_mahs.setHorizontalAlignment(SwingConstants.CENTER);
        txt_tenhs.setHorizontalAlignment(SwingConstants.CENTER);
        txt_malop.setHorizontalAlignment(SwingConstants.CENTER);
        txt_manv.setHorizontalAlignment(SwingConstants.CENTER);

        startClockThread();
    }

    public void hienthi_hocsinh() {
        tblTitle_HocSinh.add("Mã học sinh");
        tblTitle_HocSinh.add("Tên học sinh");
        tblTitle_HocSinh.add("Giới tính");
        tblTitle_HocSinh.add("Ngày sinh");
        tblTitle_HocSinh.add("Địa chỉ");
        tblTitle_HocSinh.add("Ngày vào trường");
        tblTitle_HocSinh.add("Tình trạng sức khỏe");
        tblTitle_HocSinh.add("Mã lớp");
        tblTitle_HocSinh.add("Mã phụ huynh");

    }

    public void laydulieu_hocsinh(ArrayList<HocSinh> ds_hs) {
        tblData_HocSinh.removeAllElements();
        for (HocSinh hs : ds_hs) {
            Vector v = new Vector();
            v.add(hs.getMaHS());
            v.add(hs.getTenHS());
            v.add(hs.getGioiTinh());
            v.add(hs.getNgaySinh());
            v.add(hs.getDiaChi());
            v.add(hs.getNgayVaoTruong());
            v.add(hs.getTinhTrangSucKhoe());
            v.add(hs.getMaLop());
            v.add(hs.getMaPH());

            tblData_HocSinh.add(v);
        }
        table_danhsach_hocsinh.setModel(new DefaultTableModel(tblData_HocSinh, tblTitle_HocSinh));
    }

    public void hienthi_diemdanh() {
        tblTitle_DiemDanh.add("Ngày điểm danh");
        tblTitle_DiemDanh.add("Mã học sinh");
        tblTitle_DiemDanh.add("Tên học sinh");
        tblTitle_DiemDanh.add("Trạng thái điểm danh");
        tblTitle_DiemDanh.add("Ghi chú");
        tblTitle_DiemDanh.add("Mã giáo viên");
        tblTitle_DiemDanh.add("Mã lớp");
    }

    public void laydulieu_diemdanh(ArrayList<DiemDanh> ds_dd) {
        tblData_DiemDanh.removeAllElements();
        for (DiemDanh dd : ds_dd) {
            Vector v = new Vector();
            v.add(dd.getNgayDiemDanh());
            v.add(dd.getMaHS());
            v.add(dd.getTenHS());
            v.add(dd.getTrangThaiDiemDanh());
            v.add(dd.getGhiChu());
            v.add(dd.getMaNV());
            v.add(dd.getMaLop());

            tblData_DiemDanh.add(v);
        }
        table_danhsach_diemdanh.setModel(new DefaultTableModel(tblData_DiemDanh, tblTitle_DiemDanh));

        // Thiết lập renderer cho cột trạng thái để tô màu nền
        TableColumn trangThaiColumn = table_danhsach_diemdanh.getColumnModel().getColumn(3);
        trangThaiColumn.setCellRenderer(new CustomCellRenderer(table_danhsach_diemdanh));

        table_danhsach_diemdanh.updateUI();
    }

    // Hàm để thiết lập mô hình cho JComboBox
    private void setComboBoxModel(JComboBox<String> comboBox, DefaultComboBoxModel<String> model) {
        comboBox.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        cbo_danhsach_lop = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_danhsach_diemdanh = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        cbo_trangthai_diemdanh = new javax.swing.JComboBox<>();
        txt_ngay_diemdanh = new javax.swing.JTextField();
        txt_ghichu = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_mahs = new javax.swing.JTextField();
        txt_manv = new javax.swing.JTextField();
        txt_malop = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_tenhs = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_danhsach_hocsinh = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btn_refresh = new javax.swing.JButton();
        btn_thoat = new javax.swing.JButton();
        btn_luu_diemdanh = new javax.swing.JButton();
        btn_hienthi_diemdanh = new javax.swing.JButton();
        date_chooser = new com.toedter.calendar.JDateChooser();
        btn_updatediemdanh = new javax.swing.JButton();
        btn_diemdanhbangkhuonmat = new javax.swing.JButton();
        lbl_realtime = new javax.swing.JLabel();
        txt_tenchucvu = new javax.swing.JTextField();
        btn_quaylai = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chọn lớp:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 16), new java.awt.Color(0, 0, 204))); // NOI18N

        cbo_danhsach_lop.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_danhsach_lopItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbo_danhsach_lop, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(cbo_danhsach_lop, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách điểm danh:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 16), new java.awt.Color(0, 0, 204))); // NOI18N

        table_danhsach_diemdanh.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(table_danhsach_diemdanh);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm danh:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 16), new java.awt.Color(0, 0, 204))); // NOI18N

        txt_ngay_diemdanh.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_ngay_diemdanh.setForeground(new java.awt.Color(255, 0, 51));

        txt_ghichu.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_ghichu.setForeground(new java.awt.Color(255, 0, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Mã học sinh:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Ngày điểm danh:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Trạng thái điểm danh:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Mã nhân viên:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Ghi chú:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Mã lớp:");

        txt_mahs.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_mahs.setForeground(new java.awt.Color(255, 0, 51));

        txt_manv.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_manv.setForeground(new java.awt.Color(255, 0, 51));

        txt_malop.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_malop.setForeground(new java.awt.Color(255, 0, 51));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Tên học sinh:");

        txt_tenhs.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_tenhs.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_mahs)
                    .addComponent(txt_ngay_diemdanh)
                    .addComponent(txt_tenhs)
                    .addComponent(cbo_trangthai_diemdanh, 0, 149, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_ghichu, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_manv, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                        .addComponent(txt_malop)))
                .addGap(10, 10, 10))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_malop, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txt_ngay_diemdanh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_manv, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txt_mahs, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ghichu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txt_tenhs, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbo_trangthai_diemdanh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin học sinh:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 16), new java.awt.Color(0, 0, 204))); // NOI18N

        table_danhsach_hocsinh.setModel(new javax.swing.table.DefaultTableModel(
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
        table_danhsach_hocsinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_danhsach_hocsinhMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_danhsach_hocsinh);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("ĐIỂM DANH");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tuỳ chọn:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N

        btn_refresh.setBackground(new java.awt.Color(153, 255, 255));
        btn_refresh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_thoat.setBackground(new java.awt.Color(153, 255, 255));
        btn_thoat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_thoat.setText("Thoát");
        btn_thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thoatActionPerformed(evt);
            }
        });

        btn_luu_diemdanh.setBackground(new java.awt.Color(153, 255, 255));
        btn_luu_diemdanh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_luu_diemdanh.setText("Thêm điểm danh");
        btn_luu_diemdanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_luu_diemdanhActionPerformed(evt);
            }
        });

        btn_hienthi_diemdanh.setText("Xem điểm danh của ngày");
        btn_hienthi_diemdanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hienthi_diemdanhActionPerformed(evt);
            }
        });

        btn_updatediemdanh.setBackground(new java.awt.Color(153, 255, 255));
        btn_updatediemdanh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_updatediemdanh.setText("Sửa điểm danh");
        btn_updatediemdanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatediemdanhActionPerformed(evt);
            }
        });

        btn_diemdanhbangkhuonmat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/xacthuc2.jpg"))); // NOI18N
        btn_diemdanhbangkhuonmat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_diemdanhbangkhuonmatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(date_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_hienthi_diemdanh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_thoat, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_luu_diemdanh, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_updatediemdanh)
                .addGap(39, 39, 39)
                .addComponent(btn_diemdanhbangkhuonmat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(date_chooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_hienthi_diemdanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_thoat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_luu_diemdanh, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_updatediemdanh, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(btn_diemdanhbangkhuonmat, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        lbl_realtime.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_realtime.setForeground(new java.awt.Color(255, 0, 51));
        lbl_realtime.setText("Time");

        txt_tenchucvu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_tenchucvu.setForeground(new java.awt.Color(255, 0, 51));

        btn_quaylai.setText("Trở về");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_quaylai, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lbl_realtime, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_tenchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(93, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(666, 666, 666)
                    .addComponent(jLabel4)
                    .addContainerGap(749, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_realtime)
                    .addComponent(txt_tenchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_quaylai, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addGap(72, 72, 72))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(jLabel4)
                    .addContainerGap(715, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbo_danhsach_lopItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_danhsach_lopItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedLop = cbo_danhsach_lop.getSelectedItem().toString();
            String selectedHS = cbo_danhsach_lop.getSelectedItem().toString();

            if ("Tất cả lớp".equals(selectedLop)) {
                // Hiển thị danh sách điểm danh của tất cả các lớp
                ArrayList<DiemDanh> ds_dd_tatca_lop = DiemDanh_DAO.LayThongTin_DiemDanh();
                laydulieu_diemdanh(ds_dd_tatca_lop);

                ArrayList<HocSinh> ds_hs_tatca_lop = HocSinh_DAO.LayThongTin_HocSinh();
                laydulieu_hocsinh(ds_hs_tatca_lop);

                //JOptionPane.showMessageDialog(this, "Hiển thị danh sách điểm danh & học sinh của tất cả các lớp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Lấy mã lớp được chọn từ combobox
                String maLop = selectedLop;
                String maHocSinh = selectedHS;

                // Tạo ArrayList để lưu danh sách điểm danh của lớp được chọn
                ArrayList<DiemDanh> ds_dd_theo_lop = DiemDanh_DAO.LayThongTin_DiemDanhTheoLop_DiemDanh(maLop);
                ArrayList<HocSinh> ds_hs_theo_lop = HocSinh_DAO.LayThongTin_HocSinhTheoLop_HocSinh(maHocSinh);

                // Hiển thị danh sách điểm danh của lớp đó
                laydulieu_diemdanh(ds_dd_theo_lop);
                laydulieu_hocsinh(ds_hs_theo_lop);

                // Hiển thị thông báo cho người dùng
                JOptionPane.showMessageDialog(this, "Hiển thị danh sách điểm danh & học sinh của lớp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_cbo_danhsach_lopItemStateChanged

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        // Set the text of text fields to an empty string
        txt_mahs.setText("");
        txt_tenhs.setText("");
        txt_malop.setText("");
        txt_ghichu.setText("");

        cbo_danhsach_lop.setSelectedItem("Tất cả lớp");

        // Refresh data in both tables
        ArrayList<HocSinh> ds_hs_refreshed = HocSinh_DAO.LayThongTin_HocSinh();
        laydulieu_hocsinh(ds_hs_refreshed);

        ArrayList<DiemDanh> ds_dd_refreshed = DiemDanh_DAO.LayThongTin_DiemDanh();
        laydulieu_diemdanh(ds_dd_refreshed);


    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_luu_diemdanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_luu_diemdanhActionPerformed
        // TODO add your handling code here:
        // Lấy thông tin từ các thành phần giao diện
        String ngayDiemDanh = txt_ngay_diemdanh.getText();
        String maHS = txt_mahs.getText();
        String tenHS = txt_tenhs.getText();
        String trangThaiDiemDanh = cbo_trangthai_diemdanh.getSelectedItem().toString();
        String ghiChu = txt_ghichu.getText();
        String maNV = txt_manv.getText();
        String maLop = txt_malop.getText();

        // Kiểm tra nếu giá trị của combobox là "", gán giá trị null
        trangThaiDiemDanh = (trangThaiDiemDanh != null && trangThaiDiemDanh.equals("")) ? null : trangThaiDiemDanh;

        try {
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục
            String sql = "{call NV001.ThemDiemDanh(?, ?, ?, ?, ?, ?, ?)}";

            // Tạo PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Chuyển đổi ngayDiemDanh từ String sang java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(ngayDiemDanh);

                // Đặt các tham số cho câu lệnh SQL
                preparedStatement.setDate(1, sqlDate);
                preparedStatement.setString(2, maHS);
                preparedStatement.setString(3, tenHS);
                preparedStatement.setString(4, trangThaiDiemDanh);
                preparedStatement.setString(5, ghiChu);
                preparedStatement.setString(6, maNV);
                preparedStatement.setString(7, maLop);

                // Thực thi câu lệnh SQL
                preparedStatement.executeUpdate();
            }

            // Hiển thị thông báo thành công
            //JOptionPane.showMessageDialog(this, "Cập nhật trạng thái điểm danh thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            // Lấy lớp được chọn từ combobox
            String selectedLop = cbo_danhsach_lop.getSelectedItem().toString();

            // Lấy danh sách điểm danh của lớp đó
            ArrayList<DiemDanh> ds_dd_theo_lop = DiemDanh_DAO.LayThongTin_DiemDanhTheoLop_DiemDanh(selectedLop);

            // Hiển thị danh sách điểm danh của lớp đó
            laydulieu_diemdanh(ds_dd_theo_lop);
        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Bạn đang muốn sửa thông tin này? Hãy nhấn Cập nhật điểm danh ", "Lời chú ý", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_luu_diemdanhActionPerformed

    private void btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thoatActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btn_thoatActionPerformed

    private void table_danhsach_hocsinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_danhsach_hocsinhMouseClicked
        // TODO add your handling code here:
        // Thêm ListSelectionListener cho bảng table_danhsach_hocsinh
        ListSelectionModel rowSelectionModel = table_danhsach_hocsinh.getSelectionModel();
        rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel colSelectionModel = table_danhsach_hocsinh.getColumnModel().getSelectionModel();
        colSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Lấy dòng được chọn trong bảng
        int selectedRow = table_danhsach_hocsinh.getSelectedRow();

        // Kiểm tra xem đã chọn dòng nào chưa
        if (selectedRow != -1) {
            // Lấy thông tin từ bảng
            String maHS = table_danhsach_hocsinh.getValueAt(selectedRow, 0).toString();
            String tenHS = table_danhsach_hocsinh.getValueAt(selectedRow, 1).toString();
            String maLop = table_danhsach_hocsinh.getValueAt(selectedRow, 7).toString();

            // Hiển thị thông tin lên các JTextField
            txt_mahs.setText(maHS);
            txt_tenhs.setText(tenHS);
            txt_malop.setText(maLop);

        }
    }//GEN-LAST:event_table_danhsach_hocsinhMouseClicked

    private void btn_hienthi_diemdanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hienthi_diemdanhActionPerformed
        // TODO add your handling code here:
        // Lấy ngày từ JDateChooser
        java.util.Date selectedDate = date_chooser.getDate();

        // Kiểm tra xem ngày có được chọn không
        if (selectedDate != null) {
            // Chuyển định dạng ngày thành chuỗi theo định dạng yyyy-MM-dd
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);

            // Format ngày theo định dạng yyyy-MM-dd
            String formattedDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(selectedDate), inputFormatter).format(outputFormatter);

            // Hiển thị thông báo với ngày đã chọn
            JOptionPane.showMessageDialog(this, "Đã chọn ngày: " + formattedDate, "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Lấy danh sách điểm danh của ngày và lớp đã chọn
            String selectedLop = cbo_danhsach_lop.getSelectedItem().toString();
            ArrayList<DiemDanh> ds_dd_theo_ngay_va_lop = DiemDanh_DAO.LayThongTin_DiemDanhTheoNgayVaLop_DiemDanh(formattedDate, selectedLop);

            // Hiển thị danh sách điểm danh
            laydulieu_diemdanh(ds_dd_theo_ngay_va_lop);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày trước khi thực hiện.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_hienthi_diemdanhActionPerformed

    private void btn_updatediemdanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatediemdanhActionPerformed
        // TODO add your handling code here:
        String ngayDiemDanh = txt_ngay_diemdanh.getText();
        String maHS = txt_mahs.getText();
        String tenHS = txt_tenhs.getText();
        String trangThaiDiemDanh = cbo_trangthai_diemdanh.getSelectedItem().toString();
        String ghiChu = txt_ghichu.getText();
        String maNV = txt_manv.getText();
        String maLop = txt_malop.getText();

        // Kiểm tra nếu giá trị của combobox là "", gán giá trị null
        trangThaiDiemDanh = (trangThaiDiemDanh != null && trangThaiDiemDanh.equals("")) ? null : trangThaiDiemDanh;

        try {
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục
            String sql = "{call NV001.UpdateDiemDanh(?, ?, ?, ?, ?, ?, ?)}";

            // Tạo PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Chuyển đổi ngayDiemDanh từ String sang java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(ngayDiemDanh);

                // Đặt các tham số cho câu lệnh SQL
                preparedStatement.setDate(1, sqlDate);
                preparedStatement.setString(2, maHS);
                preparedStatement.setString(3, tenHS);
                preparedStatement.setString(4, trangThaiDiemDanh);
                preparedStatement.setString(5, ghiChu);
                preparedStatement.setString(6, maNV);
                preparedStatement.setString(7, maLop);
                //preparedStatement.setString(8, maNV);
                // Thực thi câu lệnh SQL
                preparedStatement.executeUpdate();
            }

            // Hiển thị thông báo thành công
            //JOptionPane.showMessageDialog(this, "Cập nhật trạng thái điểm danh thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            // Lấy lớp được chọn từ combobox
            String selectedLop = cbo_danhsach_lop.getSelectedItem().toString();

            // Lấy danh sách điểm danh của lớp đó
            ArrayList<DiemDanh> ds_dd_theo_lop = DiemDanh_DAO.LayThongTin_DiemDanhTheoLop_DiemDanh(selectedLop);

            // Hiển thị danh sách điểm danh của lớp đó
            laydulieu_diemdanh(ds_dd_theo_lop);
        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_updatediemdanhActionPerformed

    private void btn_quaylaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quaylaiActionPerformed
        // TODO add your handling code here:
        Managanent_GUI qlc = new Managanent_GUI();
        qlc.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_quaylaiActionPerformed

    private static void runCommand(String pythonScriptPath, String pythonScriptFolder, String pythonCommand) {
        try {
            // Tạo một ProcessBuilder           
            ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand, pythonScriptPath);
            // Đặt thư mục làm việc cho ProcessBuilder

            processBuilder.directory(new File(pythonScriptFolder));
            // Redirect standard output to Java application
            processBuilder.redirectErrorStream(true);

            // Bắt đầu quá trình
            Process process = processBuilder.start();

            // Đọc output từ quá trình
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // Đợi quá trình kết thúc
            int exitCode = process.waitFor();
            System.out.println("Exited with error code " + exitCode);

        } catch (IOException | InterruptedException e) {
            //e.printStackTrace();
        }
    }

    private void btn_diemdanhbangkhuonmatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_diemdanhbangkhuonmatActionPerformed
        // TODO add your handling code here:
        // Hiển thị hộp thoại Yes/No
        int choice = JOptionPane.showConfirmDialog(this, "Sử dụng điểm danh tự động !", "Xác nhận", JOptionPane.YES_NO_OPTION);

        // Kiểm tra lựa chọn của người dùng
        if (choice == JOptionPane.YES_OPTION) {
            String pythonScriptPath = "C:\\Users\\SONDAY\\XacThucKhuonMat\\MiAI_FaceRecog_3\\src\\face_rec_cam.py";
            String pythonScriptFolder = "C:\\Users\\SONDAY\\XacThucKhuonMat\\MiAI_FaceRecog_3";
            String filePath = "C:\\Users\\SONDAY\\XacThucKhuonMat\\MiAI_FaceRecog_3\\src\\xacthuckhuongmat.txt";

            // Lệnh Python
            String pythonCommand = "python";
            runCommand(pythonScriptPath, pythonScriptFolder, pythonCommand);

            String maHS = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                StringBuilder content = new StringBuilder();
                String line;

                // Đọc từng dòng và thêm vào StringBuilder
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                maHS = content.toString().trim();

                // Hiển thị nội dung file
                //JOptionPane.showMessageDialog(rootPane, thongtin);
                System.out.println("Mã hs: " + maHS);
            } catch (IOException e) {
                //e.printStackTrace();
                //JOptionPane.showMessageDialog(this, "Không thể đọc file", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            String trangThaiDiemDanh = "Có mặt";
            String ghiChu = "";
            String maNV = txt_manv.getText();
            String ngayDiemDanh = txt_ngay_diemdanh.getText();

            String maLop = "";
            String tenHS = "";

            if (maHS.equals("KHÔNG XÁC ĐỊNH") || maHS.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Điểm danh không thành công...");
                return;
            } else {
                maLop = DAO.DiemDanh_DAO.LayMaLop(maHS);
                System.out.println("Mã lớp: " + maLop);
                tenHS = DAO.DiemDanh_DAO.LayTenHS(maHS);
                System.out.println("Tên học sinh: " + tenHS);

                if (maLop.isEmpty() || tenHS.isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, "Lỗi hệ thống !");
                    return;
                }

                int kq = DAO.DiemDanh_DAO.themDiemDanh_xacthuckhuonmat(maHS, tenHS, ngayDiemDanh, trangThaiDiemDanh, ghiChu, maLop, maNV);
                if (kq == 1) {
                    JOptionPane.showMessageDialog(rootPane, "Điểm danh thành công...");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Điểm danh không thành công...");
                }
            }
        } else {
            // Người dùng chọn No, không làm gì cả
        }
    }//GEN-LAST:event_btn_diemdanhbangkhuonmatActionPerformed

    // Hàm lấy ngày hiện tại dưới dạng chuỗi với định dạng "yyyy-MM-dd"
    private String getCurrentDate() {
        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }

    public class CustomCellRenderer extends DefaultTableCellRenderer {

        private final JTable table;

        public CustomCellRenderer(JTable table) {
            this.table = table;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String trangThai = (String) value;
            if ("Có mặt".equals(trangThai)) {
                component.setBackground(Color.GREEN);
            } else {
                if (isSelected && table.isCellSelected(row, column)) {
                    component.setBackground(table.getSelectionBackground());
                } else {
                    component.setBackground(table.getBackground());
                }
            }

            return component;
        }
    }

    // Class Clock để thực hiện luồng cập nhật giờ
    class Clock implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    // Lấy thời gian hệ thống
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");  // định dạng thời gian theo định dạng "HH:mm:ss" (giờ:phút:giây).
                    String formattedTime = now.format(formatter);

                    // Hiển thị giờ trên lbl_realtime
                    updateRealTimeLabel(formattedTime);

                    // Ngủ 1 giây trước khi cập nhật lại giờ
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

    // Phương thức để cập nhật giờ trên lbl_realtime
    private void updateRealTimeLabel(String time) {
        lbl_realtime.setText(time);
    }

    // Phương thức để bắt đầu luồng cập nhật giờ
    private void startClockThread() {
        Thread clockThread = new Thread(new Clock());
        clockThread.start();
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
            java.util.logging.Logger.getLogger(frm_DiemDanh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_DiemDanh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_DiemDanh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_DiemDanh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_DiemDanh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_diemdanhbangkhuonmat;
    private javax.swing.JButton btn_hienthi_diemdanh;
    private javax.swing.JButton btn_luu_diemdanh;
    private javax.swing.JButton btn_quaylai;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_thoat;
    private javax.swing.JButton btn_updatediemdanh;
    private javax.swing.JComboBox<String> cbo_danhsach_lop;
    private javax.swing.JComboBox<String> cbo_trangthai_diemdanh;
    private com.toedter.calendar.JDateChooser date_chooser;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_realtime;
    private javax.swing.JTable table_danhsach_diemdanh;
    private javax.swing.JTable table_danhsach_hocsinh;
    private javax.swing.JTextField txt_ghichu;
    private javax.swing.JTextField txt_mahs;
    private javax.swing.JTextField txt_malop;
    private javax.swing.JTextField txt_manv;
    private javax.swing.JTextField txt_ngay_diemdanh;
    private javax.swing.JTextField txt_tenchucvu;
    private javax.swing.JTextField txt_tenhs;
    // End of variables declaration//GEN-END:variables
}
