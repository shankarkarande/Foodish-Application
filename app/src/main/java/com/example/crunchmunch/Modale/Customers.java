package com.example.crunchmunch.Modale;

public class Customers
{

    public String name;
    public String phone;
    public String amount;
    public String date;

    public Customers() {
    }

    public Customers(String name, String phone, String amount, String date) {
        this.name = name;
        this.phone = phone;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
