package jp.blogspot.jusoncode.adverbialtwo.school;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jp.blogspot.jusoncode.adverbialtwo.R;
import jp.blogspot.jusoncode.adverbialtwo.lessons.Lesson;

class ReportSchoolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Lesson> myLessons;


    ReportSchoolAdapter(ArrayList<Lesson> lessons){

        myLessons = lessons;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_school_card,parent,false);

        return new RecyclerView.ViewHolder(v) {
        };
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Lesson lesson = myLessons.get(position);
        String noNextLine = lesson.pupils.substring(2);

        TextView tv =(TextView) holder.itemView.findViewById(R.id.lessonCardText);
        TextView tvTwo =(TextView) holder.itemView.findViewById(R.id.lessonCardTextTwo);


        tv.setText(lesson.name);
        tvTwo.setText(noNextLine);


    }

    @Override
    public int getItemCount() {
        return myLessons.size();
    }


}

