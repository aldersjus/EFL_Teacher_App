package jp.blogspot.jusoncode.adverbialtwo.students;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import jp.blogspot.jusoncode.adverbialtwo.HomeScreen;
import jp.blogspot.jusoncode.adverbialtwo.R;

public class StudentDelete extends Fragment {


    private Button btn;
    private TextView tv;
    private String nameCap;
    private CheckBox checkBox;
    private Spinner spinner;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.student_delete, container, false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        tv = (TextView) view.findViewById(R.id.deleteStudentMessage);
        spinner = (Spinner)view.findViewById(R.id.spinnerDeleteStudent);
        btn = (Button) view.findViewById(R.id.buttonDeleteStudent);
        checkBox = (CheckBox)view.findViewById(R.id.checkboxDeleteStudent);


        final SpinnerAdapterStudent spinnerAdapterStudent = new SpinnerAdapterStudent(getActivity(), HomeScreen.data.studentInMap);
        spinner.setAdapter(spinnerAdapterStudent);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameCap = spinnerAdapterStudent.getItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(nameCap != null) {
                    if (checkBox.isChecked()) {


                        HomeScreen.data.studentMap.remove(nameCap);

                        HomeScreen.data.studentInMap.remove(nameCap);
                        if (HomeScreen.imagesHome.containsKey(nameCap)) {

                            HomeScreen.imagesHome.remove(nameCap);
                            save();

                        }
                        HomeScreen.data.deleteStudent(nameCap,v);
                        spinnerAdapterStudent.notifyDataSetChanged();
                        checkBox.setChecked(false);
                        tv.setText(getString(R.string.deleted));
                    } else {
                        tv.setText(getString(R.string.checkToDelete));
                    }
                }else{
                    tv.setText(getResources().getString(R.string.noFile));
                }

            }
        });

        return view;
    }

    public void save(){

        try {
            String file = "adverbial_two_data.txt";
            FileOutputStream fos;
            fos = getActivity().openFileOutput(file, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(HomeScreen.imagesHome);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            Toast toast = Toast.makeText(getActivity(), "Unable to save data, Report to developer.", Toast.LENGTH_SHORT);
            toast.show();

        }

    }
}

