package ham_xu_ly;

import Giaodien.Ketnoidangnhap;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class xu_ly_formlichsu {
    private Connection conn;

    public xu_ly_formlichsu() {
        // Khởi tạo kết nối cơ sở dữ liệu
        conn = new Giaodien.Ketnoidangnhap().getJDBCConnection();
    }

    // Hàm để lấy danh sách tên duy nhất từ bảng table_person và thêm vào comboBox
    public void loadUserNames(JComboBox<String> cbb_ten) {
        try (Connection conn = Ketnoidangnhap.getJDBCConnection()) {
            String sql = "SELECT DISTINCT hoten FROM table_person";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            cbb_ten.removeAllItems(); // Xóa các mục cũ
            cbb_ten.addItem("Chọn tên người dùng"); // Thêm mục mặc định

            while (rs.next()) {
                cbb_ten.addItem(rs.getString("hoten")); // Thêm tên vào comboBox
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải tên người dùng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm để tải số điện thoại theo tên người dùng từ bảng table_person
public void loadPhoneNumbersByName(JComboBox<String> cbb_ten, JComboBox<String> cbb_sdt) {
    String tenDuocChon = (String) cbb_ten.getSelectedItem();
    if (tenDuocChon == null || tenDuocChon.equals("Chọn tên người dùng")) {
        cbb_sdt.removeAllItems(); // Nếu chưa chọn tên người dùng, xóa số điện thoại
        return;
    }

    try (Connection conn = Ketnoidangnhap.getJDBCConnection()) {
        String sql = "SELECT sdt FROM table_person WHERE hoten = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tenDuocChon);
        ResultSet rs = ps.executeQuery();

        cbb_sdt.removeAllItems(); // Xóa các mục cũ trong comboBox
        while (rs.next()) {
            cbb_sdt.addItem(rs.getString("sdt")); // Thêm số điện thoại vào comboBox
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi tải số điện thoại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


    // Hàm hiển thị thông tin sức khỏe theo số điện thoại
public void loadUserInfoByPhone(JComboBox<String> cbb_ten, JComboBox<String> cbb_sdt, JTable table_ls) {
    String sdtDuocChon = (String) cbb_sdt.getSelectedItem();
    if (sdtDuocChon == null || sdtDuocChon.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn số điện thoại hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try (Connection conn = Ketnoidangnhap.getJDBCConnection()) {
        String sql = "SELECT cannang, huyetap, nhiptim, buoc, nuoc, ngaynhap FROM table_fitness WHERE sdt = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, sdtDuocChon);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table_ls.getModel();
        model.setRowCount(0); // Xóa các dòng cũ trong bảng

        // Lặp qua kết quả truy vấn và thêm vào bảng
        while (rs.next()) {
            Object[] row = {
                rs.getFloat("cannang"),
                rs.getString("huyetap"),
                rs.getInt("nhiptim"),
                rs.getInt("buoc"),
                rs.getFloat("nuoc"),
                rs.getString("ngaynhap")
            };
            model.addRow(row); // Thêm dòng vào bảng
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sức khỏe: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}


    // Hàm để lấy danh sách tên người dùng từ bảng table_person
    public List<String> loadUserNames() {
        List<String> userNames = new ArrayList<>();
        try (Connection conn = Ketnoidangnhap.getJDBCConnection()) {
            String sql = "SELECT DISTINCT hoten FROM table_person";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userNames.add(rs.getString("hoten"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userNames;
    }
}
