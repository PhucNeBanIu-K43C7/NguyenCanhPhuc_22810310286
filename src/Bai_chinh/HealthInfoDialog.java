package Bai_chinh;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HealthInfoDialog extends JDialog {

    private JLabel lblCannang;
    private JLabel lblHuyetap;
    private JLabel lblNhiptim;
    private JLabel lblBuocchan;
    private JLabel lblNuoc;
    private JLabel lblNgaynhap;
    private JLabel lblBMI;
    private JLabel lblBMIDanhGia;
    private JLabel lblNuocCanThiet;
    private JLabel lblKhuyenNghi;

    public HealthInfoDialog(Frame parent, boolean modal, Map<String, String> healthInfo) {
        super(parent, modal);
        setTitle("Thông tin sức khỏe");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 400); // Tăng chiều rộng để tránh xuống dòng
        setLocationRelativeTo(parent);

        buildUI();
        populateHealthInfo(healthInfo);
    }

    private void buildUI() {
        // Sử dụng GridBagLayout để sắp xếp các thành phần đẹp hơn
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Khoảng cách giữa các thành phần
        gbc.anchor = GridBagConstraints.WEST; // Căn lề trái
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Thêm từng nhãn và giá trị
        addRow(mainPanel, gbc, "Cân nặng (kg):", lblCannang = new JLabel("N/A"));
        addRow(mainPanel, gbc, "Huyết áp:", lblHuyetap = new JLabel("N/A"));
        addRow(mainPanel, gbc, "Nhịp tim:", lblNhiptim = new JLabel("N/A"));
        addRow(mainPanel, gbc, "Bước chân:", lblBuocchan = new JLabel("N/A"));
        addRow(mainPanel, gbc, "Lượng nước (lít):", lblNuoc = new JLabel("N/A"));
        addRow(mainPanel, gbc, "Ngày nhập:", lblNgaynhap = new JLabel("N/A"));
        addRow(mainPanel, gbc, "Chỉ số BMI:", lblBMI = new JLabel("N/A"));
        addRow(mainPanel, gbc, "Đánh giá BMI:", lblBMIDanhGia = new JLabel("N/A"));
        addRow(mainPanel, gbc, "Lượng nước cần thiết (lít):", lblNuocCanThiet = new JLabel("N/A"));
        addRow(mainPanel, gbc, "Khuyến nghị:", lblKhuyenNghi = new JLabel("N/A"));

        // Nút đóng ở dưới cùng
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose()); // Đóng dialog
        gbc.gridy++; // Xuống dòng tiếp theo
        gbc.gridwidth = 2; // Chiếm cả hai cột
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(btnClose, gbc);

        // Bố cục tổng thể
        add(mainPanel, BorderLayout.CENTER);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, String labelText, JLabel valueLabel) {
        gbc.gridx = 0; // Cột 0: Nhãn
        gbc.gridy++;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1; // Cột 1: Giá trị
        panel.add(valueLabel, gbc);
    }

    private void populateHealthInfo(Map<String, String> healthInfo) {
        if (healthInfo != null) {
            lblCannang.setText(healthInfo.getOrDefault("Cân nặng", "N/A"));
            lblHuyetap.setText(healthInfo.getOrDefault("Huyết áp", "N/A"));
            lblNhiptim.setText(healthInfo.getOrDefault("Nhịp tim", "N/A"));
            lblBuocchan.setText(healthInfo.getOrDefault("Bước chân", "N/A"));
            lblNuoc.setText(healthInfo.getOrDefault("Lượng nước", "N/A"));
            lblNgaynhap.setText(healthInfo.getOrDefault("Ngày nhập", "N/A"));

            try {
                double cannang = Double.parseDouble(healthInfo.getOrDefault("Cân nặng", "0").trim());
                double chieucao = Double.parseDouble(healthInfo.getOrDefault("Chiều cao", "0").trim());
                double luongNuocDaUong = Double.parseDouble(healthInfo.getOrDefault("Lượng nước", "0").trim());

                if (cannang > 0 && chieucao > 0) {
                    double bmi = cannang / (chieucao * chieucao);
                    lblBMI.setText(String.format("%.2f", bmi));
                    lblBMIDanhGia.setText(getBMIDanhGia(bmi));
                } else {
                    lblBMI.setText("Không hợp lệ");
                    lblBMIDanhGia.setText("Không xác định");
                }

                double luongNuocCanThiet = cannang * 0.04;
                lblNuocCanThiet.setText(String.format("%.2f lít", luongNuocCanThiet));

                if (luongNuocDaUong < luongNuocCanThiet) {
                    lblKhuyenNghi.setText("Hãy uống thêm nước!");
                } else {
                    lblKhuyenNghi.setText("Bạn đã uống đủ nước.");
                }
            } catch (NumberFormatException e) {
                lblBMI.setText("Không hợp lệ");
                lblBMIDanhGia.setText("Không xác định");
                lblNuocCanThiet.setText("Không hợp lệ");
                lblKhuyenNghi.setText("Không xác định");
            }
        }
    }

    private String getBMIDanhGia(double bmi) {
        if (bmi < 18.5) return "Thiếu cân";
        if (bmi < 25) return "Cân nặng lý tưởng";
        if (bmi < 30) return "Thừa cân";
        if (bmi < 35) return "Béo phì độ 1";
        if (bmi < 40) return "Béo phì độ 2";
        return "Béo phì độ 3";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, String> healthInfo = Map.of(
            "Cân nặng", "65 kg",
            "Chiều cao", "1.7 m",
            "Huyết áp", "120/80 mmHg",
            "Nhịp tim", "72 bpm",
            "Bước chân", "8000 bước",
            "Lượng nước", "2.5 lít",
            "Ngày nhập", "2024-11-17"
        );

        EventQueue.invokeLater(() -> {
            HealthInfoDialog dialog = new HealthInfoDialog(null, true, healthInfo);
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
