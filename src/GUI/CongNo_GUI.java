/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.CongNo_DAO;
import DAO.DBConnect;
import DAO.HocSinh_DAO;
import POJO.CongNo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.io.File;
import javax.swing.JFileChooser;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.util.ArrayList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import java.util.Vector;

import POJO.CongNo_POJO;
import com.lowagie.text.Rectangle;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.util.Hashtable;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author SONDAY
 */
public final class CongNo_GUI extends javax.swing.JFrame {

    Vector tblData_CongNo = new Vector();
    Vector tblTitle_CongNo = new Vector();
    DefaultTableModel tblModel_CongNo;

    private Managanent_GUI qlc;
    static ArrayList<CongNo> dscn = CongNo_DAO.LayThongTin_CongNo();

    /**
     * Creates new form CongNo_GUI
     */
    public CongNo_GUI() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Quản Lý Công Nợ");
        hienthi_congno();
        laydulieu_congno(dscn);

        this.txt_tenchucvu.setText(Login_GUI.chucvu_User);
        this.txt_tenchucvu.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_tenchucvu.setEditable(false);

        // Tạo mô hình lấy danh sách mã học sinh
        ArrayList<String> maHSList = CongNo_DAO.get_MaHocSinhList();
        DefaultComboBoxModel<String> model_HSList = new DefaultComboBoxModel<>(maHSList.toArray(new String[0]));
        cbo_mahs.setModel(model_HSList);

        // Tạo mô hình cho cboTrangThaiCongNo
        DefaultComboBoxModel<String> modelTrangThaiCongNo = new DefaultComboBoxModel<>();

        // Thêm các trạng thái khác vào combobox
        modelTrangThaiCongNo.addElement("Chua thanh toan");
        modelTrangThaiCongNo.addElement("Da thanh toan");

        // Thiết lập mô hình cho combobox
        setComboBoxModel(cbo_trangthai_congno, modelTrangThaiCongNo);
        cbo_trangthai_congno.setSelectedItem("Chua thanh toan");
        txt_ma_congno.setEditable(true);
        txt_tienhocphi.setEditable(true);
        txt_tienan.setEditable(true);
        txt_tienphuthu.setEditable(true);
        txt_tongcongno.setEditable(true);

        this.txt_ma_congno.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_tienhocphi.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_tienan.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_tienphuthu.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_tongcongno.setHorizontalAlignment(SwingConstants.CENTER);

