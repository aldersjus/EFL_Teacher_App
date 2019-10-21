package jp.blogspot.jusoncode.adverbialtwo.lessons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import jp.blogspot.jusoncode.adverbialtwo.R;

class SpinnerAdapterLesson extends BaseAdapter{

    private LayoutInflater myInflater;
    private ArrayList<String> myWords;


    SpinnerAdapterLesson(Context myContext, ArrayList<String> words){
        myWords = words;
        myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return myWords.size();
    }

    @Override
    public String getItem(int position) {
        return myWords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ////VIEW HOLDER pattern, SEE CLASS BELOW, Android system handles calls to this....
        SpinnerAdapterLesson.ViewHolder vh;
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.spinner_contents_lesson,null);
            vh = new SpinnerAdapterLesson.ViewHolder();
            vh.tv = (TextView)convertView.findViewById(R.id.textSpinnerAdapterLesson);
            convertView.setTag(vh);
        }else{
            vh = (SpinnerAdapterLesson.ViewHolder)convertView.getTag();
        }
        vh.tv.setText(myWords.get(position));
        return convertView;
    }

    /////For implementing view holder pattern for more efficient code.....
    private static class ViewHolder{
        TextView tv;

    }
}
