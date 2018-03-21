package goose.eyephone;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by silvera94198 on 3/7/2018.
 */
//¯\_(ツ)_/¯
public class imageOverlay extends Activity implements threadLock{
    Button camera,select,back,ready;
    private static final int PICK_IMAGE = 100;
    volatile private static Uri imageUriOPEN;
    volatile private static Uri imageUriCLOSED;
    volatile private static Bitmap op;
    volatile private static Bitmap cl;
    ArrayList<Bitmap> BitListOP = new ArrayList<>();//list of old OPen bitmaps
    ArrayList<Bitmap> BitListCL = new ArrayList<>();//list of old CLosed bitmaps
    private static Thread myThread; //this is the code I got from the tutorial
    private static boolean ThreadsRunning;

    ImageView eyesOpen, eyesClosed, eyesImg;
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
        eyesImg = (ImageView)findViewById(R.id.eyes);
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
                setContentView(R.layout.fragment_eyes);
                threadTest();
            }
        });
    }

    public void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(!isOpenDone){//first starting with eyes open
            if(resultCode == RESULT_OK && requestCode == PICK_IMAGE ){
                imageUriOPEN = data.getData();
                try {
                    op = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUriOPEN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                eyesOpen.setImageURI(imageUriOPEN);
            }
            else if (requestCode == 5327){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                op = bitmap;
                eyesOpen.setImageBitmap(op);
            }
            isOpenDone = true;//toggles the imageView
            BitListOP.add(op);
        }
        else {
            if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
                imageUriCLOSED = data.getData();
                try {
                    cl = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUriCLOSED);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                eyesClosed.setImageURI(imageUriCLOSED);
            }
            else if (requestCode == 5327){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                cl = bitmap;
                eyesClosed.setImageBitmap(cl);
            }
            isOpenDone = false;//toggles the imageView
            ready.setVisibility(View.VISIBLE);//shows the button for next step
            BitListCL.add(cl);
        }
        if(BitListOP.size() > 5) BitListOP.clear();//reduces memory hogging
        if(BitListCL.size() > 5) BitListCL.clear();//better for optimization

    }
    public void threadTest(){
        ThreadsRunning = true;
        eyesImg = (ImageView)findViewById(R.id.eyes);
        eyesImg.buildDrawingCache();
        myThread = new Thread(){
            public void run() {
                while (ThreadsRunning){
                    eyesImg.setImageBitmap(op);
                    eyesImg.invalidate();
                    try {Thread.sleep(950);}
                    catch (InterruptedException ex) {
                        Log.e("TAG", "Thread Sleep Error", ex);}
                    eyesImg.setImageBitmap(cl);
                    eyesImg.invalidate();
                    try{Thread.sleep(80);}
                    catch (InterruptedException ex){Log.e("TAG", "Thread Sleep Error", ex);}
                }
            }
        };

        myThread.start();
        //ThreadsRunning = false; #this stops the thread
    }
}
