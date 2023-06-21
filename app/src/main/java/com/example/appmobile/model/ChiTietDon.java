package com.example.appmobile.model;

public class ChiTietDon {
    private int id;
    private String ten;
    private Integer giaNiemYet;
    private Integer giaBan;
    private String hinhAnh;
    private int sosp;

    public ChiTietDon() {
    }

    public ChiTietDon(int id, String ten, Integer giaNiemYet, Integer giaBan, String hinhAnh, int sosp) {
        this.id = id;
        this.ten = ten;
        this.giaNiemYet = giaNiemYet;
        this.giaBan = giaBan;
        this.hinhAnh = hinhAnh;
        this.sosp = sosp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getGiaNiemYet() {
        return giaNiemYet;
    }

    public void setGiaNiemYet(Integer giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
    }

    public Integer getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Integer giaBan) {
        this.giaBan = giaBan;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSosp() {
        return sosp;
    }

    public void setSosp(int sosp) {
        this.sosp = sosp;
    }
}
