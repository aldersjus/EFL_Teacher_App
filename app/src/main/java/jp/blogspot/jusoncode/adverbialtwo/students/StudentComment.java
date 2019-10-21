package jp.blogspot.jusoncode.adverbialtwo.students;

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

public class StudentComment extends Fragment {

    private Button btn;
    private TextView tv;
    private EditText commentStu;
    private String commentCap;
    private String nameCap;
    private String commentAdded  = "Comment Added";
    private String validInput = "a";
    private Spinner spinner;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.student_comment, container,false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }


        tv = (TextView) view.findViewById(R.id.textViewCommentMessageShow);
        spinner = (Spinner)view.findViewById(R.id.spinnerAddComment);
        commentStu = (EditText)view.findViewById(R.id.editTextCommentTwo);
        btn = (Button)view.findViewById(R.id.buttonComment);

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
                    commentCap = commentStu.getText().toString().trim();

                    if (commentCap.length() >= validInput.length()) {

                        Student student = HomeScreen.data.studentMap.get(nameCap);
                        student.comment += commentCap + ",.. ";
                        HomeScreen.data.studentMap.put(nameCap, student);
                        HomeScreen.data.updateStudentRow(student,v);
                        tv.setText(commentAdded);
                        commentStu.setText("");


                    } else {
                        tv.setText(getString(R.string.enterComment));
                    }
                }else{
                    tv.setText(getResources().getString(R.string.noFile));
                }
            }
        });

        return  view;
    }

}
