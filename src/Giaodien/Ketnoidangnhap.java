package Giaodien;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Ketnoidangnhap {
    public static Connection getJDBCConnection(){
        
        String url = "jdbc:mysql://localhost:3306/ungdungsuckhoe";
        String user = "root";
        String password = "123456";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Ketnoidangnhap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Ketnoidangnhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    public boolean login(String taikhoan,String matkhau) {
        Connection conn = getJDBCConnection();
        
        String sql = "SELECT * FROM dangnhap WHERE taikhoan = ? and matkhau = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,taikhoan);
            ps.setString(2,matkhau);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                return true;
            }else{
                return false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Ketnoidangnhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void register(Taikhoan taikhoan){
        Connection conn = getJDBCConnection();
        
        String sql = "INSERT INTO dangnhap(taikhoan,matkhau,email,role) VALUES(?,?,?,'guest')";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, taikhoan.getTaikhoan());
            ps.setString(2, taikhoan.getMatkhau());
            ps.setString(3, taikhoan.getEmail());
            
            int rs = ps.executeUpdate();
            
            if(rs != 0){
                System.out.println("Them thanh cong");
            }else{
                System.out.println("Them that bai");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Ketnoidangnhap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        // Phương thức kiểm tra tài khoản đã tồn tại
    public boolean isAccountExist(String taikhoan) {
        Connection conn = getJDBCConnection();
        String sql = "SELECT * FROM dangnhap WHERE taikhoan = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, taikhoan);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy tài khoản
        } catch (SQLException ex) {
            Logger.getLogger(Ketnoidangnhap.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Ketnoidangnhap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}