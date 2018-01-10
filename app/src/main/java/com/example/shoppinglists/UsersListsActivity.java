package com.example.shoppinglists;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.FirebaseAppHelper;

import java.util.ArrayList;

public class UsersListsActivity extends AppCompatActivity {

    private ChildEventListener mProductsListener;

    private DatabaseReference rootRef, ProductsRef;
    private FirebaseAppHelper helper;
    private ArrayList<Products> ProductList;
    private GridView gridView;

    // Create a new ArrayAdapter
    ArrayAdapter<Products> gridViewArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        gridView=(GridView) findViewById(R.id.gridviewProducts);

        rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        ProductsRef = rootRef.child("Users");
        helper=new FirebaseAppHelper();

        ProductList = new ArrayList<Products>();

        gridViewArrayAdapter = new ArrayAdapter<Products>(this,android.R.layout.simple_list_item_1, ProductList);
        // Data bind GridView with ArrayAdapter (String Array elements)
        gridView.setAdapter(gridViewArrayAdapter);

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
                // Update the GridView
                gridViewArrayAdapter.notifyDataSetChanged();

            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("ProductsActivity", "onChildChanged:" + dataSnapshot.getKey());

                // A message has changed
                Products pro = dataSnapshot.getValue(Products.class);
                Toast.makeText(UsersListsActivity.this, "onChildChanged: " + pro.getProductName(), Toast.LENGTH_SHORT).show();

                // Update the GridView
                gridViewArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("ProductsActivity", "onChildRemoved:" + dataSnapshot.getKey());

                // A message has been removed
                Products pro = dataSnapshot.getValue(Products.class);
                Toast.makeText(UsersListsActivity.this, "onChildRemoved: " + pro.getProductName(), Toast.LENGTH_SHORT).show();

                // Update the GridView
                gridViewArrayAdapter.notifyDataSetChanged();
            }


            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("ProductsActivity", "onChildMoved:" + dataSnapshot.getKey());

                // A message has changed position
                Products pro = dataSnapshot.getValue(Products.class);
                Toast.makeText(UsersListsActivity.this, "onChildMoved: " + pro.getProductName(), Toast.LENGTH_SHORT).show();

                // Update the GridView
                gridViewArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ProductsActivity", "postMessages:onCancelled", databaseError.toException());
                Toast.makeText(UsersListsActivity.this, "Failed to load Message.", Toast.LENGTH_SHORT).show();
            }
        };

        ProductsRef.addChildEventListener(childEventListener);

        // copy for removing at onStop()
        mProductsListener = childEventListener;
    }



}
