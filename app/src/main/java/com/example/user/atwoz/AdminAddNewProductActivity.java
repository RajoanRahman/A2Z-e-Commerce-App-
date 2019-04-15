package com.example.user.atwoz;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    String categoryName;
    ImageView selectImage;
    EditText productNameEdit,productDescription,productPrice;
    Button addNewProductButton;
    final static int galleryPick=1;
    private Uri imageUri;
    String saveCurrentDate,saveCurrentTime,productRandomKey,downloadIamgeUri;
    String description,name,price;
    StorageReference productImageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        categoryName=getIntent().getExtras().get("category").toString();

        productImageRef=FirebaseStorage.getInstance().getReference().child("Product Image");

        //Toast.makeText(getApplicationContext(),categoryName,Toast.LENGTH_SHORT).show();

        selectImage=findViewById(R.id.select_product_image);
        productNameEdit=findViewById(R.id.product_name);
        productDescription=findViewById(R.id.product_description);
        productPrice=findViewById(R.id.product_price);

        addNewProductButton=findViewById(R.id.add_product_button);

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void validateProductData() {
        description=productDescription.getText().toString();
        name=productNameEdit.getText().toString();
        price=productPrice.getText().toString();


        if (TextUtils.isEmpty(description)){
            Toast.makeText(AdminAddNewProductActivity.this,"Insert Product Description",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(name)){
            Toast.makeText(AdminAddNewProductActivity.this,"Insert Product Name",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(price)) {
            Toast.makeText(AdminAddNewProductActivity.this,"Insert Product Price",Toast.LENGTH_SHORT).show();
        }else if (imageUri==null){
            Toast.makeText(AdminAddNewProductActivity.this,"Upload product image",Toast.LENGTH_SHORT).show();
        }else {
            storeProductData();
        }
    }

    //TODO:Store all products into Firebase DB
    private void storeProductData() {

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomKey=saveCurrentDate+saveCurrentTime;

        //The link of the product image
        final StorageReference filePath=productImageRef.child(imageUri.getLastPathSegment()+productRandomKey+".jpg");

        final UploadTask uploadTask=filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error=e.toString();
                Toast.makeText(AdminAddNewProductActivity.this,"Error"+error,Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this,"Image Uploaded successfully ",Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }

                        downloadIamgeUri=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AdminAddNewProductActivity.this,"got the product image URL successfully ",Toast.LENGTH_SHORT).show();

                            saveProductToDatabase();
                        }
                    }
                });

            }
        });

    }

    private void saveProductToDatabase() {

        HashMap<String,Object> productMap=new HashMap<>();

        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",description);
        productMap.put("name",name);
        productMap.put("price",price);
        productMap.put("productImage",downloadIamgeUri);
        productMap.put("category",categoryName);





    }

    //TODO:Open the Gallery
    private void openGallery() {

        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPick);

    }


    //TODO: Get the selected image URI link
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==galleryPick && resultCode==RESULT_OK && data!=null){

            //Get the image from gallery, that is picked up ny User
            imageUri=data.getData();
            selectImage.setImageURI(imageUri);
        }
    }

}
