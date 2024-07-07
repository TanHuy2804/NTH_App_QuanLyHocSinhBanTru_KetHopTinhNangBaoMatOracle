/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import POJO.*;
import DAO.*;
import static DAO.v2_ChiTietDeThi_DAO.v2_LayThongTin_4ChiTietDeThi;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.util.Vector;

/**
 *
 * @author SONDAY
 */
public class frm_TaoDeThi extends javax.swing.JFrame {

    /**
     * Creates new form frm_TaoDeThi
     */
    Vector tblData_DeThi = new Vector();
    Vector tblTitle_DeThi = new Vector();
    DefaultTableModel tblModel_DeThi;
    static ArrayList<DeThi_POJO> ds_dethi = DeThi_DAO.LayThongTin_DeThi();

    public frm_TaoDeThi() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Tạo đề thi");
        txt_ma_GV.setText(Login_GUI.loggedInUser);
        txt_ngaysoan.setText(getCurrentDate());

        this.txt_ma_GV.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_ma_GV.setEditable(false);
        this.txt_ngaysoan.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_ngaysoan.setEditable(false);
        this.txt_madethi.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_madethi.setEditable(false);

        this.txt_madethi_sinhtudong.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_madethi_sinhtudong.setEditable(false);

        ArrayList<String> ma_MonHocList = MonHoc_DAO.get_MonHocList_MonHoc();
        DefaultComboBoxModel<String> model_MonHocList = new DefaultComboBoxModel<>(ma_MonHocList.toArray(new String[0]));
        cbo_ma_monhoc.setModel(model_MonHocList);

        // Tạo mô hình cho cboTrangThai
        DefaultComboBoxModel<String> modelTrangThaiDeThi = new DefaultComboBoxModel<>();

        // Thêm các trạng thái khác vào combobox
        modelTrangThaiDeThi.addElement("Đang Soạn");
        modelTrangThaiDeThi.addElement("Hoàn Thành");

        // Thiết lập mô hình cho combobox
        setComboBoxModel(cbo_trangthai, modelTrangThaiDeThi);
        cbo_trangthai.setSelectedItem("Đang Soạn");

        txt_madethi.setEditable(false);

        hienthi_dethi();
        //laydulieu_dethi(ds_dethi);

        ArrayList<DeThi_POJO> ds_dethi_refreshed = DeThi_DAO.LayThongTin_DeThi();
        laydulieu_dethi(ds_dethi_refreshed);

        this.txt_tenchucvu.setText(Login_GUI.chucvu_User);
        this.txt_tenchucvu.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_tenchucvu.setEditable(false);

