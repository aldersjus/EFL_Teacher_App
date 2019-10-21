package jp.blogspot.jusoncode.adverbialtwo.school;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import jp.blogspot.jusoncode.adverbialtwo.HomeScreen;
import jp.blogspot.jusoncode.adverbialtwo.R;
import jp.blogspot.jusoncode.adverbialtwo.lessons.Lesson;
import jp.blogspot.jusoncode.adverbialtwo.students.Student;

public class ReportSchool extends Fragment {


    private String savedData = "savedData";
    private String data;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.report_school, container, false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        TextView text = (TextView)view.findViewById(R.id.textViewReportTop);

        int screenOrientation = getActivity().getResources().getConfiguration().orientation;

        ////7 INCH TABLET LOOKS GOOD BELOW CODE
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE){

            text.setTextSize(50);
            text.setPadding(0,100,0,100);
        }

       if(savedInstanceState != null) {
           HomeScreen.message =  savedInstanceState.get(savedData).toString();
       }


        ///Code below displays the number of students and lessons using a recyclerView and adapters...
        if(!HomeScreen.data.lessonInMap.isEmpty() | !HomeScreen.data.studentInMap.isEmpty()) {

            RecyclerView recycler = (RecyclerView)view.findViewById(R.id.recyclerView);
            recycler.setHasFixedSize(true);
            RecyclerView.LayoutManager lessonLayoutManager;


            ////Adjust the number of columns for different size screens....
            if(HomeScreen.tablet & screenOrientation == Configuration.ORIENTATION_LANDSCAPE){
                lessonLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            }else{
                lessonLayoutManager = new LinearLayoutManager(view.getContext());
            }

            recycler.setLayoutManager(lessonLayoutManager);

            if(HomeScreen.message.equalsIgnoreCase("students")) {
                text.setText(getString(R.string.students));
                final ArrayList<Student> studentForReport = new ArrayList<>();

                for(Student s : HomeScreen.data.studentMap.values()){
                    studentForReport.add(s);
                }
                Collections.sort(studentForReport);
                ReportSchoolAdapterStudents reportSchoolAdapterStudents = new ReportSchoolAdapterStudents(studentForReport);
                recycler.setAdapter(reportSchoolAdapterStudents );

            }else if(HomeScreen.message.equalsIgnoreCase("lesson")){
                text.setText(getString(R.string.lessons));
                final ArrayList<Lesson> lessonForReport = new ArrayList<>();
                for(Lesson l : HomeScreen.data.lessonMap.values()){
                    lessonForReport.add(l);
                }
                Collections.sort(lessonForReport);
                ReportSchoolAdapter reportSchoolAdapter = new ReportSchoolAdapter(lessonForReport);
                recycler.setAdapter(reportSchoolAdapter );
            }


        }else{

            Toast.makeText(getActivity(),"No Lessons",Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void onSaveInstanceState(Bundle out){
        super.onSaveInstanceState(out);
        data = HomeScreen.message;
        out.putString(savedData, data);
    }

}
