package com.example.appmobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmobile.R;
import com.example.appmobile.adapter.LoaiDienThoaiAdapter;
import com.example.appmobile.model.LoaiDienThoai;
import com.example.appmobile.model.ketnoicsdl;
import com.example.appmobile.model.ktketnoi;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThongTinActivity extends AppCompatActivity {

    DrawerLayout drawerLayoutThongTin;
    Toolbar toolbarThongTin;
    TextView textViewTen;
    TextView textViewSoDT;
    TextView textViewUser;
    TextView textViewDiaChi;
    Button buttonDon;
    Button buttonDangXuat;
    NavigationView navigationViewThongTin;
    ListView listViewLoaiSP;

    ArrayList<LoaiDienThoai> arrayLoaiDienThoai;
    LoaiDienThoaiAdapter loaiDienThoaiAdapter;
    int sumLoaiDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        drawerLayoutThongTin =  findViewById(R.id.drawerLayoutThongTin);
        toolbarThongTin =  findViewById(R.id.toolbarThongTin);
        textViewTen =  findViewById(R.id.textViewHoTen);
        textViewUser =  findViewById(R.id.textViewUsername);
        textViewSoDT =  findViewById(R.id.textViewSoDT);
        textViewDiaChi =  findViewById(R.id.textViewDiaChi);
        buttonDon =  findViewById(R.id.buttonDonMua);
        buttonDangXuat =  findViewById(R.id.buttonDangXuat);
        navigationViewThongTin =  findViewById(R.id.navigationViewThongTin);
        listViewLoaiSP =  findViewById(R.id.listviewLoaiSanPhamThongTin);

        arrayLoaiDienThoai = new ArrayList<>();
        loaiDienThoaiAdapter = new LoaiDienThoaiAdapter(getApplicationContext(), arrayLoaiDienThoai);
        listViewLoaiSP.setAdapter(loaiDienThoaiAdapter);

        if(ktketnoi.haveNetworkConnection(getApplicationContext())){

            getThongTin();
            menu();
            getLoaiDienThoai();
            chonMenu();
            getDon();
            dangXuat();

        }else {
            ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_rieng,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuGioHang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                intent.putExtra("idGio",0);
                intent.putExtra("tenGio","0");
                intent.putExtra("giaNiemYetGio",0);
                intent.putExtra("giaBanGio",0);
                intent.putExtra("hinhAnhGio","0");
                intent.putExtra("motaGio","0");
                intent.putExtra("daBanGio",0);
                intent.putExtra("idLoaiSPGio",0);
                intent.putExtra("soluongGio","0");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getDon(){
        buttonDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DonActivity.class);
                startActivity(intent);
            }
        });
    }

    private void dangXuat(){
        buttonDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void chonMenu(){ //chọn loại điện thoại trong menu

        listViewLoaiSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i ==0){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutThongTin.closeDrawer(GravityCompat.START);
                }
                if(i>=1 && i<=sumLoaiDT){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),SanPhamActivity.class);
                        intent.putExtra("idLoaiDT",arrayLoaiDienThoai.get(i).getId());
                        intent.putExtra("logo",arrayLoaiDienThoai.get(i).getHinhAnh());
                        intent.putExtra("tenLoaiDT",arrayLoaiDienThoai.get(i).getTenLoaiDienThoai());
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutThongTin.closeDrawer(GravityCompat.START);
                }
                if(i==(sumLoaiDT+1)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),LienHeActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutThongTin.closeDrawer(GravityCompat.START);
                }
                if (i==(sumLoaiDT+2)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),ThongTinActivity.class);
                        startActivity(intent);

                    }else {
                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
                    }
                    drawerLayoutThongTin.closeDrawer(GravityCompat.START);
                }

            }
        });

    }

    private void getLoaiDienThoai(){  //lấy dữ liệu các loại điện thoại để cho vào thanh menu
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ketnoicsdl.getLoaiDienThoai, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                sumLoaiDT=response.length();

                if(response!= null){

                    int id=0;
                    String ten="";
                    String hinhAnh="";

                    arrayLoaiDienThoai.add(0,new LoaiDienThoai(0,"Trang chủ","https://e7.pngegg.com/pngimages/679/69/png-clipart-home-assistant-computer-icons-home-automation-kits-amazon-echo-home-blue-logo-thumbnail.png"));

                    //Thêm các loại điện thoại vào mảng
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            ten = jsonObject.getString("tenLoaiDienThoai");
                            hinhAnh = jsonObject.getString("hinhAnh");
                            arrayLoaiDienThoai.add(new LoaiDienThoai(id,ten,hinhAnh));
                            loaiDienThoaiAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    arrayLoaiDienThoai.add(sumLoaiDT+1,new LoaiDienThoai(100,"Liên Hệ","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR7mbN_a9n1sWseU11XkJJ95CqcOnbHOypZEA&usqp=CAU"));
                    arrayLoaiDienThoai.add(sumLoaiDT+2, new LoaiDienThoai(101,"Thông tin","https://cdn.icon-icons.com/icons2/212/PNG/256/User-blue256_25016.png"));

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ktketnoi.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    private void menu(){  //hiển thị thanh menu các loại điện thoại
        setSupportActionBar(toolbarThongTin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThongTin.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarThongTin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutThongTin.openDrawer(GravityCompat.START);
            }
        });
    }

    private void getThongTin(){
        textViewTen.setText(DangNhapActivity.khachHang.getHoten());
        textViewUser.setText(DangNhapActivity.khachHang.getUsername());
        textViewSoDT.setText(DangNhapActivity.khachHang.getSodt());
        textViewDiaChi.setText(DangNhapActivity.khachHang.getDiachi());
    }
}