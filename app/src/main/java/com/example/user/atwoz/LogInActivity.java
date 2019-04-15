package com.example.user.atwoz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.atwoz.Category.AdminCategoryActivity;
import com.example.user.atwoz.ModelClass.Users;
import com.example.user.atwoz.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import org.w3c.dom.Text;

import javax.security.auth.login.LoginException;

import io.paperdb.Paper;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText phnNumberEditText,passwordEditText;
    TextView adminPanel,nonAdminPanel;
    Button logInButton;
    Animation anim1,anim2;
    ProgressDialog loadingBar;
    DatabaseReference rootRef;
    String parentDBName="Users";
    CheckBox checkBoxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loadingBar=new ProgressDialog(this);
        anim2=AnimationUtils.loadAnimation(this,R.anim.animforbuttontwo);


        rootRef=FirebaseDatabase.getInstance().getReference();

        logInButton=findViewById(R.id.logIn_button);
        phnNumberEditText=findViewById(R.id.phn_number_login_edit);
        passwordEditText=findViewById(R.id.password_login_edit);
        checkBoxRememberMe=findViewById(R.id.logIn_checkBox);
        adminPanel=findViewById(R.id.admin_panel_text);
        nonAdminPanel=findViewById(R.id.non_admin_panel_text);
        Paper.init(this);

        phnNumberEditText.startAnimation(anim2);
        passwordEditText.startAnimation(anim2);

        logInButton.setOnClickListener(this);


        adminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInButton.setText("Admin LogIn");
                adminPanel.setVisibility(View.INVISIBLE);
                nonAdminPanel.setVisibility(View.VISIBLE);
                parentDBName="Admins";
            }
        });

        nonAdminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInButton.setText("Log In");
                adminPanel.setVisibility(View.VISIBLE);
                nonAdminPanel.setVisibility(View.INVISIBLE);
                parentDBName="Users";
            }
        });
    }





    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.logIn_button){

            logInUser();

        }
    }


    private void logInUser() {

        String phone_Number=phnNumberEditText.getText().toString();
        String user_password=passwordEditText.getText().toString();

        if (TextUtils.isEmpty(phone_Number)){
            Toast.makeText(LogInActivity.this,"Insert your phone number",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(user_password)){
            Toast.makeText(LogInActivity.this,"Insert your password number",Toast.LENGTH_SHORT).show();
        }else {

            loadingBar.setTitle("LogIn Account");
            loadingBar.setMessage("Wait,while we are login into your account");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            alloUsertoAccount(phone_Number,user_password);
        }

    }

    private void alloUsertoAccount(final String phone_Number, final String user_password) {


        if(checkBoxRememberMe.isChecked()){

            Paper.book().write(Prevalent.userPhoneKey,phone_Number);
            Paper.book().write(Prevalent.userPasswordKey,user_password);
        }

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(parentDBName).child(phone_Number).exists()){
                        //Pass the user value into Users class
                        Users userData=dataSnapshot.child(parentDBName).child(phone_Number).getValue(Users.class);

                        if(userData.getPhoneNumber().equals(phone_Number)){
                            if (userData.getPassword().equals(user_password)){
                               if (parentDBName.equals("Admins")){
                                   Toast.makeText(LogInActivity.this,"Welcome to admin panel",Toast.LENGTH_SHORT).show();

                                   loadingBar.dismiss();
                                   gotoAdmin();

                               }else if (parentDBName.equals("Users")){

                                   Toast.makeText(LogInActivity.this,"You are logged in successfully",Toast.LENGTH_SHORT).show();

                                   loadingBar.dismiss();
                                   gotoHome();
                               }

                            }else {
                                Toast.makeText(LogInActivity.this,"Password is incorrect",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    }else {
                        Toast.makeText(LogInActivity.this,"Account with this " + phone_Number + " not exist",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        //Toast.makeText(LogInActivity.this,"Create a new Account",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }

    private void gotoAdmin() {
        Intent adminIntent=new Intent(LogInActivity.this,AdminCategoryActivity.class);
        startActivity(adminIntent);

    }

    private void gotoHome() {

        Intent newIntent=new Intent(LogInActivity.this,HomeActivity.class);
        startActivity(newIntent);
    }


    //Intent intent=new Intent(LogInActivity.this,RegisterActivtiy.class);
    //startActivity(intent);
}
