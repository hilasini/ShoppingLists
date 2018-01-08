package com.example.shoppinglists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ProductsActivity extends AppCompatActivity {

    private ChildEventListener mProductsListener;


    DatabaseReference rootRef, ProductsRef;
    ArrayList<Products> ProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        ProductsRef = rootRef.child("Products");

        ProductList = new ArrayList<Products>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // A new message has been added
                // onChildAdded() will be called for each node at the first time
                Products pro = dataSnapshot.getValue(Products.class);
                ProductList.add(pro);

                Log.e("ProductsActivity", "onChildAdded:" + pro.getProductName());

                Products prod = ProductList.get(ProductList.size() - 1);
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("ProductsActivity", "onChildChanged:" + dataSnapshot.getKey());

                // A message has changed
                Products pro = dataSnapshot.getValue(Products.class);
                Toast.makeText(ProductsActivity.this, "onChildChanged: " + pro.getProductName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("ProductsActivity", "onChildRemoved:" + dataSnapshot.getKey());

                // A message has been removed
                Products pro = dataSnapshot.getValue(Products.class);
                Toast.makeText(ProductsActivity.this, "onChildRemoved: " + pro.getProductName(), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("ProductsActivity", "onChildMoved:" + dataSnapshot.getKey());

                // A message has changed position
                Products pro = dataSnapshot.getValue(Products.class);
                Toast.makeText(ProductsActivity.this, "onChildMoved: " + pro.getProductName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ProductsActivity", "postMessages:onCancelled", databaseError.toException());
                Toast.makeText(ProductsActivity.this, "Failed to load Message.", Toast.LENGTH_SHORT).show();
            }
        };


        ProductsRef.addChildEventListener(childEventListener);

        // copy for removing at onStop()
        mProductsListener = childEventListener;
    }



}
