package goose.eyephone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class eyesFragment extends Fragment {
    static ImageView eyesImg;

    public eyesFragment() {
        eyesImg = (ImageView) getView().findViewById(R.id.eyes);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eyesImg = (ImageView) getView().findViewById(R.id.eyes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eyesImg = (ImageView) getView().findViewById(R.id.eyes);
        return inflater.inflate(R.layout.fragment_eyes, container, false);
    }

}
