package com.example.appmobile.adapter;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

import android.content.Context;
import android.text.TextUtils;
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

public class SanPhamAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DienThoai> arrayListSanPham;

    public SanPhamAdapter(Context context, ArrayList<DienThoai> arrayListSanPham) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
    }

    @Override
    public int getCount() {
        return arrayListSanPham.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListSanPham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolderSanPham{
        ImageView imageViewSanPham;
        TextView textViewTenSanPham;
        TextView textViewGiaNiemYetSanPham;
        TextView textViewGiamGiaSanPham;
        TextView textViewGiaBanSanPham;
        TextView textViewMoTaSanPham;
        TextView textViewDaBanSanPham;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolderSanPham viewHolderSanPham = null;

        if (view==null){
            viewHolderSanPham = new ViewHolderSanPham();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongsanpham,null);

            viewHolderSanPham.imageViewSanPham = view.findViewById(R.id.imageviewDienThoai);
            viewHolderSanPham.textViewTenSanPham = view.findViewById(R.id.textViewDienThoai);
            viewHolderSanPham.textViewGiaNiemYetSanPham = view.findViewById(R.id.textViewGiaCuDienThoai);
            viewHolderSanPham.textViewGiaBanSanPham = view.findViewById(R.id.textViewGiaBanDienThoai);
            viewHolderSanPham.textViewGiamGiaSanPham = view.findViewById(R.id.textViewGiamDienThoai);
            viewHolderSanPham.textViewMoTaSanPham = view.findViewById(R.id.textViewMotaDienThoai);
            viewHolderSanPham.textViewDaBanSanPham = view.findViewById(R.id.textViewDaBanDienThoai);

            view.setTag(viewHolderSanPham);
        }else {
            viewHolderSanPham = (ViewHolderSanPham) view.getTag();
        }
        DienThoai dienThoai = (DienThoai) getItem(i);

        double a=(double) (dienThoai.getGiaNiemYet()-dienThoai.getGiaBan())/dienThoai.getGiaNiemYet();
        viewHolderSanPham.textViewTenSanPham.setText(dienThoai.getTen());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        viewHolderSanPham.textViewGiaBanSanPham.setText(decimalFormat.format(dienThoai.getGiaBan())+" đ");

        if (a==0){
            viewHolderSanPham.textViewGiamGiaSanPham.setTextSize(0);
            viewHolderSanPham.textViewGiaNiemYetSanPham.setTextSize(0);
        }else{
            viewHolderSanPham.textViewGiamGiaSanPham.setTextSize(17);
            viewHolderSanPham.textViewGiaNiemYetSanPham.setTextSize(20);
            viewHolderSanPham.textViewGiaNiemYetSanPham.setText(decimalFormat.format(dienThoai.getGiaNiemYet())+" đ");
            viewHolderSanPham.textViewGiaNiemYetSanPham.setPaintFlags(viewHolderSanPham.textViewGiaNiemYetSanPham.getPaintFlags()| STRIKE_THRU_TEXT_FLAG);//định dạng gạch ngang giá bán cũ

            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            String phantram = numberFormat.format(a);
            viewHolderSanPham.textViewGiamGiaSanPham.setText("Giảm "+phantram);
        }

        if (dienThoai.getDaBan()==0){
            viewHolderSanPham.textViewDaBanSanPham.setTextSize(0);
        }else {
            viewHolderSanPham.textViewDaBanSanPham.setTextSize(15);
            viewHolderSanPham.textViewDaBanSanPham.setText("Đã bán: "+dienThoai.getDaBan());
        }

        if (viewHolderSanPham.textViewMoTaSanPham.toString().trim().length()==0){
            viewHolderSanPham.textViewMoTaSanPham.setTextSize(0);
        }else {
            viewHolderSanPham.textViewMoTaSanPham.setTextSize(17);
            viewHolderSanPham.textViewMoTaSanPham.setMaxLines(5);
            viewHolderSanPham.textViewMoTaSanPham.setEllipsize(TextUtils.TruncateAt.END);
            viewHolderSanPham.textViewMoTaSanPham.setText(dienThoai.getMoTa());
        }

        Picasso.with(context).load(dienThoai.getHinhAnh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror)
                .into(viewHolderSanPham.imageViewSanPham);
        viewHolderSanPham.imageViewSanPham.setScaleType(ImageView.ScaleType.FIT_XY);

        return view;
    }
}
