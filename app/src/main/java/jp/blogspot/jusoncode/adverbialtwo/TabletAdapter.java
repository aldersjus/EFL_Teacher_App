package jp.blogspot.jusoncode.adverbialtwo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class TabletAdapter extends BaseAdapter {

    private Context myContext;
    private LayoutInflater myInflater;
    private ArrayList<String> myWords;

    TabletAdapter(Context c, ArrayList<String> words){
        myContext = c;
        myWords = words;
        myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return myWords.size();
    }

    @Override
    public Object getItem(int position) {
        return myWords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ////VIEW HOLDER pattern
        ViewHolder vh;
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.tablet_list_view_contents, null);
            vh = new ViewHolder();
            vh.tv = (TextView)convertView.findViewById(R.id.tabletListContents);
            vh.iv = (ImageView)convertView.findViewById(R.id.tabletListContentsImage);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
       // vh.iv.setImageResource(R.drawable.conjunction_accent_s);
        if(position == 0) {
            vh.iv.setImageResource(R.drawable.primary_s_s);
        }else if(position == 1) {
            vh.iv.setImageResource(R.drawable.ic_content_paste_black_24dp);
        }else if(position == 2){
            vh.iv.setImageResource(R.drawable.ic_chat_bubble_outline_black_24dp);
        }else if(position == 3){
            vh.iv.setImageResource(R.drawable.ic_assessment_black_24dp);
        }else if(position == 4){
            vh.iv.setImageResource(R.drawable.ic_person_add_black_24dp);
        }else if(position == 5){
            vh.iv.setImageResource(R.drawable.ic_content_cut_black_24dp);
        }else if(position == 6){
            vh.iv.setImageResource(R.drawable.ic_party_mode_black_24dp);
        }else if(position == 7){
            vh.iv.setImageResource(R.drawable.primary_l_l);
        }else if(position == 8){
            vh.iv.setImageResource(R.drawable.ic_content_paste_black_24dp);
        }else if(position == 9){
            vh.iv.setImageResource(R.drawable.ic_done_black_24dp);
        }else if(position == 10){
            vh.iv.setImageResource(R.drawable.ic_plus_one_black_24dp);
        }else if(position == 11){
            vh.iv.setImageResource(R.drawable.ic_group_add_black_24dp);
        }else if(position == 12){
            vh.iv.setImageResource(R.drawable.ic_person_add_black_24dp);
        }else if(position == 13){
            vh.iv.setImageResource(R.drawable.ic_content_cut_black_24dp);
        }else if(position == 14){
            vh.iv.setImageResource(R.drawable.accent_s_s);
        }else if(position == 15){
            vh.iv.setImageResource(R.drawable.ic_people_black_24dp);
        }else if(position == 16){
            vh.iv.setImageResource(R.drawable.ic_local_library_black_24dp);
        }
        vh.tv.setText(myWords.get(position));
        return convertView;
    }

    /////For implementing view holder pattern for more efficient code.....
    private static class ViewHolder{
        ImageView iv;
        TextView tv;

    }
}
