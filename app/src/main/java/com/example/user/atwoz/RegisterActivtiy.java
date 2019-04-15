package com.example.user.atwoz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivtiy extends AppCompatActivity {

    EditText name, phone_number,password,confirmPass;
    Button createAccount;
    Animation anim;
    ProgressDialog loadingBar;
    DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activtiy);

        loadingBar=new ProgressDialog(this);



        anim=AnimationUtils.loadAnimation(this,R.anim.animforbuttontwo);

        rootRef=FirebaseDatabase.getInstance().getReference();

        name=findViewById(R.id.user_name_register_edit);
        phone_number=findViewById(R.id.phn_number_register_edit);
        password=findViewById(R.id.password_register_edit);
        confirmPass=findViewById(R.id.confirm_password_register_edit);
        createAccount=findViewById(R.id.create_account_button);

        createAccount.startAnimation(anim);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String userName=name.getText().toString();
        String phoneNumber=phone_number.getText().toString();
        String password=confirmPass.getText().toString();
        String confirmPassword=confirmPass.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(RegisterActivtiy.this,"Insert Your Name",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(RegisterActivtiy.this,"Insert Your Phone Number",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivtiy.this,"Insert Your Password",Toast.LENGTH_SHORT).show();
        }else if (!password.equals(confirmPassword)){
            Toast.makeText(RegisterActivtiy.this,"Password does not match",Toast.LENGTH_SHORT).show();
        }else {
            validateNumber(userName,phoneNumber,password);

        }
    }

    private void validateNumber(final String userName, final String phoneNumber, final String password) {
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phoneNumber).exists())){

                    HashMap<String,Object> newMap=new HashMap<>();
                    newMap.put("name",userName);
                    newMap.put("phoneNumber",phoneNumber);
                    newMap.put("password",password);

                    //TODO: phone,pass and user name will be stored under parent node-Users and SubNode-given phoneNumber
                    rootRef.child("Users").child(phoneNumber).updateChildren(newMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivtiy.this,"Account is created successfully",Toast.LENGTH_SHORT).show();
                                        gotoLogin();
                                    }else {
                                        String message=task.getException().toString();
                                        Toast.makeText(RegisterActivtiy.this,"Error:"+message,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else {
                    Toast.makeText(RegisterActivtiy.this,"An account has been created by this " + phoneNumber + " number",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void gotoLogin() {
        Intent intent=new Intent(RegisterActivtiy.this,LogInActivity.class);
        startActivity(intent);
    }


}
