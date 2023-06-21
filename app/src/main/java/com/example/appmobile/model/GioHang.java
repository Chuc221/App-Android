package com.example.appmobile.model;

public class GioHang {
    private   int id;
    private String ten;
    private Integer giaNiemYet;
    private Integer giaBan;
    private String hinhAnh;
    private String moTa;
    private Integer daBan;
    private int idLoaiDienThoai;
    private  Integer soLuong;
    private boolean check;

    public GioHang(int id, String ten, Integer giaNiemYet, Integer giaBan, String hinhAnh, String moTa, Integer daBan, int idLoaiDienThoai, Integer soLuong, boolean check) {
        this.id = id;
        this.ten = ten;
        this.giaNiemYet = giaNiemYet;
        this.giaBan = giaBan;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.daBan = daBan;
        this.idLoaiDienThoai = idLoaiDienThoai;
        this.soLuong = soLuong;
        this.check = check;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getDaBan() {
        return daBan;
    }

    public void setDaBan(Integer daBan) {
        this.daBan = daBan;
    }

    public int getIdLoaiDienThoai() {
        return idLoaiDienThoai;
    }

    public void setIdLoaiDienThoai(int idLoaiDienThoai) {
        this.idLoaiDienThoai = idLoaiDienThoai;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