        // Lấy mã đề thi cuối cùng từ cơ sở dữ liệu
        String lastMaDeThi = DeThi_DAO.get_MaDeThi_CuoiCung();
        if (lastMaDeThi == null) {
            lastMaDeThi = "DETHI-0001"; // Nếu không có mã nào trong cơ sở dữ liệu, sử dụng mã mặc định DETHI-0001
        } else {
            // Tạo mã đề thi mới bằng cách tăng số đếm trong mã đề thi cuối cùng
            int lastNum = 0;
            if (lastMaDeThi.length() > 6) {
                lastNum = Integer.parseInt(lastMaDeThi.substring(6)); // Lấy số đếm từ mã câu hỏi cuối cùng bằng cách loại bỏ ký tự "DETHI-"
            }
            lastNum++; // Tăng số đếm lên 1
            // Format lại mã đề thi mới
            String newNum = String.format("%04d", lastNum); // Định dạng số đếm thành chuỗi có độ dài 4 ký tự, bắt đầu bằng các số 0 nếu cần thiết
            lastMaDeThi = "DETHI-" + newNum; // Ghép số đếm vào chuỗi "DETHI-" để tạo mã đề thi mới
        }
        txt_madethi_sinhtudong.setText(lastMaDeThi);

    }

    // Hàm để thiết lập mô hình cho JComboBox
    private void setComboBoxModel(JComboBox<String> comboBox, DefaultComboBoxModel<String> model) {
        comboBox.setModel(model);
    }

    // Hàm lấy ngày hiện tại dưới dạng chuỗi với định dạng "yyyy-MM-dd"
    private String getCurrentDate() {
        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }

    TableCellRenderer statusCellRenderer = new TableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setOpaque(true); // Đảm bảo rằng nền của label có thể được vẽ

            // Thiết lập giá trị và màu nền dựa trên giá trị của cột "TRANGTHAI"
            String status = (String) value;
            label.setText(status);
            if ("Hoàn Thành".equals(status)) {
                label.setBackground(Color.GREEN);
            } else {
                label.setBackground(Color.WHITE); // Màu nền mặc định cho các giá trị khác
            }

            return label;
        }
    };

    public void hienthi_dethi() {
        tblTitle_DeThi.add("Mã đề thi");
        tblTitle_DeThi.add("Ngày soạn");
        tblTitle_DeThi.add("Số lượng câu hỏi");
        tblTitle_DeThi.add("Trạng thái");
        tblTitle_DeThi.add("Mã giáo viên");
        tblTitle_DeThi.add("Mã môn học");
    }

    public void laydulieu_dethi(ArrayList<DeThi_POJO> ds_dethi) {
        tblData_DeThi.removeAllElements();
        for (DeThi_POJO dt : ds_dethi) {
            Vector v = new Vector();
            v.add(dt.getMaDeThi());
            v.add(dt.getNgaySoanDe());
            v.add(dt.getSoluongCauHoi());
            v.add(dt.getTrangThai());
            v.add(dt.getMaNV());
            v.add(dt.getMaMH());
            tblData_DeThi.add(v);
        }
        table_danhsach_dethi.setModel(new DefaultTableModel(tblData_DeThi, tblTitle_DeThi));

        TableColumn statusColumn = table_danhsach_dethi.getColumnModel().getColumn(3); // 3 là chỉ số của cột "TRANGTHAI"
        statusColumn.setCellRenderer(statusCellRenderer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_soluongcauhoi = new javax.swing.JTextField();
        txt_ma_GV = new javax.swing.JTextField();
        btn_taodethi = new javax.swing.JButton();
        btn_suadethi = new javax.swing.JButton();
        btn_xoadethi = new javax.swing.JButton();
        cbo_ma_monhoc = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cbo_trangthai = new javax.swing.JComboBox<>();
        txt_ngaysoan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btn_refresh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_danhsach_dethi = new javax.swing.JTable();
        btn_move_taocauhoi = new javax.swing.JButton();
        btn_quaylai = new javax.swing.JButton();
        txt_madethi_sinhtudong = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_madethi = new javax.swing.JTextField();
        txt_tenchucvu = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin đề thi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Số lượng câu hỏi:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Mã GV soạn đề:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Mã môn học:");

        txt_soluongcauhoi.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_soluongcauhoi.setForeground(new java.awt.Color(255, 0, 51));

        txt_ma_GV.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_ma_GV.setForeground(new java.awt.Color(255, 0, 51));

        btn_taodethi.setBackground(new java.awt.Color(153, 255, 255));
        btn_taodethi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_taodethi.setText("Tạo đề thi");
        btn_taodethi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taodethiActionPerformed(evt);
            }
        });

        btn_suadethi.setBackground(new java.awt.Color(153, 255, 255));
        btn_suadethi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_suadethi.setText("Sửa đề thi");
        btn_suadethi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suadethiActionPerformed(evt);
            }
        });

        btn_xoadethi.setBackground(new java.awt.Color(153, 255, 255));
        btn_xoadethi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_xoadethi.setText("Xoá đề thi");
        btn_xoadethi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoadethiActionPerformed(evt);
            }
        });

        cbo_ma_monhoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Trạng thái:");

        cbo_trangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txt_ngaysoan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_ngaysoan.setForeground(new java.awt.Color(255, 0, 51));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Ngày soạn:");

        btn_refresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh3.png"))); // NOI18N
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(txt_ngaysoan, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbo_trangthai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_ma_GV, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbo_ma_monhoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_soluongcauhoi)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_suadethi, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addComponent(btn_xoadethi, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_taodethi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ngaysoan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ma_GV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_soluongcauhoi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_ma_monhoc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_trangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_taodethi, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_suadethi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_xoadethi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_refresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("TẠO ĐỀ KIỂM TRA TRẮC NGHIỆM");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách đề thi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        table_danhsach_dethi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_danhsach_dethiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_danhsach_dethi);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btn_move_taocauhoi.setBackground(new java.awt.Color(153, 255, 255));
        btn_move_taocauhoi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_move_taocauhoi.setText("TẠO CÂU HỎI");
        btn_move_taocauhoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_move_taocauhoiActionPerformed(evt);
            }
        });

        btn_quaylai.setText("Trở về");
        btn_quaylai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quaylaiActionPerformed(evt);
            }
        });

        txt_madethi_sinhtudong.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_madethi_sinhtudong.setForeground(new java.awt.Color(255, 0, 51));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Mã đề thi được tạo tự động:");

        txt_madethi.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_madethi.setForeground(new java.awt.Color(255, 0, 51));

        txt_tenchucvu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_tenchucvu.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_madethi_sinhtudong, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_madethi, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_move_taocauhoi, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(183, 183, 183)
                        .addComponent(txt_tenchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btn_quaylai)))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_tenchucvu, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                            .addComponent(btn_quaylai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_madethi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_move_taocauhoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_madethi_sinhtudong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_taodethiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taodethiActionPerformed
        // TODO add your handling code here:
        //LÀM THÊM
        String maDeThi = txt_madethi_sinhtudong.getText();

        // Lấy thông tin từ các thành phần giao diện
        String ngaySoan = txt_ngaysoan.getText();
        String maGV = txt_ma_GV.getText();
        //String maDeThi = txt_madethi.getText();
        String soluongCauHoi = txt_soluongcauhoi.getText();
        String maMH = cbo_ma_monhoc.getSelectedItem().toString();
        String trangThai = cbo_trangthai.getSelectedItem().toString();

        // Kiểm tra các trường thông tin đầy đủ
        if (ngaySoan.isEmpty() || maGV.isEmpty() || maDeThi.isEmpty() || soluongCauHoi.isEmpty() || maMH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu có trường thông tin trống
        }

        // Kiểm tra định dạng của soluongCauHoi không được <= 0
        try {
            int soLuong = Integer.parseInt(soluongCauHoi);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng câu hỏi phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức nếu soluongCauHoi không hợp lệ
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng câu hỏi không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu soluongCauHoi không phải là số
        }

        try {
            // Kiểm tra xem mã đề thi đã tồn tại trong cơ sở dữ liệu chưa
            if (DeThi_DAO.KiemTraTonTaiMaDeThi(maDeThi)) {
                // Nếu mã đề thi đã tồn tại, hiển thị thông báo lỗi
                JOptionPane.showMessageDialog(this, "Mã đề thi đã tồn tại, vui lòng nhập mã khác", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức để không thực hiện các bước tiếp theo
            }

            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục
            String sql = "{call NV001.E1_ThemDeThi(?, ?, ?, ?, ?, ?)}";

            // Tạo PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Chuyển đổi ngaySoan từ String sang java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(ngaySoan);

                // Đặt các tham số cho câu lệnh SQL
                preparedStatement.setString(1, maDeThi);
                preparedStatement.setDate(2, sqlDate);
                preparedStatement.setString(3, soluongCauHoi);
                preparedStatement.setString(4, trangThai);
                preparedStatement.setString(5, maGV);
                preparedStatement.setString(6, maMH);

                // Thực thi câu lệnh SQL
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Nếu thành công, hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Thêm đề thi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Nếu không thành công, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(this, "Không thể thêm đề thi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                // Sinh mã mới cho đề thi tiếp theo và hiển thị trong txt_madethi_sinhtudong
                int lastNumber = Integer.parseInt(maDeThi.substring(6)); // Lấy số đếm từ mã đề thi cuối cùng bằng cách loại bỏ ký tự "DETHI-"
                String newMaDeThi = String.format("DETHI-%04d", lastNumber + 1); // Format lại số đếm thành chuỗi có độ dài 4 ký tự, bắt đầu bằng các số 0 nếu cần thiết

                // Cập nhật giá trị maDeThi thành mã mới
                maDeThi = newMaDeThi;
                txt_madethi_sinhtudong.setText(newMaDeThi);

            }
            ArrayList<DeThi_POJO> ds_dethi_refreshed = DeThi_DAO.LayThongTin_DeThi();
            laydulieu_dethi(ds_dethi_refreshed);

        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_taodethiActionPerformed

    private void btn_suadethiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suadethiActionPerformed
        // TODO add your handling code here:
        // Lấy thông tin từ các thành phần giao diện
        String ngaySoan = txt_ngaysoan.getText();
        String maGV = txt_ma_GV.getText();
        String maDeThi = txt_madethi.getText();
        String soluongCauHoi = txt_soluongcauhoi.getText();
        String maMH = cbo_ma_monhoc.getSelectedItem().toString();
        String trangThai = cbo_trangthai.getSelectedItem().toString();

        // Kiểm tra các trường thông tin đầy đủ
        if (ngaySoan.isEmpty() || maGV.isEmpty() || maDeThi.isEmpty() || soluongCauHoi.isEmpty() || maMH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu có trường thông tin trống
        }

        // Kiểm tra định dạng của soluongCauHoi không được <= 0
        try {
            int soLuong = Integer.parseInt(soluongCauHoi);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng câu hỏi phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức nếu soluongCauHoi không hợp lệ
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng câu hỏi không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu soluongCauHoi không phải là số
        }

        try {
            // Kiểm tra xem mã đề thi cần sửa có tồn tại trong cơ sở dữ liệu không
            if (!DeThi_DAO.KiemTraTonTaiMaDeThi(maDeThi)) {
                JOptionPane.showMessageDialog(this, "Mã đề thi này chưa có trong CSDL. Hãy tạo trước khi sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức nếu mã đề thi không tồn tại
            }

            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục
            String sql = "{call NV001.E1_SuaDeThi(?, ?, ?, ?, ?, ?)}";

            // Tạo PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Chuyển đổi ngaySoan từ String sang java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(ngaySoan);

                // Đặt các tham số cho câu lệnh SQL
                preparedStatement.setString(1, maDeThi);
                preparedStatement.setDate(2, sqlDate);
                preparedStatement.setString(3, soluongCauHoi);
                preparedStatement.setString(4, trangThai);
                preparedStatement.setString(5, maGV);
                preparedStatement.setString(6, maMH);

                // Thực thi câu lệnh SQL
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Nếu thành công, hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Sửa đề thi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Nếu không thành công, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(this, "Không thể sửa đề thi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
            ArrayList<DeThi_POJO> ds_dethi_refreshed = DeThi_DAO.LayThongTin_DeThi();
            laydulieu_dethi(ds_dethi_refreshed);

        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_suadethiActionPerformed

    private void btn_xoadethiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoadethiActionPerformed
        // TODO add your handling code here:
//        // Lấy chỉ số của hàng được chọn trong bảng
//        int rowIndex = table_danhsach_dethi.getSelectedRow();
//
//        // Kiểm tra xem người dùng đã chọn hàng nào chưa
//        if (rowIndex >= 0) {
//            // Lấy mã đề thi từ bảng
//            String maDeThi = table_danhsach_dethi.getValueAt(rowIndex, 0).toString();
//            String maNV = Login_GUI.loggedInUser;
//            // Hiển thị thông báo xác nhận
//            int choice = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xoá đề thi này?", "Xác nhận xoá đề thi", JOptionPane.YES_NO_OPTION);
//            if (choice == JOptionPane.YES_OPTION) {
//                try {
//                    // Gọi thủ tục xoá đề thi
//                    DBConnect conn = new DBConnect();
//                    conn.GetConnect();
//                    Connection connection = conn.GetConnect();
//
//                    String sql = "{call NV001.E1_XoaDeThi(?,?)}";
//
//                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                        preparedStatement.setString(1, maDeThi);
//                        preparedStatement.setString(2, maNV);
//                        int rowsAffected = preparedStatement.executeUpdate();
//                        if (rowsAffected > 0) {
//                            // Nếu thành công, hiển thị thông báo thành công
//                            JOptionPane.showMessageDialog(this, "Xoá đề thi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//
//                            // Làm mới dữ liệu trong bảng
//                            ArrayList<DeThi_POJO> ds_dethi_refreshed = DeThi_DAO.LayThongTin_DeThi();
//                            laydulieu_dethi(ds_dethi_refreshed);
//
//                            btn_refreshActionPerformed(evt);
//                        } else {
//                            // Nếu không thành công, hiển thị thông báo lỗi
//                            JOptionPane.showMessageDialog(this, "Không thể xoá đề thi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                        }
//                    }
//                } catch (SQLException ex) {
//                    // Xử lý lỗi nếu có
//                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        } else {
//            // Nếu người dùng chưa chọn hàng, hiển thị thông báo lỗi
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đề thi để xoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }

        // Lấy chỉ số của các hàng được chọn trong bảng
        int[] selectedRows = table_danhsach_dethi.getSelectedRows();

        // Kiểm tra xem người dùng đã chọn hàng nào chưa
        if (selectedRows.length > 0) {
            // Hiển thị thông báo xác nhận
            int choice = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xoá các đề thi đã chọn?", "Xác nhận xoá đề thi", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    // Tạo kết nối cơ sở dữ liệu
                    DBConnect conn = new DBConnect();
                    conn.GetConnect();
                    Connection connection = conn.GetConnect();

                    // Chuẩn bị câu lệnh gọi thủ tục xoá đề thi
                    String sql = "{call NV001.E1_XoaDeThi(?,?)}";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    // Lấy mã nhân viên đang đăng nhập
                    String maNV = Login_GUI.loggedInUser;

                    // Duyệt qua tất cả các hàng được chọn và thực hiện xoá từng hàng
                    for (int rowIndex : selectedRows) {
                        // Lấy mã đề thi từ bảng
                        String maDeThi = table_danhsach_dethi.getValueAt(rowIndex, 0).toString();

                        // Đặt tham số cho câu lệnh SQL
                        preparedStatement.setString(1, maDeThi);
                        preparedStatement.setString(2, maNV);

                        // Thực hiện câu lệnh SQL
                        preparedStatement.executeUpdate();
                    }

                    // Đóng PreparedStatement
                    preparedStatement.close();

                    // Cập nhật lại bảng sau khi xoá
                    ArrayList<DeThi_POJO> ds_dethi_refreshed = DeThi_DAO.LayThongTin_DeThi();
                    laydulieu_dethi(ds_dethi_refreshed);

                    btn_refreshActionPerformed(evt);

                    // Hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Xoá các đề thi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException ex) {
                    // Xử lý lỗi nếu có
                    //JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(this, "Xoá các đề thi không thành công", "Không thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            // Nếu người dùng chưa chọn hàng, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng chọn các đề thi để xoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_xoadethiActionPerformed

    private void table_danhsach_dethiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_danhsach_dethiMouseClicked
        // TODO add your handling code here:
        // Lấy chỉ số của hàng được chọn
        int rowIndex = table_danhsach_dethi.getSelectedRow();

        // Kiểm tra xem người dùng đã chọn hàng chưa
        if (rowIndex >= 0) {
            // Lấy mô hình của bảng
            DefaultTableModel model = (DefaultTableModel) table_danhsach_dethi.getModel();

            // Lấy thông tin từ hàng được chọn
            String maDeThi = model.getValueAt(rowIndex, 0).toString();
            String ngaySoan = model.getValueAt(rowIndex, 1).toString();
            String soLuongCauHoi = model.getValueAt(rowIndex, 2).toString();
            String trangThai = model.getValueAt(rowIndex, 3).toString();
            String maGV = model.getValueAt(rowIndex, 4).toString();
            String maMH = model.getValueAt(rowIndex, 5).toString();

            // Hiển thị thông tin lên các textfield và combobox tương ứng
            txt_madethi.setText(maDeThi);
            // Chỉ hiển thị ngày trong định dạng "yyyy-MM-dd"
            txt_ngaysoan.setText(ngaySoan.substring(0, 10));
            txt_soluongcauhoi.setText(soLuongCauHoi);
            cbo_trangthai.setSelectedItem(trangThai);
            txt_ma_GV.setText(maGV);

            // Chọn môn học tương ứng trong combobox
            cbo_ma_monhoc.setSelectedItem(maMH);
        }
    }//GEN-LAST:event_table_danhsach_dethiMouseClicked

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        txt_madethi.setText("");
        txt_soluongcauhoi.setText("");

        cbo_trangthai.setSelectedItem("Đang Soạn");

        ArrayList<DeThi_POJO> ds_dethi_refreshed = DeThi_DAO.LayThongTin_DeThi();
        laydulieu_dethi(ds_dethi_refreshed);

        // Lấy mã đề thi cuối cùng từ cơ sở dữ liệu
        String lastMaDeThi = DeThi_DAO.get_MaDeThi_CuoiCung();
        if (lastMaDeThi == null) {
            lastMaDeThi = "DETHI-0001"; // Nếu không có mã nào trong cơ sở dữ liệu, sử dụng mã mặc định DETHI-0001
        } else {
            // Tạo mã đề thi mới bằng cách tăng số đếm trong mã đề thi cuối cùng
            int lastNum = 0;
            if (lastMaDeThi.length() > 6) {
                lastNum = Integer.parseInt(lastMaDeThi.substring(6)); // Lấy số đếm từ mã câu hỏi cuối cùng bằng cách loại bỏ ký tự "DETHI-"
            }
            lastNum++; // Tăng số đếm lên 1
            // Format lại mã đề thi mới
            String newNum = String.format("%04d", lastNum); // Định dạng số đếm thành chuỗi có độ dài 4 ký tự, bắt đầu bằng các số 0 nếu cần thiết
            lastMaDeThi = "DETHI-" + newNum; // Ghép số đếm vào chuỗi "DETHI-" để tạo mã đề thi mới
        }
        txt_madethi_sinhtudong.setText(lastMaDeThi);
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_move_taocauhoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_move_taocauhoiActionPerformed
        // TODO add your handling code here:
//        // Lấy chỉ số của hàng được chọn trong bảng
//        int rowIndex = table_danhsach_dethi.getSelectedRow();
//
//        // Kiểm tra xem người dùng đã chọn hàng nào chưa
//        if (rowIndex >= 0) {
//            // Lấy mô hình của bảng
//            DefaultTableModel model = (DefaultTableModel) table_danhsach_dethi.getModel();
//
//            // Lấy thông tin từ hàng được chọn
//            String maDeThi = model.getValueAt(rowIndex, 0).toString();
//            String ngaySoan = model.getValueAt(rowIndex, 1).toString();
//            String soLuongCauHoi = model.getValueAt(rowIndex, 2).toString();
//            String trangThai = model.getValueAt(rowIndex, 3).toString();
//            String maGV = model.getValueAt(rowIndex, 4).toString();
//            String maMH = model.getValueAt(rowIndex, 5).toString();
//
//            // Hiển thị thông tin lên frm_SoanCauHoi
//            frm_SoanCauHoi soanCauHoiForm = new frm_SoanCauHoi();
//
//            soanCauHoiForm.setVisible(true);
//            soanCauHoiForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form này mà không tác động đến form chính
//
//            // Đặt thông tin vào các thành phần của frm_SoanCauHoi
//            soanCauHoiForm.txt_madethi_soan.setText(maDeThi);
//            soanCauHoiForm.txt_ngaysoan_soan.setText(ngaySoan);
//            soanCauHoiForm.txt_soluongcauhoi_soan.setText(soLuongCauHoi);
//            soanCauHoiForm.txt_trangthai_soan.setText(trangThai);
//            soanCauHoiForm.txt_ma_GV_soan.setText(maGV);
//            soanCauHoiForm.txt_ma_monhoc_soan.setText(maMH);
//
//            // Lấy thông tin chi tiết đề thi và cập nhật lên các JTextField và JDateChooser
//            ArrayList<v2_ChiTietDeThi_POJO> chiTietDeThi = v2_LayThongTin_4ChiTietDeThi(maDeThi);
//            if (!chiTietDeThi.isEmpty()) {
//                v2_ChiTietDeThi_POJO chiTiet = chiTietDeThi.get(0);
//
//                // Cập nhật JDateChooser
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    Date ngayThi = dateFormat.parse(chiTiet.getNGAYTHI());
//                    soanCauHoiForm.date_chooser.setDate(ngayThi);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                // Cập nhật JTextField
//                soanCauHoiForm.txt_thoigianlambaithi.setText(chiTiet.getTHOIGIANTHI());
//                soanCauHoiForm.txt_thoigianbatdau.setText(chiTiet.getTHOIGIANBATDAU());
//                soanCauHoiForm.txt_thoigianketthuc.setText(chiTiet.getTHOIGIANKETTHUC());
//            }
//        } else {
//            // Nếu người dùng chưa chọn hàng, hiển thị thông báo lỗi
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đề thi để chuyển đến Tạo câu hỏi !", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//
//        // Đóng form frm_TaoDeThi
//        this.dispose();

// sua 1
        // Lấy chỉ số của hàng được chọn trong bảng
        int rowIndex = table_danhsach_dethi.getSelectedRow();

        // Kiểm tra xem người dùng đã chọn hàng nào chưa
        if (rowIndex >= 0) {
            // Lấy mô hình của bảng
            DefaultTableModel model = (DefaultTableModel) table_danhsach_dethi.getModel();

            // Lấy thông tin từ hàng được chọn
            String maDeThi = model.getValueAt(rowIndex, 0).toString();
            String ngaySoan = model.getValueAt(rowIndex, 1).toString();
            String soLuongCauHoi = model.getValueAt(rowIndex, 2).toString();
            String trangThai = model.getValueAt(rowIndex, 3).toString();
            String maGV = model.getValueAt(rowIndex, 4).toString();
            String maMH = model.getValueAt(rowIndex, 5).toString();

            // Hiển thị thông tin lên frm_SoanCauHoi
            frm_SoanCauHoi soanCauHoiForm = new frm_SoanCauHoi();

            soanCauHoiForm.setVisible(true);
            soanCauHoiForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form này mà không tác động đến form chính

            // Đặt thông tin vào các thành phần của frm_SoanCauHoi
            soanCauHoiForm.txt_madethi_soan.setText(maDeThi);
            soanCauHoiForm.txt_ngaysoan_soan.setText(ngaySoan);
            soanCauHoiForm.txt_soluongcauhoi_soan.setText(soLuongCauHoi);
            soanCauHoiForm.txt_trangthai_soan.setText(trangThai);
            soanCauHoiForm.txt_ma_GV_soan.setText(maGV);
            soanCauHoiForm.txt_ma_monhoc_soan.setText(maMH);

            // Lấy thông tin chi tiết đề thi và cập nhật lên các JTextField và JDateChooser
            ArrayList<v2_ChiTietDeThi_POJO> chiTietDeThi = v2_LayThongTin_4ChiTietDeThi(maDeThi);
            if (!chiTietDeThi.isEmpty()) {
                v2_ChiTietDeThi_POJO chiTiet = chiTietDeThi.get(0);

                // Cập nhật JDateChooser
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date ngayThi = dateFormat.parse(chiTiet.getNGAYTHI());
                    soanCauHoiForm.date_chooser.setDate(ngayThi);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Cập nhật JTextField
                soanCauHoiForm.txt_thoigianlambaithi.setText(chiTiet.getTHOIGIANTHI());
                soanCauHoiForm.txt_thoigianbatdau.setText(chiTiet.getTHOIGIANBATDAU());
                soanCauHoiForm.txt_thoigianketthuc.setText(chiTiet.getTHOIGIANKETTHUC());
            }
        } else {
            // Nếu người dùng chưa chọn hàng, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đề thi để chuyển đến Tạo câu hỏi !", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Đóng form frm_TaoDeThi
        this.dispose();

//// sua 2
//// Lấy chỉ số của hàng được chọn trong bảng
//        int rowIndex = table_danhsach_dethi.getSelectedRow();
//
//        // Kiểm tra xem người dùng đã chọn hàng nào chưa
//        if (rowIndex >= 0) {
//            // Lấy mô hình của bảng
//            DefaultTableModel model = (DefaultTableModel) table_danhsach_dethi.getModel();
//
//            // Lấy thông tin từ hàng được chọn
//            String maDeThi = model.getValueAt(rowIndex, 0).toString();
//            String ngaySoan = model.getValueAt(rowIndex, 1).toString();
//            String soLuongCauHoi = model.getValueAt(rowIndex, 2).toString();
//            String trangThai = model.getValueAt(rowIndex, 3).toString();
//            String maGV = model.getValueAt(rowIndex, 4).toString();
//            String maMH = model.getValueAt(rowIndex, 5).toString();
//
//            // Hiển thị thông tin lên frm_SoanCauHoi
//            frm_SoanCauHoi soanCauHoiForm = new frm_SoanCauHoi();
//
//            // Đóng form frm_TaoDeThi
//            this.dispose();
//
//            soanCauHoiForm.setVisible(true);
//            soanCauHoiForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng form này mà không tác động đến form chính
//
//            // Đặt thông tin vào các thành phần của frm_SoanCauHoi
//            soanCauHoiForm.txt_madethi_soan.setText(maDeThi);
//            soanCauHoiForm.txt_ngaysoan_soan.setText(ngaySoan);
//            soanCauHoiForm.txt_soluongcauhoi_soan.setText(soLuongCauHoi);
//            soanCauHoiForm.txt_trangthai_soan.setText(trangThai);
//            soanCauHoiForm.txt_ma_GV_soan.setText(maGV);
//            soanCauHoiForm.txt_ma_monhoc_soan.setText(maMH);
//        } else {
//            // Nếu người dùng chưa chọn hàng, hiển thị thông báo lỗi
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đề thi để chuyển đến Tạo câu hỏi !", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
    }//GEN-LAST:event_btn_move_taocauhoiActionPerformed

    private void btn_quaylaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quaylaiActionPerformed
        // TODO add your handling code here:
        Managanent_GUI qlc = new Managanent_GUI();
        qlc.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_quaylaiActionPerformed

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
            java.util.logging.Logger.getLogger(frm_TaoDeThi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_TaoDeThi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_TaoDeThi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_TaoDeThi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_TaoDeThi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_move_taocauhoi;
    private javax.swing.JButton btn_quaylai;
    protected javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_suadethi;
    private javax.swing.JButton btn_taodethi;
    private javax.swing.JButton btn_xoadethi;
    private javax.swing.JComboBox<String> cbo_ma_monhoc;
    private javax.swing.JComboBox<String> cbo_trangthai;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_danhsach_dethi;
    private javax.swing.JTextField txt_ma_GV;
    public javax.swing.JTextField txt_madethi;
    protected javax.swing.JTextField txt_madethi_sinhtudong;
    private javax.swing.JTextField txt_ngaysoan;
    private javax.swing.JTextField txt_soluongcauhoi;
    private javax.swing.JTextField txt_tenchucvu;
    // End of variables declaration//GEN-END:variables
}
