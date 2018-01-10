package com.example.shoppinglists;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.FirebaseAppHelper;

import java.util.ArrayList;


public class ProductsActivity extends AppCompatActivity {

    private ChildEventListener mProductsListener;

    private DatabaseReference rootRef, ProductsRef;
    private FirebaseAppHelper helper;
    private ArrayList<Products> ProductList;
    private FirebaseAuth mAuth;

    private GridView gridView;
    private Button AddNewProduct;
    private Firebase status;
    private String Userstatus;

    // Create a new ArrayAdapter
    ArrayAdapter<Products> gridViewArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        gridView=(GridView) findViewById(R.id.gridviewProducts);
        AddNewProduct=(Button) findViewById(R.id.AddNewProduct);

        rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        ProductsRef = rootRef.child("Products");
        helper=new FirebaseAppHelper();
        mAuth = FirebaseAuth.getInstance();

        ProductList = new ArrayList<Products>();

        gridViewArrayAdapter = new ArrayAdapter<Products>(this,android.R.layout.simple_list_item_1, ProductList);
        // Data bind GridView with ArrayAdapter (String Array elements)
        gridView.setAdapter(gridViewArrayAdapter);


        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid= currentUser.getUid();

        status=new Firebase("https://shoppinglists-7f8a8.firebaseio.com/User/"+uid+"/status");
        Userstatus= "";
        status.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Userstatus= dataSnapshot.getValue(String.class);
                Manager();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

    }

    private void Manager() {
        if(Userstatus.contains("manager"))
        {
            AddNewProduct.setVisibility(View.VISIBLE);
            AddNewProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(view.getContext(), AddNewProductActivity.class);
                    view.getContext().startActivity(intent1);
                }
            });
        }
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
                Toast.makeText(ProductsActivity.this, "onChildChanged: " + pro.getProductName(), Toast.LENGTH_SHORT).show();

                // Update the GridView
                gridViewArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e("ProductsActivity", "onChildRemoved:" + dataSnapshot.getKey());

                // A message has been removed
                Products pro = dataSnapshot.getValue(Products.class);
                Toast.makeText(ProductsActivity.this, "onChildRemoved: " + pro.getProductName(), Toast.LENGTH_SHORT).show();

                // Update the GridView
                gridViewArrayAdapter.notifyDataSetChanged();
            }


            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.e("ProductsActivity", "onChildMoved:" + dataSnapshot.getKey());

                // A message has changed position
                Products pro = dataSnapshot.getValue(Products.class);
                Toast.makeText(ProductsActivity.this, "onChildMoved: " + pro.getProductName(), Toast.LENGTH_SHORT).show();

                // Update the GridView
                gridViewArrayAdapter.notifyDataSetChanged();
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
