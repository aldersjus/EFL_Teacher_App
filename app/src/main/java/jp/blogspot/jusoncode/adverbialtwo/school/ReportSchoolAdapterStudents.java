package jp.blogspot.jusoncode.adverbialtwo.school;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jp.blogspot.jusoncode.adverbialtwo.R;
import jp.blogspot.jusoncode.adverbialtwo.students.Student;


class ReportSchoolAdapterStudents  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Student> myStudents;


    ReportSchoolAdapterStudents(ArrayList<Student> students){

       myStudents = students;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_school_card,parent,false);

        return new RecyclerView.ViewHolder(v) {
        };
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Student student = myStudents.get(position);
        String noNextLine = student.comment.substring(2);

        TextView tv =(TextView) holder.itemView.findViewById(R.id.lessonCardText);
        TextView tvTwo =(TextView) holder.itemView.findViewById(R.id.lessonCardTextTwo);


        tv.setText(student.name);
        tvTwo.setText(noNextLine);


    }

    @Override
    public int getItemCount() {
        return myStudents.size();
    }


}