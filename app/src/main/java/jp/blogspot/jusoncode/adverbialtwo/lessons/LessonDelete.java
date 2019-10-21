package jp.blogspot.jusoncode.adverbialtwo.lessons;

import android.app.Fragment;
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

import jp.blogspot.jusoncode.adverbialtwo.HomeScreen;
import jp.blogspot.jusoncode.adverbialtwo.R;

public class LessonDelete extends Fragment {

    private TextView tv;
    private String nameCap;
    private Spinner lessons;
    private CheckBox checkbox;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.lesson_delete, container,false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        lessons = (Spinner) view.findViewById(R.id.spinnerDeleteLesson);
        checkbox = (CheckBox)view.findViewById(R.id.checkboxDeleteLesson);
        tv = (TextView) view.findViewById(R.id.textViewLessonDeleteMessage);
        Button btn = (Button) view.findViewById(R.id.buttonDeleteLesson);

        final SpinnerAdapterLesson spinnerAdapterLesson = new SpinnerAdapterLesson(getActivity(), HomeScreen.data.lessonInMap);
        lessons.setAdapter(spinnerAdapterLesson);


        lessons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    if (checkbox.isChecked()) {
                        HomeScreen.data.lessonMap.remove(nameCap);
                        HomeScreen.data.lessonInMap.remove(nameCap);
                        HomeScreen.data.deleteLesson(nameCap,v);
                        if( HomeScreen.data.studentInMap.contains(nameCap)){
                            HomeScreen.data.studentMap.remove(nameCap);
                            HomeScreen.data.studentInMap.remove(nameCap);
                            HomeScreen.data.deleteStudent(nameCap,v);
                        }
                        spinnerAdapterLesson.notifyDataSetChanged();
                        checkbox.setChecked(false);
                        tv.setText(getResources().getString(R.string.deleted));
                    } else {
                        tv.setText(getResources().getString(R.string.checkToDelete));
                    }
                }else{
                    tv.setText(getResources().getString(R.string.noFile));
                }

            }
        });

        return  view;
    }

}
