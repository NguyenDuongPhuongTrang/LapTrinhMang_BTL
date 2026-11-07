package org.example.Control;

import org.example.Model.Phong;
import org.example.Model.TaiSan;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIInterface extends Remote {

    // ==========================================
    // CÁC NGHIỆP VỤ BTL YÊU CẦU TRỌNG TÂM
    // ==========================================
    
    /**
     * Sửa thông tin Phòng
     * @param phong Đối tượng phòng với thông tin mới cần cập nhật
     * @return true nếu sửa thành công, false nếu thất bại (không tìm thấy mã phòng hoặc lỗi)
     */
    boolean suaPhong(Phong phong) throws RemoteException;

    /**
     * Sửa thông tin Tài sản
     * @param taiSan Đối tượng tài sản với thông tin mới cần cập nhật
     * @return true nếu sửa thành công, false nếu thất bại (không tìm thấy mã tài sản hoặc lỗi)
     */
    boolean suaTaiSan(TaiSan taiSan) throws RemoteException;

    /**
     * Lấy thông tin phòng theo mã phòng
     */
    Phong getPhongByMa(String maPhong) throws RemoteException;

    /**
     * Lấy thông tin tài sản theo mã tài sản
     */
    TaiSan getTaiSanByMa(String maTaiSan) throws RemoteException;

    /**
     * Lấy toàn bộ danh sách phòng
     */
    List<Phong> layTatCaPhong() throws RemoteException;

    /**
     * Lấy toàn bộ danh sách tài sản
     */
    List<TaiSan> layTatCaTaiSan() throws RemoteException;


    // ==========================================
    // CÁC NGHIỆP VỤ SERVER CHÍNH
    // ==========================================

    // Nghiệp vụ Phòng: Thêm / Xóa / Tìm kiếm
    boolean themPhong(Phong phong) throws RemoteException;
    boolean xoaPhong(String maPhong) throws RemoteException;
    List<Phong> timKiemPhong(String tuKhoa) throws RemoteException;

    // Nghiệp vụ Tài sản: Thêm / Xóa / Tìm kiếm theo phòng / giá trị
    boolean themTaiSan(TaiSan taiSan) throws RemoteException;
    boolean xoaTaiSan(String maTaiSan) throws RemoteException;
    List<TaiSan> timKiemTaiSan(String tuKhoa) throws RemoteException;
    List<TaiSan> timKiemTaiSanTheoPhong(String maPhong) throws RemoteException;
    List<TaiSan> timKiemTaiSanTheoGiaTri(BigDecimal minGia, BigDecimal maxGia) throws RemoteException;
}

