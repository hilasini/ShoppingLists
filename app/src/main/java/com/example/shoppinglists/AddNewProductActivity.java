package com.example.shoppinglists;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNewProductActivity extends AppCompatActivity {

    private EditText ProductID;
    private EditText ProductName;
    private EditText ProductPrice;

    public static Uri uri;
    private Button Confirm;


    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        ProductID = (EditText) findViewById(R.id.proID);
        ProductName = (EditText) findViewById(R.id.name);
        ProductPrice = (EditText) findViewById(R.id.price);
        Confirm = (Button) findViewById(R.id.send);

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }

        });
        ref = FirebaseDatabase.getInstance().getReference().child("Products");
    }

    public void saveProduct() {
        if (ValidInput()) {
            double price = Double.parseDouble(ProductPrice.getText().toString());
            Products product = new Products(ProductID.getText().toString(), ProductName.getText().toString(), price);
            ref.child(ProductID.getText().toString()).setValue(product);
            Intent intent1 = new Intent(this, ProductsActivity.class);
            this.startActivity(intent1);
        }
    }

    public boolean ValidInput() {
        String id = ProductID.getText().toString();
        String name = ProductName.getText().toString();
        String price = ProductPrice.getText().toString();
        if (id.isEmpty() || id.length() > 32) {
            ProductID.setError("Please enter vaild ID " + id);
            return false;
        }
        if (name.isEmpty() || name.length() > 32) {
            ProductName.setError("Please enter vaild name");
            return false;
        }
        if (price.isEmpty() || price.length() > 32) {
            try {
                Double.parseDouble(price);
            } catch (NumberFormatException e) {
                ProductPrice.setError("Please enter vaild double");
                return false;
            }
            ProductPrice.setError("Please enter vaild price");
            return false;
        }
        return true;
    }

}
