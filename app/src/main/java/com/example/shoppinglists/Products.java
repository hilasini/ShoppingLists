package com.example.shoppinglists;

public class Products {

    private String ProductID;
    private String ProductName;
    private double ProductPrice;

    public Products(String productID, String productName, double productPrice) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }
}
