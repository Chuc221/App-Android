package com.example.appmobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.appmobile.model.KhachHang;
import com.example.appmobile.model.ketnoicsdl;
import com.example.appmobile.model.ktketnoi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangNhapActivity extends AppCompatActivity {

    EditText editTextUser;
    EditText editTextPassword;
    Button buttonDangNhap;
    TextView textViewDangKy;
    public static KhachHang khachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        editTextUser =  findViewById(R.id.editTextUser);
        editTextPassword =  findViewById(R.id.editTextPassword);
        buttonDangNhap =  findViewById(R.id.buttonDangNhap);
        textViewDangKy =  findViewById(R.id.textViewDangKy);

        if(ktketnoi.haveNetworkConnection(getApplicationContext())){

            buttonDangNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = editTextUser.getText().toString().trim();
                    String pass = editTextPassword.getText().toString().trim();
                    if (user.length()>0 && pass.length()>0){
                        dangNhap();
                    }else {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            textViewDangKy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogDangKy();
                }
            });
        }
        else {
            ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
            finish();
        }
    }
    private void dangNhap(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ketnoicsdl.dangNhap, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response!=null){
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        khachHang = new KhachHang(jsonObject.getInt("id"),
                                jsonObject.getString("username"),
                                jsonObject.getString("password"),
                                jsonObject.getString("hoten"),
                                jsonObject.getString("diachi"),
                                jsonObject.getString("sodt"));
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Username hoặc password sai!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Username hoặc password sai!", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Username hoặc password sai!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("username", editTextUser.getText().toString().trim());
                map.put("password", editTextPassword.getText().toString().trim());
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void dialogDangKy(){
        Dialog dialog=new Dialog(DangNhapActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dang_ky);
        dialog.setCanceledOnTouchOutside(true);

        EditText editTextUsername = (EditText) dialog.findViewById(R.id.editTextUser);
        EditText editTextPass = (EditText) dialog.findViewById(R.id.editTextPassword);
        EditText editTextPass1 = (EditText) dialog.findViewById(R.id.editTextPassword1);
        EditText editTextTen =(EditText) dialog.findViewById(R.id.editTextTen);
        EditText editTextSoDT =(EditText) dialog.findViewById(R.id.editTextSoDT);
        EditText editTextDiaChi = (EditText) dialog.findViewById(R.id.editTextDiaChi);
        Button btnDongY =(Button)  dialog.findViewById(R.id.buttonDongY);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuy);

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextTen.getText().length()==0||editTextUsername.getText().length()==0||editTextPass.getText().length()==0||editTextPass1.getText().length()==0||editTextDiaChi.getText().length()==0||editTextSoDT.getText().length()==0){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    if(editTextPass.getText().toString().equals(editTextPass1.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                        dangKy(editTextUsername.getText().toString().trim(),
                                editTextPass.getText().toString().trim(),
                                editTextTen.getText().toString().trim(),
                                editTextDiaChi.getText().toString().trim(),
                                editTextSoDT.getText().toString().trim());

                        editTextUser.setText(editTextUsername.getText());
                        editTextPassword.setText(editTextPass.getText());
                        dialog.cancel();
                    }else {
                        Toast.makeText(getApplicationContext(), "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    }
                }
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

    private void dangKy(String user, String pass, String hoten, String diachi, String soDT){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ketnoicsdl.dangKy, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("username", user);
                map.put("password", pass);
                map.put("hoten", hoten);
                map.put("diachi", diachi);
                map.put("sodt", soDT);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
}