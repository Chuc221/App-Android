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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmobile.R;
import com.example.appmobile.adapter.LoaiDienThoaiAdapter;
import com.example.appmobile.adapter.SanPhamMoiAdapter;
import com.example.appmobile.model.DienThoai;
import com.example.appmobile.model.KhachHang;
import com.example.appmobile.model.LoaiDienThoai;
import com.example.appmobile.model.ketnoicsdl;
import com.example.appmobile.model.ktketnoi;
import com.example.appmobile.model.quangcao;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    GridView gridView;
    NavigationView navigationView;
    ListView listView;
    //tạo mảng
    ArrayList<quangcao> mangQC;
    ArrayList<LoaiDienThoai> arrayLoaiDienThoai;
    LoaiDienThoaiAdapter loaiDienThoaiAdapter;
    ArrayList<DienThoai> arrayListDienThoai;
    SanPhamMoiAdapter sanPhamMoiAdapter;
    int sumLoaiDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout =  findViewById(R.id.drawerLayoutManHinhChinh);
        toolbar =  findViewById(R.id.toolbarManHinhChinh);
        viewFlipper =  findViewById(R.id.viewFlipperManHinhChinh);
        gridView =  findViewById(R.id.gridViewManHinhChinh);
        navigationView =  findViewById(R.id.navigationViewManHinhChinh);
        listView =  findViewById(R.id.listviewManHinhChinh);

        mangQC = new ArrayList<>();

        arrayLoaiDienThoai = new ArrayList<>();
        loaiDienThoaiAdapter = new LoaiDienThoaiAdapter(getApplicationContext(), arrayLoaiDienThoai);
        listView.setAdapter(loaiDienThoaiAdapter);

        arrayListDienThoai = new ArrayList<>();
        sanPhamMoiAdapter = new SanPhamMoiAdapter(getApplicationContext(),arrayListDienThoai);
        gridView.setAdapter(sanPhamMoiAdapter);

        if(ktketnoi.haveNetworkConnection(getApplicationContext())){
            chayViewFlipper();
            menu();
            getLoaiDienThoai();
            chonMenu();
            getDienThoai();
            chonSanPham();
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
                Collections.sort(arrayListDienThoai, new Comparator<DienThoai>() {
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
                sanPhamMoiAdapter.notifyDataSetChanged();
                break;

            case R.id.menuGiam:
                Collections.sort(arrayListDienThoai, new Comparator<DienThoai>() {
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
                sanPhamMoiAdapter.notifyDataSetChanged();
                break;

            case R.id.menuPhoBien:
                Collections.sort(arrayListDienThoai, new Comparator<DienThoai>() {
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
                sanPhamMoiAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void chayViewFlipper(){ //lấy các hình ảnh quảng cáo, sale,... để hiển thị trên trang chủ

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ketnoicsdl.hinhAnhQC, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {

                    int id=0;
                    String hinhAnhQC="";
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            hinhAnhQC=jsonObject.getString("hinhanhquangcao");
                            mangQC.add(new quangcao(id,hinhAnhQC));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    for (int i = 0; i < mangQC.size(); i++) {
                        ImageView imageView=new ImageView(getApplicationContext());
                        Picasso.with(getApplicationContext()).load(mangQC.get(i).getHinhAnhQC()).into(imageView);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        viewFlipper.addView(imageView);
                    }

                    viewFlipper.setFlipInterval(3000);
                    viewFlipper.setAutoStart(true);

                    Animation animationQCin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.qc_in_left);
                    Animation animationQCout = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.qc_out_left);
                    viewFlipper.setInAnimation(animationQCin);
                    viewFlipper.setOutAnimation(animationQCout);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ktketnoi.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    private void menu(){  //hiển thị thanh menu các loại điện thoại
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void chonMenu(){ //chọn loại điện thoại trong menu

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i ==0){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(i>=1 && i<=sumLoaiDT){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(MainActivity.this,SanPhamActivity.class);
                        intent.putExtra("idLoaiDT",arrayLoaiDienThoai.get(i).getId());
                        intent.putExtra("logo",arrayLoaiDienThoai.get(i).getHinhAnh());
                        intent.putExtra("tenLoaiDT",arrayLoaiDienThoai.get(i).getTenLoaiDienThoai());
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(i==(sumLoaiDT+1)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),LienHeActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (i==(sumLoaiDT+2)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),ThongTinActivity.class);
                        startActivity(intent);

                    }else {
                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
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
                    arrayLoaiDienThoai.add(0,new LoaiDienThoai(0,"Trang chủ","https://e7.pngegg.com/pngimages/679/69/png-clipart-home-assistant-computer-icons-home-automation-kits-amazon-echo-home-blue-logo-thumbnail.png"));

                    //Thêm các loại điện thoại vào mảng
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            arrayLoaiDienThoai.add(new LoaiDienThoai(jsonObject.getInt("id"),
                                    jsonObject.getString("tenLoaiDienThoai"),
                                    jsonObject.getString("hinhAnh")));
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
    private void getDienThoai(){ //lấy dữ liệu các sản phẩm mới để đổ ra màn hình
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ketnoicsdl.getSanPhamMoi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            arrayListDienThoai.add(new DienThoai(jsonObject.getInt("id"),
                                    jsonObject.getString("tenDienThoai"),
                                    jsonObject.getInt("giaNiemYet"),
                                    jsonObject.getInt("giaBan"),
                                    jsonObject.getString("hinhAnh"),
                                    jsonObject.getString("moTa"),
                                    jsonObject.getInt("daBan"),
                                    jsonObject.getInt("idLoaiDienThoai")));
                            sanPhamMoiAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
    private void chonSanPham(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ThongTinChiTietActivity.class);
                intent.putExtra("idChiTiet",arrayListDienThoai.get(i).getId());
                intent.putExtra("tenChiTiet",arrayListDienThoai.get(i).getTen());
                intent.putExtra("giaNiemYetChiTiet",arrayListDienThoai.get(i).getGiaNiemYet());
                intent.putExtra("giaBanChiTiet",arrayListDienThoai.get(i).getGiaBan());
                intent.putExtra("hinhAnhChiTiet",arrayListDienThoai.get(i).getHinhAnh());
                intent.putExtra("motaChiTiet",arrayListDienThoai.get(i).getMoTa());
                intent.putExtra("daBanChiTiet",arrayListDienThoai.get(i).getDaBan());
                intent.putExtra("idLoaiSPChiTiet",arrayListDienThoai.get(i).getIdLoaiDienThoai());
                startActivity(intent);
            }
        });
    }
}