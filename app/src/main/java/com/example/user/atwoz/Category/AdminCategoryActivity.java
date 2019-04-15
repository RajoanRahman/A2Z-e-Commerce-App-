package com.example.user.atwoz.Category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.user.atwoz.AdminAddNewProductActivity;
import com.example.user.atwoz.R;

public class AdminCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView tShirts,sportShirts,femaleDress,sweater;
    ImageView glasses,purseBag,hats,shoes;
    ImageView headphones,laptops,watches,mobiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tShirts=findViewById(R.id.t_shirt);
        sportShirts=findViewById(R.id.sports);
        femaleDress=findViewById(R.id.femaleDress);
        sweater=findViewById(R.id.sweater);
        glasses=findViewById(R.id.glasses);
        purseBag=findViewById(R.id.purse_bag);
        hats=findViewById(R.id.hats);
        shoes=findViewById(R.id.shoes);
        headphones=findViewById(R.id.headphones);
        laptops=findViewById(R.id.laptops);
        watches=findViewById(R.id.watches);
        mobiles=findViewById(R.id.mobiles);


        tShirts.setOnClickListener(this);
        sportShirts.setOnClickListener(this);
        femaleDress.setOnClickListener(this);
        sweater.setOnClickListener(this);
        glasses.setOnClickListener(this);
        purseBag.setOnClickListener(this);
        hats.setOnClickListener(this);
        shoes.setOnClickListener(this);
        headphones.setOnClickListener(this);
        laptops.setOnClickListener(this);
        watches.setOnClickListener(this);
        mobiles.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.t_shirt){
            Intent intent1=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent1.putExtra("category","tShirts");
            startActivity(intent1);
        }else if (v.getId()==R.id.sports){
            Intent intent2=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent2.putExtra("category","sportsTShirts");
            startActivity(intent2);
        }else if (v.getId()==R.id.femaleDress){
            Intent intent3=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent3.putExtra("category","femaleDress");
            startActivity(intent3);
        }else if (v.getId()==R.id.sweater){
            Intent intent4=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent4.putExtra("category","sweater");
            startActivity(intent4);
        }else if (v.getId()==R.id.glasses){
            Intent intent5=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent5.putExtra("category","glasses");
            startActivity(intent5);
        }else if (v.getId()==R.id.purse_bag){
            Intent intent6=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent6.putExtra("category","purseBag");
            startActivity(intent6);
        }else if (v.getId()==R.id.hats){
            Intent intent7=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent7.putExtra("category","hats");
            startActivity(intent7);
        }else if (v.getId()==R.id.shoes){
            Intent intent8=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent8.putExtra("category","shoes");
            startActivity(intent8);
        }else if (v.getId()==R.id.headphones){
            Intent intent9=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent9.putExtra("category","headPhones");
            startActivity(intent9);
        }else if (v.getId()==R.id.laptops){
            Intent intent10=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent10.putExtra("category","laptops");
            startActivity(intent10);
        }else if (v.getId()==R.id.watches){
            Intent intent11=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent11.putExtra("category","watches");
            startActivity(intent11);
        }else if (v.getId()==R.id.mobiles){
            Intent intent12=new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
            intent12.putExtra("category","mobiles");
            startActivity(intent12);
        }
    }
}
