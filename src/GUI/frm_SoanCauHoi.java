/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.*;
import static DAO.v2_ChiTietDeThi_DAO.v2_LayThongTin_4ChiTietDeThi;
import POJO.*;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellType;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author SONDAY
 */
public class frm_SoanCauHoi extends javax.swing.JFrame {

    /**
     * Creates new form frm_SoanCauHoi
     */
    Vector tblData_CauHoi = new Vector();
    Vector tblTitle_CauHoi = new Vector();
    DefaultTableModel tblModel_CauHoi;
    static ArrayList<CauHoi_POJO> ds_cauhoi = CauHoi_DAO.LayThongTin_CauHoi();

    public frm_SoanCauHoi() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Soạn câu hỏi");
        System.setProperty("org.apache.logging.log4j.simplelog.StatusLogger.level", "OFF");
        txt_macauhoi_sinhtudong.setEditable(false);
        txt_macauhoi_2.setEditable(false);
        txt_macauhoi_sinhtudong.setHorizontalAlignment(SwingConstants.CENTER);
        txt_madethi_soan.setEditable(false);
        txt_madethi_soan.setHorizontalAlignment(SwingConstants.CENTER);
        txt_ngaysoan_soan.setEditable(false);
        txt_ngaysoan_soan.setHorizontalAlignment(SwingConstants.CENTER);
        txt_ma_monhoc_soan.setEditable(false);
        txt_ma_monhoc_soan.setHorizontalAlignment(SwingConstants.CENTER);
        txt_soluongcauhoi_soan.setEditable(false);
        txt_soluongcauhoi_soan.setHorizontalAlignment(SwingConstants.CENTER);
        txt_trangthai_soan.setEditable(false);
        txt_trangthai_soan.setHorizontalAlignment(SwingConstants.CENTER);
        txt_ma_GV_soan.setEditable(false);
        txt_ma_GV_soan.setHorizontalAlignment(SwingConstants.CENTER);
        txt_thoigianbatdau.setHorizontalAlignment(SwingConstants.CENTER);
        txt_thoigianketthuc.setHorizontalAlignment(SwingConstants.CENTER);
        txt_macauhoi_2.setHorizontalAlignment(SwingConstants.CENTER);
        ArrayList<String> ma_CauHoiList = CauHoi_DAO.get_CauHoiList_CauHoi();
        DefaultComboBoxModel<String> model_MonHocList = new DefaultComboBoxModel<>(ma_CauHoiList.toArray(new String[0]));

        btn_hienthi_danhsachcauhoi_theomade.doClick();

        hienthi_cauhoi();

        this.txt_tenchucvu.setText(Login_GUI.chucvu_User);
        this.txt_tenchucvu.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_tenchucvu.setEditable(false);

