package com.example.appmobile.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmobile.R;
import com.example.appmobile.adapter.ChiTietDonAdapter;
import com.example.appmobile.adapter.ThanhToanAdapter;
import com.example.appmobile.model.ChiTietDon;
import com.example.appmobile.model.DienThoai;
import com.example.appmobile.model.GioHang;
import com.example.appmobile.model.ketnoicsdl;
import com.example.appmobile.model.ktketnoi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietDonActivity extends AppCompatActivity {

    DrawerLayout drawerLayoutChiTietDon;
    Toolbar toolbarChiTietDon;
    TextView textViewTenChiTietDon;
    TextView textViewSodtChiTietDon;
    TextView textViewDiaChiChiTietDon;
    ListView listViewChiTietDon;
    TextView textViewTienChiTietDon;

    ArrayList<ChiTietDon> arrayListChiTietDon;
    ChiTietDonAdapter chiTietDonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don);

        drawerLayoutChiTietDon =  findViewById(R.id.drawerLayoutChiTietDon);
        toolbarChiTietDon =  findViewById(R.id.toolbarThanhToan);
        textViewTenChiTietDon =  findViewById(R.id.textViewThongTinKhachHang);
        textViewSodtChiTietDon =  findViewById(R.id.textViewSodtKhachHang);
        textViewDiaChiChiTietDon =  findViewById(R.id.textViewDiaChiKhachHang);
        listViewChiTietDon =  findViewById(R.id.listviewSanPhamThanhToan);
        textViewTienChiTietDon =  findViewById(R.id.textViewTienThanhToan);

        arrayListChiTietDon = new ArrayList<>();
        chiTietDonAdapter = new ChiTietDonAdapter(getApplicationContext(),arrayListChiTietDon);
        listViewChiTietDon.setAdapter(chiTietDonAdapter);

        if(ktketnoi.haveNetworkConnection(getApplicationContext())){

            getDiaChiNhan();
            getSanPham();
            getTong();
            quayLaiManHinhGioHang();

        }else {
            ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
            finish();
        }
    }
    private void quayLaiManHinhGioHang(){ //quay lại màn hình trước đó
        setSupportActionBar(toolbarChiTietDon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietDon.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbarChiTietDon.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDiaChiNhan(){
        textViewTenChiTietDon.setText(getIntent().getStringExtra("ten"));
        textViewSodtChiTietDon.setText(getIntent().getStringExtra("sodt"));
        textViewDiaChiChiTietDon.setText(getIntent().getStringExtra("diachi"));
    }

    private void getTong(){
        Integer sum = getIntent().getIntExtra("tong",-1);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        textViewTienChiTietDon.setText(decimalFormat.format(sum)+" đ");
    }

    private void getSanPham(){ //lấy dữ liệu các sản phẩm theo mã loại sản phẩm để đổ ra màn hình

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ketnoicsdl.getChiTietDon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            arrayListChiTietDon.add(new ChiTietDon(jsonObject.getInt("id"),
                                    jsonObject.getString("ten"),
                                    jsonObject.getInt("gianiemyet"),
                                    jsonObject.getInt("giaban"),
                                    jsonObject.getString("hinhanh"),
                                    jsonObject.getInt("soluongsp")
                            ));
                            chiTietDonAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                int idldt = getIntent().getIntExtra("iddon",-1);

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("iddon", String.valueOf(idldt));
                return map;
            }
        };

        requestQueue.add(stringRequest);

    }

}