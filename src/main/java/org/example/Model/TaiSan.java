package org.example.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaiSan implements Serializable {
    private static final long serialVersionUID = 1L;

    public String maTaiSan;
    public String tenTaiSan;
    public String loai;
    public String maPhong;
    public BigDecimal giaTri;

    public TaiSan() {
    }

    public TaiSan(String maTaiSan, String tenTaiSan, String loai,
                  String maPhong, BigDecimal giaTri) {
        this.maTaiSan = maTaiSan;
        this.tenTaiSan = tenTaiSan;
        this.loai = loai;
        this.maPhong = maPhong;
        this.giaTri = giaTri;
    }

    public String getMaTaiSan() {
        return maTaiSan;
    }

    public void setMaTaiSan(String maTaiSan) {
        this.maTaiSan = maTaiSan;
    }

    public String getTenTaiSan() {
        return tenTaiSan;
    }

    public void setTenTaiSan(String tenTaiSan) {
        this.tenTaiSan = tenTaiSan;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }

    @Override
    public String toString() {
        return maTaiSan + " - " + tenTaiSan + " (" + loai + ", " + maPhong + ", " + giaTri + ")";
    }
}
