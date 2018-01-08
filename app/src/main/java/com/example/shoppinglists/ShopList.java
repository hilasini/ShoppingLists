package com.example.shoppinglists;

import java.util.Hashtable;


public class ShopList {

    private String ListID;
    private double TotalPrice;
    private int TotalUnits;
    private Hashtable<Products, Double> MyProducts;
    private String ListName;

    public ShopList(String listID, double totalPrice, int totalUnits, Hashtable<Products, Double> myProducts, String listName) {
        ListID = listID;
        TotalPrice = totalPrice;
        TotalUnits = totalUnits;
        MyProducts = myProducts;
        ListName = listName;
    }

    public ShopList()
    {
        ListID = "";
        TotalPrice = 0.00;
        TotalUnits = 0;
        MyProducts = new Hashtable<Products, Double>();
        ListName = "new list";
    }

    public String getListID() {
        return ListID;
    }

    public void setListID(String listID) {
        ListID = listID;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getTotalUnits() {
        return TotalUnits;
    }

    public void setTotalUnits(int totalUnits) {
        TotalUnits = totalUnits;
    }

    public Hashtable<Products, Double> getMyProducts() {
        return MyProducts;
    }

    public void setMyProducts(Hashtable<Products, Double> myProducts) {
        MyProducts = myProducts;
    }

    public String getListName() {
        return ListName;
    }

    public void setListName(String listName) {
        ListName = listName;
    }
}
