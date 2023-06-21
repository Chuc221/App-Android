package com.example.appmobile.adapter;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmobile.R;
import com.example.appmobile.model.DienThoai;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SanPhamMoiAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DienThoai> listDienThoai;

    public SanPhamMoiAdapter(Context context, ArrayList<DienThoai> listDienThoai) {
        this.context = context;
        this.listDienThoai = listDienThoai;
    }

    @Override
    public int getCount() {
        return listDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return listDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{

        ImageView imageViewDienThoai;
        TextView textViewTenDienThoai;
        TextView textViewGiaNiemYet;
        TextView textViewGiaBan;
        TextView textViewGiamGia;
        TextView textViewDaBan;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongcacmaumoi, null);

            holder.imageViewDienThoai = view.findViewById(R.id.imageviewDienThoaiMoi);
            holder.textViewTenDienThoai = view.findViewById(R.id.textViewTenDienThoaiMoi);
            holder.textViewGiaNiemYet = view.findViewById(R.id.textViewGiaNiemYetMoi);
            holder.textViewGiaBan = view.findViewById(R.id.textViewGiaBanMoi);
            holder.textViewGiamGia = view.findViewById(R.id.textViewGiamGiaMoi);
            holder.textViewDaBan = view.findViewById(R.id.textViewDaBanMoi);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        DienThoai dienThoai = (DienThoai) getItem(i);
        double a=(double) (dienThoai.getGiaNiemYet()-dienThoai.getGiaBan())/dienThoai.getGiaNiemYet();

        holder.textViewTenDienThoai.setText(dienThoai.getTen());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        holder.textViewGiaBan.setText(decimalFormat.format(dienThoai.getGiaBan())+" đ");

        if (a==0){
            holder.textViewGiamGia.setTextSize(0);
            holder.textViewGiaNiemYet.setTextSize(0);
        }else{
            holder.textViewGiamGia.setTextSize(17);
            holder.textViewGiaNiemYet.setTextSize(17);
            holder.textViewGiaNiemYet.setText(decimalFormat.format(dienThoai.getGiaNiemYet())+" đ");
            holder.textViewGiaNiemYet.setPaintFlags(holder.textViewGiaNiemYet.getPaintFlags()| STRIKE_THRU_TEXT_FLAG);//định dạng gạch ngang giá bán cũ

            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            String phantram = numberFormat.format(a);
            holder.textViewGiamGia.setText("Giảm "+phantram);
        }

        if (dienThoai.getDaBan()==0){
            holder.textViewDaBan.setTextSize(0);
        }else {
            holder.textViewDaBan.setTextSize(15);
            holder.textViewDaBan.setText("Đã bán: "+dienThoai.getDaBan());
        }

        Picasso.with(context).load(dienThoai.getHinhAnh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror)
                .into(holder.imageViewDienThoai);

        return view;
    }
}
