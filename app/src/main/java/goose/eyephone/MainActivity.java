package goose.eyephone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button login,cancel;
    EditText nameEnter,passwordEnter;

    TextView tx1;
    int counter = 9;//total of nine tries

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button)findViewById(R.id.login);
        nameEnter = (EditText)findViewById(R.id.enterName);
        passwordEnter = (EditText)findViewById(R.id.passwordText);

        cancel = (Button)findViewById(R.id.cancel);
        tx1 = (TextView)findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEnter.getText().toString().equals("Goose") &&//username
                        passwordEnter.getText().toString().equals("thunderpants")) {//password
                    //do stuff
                    Toast.makeText(getApplicationContext(),"Pivoting...",Toast.LENGTH_SHORT).show();
                    //dispatchTakePictureIntent();
                    //switches viewfinder
                    startActivity(new Intent(MainActivity.this,imageOverlay.class));
                }
                else{
                    //do other stuff
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                    tx1.setVisibility(View.VISIBLE);
                    //tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        login.setEnabled(false);
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}