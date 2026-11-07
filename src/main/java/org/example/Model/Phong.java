package org.example.Model;

import java.io.Serializable;

public class Phong implements Serializable {
    private static final long serialVersionUID = 1L;

    public String maPhong;
    public String tenPhong;
    public String moTa;

    public Phong() {
    }

    public Phong(String maPhong, String tenPhong, String moTa) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.moTa = moTa;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return maPhong + " - " + tenPhong;
    }
}
