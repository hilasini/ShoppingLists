package com.example.shoppinglists;

import android.*;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.Manifest.permission.READ_CONTACTS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // Creating EditText.
    EditText email, password ;

    // Creating string to hold values.
    String EmailHolder, PasswordHolder;

    // Creating buttons.
    Button Login,SignUP ;

    // Creating Boolean to hold EditText empty true false value.
    Boolean EditTextEmptyCheck;

    // Creating progress dialog.
    ProgressDialog progressDialog;

    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Assign ID's to EditText.
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        // Assign ID's to button.
        Login = (Button)findViewById(R.id.sign_in_button);
        SignUP = (Button)findViewById(R.id.registerButton);

        progressDialog =  new ProgressDialog(LoginActivity.this);

        // Assign FirebaseAuth instance to FirebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();


        // Checking if user already logged in before and not logged out properly.


        // Adding click listener to login button.
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method CheckEditTextIsEmptyOrNot().
                CheckEditTextIsEmptyOrNot();

                // If  EditTextEmptyCheck == true
                if(EditTextEmptyCheck)
                {

                    // If  EditTextEmptyCheck == true then login function called.
                    LoginFunction();

                }
                else {

                    // If  EditTextEmptyCheck == false then toast display on screen.
                    Toast.makeText(LoginActivity.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
                }


            }
        });

        // Adding click listener to Sign up button.
        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Closing current activity.
                finish();

                // Opening the Main Activity .
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    // Creating method to check EditText is empty or not.
    public void CheckEditTextIsEmptyOrNot(){

        // Getting value form Email's EditText and fill into EmailHolder string variable.
        EmailHolder = email.getText().toString().trim();

        // Getting value form Password's EditText and fill into PasswordHolder string variable.
        PasswordHolder = password.getText().toString().trim();

        // Checking Both EditText is empty or not.
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            // If any of EditText is empty then set value as false.
            EditTextEmptyCheck = false;

        }
        else {

            // If any of EditText is empty then set value as true.
            EditTextEmptyCheck = true ;

        }

    }

    // Creating login function.
    public void LoginFunction(){

        // Setting up message in progressDialog.
        progressDialog.setMessage("Please Wait");

        // Showing progressDialog.
        progressDialog.show();

        // Calling  signInWithEmailAndPassword function with firebase object and passing EmailHolder and PasswordHolder inside it.
        firebaseAuth.signInWithEmailAndPassword(EmailHolder, PasswordHolder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If task done Successful.
                        if(task.isSuccessful()){

                            // Hiding the progress dialog.
                            progressDialog.dismiss();

                            // Closing the current Login Activity.
                            finish();


                            // Opening the UserProfileActivity.
                            Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Hiding the progress dialog.
                            progressDialog.dismiss();

                            // Showing toast message when email or password not found in Firebase Online database.
                            Toast.makeText(LoginActivity.this, "Email or Password Not found, Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

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

