package com.example.appmobile.activity;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.appmobile.R;
import com.example.appmobile.model.ktketnoi;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ThongTinChiTietActivity extends AppCompatActivity {

    ScrollView scrollViewChiTiet;
    ImageView imageViewChiTiet;
    TextView textViewTenChiTiet,textViewGiamChiTiet,textViewGiaBanChiTiet, textViewGiaNiemYetChiTiet,textViewMoTaChiTiet;
    Button buttonThemChiTiet;
    Toolbar toolbarChiTiet;

    int id;
    String ten;
    Integer giaNiemYet;
    Integer giaBan;
    String hinhAnh;
    String mota;
    Integer daban;
    int idLoaiSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chi_tiet);
        scrollViewChiTiet =  findViewById(R.id.scrollViewChiTiet);
        imageViewChiTiet =  findViewById(R.id.imageViewChiTiet);
        textViewTenChiTiet =  findViewById(R.id.textViewTenChiTiet);
        textViewGiamChiTiet =  findViewById(R.id.textViewGiamChiTiet);
        textViewGiaBanChiTiet =  findViewById(R.id.textViewGiaBanChiTiet);
        textViewGiaNiemYetChiTiet =  findViewById(R.id.textViewGiaNiemYetChiTiet);
        textViewMoTaChiTiet =  findViewById(R.id.textViewMotaChiTiet);
        buttonThemChiTiet =  findViewById(R.id.buttonThemChiTiet);
        toolbarChiTiet =  findViewById(R.id.toolbarChiTiet);

        if(ktketnoi.haveNetworkConnection(getApplicationContext())){

            quayLaiManHinhSanPham();
            getThongTinChiTiet();
            themGio();

        }
        else {

            ktketnoi.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối!");
            finish();

        }
    }

    private void quayLaiManHinhSanPham(){ //quay lại màn hình trước đó
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    private void getThongTinChiTiet(){ //lấy thông tin chi tiết và hiển thị ra màn hình
        id =getIntent().getIntExtra("idChiTiet",1);
        ten= getIntent().getStringExtra("tenChiTiet");
        giaNiemYet = getIntent().getIntExtra("giaNiemYetChiTiet",0);
        giaBan = getIntent().getIntExtra("giaBanChiTiet",0);
        hinhAnh= getIntent().getStringExtra("hinhAnhChiTiet");
        mota= getIntent().getStringExtra("motaChiTiet");
        daban = getIntent().getIntExtra("daBanChiTiet",0);
        idLoaiSP = getIntent().getIntExtra("idLoaiSPChiTiet",0);

        double a=(double) (giaNiemYet-giaBan)/giaBan;
        textViewTenChiTiet.setText(ten);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        textViewGiaBanChiTiet.setText(decimalFormat.format(giaBan)+" đ");

        if (a==0){
            textViewGiamChiTiet.setTextSize(0);
            textViewGiaNiemYetChiTiet.setTextSize(0);
        }else{
            textViewGiamChiTiet.setTextSize(17);
            textViewGiaNiemYetChiTiet.setTextSize(20);
            textViewGiaNiemYetChiTiet.setText(decimalFormat.format(giaNiemYet)+" đ");
            textViewGiaNiemYetChiTiet.setPaintFlags(textViewGiaNiemYetChiTiet.getPaintFlags()| STRIKE_THRU_TEXT_FLAG);//định dạng gạch ngang giá bán cũ

            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            String phantram = numberFormat.format(a);
            textViewGiamChiTiet.setText("Giảm"+'\n'+phantram);
        }

        if (textViewMoTaChiTiet.toString().trim().length()==0){
            textViewMoTaChiTiet.setTextSize(0);
        }else {
            textViewMoTaChiTiet.setTextSize(17);
            textViewMoTaChiTiet.setText(mota);
        }

        Picasso.with(getApplicationContext()).load(hinhAnh)
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror)
                .into(imageViewChiTiet);
        imageViewChiTiet.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private void themGio(){ //bấm vào nút thêm vào giỏ thì hiển thị dialog để lấy số lượng thêm
        buttonThemChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //hiển thị dialog và truyền giá trị vào dialog
                FragmentManager fm = getSupportFragmentManager();
                ThemSanPhamDialog userInfoDialog = ThemSanPhamDialog.newInstance(id,ten,giaNiemYet,giaBan,hinhAnh,mota,daban,idLoaiSP);
                userInfoDialog.show(fm, null);
            }
        });
    }
}