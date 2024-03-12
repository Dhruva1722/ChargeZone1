package com.example.chargezone1.Model;

public class ChargingStation {

    private String name;
    private String kWhValue;

    private boolean isSelected;

    public ChargingStation(String name, String kWhValue) {
        this.name = name;
        this.kWhValue = kWhValue;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public String getKWhValue() {
        return kWhValue;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
