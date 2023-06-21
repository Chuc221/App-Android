package com.example.appmobile.adapter;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.appmobile.R;
import com.example.appmobile.activity.GioHangActivity;
import com.example.appmobile.activity.ThongTinChiTietActivity;
import com.example.appmobile.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GioHang> arrayListGio;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayListGio) {
        this.context = context;
        this.arrayListGio = arrayListGio;
    }

    @Override
    public int getCount() {
        return arrayListGio.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListGio.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolderGio{
        CheckBox checkBoxGio;
        ImageView imageViewGio;
        TextView textViewTenGio;
        TextView textViewGiaBanGio;
        TextView textViewNiemYetGio;
        Button buttonTruGio;
        Button buttonSoGio;
        Button buttonCongGio;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolderGio viewHolderGio;

        if(view == null){
            viewHolderGio = new ViewHolderGio();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.donggiohang,null);

            viewHolderGio.checkBoxGio =  view.findViewById(R.id.checkboxGio);
            viewHolderGio.imageViewGio =  view.findViewById(R.id.imageviewGio);
            viewHolderGio.textViewTenGio =  view.findViewById(R.id.textViewTenGio);
            viewHolderGio.textViewGiaBanGio =  view.findViewById(R.id.textViewGiaBanGio);
            viewHolderGio.textViewNiemYetGio =  view.findViewById(R.id.textViewGiaNiemYetGio);
            viewHolderGio.buttonTruGio =  view.findViewById(R.id.buttonTruGio);
            viewHolderGio.buttonSoGio =  view.findViewById(R.id.buttonSoGio);
            viewHolderGio.buttonCongGio =  view.findViewById(R.id.buttonCongGio);

            view.setTag(viewHolderGio);
        }else {
            viewHolderGio = (ViewHolderGio) view.getTag();
        }

        GioHang gioHang = (GioHang) getItem(i);

        viewHolderGio.textViewTenGio.setText(gioHang.getTen());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        viewHolderGio.textViewGiaBanGio.setText(decimalFormat.format(gioHang.getGiaBan())+" đ");
        viewHolderGio.textViewNiemYetGio.setText(decimalFormat.format(gioHang.getGiaNiemYet()) + " đ");
        viewHolderGio.textViewNiemYetGio.setPaintFlags(viewHolderGio.textViewNiemYetGio.getPaintFlags()| STRIKE_THRU_TEXT_FLAG);
        Picasso.with(context).load(gioHang.getHinhAnh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror)
                .into(viewHolderGio.imageViewGio);
        viewHolderGio.imageViewGio.setScaleType(ImageView.ScaleType.FIT_XY);

        viewHolderGio.buttonSoGio.setText(gioHang.getSoLuong()+"");

        viewHolderGio.checkBoxGio.setChecked(gioHang.isCheck());
        viewHolderGio.checkBoxGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolderGio.checkBoxGio.isChecked()){
                    GioHangActivity.arrayListGioHang.get(i).setCheck(true);
                }else {
                    GioHangActivity.arrayListGioHang.get(i).setCheck(false);
                    GioHangActivity.checkBoxAllGioHang.setChecked(false);
                }
                GioHangActivity.getTong();
            }
        });

        viewHolderGio.buttonTruGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int so = Integer.parseInt(viewHolderGio.buttonSoGio.getText().toString());
                if(so>1){
                    so-=1;
                    viewHolderGio.buttonSoGio.setText(so+"");
                    gioHang.setSoLuong(so);
                    GioHangActivity.getTong();
                }else {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getRootView().getContext());
                    alertDialog.setIcon(R.drawable.logo_c_phone);
                    alertDialog.setTitle("Thông báo!");
                    alertDialog.setMessage("Bạn có muốn xóa "+ gioHang.getTen() +" khỏi giỏ hàng ko không?");

                    alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            arrayListGio.remove(gioHang);
                            notifyDataSetChanged();
                            if (arrayListGio.size()==0){
                                GioHangActivity.textViewThongBaoGioHang.setVisibility(View.VISIBLE);
                                GioHangActivity.checkBoxAllGioHang.setEnabled(false);
                            }
                            GioHangActivity.getTong();

                        }
                    });
                    alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    alertDialog.show();

                }
            }
        });

        viewHolderGio.buttonCongGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int so = Integer.parseInt(viewHolderGio.buttonSoGio.getText().toString())+1;
                viewHolderGio.buttonSoGio.setText(so+"");
                gioHang.setSoLuong(so);
                GioHangActivity.getTong();
            }
        });

        viewHolderGio.imageViewGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThongTinChiTietActivity.class);
                intent.putExtra("idChiTiet",gioHang.getId());
                intent.putExtra("tenChiTiet",gioHang.getTen());
                intent.putExtra("giaNiemYetChiTiet",gioHang.getGiaNiemYet());
                intent.putExtra("giaBanChiTiet",gioHang.getGiaBan());
                intent.putExtra("hinhAnhChiTiet",gioHang.getHinhAnh());
                intent.putExtra("motaChiTiet",gioHang.getMoTa());
                intent.putExtra("daBanChiTiet",gioHang.getDaBan());
                intent.putExtra("idLoaiSPChiTiet",gioHang.getIdLoaiDienThoai());
                context.startActivity(intent);
            }
        });

        viewHolderGio.textViewTenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ThongTinChiTietActivity.class);
                intent.putExtra("idChiTiet",gioHang.getId());
                intent.putExtra("tenChiTiet",gioHang.getTen());
                intent.putExtra("giaNiemYetChiTiet",gioHang.getGiaNiemYet());
                intent.putExtra("giaBanChiTiet",gioHang.getGiaBan());
                intent.putExtra("hinhAnhChiTiet",gioHang.getHinhAnh());
                intent.putExtra("motaChiTiet",gioHang.getMoTa());
                intent.putExtra("daBanChiTiet",gioHang.getDaBan());
                intent.putExtra("idLoaiSPChiTiet",gioHang.getIdLoaiDienThoai());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
