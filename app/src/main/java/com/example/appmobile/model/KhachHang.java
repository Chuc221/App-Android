package com.example.appmobile.model;

public class KhachHang {
    private int idkh;
    private String username;
    private String password;
    private String hoten;
    private String diachi;
    private String sodt;

    public KhachHang(int idkh, String username, String password, String hoten, String diachi, String sodt) {
        this.idkh = idkh;
        this.username = username;
        this.password = password;
        this.hoten = hoten;
        this.diachi = diachi;
        this.sodt = sodt;
    }

    public int getIdkh() {
        return idkh;
    }

    public void setIdkh(int idkh) {
        this.idkh = idkh;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }
}
