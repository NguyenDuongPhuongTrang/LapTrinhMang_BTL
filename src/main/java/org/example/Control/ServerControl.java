package org.example.Control;

import org.example.Model.Phong;
import org.example.Model.TaiSan;
import org.example.View.ServerView;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerControl extends UnicastRemoteObject implements RMIInterface {
    private static final long serialVersionUID = 1L;

    private Connection con;
    private ServerView view;

    public ServerControl(ServerView view) throws RemoteException {
        super();
        this.view = view;
        try {
            this.con = DBConnection.getConnection();
            log("Đã kết nối cơ sở dữ liệu MySQL thành công!");
        } catch (Exception e) {
            log("Lỗi kết nối CSDL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void log(String msg) {
        if (view != null) {
            view.showMessage("[ServerControl] " + msg);
        } else {
            System.out.println("[ServerControl] " + msg);
        }
    }

    private synchronized void checkConnection() throws SQLException {
        try {
            if (con == null || con.isClosed() || !con.isValid(2)) {
                con = DBConnection.getConnection();
                log("Đã tái thiết lập kết nối CSDL thành công.");
            }
        } catch (Exception e) {
            throw new SQLException("Không thể kết nối CSDL: " + e.getMessage(), e);
        }
    }

    // ==========================================
    // 1. CÁC PHƯƠNG THỨC TRỌNG TÂM: SỬA PHÒNG & SỬA TÀI SẢN
    // ==========================================

    @Override
    public boolean suaPhong(Phong phong) throws RemoteException {
        log("Nhận yêu cầu: Sửa thông tin phòng...");

        if (phong == null
                || phong.getMaPhong() == null || phong.getMaPhong().trim().isEmpty()
                || phong.getTenPhong() == null || phong.getTenPhong().trim().isEmpty()) {
            log("-> Thất bại: Dữ liệu phòng không hợp lệ (mã hoặc tên phòng rỗng).");
            return false;
        }

        String sql = "UPDATE phong SET ten_phong = ?, mo_ta = ? WHERE ma_phong = ?";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, phong.getTenPhong().trim());
                ps.setString(2, phong.getMoTa() != null ? phong.getMoTa().trim() : "");
                ps.setString(3, phong.getMaPhong().trim());

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    log("-> Sửa phòng thành công: Mã phòng = " + phong.getMaPhong()
                            + ", Tên mới = " + phong.getTenPhong());
                    return true;
                } else {
                    log("-> Thất bại: Không tìm thấy phòng với mã = " + phong.getMaPhong());
                    return false;
                }
            }
        } catch (SQLException e) {
            log("-> Lỗi SQL khi sửa phòng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean suaTaiSan(TaiSan taiSan) throws RemoteException {
        log("Nhận yêu cầu: Sửa thông tin tài sản...");

        if (taiSan == null
                || taiSan.getMaTaiSan() == null || taiSan.getMaTaiSan().trim().isEmpty()
                || taiSan.getTenTaiSan() == null || taiSan.getTenTaiSan().trim().isEmpty()
                || taiSan.getLoai() == null || taiSan.getLoai().trim().isEmpty()
                || taiSan.getMaPhong() == null || taiSan.getMaPhong().trim().isEmpty()
                || taiSan.getGiaTri() == null || taiSan.getGiaTri().compareTo(BigDecimal.ZERO) < 0) {
            log("-> Thất bại: Dữ liệu tài sản không hợp lệ.");
            return false;
        }

        try {
            checkConnection();

            // Kiểm tra mã phòng có tồn tại không
            String checkPhongSql = "SELECT COUNT(*) FROM phong WHERE ma_phong = ?";
            try (PreparedStatement psCheck = con.prepareStatement(checkPhongSql)) {
                psCheck.setString(1, taiSan.getMaPhong().trim());
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        log("-> Thất bại: Mã phòng [" + taiSan.getMaPhong() + "] không tồn tại trong hệ thống!");
                        return false;
                    }
                }
            }

            // Thực hiện sửa tài sản
            String sql = "UPDATE tai_san SET ten_tai_san = ?, loai = ?, ma_phong = ?, gia_tri = ? WHERE ma_tai_san = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, taiSan.getTenTaiSan().trim());
                ps.setString(2, taiSan.getLoai().trim());
                ps.setString(3, taiSan.getMaPhong().trim());
                ps.setBigDecimal(4, taiSan.getGiaTri());
                ps.setString(5, taiSan.getMaTaiSan().trim());

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    log("-> Sửa tài sản thành công: Mã tài sản = " + taiSan.getMaTaiSan()
                            + ", Tên mới = " + taiSan.getTenTaiSan());
                    return true;
                } else {
                    log("-> Thất bại: Không tìm thấy tài sản với mã = " + taiSan.getMaTaiSan());
                    return false;
                }
            }
        } catch (SQLException e) {
            log("-> Lỗi SQL khi sửa tài sản: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Phong getPhongByMa(String maPhong) throws RemoteException {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return null;
        }
        String sql = "SELECT ma_phong, ten_phong, mo_ta FROM phong WHERE ma_phong = ?";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, maPhong.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Phong(
                                rs.getString("ma_phong"),
                                rs.getString("ten_phong"),
                                rs.getString("mo_ta")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            log("Lỗi lấy thông tin phòng: " + e.getMessage());
        }
        return null;
    }

    @Override
    public TaiSan getTaiSanByMa(String maTaiSan) throws RemoteException {
        if (maTaiSan == null || maTaiSan.trim().isEmpty()) {
            return null;
        }
        String sql = "SELECT ma_tai_san, ten_tai_san, loai, ma_phong, gia_tri FROM tai_san WHERE ma_tai_san = ?";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, maTaiSan.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new TaiSan(
                                rs.getString("ma_tai_san"),
                                rs.getString("ten_tai_san"),
                                rs.getString("loai"),
                                rs.getString("ma_phong"),
                                rs.getBigDecimal("gia_tri")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            log("Lỗi lấy thông tin tài sản: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Phong> layTatCaPhong() throws RemoteException {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT ma_phong, ten_phong, mo_ta FROM phong ORDER BY ma_phong ASC";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Phong(
                            rs.getString("ma_phong"),
                            rs.getString("ten_phong"),
                            rs.getString("mo_ta")
                    ));
                }
            }
        } catch (SQLException e) {
            log("Lỗi lấy danh sách phòng: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<TaiSan> layTatCaTaiSan() throws RemoteException {
        List<TaiSan> list = new ArrayList<>();
        String sql = "SELECT ma_tai_san, ten_tai_san, loai, ma_phong, gia_tri FROM tai_san ORDER BY ma_tai_san ASC";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new TaiSan(
                            rs.getString("ma_tai_san"),
                            rs.getString("ten_tai_san"),
                            rs.getString("loai"),
                            rs.getString("ma_phong"),
                            rs.getBigDecimal("gia_tri")
                    ));
                }
            }
        } catch (SQLException e) {
            log("Lỗi lấy danh sách tài sản: " + e.getMessage());
        }
        return list;
    }

    // ==========================================
    // 2. CÁC PHƯƠNG THỨC BỔ SUNG PHÍA SERVER
    // ==========================================

    @Override
    public boolean themPhong(Phong phong) throws RemoteException {
        if (phong == null || phong.getMaPhong() == null || phong.getTenPhong() == null) {
            return false;
        }
        String sql = "INSERT INTO phong (ma_phong, ten_phong, mo_ta) VALUES (?, ?, ?)";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, phong.getMaPhong().trim());
                ps.setString(2, phong.getTenPhong().trim());
                ps.setString(3, phong.getMoTa() != null ? phong.getMoTa().trim() : "");
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            log("Lỗi thêm phòng: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean xoaPhong(String maPhong) throws RemoteException {
        if (maPhong == null || maPhong.trim().isEmpty()) {
            return false;
        }
        String sql = "DELETE FROM phong WHERE ma_phong = ?";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, maPhong.trim());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            log("Lỗi xóa phòng: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Phong> timKiemPhong(String tuKhoa) throws RemoteException {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT ma_phong, ten_phong, mo_ta FROM phong "
                + "WHERE ma_phong LIKE ? OR ten_phong LIKE ? OR mo_ta LIKE ?";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                String pattern = "%" + (tuKhoa != null ? tuKhoa.trim() : "") + "%";
                ps.setString(1, pattern);
                ps.setString(2, pattern);
                ps.setString(3, pattern);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new Phong(
                                rs.getString("ma_phong"),
                                rs.getString("ten_phong"),
                                rs.getString("mo_ta")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            log("Lỗi tìm kiếm phòng: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean themTaiSan(TaiSan taiSan) throws RemoteException {
        if (taiSan == null || taiSan.getMaTaiSan() == null || taiSan.getTenTaiSan() == null) {
            return false;
        }
        String sql = "INSERT INTO tai_san (ma_tai_san, ten_tai_san, loai, ma_phong, gia_tri) VALUES (?, ?, ?, ?, ?)";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, taiSan.getMaTaiSan().trim());
                ps.setString(2, taiSan.getTenTaiSan().trim());
                ps.setString(3, taiSan.getLoai().trim());
                ps.setString(4, taiSan.getMaPhong().trim());
                ps.setBigDecimal(5, taiSan.getGiaTri());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            log("Lỗi thêm tài sản: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean xoaTaiSan(String maTaiSan) throws RemoteException {
        if (maTaiSan == null || maTaiSan.trim().isEmpty()) {
            return false;
        }
        String sql = "DELETE FROM tai_san WHERE ma_tai_san = ?";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, maTaiSan.trim());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            log("Lỗi xóa tài sản: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<TaiSan> timKiemTaiSan(String tuKhoa) throws RemoteException {
        List<TaiSan> list = new ArrayList<>();
        String sql = "SELECT ma_tai_san, ten_tai_san, loai, ma_phong, gia_tri FROM tai_san "
                + "WHERE ma_tai_san LIKE ? OR ten_tai_san LIKE ? OR loai LIKE ?";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                String pattern = "%" + (tuKhoa != null ? tuKhoa.trim() : "") + "%";
                ps.setString(1, pattern);
                ps.setString(2, pattern);
                ps.setString(3, pattern);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new TaiSan(
                                rs.getString("ma_tai_san"),
                                rs.getString("ten_tai_san"),
                                rs.getString("loai"),
                                rs.getString("ma_phong"),
                                rs.getBigDecimal("gia_tri")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            log("Lỗi tìm kiếm tài sản: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<TaiSan> timKiemTaiSanTheoPhong(String maPhong) throws RemoteException {
        List<TaiSan> list = new ArrayList<>();
        String sql = "SELECT ma_tai_san, ten_tai_san, loai, ma_phong, gia_tri FROM tai_san WHERE ma_phong = ?";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, maPhong != null ? maPhong.trim() : "");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new TaiSan(
                                rs.getString("ma_tai_san"),
                                rs.getString("ten_tai_san"),
                                rs.getString("loai"),
                                rs.getString("ma_phong"),
                                rs.getBigDecimal("gia_tri")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            log("Lỗi tìm kiếm tài sản theo phòng: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<TaiSan> timKiemTaiSanTheoGiaTri(BigDecimal minGia, BigDecimal maxGia) throws RemoteException {
        List<TaiSan> list = new ArrayList<>();
        String sql = "SELECT ma_tai_san, ten_tai_san, loai, ma_phong, gia_tri FROM tai_san "
                + "WHERE gia_tri >= ? AND gia_tri <= ? ORDER BY gia_tri ASC";
        try {
            checkConnection();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setBigDecimal(1, minGia != null ? minGia : BigDecimal.ZERO);
                ps.setBigDecimal(2, maxGia != null ? maxGia : new BigDecimal("999999999999"));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new TaiSan(
                                rs.getString("ma_tai_san"),
                                rs.getString("ten_tai_san"),
                                rs.getString("loai"),
                                rs.getString("ma_phong"),
                                rs.getBigDecimal("gia_tri")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            log("Lỗi tìm kiếm tài sản theo giá trị: " + e.getMessage());
        }
        return list;
    }
}
