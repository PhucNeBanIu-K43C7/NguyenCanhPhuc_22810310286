
package Bai_chinh;

import Giaodien.*;
import ham_xu_ly.xu_ly_formsuckhoe;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.lang.String;
import java.util.logging.*;


public class Form_suc_khoe extends javax.swing.JFrame {

    private final DefaultTableModel tableModelSk = new DefaultTableModel();
    private boolean cothemSk = true; // Biến xác định thêm hay sửa
    private final xu_ly_formsuckhoe xuLySk = new xu_ly_formsuckhoe();
    private String userRole; // Biến lưu quyền người dùng
    private Map<String, String> healthInfo = new HashMap<>(); // Lưu thông tin sức khỏe
    private int selectedRow = -1; // Lưu dòng được chọn để sửa, mặc định không chọn dòng nào



public void setUserRole(String userRole) {
    if (userRole == null || userRole.isEmpty()) {
        this.userRole = "guest"; // Giá trị mặc định
        System.out.println("Quyền truyền vào Form_suc_khoe bị null. Đặt về mặc định: guest");
    } else {
        this.userRole = userRole;
        System.out.println("Quyền truyền vào Form_suc_khoe: " + userRole);
    }
    applyRolePermissions(); // Cập nhật quyền ngay sau khi nhận giá trị
}
        
    
private void applyRolePermissions() {
    boolean isGuest = "guest".equals(userRole);
    suabutton.setEnabled(!isGuest); // Khóa/mở khóa nút Sửa
    xoabutton.setEnabled(!isGuest); // Khóa/mở khóa nút Xóa
    thembutton.setEnabled(true);    // Luôn cho phép thêm
    luubutton.setEnabled(false);   // Đợi khi bấm nút Lưu mới bật
    kluubutton.setEnabled(false);  // Đợi khi bấm nút Không lưu mới bật
}


    
    public Form_suc_khoe() {
        initComponents();

        String[] colsName = {"Cân nặng", "Huyết áp", "Nhịp tim", "Bước chân", "Lượng nước đã uống", "Ngày nhập"};
        tableModelSk.setColumnIdentifiers(colsName);
        table_sk.setModel(tableModelSk);

        setNull(); // Xóa trắng các TextField
        setKhoa(true); // Khóa các TextField
        applyRolePermissions(); // Áp dụng quyền từ vai trò

        loadUserNames(); // Nạp tên người dùng vào ComboBox
        cbb_sdt.removeAllItems(); // Đặt ComboBox trống
        cbb_sdt.setSelectedIndex(-1); // Không chọn mục nào
        this.setLocationRelativeTo(null);
        
           // Debug quyền
    System.out.println("Quyền hiện tại trong Form_suc_khoe: " + this.userRole);
    }

    
private void updateTableByPhone(String phone) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = Ketnoidangnhap.getJDBCConnection();

        // Câu lệnh SQL lấy hàng mới nhất dựa trên ngày nhập
        String sql = "SELECT cannang, nhiptim, buoc, nuoc, huyetap, ngaynhap " +
                     "FROM table_fitness " +
                     "WHERE sdt = ? " +
                     "ORDER BY ngaynhap DESC " +
                     "LIMIT 1";
        ps = conn.prepareStatement(sql);
        ps.setString(1, phone);
        rs = ps.executeQuery();

        // Xóa các hàng hiện có trong bảng
        DefaultTableModel model = (DefaultTableModel) table_sk.getModel();
        model.setRowCount(0);

