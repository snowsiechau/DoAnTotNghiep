package com.example.chaut.hieuthuoc.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaut on 4/21/2018.
 */

public class Model implements Serializable{
    private String ten;
    private String bacsi;
    private String chuyenkhoa;
    private String diachi;
    private double danhgia;
    private double lat;
    private double lon;
    private String sdt;

    public Model() {

    }

    public Model(String bacsi, String chuyenkhoa, double danhgia, String diachi, double lat, double lon, String ten, String sdt) {
        this.ten = ten;
        this.bacsi = bacsi;
        this.chuyenkhoa = chuyenkhoa;
        this.diachi = diachi;
        this.danhgia = danhgia;
        this.lat = lat;
        this.lon = lon;
        this.sdt = sdt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getBacsi() {
        return bacsi;
    }

    public void setBacsi(String bacsi) {
        this.bacsi = bacsi;
    }

    public String getChuyenkhoa() {
        return chuyenkhoa;
    }

    public void setChuyenkhoa(String chuyenkhoa) {
        this.chuyenkhoa = chuyenkhoa;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(double danhgia) {
        this.danhgia = danhgia;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
