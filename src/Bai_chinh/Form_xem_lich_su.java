package Bai_chinh;

import Giaodien.Dangnhap;
import ham_xu_ly.*;
import java.util.*;
import javax.swing.*;
import java.sql.*;
import Bai_chinh.*;




public class Form_xem_lich_su extends javax.swing.JFrame {
    
    private xu_ly_formlichsu xuLyFormLichSu;
    private String userRole;
    
    
    
    public Form_xem_lich_su() {
        initComponents();
        xuLyFormLichSu = new xu_ly_formlichsu();

        // Đảm bảo cbb_ten bắt đầu với mục mặc định "Chọn tên người dùng"
        cbb_ten.addItem("Chọn tên người dùng");

        // Nạp dữ liệu từ cơ sở dữ liệu vào ComboBox
        xuLyFormLichSu.loadUserNames(cbb_ten);

        // Đảm bảo cbb_sdt không có dữ liệu khi mới khởi tạo
        cbb_sdt.removeAllItems();

        // Đặt JFrame hiển thị ở trung tâm màn hình
        this.setLocationRelativeTo(null);
    }
    
    
        // Hàm để nhận quyền từ form trước đó
    public void setUserRole(String userRole) {
    this.userRole = userRole;
    System.out.println("Quyền truyền vào Form_xem_lich_su: " + userRole); // In ra để kiểm tra
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbb_ten = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbb_sdt = new javax.swing.JComboBox<>();
        checkbutton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_ls = new javax.swing.JTable();
        Ho_so = new javax.swing.JLabel();
        data_suc_khoe = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        thongbao = new javax.swing.JLabel();
        Logout = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("KIỂM TRA SỨC KHỎE");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setText("Tên người dùng");

        cbb_ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_tenActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 0));
        jLabel3.setText("Số điện thoại");

        cbb_sdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_sdtActionPerformed(evt);
            }
        });

        checkbutton.setBackground(new java.awt.Color(242, 242, 242));
        checkbutton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkbutton.setForeground(new java.awt.Color(0, 153, 0));
        checkbutton.setText("CHECK");
        checkbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbuttonActionPerformed(evt);
            }
        });

        table_ls.setBackground(new java.awt.Color(242, 242, 242));
        table_ls.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cân nặng", "Huyết áp", "Nhịp tim", "Lnước đã uống", "Số bước chân", "Ngày nhập"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_ls);
        if (table_ls.getColumnModel().getColumnCount() > 0) {
            table_ls.getColumnModel().getColumn(0).setResizable(false);
            table_ls.getColumnModel().getColumn(1).setResizable(false);
            table_ls.getColumnModel().getColumn(2).setResizable(false);
            table_ls.getColumnModel().getColumn(3).setResizable(false);
            table_ls.getColumnModel().getColumn(4).setResizable(false);
            table_ls.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(208, 208, 208))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(checkbutton)
                        .addGap(171, 171, 171))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbb_ten, 0, 160, Short.MAX_VALUE)
                            .addComponent(cbb_sdt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbb_ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbb_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(checkbutton)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        Ho_so.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Ho_so.setForeground(new java.awt.Color(242, 242, 242));
        Ho_so.setText("HỒ SƠ");
        Ho_so.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Ho_soMouseClicked(evt);
            }
        });

        data_suc_khoe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        data_suc_khoe.setForeground(new java.awt.Color(242, 242, 242));
        data_suc_khoe.setText("<html>THÔNG TIN<br>SỨC KHỎE</html>");
        data_suc_khoe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                data_suc_khoeMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(242, 242, 242));
        jLabel10.setText("XEM LỊCH SỬ");

        thongbao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        thongbao.setForeground(new java.awt.Color(242, 242, 242));
        thongbao.setText("MỚI NHẤT");
        thongbao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thongbaoMouseClicked(evt);
            }
        });

        Logout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Logout.setForeground(new java.awt.Color(242, 242, 242));
        Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh_va_icon/logout.png"))); // NOI18N
        Logout.setText("<html>LOG<br>OUT</html>");
        Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(data_suc_khoe)
                    .addComponent(Ho_so, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(thongbao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(Ho_so)
                .addGap(30, 30, 30)
                .addComponent(data_suc_khoe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel10)
                .addGap(30, 30, 30)
                .addComponent(thongbao, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbb_sdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_sdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbb_sdtActionPerformed

    private void cbb_tenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_tenActionPerformed
        // Làm trống cbb_sdt trước khi tải số điện thoại
        cbb_sdt.removeAllItems(); // Xóa tất cả các mục trong comboBox số điện thoại

        // Sau đó mới gọi phương thức để tải số điện thoại theo tên đã chọn
        xuLyFormLichSu.loadPhoneNumbersByName(cbb_ten, cbb_sdt);
    }//GEN-LAST:event_cbb_tenActionPerformed

    private void checkbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbuttonActionPerformed
    xuLyFormLichSu.loadUserInfoByPhone(cbb_ten, cbb_sdt, table_ls);

    if (table_ls.getRowCount() > 0) {
        thongbao.setEnabled(true); // Bật label nếu có dữ liệu
    } else {
        thongbao.setEnabled(false); // Tắt label nếu không có dữ liệu
    }
    }//GEN-LAST:event_checkbuttonActionPerformed

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
        // TODO add your handling code here:
        Dangnhap mainForm = new Dangnhap();
        mainForm.setVisible(true); // Hiển thị MainForm
        this.dispose(); // Đóng SecondForm
    }//GEN-LAST:event_LogoutMouseClicked

    private void Ho_soMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Ho_soMouseClicked
    Form_nguoi_dung formNguoiDung = new Form_nguoi_dung();
    formNguoiDung.setUserRole(this.userRole); // Truyền quyền từ Form_xem_lich_su
    System.out.println("Truyền quyền từ Form_xem_lich_su sang Form_nguoi_dung: " + this.userRole);
    formNguoiDung.setVisible(true);
    this.dispose(); // Đóng Form_xem_lich_su
    }//GEN-LAST:event_Ho_soMouseClicked

    private void data_suc_khoeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_suc_khoeMouseClicked
    Form_suc_khoe formSucKhoe = new Form_suc_khoe();
    formSucKhoe.setUserRole(this.userRole); // Truyền quyền từ Form_xem_lich_su
    System.out.println("Truyền quyền từ Form_xem_lich_su sang Form_suc_khoe: " + this.userRole);
    formSucKhoe.setVisible(true);
    this.dispose(); // Đóng Form_xem_lich_su
    }//GEN-LAST:event_data_suc_khoeMouseClicked

    private void thongbaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thongbaoMouseClicked
    int selectedRow = table_ls.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một dòng trong bảng thông tin sức khỏe!",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String selectedPhoneNumber = table_ls.getValueAt(selectedRow, 0).toString(); // Cột 0 chứa số điện thoại
    if (selectedPhoneNumber == null || selectedPhoneNumber.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        xu_ly_formsuckhoe xuLy = new xu_ly_formsuckhoe();
        Map<String, String> healthInfo = xuLy.getCombinedHealthData(selectedPhoneNumber);

        if (healthInfo != null) {
            new HealthInfoDialog(this, true, healthInfo).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_thongbaoMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(Form_xem_lich_su.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(Form_xem_lich_su.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(Form_xem_lich_su.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(Form_xem_lich_su.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
        // Khởi tạo form Form_xem_lich_su và hiển thị
    Form_xem_lich_su formXemLichSu = new Form_xem_lich_su();
    formXemLichSu.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Ho_so;
    private javax.swing.JLabel Logout;
    private javax.swing.JComboBox<String> cbb_sdt;
    private javax.swing.JComboBox<String> cbb_ten;
    private javax.swing.JButton checkbutton;
    private javax.swing.JLabel data_suc_khoe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_ls;
    private javax.swing.JLabel thongbao;
    // End of variables declaration//GEN-END:variables
}
