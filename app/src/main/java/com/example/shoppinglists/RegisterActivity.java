package com.example.shoppinglists;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity
{
    Button b;
    private static final int CAMERA_PIC_REQUEST = 22;
    private ImageView ImgPhoto;
    private Bitmap bitmap;
    private Intent intent;
    public static Uri uri;

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

            } catch (Exception e) {
                Toast.makeText(this, "Couldn't load photo", Toast.LENGTH_LONG).show();
            }
        }
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
