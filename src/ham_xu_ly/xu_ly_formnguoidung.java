package ham_xu_ly;

import java.sql.*;
import Giaodien.Ketnoidangnhap;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class xu_ly_formnguoidung {
    private String hoten;
    private int tuoi;
    private String diachi;
    private String sdt;
    private float chieucao;
    private Ketnoidangnhap cn = new Ketnoidangnhap();
    
    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public float getChieucao() {
        return chieucao;
    }

    public void setChieucao(float chieucao) {
        this.chieucao = chieucao;
    }
    
    // Truy vấn dữ liệu từ bảng table_person với tất cả các cột
    public List<xu_ly_formnguoidung> getThongTinNguoiDung() throws SQLException {
        List<xu_ly_formnguoidung> list = new ArrayList<>();
        String sql = "SELECT * FROM table_person";  // Chỉ cần sử dụng * để lấy tất cả cột
        
        try (Connection con = Ketnoidangnhap.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                xu_ly_formnguoidung nguoiDung = new xu_ly_formnguoidung();
                nguoiDung.setHoten(rs.getString("hoten"));
                nguoiDung.setTuoi(rs.getInt("tuoi"));
                nguoiDung.setSdt(rs.getString("sdt"));
                nguoiDung.setDiachi(rs.getString("diachi"));
                nguoiDung.setChieucao(rs.getFloat("chieucao"));  // Đảm bảo kiểu float
                list.add(nguoiDung);
            }
        }
        return list;
    }

    // Phương thức truy vấn thông tin người dùng theo số điện thoại
    public xu_ly_formnguoidung getNguoiDungBySdt(String sdt) throws SQLException {
        String sql = "SELECT * FROM table_person WHERE sdt=?";
        
        try (Connection con = Ketnoidangnhap.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sdt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    xu_ly_formnguoidung nguoiDung = new xu_ly_formnguoidung();
                    nguoiDung.setHoten(rs.getString("hoten"));
                    nguoiDung.setTuoi(rs.getInt("tuoi"));
                    nguoiDung.setSdt(rs.getString("sdt"));
                    nguoiDung.setDiachi(rs.getString("diachi"));
                    nguoiDung.setChieucao(rs.getFloat("chieucao"));  // Đảm bảo kiểu float
                    return nguoiDung;
                }
            }
        }
        return null;
    }

    // Thêm thông tin người dùng vào bảng table_person
    public boolean insertNguoiDung(xu_ly_formnguoidung obj) throws SQLException {
        String sql = "INSERT INTO table_person (hoten, tuoi, sdt, diachi, chieucao) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = Ketnoidangnhap.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getHoten());
            ps.setInt(2, obj.getTuoi());
            ps.setString(3, obj.getSdt());
            ps.setString(4, obj.getDiachi());
            ps.setFloat(5, obj.getChieucao());  // Đảm bảo kiểu float
            return ps.executeUpdate() > 0;
        }
    }

    // Sửa thông tin người dùng trong bảng table_person
    public boolean editNguoiDung(xu_ly_formnguoidung obj) throws SQLException {
        String sql = "UPDATE table_person SET tuoi=?, sdt=?, diachi=?, chieucao=? WHERE hoten=?";
        
        try (Connection con = Ketnoidangnhap.getJDBCConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getTuoi());
            ps.setString(2, obj.getSdt());
            ps.setString(3, obj.getDiachi());
            ps.setFloat(4, obj.getChieucao());  // Đảm bảo kiểu float
            ps.setString(5, obj.getHoten());
            return ps.executeUpdate() > 0;
        }
    }

public void deleteNguoiDung(String hoten) throws SQLException {
    Connection conn = Ketnoidangnhap.getJDBCConnection();
    String sql = "DELETE FROM table_person WHERE hoten = ?";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, hoten); // Thiết lập tham số họ tên vào câu lệnh SQL
        int rowsAffected = stmt.executeUpdate(); // Thực hiện xóa
        
        if (rowsAffected > 0) {
            System.out.println("Đã xóa người dùng: " + hoten);
        } else {
            System.out.println("Không tìm thấy người dùng để xóa");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}

    
        // Hàm lấy dữ liệu người dùng từ CSDL theo họ tên
    public xu_ly_formnguoidung getNguoiDungByHoTen(String hoten) throws SQLException {
        // Kết nối cơ sở dữ liệu
        try (Connection conn = Giaodien.Ketnoidangnhap.getJDBCConnection()) {
            String query = "SELECT hoten, tuoi, sdt, diachi, chieucao FROM table_person WHERE hoten = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, hoten);  // Thiết lập tham số trong câu truy vấn

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        xu_ly_formnguoidung user = new xu_ly_formnguoidung();
                        user.setHoten(rs.getString("hoten"));
                        user.setTuoi(rs.getInt("tuoi"));
                        user.setSdt(rs.getString("sdt"));
                        user.setDiachi(rs.getString("diachi"));
                        user.setChieucao(rs.getFloat("chieucao"));
                        return user;
                    }
                }
            }
        }
        return null;  // Trả về null nếu không tìm thấy người dùng
    }
}
