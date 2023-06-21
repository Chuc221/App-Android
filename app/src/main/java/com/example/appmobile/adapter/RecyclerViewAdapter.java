package com.example.appmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmobile.R;
import com.example.appmobile.model.Don;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHoler>{

    List<Don> list;

    private DonListener donListener;

    public void setCvListener(DonListener donListener) {
        this.donListener = donListener;
    }

    public RecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    public List<Don> getList() {
        return list;
    }

    public void setList(ArrayList<Don> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Don getDon(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongdon, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        Don cv = list.get(position);
        if(cv==null){
            return;
        }
        holder.txtTen.setText(cv.getTen());
        holder.txtND.setText(cv.getSdt());
        holder.txtngay.setText(" ");
        holder.txtTT.setText(cv.getSosp() +" sản phẩm");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtCT.setText("Thành tiền: "+decimalFormat.format(cv.getTien())+" đ");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtTen, txtND, txtTT, txtngay, txtCT;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtND = itemView.findViewById(R.id.txtND);
            txtngay = itemView.findViewById(R.id.txtngay);
            txtTT = itemView.findViewById(R.id.txtTT);
            txtCT = itemView.findViewById(R.id.txtCT);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(donListener!=null){
                donListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface DonListener{
        void onItemClick(View view, int position);
    }
}
