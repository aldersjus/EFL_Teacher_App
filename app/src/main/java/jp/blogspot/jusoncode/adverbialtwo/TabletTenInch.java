package jp.blogspot.jusoncode.adverbialtwo;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonAddStudent;
import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonDelete;
import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonMarkOff;
import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonNew;
import jp.blogspot.jusoncode.adverbialtwo.lessons.LessonNewMan;
import jp.blogspot.jusoncode.adverbialtwo.lessons.ReportFragmentLess;
import jp.blogspot.jusoncode.adverbialtwo.school.ReportSchool;
import jp.blogspot.jusoncode.adverbialtwo.students.Grades;
import jp.blogspot.jusoncode.adverbialtwo.students.ReportFragment;
import jp.blogspot.jusoncode.adverbialtwo.students.StudentComment;
import jp.blogspot.jusoncode.adverbialtwo.students.StudentDelete;
import jp.blogspot.jusoncode.adverbialtwo.students.StudentFragment;

public class TabletTenInch extends Fragment {

    private final ArrayList<String> list = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.tablet_list_view, container,false);
        setRetainInstance(false);

        populateList();

        ListView listView = (ListView)view.findViewById(R.id.tabletList);

        TabletAdapter tabletAdapter = new TabletAdapter(getActivity(),list);

        listView.setAdapter(tabletAdapter);

        final FragmentManager fm = getActivity().getFragmentManager();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(HomeScreen.tablet){
                    //////If using a tablet, now set to false....
                    switch (position) {
                        case 1:
                            ReportFragment reportFragment = new ReportFragment();
                            fm.beginTransaction().replace(R.id.here,reportFragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 2:
                            StudentComment studentComment = new StudentComment();
                            fm.beginTransaction().replace(R.id.here,studentComment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 3:
                            Grades grades = new Grades();
                            fm.beginTransaction().replace(R.id.here,grades).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 4:
                            StudentFragment studentFragment = new StudentFragment();
                            fm.beginTransaction().replace(R.id.here,studentFragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 5:
                            StudentDelete sd = new StudentDelete();
                            fm.beginTransaction().replace(R.id.here, sd).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 6:
                            Intent intent = new Intent(getActivity(), PhotoScreen.class);
                            startActivity(intent);
                            break;
                        case 8:
                            ReportFragmentLess reportFragmentLess = new ReportFragmentLess();
                            fm.beginTransaction().replace(R.id.here,reportFragmentLess).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 9:
                            LessonMarkOff lessonMarkOff = new LessonMarkOff();
                            fm.beginTransaction().replace(R.id.here,lessonMarkOff).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 10:
                            LessonAddStudent lessonAddStudent = new LessonAddStudent();
                            fm.beginTransaction().replace(R.id.here,lessonAddStudent).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 11:
                            LessonNew lessonNew= new LessonNew();
                            fm.beginTransaction().replace(R.id.here,lessonNew).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 12:
                            LessonNewMan lessonNewMan = new LessonNewMan();
                            fm.beginTransaction().replace(R.id.here,lessonNewMan).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 13:
                            LessonDelete lessonDelete = new LessonDelete();
                            fm.beginTransaction().replace(R.id.here,lessonDelete).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 15:
                            HomeScreen.message = "students";
                            ReportSchool reportSchool = new ReportSchool();
                            fm.beginTransaction().replace(R.id.here,reportSchool).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                        case 16:
                            HomeScreen.message = "lesson";
                            ReportSchool reportSchoolLesson = new ReportSchool();
                            fm.beginTransaction().replace(R.id.here, reportSchoolLesson).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                            break;
                    }
                }
            }

        });



        return view;

    }

    private void populateList(){
        list.add("Student");
        list.add("\t\t\tReport");
        list.add("\t\t\tComment");
        list.add("\t\t\tGrades");
        list.add("\t\t\tNew");
        list.add("\t\t\tDelete");
        list.add("\t\t\tPhoto");
        list.add("Lesson");
        list.add("\t\t\tInformation");
        list.add("\t\t\tMark Off");
        list.add("\t\t\tAdd Student");
        list.add("\t\t\tNew Group");
        list.add("\t\t\tNew Individual");
        list.add("\t\t\tDelete");
        list.add("School");
        list.add("\t\t\tStudents");
        list.add("\t\t\tLessons");
    }

}
