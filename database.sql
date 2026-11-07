-- Tạo cơ sở dữ liệu cho đề tài Quản lý tài sản trong phòng
CREATE DATABASE IF NOT EXISTS quanlytaisan CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE quanlytaisan;

-- Xóa bảng cũ nếu tồn tại
DROP TABLE IF EXISTS tai_san;
DROP TABLE IF EXISTS phong;

-- Bảng Phòng (ma_phong, ten_phong, mo_ta)
CREATE TABLE phong (
    ma_phong VARCHAR(50) PRIMARY KEY,
    ten_phong VARCHAR(100) NOT NULL,
    mo_ta VARCHAR(255)
);

-- Bảng Tài sản (ma_tai_san, ten_tai_san, loai, ma_phong, gia_tri)
CREATE TABLE tai_san (
    ma_tai_san VARCHAR(50) PRIMARY KEY,
    ten_tai_san VARCHAR(100) NOT NULL,
    loai VARCHAR(50) NOT NULL,
    ma_phong VARCHAR(50) NOT NULL,
    gia_tri DECIMAL(15, 2) NOT NULL,
    CONSTRAINT fk_taisan_phong FOREIGN KEY (ma_phong) REFERENCES phong(ma_phong) ON UPDATE CASCADE ON DELETE RESTRICT
);

-- Dữ liệu mẫu bảng Phòng
INSERT INTO phong (ma_phong, ten_phong, mo_ta) VALUES
('P101', 'Phòng Thực Hành Mạng 1', 'Tầng 1 Nhà A2 - Phòng máy vi tính'),
('P102', 'Phòng Server Trung Tâm', 'Tầng 1 Nhà A2 - Đặt các máy chủ hệ thống'),
('P201', 'Phòng Hội Thảo Đa Năng', 'Tầng 2 Nhà A1 - Phòng họp và hội thảo'),
('P301', 'Phòng Làm Việc Khoa CNTT', 'Tầng 3 Nhà A3 - Phòng giảng viên');

-- Dữ liệu mẫu bảng Tài sản
INSERT INTO tai_san (ma_tai_san, ten_tai_san, loai, ma_phong, gia_tri) VALUES
('TS01', 'Máy tính để bàn Dell OptiPlex', 'Máy tính', 'P101', 15500000.00),
('TS02', 'Switch Cisco Catalyst 2960 24 Port', 'Thiết bị mạng', 'P101', 9800000.00),
('TS03', 'Máy chủ Dell PowerEdge R740', 'Máy chủ', 'P102', 65000000.00),
('TS04', 'Bộ lưu điện UPS APC Smart 3000VA', 'Bộ lưu điện', 'P102', 28000000.00),
('TS05', 'Máy chiếu Sony Laser 4K', 'Thiết bị trình chiếu', 'P201', 24500000.00),
('TS06', 'Hệ thống âm thanh hội trường Yamaha', 'Thiết bị âm thanh', 'P201', 18000000.00);

