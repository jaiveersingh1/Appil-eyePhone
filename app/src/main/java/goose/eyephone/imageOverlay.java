package goose.eyephone;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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
    Button camera,select,back,ready;
    String mCurrentPhotoPath;
    private static final int PICK_IMAGE = 100;
    public static Uri imageUriOPEN;
    public static Uri imageUriCLOSED;

    final int PIC_CROP = 2;
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
        ready = (Button)findViewById(R.id.readyButton);

        ready.setVisibility(View.GONE);

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
                startActivity(new Intent(imageOverlay.this, MainActivity.class));
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SO FAR THIS DOSENT WORK
                Intent image = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                image.putExtra("crop", "true");
                image.putExtra("aspectX", 1);
                image.putExtra("aspectY", 1);
                image.putExtra("outputX", 200);
                image.putExtra("outputY", 200);
                image.putExtra("return-data", "true");
                startActivityForResult(image, PIC_CROP);
            }
        });
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
                imageUriOPEN = data.getData();
                eyesOpen.setImageURI(imageUriOPEN);
            }
            else if (requestCode == 5327){
                Bitmap Bitmap = (Bitmap)data.getExtras().get("data");
                eyesOpen.setImageBitmap(Bitmap);
            }
            isOpenDone = true;//toggles the imageView
        }
        else {
            if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
                imageUriCLOSED = data.getData();
                eyesClosed.setImageURI(imageUriCLOSED);
            }
            else if (requestCode == 5327){
                Bitmap Bitmap = (Bitmap)data.getExtras().get("data");
                eyesClosed.setImageBitmap(Bitmap);
            }
            isOpenDone = false;//toggles the imageView
            ready.setVisibility(View.VISIBLE);//shows the button for next step
        }
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            Bitmap image = extras.getParcelable("data");
            eyesOpen.setImageBitmap(image);
        }
    }
}
