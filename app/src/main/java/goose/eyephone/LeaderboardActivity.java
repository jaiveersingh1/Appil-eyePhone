package goose.eyephone;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.HeadersManager;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.*;

import static goose.eyephone.Defaults.API_KEY;
import static goose.eyephone.Defaults.APPLICATION_ID;


/**
 * Created by koh91508 on 3/15/2018.
 */
//https://backendless.com/documentation/files/android/files_file_download.htm

public class LeaderboardActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Backendless.initApp(this, APPLICATION_ID, API_KEY);

        URL url = null;
        try {
            url = new URL("http://api.backendless.com/B019975F-846C-8D90-FFD5-7C8013CF4F00/E5B151B4-07E1-914B-FFC8-80B9103BEF00/files/leaderboard.txt");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String userToken = HeadersManager.getInstance().getHeader(HeadersManager.HeadersEnum.USER_TOKEN_KEY);
            con.setRequestProperty("user-token",userToken);

            con.setUseCaches(false);
            con.setDoInput(false);
            con.connect();

            InputStream in = con.getInputStream();
            /*FileOutputStream out = new FileOutputStream("leaderboard.txt");

            byte[] buf = new byte[4096];
            int len;

            while((len = in.read(buf))>0)
                out.write(buf, 0, len);

            out.close();*/
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        String leaderboardRaw = "shloop";


        Log.d("Raw Text", leaderboardRaw);
    }
}
