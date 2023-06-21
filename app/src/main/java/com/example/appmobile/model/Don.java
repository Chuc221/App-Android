package com.example.appmobile.model;

public class Don {
    private int id;
    private int idkh;
    private String ten;
    private String sdt;
    private String diachi;
    private int sosp;
    private Integer tien;

    public Don() {
    }

    public Don(int id, int idkh, String ten, String sdt, String diachi, int sosp, Integer tien) {
        this.id = id;
        this.idkh = idkh;
        this.ten = ten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.sosp = sosp;
        this.tien = tien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdkh() {
        return idkh;
    }

    public void setIdkh(int idkh) {
        this.idkh = idkh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getSosp() {
        return sosp;
    }

    public void setSosp(int sosp) {
        this.sosp = sosp;
    }

    public Integer getTien() {
        return tien;
    }

    public void setTien(Integer tien) {
        this.tien = tien;
    }
}
