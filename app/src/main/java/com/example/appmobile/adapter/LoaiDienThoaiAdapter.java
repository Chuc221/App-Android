package com.example.appmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmobile.R;
import com.example.appmobile.model.LoaiDienThoai;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoaiDienThoaiAdapter extends BaseAdapter {

    private Context context;
    private List<LoaiDienThoai> loaiDienThoais;

    public LoaiDienThoaiAdapter(Context context, List<LoaiDienThoai> loaiDienThoais) {
        this.context = context;
        this.loaiDienThoais = loaiDienThoais;
    }

    @Override
    public int getCount() {
        return loaiDienThoais.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiDienThoais.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        ImageView imageView;
        TextView textViewTen;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder=null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongloaidienthoai,null);
            viewHolder.imageView = view.findViewById(R.id.imageviewLoaiDienThoai);
            viewHolder.textViewTen = view.findViewById(R.id.textViewTenLoaiDienThoai);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        LoaiDienThoai loaiDienThoai = (LoaiDienThoai) getItem(i);
        viewHolder.textViewTen.setText(loaiDienThoai.getTenLoaiDienThoai());
        Picasso.with(context).load(loaiDienThoai.getHinhAnh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror)
                .into(viewHolder.imageView);

        return view;
    }
}
