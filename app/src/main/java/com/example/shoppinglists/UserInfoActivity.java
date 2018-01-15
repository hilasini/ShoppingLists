package com.example.shoppinglists;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import org.w3c.dom.Text;

public class UserInfoActivity extends AppCompatActivity {

    static TextView txtfirstname;
    static TextView txtlastname;
    static TextView txtcity;
    static TextView txtstreet;
    static TextView txtpassword;
    static TextView txtemail;

    private Firebase FirstNameRef;
    private Firebase LastNameRef;
    private Firebase CityRef;
    private Firebase StreetRef;
    private Firebase PasswordRef;
    private Firebase EmailRef;

    private FirebaseDatabase mFirebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    String userId;

    private ImageView inputPhoto;
    private StorageReference mStorageRef;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Firebase.setAndroidContext(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //////////////////
        txtfirstname = (TextView) findViewById(R.id.userfirstnametextView);
        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=mFirebaseDatabase.getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        firebaseUser=mAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        // Toast.makeText(this, userId,Toast.LENGTH_LONG).show();



        FirstNameRef=new Firebase("https://shoppinglists-7f8a8.firebaseio.com/User/"+userId+"/firstname");
        FirstNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue(String.class);
                UserInfoActivity.txtfirstname.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        txtlastname = (TextView) findViewById(R.id.userlastnametextView);
        LastNameRef=new Firebase("https://shoppinglists-7f8a8.firebaseio.com/User/"+userId+"/lastname");
        LastNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue(String.class);
                UserInfoActivity.txtlastname.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        txtcity = (TextView) findViewById(R.id.citytextView);
        CityRef=new Firebase("https://shoppinglists-7f8a8.firebaseio.com/User/"+userId+"/city");
        CityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue(String.class);
                UserInfoActivity.txtcity.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        txtstreet = (TextView) findViewById(R.id.streettextView);
        StreetRef=new Firebase("https://shoppinglists-7f8a8.firebaseio.com/User/"+userId+"/street");
        StreetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue(String.class);
                UserInfoActivity.txtstreet.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        txtpassword = (TextView) findViewById(R.id.passwordtextView);
        PasswordRef=new Firebase("https://shoppinglists-7f8a8.firebaseio.com/User/"+userId+"/password");
        PasswordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue(String.class);
                UserInfoActivity.txtpassword.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        txtemail = (TextView) findViewById(R.id.emailtextView);
        EmailRef=new Firebase("https://shoppinglists-7f8a8.firebaseio.com/User/"+userId+"/email");
        EmailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue(String.class);
                UserInfoActivity.txtemail.setText(value);
                inputPhoto=(ImageView)findViewById(R.id.imgButton);
                StorageReference image=mStorageRef.child("images/"+ txtemail.getText().toString());
                Glide.with(UserInfoActivity.this)
                        .using(new FirebaseImageLoader())
                        .load(image).into(inputPhoto);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private  void showImage()
    {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater infalter=getMenuInflater();
        infalter.inflate(R.menu.main_menu, menu);

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.Login:
                Intent intent1 = new Intent(this, LoginActivity.class);
                this.startActivity(intent1);
                return true;

            case R.id.Maps:
                Intent intent2 = new Intent(this, MapsActivity.class);
                this.startActivity(intent2);
                return true;

            case R.id.Main:
                Intent intent3 = new Intent(this, MainActivity.class);
                this.startActivity(intent3);
                return true;

            case R.id.UserInfo:
                Intent intent4 = new Intent(this, UserInfoActivity.class);
                this.startActivity(intent4);
                return true;

            case R.id.Products:
                Intent intent5 = new Intent(this, ProductsActivity.class);
                this.startActivity(intent5);
                return true;

            case R.id.ProductsInList:
                Intent intent6 = new Intent(this, ProductsInListActivity.class);
                this.startActivity(intent6);
                return true;

            case R.id.UsersLists:
                Intent intent7 = new Intent(this, UsersListsActivity.class);
                this.startActivity(intent7);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }



}