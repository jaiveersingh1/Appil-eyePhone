package goose.eyephone;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Gustavo Silvera on 3/20/2018.
 */

public abstract interface threadLock {
    void openGallery();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void threadTest();

}
