package jp.blogspot.jusoncode.adverbialtwo.students;

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

public class StudentFragment extends Fragment {

    private  EditText ed;
    private  TextView tv;
    private  String nameAdded;
    private  String nameAlready = "Student File Already Exists\nPlease Choose Another Name";
    private  String nameCap;
    private  String validInput = "a";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.student_fragment, container,false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        ed = (EditText)view.findViewById(R.id.editTextStudentNew);
        tv = (TextView)view.findViewById(R.id.textViewNewStudentMessage);
        Button btn = (Button) view.findViewById(R.id.buttonStudentNew);
        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.studentCheckBox);
        final CheckBox checkBoxKid = (CheckBox)view.findViewById(R.id.studentCheckBoxKid);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv.setText("");
                nameCap = ed.getText().toString().trim();
                if (nameCap.length() >= validInput.length()) {
                    //nameCap = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    Student student;
                    if(checkBoxKid.isChecked()) {
                        if (HomeScreen.data.studentMap.get("KID " + nameCap) != null) {
                            tv.setText(nameAlready);
                            ed.setText("");
                        } else {
                            student = new Student(getString(R.string.addKidsStudent)+ nameCap);
                            HomeScreen.data.studentMap.put(getString(R.string.addKidsStudent)+ nameCap, student);
                            HomeScreen.data.studentInMap.add(getString(R.string.addKidsStudent)+ nameCap);
                            HomeScreen.data.addNewStudent(student,v);
                            nameAdded = getString(R.string.studentAdd) + getString(R.string.addKidsStudent)+ nameCap + getString(R.string.added);
                            tv.setText(nameAdded);
                            ed.setText("");

                        }
                    }else{
                        if (HomeScreen.data.studentMap.get(nameCap) != null) {
                            tv.setText(nameAlready);
                            ed.setText("");
                        } else {
                            student = new Student(nameCap);
                            HomeScreen.data.studentMap.put(nameCap, student);
                            HomeScreen.data.studentInMap.add(nameCap);
                            HomeScreen.data.addNewStudent(student, v);
                            nameAdded = getString(R.string.studentAdd) + nameCap + getString(R.string.added);
                            tv.setText(nameAdded);
                            ed.setText("");
                        }
                    }


                    Collections.sort(HomeScreen.data.studentInMap);
                    if(checkBox.isChecked()){
                        addToContacts(nameCap);
                        checkBox.setChecked(false);
                    }

                }else {
                    tv.setText(getString(R.string.invalid));
                }
            }
        });

        return  view;
    }

    public void addToContacts(String name){
        Intent intent = new Intent(Intent.ACTION_INSERT);

        ////This code is slightly different to text example...
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME,name);

        if(intent.resolveActivity(getActivity().getPackageManager())!= null){

            startActivity(intent);
        }

    }

}
