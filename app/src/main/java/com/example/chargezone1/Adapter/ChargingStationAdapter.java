    package com.example.chargezone1.Adapter;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.CheckBox;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.chargezone1.Model.ChargingStation;
    import com.example.chargezone1.R;
    import com.example.chargezone1.UserActivity.FirstStationActivity;

    import java.util.List;

    public class ChargingStationAdapter extends RecyclerView.Adapter<ChargingStationAdapter.ViewHolder> {
        private  List<ChargingStation> chargingStationList;
        private boolean isDeleteModeEnabled = false;

        public interface DeleteModeListener {
            void onDeleteModeChanged(boolean isDeleteModeEnabled);
        }

        private DeleteModeListener deleteModeListener;

        public void setDeleteModeEnabled(boolean enabled) {
            isDeleteModeEnabled = enabled;
        }
        public ChargingStationAdapter(List<ChargingStation> chargingStationList ,DeleteModeListener deleteModeListener  ) {
            this.chargingStationList = chargingStationList;
            this.deleteModeListener = deleteModeListener;
        }

        public ChargingStation getItem(int position) {
            return chargingStationList.get(position);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_charging_station, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ChargingStation chargingStation = chargingStationList.get(position);
            holder.stationName.setText(chargingStation.getName());
            holder.kwhValue.setText(chargingStation.getKWhValue());

            holder.checkBox.setVisibility(isDeleteModeEnabled ? View.VISIBLE : View.GONE);
            holder.checkBox.setChecked(chargingStation.isSelected());

            // Set OnClickListener for CheckBox
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle checkbox state change
                    chargingStation.setSelected(holder.checkBox.isChecked());
                }
            });
        }

        @Override
        public int getItemCount() {
            return chargingStationList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView stationName;
            TextView kwhValue;
            CheckBox checkBox;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                stationName = itemView.findViewById(R.id.stationName);
                kwhValue = itemView.findViewById(R.id.stationKwh);

                itemView.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ChargingStation clickedStation = chargingStationList.get(position);
                    // Perform different operations based on the clicked station
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(itemView.getContext(), FirstStationActivity.class);
                            itemView.getContext().startActivity(intent);
                            break;
                        case 1:
                            Intent intent1 = new Intent(itemView.getContext(), FirstStationActivity.class);
                            itemView.getContext().startActivity(intent1);
                            break;
                        default:
                            Toast.makeText(itemView.getContext(), "Clicked: " + clickedStation.getName(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        }
    }