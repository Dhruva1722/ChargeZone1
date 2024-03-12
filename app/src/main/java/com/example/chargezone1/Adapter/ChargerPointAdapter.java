package com.example.chargezone1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chargezone1.R;


public class ChargerPointAdapter extends RecyclerView.Adapter<ChargerPointAdapter.ViewHolder> {

    @NonNull
    @Override
    public ChargerPointAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.charger_point_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChargerPointAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
         Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
