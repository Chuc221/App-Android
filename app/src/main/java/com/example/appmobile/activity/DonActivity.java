package com.example.appmobile.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmobile.R;
import com.example.appmobile.adapter.LoaiDienThoaiAdapter;
import com.example.appmobile.adapter.ViewPagerAdapter;
import com.example.appmobile.model.LoaiDienThoai;
import com.example.appmobile.model.ketnoicsdl;
import com.example.appmobile.model.ktketnoi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonActivity extends AppCompatActivity {

    DrawerLayout drawerLayoutDon;
    Toolbar toolbarDon;
    private BottomNavigationView navigationView;
    private ViewPager viewPager;

    NavigationView navigationViewDon;
    ListView listViewDon;
    ArrayList<LoaiDienThoai> arrayLoaiSanPham;
    LoaiDienThoaiAdapter loaiDienThoaiAdapter;
    int sumLoaiSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don);

        drawerLayoutDon =  findViewById(R.id.drawerLayoutDon);
        toolbarDon =  findViewById(R.id.toolbarGioHang);
        navigationViewDon =  findViewById(R.id.navigationViewDon);
        listViewDon =  findViewById(R.id.listviewDon);

        arrayLoaiSanPham = new ArrayList<>();
        loaiDienThoaiAdapter = new LoaiDienThoaiAdapter(getApplicationContext(), arrayLoaiSanPham);
        listViewDon.setAdapter(loaiDienThoaiAdapter);

        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: navigationView.getMenu().findItem(R.id.mChoXN).setChecked(true);
                        break;
                    case 1: navigationView.getMenu().findItem(R.id.mDangGiao).setChecked(true);
                        break;
                    case 2: navigationView.getMenu().findItem(R.id.mDaGiao).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mChoXN: viewPager.setCurrentItem(0);
                        break;
                    case R.id.mDangGiao: viewPager.setCurrentItem(1);
                        break;
                    case R.id.mDaGiao: viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

        menu();
        getLoaiDienThoai();
        chonMenu();
    }

    private void menu(){  //hiển thị thanh menu các loại sản phẩm
        setSupportActionBar(toolbarDon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDon.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarDon.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutDon.openDrawer(GravityCompat.START);
            }
        });
    }

    private void getLoaiDienThoai(){  //lấy dữ liệu các loại sản phẩm để cho vào thanh menu
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ketnoicsdl.getLoaiDienThoai, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                sumLoaiSP = response.length();
                if(response!= null){
                    arrayLoaiSanPham.add(0,new LoaiDienThoai(0,"Trang chủ","https://e7.pngegg.com/pngimages/679/69/png-clipart-home-assistant-computer-icons-home-automation-kits-amazon-echo-home-blue-logo-thumbnail.png"));

                    //Thêm các loại sản phẩm vào mảng
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            arrayLoaiSanPham.add(new LoaiDienThoai(jsonObject.getInt("id"),
                                    jsonObject.getString("tenLoaiDienThoai"),
                                    jsonObject.getString("hinhAnh")));
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

    private void chonMenu(){ //chọn loại sản phẩm trong menu

        listViewDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == 0){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(DonActivity.this,MainActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutDon.closeDrawer(GravityCompat.START);
                }
                if(i>=1 && i<=sumLoaiSP){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(DonActivity.this,SanPhamActivity.class);
                        intent.putExtra("idLoaiDT",arrayLoaiSanPham.get(i).getId());
                        intent.putExtra("logo",arrayLoaiSanPham.get(i).getHinhAnh());
                        intent.putExtra("tenLoaiDT",arrayLoaiSanPham.get(i).getTenLoaiDienThoai());
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutDon.closeDrawer(GravityCompat.START);
                }
                if(i==(sumLoaiSP+1)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),LienHeActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutDon.closeDrawer(GravityCompat.START);
                }
                if (i==(sumLoaiSP+2)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),ThongTinActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutDon.closeDrawer(GravityCompat.START);
                }

            }
        });

    }
}