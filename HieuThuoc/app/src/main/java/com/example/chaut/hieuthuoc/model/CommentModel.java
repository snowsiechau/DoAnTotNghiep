package com.example.chaut.hieuthuoc.model;

/**
 * Created by chaut on 4/29/2018.
 */

public class CommentModel {
    private String tieude;
    private String noidung;
    private double danhgia;
    private String date;

    public CommentModel() {
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public double getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(double danhgia) {
        this.danhgia = danhgia;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
