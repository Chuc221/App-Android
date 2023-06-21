package com.example.appmobile.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmobile.R;
import com.example.appmobile.activity.ChiTietDonActivity;
import com.example.appmobile.activity.DangNhapActivity;
import com.example.appmobile.activity.MainActivity;
import com.example.appmobile.adapter.RecyclerViewAdapter;
import com.example.appmobile.model.Don;
import com.example.appmobile.model.ketnoicsdl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DaGiaoFragment extends Fragment implements RecyclerViewAdapter.DonListener{

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    ArrayList<Don> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_da_giao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rviewDS);
        adapter = new RecyclerViewAdapter();

        arrayList = new ArrayList<>();
        adapter.setList(arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setCvListener(this);
        getDon();
    }

    @Override
    public void onItemClick(View view, int position) {
        Don don = adapter.getDon(position);
        Intent intent = new Intent(getActivity(), ChiTietDonActivity.class);
        intent.putExtra("iddon",don.getId());
        intent.putExtra("tong",don.getTien());
        intent.putExtra("ten",don.getTen());
        intent.putExtra("sodt",don.getSdt());
        intent.putExtra("diachi",don.getDiachi());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getDon(){ //lấy dữ liệu các đơn để đổ ra màn hình
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ketnoicsdl.getDon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            arrayList.add(new Don(jsonObject.getInt("id"),
                                    jsonObject.getInt("idkh"),
                                    jsonObject.getString("hoten"),
                                    jsonObject.getString("sodt"),
                                    jsonObject.getString("diachi"),
                                    jsonObject.getInt("soluongsp"),
                                    jsonObject.getInt("tongthanhtoan")));
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int idkh = DangNhapActivity.khachHang.getIdkh();
                int tt = 3;
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("idkh", String.valueOf(idkh));
                map.put("tt", String.valueOf(tt));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}