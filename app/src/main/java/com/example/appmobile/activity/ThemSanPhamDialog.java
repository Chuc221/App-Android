package com.example.appmobile.activity;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appmobile.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ThemSanPhamDialog extends DialogFragment {

    ImageView imageView ;
    TextView textViewTen ;
    TextView textViewGia ;
    TextView textViewNiemYet ;
    TextView textViewSoLuong;
    Button buttonTru;
    Button buttonso;
    Button buttoncong;
    Button buttonThem ;
    Button buttonHuy;

    //nhận giá trị tên, giá bán, hình ảnh sản phẩm khi khởi tạo dialog
    public static ThemSanPhamDialog newInstance(int id, String ten,Integer giaNiemYet, Integer gia, String hinhAnh, String mota, Integer daban, int idLoaiSP) {
        ThemSanPhamDialog dialog = new ThemSanPhamDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("ten", ten);
        bundle.putInt("giaNiemYet",giaNiemYet);
        bundle.putInt("gia", gia);
        bundle.putString("hinhAnh", hinhAnh);
        bundle.putString("mota",mota);
        bundle.putInt("daban",daban);
        bundle.putInt("idLoaiSP",idLoaiSP);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_them_san_pham,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // lấy giá trị từ bundle
        String ten = getArguments().getString("ten", "");
        Integer gia = getArguments().getInt("gia",0);
        Integer giaNiemYet = getArguments().getInt("giaNiemYet",0);
        String hinhAnh = getArguments().getString("hinhAnh", "");

        imageView =  view.findViewById(R.id.imageviewDialog);
        textViewTen =  view.findViewById(R.id.textViewTenDialog);
        textViewGia =  view.findViewById(R.id.textViewGiaBanDialog);
        textViewNiemYet =  view.findViewById(R.id.textViewNiemYetDialog);
        textViewSoLuong =  view.findViewById(R.id.textViewSoLuongDialog);
        buttonTru =  view.findViewById(R.id.buttonTruDialog);
        buttonso =  view.findViewById(R.id.buttonSoDialog);
        buttoncong =  view.findViewById(R.id.buttonCongDialog);
        buttonThem =  view.findViewById(R.id.buttonThemDialog);
        buttonHuy =  view.findViewById(R.id.buttonHuyDialog);

        textViewTen.setText(ten);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###"); //định dạng giá cả
        textViewGia.setText(decimalFormat.format(gia)+" đ");
        textViewNiemYet.setText(decimalFormat.format(giaNiemYet)+" đ");
        textViewNiemYet.setPaintFlags(textViewNiemYet.getPaintFlags()| STRIKE_THRU_TEXT_FLAG);
        Picasso.with(getActivity()).load(hinhAnh)
                .placeholder(R.drawable.loading)
                .error(R.drawable.eror)
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        buttonTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int so = Integer.parseInt(buttonso.getText().toString());
                if(so>1){
                    so-=1;
                    buttonso.setText(so+"");
                }else {
                    getDialog().cancel();
                }
            }
        });

        buttoncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int so = Integer.parseInt(buttonso.getText().toString())+1;
                buttonso.setText(so+"");
            }
        });

        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
                int t=Integer.parseInt(buttonso.getText().toString());
                Intent intent = new Intent(getActivity(),GioHangActivity.class);
                intent.putExtra("idGio",getArguments().getInt("id",0));
                intent.putExtra("tenGio",getArguments().getString("ten", ""));
                intent.putExtra("giaNiemYetGio",getArguments().getInt("giaNiemYet",0));
                intent.putExtra("giaBanGio",getArguments().getInt("gia",0));
                intent.putExtra("hinhAnhGio",getArguments().getString("hinhAnh", ""));
                intent.putExtra("motaGio",getArguments().getString("mota", ""));
                intent.putExtra("daBanGio",getArguments().getInt("daban",0));
                intent.putExtra("idLoaiSPGio",getArguments().getInt("idLoaiSP",0));
                intent.putExtra("soluongGio",t);
                startActivity(intent);
            }
        });
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });
    }
}
