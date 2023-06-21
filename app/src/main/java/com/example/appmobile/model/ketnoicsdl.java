package com.example.appmobile.model;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmobile.adapter.LoaiDienThoaiAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ketnoicsdl {
    public  static String localhost = "192.168.187.201:8080";
    public static String dangNhap = "http://"+localhost+"/mobile/dangnhap.php";
    public static String dangKy = "http://"+localhost+"/mobile/dangky.php";
    public static  String hinhAnhQC= "http://"+localhost+"/mobile/quangcao.php";
    public static String getLoaiDienThoai = "http://"+localhost+"/mobile/loaidienthoai.php";
    public static String getSanPhamMoi = "http://"+localhost+"/mobile/cacmaumoi.php";
    public static String getSanPham = "http://"+localhost+"/mobile/sanpham.php";
    public static String updateDaBan = "http://"+localhost+"/mobile/updatedaban.php";
    public static String insertDon = "http://"+localhost+"/mobile/insertdon.php";
    public static String getDon = "http://"+localhost+"/mobile/getdon.php";
    public static String insertChiTiet = "http://"+localhost+"/mobile/chitietdon.php";
    public static String getChiTietDon = "http://"+localhost+"/mobile/getchitietdon.php";
}
