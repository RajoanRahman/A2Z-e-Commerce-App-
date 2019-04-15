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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.atwoz.ModelClass.Users;
import com.example.user.atwoz.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button joinNow, alreadyAnAccount;
    LinearLayout layout;
    Animation anim1, anim2;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO:Loaf the Animation
        anim1 = AnimationUtils.loadAnimation(this, R.anim.animforlayout);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.animforbutton);

        loadingBar = new ProgressDialog(this);

        joinNow = findViewById(R.id.join_now_button);
        alreadyAnAccount = findViewById(R.id.alreadu_have_button);
        layout = findViewById(R.id.layout1);

        //TODO:Get the Animation
        layout.startAnimation(anim1);
        joinNow.startAnimation(anim2);
        alreadyAnAccount.startAnimation(anim2);

        joinNow.setOnClickListener(this);
        alreadyAnAccount.setOnClickListener(this);

        Paper.init(this);


        String user_PhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String user_PassKey = Paper.book().read(Prevalent.userPasswordKey);

        if (user_PassKey != "" && user_PhoneKey != "") {
            if (!TextUtils.isEmpty(user_PassKey) && !TextUtils.isEmpty(user_PhoneKey)) {
                allowAccess(user_PhoneKey, user_PassKey);

                loadingBar.setTitle("Already Logges in");
                loadingBar.setMessage("please wait.....");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);
            }
        }

    }

    private void allowAccess(final String phone_Number, final String user_password) {

        DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone_Number).exists()) {
                    //Pass the user value into Users class
                    Users userData = dataSnapshot.child("Users").child(phone_Number).getValue(Users.class);

                    if (userData.getPhoneNumber().equals(phone_Number)) {
                        if (userData.getPassword().equals(user_password)) {
                            Toast.makeText(MainActivity.this, "You are logged in successfully", Toast.LENGTH_SHORT).show();
                            gotoHome();
                            loadingBar.dismiss();


                        } else {
                            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Account with this " + phone_Number + " not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    //Toast.makeText(LogInActivity.this,"Create a new Account",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void gotoHome() {
        Intent newIntent=new Intent(MainActivity.this,HomeActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(newIntent);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.alreadu_have_button){

            gotoLoginPage();

        }if (v.getId()==R.id.join_now_button){
            gotoRegisterPage();
        }
    }

   public void gotoLoginPage(){
       Intent intent=new Intent(MainActivity.this,LogInActivity.class);
       startActivity(intent);
    }

    public void gotoRegisterPage(){
        Intent intent1=new Intent(MainActivity.this,RegisterActivtiy.class);
        startActivity(intent1);
    }
}
