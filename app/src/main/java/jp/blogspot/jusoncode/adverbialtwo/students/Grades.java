package jp.blogspot.jusoncode.adverbialtwo.students;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import jp.blogspot.jusoncode.adverbialtwo.HomeScreen;
import jp.blogspot.jusoncode.adverbialtwo.R;


public class Grades  extends Fragment {

    private TextView tv;
    private RadioGroup groupG;
    private RadioGroup groupV;
    private RadioGroup groupF;
    private RadioGroup groupL;
    private RadioGroup groupC;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;
    private String nameCap;
    private String updated = "Grades Updated";

    private Spinner spinner;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.grades_fragment, container,false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        tv = (TextView)view.findViewById(R.id.displayResult);
        spinner = (Spinner)view.findViewById(R.id.spinnerGrades);
        Button btn = (Button) view.findViewById(R.id.buttonGrades);
        groupG = (RadioGroup) view.findViewById(R.id.radioGroupGrammar);
        groupV = (RadioGroup) view.findViewById(R.id.radioGroupVocabulary);
        groupF = (RadioGroup) view.findViewById(R.id.radioGroupFluency);
        groupC = (RadioGroup) view.findViewById(R.id.radioGroupCommunacative);
        groupL = (RadioGroup) view.findViewById(R.id.radioGroupListening);
        rb1 = (RadioButton)view.findViewById(R.id.radioButton3g);
        rb2 = (RadioButton)view.findViewById(R.id.radioButton3c);
        rb3 = (RadioButton)view.findViewById(R.id.radioButton3f);
        rb4 = (RadioButton)view.findViewById(R.id.radioButton3l);
        rb5 = (RadioButton)view.findViewById(R.id.radioButton3v);
        rb1.setChecked(true);
        rb2.setChecked(true);
        rb3.setChecked(true);
        rb4.setChecked(true);
        rb5.setChecked(true);

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
                    String grammar = grammar();
                    String vocab = vocab();
                    String listen = listen();
                    String fluency = fluency();
                    String comm = communicative();


                    Student found = HomeScreen.data.studentMap.get(nameCap);
                    found.communicative += comm;
                    found.fluency += fluency;
                    found.grammar += grammar;
                    found.listening += listen;
                    found.vocabulary += vocab;
                    HomeScreen.data.studentMap.put(nameCap, found);
                    HomeScreen.data.updateStudentRow(found,v);
                    tv.setText(updated);
                }else{
                    tv.setText(getResources().getString(R.string.noFile));
                }
            }
        });


        return  view;
    }

    public String grammar(){
        int a = groupG.getCheckedRadioButtonId();
        String grammar = "";
        ///Grammar
        if(a == R.id.radioButton1g){
             grammar = "1, ";
        }else if(a == R.id.radioButton2g){
            grammar = "2, ";
        }else if(a == R.id.radioButton3g){
            grammar = "3, ";
        }else if(a == R.id.radioButton4g){
            grammar = "4, ";
        }else if(a == R.id.radioButton5g){
            grammar = "5, ";
        }
        return grammar;
    }

    public String vocab(){
        int b = groupV.getCheckedRadioButtonId();
        String vocab = "";
        ////Vocabulary
        if(b == R.id.radioButton1v){
            vocab = "1, ";
        }else if(b == R.id.radioButton2v){
            vocab = "2, ";
        }else if(b == R.id.radioButton3v){
            vocab = "3, ";
        }else if(b == R.id.radioButton4v){
            vocab = "4, ";
        }else if(b == R.id.radioButton5v){
            vocab = "5, ";
        }

        return vocab ;
    }

    public String listen(){
        int d = groupL.getCheckedRadioButtonId();
        String listen = "";
        //Listening
        if(d == R.id.radioButton1l){
            listen = "1, ";
        }else if(d == R.id.radioButton2l){
            listen = "2, ";
        }else if(d == R.id.radioButton3l){
            listen = "3, ";
        }else if(d == R.id.radioButton4l){
            listen = "4, ";
        }else if(d == R.id.radioButton5l){
            listen = "5, ";
        }

        return listen ;
    }

    public String fluency(){
        int e = groupF.getCheckedRadioButtonId();
        String fluency = "";
        //Fluency
        if(e == R.id.radioButton1f){
            fluency = "1, ";
        }else if(e == R.id.radioButton2f){
            fluency = "2, ";
        }else if(e == R.id.radioButton3f){
            fluency = "3, ";
        }else if(e == R.id.radioButton4f){
            fluency = "4, ";
        }else if(e == R.id.radioButton5f){
            fluency = "5, ";
        }

        return fluency ;
    }

    public String communicative(){
        int c = groupC.getCheckedRadioButtonId();
        String com = "";

        ///Communicative
        if(c == R.id.radioButton1c){
            com = "1, ";
        }else if(c == R.id.radioButton2c){
            com = "2, ";
        }else if(c == R.id.radioButton3c){
            com = "3, ";
        }else if(c == R.id.radioButton4c){
            com = "4, ";
        }else if(c == R.id.radioButton5c){
            com = "5, ";
        }

        return com ;
    }

}

