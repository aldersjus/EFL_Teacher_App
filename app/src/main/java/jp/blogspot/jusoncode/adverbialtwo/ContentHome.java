package jp.blogspot.jusoncode.adverbialtwo;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContentHome extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.content_home_screen, container,false);
        setRetainInstance(false);

        ////7 INCH TABLET ADJUST TEXT SIZE PROGRAMMATICAlLY
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE){
            TextView text = (TextView)view.findViewById(R.id.content_home_text);
            text.setTextSize(60);

            int screenOrientation = getActivity().getResources().getConfiguration().orientation;
            if(screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                text.setText(getString(R.string.contentHomeText));
            }
        }

        return view;

    }

}
