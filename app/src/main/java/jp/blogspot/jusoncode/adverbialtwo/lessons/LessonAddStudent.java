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

import jp.blogspot.jusoncode.adverbialtwo.HomeScreen;
import jp.blogspot.jusoncode.adverbialtwo.R;
import jp.blogspot.jusoncode.adverbialtwo.students.Student;

public class LessonAddStudent extends Fragment {

    private Button btn;
    private TextView tv;
    private EditText addToLess;
    private String student;
    private String nameCap;
    private String nameAdded;
    private String validInput = "a";
    private Spinner spinner;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.lesson_add_student, container,false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        tv = (TextView) view.findViewById(R.id.textViewAddStudentMessage);
        spinner = (Spinner)view.findViewById(R.id.spinnerLessonAddStudent);
        addToLess = (EditText) view.findViewById(R.id.editTextAddStudentTwo);
        btn = (Button) view.findViewById(R.id.buttonAddStudent);

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
                    student = addToLess.getText().toString().trim();
                    if (student.length() >= validInput.length()) {

                        if(HomeScreen.data.lessonMap.get(nameCap) != null & HomeScreen.data.studentMap.get(nameCap) != null){
                            tv.setText(getResources().getString(R.string.individualCannot));
                        }else {
                            Lesson lesson = HomeScreen.data.lessonMap.get(nameCap);
                            lesson.pupils += student + ",.. ";
                            HomeScreen.data.lessonMap.put(nameCap, lesson);
                            HomeScreen.data.updateLessonRow(lesson,v);
                            nameAdded = "Student: " + student + " ,added to " + nameCap + ".";
                            if(HomeScreen.data.studentMap.get(student) == null){
                                Student studentAdded = new Student(student);
                                HomeScreen.data.studentInMap.add(student);
                                HomeScreen.data.studentMap.put(student,studentAdded);
                                HomeScreen.data.addNewStudent(studentAdded,v);
                            }
                            tv.setText(nameAdded);
                            addToLess.setText("");
                        }

                    } else {
                        tv.setText(getResources().getString(R.string.enter_name));
                    }
                }else{
                    tv.setText(getResources().getString(R.string.noFile));
                }


            }
        });

        return view;
    }

}
