package com.example.android_practice_1.activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageOptions;
import com.canhub.cropper.CropImageView;
import com.example.android_practice_1.R;

public class Crop_Image_Practice extends AppCompatActivity {

    private ImageView imageView;
    private final ActivityResultLauncher<CropImageContractOptions> cropImageLauncher =
            registerForActivityResult(new CropImageContract(), result -> {
                if (result.isSuccessful()) {
                    // Display the cropped image
                    Uri croppedImageUri = result.getUriContent();
                    imageView.setImageURI(croppedImageUri);
                } else {
                    // Handle errors if any
                    Exception error = result.getError();
                    error.printStackTrace();
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crop_image_practice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnSelectImage = findViewById(R.id.btn_select_image);
        imageView = findViewById(R.id.image_view);

        btnSelectImage.setOnClickListener(v -> startCropImageActivity());
    }

    private void startCropImageActivity() {
        CropImageOptions options = new CropImageOptions();
        options.guidelines = CropImageView.Guidelines.ON; // Show guidelines
        options.activityTitle = "Crop Image"; // Set title for the crop screen
        options.allowRotation = true; // Allow rotation
        options.allowFlipping = true; // Allow flipping
        options.cropMenuCropButtonTitle = "Done"; // Set button text for cropping
        //options.cropMenuCropButtonIcon = R.drawable.ic_done; // Optional: Set custom icon

        cropImageLauncher.launch(new CropImageContractOptions(null, options));
    }
}
