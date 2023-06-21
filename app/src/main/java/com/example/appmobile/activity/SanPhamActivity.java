package com.example.appmobile.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmobile.R;
import com.example.appmobile.adapter.LoaiDienThoaiAdapter;
import com.example.appmobile.adapter.SanPhamAdapter;
import com.example.appmobile.model.DienThoai;
import com.example.appmobile.model.LoaiDienThoai;
import com.example.appmobile.model.ketnoicsdl;
import com.example.appmobile.model.ktketnoi;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SanPhamActivity extends AppCompatActivity {

    DrawerLayout drawerLayoutSanPham;
    Toolbar toolbarSanPham;
    ImageView imageViewSanPham;
    NavigationView navigationViewSanPham;
    ListView listViewSanPham, listViewLoaiSanPham;

    //Mảng menu
    ArrayList<LoaiDienThoai> arrayLoaiSanPham;
    LoaiDienThoaiAdapter loaiDienThoaiAdapter;

    //Mảng sản phẩm
    ArrayList<DienThoai> arrayListSanPham;
    SanPhamAdapter sanPhamAdapter;

    int sumLoaiSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        drawerLayoutSanPham =  findViewById(R.id.drawerLayoutSanPham);
        toolbarSanPham =  findViewById(R.id.toolbarSanPham);
        imageViewSanPham =  findViewById(R.id.imageViewSanPham);
        listViewSanPham =  findViewById(R.id.listViewSanPham);
        navigationViewSanPham =  findViewById(R.id.navigationViewSanPham);
        listViewLoaiSanPham =  findViewById(R.id.listviewLoaiSanPham);

        arrayLoaiSanPham = new ArrayList<>();
        loaiDienThoaiAdapter = new LoaiDienThoaiAdapter(getApplicationContext(), arrayLoaiSanPham);
        listViewLoaiSanPham.setAdapter(loaiDienThoaiAdapter);

        arrayListSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(SanPhamActivity.this,arrayListSanPham);
        listViewSanPham.setAdapter(sanPhamAdapter);

        if(ktketnoi.haveNetworkConnection(getApplicationContext())){
            getTenLoaiSP();
            menu();
            getLoaiDienThoai();
            chonMenu();
            getLogoLoaiSP();
            getSanPham();
            sanPhamChiTiet();
        }
        else {
            ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
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
                intent.putExtra("soluongGio",0);
                startActivity(intent);
                break;

            case R.id.menuTang:
                Collections.sort(arrayListSanPham, new Comparator<DienThoai>() {
                    @Override
                    public int compare(DienThoai t, DienThoai t1) {
                        if (t.getGiaBan()> t1.getGiaBan()){
                            return 1;
                        }
                        if (t.getGiaBan() == t1.getGiaBan()){
                            if (t.getDaBan()< t1.getDaBan()){
                                return 1;
                            }
                        }
                        return -1;
                    }
                });
                sanPhamAdapter.notifyDataSetChanged();
                break;

            case R.id.menuGiam:
                Collections.sort(arrayListSanPham, new Comparator<DienThoai>() {
                    @Override
                    public int compare(DienThoai t, DienThoai t1) {
                        if (t.getGiaBan()< t1.getGiaBan()){
                            return 1;
                        }
                        if (t.getGiaBan() == t1.getGiaBan()){
                            if (t.getDaBan()< t1.getDaBan()){
                                return 1;
                            }
                        }
                        return -1;
                    }
                });
                sanPhamAdapter.notifyDataSetChanged();
                break;

            case R.id.menuPhoBien:
                Collections.sort(arrayListSanPham, new Comparator<DienThoai>() {
                    @Override
                    public int compare(DienThoai t, DienThoai t1) {
                        if (t.getDaBan()< t1.getDaBan()){
                            return 1;
                        }
                        if (t.getDaBan() == t1.getDaBan()){
                            if (t.getGiaBan()< t1.getGiaBan()){
                                return 1;
                            }
                        }
                        return -1;
                    }
                });
                sanPhamAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void menu(){  //hiển thị thanh menu các loại điện thoại
        setSupportActionBar(toolbarSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSanPham.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutSanPham.openDrawer(GravityCompat.START);
            }
        });
    }
    private void chonMenu(){ //chọn loại điện thoại trong menu

        listViewLoaiSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i ==0){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(SanPhamActivity.this,MainActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutSanPham.closeDrawer(GravityCompat.START);
                }
                if(i>=1 && i<=sumLoaiSP){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(SanPhamActivity.this,SanPhamActivity.class);
                        intent.putExtra("idLoaiDT",arrayLoaiSanPham.get(i).getId());
                        intent.putExtra("logo",arrayLoaiSanPham.get(i).getHinhAnh());
                        intent.putExtra("tenLoaiDT",arrayLoaiSanPham.get(i).getTenLoaiDienThoai());
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutSanPham.closeDrawer(GravityCompat.START);
                }
                if(i==(sumLoaiSP+1)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),LienHeActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutSanPham.closeDrawer(GravityCompat.START);
                }
                if (i==(sumLoaiSP+2)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),ThongTinActivity.class);
                        startActivity(intent);

                    }else {
                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
                    }
                    drawerLayoutSanPham.closeDrawer(GravityCompat.START);
                }

            }
        });

    }
    private void getLogoLoaiSP(){
        String logo = getIntent().getStringExtra("logo");
        Picasso.with(getApplicationContext()).load(logo)
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror)
                .into(imageViewSanPham);
    }
    private void getTenLoaiSP(){
        String ten = getIntent().getStringExtra("tenLoaiDT");
        toolbarSanPham.setTitle(ten);
    }
    private void getLoaiDienThoai(){  //lấy dữ liệu các loại sản phẩm để cho vào thanh menu
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ketnoicsdl.getLoaiDienThoai, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                sumLoaiSP = response.length();

                if(response!= null){

                    int id=0;
                    String ten="";
                    String hinhAnh="";

                    arrayLoaiSanPham.add(0,new LoaiDienThoai(0,"Trang chủ","https://e7.pngegg.com/pngimages/679/69/png-clipart-home-assistant-computer-icons-home-automation-kits-amazon-echo-home-blue-logo-thumbnail.png"));

                    //Thêm các loại sản phẩm vào mảng
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            ten = jsonObject.getString("tenLoaiDienThoai");
                            hinhAnh = jsonObject.getString("hinhAnh");
                            arrayLoaiSanPham.add(new LoaiDienThoai(id,ten,hinhAnh));
                            loaiDienThoaiAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    arrayLoaiSanPham.add(sumLoaiSP+1,new LoaiDienThoai(100,"Liên Hệ","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR7mbN_a9n1sWseU11XkJJ95CqcOnbHOypZEA&usqp=CAU"));
                    arrayLoaiSanPham.add(sumLoaiSP+2, new LoaiDienThoai(101,"Thông tin","https://cdn.icon-icons.com/icons2/212/PNG/256/User-blue256_25016.png"));

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
    private void getSanPham(){ //lấy dữ liệu các sản phẩm theo mã loại sản phẩm để đổ ra màn hình

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ketnoicsdl.getSanPham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int idDT;
                String tenDT;
                Integer giaNiemYetDT;
                Integer giaBanDT;
                String hinhAnhDT;
                String moTaDT;
                Integer daBanDT;
                int idLoaiDienThoaiDT;

                if (response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idDT = jsonObject.getInt("idSP");
                            tenDT = jsonObject.getString("tenSP");
                            giaNiemYetDT = jsonObject.getInt("giaNiemYetSP");
                            giaBanDT = jsonObject.getInt("giaBanSP");
                            hinhAnhDT = jsonObject.getString("hinhAnhSP");
                            moTaDT = jsonObject.getString("moTaSP");
                            daBanDT = jsonObject.getInt("daBanSP");
                            idLoaiDienThoaiDT = jsonObject.getInt("idLoaiSP");

                            arrayListSanPham.add(new DienThoai(idDT,tenDT, giaNiemYetDT, giaBanDT, hinhAnhDT, moTaDT, daBanDT, idLoaiDienThoaiDT));
                            sanPhamAdapter.notifyDataSetChanged();

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

                int idldt = getIntent().getIntExtra("idLoaiDT",-1);

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idloaidienthoai", String.valueOf(idldt));

                return map;
            }
        };

        requestQueue.add(stringRequest);

    }
    private void sanPhamChiTiet(){
        listViewSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ThongTinChiTietActivity.class);
                intent.putExtra("idChiTiet",arrayListSanPham.get(i).getId());
                intent.putExtra("tenChiTiet",arrayListSanPham.get(i).getTen());
                intent.putExtra("giaNiemYetChiTiet",arrayListSanPham.get(i).getGiaNiemYet());
                intent.putExtra("giaBanChiTiet",arrayListSanPham.get(i).getGiaBan());
                intent.putExtra("hinhAnhChiTiet",arrayListSanPham.get(i).getHinhAnh());
                intent.putExtra("motaChiTiet",arrayListSanPham.get(i).getMoTa());
                intent.putExtra("daBanChiTiet",arrayListSanPham.get(i).getDaBan());
                intent.putExtra("idLoaiSPChiTiet",arrayListSanPham.get(i).getIdLoaiDienThoai());
                startActivity(intent);
            }
        });
    }
}