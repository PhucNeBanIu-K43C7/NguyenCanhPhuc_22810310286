package ham_xu_ly;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import Giaodien.Ketnoidangnhap;

public class xu_ly_formsuckhoe {
    private float cannang;
    private String huyetap;
    private int nhiptim;
    private int buoc;
    private float nuoc;
    private String ngaynhap;
    private String sdt;
    private String hoten;
    private Ketnoidangnhap cn = new Ketnoidangnhap();

    // Getters và Setters
    public float getCanNang() { return cannang; }
    public void setCanNang(float cannang) { this.cannang = cannang; }
    public String getHuyetAp() { return huyetap; }
    public void setHuyetAp(String huyetap) { this.huyetap = huyetap; }
    public int getNhipTim() { return nhiptim; }
    public void setNhipTim(int nhiptim) { this.nhiptim = nhiptim; }
    public int getBuocChan() { return buoc; }
    public void setBuocChan(int buoc) { this.buoc = buoc; }
    public float getLuongNuocDaUong() { return nuoc; }
    public void setLuongNuocDaUong(float nuoc) { this.nuoc = nuoc; }
    public String getNgayNhap() { return ngaynhap; }
    public void setNgayNhap(String ngaynhap) { this.ngaynhap = ngaynhap; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getHoten() { return hoten; }
    public void setHoten(String hoten) { this.hoten = hoten; }

    // Truy vấn thông tin sức khỏe từ bảng, chỉ hiển thị các cột cần thiết
    public List<xu_ly_formsuckhoe> getThongTinSucKhoe() throws SQLException {
        List<xu_ly_formsuckhoe> list = new ArrayList<>();
        String sql = "SELECT cannang, huyetap, nhiptim, buoc, nuoc, ngaynhap FROM table_fitness";

        try (Connection con = Ketnoidangnhap.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                xu_ly_formsuckhoe sk = new xu_ly_formsuckhoe();
                sk.setCanNang(rs.getFloat("cannang"));
                sk.setHuyetAp(rs.getString("huyetap"));
                sk.setNhipTim(rs.getInt("nhiptim"));
                sk.setBuocChan(rs.getInt("buoc"));
                sk.setLuongNuocDaUong(rs.getFloat("nuoc"));
                sk.setNgayNhap(rs.getString("ngaynhap"));
                list.add(sk);
            }
        }
        return list;
    }

// Sửa lại hàm insertSucKhoe để không cần insert sdt
public boolean insertSucKhoe(xu_ly_formsuckhoe obj) throws SQLException {
    // Chỉ cần lấy số điện thoại đã có từ Form_nguoi_dung, không cần nhập lại
    String sql = "INSERT INTO table_fitness (cannang, huyetap, nhiptim, buoc, nuoc, ngaynhap, sdt) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = Ketnoidangnhap.getJDBCConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        // Các thông tin về sức khỏe
        ps.setFloat(1, obj.getCanNang());
        ps.setString(2, obj.getHuyetAp());
        ps.setInt(3, obj.getNhipTim());
        ps.setInt(4, obj.getBuocChan());
        ps.setFloat(5, obj.getLuongNuocDaUong());
        ps.setString(6, obj.getNgayNhap());
        
        // Lấy số điện thoại từ đối tượng đã được nhập trong form
        ps.setString(7, obj.getSdt());  // Đảm bảo sdt đã được nhập từ Form_nguoi_dung

        return ps.executeUpdate() > 0;
    }
}

    // Sửa thông tin sức khỏe theo số điện thoại
public boolean editSucKhoe(xu_ly_formsuckhoe obj) throws SQLException {
    String sql = "UPDATE table_fitness SET cannang = ?, huyetap = ?, nhiptim = ?, buoc = ?, nuoc = ?, ngaynhap = ? WHERE sdt = ?";

    try (Connection con = Ketnoidangnhap.getJDBCConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setFloat(1, obj.getCanNang());
        ps.setString(2, obj.getHuyetAp());
        ps.setInt(3, obj.getNhipTim());
        ps.setInt(4, obj.getBuocChan());
        ps.setFloat(5, obj.getLuongNuocDaUong());
        ps.setString(6, obj.getNgayNhap());
        ps.setString(7, obj.getSdt());

        return ps.executeUpdate() > 0;
    }
}


    // Xóa thông tin sức khỏe theo số điện thoại
    public boolean deleteSucKhoeBySdt(String sdt) throws SQLException {
        String sql = "DELETE FROM table_fitness WHERE sdt = ?";

        try (Connection con = Ketnoidangnhap.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sdt);
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy danh sách tên người dùng
    public List<String> getUserNames() throws SQLException {
        List<String> userNames = new ArrayList<>();
        String query = "SELECT DISTINCT hoten FROM table_person";

        try (Connection conn = Ketnoidangnhap.getJDBCConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                userNames.add(rs.getString("hoten"));
            }
        }
        return userNames;
    }

    // Lấy số điện thoại theo tên người dùng
    public List<String> getPhoneNumbersByName(String name) throws SQLException {
        List<String> phoneNumbers = new ArrayList<>();
        String query = "SELECT sdt FROM table_person WHERE hoten = ?";

        try (Connection conn = Ketnoidangnhap.getJDBCConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                phoneNumbers.add(rs.getString("sdt"));
            }
        }
        return phoneNumbers;
    }
    
    public void saveData(xu_ly_formsuckhoe sk) throws SQLException {
    String sql = "INSERT INTO table_fitness (cannang, nhiptim, buoc, nuoc, huyetap, ngaynhap) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = Ketnoidangnhap.getJDBCConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setFloat(1, sk.getCanNang());
        ps.setInt(2, sk.getNhipTim());
        ps.setInt(3, sk.getBuocChan());
        ps.setInt(4, (int) sk.getLuongNuocDaUong());
        ps.setString(5, sk.getHuyetAp());
        ps.setString(6, sk.getNgayNhap());
        ps.executeUpdate();
    }
}
    

