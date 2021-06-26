package com.example.blooddonor;

public class Donor {

    private String name;
    private String number;
    private String address;
    private String blood;

    public Donor()
    {

    }

    public Donor(String name, String number, String address, String blood) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.blood = blood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}