        // Lấy mã câu hỏi cuối cùng từ cơ sở dữ liệu
        String lastMaCH = CauHoi_DAO.get_MaCH_CuoiCung();
        if (lastMaCH == null) {
            lastMaCH = "CH001"; // Nếu không có mã nào trong cơ sở dữ liệu, sử dụng mã mặc định CH001
        } else {
            // Tạo mã câu hỏi mới bằng cách tăng số đếm trong mã câu hỏi cuối cùng
            int lastNum = 0;
            // ví dụ: CH001 -> thì tức là có 001 phía sau 2 chữ CH
            if (lastMaCH.length() > 2) {
                // Lấy số đếm từ mã câu hỏi cuối cùng bằng cách loại bỏ ký tự "CH"
                lastNum = Integer.parseInt(lastMaCH.substring(2));
            }
            lastNum++; // Tăng số đếm lên 1
            // Format lại mã câu hỏi mới
            String newNum = String.format("%03d", lastNum); // Định dạng số đếm thành chuỗi có độ dài 3 ký tự, bắt đầu bằng các số 0 nếu cần thiết
            lastMaCH = "CH" + newNum; // Ghép số đếm vào chuỗi "CH" để tạo mã câu hỏi mới
        }
        txt_macauhoi_sinhtudong.setText(lastMaCH);
    }

    public void hienthi_cauhoi() {
        tblTitle_CauHoi.add("Câu hỏi");
        tblTitle_CauHoi.add("Đáp án A");
        tblTitle_CauHoi.add("Đáp án B");
        tblTitle_CauHoi.add("Đáp án C");
        tblTitle_CauHoi.add("Đáp án D");
        tblTitle_CauHoi.add("Đáp án đúng");
        tblTitle_CauHoi.add("Mã câu hỏi");

    }

    public void laydulieu_cauhoi(ArrayList<CauHoi_POJO> ds_cauhoi) {
        tblData_CauHoi.removeAllElements();
        for (CauHoi_POJO ch : ds_cauhoi) {
            Vector v = new Vector();
            v.add(ch.getCAUHOI());
            v.add(ch.getDAPAN_A());
            v.add(ch.getDAPAN_B());
            v.add(ch.getDAPAN_C());
            v.add(ch.getDAPAN_D());
            v.add(ch.getDAPANDUNG());
            v.add(ch.getMACAUHOI());
            tblData_CauHoi.add(v);
        }
        table_danhsach_cauhoidasoan_theomade.setModel(new DefaultTableModel(tblData_CauHoi, tblTitle_CauHoi));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TimePicker_ThoiGianBatDau = new com.raven.swing.TimePicker();
        TimePicker_ThoiGianKetThuc = new com.raven.swing.TimePicker();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_madethi_soan = new javax.swing.JTextField();
        txt_ngaysoan_soan = new javax.swing.JTextField();
        txt_soluongcauhoi_soan = new javax.swing.JTextField();
        txt_trangthai_soan = new javax.swing.JTextField();
        txt_ma_monhoc_soan = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_ma_GV_soan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_thoigianlambaithi = new javax.swing.JTextField();
        txt_thoigianbatdau = new javax.swing.JTextField();
        txt_thoigianketthuc = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        date_chooser = new com.toedter.calendar.JDateChooser();
        btn_suathongtin_chitietdethi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_noidungcauhoi = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_A = new javax.swing.JTextField();
        txt_B = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txt_C = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txt_D = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txt_DUNG = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_danhsach_cauhoidasoan_theomade = new javax.swing.JTable();
        btn_themcauhoi = new javax.swing.JButton();
        btn_suacauhoi = new javax.swing.JButton();
        btn_xoacauhoi = new javax.swing.JButton();
        btn_quaylai_frmTaoDeThi = new javax.swing.JButton();
        txt_macauhoi_sinhtudong = new javax.swing.JTextField();
        btn_hienthi_danhsachcauhoi_theomade = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        txt_macauhoi_2 = new javax.swing.JTextField();
        btn_quaylai = new javax.swing.JButton();
        btn_chon_cauhoi_tufile = new java.awt.Button();
        jLabel16 = new javax.swing.JLabel();
        txt_tenchucvu = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin đề thi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Mã đề thi:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Mã GV soạn đề:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Mã môn học:");

        txt_madethi_soan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_madethi_soan.setForeground(new java.awt.Color(255, 0, 51));

        txt_ngaysoan_soan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_ngaysoan_soan.setForeground(new java.awt.Color(255, 0, 51));

        txt_soluongcauhoi_soan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_soluongcauhoi_soan.setForeground(new java.awt.Color(255, 0, 51));

        txt_trangthai_soan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_trangthai_soan.setForeground(new java.awt.Color(255, 0, 51));

        txt_ma_monhoc_soan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_ma_monhoc_soan.setForeground(new java.awt.Color(255, 0, 51));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Ngày soạn:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Số lượng câu hỏi:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Trạng thái:");

        txt_ma_GV_soan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_ma_GV_soan.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_ngaysoan_soan, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(txt_madethi_soan)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_ma_GV_soan, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_ma_monhoc_soan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_trangthai_soan, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_soluongcauhoi_soan, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_madethi_soan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ngaysoan_soan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ma_monhoc_soan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_soluongcauhoi_soan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_trangthai_soan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ma_GV_soan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("SOẠN CÂU HỎI CHO ĐỀ KIỂM TRA TRẮC NGHIỆM");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thời gian làm bài", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Ngày thi:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Thời gian làm bài  thi:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Thời gian bắt đầu:");

        txt_thoigianlambaithi.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_thoigianlambaithi.setForeground(new java.awt.Color(255, 0, 51));

        txt_thoigianbatdau.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_thoigianbatdau.setForeground(new java.awt.Color(255, 0, 51));
        txt_thoigianbatdau.setText("");

        txt_thoigianketthuc.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_thoigianketthuc.setForeground(new java.awt.Color(255, 0, 51));
        txt_thoigianketthuc.setText("");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Thời gian kết thúc:");

        date_chooser.setDateFormatString("yyyy-MM-dd");

        btn_suathongtin_chitietdethi.setBackground(new java.awt.Color(153, 255, 255));
        btn_suathongtin_chitietdethi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_suathongtin_chitietdethi.setText(" Sửa thông tin");
        btn_suathongtin_chitietdethi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suathongtin_chitietdethiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(date_chooser, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(txt_thoigianlambaithi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_thoigianbatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_thoigianketthuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(btn_suathongtin_chitietdethi)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_thoigianlambaithi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_thoigianbatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_thoigianketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_suathongtin_chitietdethi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin câu hỏi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        txt_noidungcauhoi.setColumns(20);
        txt_noidungcauhoi.setRows(5);
        jScrollPane2.setViewportView(txt_noidungcauhoi);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Đáp án A:");

        txt_A.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_A.setForeground(new java.awt.Color(255, 0, 51));

        txt_B.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_B.setForeground(new java.awt.Color(255, 0, 51));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setText("Đáp án B:");

        txt_C.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_C.setForeground(new java.awt.Color(255, 0, 51));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("Đáp án C:");

        txt_D.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_D.setForeground(new java.awt.Color(255, 0, 51));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Đáp án D:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("Đáp án đúng:");

        txt_DUNG.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_DUNG.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_DUNG, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_C, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(txt_B)
                    .addComponent(txt_A)
                    .addComponent(txt_D))
                .addGap(602, 602, 602))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_A, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_B, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_C, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_D, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_DUNG, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 23, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách câu hỏi đã soạn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        table_danhsach_cauhoidasoan_theomade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_danhsach_cauhoidasoan_theomadeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_danhsach_cauhoidasoan_theomade);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
        );

        btn_themcauhoi.setBackground(new java.awt.Color(153, 255, 255));
        btn_themcauhoi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_themcauhoi.setText("Thêm câu hỏi");
        btn_themcauhoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themcauhoiActionPerformed(evt);
            }
        });

        btn_suacauhoi.setBackground(new java.awt.Color(153, 255, 255));
        btn_suacauhoi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_suacauhoi.setText("Sửa câu hỏi");
        btn_suacauhoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suacauhoiActionPerformed(evt);
            }
        });

        btn_xoacauhoi.setBackground(new java.awt.Color(153, 255, 255));
        btn_xoacauhoi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_xoacauhoi.setText("Xoá câu hỏi");
        btn_xoacauhoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoacauhoiActionPerformed(evt);
            }
        });

        btn_quaylai_frmTaoDeThi.setBackground(new java.awt.Color(153, 255, 255));
        btn_quaylai_frmTaoDeThi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_quaylai_frmTaoDeThi.setText("Quay lại Tạo đề thi");
        btn_quaylai_frmTaoDeThi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quaylai_frmTaoDeThiActionPerformed(evt);
            }
        });

        txt_macauhoi_sinhtudong.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_macauhoi_sinhtudong.setForeground(new java.awt.Color(255, 0, 51));

        btn_hienthi_danhsachcauhoi_theomade.setText("XEM DANH SÁCH CÂU HỎI");
        btn_hienthi_danhsachcauhoi_theomade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hienthi_danhsachcauhoi_theomadeActionPerformed(evt);
            }
        });

        btn_refresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh3.png"))); // NOI18N
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        txt_macauhoi_2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_macauhoi_2.setForeground(new java.awt.Color(255, 0, 51));

        btn_quaylai.setText("Trở về");
        btn_quaylai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quaylaiActionPerformed(evt);
            }
        });

        btn_chon_cauhoi_tufile.setBackground(new java.awt.Color(153, 255, 255));
        btn_chon_cauhoi_tufile.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_chon_cauhoi_tufile.setLabel("Open file Excel");
        btn_chon_cauhoi_tufile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chon_cauhoi_tufileActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Mã câu hỏi được tạo tự động:");

        txt_tenchucvu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_tenchucvu.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_chon_cauhoi_tufile, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_macauhoi_2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_hienthi_danhsachcauhoi_theomade)
                                .addGap(12, 12, 12))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(101, 101, 101)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_tenchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_quaylai, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_quaylai_frmTaoDeThi, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_xoacauhoi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_suacauhoi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_themcauhoi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_refresh))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_macauhoi_sinhtudong, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_tenchucvu, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                            .addComponent(btn_quaylai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(btn_themcauhoi, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(249, 249, 249)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btn_suacauhoi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btn_xoacauhoi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(btn_refresh))
                                    .addGap(18, 18, 18)
                                    .addComponent(btn_quaylai_frmTaoDeThi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btn_hienthi_danhsachcauhoi_theomade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_macauhoi_2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btn_chon_cauhoi_tufile, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 305, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_macauhoi_sinhtudong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_quaylai_frmTaoDeThiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quaylai_frmTaoDeThiActionPerformed
        // TODO add your handling code here:
        frm_TaoDeThi tao_dethi = new frm_TaoDeThi();
        tao_dethi.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_quaylai_frmTaoDeThiActionPerformed

    private void btn_themcauhoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themcauhoiActionPerformed
        // TODO add your handling code here:
// Lấy số lượng câu hỏi từ txt_soluongcauhoi_soan
        int soLuongCauHoiNhap = Integer.parseInt(txt_soluongcauhoi_soan.getText());

        // Lấy mã đề từ txt_madethi_soan
        String ktra = txt_madethi_soan.getText();

        String maCH = txt_macauhoi_sinhtudong.getText();

        // Lấy thông tin từ các thành phần giao diện
        String noidungCH = txt_noidungcauhoi.getText();
        String dapan_a = txt_A.getText();
        String dapan_b = txt_B.getText();
        String dapan_c = txt_C.getText();
        String dapan_d = txt_D.getText();
        String dapan_dung = txt_DUNG.getText();
        String maMH = txt_ma_monhoc_soan.getText();
        String maDeThi = txt_madethi_soan.getText();
        String thoiGianLamBai = txt_thoigianlambaithi.getText();
        String thoiGianBatDau = txt_thoigianbatdau.getText();
        String thoiGianKetThuc = txt_thoigianketthuc.getText();
        String maNV = Login_GUI.loggedInUser;

        // Kiểm tra số lượng câu hỏi của mã đề đã đạt tối đa chưa
        int soLuongCauHoiHienTai = CauHoi_DAO.get_SoLuongCauHoi_Theo_MaDeThi(ktra);
        if (soLuongCauHoiHienTai >= soLuongCauHoiNhap) {
            JOptionPane.showMessageDialog(this, "Số lượng câu hỏi đã đạt tối đa cho mã đề này", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu số lượng câu hỏi đã đạt tối đa
        }

        // Kiểm tra ngày thi có được chọn hay không
        if (date_chooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày thi", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu ngày thi chưa được chọn
        }

        int thoiGianLamBaiInt = 0;
        try {
            // Tách số thời gian ra khỏi phần "phút" và kiểm tra thời gian làm bài thi phải lớn hơn 0
            String[] thoiGianParts = thoiGianLamBai.split(" ");
            thoiGianLamBaiInt = Integer.parseInt(thoiGianParts[0]);
            if (thoiGianLamBaiInt <= 0) {
                JOptionPane.showMessageDialog(this, "Thời gian làm bài thi phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức nếu thời gian làm bài thi không đạt yêu cầu
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thời gian làm bài thi là một số nguyên dương", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu thời gian làm bài thi không phải là một số nguyên dương
        }

        /*
        Ví dụ: Bắt đầu 10h đổi sang kiểu Date = 36.000.000 mili giây
               Kết thúc 10h30 = 37.800.000 mili giây
               Tính toán thời gian chênh lệch = 1.800.000 mili giây
               chia cho 1000 để chuyển mili giây sang giây
               chia tiếp cho 60 để chuyển từ giây sang phút             
         */
        // Kiểm tra thời gian bắt đầu và thời gian kết thúc có hợp lệ
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date batDau = dateFormat.parse(thoiGianBatDau);
            Date ketThuc = dateFormat.parse(thoiGianKetThuc);
            long diffInMillies = ketThuc.getTime() - batDau.getTime();
            long diffInMinutes = (diffInMillies / 1000) / 60;

            if (diffInMinutes != thoiGianLamBaiInt) {
                JOptionPane.showMessageDialog(this, "Thời gian bắt đầu và kết thúc không nằm trong khoảng thời gian làm bài thi", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức nếu thời gian không hợp lệ
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng thời gian không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu định dạng thời gian không hợp lệ
        }

        try {
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục thêm câu hỏi
            String sqlThemCauHoi = "{call NV001.E1_ThemCauHoi(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            // Gọi thủ tục thêm chi tiết đề thi
            String sqlThemChiTietDeThi = "{call NV001.E1_ThemChiTietDeThi(?, ?, ?, ?, ?, ?, ?)}";

            // Tạo PreparedStatement cho thêm câu hỏi
            try (PreparedStatement psThemCauHoi = connection.prepareStatement(sqlThemCauHoi)) {
                // Đặt các tham số cho câu lệnh SQL thêm câu hỏi
                psThemCauHoi.setString(1, maCH);
                psThemCauHoi.setString(2, noidungCH);
                psThemCauHoi.setString(3, dapan_a);
                psThemCauHoi.setString(4, dapan_b);
                psThemCauHoi.setString(5, dapan_c);
                psThemCauHoi.setString(6, dapan_d);
                psThemCauHoi.setString(7, dapan_dung);
                psThemCauHoi.setString(8, maMH);
                psThemCauHoi.setString(9, maNV);
                // Thực thi câu lệnh SQL thêm câu hỏi
                int rowsAffectedCauHoi = psThemCauHoi.executeUpdate();
                if (rowsAffectedCauHoi > 0) {
                    // Nếu thêm câu hỏi thành công, tiếp tục thêm chi tiết đề thi
                    try (PreparedStatement psThemChiTietDeThi = connection.prepareStatement(sqlThemChiTietDeThi)) {
                        // Đặt các tham số cho câu lệnh SQL thêm chi tiết đề thi
                        psThemChiTietDeThi.setString(1, maDeThi);
                        psThemChiTietDeThi.setString(2, maCH);
                        // Thêm các thông tin khác vào câu lệnh thêm chi tiết đề thi
                        psThemChiTietDeThi.setDate(3, new java.sql.Date(date_chooser.getDate().getTime())); // Chuyển từ Java Date sang SQL Date
                        psThemChiTietDeThi.setString(4, txt_thoigianlambaithi.getText());
                        psThemChiTietDeThi.setString(5, txt_thoigianbatdau.getText());
                        psThemChiTietDeThi.setString(6, txt_thoigianketthuc.getText());
                        psThemChiTietDeThi.setString(7, maNV);
                        // Thực thi câu lệnh SQL thêm chi tiết đề thi
                        int rowsAffectedChiTietDeThi = psThemChiTietDeThi.executeUpdate();
                        if (rowsAffectedChiTietDeThi > 0) {
                            // Nếu thêm chi tiết đề thi thành công, hiển thị thông báo thành công
                            // JOptionPane.showMessageDialog(this, "Thêm câu hỏi và chi tiết đề thi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Nếu không thành công, hiển thị thông báo lỗi
                            JOptionPane.showMessageDialog(this, "Không thể thêm chi tiết đề thi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }

                        // Sinh mã mới cho câu hỏi tiếp theo và hiển thị trong txt_macauhoi_sinhtudong
                        int lastNumber = Integer.parseInt(maCH.substring(2));
                        String newMaCH = String.format("CH%03d", lastNumber + 1);

                        // Cập nhật giá trị maCH thành mã mới
                        maCH = newMaCH;
                        txt_macauhoi_sinhtudong.setText(newMaCH);

                        resetTextFields();
                    }
                } else {
                    // Nếu không thành công, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(this, "Không thể thêm câu hỏi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Cập nhật lại danh sách câu hỏi sau khi thêm
            // Tạo ArrayList để lưu danh sách điểm danh của lớp được chọn
            ArrayList<CauHoi_POJO> ds_cauhoi_theo_madethi = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(maDeThi);

            // Hiển thị danh sách điểm danh của lớp đó
            laydulieu_cauhoi(ds_cauhoi_theo_madethi);

        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_themcauhoiActionPerformed

    private void btn_suacauhoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suacauhoiActionPerformed
        // TODO add your handling code here:
        // Lấy thông tin từ các thành phần giao diện
        String maCH = txt_macauhoi_2.getText();
        String noidungCH = txt_noidungcauhoi.getText();
        String dapan_a = txt_A.getText();
        String dapan_b = txt_B.getText();
        String dapan_c = txt_C.getText();
        String dapan_d = txt_D.getText();
        String dapan_dung = txt_DUNG.getText();
        String maMH = txt_ma_monhoc_soan.getText();
        String maNV = Login_GUI.loggedInUser;
        // Kiểm tra các trường thông tin đầy đủ
        if (maCH.isEmpty() || noidungCH.isEmpty() || dapan_a.isEmpty() || dapan_b.isEmpty() || dapan_c.isEmpty() || dapan_d.isEmpty() || dapan_dung.isEmpty() || maMH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu có trường thông tin trống
        }

        try {
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục
            String sql = "{call NV001.E1_SuaCauHoi(?, ?, ?, ?, ?, ?, ?, ?,?)}";

            // Tạo PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Đặt các tham số cho câu lệnh SQL
                preparedStatement.setString(1, maCH);
                preparedStatement.setString(2, noidungCH);
                preparedStatement.setString(3, dapan_a);
                preparedStatement.setString(4, dapan_b);
                preparedStatement.setString(5, dapan_c);
                preparedStatement.setString(6, dapan_d);
                preparedStatement.setString(7, dapan_dung);
                preparedStatement.setString(8, maMH);
                preparedStatement.setString(9, maNV);
                // Thực thi câu lệnh SQL
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Nếu thành công, hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Sửa câu hỏi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Nếu không thành công, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(this, "Không thể sửa câu hỏi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
            String madethi = txt_madethi_soan.getText();

            // Tạo ArrayList để lưu danh sách điểm danh của lớp được chọn
            ArrayList<CauHoi_POJO> ds_cauhoi_theo_madethi = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(madethi);

            // Hiển thị danh sách điểm danh của lớp đó
            laydulieu_cauhoi(ds_cauhoi_theo_madethi);

        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_suacauhoiActionPerformed

    private void btn_xoacauhoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoacauhoiActionPerformed
        // TODO add your handling code here:
//        // Lấy chỉ số của hàng được chọn trong bảng
//        int rowIndex = table_danhsach_cauhoidasoan_theomade.getSelectedRow();
//
//        // Kiểm tra xem người dùng đã chọn hàng nào chưa
//        if (rowIndex >= 0) {
//            // Lấy mã câu hỏi từ bảng
//            String maCH = table_danhsach_cauhoidasoan_theomade.getValueAt(rowIndex, 6).toString();
//            String maNV = Login_GUI.loggedInUser;
//            // Hiển thị thông báo xác nhận
//            int choice = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xoá câu hỏi này?", "Xác nhận xoá đề thi", JOptionPane.YES_NO_OPTION);
//            if (choice == JOptionPane.YES_OPTION) {
//                try {
//                    // Gọi thủ tục xoá câu hỏi
//                    DBConnect conn = new DBConnect();
//                    conn.GetConnect();
//                    Connection connection = conn.GetConnect();
//
//                    String sql = "{call NV001.E1_XoaCauHoi(?,?)}";
//
//                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                        preparedStatement.setString(1, maCH);
//                        preparedStatement.setString(2, maNV);
//                        int rowsAffected = preparedStatement.executeUpdate();
//                        if (rowsAffected > 0) {
//                            // Nếu thành công, hiển thị thông báo thành công
//                            //JOptionPane.showMessageDialog(this, "Xoá câu hỏi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//
//                            String madethi = txt_madethi_soan.getText();
//
//                            // Tạo ArrayList để lưu danh sách câu hỏi của mã đề được chọn
//                            ArrayList<CauHoi_POJO> ds_cauhoi_theo_madethi = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(madethi);
//
//                            // Hiển thị danh sách câu hỏi của mã đề đó
//                            laydulieu_cauhoi(ds_cauhoi_theo_madethi);
//
//                            btn_refreshActionPerformed(evt);
//                        } else {
//                            // Nếu không thành công, hiển thị thông báo lỗi
//                            JOptionPane.showMessageDialog(this, "Không thể xoá câu hỏi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        int[] selectedRows = table_danhsach_cauhoidasoan_theomade.getSelectedRows();

        // Kiểm tra xem người dùng đã chọn hàng nào chưa
        if (selectedRows.length > 0) {
            // Hiển thị thông báo xác nhận
            int choice = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xoá các câu hỏi đã chọn?", "Xác nhận xoá đề thi", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    // Tạo kết nối cơ sở dữ liệu
                    DBConnect conn = new DBConnect();
                    conn.GetConnect();
                    Connection connection = conn.GetConnect();

                    // Chuẩn bị câu lệnh gọi thủ tục xoá câu hỏi
                    String sql = "{call NV001.E1_XoaCauHoi(?,?)}";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    // Lấy mã nhân viên đang đăng nhập
                    String maNV = Login_GUI.loggedInUser;

                    // Duyệt qua tất cả các hàng được chọn và thực hiện xoá từng hàng
                    for (int rowIndex : selectedRows) {
                        // Lấy mã câu hỏi từ bảng
                        String maCH = table_danhsach_cauhoidasoan_theomade.getValueAt(rowIndex, 6).toString();

                        // Đặt tham số cho câu lệnh SQL
                        preparedStatement.setString(1, maCH);
                        preparedStatement.setString(2, maNV);

                        // Thực hiện câu lệnh SQL
                        preparedStatement.executeUpdate();
                    }

                    // Đóng PreparedStatement
                    preparedStatement.close();

                    // Cập nhật lại bảng sau khi xoá
                    String madethi = txt_madethi_soan.getText();
                    ArrayList<CauHoi_POJO> ds_cauhoi_theo_madethi = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(madethi);
                    laydulieu_cauhoi(ds_cauhoi_theo_madethi);
                    btn_refreshActionPerformed(evt);

                    // Hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Xoá các câu hỏi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException ex) {
                    // Xử lý lỗi nếu có
                    //JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(this, "Xoá các câu hỏi không thành công", "Không thành công", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            // Nếu người dùng chưa chọn hàng, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng chọn các câu hỏi để xoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_xoacauhoiActionPerformed

    private void btn_hienthi_danhsachcauhoi_theomadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hienthi_danhsachcauhoi_theomadeActionPerformed
        // TODO add your handling code here:
        String madethi = txt_madethi_soan.getText();

        // Tạo ArrayList để lưu danh sách điểm danh của lớp được chọn
        ArrayList<CauHoi_POJO> ds_cauhoi_theo_madethi = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(madethi);

        // Hiển thị danh sách câu hỏi của mã đề
        laydulieu_cauhoi(ds_cauhoi_theo_madethi);

        txt_macauhoi_2.setText("");
    }//GEN-LAST:event_btn_hienthi_danhsachcauhoi_theomadeActionPerformed

    private void table_danhsach_cauhoidasoan_theomadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_danhsach_cauhoidasoan_theomadeMouseClicked
        // TODO add your handling code here:
        // Lấy chỉ số của hàng được chọn
        int rowIndex = table_danhsach_cauhoidasoan_theomade.getSelectedRow();

        // Kiểm tra xem người dùng đã chọn hàng chưa
        if (rowIndex >= 0) {
            // Lấy mô hình của bảng
            DefaultTableModel model = (DefaultTableModel) table_danhsach_cauhoidasoan_theomade.getModel();

            // Lấy thông tin từ hàng được chọn
            String noidungCH = model.getValueAt(rowIndex, 0).toString();
            String dapanA = model.getValueAt(rowIndex, 1).toString();
            String dapanB = model.getValueAt(rowIndex, 2).toString();
            String dapanC = model.getValueAt(rowIndex, 3).toString();
            String dapanD = model.getValueAt(rowIndex, 4).toString();
            String dapanDUNG = model.getValueAt(rowIndex, 5).toString();
            String mach = model.getValueAt(rowIndex, 6).toString();

            // Hiển thị thông tin lên các textfield và combobox tương ứng
            txt_noidungcauhoi.setText(noidungCH);
            txt_A.setText(dapanA);
            txt_B.setText(dapanB);
            txt_C.setText(dapanC);
            txt_D.setText(dapanD);
            txt_DUNG.setText(dapanDUNG);
            txt_macauhoi_2.setText(mach);
        }
    }//GEN-LAST:event_table_danhsach_cauhoidasoan_theomadeMouseClicked

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        String madethi = txt_madethi_soan.getText();

        // Tạo ArrayList để lưu danh sách điểm danh của lớp được chọn
        ArrayList<CauHoi_POJO> ds_cauhoi_theo_madethi = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(madethi);
        // Hiển thị danh sách điểm danh của lớp đó
        laydulieu_cauhoi(ds_cauhoi_theo_madethi);

        // Lấy thông tin chi tiết đề thi và cập nhật lên các JTextField và JDateChooser
        ArrayList<v2_ChiTietDeThi_POJO> chiTietDeThi = v2_LayThongTin_4ChiTietDeThi(madethi);
        if (!chiTietDeThi.isEmpty()) {
            v2_ChiTietDeThi_POJO chiTiet = chiTietDeThi.get(0);

            // Cập nhật JDateChooser
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date ngayThi = dateFormat.parse(chiTiet.getNGAYTHI());
                date_chooser.setDate(ngayThi);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Cập nhật JTextField
            txt_thoigianlambaithi.setText(chiTiet.getTHOIGIANTHI());
            txt_thoigianbatdau.setText(chiTiet.getTHOIGIANBATDAU());
            txt_thoigianketthuc.setText(chiTiet.getTHOIGIANKETTHUC());
        }

        // Lấy mã câu hỏi cuối cùng từ cơ sở dữ liệu
        String lastMaCH = CauHoi_DAO.get_MaCH_CuoiCung();
        if (lastMaCH == null) {
            lastMaCH = "CH001"; // Nếu không có mã nào trong cơ sở dữ liệu, sử dụng mã mặc định CH001
        } else {
            // Tạo mã câu hỏi mới bằng cách tăng số đếm trong mã câu hỏi cuối cùng
            int lastNum = 0;
            if (lastMaCH.length() > 2) {
                lastNum = Integer.parseInt(lastMaCH.substring(2)); // Lấy số đếm từ mã câu hỏi cuối cùng bằng cách loại bỏ ký tự "CH"
            }
            lastNum++; // Tăng số đếm lên 1
            // Format lại mã câu hỏi mới
            String newNum = String.format("%03d", lastNum); // Định dạng số đếm thành chuỗi có độ dài 3 ký tự, bắt đầu bằng các số 0 nếu cần thiết
            lastMaCH = "CH" + newNum; // Ghép số đếm vào chuỗi "CH" để tạo mã câu hỏi mới
        }
        txt_macauhoi_sinhtudong.setText(lastMaCH);
        resetTextFields();
    }//GEN-LAST:event_btn_refreshActionPerformed
    private void resetTextFields() {
        txt_noidungcauhoi.setText("");
        txt_A.setText("");
        txt_B.setText("");
        txt_C.setText("");
        txt_D.setText("");
        txt_DUNG.setText("");
    }

    private void btn_quaylaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quaylaiActionPerformed
        // TODO add your handling code here:
        Managanent_GUI qlc = new Managanent_GUI();
        qlc.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_quaylaiActionPerformed

    private void v6_themCauHoiVaChiTietDeThi(String[] parts) {
        String maNV = Login_GUI.loggedInUser;
        // Lấy mã môn học từ giao diện
        String maMH = txt_ma_monhoc_soan.getText();
        // Lấy mã đề thi từ giao diện
        String maDeThi = txt_madethi_soan.getText();
        // Lấy thời gian làm bài thi từ giao diện
        String thoigianLambaithi = txt_thoigianlambaithi.getText();
        // Lấy thời gian bắt đầu từ giao diện
        String thoigianBatdau = txt_thoigianbatdau.getText();
        // Lấy thời gian kết thúc từ giao diện
        String thoigianKetthuc = txt_thoigianketthuc.getText();

        try {

            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục thêm câu hỏi
            String sqlThemCauHoi = "{call NV001.E1_ThemCauHoi(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            // Gọi thủ tục thêm chi tiết đề thi
            String sqlThemChiTietDeThi = "{call NV001.E1_ThemChiTietDeThi(?, ?, ?, ?, ?, ?, ?)}";

            // Lặp qua mỗi dòng dữ liệu câu hỏi
            for (String part : parts) {
                // Tạo PreparedStatement cho thêm câu hỏi
                try (PreparedStatement psThemCauHoi = connection.prepareStatement(sqlThemCauHoi)) {
                    // Đặt các tham số cho câu lệnh SQL thêm câu hỏi
                    psThemCauHoi.setString(1, parts[6]);
                    psThemCauHoi.setString(2, parts[0]);
                    psThemCauHoi.setString(3, parts[1]);
                    psThemCauHoi.setString(4, parts[2]);
                    psThemCauHoi.setString(5, parts[3]);
                    psThemCauHoi.setString(6, parts[4]);
                    psThemCauHoi.setString(7, parts[5]);
                    psThemCauHoi.setString(8, maMH); // Lấy từ giao diện
                    psThemCauHoi.setString(9, maNV);
                    // Thực thi câu lệnh SQL thêm câu hỏi
                    int rowsAffectedCauHoi = psThemCauHoi.executeUpdate();
                    if (rowsAffectedCauHoi > 0) {
                        // Nếu thêm câu hỏi thành công, tiếp tục thêm chi tiết đề thi
                        try (PreparedStatement psThemChiTietDeThi = connection.prepareStatement(sqlThemChiTietDeThi)) {
                            // Đặt các tham số cho câu lệnh SQL thêm chi tiết đề thi
                            psThemChiTietDeThi.setString(1, maDeThi);
                            psThemChiTietDeThi.setString(2, parts[6]); // maCH
                            // Thêm các thông tin khác vào câu lệnh thêm chi tiết đề thi
                            psThemChiTietDeThi.setDate(3, new java.sql.Date(date_chooser.getDate().getTime())); // Chuyển từ Java Date sang SQL Date
                            psThemChiTietDeThi.setString(4, thoigianLambaithi);
                            psThemChiTietDeThi.setString(5, thoigianBatdau);
                            psThemChiTietDeThi.setString(6, thoigianKetthuc);
                            psThemChiTietDeThi.setString(7, maNV);
                            // Thực thi câu lệnh SQL thêm chi tiết đề thi
                            int rowsAffectedChiTietDeThi = psThemChiTietDeThi.executeUpdate();
                            if (rowsAffectedChiTietDeThi > 0) {
                                // Nếu thêm chi tiết đề thi thành công, hiển thị thông báo thành công
                                // JOptionPane.showMessageDialog(this, "Thêm câu hỏi và chi tiết đề thi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                // Nếu không thành công, hiển thị thông báo lỗi
                                JOptionPane.showMessageDialog(this, "Không thể thêm chi tiết đề thi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        // Nếu không thành công, hiển thị thông báo lỗi
                        JOptionPane.showMessageDialog(this, "Không thể thêm câu hỏi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm kiểm tra dòng có rỗng hay không
    private boolean isEmptyRow(Row row) {
        for (Cell cell : row) {
            if (cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    // Hàm kiểm tra dòng có chứa ít nhất một ô không rỗng hay không
    private boolean hasNonEmptyCell(Row row) {
        for (Cell cell : row) {
            if (cell.getCellType() != CellType.BLANK) {
                return true;
            }
        }
        return false;
    }

    // Phương thức kiểm tra xem có câu hỏi nào trong bảng hay không
    private boolean daTonTaiCauHoiTrongBang() {
        DefaultTableModel model = (DefaultTableModel) table_danhsach_cauhoidasoan_theomade.getModel();
        return model.getRowCount() > 0;
    }

    // Hàm sinh mã câu hỏi tự động
    private String sinhMaCauHoiTuDong() {
        // Lấy mã câu hỏi cuối cùng từ cơ sở dữ liệu
        String lastMaCH = CauHoi_DAO.get_MaCH_CuoiCung();
        if (lastMaCH == null) {
            lastMaCH = "CH001"; // Nếu không có mã nào trong cơ sở dữ liệu, sử dụng mã mặc định CH001
        } else {
            // Tạo mã câu hỏi mới bằng cách tăng số đếm trong mã câu hỏi cuối cùng
            int lastNum = Integer.parseInt(lastMaCH.substring(2));
            lastNum++; // Tăng số đếm lên 1
            // Format lại mã câu hỏi mới
            String newNum = String.format("%03d", lastNum); // Định dạng số đếm thành chuỗi có độ dài 3 ký tự, bắt đầu bằng các số 0 nếu cần thiết
            lastMaCH = "CH" + newNum;
        }
        return lastMaCH;
    }
    // Khai báo biến để lưu trữ số lượng câu hỏi từ tất cả các lần chọn file trước đó
    private int tongSoLuongCauHoi = 0;

    // Biến boolean để kiểm tra xem đã import file thành công chưa
    private boolean daImportFileThanhCong = false;

    private void btn_chon_cauhoi_tufileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chon_cauhoi_tufileActionPerformed
        // TODO add your handling code here:       

        String maDeThi = txt_madethi_soan.getText();

        // Kiểm tra nếu đã tồn tại dữ liệu trong bảng
        if (daTonTaiCauHoiTrongBang()) {
            int confirmed = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá hết dữ liệu cũ trước khi import file mới?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                // Xoá hết dữ liệu trong bảng
                DefaultTableModel model = (DefaultTableModel) table_danhsach_cauhoidasoan_theomade.getModel();
                model.setRowCount(0); // Xoá tất cả các hàng trong bảng
                tongSoLuongCauHoi = 0; // Reset lại tổng số lượng câu hỏi

                xoaTatCaCauHoiCuaDeThi(maDeThi);
                daImportFileThanhCong = false; // Đánh dấu là chưa import file thành công
            } else {
                return; // Nếu người dùng không muốn xoá dữ liệu cũ, thoát khỏi phương thức
            }
        }

//        // Kiểm tra nếu đã tồn tại dữ liệu trong bảng
//        if (daTonTaiCauHoiTrongBang()) {
//            JOptionPane.showMessageDialog(this, "Chỉ cho phép import câu hỏi từ File 1 lần. Vui lòng Tạo 1 đề thi mới hoặc xoá hết dữ liệu cũ nếu muốn import file mới.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
        // TODO add your handling code here:
        // Kiểm tra các trường không được để trống
        if (date_chooser.getDate() == null || txt_thoigianlambaithi.getText().isEmpty() || txt_thoigianbatdau.getText().isEmpty() || txt_thoigianketthuc.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Có thể: Ngày thi, Thời gian làm bài thi, Thời gian bắt đầu, Thời gian kết thúc chưa được thiết lập ! Vui lòng điền đầy đủ thông tin trước khi chọn file.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tách số thời gian ra khỏi phần "phút" và kiểm tra thời gian làm bài thi phải lớn hơn 0
        String thoiGianLamBai = txt_thoigianlambaithi.getText();
        String[] thoiGianParts = thoiGianLamBai.split(" ");
        int thoiGianLamBaiInt = Integer.parseInt(thoiGianParts[0]);
        if (thoiGianLamBaiInt <= 0) {
            JOptionPane.showMessageDialog(this, "Thời gian làm bài thi phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu thời gian làm bài thi không đạt yêu cầu
        }

        // Lấy số lượng câu hỏi tối đa từ txt_soluongcauhoi_soan
        int soLuongCauHoiToiDa = Integer.parseInt(txt_soluongcauhoi_soan.getText().trim());

        // Tạo một đối tượng JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Chỉ cho phép chọn file có định dạng Excel
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        // Hiển thị hộp thoại chọn file và lặp lại quá trình cho đến khi người dùng không muốn chọn file nữa
        while (true) {
            // Hiển thị hộp thoại chọn file
            int result = fileChooser.showOpenDialog(this);

            // Kiểm tra nếu người dùng đã chọn file
            if (result == JFileChooser.APPROVE_OPTION) {
                // Lấy đường dẫn của file được chọn
                File selectedFile = fileChooser.getSelectedFile();

                // Đọc dữ liệu từ file Excel và đưa vào bảng
                try {
                    FileInputStream fileInputStream = new FileInputStream(selectedFile);
                    XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                    XSSFSheet sheet = workbook.getSheetAt(0); // Chọn sheet đầu tiên

                    DefaultTableModel model = (DefaultTableModel) table_danhsach_cauhoidasoan_theomade.getModel();

                    // Đếm số lượng câu hỏi đã thêm vào bảng từ lần chọn file hiện tại
                    int rowCount = 0;

                    // Đọc dữ liệu từ từng hàng trong sheet và đưa vào bảng
                    for (Row row : sheet) {
                        // Kiểm tra nếu dòng không rỗng và có ít nhất một ô không rỗng
                        if (!isEmptyRow(row) && hasNonEmptyCell(row)) {
                            Vector rowData = new Vector();
                            for (Cell cell : row) {
                                switch (cell.getCellType()) {
                                    case STRING:
                                        rowData.add(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        // Chuyển đổi dữ liệu numeric sang dạng string (chỉ hiển thị số nguyên)
                                        rowData.add(String.valueOf((int) cell.getNumericCellValue()));
                                        break;
                                    case BOOLEAN:
                                        rowData.add(cell.getBooleanCellValue());
                                        break;
                                    default:
                                        rowData.add(null);
                                }
                            }
                            model.addRow(rowData);
                            rowCount++;
                        }
                    }

                    workbook.close();
                    fileInputStream.close();

                    // Tính tổng số lượng câu hỏi từ tất cả các lần chọn file trước đó
                    int tongSoLuongCauHoiTruocDo = tongSoLuongCauHoi;

                    // Kiểm tra tổng số lượng câu hỏi từ tất cả các lần chọn file có vượt quá số lượng tối đa hay không
                    if (tongSoLuongCauHoiTruocDo + rowCount > soLuongCauHoiToiDa) {
                        JOptionPane.showMessageDialog(this, "Số lượng câu hỏi vượt quá số lượng tối đa của mã đề (" + soLuongCauHoiToiDa + " câu hỏi)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        model.setRowCount(tongSoLuongCauHoiTruocDo); // Xóa các dòng mới thêm vào bảng
                    } else {
                        // Cập nhật tổng số lượng câu hỏi
                        tongSoLuongCauHoi += rowCount;

                        // Lưu vào cơ sở dữ liệu
                        for (int i = 0; i < model.getRowCount(); i++) {
                            // Trong mỗi lần lặp, một mảng rowDataArray được tạo với kích thước bằng số cột của bảng.
                            String[] rowDataArray = new String[model.getColumnCount()];
                            for (int j = 0; j < model.getColumnCount(); j++) {
                                rowDataArray[j] = String.valueOf(model.getValueAt(i, j));
                            }

                            // Sinh mã câu hỏi tự động
                            String newMaCH = sinhMaCauHoiTuDong();
                            // Thêm mã câu hỏi vào dòng dữ liệu
                            rowDataArray[model.getColumnCount() - 1] = newMaCH;

                            // Gọi phương thức themCauHoiVaChiTietDeThi với mảng rowDataArray
                            v6_themCauHoiVaChiTietDeThi(rowDataArray);
                        }

                        // Đánh dấu đã import file thành công
                        daImportFileThanhCong = true;

                        btn_refreshActionPerformed(evt);
                        btn_hienthi_danhsachcauhoi_theomadeActionPerformed(evt);

                        break; // Thoát khỏi vòng lặp while
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Không thể đọc dữ liệu từ file", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                // Người dùng không chọn file nữa, thoát khỏi vòng lặp while
                break;
            }
        }
    }//GEN-LAST:event_btn_chon_cauhoi_tufileActionPerformed

    private void btn_suathongtin_chitietdethiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suathongtin_chitietdethiActionPerformed
        // TODO add your handling code here:
        String maDeThi = txt_madethi_soan.getText();
        Date ngayThi = date_chooser.getDate();
        String thoigianlambaithi = txt_thoigianlambaithi.getText();
        String thoigianbatdau = txt_thoigianbatdau.getText();
        String thoigianketthuc = txt_thoigianketthuc.getText();
        String maNV = Login_GUI.loggedInUser;

        int thoiGianLamBaiInt = 0;
        try {
            // Tách số thời gian ra khỏi phần "phút" và kiểm tra thời gian làm bài thi phải lớn hơn 0
            String[] thoiGianParts = thoigianlambaithi.split(" ");
            thoiGianLamBaiInt = Integer.parseInt(thoiGianParts[0]);
            if (thoiGianLamBaiInt <= 0) {
                JOptionPane.showMessageDialog(this, "Thời gian làm bài thi phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức nếu thời gian làm bài thi không đạt yêu cầu
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thời gian làm bài thi là một số nguyên dương", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu thời gian làm bài thi không phải là một số nguyên dương
        }

        /*
        Ví dụ: Bắt đầu 10h đổi sang kiểu Date = 36.000.000 mili giây
               Kết thúc 10h30 = 37.800.000 mili giây
               Tính toán thời gian chênh lệch = 1.800.000 mili giây
               chia cho 1000 để chuyển mili giây sang giây
               chia tiếp cho 60 để chuyển từ giây sang phút             
         */
        // Kiểm tra thời gian bắt đầu và thời gian kết thúc có hợp lệ
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date batDau = dateFormat.parse(thoigianbatdau);
            Date ketThuc = dateFormat.parse(thoigianketthuc);
            long diffInMillies = ketThuc.getTime() - batDau.getTime();
            long diffInMinutes = (diffInMillies / 1000) / 60;

            if (diffInMinutes != thoiGianLamBaiInt) {
                JOptionPane.showMessageDialog(this, "Thời gian bắt đầu và kết thúc không nằm trong khoảng thời gian làm bài thi", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức nếu thời gian không hợp lệ
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng thời gian không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu định dạng thời gian không hợp lệ
        }

        try {
            DBConnect conn = new DBConnect();
            Connection connection = conn.GetConnect(); // Lấy kết nối đến cơ sở dữ liệu

            // Câu lệnh SQL để cập nhật thông tin chi tiết đề thi
            String sql = "{call NV001.Sua4ChiTietDeThi (?, ?, ?, ?, ?, ?)}";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Chuyển đổi ngày thi từ java.util.Date sang java.sql.Date
                java.sql.Date sqlDate = new java.sql.Date(ngayThi.getTime());

                // Đặt các tham số cho câu lệnh SQL
                preparedStatement.setString(1, maDeThi);
                preparedStatement.setDate(2, sqlDate);
                preparedStatement.setString(3, thoigianlambaithi);
                preparedStatement.setString(4, thoigianbatdau);
                preparedStatement.setString(5, thoigianketthuc);
                preparedStatement.setString(6, maNV);

                // Thực thi câu lệnh SQL
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin chi tiết đề thi thành công!");

                    txt_thoigianlambaithi.setText(thoigianlambaithi);
                    txt_thoigianbatdau.setText(thoigianbatdau);
                    txt_thoigianketthuc.setText(thoigianketthuc);
                    date_chooser.setDate(ngayThi);
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật không thành công!");
                }
            }
        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_suathongtin_chitietdethiActionPerformed

    private void xoaTatCaCauHoiCuaDeThi(String maDeThi) {
        try {
            // Gọi thủ tục xoá tất cả các câu hỏi của một mã đề
            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();
            String maNV = Login_GUI.loggedInUser;
            String sql = "{call NV001.v1_XoaAllTheoMaDeThi(?,?)}";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, maDeThi);
                preparedStatement.setString(2, maNV);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Nếu thành công, hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Xoá tất cả câu hỏi của đề thi thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Kiểm tra nếu đã tồn tại dữ liệu trong bảng, nếu có, xóa tất cả các dòng dữ liệu trước khi đọc file
                    DefaultTableModel model = (DefaultTableModel) table_danhsach_cauhoidasoan_theomade.getModel();
                    if (model.getRowCount() > 0) {
                        model.setRowCount(0); // Xóa tất cả các dòng trong bảng
                    }

                    // Tải lại dữ liệu cho bảng
                    btn_refreshActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                    btn_hienthi_danhsachcauhoi_theomadeActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

                } else {
                    // Nếu không thành công, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(this, "Không thể xoá tất cả câu hỏi của đề thi, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
            java.util.logging.Logger.getLogger(frm_SoanCauHoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_SoanCauHoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_SoanCauHoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_SoanCauHoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_SoanCauHoi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public com.raven.swing.TimePicker TimePicker_ThoiGianBatDau;
    public com.raven.swing.TimePicker TimePicker_ThoiGianKetThuc;
    private java.awt.Button btn_chon_cauhoi_tufile;
    private javax.swing.JButton btn_hienthi_danhsachcauhoi_theomade;
    private javax.swing.JButton btn_quaylai;
    private javax.swing.JButton btn_quaylai_frmTaoDeThi;
    protected javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_suacauhoi;
    private javax.swing.JButton btn_suathongtin_chitietdethi;
    private javax.swing.JButton btn_themcauhoi;
    private javax.swing.JButton btn_xoacauhoi;
    protected com.toedter.calendar.JDateChooser date_chooser;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_danhsach_cauhoidasoan_theomade;
    private javax.swing.JTextField txt_A;
    private javax.swing.JTextField txt_B;
    private javax.swing.JTextField txt_C;
    private javax.swing.JTextField txt_D;
    private javax.swing.JTextField txt_DUNG;
    protected javax.swing.JTextField txt_ma_GV_soan;
    protected javax.swing.JTextField txt_ma_monhoc_soan;
    protected javax.swing.JTextField txt_macauhoi_2;
    protected javax.swing.JTextField txt_macauhoi_sinhtudong;
    protected javax.swing.JTextField txt_madethi_soan;
    protected javax.swing.JTextField txt_ngaysoan_soan;
    private javax.swing.JTextArea txt_noidungcauhoi;
    protected javax.swing.JTextField txt_soluongcauhoi_soan;
    private javax.swing.JTextField txt_tenchucvu;
    public javax.swing.JTextField txt_thoigianbatdau;
    public javax.swing.JTextField txt_thoigianketthuc;
    protected javax.swing.JTextField txt_thoigianlambaithi;
    protected javax.swing.JTextField txt_trangthai_soan;
    // End of variables declaration//GEN-END:variables
}
