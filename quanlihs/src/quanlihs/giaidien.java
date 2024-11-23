/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlihs;

/**
 *
 * @author LENOVO
 */
import java.awt.*;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class giaidien extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable tableHocSinh;
    private DefaultTableModel modelHocSinh;
    private JButton btnThemHS, btnXoaHS, btnSuaHS;
    private JTextField txtHoTen, txtNgaySinh, txtGioiTinh;
    
    // Database connection variables
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public giaidien() {
        setTitle("Quản Lý Học Sinh");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        JPanel panelHocSinh = new JPanel(new BorderLayout());

        // Bảng học sinh
        modelHocSinh = new DefaultTableModel(new String[]{"Mã Học Sinh", "Họ Tên", "Ngày Sinh", "Giới Tính"}, 0);
        tableHocSinh = new JTable(modelHocSinh);
        JScrollPane scrollPaneHS = new JScrollPane(tableHocSinh);
        panelHocSinh.add(scrollPaneHS, BorderLayout.CENTER);

        // Panel nhập liệu
        JPanel panelInputHS = new JPanel(new GridLayout(2, 4, 10, 10));
        panelInputHS.add(new JLabel("Họ Tên:"));
        txtHoTen = new JTextField();
        panelInputHS.add(txtHoTen);
        panelInputHS.add(new JLabel("Ngày Sinh:"));
        txtNgaySinh = new JTextField();
        panelInputHS.add(txtNgaySinh);
        panelInputHS.add(new JLabel("Giới Tính:"));
        txtGioiTinh = new JTextField();
        panelInputHS.add(txtGioiTinh);

        panelHocSinh.add(panelInputHS, BorderLayout.NORTH);

        // Nút thêm, xóa, sửa học sinh
        JPanel panelButtonHS = new JPanel();
        btnThemHS = new JButton("Thêm");
        btnXoaHS = new JButton("Xóa");
        btnSuaHS = new JButton("Sửa");
        panelButtonHS.add(btnThemHS);
        panelButtonHS.add(btnXoaHS);
        panelButtonHS.add(btnSuaHS);
        panelHocSinh.add(panelButtonHS, BorderLayout.SOUTH);

        tabbedPane.addTab("Quản lý học sinh", panelHocSinh);
        add(tabbedPane);

        // Kết nối CSDL và xử lý sự kiện
        connectDatabase();
        loadDataHocSinh();
        addEventListeners();
    }

    // Kết nối cơ sở dữ liệu
    private void connectDatabase() {
        try {
conn = DriverManager.getConnection("jdbc:localhost_3306;databaseName=quanlihs;integratedSecurity=true");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kết nối cơ sở dữ liệu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xử lý sự kiện cho các nút
    private void addEventListeners() {
        btnThemHS.addActionListener(e -> {
            String hoTen = txtHoTen.getText();
            String ngaySinh = txtNgaySinh.getText();
            String gioiTinh = txtGioiTinh.getText();
            if (hoTen.isEmpty() || ngaySinh.isEmpty() || gioiTinh.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String sql = "INSERT INTO HocSinh (HoTen, NgaySinh, GioiTinh) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, hoTen);
                pstmt.setString(2, ngaySinh);
                pstmt.setString(3, gioiTinh);
                pstmt.executeUpdate();
                loadDataHocSinh();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnXoaHS.addActionListener(e -> {
            int row = tableHocSinh.getSelectedRow();
            if (row != -1) {
                int maHocSinh = (int) modelHocSinh.getValueAt(row, 0);
                try {
                    String sql = "DELETE FROM HocSinh WHERE MaHocSinh = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, maHocSinh);
                    pstmt.executeUpdate();
                    loadDataHocSinh();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // Load dữ liệu học sinh vào bảng
    private void loadDataHocSinh() {
        try {
            modelHocSinh.setRowCount(0);
            String sql = "SELECT * FROM HocSinh";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                modelHocSinh.addRow(new Object[]{
                    rs.getInt("MaHocSinh"),
                    rs.getString("HoTen"),
                    rs.getDate("NgaySinh"),
                    rs.getString("GioiTinh")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            giaidien app = new giaidien();
            app.setVisible(true);
        });
    }
}

