package com.example.shoppinglists;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity
{

    DatabaseReference rootRef,demoRef;

    Button b;
    private static final int CAMERA_PIC_REQUEST = 22;
    private ImageView ImgPhoto;
    private Bitmap bitmap;
    private Intent intent;
    public static Uri uri;

    private EditText editTextUsername;
    private EditText editTextFirstname;
    private EditText editTextLastname;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextCity;
    private EditText editTextStreet;
    private String photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.CAMERA  },22 );
        }
        setContentView(R.layout.activity_register);
        b=(Button)findViewById(R.id.Photobutton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        demoRef = rootRef.child("User");


        editTextUsername=(EditText) findViewById(R.id.usernicknameText);
        editTextFirstname=(EditText) findViewById(R.id.userfirstnameText);
        editTextLastname=(EditText) findViewById(R.id.userlastnameText);
        editTextEmail=(EditText) findViewById(R.id.emaileditText);
        editTextPassword=(EditText) findViewById(R.id.passwordText);
        editTextCity=(EditText) findViewById(R.id.cityText);
        editTextStreet=(EditText) findViewById(R.id.streetText);
    }


    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    ImgPhoto = (ImageView) findViewById(R.id.imageButton);

                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    intent = new Intent();
                    // Show only images, no videos or anything else
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    // Always show the chooser (if there are multiple options available)
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            try {

            uri = data.getData();

            String ext=getContentResolver().getType(uri);
            ext=ext.substring(ext.lastIndexOf("/")+ 1);

            if(ext.equals("jpeg") || ext.equals("png"))
            {
                ((ImageButton) findViewById(R.id.imageButton)).setImageURI(uri);

                if(ext.equals("png")) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    photo = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                }

                if(ext.equals("jpeg")) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    photo = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                }
            }
            else
            {
                Toast.makeText(this, "please insert a .jpg or .png", Toast.LENGTH_LONG).show();
            }


            } catch (Exception e) {
                Toast.makeText(this, "Couldn't load photo", Toast.LENGTH_LONG).show();
            }
        }
        else if (resultCode == RESULT_OK) {
            try {
                bitmap = (Bitmap) data.getExtras().get("data");

                ImgPhoto.setImageBitmap(bitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                photo = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

            } catch (Exception e) {
                Toast.makeText(this, "Couldn't load photo", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void registerUser()
    {
        //getting email and password from edit texts
        String username = editTextUsername.getText().toString().trim();
        String firstname = editTextFirstname.getText().toString().trim();
        String lastname = editTextLastname.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String street = editTextStreet.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();



        //checking if email and passwords are empty
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter user name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(firstname)){
            Toast.makeText(this,"Please enter first name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(lastname)){
            Toast.makeText(this,"Please enter last name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(city)){
            Toast.makeText(this,"Please enter a city",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(street)){
            Toast.makeText(this,"Please enter a street",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        List<ShopList> myShopList=new ArrayList<ShopList>();

        User user=new User(username,password,firstname,lastname,email,city,street,photo,"user",myShopList);
        demoRef.child(username).setValue(user);

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void SendClicked(View view) {
        //calling register method on click
        registerUser();
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }





}
