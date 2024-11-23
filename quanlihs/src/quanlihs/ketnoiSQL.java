import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ketnoiSQL {
    public static Connection connect() {
        Connection conn = null;
        try {
            // Định dạng URL JDBC chính xác
            String url = "jdbc:sqlserver://DESKTOP-NI02QKO\\SQLEXPRESS;databaseName=quanlihs;integratedSecurity=true;";
            
            // Kết nối đến SQL Server
            conn = DriverManager.getConnection(url);
            System.out.println("Kết nối thành công!");
            
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
        return conn;
    }
    
    public static void main(String[] args) {
        // Kiểm tra kết nối
        Connection connection = connect();
        if (connection != null) {
            System.out.println("Kết nối cơ sở dữ liệu thành công.");
        } else {
            System.out.println("Kết nối cơ sở dữ liệu thất bại.");
        }
    }
}
