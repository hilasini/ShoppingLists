package com.example.shoppinglists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;

public class AddNewProductActivity extends AppCompatActivity {

    private EditText ProductID;
    private EditText ProductName;
    private EditText ProductPrice;
    private Button Confirm;


    private Firebase mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        ProductID=(EditText)findViewById(R.id.proID);
        ProductName=(EditText)findViewById(R.id.name);
        ProductPrice=(EditText)findViewById(R.id.price);
        Confirm=(Button) findViewById(R.id.send);


        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }

        });

        mRootRef=new Firebase("https://shoppinglists-7f8a8.firebaseio.com/Products");
    }

    public void saveProduct()
    {
        if(ValidInput())
        {
            double price=Double.parseDouble(ProductPrice.toString());
            Products product=new Products(ProductID.toString(),ProductName.toString(),price);
            mRootRef.child(ProductID.toString()).setValue(product);
        }
    }

    public boolean ValidInput()
    {
        if (ProductID.toString().isEmpty()||ProductID.toString().length()>32){
            ProductID.setError("Please enter vaild ID");
            return false;
        }
        if (ProductName.toString().isEmpty()||ProductName.toString().length()>32){
            ProductName.setError("Please enter vaild name");
            return false;
        }
        if (ProductPrice.toString().isEmpty()||ProductPrice.toString().length()>32){
            try
            {
                Double.parseDouble(ProductPrice.toString());
            }
            catch(NumberFormatException e)
            {
                ProductPrice.setError("Please enter vaild double");
                return false;
            }
            ProductPrice.setError("Please enter vaild price");
            return false;
        }
        return true;
    }


}
