/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DAO.CauHoi_DAO;
import static DAO.DeThi_DAO.LayThoiGianThi;
import DAO.KetQua_DAO;
import POJO.CauHoi_POJO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.util.Collections;
import javax.swing.JOptionPane;
/**
 *
 * @author TAN HUY
 */
public class ThiTracNghiem_GUI extends javax.swing.JFrame {
    Vector tblTitle = new Vector();
    CauHoi_POJO q;
    private static int pos = 0;
    private ArrayList<CauHoi_POJO> dschTron;
    
    ArrayList<String> selectedAnswers = new ArrayList<>(); // Mảng lưu trữ đáp án đã chọn

    private Timer countdownTimer; // Đối tượng Timer để đếm ngược thời gian
    private int remainingTime = 45 * 60; // Thời gian còn lại, đơn vị là giây
    
    
    /**
     * Creates new form ThiTracNghiem_GUI
     */
    public ThiTracNghiem_GUI() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        setTitle("Chức năng thi");
        
        String maDe = ThongTinThiOnline_GUI.maDeThi;
        ArrayList<CauHoi_POJO> dsch1 = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(maDe);
        
        bangCauHoi();
         if (!dsch1.isEmpty()) {
            q = dsch1.get(0);
        }
        tronDanhSachCauHoi();
        laydulieuCH();
        // Khởi tạo và bắt đầu đếm ngược
        DemNguocThoiGian();
        
        txt_monThi.setText(ThongTinThiOnline_GUI.maMonHoc);
        txt_monThi.setHorizontalAlignment(SwingConstants.CENTER);
        
        txt_maHS.setText(ThongTinThiOnline_GUI.maHocSinh);
        txt_maHS.setHorizontalAlignment(SwingConstants.CENTER);
        
