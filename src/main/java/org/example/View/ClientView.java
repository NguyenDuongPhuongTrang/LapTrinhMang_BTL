package org.example.View;

import org.example.Control.ClientControl;
import org.example.Model.Phong;
import org.example.Model.TaiSan;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ClientView extends JFrame implements ActionListener {
    // Controller giao tiếp qua RMI
    private ClientControl clientControl;

    // Các thành phần form Sửa Phòng
    private JTextField txtMaPhong;
    private JTextField txtTenPhong;
    private JTextField txtMoTa;
    private JButton btnTaiPhong;
    private JButton btnSuaPhong;

    // Các thành phần form Sửa Tài Sản
    private JTextField txtMaTaiSan;
    private JTextField txtTenTaiSan;
    private JTextField txtLoai;
    private JTextField txtPhongTaiSan;
    private JTextField txtGiaTri;
    private JButton btnTaiTaiSan;
    private JButton btnSuaTaiSan;

    // Vùng hiển thị kết quả
    private JLabel lblKetQua;
    private JButton btnReset;

    public ClientView() {
        super("Quản lý tài sản trong phòng - Giao thức RMI");
        this.clientControl = new ClientControl();

        initUI();
    }

    private void initUI() {
        // Cấu hình phông chữ chung
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 13);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 14);

        // ------------------ 1. Panel Sửa Phòng ------------------
        txtMaPhong = new JTextField(15);
        txtTenPhong = new JTextField(15);
        txtMoTa = new JTextField(15);

        btnTaiPhong = new JButton("Tải dữ liệu cũ");
        btnTaiPhong.setFont(labelFont);
        btnTaiPhong.setToolTipText("Tải thông tin hiện tại của phòng theo mã để chỉnh sửa");
        btnTaiPhong.addActionListener(this);

        btnSuaPhong = new JButton("Sửa phòng");
        btnSuaPhong.setFont(boldFont);
        btnSuaPhong.setBackground(new Color(220, 240, 255));
        btnSuaPhong.addActionListener(this);

        JPanel panelMaPhong = new JPanel(new BorderLayout(5, 0));
        panelMaPhong.add(txtMaPhong, BorderLayout.CENTER);
        panelMaPhong.add(btnTaiPhong, BorderLayout.EAST);

        JPanel formPhong = new JPanel(new GridLayout(3, 2, 8, 10));
        formPhong.add(createLabel("Mã phòng cần sửa (*):", labelFont));
        formPhong.add(panelMaPhong);
        formPhong.add(createLabel("Tên phòng mới (*):", labelFont));
        formPhong.add(txtTenPhong);
        formPhong.add(createLabel("Mô tả mới:", labelFont));
        formPhong.add(txtMoTa);

        JPanel panelPhong = createBoxPanel("1. Nghiệp vụ: Sửa Phòng", titleFont, formPhong, btnSuaPhong);

        // ------------------ 2. Panel Sửa Tài Sản ------------------
        txtMaTaiSan = new JTextField(15);
        txtTenTaiSan = new JTextField(15);
        txtLoai = new JTextField(15);
        txtPhongTaiSan = new JTextField(15);
        txtGiaTri = new JTextField(15);

        btnTaiTaiSan = new JButton("Tải dữ liệu cũ");
        btnTaiTaiSan.setFont(labelFont);
        btnTaiTaiSan.setToolTipText("Tải thông tin hiện tại của tài sản theo mã để chỉnh sửa");
        btnTaiTaiSan.addActionListener(this);

        btnSuaTaiSan = new JButton("Sửa tài sản");
        btnSuaTaiSan.setFont(boldFont);
        btnSuaTaiSan.setBackground(new Color(220, 255, 230));
        btnSuaTaiSan.addActionListener(this);

        JPanel panelMaTaiSan = new JPanel(new BorderLayout(5, 0));
        panelMaTaiSan.add(txtMaTaiSan, BorderLayout.CENTER);
        panelMaTaiSan.add(btnTaiTaiSan, BorderLayout.EAST);

        JPanel formTaiSan = new JPanel(new GridLayout(5, 2, 8, 10));
        formTaiSan.add(createLabel("Mã tài sản cần sửa (*):", labelFont));
        formTaiSan.add(panelMaTaiSan);
        formTaiSan.add(createLabel("Tên tài sản mới (*):", labelFont));
        formTaiSan.add(txtTenTaiSan);
        formTaiSan.add(createLabel("Loại (*):", labelFont));
        formTaiSan.add(txtLoai);
        formTaiSan.add(createLabel("Vị trí phòng (Mã phòng) (*):", labelFont));
        formTaiSan.add(txtPhongTaiSan);
        formTaiSan.add(createLabel("Giá trị mới (VNĐ) (*):", labelFont));
        formTaiSan.add(txtGiaTri);

        JPanel panelTaiSan = createBoxPanel("2. Nghiệp vụ: Sửa Tài Sản", titleFont, formTaiSan, btnSuaTaiSan);

        // ------------------ Bố cục 2 cột ------------------
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        centerPanel.add(panelPhong);
        centerPanel.add(panelTaiSan);

        // ------------------ 3. Vùng Hiển thị Kết quả Sửa ------------------
        lblKetQua = new JLabel("Sẵn sàng. Nhập mã phòng hoặc mã tài sản cần sửa rồi nhấn nút thực hiện.");
        lblKetQua.setFont(boldFont);
        lblKetQua.setForeground(new Color(40, 80, 160));

        btnReset = new JButton("Làm mới form");
        btnReset.setFont(labelFont);
        btnReset.addActionListener(e -> resetForm());

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 5));
        bottomPanel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.GRAY),
                        "3. Hiển thị Kết Quả Sửa",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        titleFont
                ),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JPanel ketQuaTextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel lblTieuDeKQ = new JLabel("Trạng thái: ");
        lblTieuDeKQ.setFont(boldFont);
        ketQuaTextPanel.add(lblTieuDeKQ);
        ketQuaTextPanel.add(lblKetQua);

        JPanel resetPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        resetPanel.add(btnReset);

        bottomPanel.add(ketQuaTextPanel, BorderLayout.CENTER);
        bottomPanel.add(resetPanel, BorderLayout.EAST);

        // ------------------ Khung chính ------------------
        JPanel rootPanel = new JPanel(new BorderLayout(15, 15));
        rootPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        rootPanel.add(centerPanel, BorderLayout.CENTER);
        rootPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JPanel createBoxPanel(String title, Font titleFont, JPanel form, JButton actionBtn) {
        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 5));
        btnWrapper.add(actionBtn);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new CompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        title,
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        titleFont
                ),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panel.add(form, BorderLayout.NORTH);
        panel.add(btnWrapper, BorderLayout.SOUTH);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnSuaPhong) {
            xuLySuaPhong();
        } else if (src == btnSuaTaiSan) {
            xuLySuaTaiSan();
        } else if (src == btnTaiPhong) {
            xuLyTaiPhong();
        } else if (src == btnTaiTaiSan) {
            xuLyTaiTaiSan();
        }
    }

    // ==========================================
    // XỬ LÝ SỬA PHÒNG
    // ==========================================
    private void xuLySuaPhong() {
        String ma = txtMaPhong.getText().trim();
        String ten = txtTenPhong.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            hienThiLoi("Vui lòng nhập đầy đủ Mã phòng và Tên phòng mới!");
            return;
        }

        Phong phong = new Phong(ma, ten, moTa);

        try {
            boolean thanhCong = clientControl.suaPhong(phong);

            if (thanhCong) {
                String msg = "Sửa phòng [" + ma + "] thành công!";
                hienThiThanhCong(msg);
                JOptionPane.showMessageDialog(this, msg, "Thông báo thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String msg = "Sửa phòng thất bại! Không tìm thấy mã phòng [" + ma + "] trong cơ sở dữ liệu.";
                hienThiLoi(msg);
                JOptionPane.showMessageDialog(this, msg, "Lỗi cập nhật", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = "Lỗi kết nối RMI: " + ex.getMessage();
            hienThiLoi(msg);
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến RMI Server!\nVui lòng kiểm tra RMI Server đã chạy chưa.\nChi tiết: " + ex.getMessage(),
                    "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==========================================
    // XỬ LÝ SỬA TÀI SẢN
    // ==========================================
    private void xuLySuaTaiSan() {
        String ma = txtMaTaiSan.getText().trim();
        String ten = txtTenTaiSan.getText().trim();
        String loai = txtLoai.getText().trim();
        String maPhong = txtPhongTaiSan.getText().trim();
        String giaStr = txtGiaTri.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || loai.isEmpty() || maPhong.isEmpty() || giaStr.isEmpty()) {
            hienThiLoi("Vui lòng điền đầy đủ tất cả thông tin tài sản cần sửa!");
            return;
        }

        BigDecimal giaTri;
        try {
            giaTri = new BigDecimal(giaStr);
            if (giaTri.compareTo(BigDecimal.ZERO) < 0) {
                hienThiLoi("Giá trị tài sản không được là số âm!");
                return;
            }
        } catch (NumberFormatException ex) {
            hienThiLoi("Giá trị tài sản phải là số hợp lệ (ví dụ: 15000000)!");
            return;
        }

        TaiSan taiSan = new TaiSan(ma, ten, loai, maPhong, giaTri);

        try {
            boolean thanhCong = clientControl.suaTaiSan(taiSan);

            if (thanhCong) {
                String msg = "Sửa tài sản [" + ma + "] thành công!";
                hienThiThanhCong(msg);
                JOptionPane.showMessageDialog(this, msg, "Thông báo thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String msg = "Sửa tài sản thất bại! Không tìm thấy mã tài sản [" + ma + "] hoặc mã phòng [" + maPhong + "] không tồn tại.";
                hienThiLoi(msg);
                JOptionPane.showMessageDialog(this, msg, "Lỗi cập nhật", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = "Lỗi kết nối RMI: " + ex.getMessage();
            hienThiLoi(msg);
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến RMI Server!\nVui lòng kiểm tra RMI Server đã chạy chưa.\nChi tiết: " + ex.getMessage(),
                    "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==========================================
    // TẢI DỮ LIỆU CŨ ĐỂ TIỆN CHỈNH SỬA
    // ==========================================
    private void xuLyTaiPhong() {
        String ma = txtMaPhong.getText().trim();
        if (ma.isEmpty()) {
            hienThiLoi("Vui lòng nhập Mã phòng để tải dữ liệu!");
            return;
        }
        try {
            Phong p = clientControl.getPhongByMa(ma);
            if (p != null) {
                txtTenPhong.setText(p.getTenPhong());
                txtMoTa.setText(p.getMoTa());
                hienThiThanhCong("Đã tải dữ liệu phòng [" + ma + "] thành công.");
            } else {
                hienThiLoi("Không tìm thấy phòng với mã [" + ma + "].");
            }
        } catch (Exception ex) {
            hienThiLoi("Lỗi tải thông tin phòng: " + ex.getMessage());
        }
    }

    private void xuLyTaiTaiSan() {
        String ma = txtMaTaiSan.getText().trim();
        if (ma.isEmpty()) {
            hienThiLoi("Vui lòng nhập Mã tài sản để tải dữ liệu!");
            return;
        }
        try {
            TaiSan ts = clientControl.getTaiSanByMa(ma);
            if (ts != null) {
                txtTenTaiSan.setText(ts.getTenTaiSan());
                txtLoai.setText(ts.getLoai());
                txtPhongTaiSan.setText(ts.getMaPhong());
                txtGiaTri.setText(ts.getGiaTri() != null ? ts.getGiaTri().toPlainString() : "");
                hienThiThanhCong("Đã tải dữ liệu tài sản [" + ma + "] thành công.");
            } else {
                hienThiLoi("Không tìm thấy tài sản với mã [" + ma + "].");
            }
        } catch (Exception ex) {
            hienThiLoi("Lỗi tải thông tin tài sản: " + ex.getMessage());
        }
    }

    private void resetForm() {
        txtMaPhong.setText("");
        txtTenPhong.setText("");
        txtMoTa.setText("");
        txtMaTaiSan.setText("");
        txtTenTaiSan.setText("");
        txtLoai.setText("");
        txtPhongTaiSan.setText("");
        txtGiaTri.setText("");
        lblKetQua.setText("Đã làm mới form nhập liệu.");
        lblKetQua.setForeground(new Color(40, 80, 160));
    }

    private void hienThiThanhCong(String msg) {
        lblKetQua.setText(msg);
        lblKetQua.setForeground(new Color(0, 130, 0));
    }

    private void hienThiLoi(String msg) {
        lblKetQua.setText(msg);
        lblKetQua.setForeground(Color.RED);
    }
}
