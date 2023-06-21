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
import com.example.appmobile.adapter.ThanhToanAdapter;
import com.example.appmobile.model.GioHang;
import com.example.appmobile.model.ketnoicsdl;
import com.example.appmobile.model.ktketnoi;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {

    DrawerLayout drawerLayoutThanhToan;
    Toolbar toolbarThanhToan;
    ImageView imageViewEditDiaChiThanhToan;
    TextView textViewTenThanhToan;
    TextView textViewSodtThanhToan;
    TextView textViewDiaChiThanhToan;
    ListView listViewThanhToan;
    TextView textViewTienThanhToan;
    Button buttonThanhToan;
    ArrayList<GioHang> arrayListThanhToan;
    ThanhToanAdapter thanhToanAdapter;
    int iddon=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        drawerLayoutThanhToan =  findViewById(R.id.drawerLayoutThanhToan);
        toolbarThanhToan =  findViewById(R.id.toolbarThanhToan);
        imageViewEditDiaChiThanhToan =  findViewById(R.id.imageviewEditDiaChi);
        textViewTenThanhToan=  findViewById(R.id.textViewThongTinKhachHang);
        textViewSodtThanhToan =  findViewById(R.id.textViewSodtKhachHang);
        textViewDiaChiThanhToan =  findViewById(R.id.textViewDiaChiKhachHang);
        listViewThanhToan =  findViewById(R.id.listviewSanPhamThanhToan);
        textViewTienThanhToan =  findViewById(R.id.textViewTienThanhToan);
        buttonThanhToan =  findViewById(R.id.buttonThanhToan);

        arrayListThanhToan = new ArrayList<>();
        thanhToanAdapter = new ThanhToanAdapter(getApplicationContext(),arrayListThanhToan);
        listViewThanhToan.setAdapter(thanhToanAdapter);

        if(ktketnoi.haveNetworkConnection(getApplicationContext())){

            getDiaChiNhan();
            editDiaChiNhan();
            getSanPham();
            getTong();
            quayLaiManHinhGioHang();
            datHang();

        }else {
            ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
            finish();
        }
    }
    private void quayLaiManHinhGioHang(){ //quay lại màn hình trước đó
        setSupportActionBar(toolbarThanhToan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThanhToan.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbarThanhToan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDiaChiNhan(){
        textViewTenThanhToan.setText(DangNhapActivity.khachHang.getHoten());
        textViewSodtThanhToan.setText(DangNhapActivity.khachHang.getSodt());
        textViewDiaChiThanhToan.setText(DangNhapActivity.khachHang.getDiachi());
    }

    private void editDiaChiNhan(){
        imageViewEditDiaChiThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(ThanhToanActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_dia_chi_nhan);
                dialog.setCanceledOnTouchOutside(true);

                EditText editTextTen =(EditText) dialog.findViewById(R.id.editTextTen);
                EditText editTextSoDT =(EditText) dialog.findViewById(R.id.editTextSoDT);
                EditText editTextDiaChi = (EditText) dialog.findViewById(R.id.editTextDiaChi);
                Button btnDongY =(Button)  dialog.findViewById(R.id.buttonDongY);
                Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuy);

                editTextTen.setText(textViewTenThanhToan.getText());
                editTextSoDT.setText(textViewSodtThanhToan.getText());
                editTextDiaChi.setText(textViewDiaChiThanhToan.getText());

                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String ten = editTextTen.getText().toString().trim();
                        String sodt = editTextSoDT.getText().toString().trim();
                        String diachi = editTextDiaChi.getText().toString().trim();

                        textViewTenThanhToan.setText(ten);
                        textViewSodtThanhToan.setText(sodt);
                        textViewDiaChiThanhToan.setText(diachi);

                        dialog.cancel();
                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
    }

    private void getTong(){
        Integer sum = 0;
        for (int i = 0; i < arrayListThanhToan.size(); i++) {
            sum=sum+(arrayListThanhToan.get(i).getGiaBan())*(arrayListThanhToan.get(i).getSoLuong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        textViewTienThanhToan.setText(decimalFormat.format(sum)+" đ");
    }

    private void getSanPham(){
        for (int i = 0; i < GioHangActivity.arrayListGioHang.size(); i++) {
            if (GioHangActivity.arrayListGioHang.get(i).isCheck()){
                arrayListThanhToan.add(GioHangActivity.arrayListGioHang.get(i));
            }
        }
        thanhToanAdapter.notifyDataSetChanged();
    }

    private void datHang(){
        buttonThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ThanhToanActivity.this);
                alertDialog.setIcon(R.drawable.logo_c_phone);
                alertDialog.setTitle("Thông báo!");
                alertDialog.setMessage("Vui lòng kiểm tra sản phẩm và thanh toán khi nhận hàng!"+'\n'+"Click Đồng ý để xác nhận đơn!");

                alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Integer sum = 0, soluong=0;
                        for (int j = 0; j < arrayListThanhToan.size(); j++) {
                            soluong += arrayListThanhToan.get(j).getSoLuong();
                            sum=sum+(arrayListThanhToan.get(j).getGiaBan())*(arrayListThanhToan.get(j).getSoLuong());
                            updateSP(arrayListThanhToan.get(j).getId(),
                                    (arrayListThanhToan.get(j).getDaBan()+arrayListThanhToan.get(j).getSoLuong()));
                        }

                        insertDon(soluong,sum);

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                });

                alertDialog.show();
            }
        });
    }

    private void updateSP(int id, int soluong){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ketnoicsdl.updateDaBan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")){

                }else {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idsp", id+"");
                map.put("daban", soluong+"");
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void insertDon(int soluong, int tong){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ketnoicsdl.insertDon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response!=null){
                    try {
                        Toast.makeText(getApplicationContext(), "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();

                        JSONObject jsonObject=new JSONObject(response);
                        iddon = jsonObject.getInt("id");

                        for (int i = 0; i < arrayListThanhToan.size() ; i++) {
                            insertchitiet(iddon,arrayListThanhToan.get(i).getId(),arrayListThanhToan.get(i).getSoLuong());
                            GioHangActivity.arrayListGioHang.remove(arrayListThanhToan.get(i));
                        }

                        GioHangActivity.gioHangAdapter.notifyDataSetChanged();
                        GioHangActivity.getTong();

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }else {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idkh", DangNhapActivity.khachHang.getIdkh()+"");
                map.put("hoten", textViewTenThanhToan.getText().toString().trim());
                map.put("sodt", textViewSodtThanhToan.getText().toString().trim());
                map.put("diachi", textViewDiaChiThanhToan.getText().toString().trim());
                map.put("soluongsp", soluong+"");
                map.put("tongthanhtoan", tong+"");
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void insertchitiet(int iddon, int idsp, int soluong){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ketnoicsdl.insertChiTiet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                }
                else {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("iddon", iddon+"");
                map.put("idsp", idsp+"");
                map.put("soluong", soluong+"");
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
}