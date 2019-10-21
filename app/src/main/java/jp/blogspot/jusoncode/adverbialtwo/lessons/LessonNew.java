package jp.blogspot.jusoncode.adverbialtwo.lessons;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Collections;

import jp.blogspot.jusoncode.adverbialtwo.HomeScreen;
import jp.blogspot.jusoncode.adverbialtwo.R;

public class LessonNew extends Fragment {

    private EditText ed;
    private TextView tv;
    private String nameAdded;
    private String nameCap;
    private String nameAlready = "Lesson File Already Exists\nPlease Choose Another Name";
    private String validInput = "a";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View view =  inflater.inflate(R.layout.lesson_new, container,false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        ed = (EditText)view.findViewById(R.id.editTextLessonNew);
        tv = (TextView)view.findViewById(R.id.textViewLessonNewMessage);
        Button btn = (Button) view.findViewById(R.id.buttonLessonNew);
        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.lessonCheckBoxKidGroup);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameCap = ed.getText().toString().trim();
                if(nameCap.length() >= validInput.length()) {
                    Lesson lesson;
                    if (checkBox.isChecked()) {
                        if (HomeScreen.data.lessonMap.get("KIDS " + nameCap) != null) {
                            tv.setText(nameAlready);
                            ed.setText("");
                        } else {
                            lesson = new Lesson(getString(R.string.addKidsGroupLesson) + nameCap);
                            HomeScreen.data.lessonMap.put(getString(R.string.addKidsGroupLesson) + nameCap, lesson);
                            HomeScreen.data.lessonInMap.add(getString(R.string.addKidsGroupLesson) + nameCap);
                            HomeScreen.data.addNewLesson(lesson, v);
                            nameAdded = getString(R.string.lessonAdd) + getString(R.string.addKidsGroupLesson) + nameCap + getString(R.string.added);
                            tv.setText(nameAdded);
                            ed.setText("");
                            Collections.sort(HomeScreen.data.lessonInMap);
                        }
                    } else {
                        if (HomeScreen.data.lessonMap.get(nameCap) != null) {
                            tv.setText(nameAlready);
                            ed.setText("");
                        } else {
                            lesson = new Lesson(nameCap);
                            HomeScreen.data.lessonMap.put(nameCap, lesson);
                            HomeScreen.data.lessonInMap.add(nameCap);
                            HomeScreen.data.addNewLesson(lesson, v);
                            nameAdded = getString(R.string.lessonAdd) + nameCap + getString(R.string.added);
                            tv.setText(nameAdded);
                            ed.setText("");
                            Collections.sort(HomeScreen.data.lessonInMap);
                        }
                    }
                }else{
                    tv.setText(getResources().getString(R.string.invalid));
                }

            }

        });

        return view;
    }

}
