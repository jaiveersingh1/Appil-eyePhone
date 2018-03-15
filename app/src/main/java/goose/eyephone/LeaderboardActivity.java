package goose.eyephone;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.backendless.Backendless;

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

        String leaderboardRaw;



        Log.d("Raw Text", leaderboardRaw);
    }
}