public Map<String, String> getCombinedHealthData(String sdt) throws SQLException {
    String sql = "SELECT tf.cannang, tf.huyetap, tf.nhiptim, tf.buoc, tf.nuoc, tf.ngaynhap, tp.chieucao " +
                 "FROM table_fitness tf " +
                 "INNER JOIN table_person tp ON tf.sdt = tp.sdt " +
                 "WHERE tf.sdt = ?";
    try (Connection conn = Ketnoidangnhap.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, sdt);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return Map.of(
                    "Cân nặng", rs.getString("cannang"),
                    "Huyết áp", rs.getString("huyetap"),
                    "Nhịp tim", rs.getString("nhiptim"),
                    "Bước chân", rs.getString("buoc"),
                    "Lượng nước", rs.getString("nuoc"),
                    "Ngày nhập", rs.getString("ngaynhap"),
                    "Chiều cao", rs.getString("chieucao")
                );
            }
        }
    }
    return null; // Trả về null nếu không tìm thấy dữ liệu
}


public List<Object[]> getHealthDataByPhone(String phone) throws SQLException {
    String sql = "SELECT cannang, nhiptim, buoc, nuoc, huyetap, ngaynhap FROM table_fitness WHERE sdt = ?";
    try (Connection conn = Ketnoidangnhap.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, phone);
        try (ResultSet rs = ps.executeQuery()) {
            List<Object[]> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(new Object[]{
                    rs.getFloat("cannang"),
                    rs.getInt("nhiptim"),
                    rs.getInt("buoc"),
                    rs.getFloat("nuoc"),
                    rs.getString("huyetap"),
                    rs.getString("ngaynhap")
                });
            }
            return resultList;
        }
    }
}

public List<String> getAllUserNames() throws SQLException {
    String sql = "SELECT DISTINCT hoten FROM table_person";
    try (Connection conn = Ketnoidangnhap.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        List<String> names = new ArrayList<>();
        while (rs.next()) {
            names.add(rs.getString("hoten"));
        }
        return names;
    }
}


public List<String> getPhonesByUserName(String userName) throws SQLException {
    String sql = "SELECT sdt FROM table_person WHERE hoten = ?";
    try (Connection conn = Ketnoidangnhap.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, userName);
        try (ResultSet rs = ps.executeQuery()) {
            List<String> phones = new ArrayList<>();
            while (rs.next()) {
                phones.add(rs.getString("sdt"));
            }
            return phones;
        }
    }
}


public boolean addHealthData(String userPhone, String cannang, String huyetap, String nhiptim, String buoc, String nuoc, String ngaynhap) throws SQLException {
    String sql = "INSERT INTO table_fitness (sdt, cannang, huyetap, nhiptim, buoc, nuoc, ngaynhap) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = Ketnoidangnhap.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, userPhone);
        ps.setString(2, cannang);  // Giữ nguyên kiểu String
        ps.setString(3, huyetap);
        ps.setString(4, nhiptim);
        ps.setString(5, buoc);
        ps.setString(6, nuoc);
        ps.setString(7, ngaynhap);
        return ps.executeUpdate() > 0;
    }
}


public List<Object[]> getAllHealthDataByPhone(String phone) throws SQLException {
    String sql = "SELECT cannang, nhiptim, buoc, nuoc, huyetap, ngaynhap " +
                 "FROM table_fitness " +
                 "WHERE sdt = ? " +
                 "ORDER BY ngaynhap DESC";
    try (Connection conn = Ketnoidangnhap.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, phone);
        try (ResultSet rs = ps.executeQuery()) {
            List<Object[]> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(new Object[]{
                    rs.getFloat("cannang"),
                    rs.getInt("nhiptim"),
                    rs.getInt("buoc"),
                    rs.getFloat("nuoc"),
                    rs.getString("huyetap"),
                    rs.getString("ngaynhap")
                });
            }
            return resultList;
        }
    }
}


}
