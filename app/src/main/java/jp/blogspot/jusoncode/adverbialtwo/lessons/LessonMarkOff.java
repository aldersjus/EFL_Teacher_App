package jp.blogspot.jusoncode.adverbialtwo.lessons;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.blogspot.jusoncode.adverbialtwo.HomeScreen;
import jp.blogspot.jusoncode.adverbialtwo.R;


public class LessonMarkOff extends Fragment {

    private Button btn;
    private TextView tv;
    private EditText titleLess;
    private String commentCap;
    private String nameCap;
    private String commentAdded;
    private String validInput = "a";
    private Spinner spinner;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lesson_mark_off, container, false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        tv = (TextView) view.findViewById(R.id.textViewLessonMarkOffMessage);
        spinner = (Spinner)view.findViewById(R.id.spinnerMarkOff);
        titleLess = (EditText) view.findViewById(R.id.editTextTwoMarkOff);
        btn = (Button) view.findViewById(R.id.buttonMarkOff);

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

                String format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).format(new Date());

                if(nameCap != null) {

                    commentCap = titleLess.getText().toString();

                    if (commentCap.length() >= validInput.length()) {

                        commentAdded = "Lesson: " + commentCap + " ,added to " + nameCap + ".";
                        Lesson lesson = HomeScreen.data.lessonMap.get(nameCap);
                        String space = "\n" + format + " " + commentCap + ",..";
                        lesson.takenLessons += space;
                        HomeScreen.data.lessonMap.put(nameCap, lesson);
                        HomeScreen.data.updateLessonRow(lesson,v);
                        tv.setText(commentAdded);
                        titleLess.setText("");


                    } else {
                        tv.setText(getResources().getString(R.string.addLessonTitle));
                    }

                }else{
                    tv.setText(getResources().getString(R.string.noFile));
                }

            }
        });

        return view;
    }
}
