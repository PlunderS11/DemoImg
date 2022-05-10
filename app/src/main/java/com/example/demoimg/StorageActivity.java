package com.example.demoimg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.demoimg.databinding.ActivityMainBinding;
import com.example.demoimg.databinding.ActivityStorageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StorageActivity extends AppCompatActivity {
    private ActivityStorageBinding binding;
    private Uri imageURI;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStorageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        binding.buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        binding.buttonGoToSignInView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignInView();
            }
        });
    }

    private void openSignInView() {
        Intent intentSignInView = new Intent(this, SignInActivity.class);
        startActivity(intentSignInView);
    }

    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File...");
        progressDialog.show();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH);
        Date now = new Date();
        String fileName = simpleDateFormat.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("image/"+fileName);

        storageReference.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                binding.imageViewWallpaper.setImageURI(null);
                Toast.makeText(StorageActivity.this, "Successfully Upload", Toast.LENGTH_SHORT).show();
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Toast.makeText(StorageActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && data != null && data.getData() != null){
            imageURI = data.getData();
            binding.imageViewWallpaper.setImageURI(imageURI);
        }
    }
}