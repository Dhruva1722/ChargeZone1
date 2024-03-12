package com.example.chargezone1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.view.ActionMode;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chargezone1.Model.StationData;
import com.example.chargezone1.R;

import java.util.ArrayList;
import java.util.List;

public class StationDataAdapter extends RecyclerView.Adapter<StationDataAdapter.ViewHolder> {

    private List<StationData> stationDataList;
    private ArrayList<StationData> selectedItems = new ArrayList<>();
    private boolean isActionModeActive = false;
    private AppCompatActivity activity;

    public StationDataAdapter(List<StationData> stationDataList, AppCompatActivity activity) {
        this.stationDataList = stationDataList;
        this.activity = activity;
    }

    public List<StationData> getStationDataList() {
        return stationDataList;
    }

    public void clearSelectedItems() {
        selectedItems.clear();
        notifyDataSetChanged(); // Notify adapter after clearing selection
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_charging_station, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StationData stationData = stationDataList.get(position);
        holder.stationNameTextView.setText(stationData.getStationAddress());
        holder.unitTextView.setText(stationData.getStationUnits());
        boolean isSelected = selectedItems.contains(stationData.getStationAddress());
        holder.bind(stationData, isSelected);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isActionModeActive) {
                    activity.startSupportActionMode((ActionMode.Callback) actionModeCallback);
                    toggleSelection(holder.getAdapterPosition());
                    return true;
                }
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActionModeActive) {
                    toggleSelection(holder.getAdapterPosition());
                } else {
                    // Handle item click in normal mode
                    Toast.makeText(activity, "Item clicked: " + stationData.getStationName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Change UI based on item selection
        holder.itemView.setActivated(selectedItems.contains(stationData));
    }

    // Implement ActionMode.Callback for multi-selection
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            isActionModeActive = true;
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.delete_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int id=item.getItemId();
            if (id == R.id.menu_delete) {
                // Delete selected items
                deleteSelectedItems();
                mode.finish();
                }
                return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionModeActive = false;
            selectedItems.clear();
            notifyDataSetChanged();
        }
    };

    private void toggleSelection(int position) {
        if (selectedItems.contains(stationDataList.get(position))) {
            selectedItems.remove(stationDataList.get(position));
        } else {
            selectedItems.add(stationDataList.get(position));
        }
        notifyItemChanged(position);
    }

    private void deleteSelectedItems() {
        stationDataList.removeAll(selectedItems);
        notifyDataSetChanged();
        // Perform delete operation on your data source or database
        // Notify the user about successful deletion
        Toast.makeText(activity, "Selected items deleted", Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount() {
        return stationDataList != null ? stationDataList.size() : 0; // Check if the list is null before accessing its size
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView stationNameTextView;
        TextView unitTextView , textView;
        ImageView checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stationNameTextView = itemView.findViewById(R.id.stationName);
            unitTextView = itemView.findViewById(R.id.stationKwh);
            textView = itemView.findViewById(R.id.tv_empty);
            checkBox = itemView.findViewById(R.id.check_box);
        }

        public void bind(StationData stationData, boolean isSelected) {
            stationNameTextView.setText(stationData.getStationAddress());
            unitTextView.setText(stationData.getStationUnits());

            // Change background color based on selection
            if (isSelected) {
                // Apply highlighted color
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.yellow));
            } else {
                // Apply default color
                itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }

    public ArrayList<StationData> getSelectedItems() {
        return selectedItems;
    }



    public void highlightItem(int position) {
        // Highlight the item at the specified position
        notifyItemChanged(position);
    }


}