        txt_maDeThi.setText(ThongTinThiOnline_GUI.maDeThi);
        txt_maDeThi.setHorizontalAlignment(SwingConstants.CENTER);
    }
    public void bangCauHoi() {
        tblTitle.add("MACAUHOI");
        tblTitle.add("CAUHOI");
        tblTitle.add("DAPAN_A");
        tblTitle.add("DAPAN_B");
        tblTitle.add("DAPAN_C");
        tblTitle.add("DAPAN_D");
        tblTitle.add("DAPANDUNG");
        tblTitle.add("MAMH");
    }
    // Phương thức để trộn danh sách câu hỏi và lưu vào dschTron
    private void tronDanhSachCauHoi() {
        String maDe = ThongTinThiOnline_GUI.maDeThi;
        ArrayList<CauHoi_POJO> dsch = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(maDe);
        dschTron = new ArrayList<>(dsch);
        Collections.shuffle(dschTron);
    }
     // Phương thức để lấy dữ liệu câu hỏi từ danh sách đã được trộn
    public void laydulieuCH() {
        if (pos >= 0 && pos < dschTron.size()) { 
            CauHoi_POJO q = dschTron.get(pos);
            lbl_cauHoi.setText("Câu số " + (pos + 1) + ":  " + q.getCAUHOI());

            // Hiển thị các câu trả lời mà không bao gồm đáp án đúng
            rdb_DapAnA.setText("A. " + q.getDAPAN_A());
            rdb_DapAnB.setText("B. " + q.getDAPAN_B());
            rdb_DapAnC.setText("C. " + q.getDAPAN_C());
            rdb_DapAnD.setText("D. " + q.getDAPAN_D());

            // Xoá màu nền của các radio button
            rdb_DapAnA.setBackground(null);
            rdb_DapAnB.setBackground(null);
            rdb_DapAnC.setBackground(null);
            rdb_DapAnD.setBackground(null);

            // Hiển thị đáp án đã chọn trước đó (nếu có)
            if (!selectedAnswers.isEmpty() && pos < selectedAnswers.size()) {
                String selected = selectedAnswers.get(pos);
                switch (selected) {
                    case "A":
                        rdb_DapAnA.setSelected(true);
                        break;
                    case "B":
                        rdb_DapAnB.setSelected(true);
                        break;
                    case "C":
                        rdb_DapAnC.setSelected(true);
                        break;
                    case "D":
                        rdb_DapAnD.setSelected(true);
                        break;
                    default:
                        // Nếu không có đáp án được chọn trước đó, xóa chọn tất cả các nút radio
                        buttonGroup1.clearSelection();
                        break;
                }
            } else {
                // Nếu không có đáp án được chọn trước đó, xóa chọn tất cả các nút radio
                buttonGroup1.clearSelection();
            }

            // Hiển thị mã câu hỏi lên list_danhSachCauHoi
            DefaultListModel<String> model = new DefaultListModel<>();
            for (CauHoi_POJO cauhoi : dschTron) {
                model.addElement(cauhoi.getMACAUHOI());
            }
            list_danhSachCauHoi.setModel(model);
        } else {
            System.out.println("Vị trí câu hỏi không hợp lệ.");
        }
    }

    public String Choice() {
        String answer = "";
        if (rdb_DapAnA.isSelected()) {
            answer = "A";
        } else if (rdb_DapAnB.isSelected()) {
            answer = "B";
        } else if (rdb_DapAnC.isSelected()) {
            answer = "C";
        } else if (rdb_DapAnD.isSelected()) {
            answer = "D";
        }
        return answer != null ? answer : ""; // Xử lý trường hợp chuỗi null
    }

    void OnOff (boolean A, boolean B, boolean C, boolean D) {
        this.rdb_DapAnA.setSelected(A);
        this.rdb_DapAnB.setSelected(B);
        this.rdb_DapAnC.setSelected(C);
        this.rdb_DapAnD.setSelected(D);
    }
    public void OnAnswer (String n) {
        switch (n) {
            case "A":
            this.rdb_DapAnA.setBackground (Color.yellow);
            break;
            case "B":
            this.rdb_DapAnB.setBackground (Color.yellow);
            break;
            case "C":
            this.rdb_DapAnC.setBackground (Color.yellow);
            break;
            case "D":
            this.rdb_DapAnD.setBackground (Color.yellow);
            break;
        }
    }
    
    private void DemNguocThoiGian() {
        int thoiGianThi = LayThoiGianThi(ThongTinThiOnline_GUI.maDeThi); // Lấy thời gian thi từ cơ sở dữ liệu
        remainingTime = thoiGianThi * 60; // Chuyển thời gian từ phút sang giây
        countdownTimer = new Timer(1000, e -> {
            remainingTime--;
            if (remainingTime >= 0) {
                int minutes = remainingTime / 60;
                int seconds = remainingTime % 60;
                lbl_tgConLai.setText(String.format("%02d:%02d", minutes, seconds)); // Hiển thị thời gian dưới dạng mm:ss trên label
            } else {
                // Thời gian đếm ngược đã hết, thực hiện các xử lý khi hết thời gian
                countdownTimer.stop(); // Dừng đếm ngược
                XuLyHetThoiGian();
            }
        });
        countdownTimer.start(); // Khởi động đếm ngược
    }
    private void XuLyHetThoiGian() {
        // Hiển thị thông báo về việc hết thời gian và không tính điểm
        javax.swing.JOptionPane.showMessageDialog(this, "Hết thời gian! Bạn không được tính điểm cho bài test.");

        // Đóng frame sau khi hiển thị thông báo
        this.dispose();
    }





    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lbl_cauHoi = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_danhSachCauHoi = new javax.swing.JList<>();
        btn_cauKeSau = new javax.swing.JButton();
        btn_CauKeTiep = new javax.swing.JButton();
        btn_cauDaTraLoi = new javax.swing.JButton();
        btn_cauChuaTraLoi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        rdb_DapAnA = new javax.swing.JRadioButton();
        rdb_DapAnB = new javax.swing.JRadioButton();
        rdb_DapAnC = new javax.swing.JRadioButton();
        rdb_DapAnD = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_maHS = new javax.swing.JTextField();
        txt_monThi = new javax.swing.JTextField();
        btn_nopBai = new javax.swing.JButton();
        lbl_tgDemNguoc = new javax.swing.JLabel();
        lbl_tgConLai = new javax.swing.JLabel();
        txt_maDeThi = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Câu Hỏi"));

        lbl_cauHoi.setText("Câu Hỏi: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_cauHoi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lbl_cauHoi)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Câu Hỏi"));

        list_danhSachCauHoi.setEnabled(false);
        list_danhSachCauHoi.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                list_danhSachCauHoiValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list_danhSachCauHoi);

        btn_cauKeSau.setBackground(new java.awt.Color(204, 255, 255));
        btn_cauKeSau.setText("<<<");
        btn_cauKeSau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cauKeSauActionPerformed(evt);
            }
        });

        btn_CauKeTiep.setBackground(new java.awt.Color(204, 255, 255));
        btn_CauKeTiep.setText(">>>");
        btn_CauKeTiep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CauKeTiepActionPerformed(evt);
            }
        });

        btn_cauDaTraLoi.setText("Câu đã trả lời");
        btn_cauDaTraLoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cauDaTraLoiActionPerformed(evt);
            }
        });

        btn_cauChuaTraLoi.setText("Câu chưa trả lời");
        btn_cauChuaTraLoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cauChuaTraLoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_cauDaTraLoi, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cauChuaTraLoi)
                        .addGap(7, 7, 7))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_cauKeSau, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_CauKeTiep, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cauDaTraLoi)
                    .addComponent(btn_cauChuaTraLoi))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cauKeSau)
                    .addComponent(btn_CauKeTiep))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Các Đáp Án Trả Lời"));

        buttonGroup1.add(rdb_DapAnA);
        rdb_DapAnA.setText("A.");

        buttonGroup1.add(rdb_DapAnB);
        rdb_DapAnB.setText("B.");

        buttonGroup1.add(rdb_DapAnC);
        rdb_DapAnC.setText("C.");

        buttonGroup1.add(rdb_DapAnD);
        rdb_DapAnD.setText("D.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rdb_DapAnA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdb_DapAnB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdb_DapAnC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdb_DapAnD, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(rdb_DapAnA)
                .addGap(35, 35, 35)
                .addComponent(rdb_DapAnB)
                .addGap(35, 35, 35)
                .addComponent(rdb_DapAnC)
                .addGap(35, 35, 35)
                .addComponent(rdb_DapAnD)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Mã số học sinh:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Mã môn học:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Thời gian còn lại:");

        txt_maHS.setEditable(false);
        txt_maHS.setBackground(new java.awt.Color(255, 255, 255));

        txt_monThi.setEditable(false);
        txt_monThi.setBackground(new java.awt.Color(255, 255, 255));

        btn_nopBai.setBackground(new java.awt.Color(204, 255, 255));
        btn_nopBai.setText("Nộp Bài");
        btn_nopBai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nopBaiActionPerformed(evt);
            }
        });

        lbl_tgConLai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_tgConLai.setForeground(new java.awt.Color(255, 0, 0));
        lbl_tgConLai.setText("00 : 00");

        txt_maDeThi.setEditable(false);
        txt_maDeThi.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Mã đề :");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_maHS, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_monThi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_maDeThi, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_tgConLai)
                .addGap(18, 18, 18)
                .addComponent(lbl_tgDemNguoc, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_nopBai)
                .addGap(58, 58, 58))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txt_maHS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_monThi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nopBai)
                    .addComponent(lbl_tgDemNguoc)
                    .addComponent(lbl_tgConLai)
                    .addComponent(txt_maDeThi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void updateSelectedAnswers(int pos) {
        String n = Choice();
        if (selectedAnswers.size() <= pos) {
            // Nếu vị trí pos lớn hơn hoặc bằng kích thước hiện tại của danh sách,
            // thêm các phần tử null vào danh sách cho đến khi đủ vị trí pos + 1
            while (selectedAnswers.size() < pos + 1) {
                selectedAnswers.add(null);
            }
            // Thêm câu trả lời mới vào danh sách
            selectedAnswers.set(pos, n);
        } else {
            // Nếu vị trí pos đã có trong danh sách, cập nhật câu trả lời tại vị trí đó
            selectedAnswers.set(pos, n);
        }
    }

    private void list_danhSachCauHoiValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_list_danhSachCauHoiValueChanged
        // TODO add your handling code here:
        String maDe = ThongTinThiOnline_GUI.maDeThi;
        ArrayList<CauHoi_POJO> dsch1 = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(maDe);
            if (!list_danhSachCauHoi.isSelectionEmpty() && !evt.getValueIsAdjusting() && !dsch1.isEmpty()) {
            // Cập nhật đáp án đã chọn cho câu hỏi hiện tại
            updateSelectedAnswers(pos);

            // Di chuyển đến câu hỏi mới được chọn
            pos = list_danhSachCauHoi.getSelectedIndex();

            // Nếu vị trí câu hỏi hợp lệ
            if (pos >= 0 && pos < dsch1.size()) {
                q = dsch1.get(pos);
                laydulieuCH();

                // Hiển thị lại đáp án đã chọn cho câu hỏi mới
                if (!selectedAnswers.isEmpty() && pos < selectedAnswers.size()) {
                    String selected = selectedAnswers.get(pos);
                    switch (selected) {
                        case "A":
                            rdb_DapAnA.setSelected(true);
                            break;
                        case "B":
                            rdb_DapAnB.setSelected(true);
                            break;
                        case "C":
                            rdb_DapAnC.setSelected(true);
                            break;
                        case "D":
                            rdb_DapAnD.setSelected(true);
                            break;
                    }
                }
            }
        }
    }//GEN-LAST:event_list_danhSachCauHoiValueChanged

    private int tinhSoCauDung() {
        int soCauDung = 0;
        boolean allAnswered = isAllQuestionsAnswered(); // Kiểm tra xem tất cả các câu hỏi đã được trả lời chưa
        for (int i = 0; i < dschTron.size(); i++) {
            CauHoi_POJO cauHoi = dschTron.get(i);
            String dapAnDung = cauHoi.getDAPANDUNG();
            String dapAnDaChon = selectedAnswers.get(i);
            // Kiểm tra xem câu hỏi đã chọn đáp án và đáp án đó có chính xác hay không
            if (dapAnDaChon != null && !dapAnDaChon.isEmpty() && dapAnDung != null && dapAnDung.equals(dapAnDaChon)) {
                soCauDung++;
            } else if (!allAnswered && (dapAnDaChon == null || dapAnDaChon.isEmpty())) {
                // Nếu người dùng chưa trả lời tất cả các câu hỏi và câu hỏi hiện tại chưa được trả lời, không tính vào số câu đúng
                // Các câu hỏi chưa được trả lời sẽ không được tính là sai trong việc tính toán tổng số câu đúng
            }
        }
        return soCauDung;
    }

    private boolean isAllQuestionsAnswered() {
        // Kiểm tra xem tất cả các câu hỏi đã được chọn đáp án hay chưa
        for (String answer : selectedAnswers) {
            if (answer == null || answer.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    private void btn_nopBaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nopBaiActionPerformed
        // TODO add your handling code here:
        int confirmed = JOptionPane.showConfirmDialog(this, "BẠN CHẮC CHẮC MUỐN NỘP BÀI?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                String maDe = ThongTinThiOnline_GUI.maDeThi;
                System.out.println("Mã đề thi: " + maDe);
                ArrayList<CauHoi_POJO> dsch1 = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(maDe);
                updateSelectedAnswers(pos); //Cập nhật danh sách selectedAnswers trước khi tính điểm
                if (!isAllQuestionsAnswered()) {
                    // Nếu có câu hỏi nào chưa được chọn đáp án, hiển thị thông báo cảnh báo
                    javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn đáp án cho tất cả các câu hỏi trước khi nộp bài!");
                } else {
                    // Tính số câu làm đúng và hiển thị điểm số cùng thời gian làm bài
                    int soCauDung = tinhSoCauDung();
                    countdownTimer.stop(); // Dừng đếm ngược
                    int thoiGianThi = LayThoiGianThi(ThongTinThiOnline_GUI.maDeThi); // Lấy thời gian thi từ cơ sở dữ liệu
                    int minutes = (thoiGianThi * 60 - remainingTime) / 60; // Tính số phút đã làm bài
                    int seconds = (thoiGianThi * 60 - remainingTime) % 60; // Tính số giây còn lại
                    String resultMessage = "Số câu làm đúng: " + soCauDung + "/" + dsch1.size() + "\nĐiểm số: " + ((float) soCauDung / dsch1.size() * 10) + "\nThời gian làm bài: " + minutes + " phút " + seconds + " giây";
                    javax.swing.JOptionPane.showMessageDialog(this, resultMessage);

                    // Thêm mã lệnh để lưu kết quả vào CSDL ở đây nếu cần
                    String maHocSinh = txt_maHS.getText();
                    String maDeThi = txt_maDeThi.getText();
                    String sooCauDung = Integer.toString(tinhSoCauDung());
                    String diemSo = Float.toString((float) tinhSoCauDung() / dsch1.size() * 10);
                    String sql = "{CALL NV001.ThemKetQua('"+maHocSinh+"', '"+maDeThi+"', '"+sooCauDung+"',  '"+diemSo+"')}";
                    KetQua_DAO.ThemXoaSuaKetQua(sql);

                    // Đóng frame sau khi hiển thị thông báo
                    this.dispose();
                }
            } else {
                    return;
            }
    }//GEN-LAST:event_btn_nopBaiActionPerformed

    private void btn_cauKeSauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cauKeSauActionPerformed
        // TODO add your handling code here
        if (pos > 0) { // Đảm bảo vị trí câu hỏi không nhỏ hơn 0
            updateSelectedAnswers(pos); // Cập nhật đáp án đã chọn cho câu hỏi hiện tại
            pos--;
            String maDe = ThongTinThiOnline_GUI.maDeThi;
            ArrayList<CauHoi_POJO> dsch1 = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(maDe);
            laydulieuCH();
            highlightSelectedQuestion(pos);
        }
    }//GEN-LAST:event_btn_cauKeSauActionPerformed

    private void btn_CauKeTiepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CauKeTiepActionPerformed
        // TODO add your handling code here:
        String maDe = ThongTinThiOnline_GUI.maDeThi;
        ArrayList<CauHoi_POJO> dsch1 = CauHoi_DAO.LayThongTin_CauHoi_Theo_MaDeThi(maDe);
        if (pos < dsch1.size() - 1) { // Đảm bảo vị trí câu hỏi không vượt quá số lượng câu hỏi trong danh sách
            updateSelectedAnswers(pos); // Cập nhật đáp án đã chọn cho câu hỏi hiện tại
            pos++;
            laydulieuCH();
            highlightSelectedQuestion(pos);
        }
    }//GEN-LAST:event_btn_CauKeTiepActionPerformed
    
    private void highlightSelectedQuestion(int pos) {
    list_danhSachCauHoi.clearSelection(); // Xóa bỏ bất kỳ lựa chọn nào trước đó trên danh sách
    list_danhSachCauHoi.setSelectedIndex(pos); // Chọn mã câu hỏi tương ứng
    list_danhSachCauHoi.ensureIndexIsVisible(pos); // Đảm bảo rằng mã câu hỏi được chọn sẽ hiển thị trên danh sách
}


    private void btn_cauDaTraLoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cauDaTraLoiActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btn_cauDaTraLoiActionPerformed

    private void btn_cauChuaTraLoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cauChuaTraLoiActionPerformed
        // TODO add your handling code here:
        cauChuaTraLoi();
    }//GEN-LAST:event_btn_cauChuaTraLoiActionPerformed
    
   // Phương thức để cập nhật danh sách câu hỏi dựa trên trạng thái đã xem
    private void cauChuaTraLoi() {
        String maDe = ThongTinThiOnline_GUI.maDeThi;
        System.out.println("Mã đề thi: " + maDe);
        ArrayList<CauHoi_POJO> dsch1 = CauHoi_DAO.LayThongTinCauHoiTheoMaDeThi(maDe);
        DefaultListModel<String> model = new DefaultListModel<>();
        // Đảm bảo selectedAnswers có ít nhất số lượng phần tử bằng với dsch
        if (selectedAnswers.size() >= dsch1.size()) {
            for (int i = 0; i < dsch1.size(); i++) {
                CauHoi_POJO cauHoi = dsch1.get(i);
                String question = cauHoi.getMACAUHOI();
                // Kiểm tra xem vị trí index có hợp lệ không trước khi truy cập vào selectedAnswers
                if (i < selectedAnswers.size()) {
                    String answer = selectedAnswers.get(i);
                    // Nếu câu hỏi chưa được trả lời, thêm vào danh sách với màu đỏ
                    if (answer == null || answer.isEmpty()) {
                        model.addElement("<html><font color='red'>" + question + "</font></html>");
                    }
                }
            }
        }
        list_danhSachCauHoi.setModel(model); // Cập nhật danh sách câu hỏi
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
            java.util.logging.Logger.getLogger(ThiTracNghiem_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThiTracNghiem_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThiTracNghiem_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThiTracNghiem_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThiTracNghiem_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_CauKeTiep;
    private javax.swing.JButton btn_cauChuaTraLoi;
    private javax.swing.JButton btn_cauDaTraLoi;
    private javax.swing.JButton btn_cauKeSau;
    private javax.swing.JButton btn_nopBai;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_cauHoi;
    private javax.swing.JLabel lbl_tgConLai;
    private javax.swing.JLabel lbl_tgDemNguoc;
    private javax.swing.JList<String> list_danhSachCauHoi;
    private javax.swing.JRadioButton rdb_DapAnA;
    private javax.swing.JRadioButton rdb_DapAnB;
    private javax.swing.JRadioButton rdb_DapAnC;
    private javax.swing.JRadioButton rdb_DapAnD;
    private javax.swing.JTextField txt_maDeThi;
    private javax.swing.JTextField txt_maHS;
    private javax.swing.JTextField txt_monThi;
    // End of variables declaration//GEN-END:variables
}
