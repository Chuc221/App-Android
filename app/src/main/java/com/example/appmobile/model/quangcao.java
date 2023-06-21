package com.example.appmobile.model;

public class quangcao {
    private int id;
    private String hinhAnhQC;

    public quangcao(int id, String hinhAnhQC) {
        this.id = id;
        this.hinhAnhQC = hinhAnhQC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHinhAnhQC() {
        return hinhAnhQC;
    }

    public void setHinhAnhQC(String hinhAnhQC) {
        this.hinhAnhQC = hinhAnhQC;
    }
}