        // Thêm hàng mới nhất vào bảng
        if (rs.next()) {
            Object[] rowData = {
                rs.getFloat("cannang"),
                rs.getInt("nhiptim"),
                rs.getInt("buoc"),
                rs.getFloat("nuoc"),
                rs.getString("huyetap"),
                rs.getString("ngaynhap")
            };
            model.addRow(rowData);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu mới nhất!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    // Hàm tải tên người dùng vào ComboBox
private void loadUserNames() {
    try {
        List<String> userNames = xuLySk.getAllUserNames();
        cbb_ten.removeAllItems(); // Xóa các mục cũ
        cbb_ten.addItem("Nhập thông tin người dùng"); // Thêm mục mặc định

        for (String name : userNames) {
            cbb_ten.addItem(name); // Thêm từng tên người dùng
        }

        cbb_ten.setSelectedIndex(0); // Đặt mục đầu tiên làm lựa chọn mặc định
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi tải tên người dùng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}

    private void loadUserPhonesByName(String userName) {
        try (Connection conn = Ketnoidangnhap.getJDBCConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!", "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "SELECT sdt FROM table_person WHERE hoten = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();
            cbb_sdt.removeAllItems(); // Xóa các mục cũ trong comboBox

            while (rs.next()) {
                cbb_sdt.addItem(rs.getString("sdt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải số điện thoại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
private void showHealthData(String phone) {
    try {
        // Truy vấn dữ liệu từ CSDL
        List<Object[]> healthData = xuLySk.getAllHealthDataByPhone(phone);
        DefaultTableModel model = (DefaultTableModel) table_sk.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        if (healthData != null && !healthData.isEmpty()) {
            for (Object[] row : healthData) {
                model.addRow(row); // Thêm từng hàng dữ liệu vào bảng
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để hiển thị!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị dữ liệu sức khỏe: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


    // Hàm thêm thông tin sức khỏe vào bảng table_fitness
public void addHealthData(String userPhone, String cannang, String huyetap, 
                          String nhiptim, String buoc, String nuoc, String ngaynhap) {
    if (userPhone == null || userPhone.isEmpty() || cannang.isEmpty() || huyetap.isEmpty() ||
        nhiptim.isEmpty() || buoc.isEmpty() || nuoc.isEmpty() || ngaynhap.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String sql = "INSERT INTO table_fitness (sdt, cannang, huyetap, nhiptim, buoc, nuoc, ngaynhap) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = Ketnoidangnhap.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, userPhone);
        ps.setString(2, cannang);
        ps.setString(3, huyetap);
        ps.setString(4, nhiptim);
        ps.setString(5, buoc);
        ps.setString(6, nuoc);
        ps.setString(7, ngaynhap);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Thông tin sức khỏe đã được thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Gọi các hàm sau khi thêm thành công
            setNull(); // Xóa trắng các TextField
            setButton(true); // Điều chỉnh trạng thái các nút
            setKhoa(true); // Khóa các trường nhập liệu
            applyRolePermissions(); // Áp dụng lại quyền

            // Hiển thị dữ liệu mới
            showHealthData(userPhone); // Hiển thị dữ liệu sức khỏe theo số điện thoại
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi thêm thông tin sức khỏe: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


    // Set lại giá trị mặc định cho các TextField
    private void setNull() {
        this.txtDate.setText(null);
        this.txtCannang.setText(null);
        this.txtHuyetap.setText(null);
        this.txtNhiptim.setText(null);
        this.txtBuoc.setText(null);
        this.txtNuoc.setText(null);
        this.txtDate.requestFocus();
    }

    // Khóa hay mở khóa các TextField
    private void setKhoa(boolean a) {
        this.txtDate.setEnabled(!a);
        this.txtCannang.setEnabled(!a);
        this.txtHuyetap.setEnabled(!a);
        this.txtNhiptim.setEnabled(!a);
        this.txtBuoc.setEnabled(!a);
        this.txtNuoc.setEnabled(!a);
    }

    // Điều khiển trạng thái của các nút
private void setButton(boolean a) {
    this.thembutton.setEnabled(a);
    this.suabutton.setEnabled(a);
    this.xoabutton.setEnabled(a);
    this.luubutton.setEnabled(!a); // Mở khóa nút Lưu khi đang sửa
    this.kluubutton.setEnabled(!a); // Mở khóa nút Không Lưu khi đang sửa
    applyRolePermissions();
}

    
    // Hàm ClearData để xóa dữ liệu trong bảng và các TextField
private void ClearDataSK() {
    // Xóa dữ liệu trong bảng
    tableModelSk.setRowCount(0); // Xóa tất cả các dòng dữ liệu trong bảng

    // Xóa dữ liệu trong các TextField
    txtCannang.setText("");
    txtHuyetap.setText("");
    txtNhiptim.setText("");
    txtBuoc.setText("");
    txtNuoc.setText("");
    txtDate.setText("");
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbb_sdt = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCannang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNhiptim = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtHuyetap = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtBuoc = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtNuoc = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        thembutton = new javax.swing.JButton();
        suabutton = new javax.swing.JButton();
        xoabutton = new javax.swing.JButton();
        luubutton = new javax.swing.JButton();
        kluubutton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_sk = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbb_ten = new javax.swing.JComboBox<>();
        form_ho_so = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        xem_lich_su = new javax.swing.JLabel();
        thongbao = new javax.swing.JLabel();
        Logout = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setPreferredSize(new java.awt.Dimension(675, 447));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("KIỂM TRA SỨC KHỎE");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setText("Số điện thoại");

        cbb_sdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_sdtActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 0));
        jLabel7.setText("Ngày nhập");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 0));
        jLabel3.setText("Cân nặng");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 0));
        jLabel4.setText("kg");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 0));
        jLabel5.setText("Nhịp tim");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 0));
        jLabel6.setText("Huyết áp");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 0));
        jLabel13.setText("mmHg");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 153, 0));
        jLabel15.setText("BPM");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 153, 0));
        jLabel16.setText("Bước chân");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 153, 0));
        jLabel17.setText("Nước");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 0));
        jLabel18.setText("Lít");

        thembutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        thembutton.setForeground(new java.awt.Color(0, 153, 0));
        thembutton.setText("Thêm");
        thembutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thembuttonActionPerformed(evt);
            }
        });

        suabutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        suabutton.setForeground(new java.awt.Color(0, 153, 0));
        suabutton.setText("Sửa");
        suabutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suabuttonActionPerformed(evt);
            }
        });

        xoabutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        xoabutton.setForeground(new java.awt.Color(0, 153, 0));
        xoabutton.setText("Xóa");
        xoabutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoabuttonActionPerformed(evt);
            }
        });

        luubutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        luubutton.setForeground(new java.awt.Color(0, 153, 0));
        luubutton.setText("Lưu");
        luubutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                luubuttonActionPerformed(evt);
            }
        });

        kluubutton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        kluubutton.setForeground(new java.awt.Color(0, 153, 0));
        kluubutton.setText("K.Lưu");
        kluubutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kluubuttonActionPerformed(evt);
            }
        });

        table_sk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cân nặng", "Huyết áp", "Nhịp tim", "Bước chân", "L.nước đã uống", "Ngày nhập"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Float.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class
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
        table_sk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_skMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_sk);
        if (table_sk.getColumnModel().getColumnCount() > 0) {
            table_sk.getColumnModel().getColumn(0).setResizable(false);
            table_sk.getColumnModel().getColumn(1).setResizable(false);
            table_sk.getColumnModel().getColumn(2).setResizable(false);
            table_sk.getColumnModel().getColumn(3).setResizable(false);
            table_sk.getColumnModel().getColumn(4).setResizable(false);
            table_sk.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 153, 0));
        jLabel21.setText("Bước");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 0));
        jLabel8.setText("Người dùng");

        cbb_ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbb_tenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(thembutton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(153, 153, 153)
                                .addComponent(xoabutton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtCannang, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(166, 166, 166)
                                .addComponent(kluubutton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(luubutton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtHuyetap, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel13))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(suabutton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbb_sdt, 0, 150, Short.MAX_VALUE)
                                    .addComponent(cbb_ten, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(74, 74, 74)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNhiptim, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(txtBuoc)
                            .addComponent(txtNuoc))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(208, 208, 208))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNhiptim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel8)
                    .addComponent(cbb_ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtBuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel2)
                    .addComponent(cbb_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtHuyetap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel3)
                    .addComponent(txtCannang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thembutton)
                    .addComponent(suabutton)
                    .addComponent(xoabutton)
                    .addComponent(luubutton)
                    .addComponent(kluubutton))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        form_ho_so.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        form_ho_so.setForeground(new java.awt.Color(242, 242, 242));
        form_ho_so.setText("HỒ SƠ");
        form_ho_so.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                form_ho_soMouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(242, 242, 242));
        jLabel9.setText("<html>THÔNG TIN<br>SỨC KHỎE</html>");

        xem_lich_su.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        xem_lich_su.setForeground(new java.awt.Color(242, 242, 242));
        xem_lich_su.setText("XEM LỊCH SỬ");
        xem_lich_su.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                xem_lich_suMouseClicked(evt);
            }
        });

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
                    .addComponent(jLabel9)
                    .addComponent(form_ho_so, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xem_lich_su, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
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
                .addGap(74, 74, 74)
                .addComponent(form_ho_so)
                .addGap(30, 30, 30)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(xem_lich_su)
                .addGap(30, 30, 30)
                .addComponent(thongbao, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
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

    private void suabuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suabuttonActionPerformed
    // Lấy chỉ số dòng được chọn trong bảng
    selectedRow = table_sk.getSelectedRow();

    if (selectedRow == -1) { // Kiểm tra nếu chưa chọn dòng nào
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Lấy dữ liệu từ dòng được chọn
    DefaultTableModel model = (DefaultTableModel) table_sk.getModel();
    txtCannang.setText(model.getValueAt(selectedRow, 0).toString());
    txtHuyetap.setText(model.getValueAt(selectedRow, 1).toString());
    txtNhiptim.setText(model.getValueAt(selectedRow, 2).toString());
    txtBuoc.setText(model.getValueAt(selectedRow, 3).toString());
    txtNuoc.setText(model.getValueAt(selectedRow, 4).toString());
    txtDate.setText(model.getValueAt(selectedRow, 5).toString());

    // Chuyển trạng thái sửa
    cothemSk = false; // Đang sửa
    setKhoa(false); // Mở khóa các TextField
    setButton(false); // Chuyển trạng thái nút
    }//GEN-LAST:event_suabuttonActionPerformed

    private void xoabuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoabuttonActionPerformed
        // TODO add your handling code here:
    // Kiểm tra xem họ tên đã được chọn từ ComboBox chưa
    if (cbb_sdt.getSelectedItem() == null || cbb_sdt.getSelectedItem().toString().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn họ tên người dùng từ danh sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
    // Lấy số điện thoại người dùng đã chọn từ ComboBox
    String selectedUser = cbb_sdt.getSelectedItem().toString();

    // Hiển thị hộp thoại xác nhận khi xóa thông tin sức khỏe
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Bạn có chắc chắn muốn xóa thông tin sức khỏe của người dùng: " + selectedUser + "?", 
        "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        // Thực hiện xóa thông tin sức khỏe của người dùng từ cơ sở dữ liệu
        try {
            boolean isDeleted = xuLySk.deleteSucKhoeBySdt(selectedUser); // Gọi hàm deleteSucKhoeBySdt từ xuLySk
            if (isDeleted) {
                JOptionPane.showMessageDialog(this, "Xóa thông tin sức khỏe thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Cập nhật lại bảng và ComboBox sau khi xóa
                loadUserNames();  // Tải lại tên người dùng vào ComboBox
                cbb_sdt.setSelectedIndex(-1); // Đặt ComboBox về trạng thái không chọn
                ClearDataSK(); // Xóa dữ liệu hiện tại trên bảng và TextField
                setNull(); // Đảm bảo các TextField sẽ được làm sạch
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa thông tin sức khỏe!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_xoabuttonActionPerformed

    private void thembuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thembuttonActionPerformed
    cothemSk = true; // Chuyển trạng thái sang Thêm mới
    setNull(); // Xóa trắng các TextField
    setKhoa(false); // Mở khóa các TextField
    luubutton.setEnabled(true); // Bật nút Lưu
    kluubutton.setEnabled(true); // Bật nút Không lưu
    thembutton.setEnabled(false); // Vô hiệu hóa nút Thêm trong khi thao tác
    suabutton.setEnabled(false); // Vô hiệu hóa nút Sửa
    xoabutton.setEnabled(false); // Vô hiệu hóa nút Xóa
    }//GEN-LAST:event_thembuttonActionPerformed

    private void luubuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_luubuttonActionPerformed
    // Lấy dữ liệu từ các TextField
    // Lấy dữ liệu từ các TextField
    String cannang = txtCannang.getText();
    String huyetap = txtHuyetap.getText();
    String nhiptim = txtNhiptim.getText();
    String buoc = txtBuoc.getText();
    String nuoc = txtNuoc.getText();
    String ngaynhap = txtDate.getText();
    String phone = (String) cbb_sdt.getSelectedItem();

    if (phone == null || phone.isEmpty() || cannang.isEmpty() || huyetap.isEmpty() || 
        nhiptim.isEmpty() || buoc.isEmpty() || nuoc.isEmpty() || ngaynhap.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        if (cothemSk) {
            // Thêm thông tin mới
            boolean success = xuLySk.addHealthData(phone, cannang, huyetap, nhiptim, buoc, nuoc, ngaynhap);
            if (success) {
                JOptionPane.showMessageDialog(this, "Thông tin sức khỏe đã được thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Hiển thị thông tin mới sau khi thêm
                showHealthData(phone);
                setNull(); // Xóa trắng các TextField
                setKhoa(true); // Khóa các TextField
                setButton(true); // Đặt lại trạng thái các nút
                applyRolePermissions(); // Kiểm tra và áp dụng lại quyền
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Sửa thông tin dòng được chọn
            xu_ly_formsuckhoe updatedData = new xu_ly_formsuckhoe();
            updatedData.setCanNang(Float.parseFloat(cannang));
            updatedData.setHuyetAp(huyetap);
            updatedData.setNhipTim(Integer.parseInt(nhiptim));
            updatedData.setBuocChan(Integer.parseInt(buoc));
            updatedData.setLuongNuocDaUong(Float.parseFloat(nuoc));
            updatedData.setNgayNhap(ngaynhap);
            updatedData.setSdt(phone);

            boolean success = xuLySk.editSucKhoe(updatedData);
            if (success) {
                JOptionPane.showMessageDialog(this, "Thông tin sức khỏe đã được sửa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Hiển thị thông tin mới sau khi sửa
                showHealthData(phone);
                setNull(); // Xóa trắng các TextField
                setKhoa(true); // Khóa các TextField
                setButton(true); // Đặt lại trạng thái các nút
                applyRolePermissions(); // Kiểm tra và áp dụng lại quyền
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi khi thực hiện thao tác trên cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho các trường dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_luubuttonActionPerformed

    private void kluubuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kluubuttonActionPerformed
        // TODO add your handling code here:
            // Khôi phục lại các TextField và nút về trạng thái ban đầu
    setNull();
    setKhoa(true); // Khóa các TextField lại
    setButton(true); // Đặt lại trạng thái của các nút
    }//GEN-LAST:event_kluubuttonActionPerformed

    private void form_ho_soMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_form_ho_soMouseClicked
    Form_nguoi_dung formNguoiDung = new Form_nguoi_dung();
    formNguoiDung.setUserRole(userRole); // Truyền quyền ngược lại
    formNguoiDung.setVisible(true);
    this.dispose(); // Đóng form hiện tại
    }//GEN-LAST:event_form_ho_soMouseClicked

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
        // TODO add your handling code here:
        Dangnhap mainForm = new Dangnhap();
        mainForm.setVisible(true); // Hiển thị MainForm
        this.dispose(); // Đóng SecondForm
    }//GEN-LAST:event_LogoutMouseClicked

    private void xem_lich_suMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xem_lich_suMouseClicked
        // TODO add your handling code here:
        Form_xem_lich_su formlichsu = new Form_xem_lich_su();
        formlichsu.setVisible(true);
        dispose();
    }//GEN-LAST:event_xem_lich_suMouseClicked

    private void cbb_tenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_tenActionPerformed
    String userName = (String) cbb_ten.getSelectedItem();

    // Kiểm tra nếu người dùng chưa chọn hoặc chọn mục mặc định
    if (userName == null || userName.equals("Nhập thông tin người dùng")) {
        cbb_sdt.removeAllItems(); // Xóa dữ liệu trong ComboBox số điện thoại
        return;
    }

    // Nếu chọn hợp lệ, tải số điện thoại liên quan
    loadUserPhonesByName(userName);
    }//GEN-LAST:event_cbb_tenActionPerformed

    private void table_skMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_skMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_table_skMouseClicked

    private void cbb_sdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbb_sdtActionPerformed

    String phone = (String) cbb_sdt.getSelectedItem();
    if (phone != null) {
        showHealthData(phone);
    }
    }//GEN-LAST:event_cbb_sdtActionPerformed

    private void thongbaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thongbaoMouseClicked
    String selectedPhoneNumber = (String) cbb_sdt.getSelectedItem(); // Lấy số điện thoại từ ComboBox
    if (selectedPhoneNumber == null || selectedPhoneNumber.isEmpty()) {
        int selectedRow = table_sk.getSelectedRow();
        if (selectedRow != -1) {
            selectedPhoneNumber = table_sk.getValueAt(selectedRow, 0).toString(); // Lấy từ JTable nếu ComboBox không có
        }
    }

    if (selectedPhoneNumber == null || selectedPhoneNumber.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn số điện thoại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
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
            java.util.logging.Logger.getLogger(Form_suc_khoe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_suc_khoe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_suc_khoe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_suc_khoe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_suc_khoe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logout;
    private javax.swing.JComboBox<String> cbb_sdt;
    private javax.swing.JComboBox<String> cbb_ten;
    private javax.swing.JLabel form_ho_so;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton kluubutton;
    private javax.swing.JButton luubutton;
    private javax.swing.JButton suabutton;
    private javax.swing.JTable table_sk;
    private javax.swing.JButton thembutton;
    private javax.swing.JLabel thongbao;
    private javax.swing.JTextField txtBuoc;
    private javax.swing.JTextField txtCannang;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtHuyetap;
    private javax.swing.JTextField txtNhiptim;
    private javax.swing.JTextField txtNuoc;
    private javax.swing.JLabel xem_lich_su;
    private javax.swing.JButton xoabutton;
    // End of variables declaration//GEN-END:variables
}
