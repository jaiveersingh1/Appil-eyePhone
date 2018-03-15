package goose.eyephone;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.backendless.Backendless;
import org.json.*;

import static goose.eyephone.Defaults.API_KEY;
import static goose.eyephone.Defaults.APPLICATION_ID;

public class MainActivity extends Activity  {
    Button b1,b2;
    EditText ed1,ed2;

    TextView tx1;
    int counter = 9;//total of nine tries

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Backendless.initApp(this, APPLICATION_ID, API_KEY);

        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);

        b2 = (Button)findViewById(R.id.button2);
        tx1 = (TextView)findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(ed1.getText().toString().equals("Goose") &&//username
                    ed2.getText().toString().equals("pantless thunder goose")) {//password
                //do stuff
                Toast.makeText(getApplicationContext(),"Redirecting...",Toast.LENGTH_SHORT).show();
            }
            else{
                //do other stuff
                Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                tx1.setVisibility(View.VISIBLE);
                //tx1.setBackgroundColor(Color.RED);
                counter--;
                tx1.setText(Integer.toString(counter));

                if (counter == 0) {
                    b1.setEnabled(false);
                }
            }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}