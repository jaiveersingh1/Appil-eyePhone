package goose.eyephone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by silvera94198 on 3/7/2018.
 */
//¯\_(ツ)_/¯
public class imageOverlay extends Activity{
    Button camera,select,back;
    String mCurrentPhotoPath;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView eyesOpen;
    ImageView eyesClosed;
    boolean isOpenDone = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overlay);

        camera = (Button)findViewById(R.id.cameraButton);
        select = (Button)findViewById(R.id.galleryButton);
        back = (Button)findViewById(R.id.backButton);
        eyesOpen = (ImageView)findViewById(R.id.eyesOpen);
        eyesClosed = (ImageView)findViewById(R.id.eyesClosed);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dispatchTakePictureIntent();//runs camera thing
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 5327);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(imageOverlay.this,MainActivity.class));
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img";//"JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",    /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "goose.eyephone.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(!isOpenDone){//first starting with eyes open
            if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
                imageUri = data.getData();
                eyesOpen.setImageURI(imageUri);
            }
            else if (requestCode == 5327){
                Bitmap Bitmap = (Bitmap)data.getExtras().get("data");
                eyesOpen.setImageBitmap(Bitmap);
            }
            isOpenDone = true;//toggles the imageView
        }
        else {
            if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
                imageUri = data.getData();
                eyesClosed.setImageURI(imageUri);
            }
            else if (requestCode == 5327){
                Bitmap Bitmap = (Bitmap)data.getExtras().get("data");
                eyesClosed.setImageBitmap(Bitmap);
            }
            isOpenDone = false;//toggles the imageView

        }
    }
}
