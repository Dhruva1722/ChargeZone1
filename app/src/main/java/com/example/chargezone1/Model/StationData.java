package com.example.chargezone1.Model;

public class StationData {

    private String stationName;
    private String stationUnits;
    private String stationAddress;
    private String stationPhoneNo;
    private String stationEmail;

    private int stationValue;

    public StationData(String stationName, String stationUnits, String stationAddress, String stationPhoneNo, String stationEmail) {
        this.stationName = stationName;
        this.stationUnits = stationUnits;
        this.stationAddress = stationAddress;
        this.stationPhoneNo = stationPhoneNo;
        this.stationEmail = stationEmail;
        this.stationValue = stationValue;
    }

    // Getter and setter methods for each field (optional)

    public int getStationValue() {
        return stationValue;
    }

    public void setStationValue(int stationValue) {
        this.stationValue = stationValue;
    }
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationUnits() {
        return stationUnits;
    }

    public void setStationUnits(String stationUnits) {
        this.stationUnits = stationUnits;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getStationPhoneNo() {
        return stationPhoneNo;
    }

    public void setStationPhoneNo(String stationPhoneNo) {
        this.stationPhoneNo = stationPhoneNo;
    }

    public String getStationEmail() {
        return stationEmail;
    }

    public void setStationEmail(String stationEmail) {
        this.stationEmail = stationEmail;
    }


}