        cbo_mahs.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                renderer.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa văn bản
                return renderer;
            }
        });

        this.txt_macongno_sinhtudong.setHorizontalAlignment(SwingConstants.CENTER);
        this.txt_macongno_sinhtudong.setEditable(false);
        this.txt_ma_congno.setEditable(false);
        this.txt_tongcongno.setEditable(false);

        ((JTextField) date_chooser.getDateEditor().getUiComponent()).setHorizontalAlignment(SwingConstants.CENTER);

        // Lấy mã công nợ cuối cùng từ cơ sở dữ liệu
        String lastMaCongNo = CongNo_DAO.get_MaCongNo_CuoiCung();
        if (lastMaCongNo == null) {
            lastMaCongNo = "CN001"; // Nếu không có mã nào trong cơ sở dữ liệu, sử dụng mã mặc định CN001
        } else {
            // Tạo mã công nợ mới bằng cách tăng số đếm trong mã công nợ cuối cùng
            int lastNum = 0;
            if (lastMaCongNo.length() > 2) {
                lastNum = Integer.parseInt(lastMaCongNo.substring(2)); // Lấy số đếm từ mã công nợ cuối cùng bằng cách loại bỏ ký tự "CN"
            }
            lastNum++; // Tăng số đếm lên 1
            // Format lại mã công nợ mới
            String newNum = String.format("%03d", lastNum); // Định dạng số đếm thành chuỗi có độ dài 3 ký tự, bắt đầu bằng các số 0 nếu cần thiết
            lastMaCongNo = "CN" + newNum; // Ghép số đếm vào chuỗi "CN" để tạo mã công nợ mới
        }
        txt_macongno_sinhtudong.setText(lastMaCongNo);
        btn_refreshActionPerformed(new java.awt.event.ActionEvent(this, java.awt.event.ActionEvent.ACTION_PERFORMED, "Refresh"));
    }

    // Hàm để thiết lập mô hình cho JComboBox
    private void setComboBoxModel(JComboBox<String> comboBox, DefaultComboBoxModel<String> model) {
        comboBox.setModel(model);
    }

    public void hienthi_congno() {
        tblTitle_CongNo.add("Mã công nợ");
        tblTitle_CongNo.add("Mã học sinh");
        tblTitle_CongNo.add("Ngày tạo công nợ");
        tblTitle_CongNo.add("Tiền học phí");
        tblTitle_CongNo.add("Tiền ăn");
        tblTitle_CongNo.add("Tiền phụ thu");
        tblTitle_CongNo.add("Tổng công nợ");
        tblTitle_CongNo.add("Trạng thái");
    }

    public void laydulieu_congno(ArrayList<CongNo> ds_congno) {
        tblData_CongNo.removeAllElements();
        for (CongNo cn : ds_congno) {
            Vector v = new Vector();
            v.add(cn.getMaCongNo());
            v.add(cn.getMaHocSinh());
            v.add(cn.getNgayTaoCongNo());
            v.add(cn.getTienHocPhi());
            v.add(cn.getTienAn());
            v.add(cn.getTienPhuThu());
            v.add(cn.getTongCongNo());
            v.add(cn.getTrangThai());
            tblData_CongNo.add(v);
        }
        table_danhsach_congno.setModel(new DefaultTableModel(tblData_CongNo, tblTitle_CongNo));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        table_danhsach_congno = new javax.swing.JTable();
        txt_ma_congno = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btn_sua_congno = new javax.swing.JButton();
        btn_them_congno = new javax.swing.JButton();
        btn_xoa_congno = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btn_trove = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        date_chooser = new com.toedter.calendar.JDateChooser();
        txt_tienhocphi = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_tienan = new javax.swing.JTextField();
        txt_tienphuthu = new javax.swing.JTextField();
        txt_tongcongno = new javax.swing.JTextField();
        cbo_trangthai_congno = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btn_timkiem_congno_theo_mahocsinh = new javax.swing.JButton();
        btn_tinh_tongcongno = new javax.swing.JButton();
        cbo_mahs = new javax.swing.JComboBox<>();
        btn_loc_theongay = new javax.swing.JButton();
        btn_xuatcongno = new javax.swing.JButton();
        txt_tenchucvu = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_macongno_sinhtudong = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách các công nợ:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        table_danhsach_congno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_danhsach_congnoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_danhsach_congno);

        txt_ma_congno.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_ma_congno.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_ma_congno, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_ma_congno, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        btn_sua_congno.setBackground(new java.awt.Color(153, 255, 255));
        btn_sua_congno.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_sua_congno.setText("Cập nhật");
        btn_sua_congno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sua_congnoActionPerformed(evt);
            }
        });

        btn_them_congno.setBackground(new java.awt.Color(153, 255, 255));
        btn_them_congno.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_them_congno.setText("Thêm công nợ");
        btn_them_congno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_them_congnoActionPerformed(evt);
            }
        });

        btn_xoa_congno.setBackground(new java.awt.Color(153, 255, 255));
        btn_xoa_congno.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_xoa_congno.setText("Xoá");
        btn_xoa_congno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoa_congnoActionPerformed(evt);
            }
        });

        btn_refresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh3.png"))); // NOI18N
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btn_sua_congno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_them_congno, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                    .addComponent(btn_xoa_congno, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_them_congno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_sua_congno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_xoa_congno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addComponent(btn_refresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setText("QUẢN LÝ CÔNG NỢ");

        btn_trove.setText("Trở về");
        btn_trove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_troveActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin công nợ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Mã học sinh:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Ngày tạo công nợ:");

        date_chooser.setForeground(new java.awt.Color(255, 0, 51));
        date_chooser.setDateFormatString("yyyy-MM-dd");

        txt_tienhocphi.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_tienhocphi.setForeground(new java.awt.Color(255, 0, 51));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Tiền học phí:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Tiền ăn:");

        txt_tienan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_tienan.setForeground(new java.awt.Color(255, 0, 51));

        txt_tienphuthu.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_tienphuthu.setForeground(new java.awt.Color(255, 0, 51));

        txt_tongcongno.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_tongcongno.setForeground(new java.awt.Color(255, 0, 51));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Trạng thái:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Tổng công nợ:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Tiền phụ thu:");

        btn_timkiem_congno_theo_mahocsinh.setBackground(new java.awt.Color(153, 255, 255));
        btn_timkiem_congno_theo_mahocsinh.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_timkiem_congno_theo_mahocsinh.setText("Tìm kiếm");
        btn_timkiem_congno_theo_mahocsinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timkiem_congno_theo_mahocsinhActionPerformed(evt);
            }
        });

        btn_tinh_tongcongno.setBackground(new java.awt.Color(153, 255, 255));
        btn_tinh_tongcongno.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_tinh_tongcongno.setText("Tính tổng");
        btn_tinh_tongcongno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tinh_tongcongnoActionPerformed(evt);
            }
        });

        cbo_mahs.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cbo_mahs.setForeground(new java.awt.Color(255, 0, 51));
        cbo_mahs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_loc_theongay.setBackground(new java.awt.Color(153, 255, 255));
        btn_loc_theongay.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_loc_theongay.setText("Lọc ngày");
        btn_loc_theongay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loc_theongayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date_chooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_tienphuthu, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tienan, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(txt_tienhocphi)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(59, 59, 59)
                                .addComponent(cbo_mahs, 0, 196, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))
                                .addGap(43, 43, 43)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbo_trangthai_congno, 0, 197, Short.MAX_VALUE)
                                    .addComponent(txt_tongcongno))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_tinh_tongcongno)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btn_loc_theongay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_timkiem_congno_theo_mahocsinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbo_mahs, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_timkiem_congno_theo_mahocsinh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txt_tienhocphi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(btn_loc_theongay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tienan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tienphuthu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tongcongno, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_tinh_tongcongno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_trangthai_congno, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        btn_xuatcongno.setBackground(new java.awt.Color(255, 255, 0));
        btn_xuatcongno.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btn_xuatcongno.setText("IN CÔNG NỢ");
        btn_xuatcongno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xuatcongnoActionPerformed(evt);
            }
        });

        txt_tenchucvu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_tenchucvu.setForeground(new java.awt.Color(255, 0, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Mã công nợ được tạo tự động:");

        txt_macongno_sinhtudong.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_macongno_sinhtudong.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(430, 430, 430)
                        .addComponent(txt_tenchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_trove))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btn_xuatcongno, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_macongno_sinhtudong, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tenchucvu, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_trove, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_xuatcongno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(142, 142, 142)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_macongno_sinhtudong, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_troveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_troveActionPerformed
        // TODO add your handling code here:
        Managanent_GUI qlc = new Managanent_GUI();
        qlc.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_troveActionPerformed

    private void btn_sua_congnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sua_congnoActionPerformed
        // TODO add your handling code here:
        // Lấy thông tin từ các thành phần giao diện
        String ma_congno = txt_ma_congno.getText();
        String ma_hocsinh = cbo_mahs.getSelectedItem().toString();
        String tien_hocphi = txt_tienhocphi.getText();
        String tien_an = txt_tienan.getText();
        String tien_phuthu = txt_tienphuthu.getText();
        String tong_congno = txt_tongcongno.getText();
        String trangThai = cbo_trangthai_congno.getSelectedItem().toString();
        String maNV = Login_GUI.loggedInUser;
        // Kiểm tra các trường thông tin đầy đủ
        if (ma_congno.isEmpty() || ma_hocsinh.isEmpty() || tien_hocphi.isEmpty() || tien_an.isEmpty() || tien_phuthu.isEmpty() || tong_congno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu có trường thông tin trống
        }

        // Kiểm tra định dạng của tiền học phí, tiền ăn, tiền phụ thu, tổng công nợ không được <= 0
        try {
            int kt_tienhocphi = Integer.parseInt(tien_hocphi);
            int kt_tienan = Integer.parseInt(tien_an);
            int kt_tienphuthu = Integer.parseInt(tien_phuthu);
            int kt_tongcongno = Integer.parseInt(tong_congno);
            if (kt_tienhocphi < 0 || kt_tienan < 0 || kt_tienphuthu < 0 || kt_tongcongno < 0) {
                JOptionPane.showMessageDialog(this, "Tiền học phí, tiền ăn, tiền phụ thu, tổng công nợ phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức nếu không hợp lệ
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Định dạng của tiền học phí, tiền ăn, tiền phụ thu, tổng công nợ không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu không phải là số
        }

        try {
            // Kiểm tra xem mã công nợ đã tồn tại trong cơ sở dữ liệu chưa
            if (!CongNo_DAO.KiemTraTonTaiMaCongNo(ma_congno)) {
                // Nếu mã công nợ chưa tồn tại, hiển thị thông báo lỗi
                JOptionPane.showMessageDialog(this, "Mã công nợ không tồn tại, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức để không thực hiện các bước tiếp theo
            }

            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục
            String sql = "{call NV001.E1_SuaCongNo(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            // Tạo PreparedStatement và thực thi
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Lấy ngày từ JDateChooser
                java.util.Date ngayTaoCongNoUtil = date_chooser.getDate();

                // Chuyển đổi ngày từ java.util.Date thành java.sql.Date
                java.sql.Date ngayTaoCongNoSql = new java.sql.Date(ngayTaoCongNoUtil.getTime());

                // Đặt các tham số cho câu lệnh SQL
                preparedStatement.setString(1, ma_congno);
                preparedStatement.setString(2, ma_hocsinh);
                preparedStatement.setDate(3, ngayTaoCongNoSql);
                preparedStatement.setInt(4, Integer.parseInt(tien_hocphi));
                preparedStatement.setInt(5, Integer.parseInt(tien_an));
                preparedStatement.setInt(6, Integer.parseInt(tien_phuthu));
                preparedStatement.setInt(7, Integer.parseInt(tong_congno));
                preparedStatement.setString(8, trangThai);
                preparedStatement.setString(9, maNV);

                // Thực thi câu lệnh SQL
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Nếu thành công, hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Sửa công nợ thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    // Cập nhật lại dữ liệu trong bảng hiển thị
                    ArrayList<CongNo> ds_congno_refreshed = CongNo_DAO.LayThongTin_CongNo();
                    laydulieu_congno(ds_congno_refreshed);
                } else {
                    // Nếu không thành công, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(this, "Không thể sửa công nợ, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_sua_congnoActionPerformed

    private void table_danhsach_congnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_danhsach_congnoMouseClicked
        // TODO add your handling code here:
        // Lấy chỉ số của dòng đã chọn trong bảng
        int rowIndex = table_danhsach_congno.getSelectedRow();
        // Kiểm tra xem đã chọn một dòng trong bảng chưa
        if (rowIndex >= 0) {
            // Lấy giá trị từ bảng và đặt lên các trường nhập liệu
            txt_ma_congno.setText(table_danhsach_congno.getValueAt(rowIndex, 0).toString());
            String maHS = table_danhsach_congno.getValueAt(rowIndex, 1).toString();
            cbo_mahs.setSelectedItem(maHS);
            txt_tienhocphi.setText(table_danhsach_congno.getValueAt(rowIndex, 3).toString());
            txt_tienan.setText(table_danhsach_congno.getValueAt(rowIndex, 4).toString());
            txt_tienphuthu.setText(table_danhsach_congno.getValueAt(rowIndex, 5).toString());
            txt_tongcongno.setText(table_danhsach_congno.getValueAt(rowIndex, 6).toString());
            cbo_trangthai_congno.setSelectedItem(table_danhsach_congno.getValueAt(rowIndex, 7).toString());

            // Hiển thị ngày tạo công nợ lên date chooser
            try {
                String ngayTaoCongNo = table_danhsach_congno.getValueAt(rowIndex, 2).toString();
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(ngayTaoCongNo);
                date_chooser.setDate(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_table_danhsach_congnoMouseClicked


    private void btn_them_congnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_them_congnoActionPerformed
        // TODO add your handling code here:
        // Lấy thông tin từ các thành phần giao diện
        // LÀM THÊM
        String macongno_tudong = txt_macongno_sinhtudong.getText();

        //String ma_hocsinh = txt_ma_hs.getText();
        String ma_hocsinh = cbo_mahs.getSelectedItem().toString();
        String tien_hocphi = txt_tienhocphi.getText();
        String tien_an = txt_tienan.getText();
        String tien_phuthu = txt_tienphuthu.getText();
        String tong_congno = txt_tongcongno.getText();
        String trangThai = cbo_trangthai_congno.getSelectedItem().toString();
        String maNV = Login_GUI.loggedInUser;
        // Kiểm tra các trường thông tin đầy đủ
        if (macongno_tudong.isEmpty() || ma_hocsinh.isEmpty() || tien_hocphi.isEmpty() || tien_an.isEmpty() || tien_phuthu.isEmpty() || tong_congno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu có trường thông tin trống
        }

        // Kiểm tra định dạng của tiền học phí, tiền ăn, tiền phụ thu, tổng công nợ không được <= 0
        try {
            int kt_tienhocphi = Integer.parseInt(tien_hocphi);
            int kt_tienan = Integer.parseInt(tien_an);
            int kt_tienphuthu = Integer.parseInt(tien_phuthu);
            int kt_tongcongno = Integer.parseInt(tong_congno);
            if (kt_tienhocphi < 0 || kt_tienan < 0 || kt_tienphuthu < 0 || kt_tongcongno < 0) {
                JOptionPane.showMessageDialog(this, "Tiền học phí, tiền ăn, tiền phụ thu, tổng công nợ phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức nếu không hợp lệ
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Định dạng của tiền học phí, tiền ăn, tiền phụ thu, tổng công nợ không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu không phải là số
        }

        try {
            // Kiểm tra xem mã công nợ đã tồn tại trong cơ sở dữ liệu chưa
            if (CongNo_DAO.KiemTraTonTaiMaCongNo(macongno_tudong)) {
                // Nếu mã công nợ đã tồn tại, hiển thị thông báo lỗi
                JOptionPane.showMessageDialog(this, "Mã công nợ đã tồn tại, vui lòng nhập mã khác", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc phương thức để không thực hiện các bước tiếp theo
            }

            DBConnect conn = new DBConnect();
            conn.GetConnect();
            Connection connection = conn.GetConnect();

            // Gọi thủ tục
            String sql = "{call NV001.E1_ThemCongNo(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

            // Tạo PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Lấy ngày từ JDateChooser
                java.util.Date ngayTaoCongNoUtil = date_chooser.getDate();

                // Chuyển đổi ngày từ java.util.Date thành java.sql.Date
                java.sql.Date ngayTaoCongNoSql = new java.sql.Date(ngayTaoCongNoUtil.getTime());

                // Đặt các tham số cho câu lệnh SQL
                preparedStatement.setString(1, macongno_tudong);
                preparedStatement.setString(2, ma_hocsinh);
                preparedStatement.setDate(3, ngayTaoCongNoSql);
                preparedStatement.setInt(4, Integer.parseInt(tien_hocphi));
                preparedStatement.setInt(5, Integer.parseInt(tien_an));
                preparedStatement.setInt(6, Integer.parseInt(tien_phuthu));
                preparedStatement.setInt(7, Integer.parseInt(tong_congno));
                preparedStatement.setString(8, trangThai);
                preparedStatement.setString(9, maNV);
                // Thực thi câu lệnh SQL
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Nếu thành công, hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Thêm công nợ thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Nếu không thành công, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(this, "Không thể thêm công nợ, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                // Sinh mã mới cho công nợ tiếp theo và hiển thị trong txt_congno_sinhtudong
                int lastNumber = Integer.parseInt(macongno_tudong.substring(2)); // Lấy số đếm từ mã công nợ cuối cùng bằng cách loại bỏ ký tự "CN"
                String newMaCongNo = String.format("CN%03d", lastNumber + 1); // Format lại số đếm thành chuỗi có độ dài 3 ký tự, bắt đầu bằng các số 0 nếu cần thiết

                // Cập nhật giá trị maCongNo thành mã mới
                macongno_tudong = newMaCongNo;
                txt_macongno_sinhtudong.setText(newMaCongNo);

            }
            ArrayList<CongNo> ds_congno_refreshed = CongNo_DAO.LayThongTin_CongNo();
            laydulieu_congno(ds_congno_refreshed);

        } catch (SQLException ex) {
            // Xử lý lỗi nếu có
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_them_congnoActionPerformed

    private void btn_xoa_congnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoa_congnoActionPerformed
        // TODO add your handling code here:
        // Lấy chỉ số của tất cả các hàng được chọn trong bảng
        int[] selectedRows = table_danhsach_congno.getSelectedRows();

        // Kiểm tra xem người dùng đã chọn ít nhất một hàng hay chưa
        if (selectedRows.length > 0) {
            // Hiển thị thông báo xác nhận
            int choice = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xoá các công nợ đã chọn?", "Xác nhận xoá công nợ", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                String maNV = Login_GUI.loggedInUser;
                boolean hasError = false;

                try {
                    // Gọi thủ tục xoá công nợ
                    DBConnect conn = new DBConnect();
                    conn.GetConnect();
                    Connection connection = conn.GetConnect();

                    String sql = "{call NV001.E1_XoaCongNo(?, ?)}";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        for (int rowIndex : selectedRows) {
                            // Lấy mã công nợ từ bảng
                            String maCongNo = table_danhsach_congno.getValueAt(rowIndex, 0).toString();

                            preparedStatement.setString(1, maCongNo);
                            preparedStatement.setString(2, maNV);

                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected <= 0) {
                                hasError = true;
                            }
                        }
                    }
                } catch (SQLException ex) {
                    // Xử lý lỗi nếu có
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (hasError) {
                    // Nếu có bất kỳ lỗi nào xảy ra trong quá trình xoá, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(this, "Không thể xoá một hoặc nhiều công nợ, vui lòng kiểm tra lại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Nếu thành công, hiển thị thông báo thành công
                    JOptionPane.showMessageDialog(this, "Xoá công nợ thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                }

                // Làm mới dữ liệu trong bảng
                ArrayList<CongNo> ds_congno_refreshed = CongNo_DAO.LayThongTin_CongNo();
                laydulieu_congno(ds_congno_refreshed);
            }
        } else {
            // Nếu người dùng chưa chọn hàng, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một công nợ để xoá", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_xoa_congnoActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        txt_ma_congno.setText("");
        cbo_mahs.setSelectedIndex(0);
        txt_tienhocphi.setText("");
        txt_tienan.setText("");
        txt_tienphuthu.setText("");
        txt_tongcongno.setText("");
        date_chooser.setDate(null); // Đặt giá trị ngày thành null để xóa ngày được chọn
        cbo_trangthai_congno.setSelectedItem("Chua thanh toan");

        ArrayList<CongNo> ds_congno_refreshed = CongNo_DAO.LayThongTin_CongNo();
        laydulieu_congno(ds_congno_refreshed);
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_timkiem_congno_theo_mahocsinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timkiem_congno_theo_mahocsinhActionPerformed
        // TODO add your handling code here:
        // Lấy mã học sinh từ trường văn bản
        //String maHS = txt_ma_hs.getText();
        String maHS = cbo_mahs.getSelectedItem().toString();

        // Kiểm tra xem người dùng đã nhập mã học sinh hay chưa
        if (maHS.isEmpty()) {
            // Nếu không nhập mã học sinh, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã học sinh để tìm kiếm công nợ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức
        }

        // Lấy danh sách công nợ dựa trên mã học sinh
        ArrayList<CongNo> ds_congno = CongNo_DAO.TimKiemCongNo_TheoMaHocSinh(maHS);

        // Kiểm tra xem danh sách công nợ có bản ghi nào không
        if (ds_congno.isEmpty()) {
            // Nếu không có bản ghi nào tồn tại, hiển thị thông báo
            JOptionPane.showMessageDialog(this, "Không có bản ghi nào tồn tại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Nếu có bản ghi tồn tại, hiển thị danh sách công nợ
            laydulieu_congno(ds_congno);
        }
    }//GEN-LAST:event_btn_timkiem_congno_theo_mahocsinhActionPerformed

    private void xuatCongNoRaFile_v1() throws IOException {
        // Mở cửa sổ lưu file và cho phép người dùng chọn vị trí và đặt tên cho file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file công nợ"); // Tiêu đề cửa sổ
        int userSelection = fileChooser.showSaveDialog(this);
        // Kiểm tra xem người dùng đã chọn nút Lưu hay không
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn đến file được chọn
            File fileToSave = fileChooser.getSelectedFile();

            // Tạo một đối tượng BufferedWriter để viết dữ liệu ra file
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));

            // Ghi tiêu đề của bảng vào file
            for (int i = 0; i < table_danhsach_congno.getColumnCount(); i++) {
                writer.write(table_danhsach_congno.getColumnName(i) + "\t");
            }
            writer.write("\n");

            // Ghi dữ liệu từ bảng vào file
            for (int i = 0; i < table_danhsach_congno.getRowCount(); i++) {
                for (int j = 0; j < table_danhsach_congno.getColumnCount(); j++) {
                    writer.write(table_danhsach_congno.getValueAt(i, j).toString() + "\t");
                }
                writer.write("\n");
            }

            // Đóng luồng ghi
            writer.close();

            // Hiển thị thông báo xuất file thành công
            JOptionPane.showMessageDialog(this, "Xuất công nợ ra file thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // xuat txt
    private void xuatCongNoRaFile_v2() throws IOException {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (table_danhsach_congno.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất ra file", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mở cửa sổ lưu file và cho phép người dùng chọn vị trí và đặt tên cho file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file công nợ"); // Tiêu đề cửa sổ
        int userSelection = fileChooser.showSaveDialog(this);

        // Kiểm tra xem người dùng đã chọn nút Lưu hay không
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn và tên file được chọn
            File fileToSave = fileChooser.getSelectedFile();

            // Đảm bảo rằng đuôi file là .txt
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".txt")) {
                fileToSave = new File(filePath + ".txt");
            }

            // Sử dụng try-with-resources để tự động đóng luồng ghi dữ liệu
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                // Ghi tiêu đề của bảng vào file
                for (int i = 0; i < table_danhsach_congno.getColumnCount(); i++) {
                    writer.write(table_danhsach_congno.getColumnName(i) + "\t");
                }
                writer.write("\n");

                // Ghi dữ liệu từ bảng vào file
                for (int i = 0; i < table_danhsach_congno.getRowCount(); i++) {
                    for (int j = 0; j < table_danhsach_congno.getColumnCount(); j++) {
                        writer.write(table_danhsach_congno.getValueAt(i, j).toString() + "\t");
                    }
                    writer.write("\n");
                }

                // Hiển thị thông báo xuất file thành công
                JOptionPane.showMessageDialog(this, "Xuất công nợ ra file thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void xuatCongNoRaPDF_v3() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (table_danhsach_congno.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất ra file", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mở cửa sổ lưu file và cho phép người dùng chọn vị trí và đặt tên cho file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file công nợ"); // Tiêu đề cửa sổ
        int userSelection = fileChooser.showSaveDialog(this);

        // Kiểm tra xem người dùng đã chọn nút Lưu hay không
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn và tên file được chọn
            File fileToSave = fileChooser.getSelectedFile();

            // Đảm bảo rằng đuôi file là .pdf
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(filePath + ".pdf");
            }

            // Tạo một tài liệu PDF mới
            Document document = new Document(PageSize.A4);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Tạo một bảng để chứa dữ liệu
                PdfPTable table = new PdfPTable(table_danhsach_congno.getColumnCount());

                // Thêm tiêu đề của bảng
                for (int i = 0; i < table_danhsach_congno.getColumnCount(); i++) {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new com.lowagie.text.Phrase(table_danhsach_congno.getColumnName(i)));
                    table.addCell(cell);
                }

                // Thêm dữ liệu từ bảng vào tài liệu PDF
                for (int i = 0; i < table_danhsach_congno.getRowCount(); i++) {
                    for (int j = 0; j < table_danhsach_congno.getColumnCount(); j++) {
                        table.addCell(table_danhsach_congno.getValueAt(i, j).toString());
                    }
                }

                document.add(table);
                document.close();

                // Hiển thị thông báo xuất file thành công
                JOptionPane.showMessageDialog(this, "Xuất công nợ ra file PDF thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | FileNotFoundException ex) {
                Logger.getLogger(CongNo_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // xuat PDF
    private void xuatCongNoRaPDF_v4() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (table_danhsach_congno.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất ra file", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mở cửa sổ lưu file và cho phép người dùng chọn vị trí và đặt tên cho file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file công nợ"); // Tiêu đề cửa sổ
        int userSelection = fileChooser.showSaveDialog(this);

        // Kiểm tra xem người dùng đã chọn nút Lưu hay không
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn và tên file được chọn
            File fileToSave = fileChooser.getSelectedFile();

            // Đảm bảo rằng đuôi file là .pdf
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(filePath + ".pdf");
            }

            // Tạo một tài liệu PDF mới
            Document document = new Document(PageSize.A4);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Tiêu đề của tài liệu
                Paragraph title = new Paragraph("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Xuống dòng sau tiêu đề
                document.add(new Paragraph("\n"));

                // Tạo một bảng để chứa dữ liệu
                PdfPTable table = new PdfPTable(table_danhsach_congno.getColumnCount());

                // Thêm tiêu đề của bảng
                for (int i = 0; i < table_danhsach_congno.getColumnCount(); i++) {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new com.lowagie.text.Phrase(table_danhsach_congno.getColumnName(i)));
                    table.addCell(cell);
                }

                // Thêm dữ liệu từ bảng vào tài liệu PDF
                for (int i = 0; i < table_danhsach_congno.getRowCount(); i++) {
                    for (int j = 0; j < table_danhsach_congno.getColumnCount(); j++) {
                        table.addCell(table_danhsach_congno.getValueAt(i, j).toString());
                    }
                }

                document.add(table);
                document.close();

                // Hiển thị thông báo xuất file thành công
                JOptionPane.showMessageDialog(this, "Xuất công nợ ra file PDF thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | FileNotFoundException ex) {
                Logger.getLogger(CongNo_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // chinh duoc size cot
    private void xuatCongNoRaPDF_v5() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (table_danhsach_congno.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất ra file", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mở cửa sổ lưu file và cho phép người dùng chọn vị trí và đặt tên cho file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file công nợ"); // Tiêu đề cửa sổ
        int userSelection = fileChooser.showSaveDialog(this);

        // Kiểm tra xem người dùng đã chọn nút Lưu hay không
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn và tên file được chọn
            File fileToSave = fileChooser.getSelectedFile();

            // Đảm bảo rằng đuôi file là .pdf
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(filePath + ".pdf");
            }

            // Tạo một tài liệu PDF mới
            Document document = new Document(PageSize.A4);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Tiêu đề của tài liệu
                Paragraph title = new Paragraph("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Xuống dòng sau tiêu đề
                document.add(new Paragraph("\n"));

                // Tạo một bảng để chứa dữ liệu
                PdfPTable table = new PdfPTable(table_danhsach_congno.getColumnCount());

                // Thêm tiêu đề của bảng với kích thước chữ tùy chỉnh
                for (int i = 0; i < table_danhsach_congno.getColumnCount(); i++) {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new com.lowagie.text.Phrase(table_danhsach_congno.getColumnName(i), FontFactory.getFont(FontFactory.HELVETICA, 5)));
                    table.addCell(cell);
                }

                // Thêm dữ liệu từ bảng vào tài liệu PDF
                for (int i = 0; i < table_danhsach_congno.getRowCount(); i++) {
                    for (int j = 0; j < table_danhsach_congno.getColumnCount(); j++) {
                        table.addCell(table_danhsach_congno.getValueAt(i, j).toString());
                    }
                }

                document.add(table);
                document.close();

                // Hiển thị thông báo xuất file thành công
                JOptionPane.showMessageDialog(this, "Xuất công nợ ra file PDF thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | FileNotFoundException ex) {
                Logger.getLogger(CongNo_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // tuy chinh duoc size chu, can giua column 
    private void xuatCongNoRaPDF_v6() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (table_danhsach_congno.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất ra file", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mở cửa sổ lưu file và cho phép người dùng chọn vị trí và đặt tên cho file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file công nợ"); // Tiêu đề cửa sổ
        int userSelection = fileChooser.showSaveDialog(this);

        // Kiểm tra xem người dùng đã chọn nút Lưu hay không
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn và tên file được chọn
            File fileToSave = fileChooser.getSelectedFile();

            // Đảm bảo rằng đuôi file là .pdf
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(filePath + ".pdf");
            }

            // Tạo một tài liệu PDF mới
            Document document = new Document(PageSize.A5);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Tiêu đề của tài liệu
                Paragraph title = new Paragraph("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Xuống dòng sau tiêu đề
                document.add(new Paragraph("\n"));

                // Tạo một bảng để chứa dữ liệu
                PdfPTable table = new PdfPTable(table_danhsach_congno.getColumnCount());

                // Tiêu đề tùy chỉnh của các cột
                String[] customColumnTitles = {"Ma cong no", "Ma hoc sinh", "Ngay tao cong no", "Tien hoc phi", "Tien an", "Tien phu thu", "Tong cong no", "Trang thai"};

                // Thêm tiêu đề của bảng với tiêu đề tùy chỉnh
                for (String title_column : customColumnTitles) {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new com.lowagie.text.Phrase(title_column, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa tiêu đề
                    table.addCell(cell);
                }

                // Thêm dữ liệu từ bảng vào tài liệu PDF
                for (int i = 0; i < table_danhsach_congno.getRowCount(); i++) {
                    for (int j = 0; j < table_danhsach_congno.getColumnCount(); j++) {
                        table.addCell(table_danhsach_congno.getValueAt(i, j).toString());
                    }
                }

                document.add(table);

                // Tạo một đối tượng Paragraph trống để tạo khoảng trống giữa dòng "Ngày xuất hoá đơn" và bảng
                Paragraph emptyLine = new Paragraph(" "); // Tạo một dòng trống
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF

                // Tạo đối tượng để lưu ngày hiện tại
                Date currentDate = new Date();

                // Định dạng ngày hiện tại
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                // Tạo một đối tượng Paragraph chứa thông tin về ngày xuất hoá đơn
                Paragraph ngayXuatHoaDon = new Paragraph("Ngay xuat hoa don: " + dateFormat.format(currentDate), FontFactory.getFont(FontFactory.HELVETICA, 10));
                ngayXuatHoaDon.setAlignment(Element.ALIGN_CENTER); // Căn giữa theo chiều ngang
                document.add(ngayXuatHoaDon); // Thêm dòng ngày xuất hoá đơn vào tài liệu PDF

                document.close();

                // Hiển thị thông báo xuất file thành công
                JOptionPane.showMessageDialog(this, "Xuất công nợ ra file PDF thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | FileNotFoundException ex) {
                Logger.getLogger(CongNo_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // OK nhat
    private void xuatCongNoRaPDF_v7() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (table_danhsach_congno.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất ra file", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mở cửa sổ lưu file và cho phép người dùng chọn vị trí và đặt tên cho file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file công nợ"); // Tiêu đề cửa sổ
        int userSelection = fileChooser.showSaveDialog(this);

        // Kiểm tra xem người dùng đã chọn nút Lưu hay không
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn và tên file được chọn
            File fileToSave = fileChooser.getSelectedFile();

            // Đảm bảo rằng đuôi file là .pdf
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(filePath + ".pdf");
            }

            // Tạo một tài liệu PDF mới
            Document document = new Document(PageSize.A6);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Chèn hình ảnh logo vào tài liệu PDF và điều chỉnh khoảng cách
                try {
                    Image logo = Image.getInstance("src/img/logo_NguyenVietXuan.png");
                    logo.scaleToFit(79, 79);

                    // Căn lề logo sang bên trái với khoảng cách từ mép trái của trang là 20
                    logo.setAbsolutePosition(20, PageSize.A6.getHeight() - logo.getScaledHeight() - 20);

                    document.add(logo);

                    // Thêm một đoạn trống để tạo khoảng cách giữa logo và TOP của trang giấy
                    Paragraph emptySpace = new Paragraph(new Phrase(" "));
                    emptySpace.setSpacingAfter(-20);
                    document.add(emptySpace);
                    document.add(new Paragraph("\n\n"));

                } catch (BadElementException | IOException ex) {
                    Logger.getLogger(CongNo_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Tiêu đề của tài liệu
                Paragraph title = new Paragraph("PHIEU BAO THU TIEN", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Xuống dòng sau tiêu đề
                document.add(new Paragraph("\n"));

                // Tạo một bảng để chứa dữ liệu
                float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f}; // Đặt kích thước cho từng cột
                PdfPTable table = new PdfPTable(columnWidths);
                table.setTotalWidth(PageSize.A6.getWidth() - 20); // Đặt tổng kích thước của bảng
                table.setLockedWidth(true); // Khóa kích thước của bảng

                // Tiêu đề tùy chỉnh của các cột
                String[] customColumnTitles = {"Ma cong no", "Ma hoc sinh", "Ngay tao cong no", "Tien hoc phi", "Tien an", "Tien phu thu", "TONG", "Trang thai"};

                // Thêm tiêu đề của bảng với tiêu đề tùy chỉnh
                for (String title_column : customColumnTitles) {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new com.lowagie.text.Phrase(title_column, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 5)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa tiêu đề
                    table.addCell(cell);
                }

                // Thêm dữ liệu từ bảng vào tài liệu PDF
                for (int i = 0; i < table_danhsach_congno.getRowCount(); i++) {
                    for (int j = 0; j < table_danhsach_congno.getColumnCount(); j++) {
                        PdfPCell cell = new PdfPCell(new Phrase(table_danhsach_congno.getValueAt(i, j).toString(), FontFactory.getFont(FontFactory.HELVETICA, 5)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }
                }

                document.add(table);

                Paragraph emptyLine = new Paragraph(" "); // Tạo một dòng trống
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF
                // Tính tổng giá trị của cột "TONG"
                double tongTongTien = 0;
                for (int i = 0; i < table_danhsach_congno.getRowCount(); i++) {
                    tongTongTien += Double.parseDouble(table_danhsach_congno.getValueAt(i, 6).toString());
                }

                // Định dạng số để hiển thị tổng tiền một cách rõ ràng
                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                String tongTongTienFormatted = decimalFormat.format(tongTongTien);

                // Tạo một đối tượng Chunk chứa thông tin về "SO TIEN THANH TOAN"
                Chunk tongTienThanhToanChunk = new Chunk("                                                   SO TIEN THANH TOAN = " + tongTongTienFormatted, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7));
                Paragraph tongTienThanhToanParagraph = new Paragraph(tongTienThanhToanChunk);
                tongTienThanhToanParagraph.add(new Chunk("                                                                                                 ")); // Tạo một khoảng trống giữa hai Chunk
                tongTienThanhToanParagraph.setAlignment(Element.ALIGN_LEFT);

                // Thêm dòng tính toán vào tài liệu PDF
                document.add(tongTienThanhToanParagraph);

                // Tạo một đối tượng Chunk chứa thông tin về Người nộp tiền
                Chunk nguoiNopTien = new Chunk("Chu ky nguoi nop tien", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6));

                // Tạo một đối tượng Chunk chứa thông tin về Người thu tiền
                Chunk nguoiThuTien = new Chunk("Nguoi thu tien", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6));

                // Tạo một đối tượng Paragraph chứa cả hai Chunk
                Paragraph nguoiNopVaThuTien = new Paragraph();
                nguoiNopVaThuTien.add(nguoiNopTien);
                nguoiNopVaThuTien.add(new Chunk("                                                   ")); // Tạo một khoảng trống giữa hai Chunk
                nguoiNopVaThuTien.add(nguoiThuTien);

                // Đặt căn lề trái và phải cho cả đoạn văn bản
                nguoiNopVaThuTien.setIndentationLeft(-25); // Đặt căn lề trái là 20 đơn vị
                nguoiNopVaThuTien.setIndentationRight(-25); // Đặt căn lề phải là 20 đơn vị

                // Thêm đoạn văn bản vào tài liệu PDF
                document.add(nguoiNopVaThuTien);

                // Tạo một đối tượng Paragraph trống để tạo khoảng trống giữa dòng "Ngày xuất hoá đơn" và bảng
                //Paragraph emptyLine = new Paragraph(" "); // Tạo một dòng trống
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF

                // Tạo đối tượng để lưu ngày hiện tại
                Date currentDate = new Date();

                // Định dạng ngày hiện tại
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                // Tạo một đối tượng Paragraph chứa thông tin về ngày xuất hoá đơn
                // Tạo một đối tượng Font với font chữ Helvetica, kích thước 10, in đậm và in nghiêng
                Font font = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLDITALIC);

                // Tạo một đối tượng Paragraph với nội dung và font chữ đã xác định
                Paragraph ngayXuatHoaDon = new Paragraph("Ngay xuat hoa don: " + dateFormat.format(currentDate), font);

                // Paragraph ngayXuatHoaDon = new Paragraph("Ngay xuat hoa don: " + dateFormat.format(currentDate), FontFactory.getFont(FontFactory.HELVETICA, 10));
                ngayXuatHoaDon.setAlignment(Element.ALIGN_CENTER); // Căn giữa theo chiều ngang
                document.add(ngayXuatHoaDon); // Thêm dòng ngày xuất hoá đơn vào tài liệu PDF

                // Tạo một đối tượng Font với font chữ Helvetica, kích thước 10, in đậm và in nghiêng
                Font font_inc = FontFactory.getFont(FontFactory.HELVETICA, 5, Font.BOLDITALIC);

                // Tạo một đối tượng Paragraph mới chứa nội dung bạn muốn thêm và sử dụng font đã tạo
                Paragraph in = new Paragraph("In tai phong Ke hoach tai chinh * Hotline lien he: 0906 000 768", font_inc);

                // Đặt căn lề cho đối tượng Paragraph mới
                in.setAlignment(Element.ALIGN_CENTER);

                in.setSpacingBefore(6);

                // Thêm đối tượng Paragraph mới vào tài liệu PDF
                document.add(in);

                document.add(new Paragraph("--------------------------------------------------------"));

                document.close();

                // Hiển thị thông báo xuất file thành công
                JOptionPane.showMessageDialog(this, "Xuất công nợ ra file PDF thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | FileNotFoundException ex) {
                Logger.getLogger(CongNo_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void xuatCongNoRaPDF_v8() {
        // Kiểm tra xem bảng có dữ liệu hay không
        if (table_danhsach_congno.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất ra file", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Lấy các dòng được chọn
        int[] selectedRows = table_danhsach_congno.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Không có dòng nào được chọn để xuất ra file", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mở cửa sổ lưu file và cho phép người dùng chọn vị trí và đặt tên cho file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file công nợ"); // Tiêu đề cửa sổ
        int userSelection = fileChooser.showSaveDialog(this);

        // Kiểm tra xem người dùng đã chọn nút Lưu hay không
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn và tên file được chọn
            File fileToSave = fileChooser.getSelectedFile();

            // Đảm bảo rằng đuôi file là .pdf
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(filePath + ".pdf");
            }

            // Tạo một tài liệu PDF mới
            Document document = new Document(PageSize.A6);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                // Chèn hình ảnh logo vào tài liệu PDF và điều chỉnh khoảng cách
                try {
                    Image logo = Image.getInstance("C:\\Users\\SONDAY\\Desktop\\khoaluan_totnghiep\\ALL_tongkhoaluan\\APP\\TongHopApp_Ngay_24-06\\TongHopApp_Ngay_01-05\\DoAnChuyenNganh_NTH\\src\\img\\logo_NguyenVietXuan.png");
                    logo.scaleToFit(79, 79);

                    // Căn lề logo sang bên trái với khoảng cách từ mép trái của trang là 20
                    logo.setAbsolutePosition(20, PageSize.A6.getHeight() - logo.getScaledHeight() - 20);

                    document.add(logo);

                    // Thêm một đoạn trống để tạo khoảng cách giữa logo và TOP của trang giấy
                    Paragraph emptySpace = new Paragraph(new Phrase(" "));
                    emptySpace.setSpacingAfter(-20);
                    document.add(emptySpace);
                    document.add(new Paragraph("\n\n"));

                } catch (BadElementException | IOException ex) {
                    Logger.getLogger(CongNo_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Tiêu đề của tài liệu
                Paragraph title = new Paragraph("PHIEU BAO THU TIEN", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Xuống dòng sau tiêu đề
                document.add(new Paragraph("\n"));

                // Tạo một bảng để chứa dữ liệu
                float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f}; // Đặt kích thước cho từng cột
                PdfPTable table = new PdfPTable(columnWidths);
                table.setTotalWidth(PageSize.A6.getWidth() - 20); // Đặt tổng kích thước của bảng
                table.setLockedWidth(true); // Khóa kích thước của bảng

                // Tiêu đề tùy chỉnh của các cột
                String[] customColumnTitles = {"Ma cong no", "Ma hoc sinh", "Ngay tao cong no", "Tien hoc phi", "Tien an", "Tien phu thu", "TONG", "Trang thai"};

                // Thêm tiêu đề của bảng với tiêu đề tùy chỉnh
                for (String title_column : customColumnTitles) {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new com.lowagie.text.Phrase(title_column, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 5)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa tiêu đề
                    table.addCell(cell);
                }

                // Thêm dữ liệu từ các dòng được chọn vào tài liệu PDF
                for (int row : selectedRows) {
                    for (int j = 0; j < table_danhsach_congno.getColumnCount(); j++) {
                        PdfPCell cell = new PdfPCell(new Phrase(table_danhsach_congno.getValueAt(row, j).toString(), FontFactory.getFont(FontFactory.HELVETICA, 5)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }
                }

                document.add(table);

                Paragraph emptyLine = new Paragraph(" "); // Tạo một dòng trống
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF
                // Tính tổng giá trị của cột "TONG"
                double tongTongTien = 0;
                for (int row : selectedRows) {
                    tongTongTien += Double.parseDouble(table_danhsach_congno.getValueAt(row, 6).toString());
                }

                // Định dạng số để hiển thị tổng tiền một cách rõ ràng
                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                String tongTongTienFormatted = decimalFormat.format(tongTongTien);

                // Tạo một đối tượng Chunk chứa thông tin về "SO TIEN THANH TOAN"
                Chunk tongTienThanhToanChunk = new Chunk("                                             SO TIEN THANH TOAN = " + tongTongTienFormatted + " VND", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7));
                Paragraph tongTienThanhToanParagraph = new Paragraph(tongTienThanhToanChunk);
                tongTienThanhToanParagraph.add(new Chunk("                                                                                                 ")); // Tạo một khoảng trống giữa hai Chunk
                tongTienThanhToanParagraph.setAlignment(Element.ALIGN_LEFT);

                // Thêm dòng tính toán vào tài liệu PDF
                document.add(tongTienThanhToanParagraph);

                // Tạo một đối tượng Chunk chứa thông tin về Người nộp tiền
                Chunk nguoiNopTien = new Chunk("Chu ky nguoi nop tien", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6));

                // Tạo một đối tượng Chunk chứa thông tin về Người thu tiền
                Chunk nguoiThuTien = new Chunk("Nguoi thu tien", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 6));

                // Tạo một đối tượng Paragraph chứa cả hai Chunk
                Paragraph nguoiNopVaThuTien = new Paragraph();
                nguoiNopVaThuTien.add(nguoiNopTien);
                nguoiNopVaThuTien.add(new Chunk("                                                   ")); // Tạo một khoảng trống giữa hai Chunk
                nguoiNopVaThuTien.add(nguoiThuTien);

                // Đặt căn lề trái và phải cho cả đoạn văn bản
                nguoiNopVaThuTien.setIndentationLeft(-25); // Đặt căn lề trái là 20 đơn vị
                nguoiNopVaThuTien.setIndentationRight(-25); // Đặt căn lề phải là 20 đơn vị

                // Thêm đoạn văn bản vào tài liệu PDF
                document.add(nguoiNopVaThuTien);

                // Tạo một đối tượng Paragraph trống để tạo khoảng trống giữa dòng "Ngày xuất hoá đơn" và bảng
                //Paragraph emptyLine = new Paragraph(" "); // Tạo một dòng trống
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF
                document.add(emptyLine); // Thêm dòng trống vào tài liệu PDF

                // Tạo đối tượng để lưu ngày hiện tại
                Date currentDate = new Date();

                // Định dạng ngày hiện tại
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                // Tạo một đối tượng Paragraph chứa thông tin về ngày xuất hoá đơn
                // Tạo một đối tượng Font với font chữ Helvetica, kích thước 10, in đậm và in nghiêng
                Font font = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLDITALIC);

                // Tạo một đối tượng Paragraph với nội dung và font chữ đã xác định
                Paragraph ngayXuatHoaDon = new Paragraph("Ngay xuat hoa don: " + dateFormat.format(currentDate), font);

                // Paragraph ngayXuatHoaDon = new Paragraph("Ngay xuat hoa don: " + dateFormat.format(currentDate), FontFactory.getFont(FontFactory.HELVETICA, 10));
                ngayXuatHoaDon.setAlignment(Element.ALIGN_CENTER); // Căn giữa theo chiều ngang
                document.add(ngayXuatHoaDon); // Thêm dòng ngày xuất hoá đơn vào tài liệu PDF

                // Tạo một đối tượng Font với font chữ Helvetica, kích thước 10, in đậm và in nghiêng
                Font font_inc = FontFactory.getFont(FontFactory.HELVETICA, 5, Font.BOLDITALIC);

                // Tạo một đối tượng Paragraph mới chứa nội dung bạn muốn thêm và sử dụng font đã tạo
                Paragraph in = new Paragraph("In tai phong Ke hoach tai chinh * Hotline lien he: 0906 000 768", font_inc);

                // Đặt căn lề cho đối tượng Paragraph mới
                in.setAlignment(Element.ALIGN_CENTER);

                in.setSpacingBefore(6);

                // Thêm đối tượng Paragraph mới vào tài liệu PDF
                document.add(in);

                document.add(new Paragraph("--------------------------------------------------------"));

                document.close();

                // Hiển thị thông báo xuất file thành công
                JOptionPane.showMessageDialog(this, "Xuất công nợ ra file PDF thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (DocumentException | FileNotFoundException ex) {
                Logger.getLogger(CongNo_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    private void btn_xuatcongnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xuatcongnoActionPerformed
        xuatCongNoRaPDF_v8();
    }//GEN-LAST:event_btn_xuatcongnoActionPerformed

    private void btn_tinh_tongcongnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tinh_tongcongnoActionPerformed
        // TODO add your handling code here:
        // Lấy giá trị từ các textfield, nếu null hoặc rỗng thì mặc định là 0
        String tienHocPhiText = txt_tienhocphi.getText();
        String tienAnText = txt_tienan.getText();
        String tienPhuThuText = txt_tienphuthu.getText();

        int tienHocPhi = tienHocPhiText.isEmpty() ? 0 : Integer.parseInt(tienHocPhiText);
        int tienAn = tienAnText.isEmpty() ? 0 : Integer.parseInt(tienAnText);
        int tienPhuThu = tienPhuThuText.isEmpty() ? 0 : Integer.parseInt(tienPhuThuText);

        // Tính tổng công nợ
        int tongCongNo = tienHocPhi + tienAn + tienPhuThu;

        // Hiển thị tổng công nợ lên textfield txt_tongcongno
        txt_tongcongno.setText(String.valueOf(tongCongNo));
    }//GEN-LAST:event_btn_tinh_tongcongnoActionPerformed

    private void btn_loc_theongayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loc_theongayActionPerformed
        // TODO add your handling code here:
        // Lấy ngày từ JDateChooser
        java.util.Date selectedDate = date_chooser.getDate();

        // Kiểm tra xem ngày có được chọn không
        if (selectedDate != null) {
            // Chuyển định dạng ngày thành chuỗi theo định dạng yyyy-MM-dd
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

            // Format ngày theo định dạng yyyy-MM-dd
            String formattedDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(selectedDate), inputFormatter).format(outputFormatter);

            // Lấy mã học sinh từ trường nhập mã học sinh
            String ma_hocsinh = cbo_mahs.getSelectedItem().toString();

            // Lấy danh sách công nợ của ngày và mã học sinh đã chọn
            ArrayList<CongNo> dsCongNo = CongNo_DAO.Loc_DanhSachCongNo_TheoNgayVaMaHS(formattedDate, ma_hocsinh);

            // Hiển thị danh sách công nợ
            laydulieu_congno(dsCongNo);

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày trước khi thực hiện.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_loc_theongayActionPerformed

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
            java.util.logging.Logger.getLogger(CongNo_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CongNo_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CongNo_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CongNo_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CongNo_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_loc_theongay;
    protected javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_sua_congno;
    private javax.swing.JButton btn_them_congno;
    private javax.swing.JButton btn_timkiem_congno_theo_mahocsinh;
    private javax.swing.JButton btn_tinh_tongcongno;
    private javax.swing.JButton btn_trove;
    private javax.swing.JButton btn_xoa_congno;
    private javax.swing.JButton btn_xuatcongno;
    private javax.swing.JComboBox<String> cbo_mahs;
    private javax.swing.JComboBox<String> cbo_trangthai_congno;
    private com.toedter.calendar.JDateChooser date_chooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_danhsach_congno;
    private javax.swing.JTextField txt_ma_congno;
    private javax.swing.JTextField txt_macongno_sinhtudong;
    private javax.swing.JTextField txt_tenchucvu;
    private javax.swing.JTextField txt_tienan;
    private javax.swing.JTextField txt_tienhocphi;
    private javax.swing.JTextField txt_tienphuthu;
    private javax.swing.JTextField txt_tongcongno;
    // End of variables declaration//GEN-END:variables
}
