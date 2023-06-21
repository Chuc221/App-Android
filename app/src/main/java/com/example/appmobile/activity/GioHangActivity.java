package com.example.appmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmobile.R;
import com.example.appmobile.adapter.GioHangAdapter;
import com.example.appmobile.adapter.LoaiDienThoaiAdapter;
import com.example.appmobile.model.GioHang;
import com.example.appmobile.model.LoaiDienThoai;
import com.example.appmobile.model.ketnoicsdl;
import com.example.appmobile.model.ktketnoi;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {

    DrawerLayout drawerLayoutGioHang;
    Toolbar toolbarGioHang;
    public static TextView textViewThongBaoGioHang;
    ListView listViewSanPhamGioHang;
    public static CheckBox checkBoxAllGioHang;
    static TextView textViewTienGioHang;
    static Button buttonGioHang;
    NavigationView navigationViewGioHang;
    ListView listViewGioHang;

    ArrayList<LoaiDienThoai> arrayLoaiSanPham;
    LoaiDienThoaiAdapter loaiDienThoaiAdapter;
    int sumLoaiSP;

    public static ArrayList<GioHang> arrayListGioHang;
    static GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        drawerLayoutGioHang =  findViewById(R.id.drawerLayoutGioHang);
        toolbarGioHang =  findViewById(R.id.toolbarGioHang);
        textViewThongBaoGioHang =  findViewById(R.id.textviewThongBaoGioHang);
        listViewSanPhamGioHang =  findViewById(R.id.listviewSanPhamGioHang);
        checkBoxAllGioHang =  findViewById(R.id.checkboxAllGioHang);
        textViewTienGioHang =  findViewById(R.id.textViewTienGioHang);
        buttonGioHang =  findViewById(R.id.buttonGioHang);
        navigationViewGioHang =  findViewById(R.id.navigationViewGioHang);
        listViewGioHang =  findViewById(R.id.listviewGioHang);

        arrayLoaiSanPham = new ArrayList<>();
        loaiDienThoaiAdapter = new LoaiDienThoaiAdapter(getApplicationContext(), arrayLoaiSanPham);
        listViewGioHang.setAdapter(loaiDienThoaiAdapter);

        if (arrayListGioHang == null){
            arrayListGioHang = new ArrayList<>();
        }
        gioHangAdapter = new GioHangAdapter(getApplicationContext(),arrayListGioHang);
        listViewSanPhamGioHang.setAdapter(gioHangAdapter);

        if(ktketnoi.haveNetworkConnection(getApplicationContext())){

            menu();
            getLoaiDienThoai();
            chonMenu();
            getSP();
            checkAll();
            muaHang();

        }
        else {

            ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
            finish();

        }
    }

    private void checkAll(){
        checkBoxAllGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxAllGioHang.isChecked()){
                    for (int i = 0; i < arrayListGioHang.size(); i++) {
                        arrayListGioHang.get(i).setCheck(true);
                    }
                }else {
                    for (int i = 0; i < arrayListGioHang.size(); i++) {
                        arrayListGioHang.get(i).setCheck(false);
                    }
                }
                gioHangAdapter.notifyDataSetChanged();
                getTong();
            }
        });
    }

    private void getSP(){
        int id =getIntent().getIntExtra("idGio",1);
        String ten= getIntent().getStringExtra("tenGio");
        Integer giaNiemYet = getIntent().getIntExtra("giaNiemYetGio",0);
        Integer giaBan = getIntent().getIntExtra("giaBanGio",0);
        String hinhAnh= getIntent().getStringExtra("hinhAnhGio");
        String mota= getIntent().getStringExtra("motaGio");
        Integer daban = getIntent().getIntExtra("daBanGio",0);
        int idLoaiSP = getIntent().getIntExtra("idLoaiSPGio",0);
        Integer soLuong =getIntent().getIntExtra("soluongGio",0);
        textViewThongBaoGioHang.setVisibility(View.INVISIBLE);
        GioHang gioHang = new GioHang(id,ten,giaNiemYet,giaBan,hinhAnh,mota,daban,idLoaiSP,soLuong,false);

        int x=0;
        for (int i = 0; i < arrayListGioHang.size(); i++) {
            if (arrayListGioHang.get(i).getId()==gioHang.getId()){
                arrayListGioHang.get(i).setSoLuong(arrayListGioHang.get(i).getSoLuong()+gioHang.getSoLuong());
                break;
            }else {
                x++;
            }
        }
        if (x==arrayListGioHang.size() && id!=0){
            arrayListGioHang.add(gioHang);
        }
        if (arrayListGioHang.size()==0){
            checkBoxAllGioHang.setEnabled(false);
            textViewThongBaoGioHang.setVisibility(View.VISIBLE);
        }
        gioHangAdapter.notifyDataSetChanged();
        getTong();
    }

    public static void getTong(){ //tính tổng tiền thanh toán
        Integer sum = 0;
        int t=0;
        for (int i = 0; i < arrayListGioHang.size(); i++) {
            if (arrayListGioHang.get(i).isCheck()){
                t++;
                sum=sum+(arrayListGioHang.get(i).getGiaBan())*(arrayListGioHang.get(i).getSoLuong());
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        buttonGioHang.setText("Mua hàng("+t+")");
        textViewTienGioHang.setText(decimalFormat.format(sum)+" đ");
    }

    private void menu(){  //hiển thị thanh menu các loại sản phẩm
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutGioHang.openDrawer(GravityCompat.START);
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

        listViewGioHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == 0){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(GioHangActivity.this,MainActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutGioHang.closeDrawer(GravityCompat.START);
                }
                if(i>=1 && i<=sumLoaiSP){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(GioHangActivity.this,SanPhamActivity.class);
                        intent.putExtra("idLoaiDT",arrayLoaiSanPham.get(i).getId());
                        intent.putExtra("logo",arrayLoaiSanPham.get(i).getHinhAnh());
                        intent.putExtra("tenLoaiDT",arrayLoaiSanPham.get(i).getTenLoaiDienThoai());
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutGioHang.closeDrawer(GravityCompat.START);
                }
                if(i==(sumLoaiSP+1)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),LienHeActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutGioHang.closeDrawer(GravityCompat.START);
                }
                if (i==(sumLoaiSP+2)){
                    if (ktketnoi.haveNetworkConnection(getApplicationContext())){

                        Intent intent = new Intent(getApplicationContext(),ThongTinActivity.class);
                        startActivity(intent);

                    }else {

                        ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");

                    }
                    drawerLayoutGioHang.closeDrawer(GravityCompat.START);
                }

            }
        });

    }

    private void muaHang(){
        buttonGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayListGioHang.size()==0){
                    Toast.makeText(getApplicationContext(), "Vui lòng thêm sản phẩm vào giỏ!", Toast.LENGTH_SHORT).show();
                }else {
                    int j=arrayListGioHang.size()-1;
                    int x=0;
                    for (int i = j; i >=0 ; i--) {
                        if (!arrayListGioHang.get(i).isCheck()){
                            x++;
                        }
                    }
                    if (x==j+1){
                        Toast.makeText(getApplicationContext(), "Vui lòng chọn sản phẩm để mua!", Toast.LENGTH_SHORT).show();
                    }else {
                        if (arrayListGioHang.size()==0){
                            textViewThongBaoGioHang.setVisibility(View.VISIBLE);
                        }
                        gioHangAdapter.notifyDataSetChanged();
                        getTong();

                        Intent intent = new Intent(getApplicationContext(),ThanhToanActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}