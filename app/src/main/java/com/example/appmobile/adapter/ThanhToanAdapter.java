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
import com.example.appmobile.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThanhToanAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GioHang> arrayListThanhToan;

    public ThanhToanAdapter(Context context, ArrayList<GioHang> arrayListThanhToan) {
        this.context = context;
        this.arrayListThanhToan = arrayListThanhToan;
    }

    @Override
    public int getCount() {
        return arrayListThanhToan.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListThanhToan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolderThanhToan{
        ImageView imageViewThanhToan;
        TextView textViewTenThanhToan;
        TextView textViewGiaBanThanhToan;
        TextView textViewGiaNiemYetThanhToan;
        TextView textViewSoLuongThanhToan;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolderThanhToan viewHolderThanhToan;

        if (view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongthanhtoan,null);

            viewHolderThanhToan.imageViewThanhToan =  view.findViewById(R.id.imageviewThanhToan);
            viewHolderThanhToan.textViewTenThanhToan =  view.findViewById(R.id.textViewTenThanhToan);
            viewHolderThanhToan.textViewGiaBanThanhToan =  view.findViewById(R.id.textViewGiaBanThanhToan);
            viewHolderThanhToan.textViewGiaNiemYetThanhToan =  view.findViewById(R.id.textViewGiaNiemYetThanhToan);
            viewHolderThanhToan.textViewSoLuongThanhToan =  view.findViewById(R.id.textViewSoLuongThanhToan);

            view.setTag(viewHolderThanhToan);
        }else {
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        GioHang gioHang = (GioHang) getItem(i);
        viewHolderThanhToan.textViewTenThanhToan.setText(gioHang.getTen());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        viewHolderThanhToan.textViewGiaBanThanhToan.setText(decimalFormat.format(gioHang.getGiaBan())+" đ");
        viewHolderThanhToan.textViewGiaNiemYetThanhToan.setText(decimalFormat.format(gioHang.getGiaNiemYet())+" đ");
        viewHolderThanhToan.textViewGiaNiemYetThanhToan.setPaintFlags(viewHolderThanhToan.textViewGiaNiemYetThanhToan.getPaintFlags()| STRIKE_THRU_TEXT_FLAG);


        viewHolderThanhToan.textViewSoLuongThanhToan.setText("x"+gioHang.getSoLuong());

        Picasso.with(context).load(gioHang.getHinhAnh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror)
                .into(viewHolderThanhToan.imageViewThanhToan);
        viewHolderThanhToan.imageViewThanhToan.setScaleType(ImageView.ScaleType.FIT_XY);

        return view;
    }
}
