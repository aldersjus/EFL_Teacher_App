package jp.blogspot.jusoncode.adverbialtwo.lessons;


import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import jp.blogspot.jusoncode.adverbialtwo.HomeScreen;
import jp.blogspot.jusoncode.adverbialtwo.R;

public class ReportFragmentLess extends Fragment{

    private Button btn,shareButton;
    private TextView tv;
    private String nameCap;
    private StringBuilder thisData;
    private Spinner spinner;
    private String savedData = "Information will be displayed here.";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View view = inflater.inflate(R.layout.report_fragment_lesson, container,false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }


        tv = (TextView) view.findViewById(R.id.textViewReportInfoLess);
        spinner = (Spinner)view.findViewById(R.id.spinnerLessonReportHere);
        btn = (Button)view.findViewById(R.id.buttonReportLess);
        shareButton = (Button) view.findViewById(R.id.shareButtonLesson);

        tv.setText(savedData);



        final SpinnerAdapterLesson spinnerAdapterLesson = new SpinnerAdapterLesson(getActivity(), HomeScreen.data.lessonInMap);
        spinner.setAdapter(spinnerAdapterLesson);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameCap = spinnerAdapterLesson.getItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nameCap != null) {

                    thisData = new StringBuilder();

                    Lesson found = HomeScreen.data.lessonMap.get(nameCap);
                    thisData.append(found.name).append(found.pupils).append(found.takenLessons);
                    tv.setText(thisData);
                    savedData = thisData.toString();
                }else{
                    tv.setText(getResources().getString(R.string.noFile));
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(savedData);
            }
        });



        return view;


        }

    private void share(String text){
        Intent yo = ShareCompat.IntentBuilder.from(getActivity()).setType("text/plain").setText(text).getIntent();
        try {
            startActivity(Intent.createChooser(yo, "share"));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Failed to share, report to developer", Toast.LENGTH_SHORT).show();
        }
    }
}
