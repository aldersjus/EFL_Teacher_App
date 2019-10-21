package jp.blogspot.jusoncode.adverbialtwo.lessons;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import jp.blogspot.jusoncode.adverbialtwo.students.Student;


public class LessonNewMan extends Fragment {

    private EditText ed;
    private TextView tv;
    private String nameAdded;
    private String nameCap;
    private String validInput = "a";
    private String nameAlready = "Lesson File Already Exists\nPlease Choose Another Name";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.lesson_new_man, container,false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        ed = (EditText)view.findViewById(R.id.editMtoM);
        tv = (TextView)view.findViewById(R.id.textViewLessonNewMMMessage);
        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.lessonCheckBoxAddToContacts);
        final CheckBox checkBoxKid = (CheckBox)view.findViewById(R.id.checkBoxKidIndividualCreateKids);

        Button btn = (Button) view.findViewById(R.id.buttonMtoM);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nameCap = ed.getText().toString().trim();
                if(nameCap.length() >= validInput.length()){
                    nameAdded = "Individual Lesson: " + nameCap + getString(R.string.added);
                    tv.setText(nameAdded);
                    Student student;
                    if(checkBoxKid.isChecked()) {
                        if(HomeScreen.data.studentMap.get(nameCap) == null) {

                            student = new Student(getString(R.string.addKidsStudent)+ nameCap);
                            HomeScreen.data.studentInMap.add(getString(R.string.addKidsStudent)+ nameCap);
                            HomeScreen.data.studentMap.put(getString(R.string.addKidsStudent)+ nameCap,student);
                            HomeScreen.data.addNewStudent(student,v);
                        }
                    }else{
                        if(HomeScreen.data.studentMap.get(nameCap) == null) {
                            student = new Student(nameCap);
                            HomeScreen.data.studentInMap.add(nameCap);
                            HomeScreen.data.studentMap.put(nameCap, student);
                            HomeScreen.data.addNewStudent(student, v);
                        }
                    }
                    Lesson lesson;
                    if(checkBoxKid.isChecked()){
                        if(HomeScreen.data.lessonMap.get("KIDS " +nameCap) != null){
                            tv.setText(nameAlready);
                            ed.setText("");
                        }else{

                            lesson = new Lesson(getString(R.string.addKidsStudent) + nameCap);
                            lesson.pupils += "Individual Lesson ";
                            HomeScreen.data.lessonMap.put(getString(R.string.addKidsStudent) + nameCap, lesson);
                            HomeScreen.data.lessonInMap.add(getString(R.string.addKidsStudent) + nameCap);
                            HomeScreen.data.addNewLesson(lesson,v);
                            nameAdded = "Individual Lesson: " + getString(R.string.addKidsStudent) + nameCap + " ,added.";
                            tv.setText(nameAdded);
                            ed.setText("");
                        }
                    }else{
                        if(HomeScreen.data.lessonMap.get(nameCap) != null){
                            tv.setText(nameAlready);
                            ed.setText("");
                        }else {
                            lesson = new Lesson(nameCap);
                            lesson.pupils += "Individual Lesson ";
                            HomeScreen.data.lessonMap.put(nameCap, lesson);
                            HomeScreen.data.lessonInMap.add(nameCap);
                            HomeScreen.data.addNewLesson(lesson,v);
                            nameAdded = "Individual Lesson: " + nameCap + " ,added.";
                            tv.setText(nameAdded);
                            ed.setText("");
                        }
                    }

                    Collections.sort(HomeScreen.data.lessonInMap);
                    if(checkBox.isChecked()){
                        addToContacts(nameCap);
                        checkBox.setChecked(false);
                    }
                    } else{
                    tv.setText(getResources().getString(R.string.invalid));
                }

            }
        });

        return view;
    }

    public void addToContacts(String name){
        Intent intent = new Intent(Intent.ACTION_INSERT);

        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME,name);

        if(intent.resolveActivity(getActivity().getPackageManager())!= null){

            startActivity(intent);
        }

    }


}