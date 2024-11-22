package Bai_chinh;

import Giaodien.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import ham_xu_ly.xu_ly_formnguoidung;
import javax.swing.JComboBox;
import Bai_chinh.*;
import java.util.logging.*;




public class Form_nguoi_dung extends javax.swing.JFrame {
    
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private boolean cothem = true; // Biến để xác định thêm hay sửa
    private final xu_ly_formnguoidung xuLy = new xu_ly_formnguoidung(); // Lớp xử lý
    private String userRole = "guest";  // Biến lưu quyền người dùng (guest, admin, ...)
    

// Phương thức này sẽ nhận quyền người dùng từ Form_Login
public void setUserRole(String userRole) {
    if (userRole == null || userRole.isEmpty()) {
        this.userRole = "guest"; // Giá trị mặc định
        System.out.println("Quyền truyền vào Form_nguoi_dung bị null. Đặt về mặc định: guest");
    } else {
        this.userRole = userRole;
        System.out.println("Quyền truyền vào Form_nguoi_dung: " + userRole);
    }
    updateButtonPermissions(); // Cập nhật quyền sau khi gán giá trị
}


    // Hàm hiển thị dữ liệu từ CSDL lên JTable
    public void ShowData() {
        DefaultTableModel model = (DefaultTableModel) table_person.getModel();
        model.setRowCount(0);  // Xóa dữ liệu cũ trong bảng

        try (Connection conn = Ketnoidangnhap.getJDBCConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT sdt, hoten, tuoi, diachi, chieucao FROM table_person");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String sdt = rs.getString("sdt");
                String hoten = rs.getString("hoten");
                int tuoi = rs.getInt("tuoi");
                String diachi = rs.getString("diachi");
                String chieucao = rs.getString("chieucao");

                // Thêm từng dòng dữ liệu vào JTable
                model.addRow(new Object[]{sdt, hoten, tuoi, diachi, chieucao});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hàm xóa dữ liệu hiện tại trên bảng
    public void ClearData() {
        int n = tableModel.getRowCount() - 1;
        for (int i = n; i >= 0; i--) {
            tableModel.removeRow(i); // Remove từng dòng
        }
    }

    // Hàm set lại giá trị mặc định cho các TextField
    private void setNull() {
        this.txtHoten.setText(null);
        this.txtTuoi.setText(null);
        this.txtSdt.setText(null);
        this.txtDiachi.setText(null);
        this.txtChieucao.setText(null);
        this.txtHoten.requestFocus();
    }

    // Hàm khóa hay mở khóa các TextField
    private void setKhoa(boolean a) {
        this.txtHoten.setEnabled(!a);
        this.txtTuoi.setEnabled(!a);
        this.txtSdt.setEnabled(!a);
        this.txtDiachi.setEnabled(!a);
        this.txtChieucao.setEnabled(!a);
    }

private void updateButtonPermissions() {
    boolean isAdmin = userRole.equals("admin");

    buttonthem.setEnabled(true); // Guest và admin đều có thể thêm
    buttonsua.setEnabled(isAdmin); // Chỉ admin được sửa
    buttonxoa.setEnabled(isAdmin); // Chỉ admin được xóa
    buttonluu.setEnabled(true); // Tất cả đều có thể lưu
    buttonkluu.setEnabled(true); // Tất cả đều có thể không lưu
}



    public Form_nguoi_dung() {
    initComponents();

    String[] colsName = { "Họ tên", "Tuổi", "Số điện thoại", "Địa chỉ", "Chiều cao" };
    tableModel.setColumnIdentifiers(colsName);
    table_person.setModel(tableModel); // Đảm bảo sử dụng table_person cho JTable
    try {
        ShowData(); // Gọi hàm ShowData để đưa dữ liệu vào tableModel
    } catch (Exception e) {
        e.printStackTrace();
    }

    setNull(); // Gọi hàm xóa trắng các TextField
    setKhoa(true); // Gọi hàm khóa các TextField
    if (this.userRole == null) {
        this.userRole = "guest"; // Quyền mặc định nếu chưa được thiết lập
    }
    updateButtonPermissions(); // Chỉ áp dụng quyền sau khi userRole đã được thiết lập
    setLocationRelativeTo(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtHoten = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        txtChieucao = new javax.swing.JTextField();
        buttonthem = new javax.swing.JButton();
        buttonsua = new javax.swing.JButton();
        buttonxoa = new javax.swing.JButton();
        buttonluu = new javax.swing.JButton();
        buttonkluu = new javax.swing.JButton();
        txtDiachi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_person = new javax.swing.JTable();
        txtTuoi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        data_nguoi_dung = new javax.swing.JLabel();
        data_suc_khoe = new javax.swing.JLabel();
        xem_lich_su = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Logout = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("KIỂM TRA SỨC KHỎE");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setText("Họ tên");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 0));
        jLabel3.setText("Tuổi");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 0));
        jLabel4.setText("Địa chỉ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 0));
        jLabel5.setText("Số điện thoại");
        jLabel5.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 0));
        jLabel6.setText("Chiều cao");

        txtHoten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHotenActionPerformed(evt);
            }
        });

        txtSdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtActionPerformed(evt);
            }
        });

        buttonthem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonthem.setForeground(new java.awt.Color(0, 153, 0));
        buttonthem.setText("Thêm");
        buttonthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonthemActionPerformed(evt);
            }
        });

        buttonsua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonsua.setForeground(new java.awt.Color(0, 153, 0));
        buttonsua.setText("Sửa");
        buttonsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonsuaActionPerformed(evt);
            }
        });

        buttonxoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonxoa.setForeground(new java.awt.Color(0, 153, 0));
        buttonxoa.setText("Xóa");
        buttonxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonxoaActionPerformed(evt);
            }
        });

        buttonluu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonluu.setForeground(new java.awt.Color(0, 153, 0));
        buttonluu.setText("Lưu");
        buttonluu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonluuActionPerformed(evt);
            }
        });

        buttonkluu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonkluu.setForeground(new java.awt.Color(0, 153, 0));
        buttonkluu.setText("K.Lưu");
        buttonkluu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonkluuActionPerformed(evt);
            }
        });

        table_person.setForeground(new java.awt.Color(0, 153, 0));
        table_person.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Họ tên", "Tuổi", "Địa chỉ", "Số điện thoại", "Chiều cao"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_person.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_personMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_person);
        if (table_person.getColumnModel().getColumnCount() > 0) {
            table_person.getColumnModel().getColumn(0).setResizable(false);
            table_person.getColumnModel().getColumn(1).setResizable(false);
            table_person.getColumnModel().getColumn(2).setResizable(false);
            table_person.getColumnModel().getColumn(3).setResizable(false);
            table_person.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 0));
        jLabel7.setText("cm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(buttonthem, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtHoten, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(txtDiachi))
                                    .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(16, 16, 16))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(buttonsua, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(buttonxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(buttonluu, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtChieucao, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(67, 67, 67))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(543, 543, 543)
                        .addComponent(buttonkluu, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtHoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChieucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonthem)
                    .addComponent(buttonsua)
                    .addComponent(buttonxoa)
                    .addComponent(buttonluu)
                    .addComponent(buttonkluu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        data_nguoi_dung.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        data_nguoi_dung.setForeground(new java.awt.Color(242, 242, 242));
        data_nguoi_dung.setText("HỒ SƠ");
        data_nguoi_dung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                data_nguoi_dungMouseClicked(evt);
            }
        });

        data_suc_khoe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        data_suc_khoe.setForeground(new java.awt.Color(242, 242, 242));
        data_suc_khoe.setText("<html>THÔNG TIN<br>SỨC KHỎE</html>");
        data_suc_khoe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                data_suc_khoeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                data_suc_khoeMouseEntered(evt);
            }
        });

        xem_lich_su.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        xem_lich_su.setForeground(new java.awt.Color(242, 242, 242));
        xem_lich_su.setText("XEM LỊCH SỬ");
        xem_lich_su.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                xem_lich_suMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(242, 242, 242));
        jLabel11.setText("MỚI NHẤT");

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
                    .addComponent(data_nguoi_dung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xem_lich_su, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(data_nguoi_dung)
                        .addGap(30, 30, 30)
                        .addComponent(data_suc_khoe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(xem_lich_su)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(163, 163, 163)
                        .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtHotenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHotenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHotenActionPerformed

    private void buttonsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonsuaActionPerformed
        // TODO add your handling code here:
    // Lấy giá trị Họ tên và loại bỏ khoảng trắng
    String hoten = txtHoten.getText().trim(); 

    // Kiểm tra xem người dùng đã chọn Họ tên hay chưa
    if (hoten.isEmpty()) { 
        JOptionPane.showMessageDialog(null, "Vui lòng chọn người dùng cần sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
    } else {
        // Mở khóa các JTextField để người dùng có thể chỉnh sửa
        setKhoa(false); // Mở khóa các JTextField

        // Khóa JTextField họ tên để không cho phép sửa đổi họ tên
        this.txtHoten.setEnabled(false); // Sử dụng setEnabled để đảm bảo họ tên không thể thay đổi

        // Khóa tất cả các nút khác, ngoại trừ nút Lưu và K.Lưu
        updateButtonPermissions(); // Chỉ cho phép nút Lưu và K.Lưu hoạt động

        // Ghi nhận trạng thái là sửa đổi
        cothem = false; // Gán cothem = false để ghi nhận trạng thái là sửa
}

    }//GEN-LAST:event_buttonsuaActionPerformed

    private void txtSdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSdtActionPerformed

    private void table_personMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_personMouseClicked
        // TODO add your handling code here:
    // Lấy chỉ số dòng đang chọn trong JTable Swing
    int row = this.table_person.getSelectedRow();
    
    // Kiểm tra nếu có dòng được chọn
    if (row >= 0) {
        // Lấy họ tên từ cột đầu tiên của dòng đã chọn trong JTable
        String hoten = this.table_person.getModel().getValueAt(row, 1).toString();
        
        // Gọi hàm lấy dữ liệu theo họ tên từ CSDL (bảng table_person trong CSDL)
        xu_ly_formnguoidung obj = new xu_ly_formnguoidung();
        xu_ly_formnguoidung data = null;
        try {
            data = obj.getNguoiDungByHoTen(hoten);
        } catch (SQLException ex) {
            Logger.getLogger(Form_nguoi_dung.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (data != null) { // Nếu có dữ liệu
            // Thiết lập giá trị vào các JTextField từ dữ liệu trong CSDL
            this.txtHoten.setText(data.getHoten());
            this.txtTuoi.setText(String.valueOf(data.getTuoi()));
            this.txtSdt.setText(data.getSdt());
            this.txtDiachi.setText(data.getDiachi());
            this.txtChieucao.setText(String.valueOf(data.getChieucao()));
        }
    }
    }//GEN-LAST:event_table_personMouseClicked

    private void buttonxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonxoaActionPerformed
        // TODO add your handling code here:
    // Lấy chỉ số dòng đang chọn trong JTable
    int row = table_person.getSelectedRow();
    
    // Kiểm tra nếu có dòng được chọn
    if (row >= 0) {
        // Lấy họ tên từ cột thứ hai của dòng đã chọn
        String hoten = table_person.getModel().getValueAt(row, 1).toString(); // Cột họ tên
        
        // Hiển thị hộp thoại xác nhận trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa người dùng " + hoten + " này hay không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        // Nếu người dùng chọn "Có"
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Gọi hàm xóa dữ liệu theo họ tên
                xu_ly_formnguoidung xl = new xu_ly_formnguoidung();
                xl.deleteNguoiDung(hoten); // Xóa người dùng theo họ tên
                
                // Cập nhật lại bảng sau khi xóa
                ClearData();
                ShowData();
                
                // Xóa trang JTextField
                setNull();
            } catch (SQLException ex) {
                // Hiển thị thông báo lỗi nếu xảy ra ngoại lệ
                JOptionPane.showMessageDialog(this, "Xóa thất bại: " + ex.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        // Nếu không có dòng nào được chọn, thông báo cho người dùng
        JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_buttonxoaActionPerformed

    private void buttonthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonthemActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
            setNull(); // Gọi hàm để xóa trang các JTextField
            
        // Mở khóa các JTextField để người dùng có thể nhập dữ liệu
        setKhoa(false); // Mở khóa các JTextField

       // Khóa các nút khác và chỉ cho phép nút Lưu và K.Lưu hoạt động
       updateButtonPermissions(); // Khóa tất cả các JButton, ngoại trừ nút Lưu và K.Lưu

       // Đánh dấu trạng thái là thêm mới
       cothem = true; // Gán cothem = true để ghi nhận trạng thái thêm mới
    }//GEN-LAST:event_buttonthemActionPerformed

    private void buttonluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonluuActionPerformed
        // TODO add your handling code here:
    String hoten = txtHoten.getText().trim(); // Lấy giá trị Họ tên và loại bỏ khoảng trắng
    String tuoiStr = txtTuoi.getText().trim(); // Lấy giá trị Tuổi và loại bỏ khoảng trắng
    String sdt = txtSdt.getText().trim(); // Lấy giá trị Số điện thoại và loại bỏ khoảng trắng
    String diachi = txtDiachi.getText().trim(); // Lấy giá trị Địa chỉ và loại bỏ khoảng trắng
    String chieucaoStr = txtChieucao.getText().trim(); // Lấy giá trị Mục tiêu và loại bỏ khoảng trắng

    // Kiểm tra xem người dùng đã nhập đủ thông tin hay chưa
    if (hoten.isEmpty() || tuoiStr.isEmpty() || sdt.isEmpty() || diachi.isEmpty() || chieucaoStr.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin", "Thông báo", JOptionPane.WARNING_MESSAGE);
    } else {
        try {
            int tuoi = Integer.parseInt(tuoiStr); // Chuyển đổi tuổi từ String sang int
            float chieucao = Float.parseFloat(chieucaoStr); // Chuyển đổi chiều cao từ String sang float

            // Tạo một đối tượng `xu_ly_formnguoidung` mới để lưu trữ thông tin
            xu_ly_formnguoidung obj = new xu_ly_formnguoidung();
            obj.setHoten(hoten); // Gán giá trị cho Họ tên
            obj.setTuoi(tuoi); // Gán giá trị cho Tuổi
            obj.setSdt(sdt); // Gán giá trị cho Số điện thoại
            obj.setDiachi(diachi); // Gán giá trị cho Địa chỉ
            obj.setChieucao(chieucao); // Gán giá trị cho Mục tiêu (float)

            // Kiểm tra trạng thái để xác định là thêm mới hay sửa
            if (cothem) { // Lưu cho thêm mới
                xuLy.insertNguoiDung(obj);
            } else { // Lưu cho sửa
                xuLy.editNguoiDung(obj);
            }

            // Cập nhật lại tableModel
            ClearData(); // Gọi hàm xóa dữ liệu trong tableModel
            ShowData(); // Đưa lại dữ liệu vào tableModel

            // Thiết lập lại trạng thái các JTextField và JButton
            setKhoa(true); // Khóa các JTextField
            updateButtonPermissions(); // Khóa tất cả các JButton
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tuổi phải là số nguyên và Chiều cao phải là số thực", "Thông báo", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_buttonluuActionPerformed

    private void buttonkluuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonkluuActionPerformed
        // TODO add your handling code here:
                    // Xóa dữ liệu trong các JTextField
            setNull();
           // Khóa tất cả các JTextField để ngăn người dùng nhập dữ liệu
           setKhoa(true);
           // Kích hoạt lại các JButton cần thiết
           updateButtonPermissions();
           // Nếu cần, đặt lại trạng thái thêm mới
           cothem = true;
    }//GEN-LAST:event_buttonkluuActionPerformed

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
        // TODO add your handling code here:
        Dangnhap mainForm = new Dangnhap();
        mainForm.setVisible(true); // Hiển thị MainForm
        this.dispose(); // Đóng SecondForm
    }//GEN-LAST:event_LogoutMouseClicked

    private void data_suc_khoeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_suc_khoeMouseClicked
Form_xem_lich_su formLichSu = new Form_xem_lich_su();
formLichSu.setUserRole(this.userRole); // Truyền quyền từ form hiện tại
formLichSu.setVisible(true);
this.dispose();

    }//GEN-LAST:event_data_suc_khoeMouseClicked

    private void xem_lich_suMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xem_lich_suMouseClicked
        Form_xem_lich_su formLichSu = new Form_xem_lich_su();
        formLichSu.setUserRole(userRole); // Truyền quyền
        formLichSu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_xem_lich_suMouseClicked

    private void data_nguoi_dungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_nguoi_dungMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_data_nguoi_dungMouseClicked

    private void data_suc_khoeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_suc_khoeMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_data_suc_khoeMouseEntered

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_nguoi_dung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_nguoi_dung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_nguoi_dung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_nguoi_dung.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_nguoi_dung().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logout;
    private javax.swing.JButton buttonkluu;
    private javax.swing.JButton buttonluu;
    private javax.swing.JButton buttonsua;
    private javax.swing.JButton buttonthem;
    private javax.swing.JButton buttonxoa;
    private javax.swing.JLabel data_nguoi_dung;
    private javax.swing.JLabel data_suc_khoe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_person;
    private javax.swing.JTextField txtChieucao;
    private javax.swing.JTextField txtDiachi;
    private javax.swing.JTextField txtHoten;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTuoi;
    private javax.swing.JLabel xem_lich_su;
    // End of variables declaration//GEN-END:variables
}