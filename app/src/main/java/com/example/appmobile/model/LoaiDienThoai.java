package com.example.appmobile.model;

public class LoaiDienThoai {
    private int id;
    private String tenLoaiDienThoai, hinhAnh;

    public LoaiDienThoai(int id, String tenLoaiDienThoai, String hinhAnh) {
        this.id = id;
        this.tenLoaiDienThoai = tenLoaiDienThoai;
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiDienThoai() {
        return tenLoaiDienThoai;
    }

    public void setTenLoaiDienThoai(String tenLoaiDienThoai) {
        this.tenLoaiDienThoai = tenLoaiDienThoai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
