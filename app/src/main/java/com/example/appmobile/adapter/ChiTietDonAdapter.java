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
import com.example.appmobile.model.ChiTietDon;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietDonAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ChiTietDon> arrayList;

    public ChiTietDonAdapter(Context context, ArrayList<ChiTietDon> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView textViewTen;
        TextView textViewGiaBan;
        TextView textViewGiaNiemYet;
        TextView textViewSoLuong;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongthanhtoan,null);

            viewHolder.imageView =  view.findViewById(R.id.imageviewThanhToan);
            viewHolder.textViewTen =  view.findViewById(R.id.textViewTenThanhToan);
            viewHolder.textViewGiaBan =  view.findViewById(R.id.textViewGiaBanThanhToan);
            viewHolder.textViewGiaNiemYet=  view.findViewById(R.id.textViewGiaNiemYetThanhToan);
            viewHolder.textViewSoLuong =  view.findViewById(R.id.textViewSoLuongThanhToan);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ChiTietDon chiTietDon = (ChiTietDon) getItem(i);
        viewHolder.textViewTen.setText(chiTietDon.getTen());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        viewHolder.textViewGiaBan.setText(decimalFormat.format(chiTietDon.getGiaBan())+" đ");
        viewHolder.textViewGiaNiemYet.setText(decimalFormat.format(chiTietDon.getGiaNiemYet())+" đ");
        viewHolder.textViewGiaNiemYet.setPaintFlags(viewHolder.textViewGiaNiemYet.getPaintFlags()| STRIKE_THRU_TEXT_FLAG);


        viewHolder.textViewSoLuong.setText("x"+chiTietDon.getSosp());

        Picasso.with(context).load(chiTietDon.getHinhAnh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror)
                .into(viewHolder.imageView);
        viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return view;
    }
}
