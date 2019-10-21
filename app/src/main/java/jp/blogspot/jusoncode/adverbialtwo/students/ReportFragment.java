package jp.blogspot.jusoncode.adverbialtwo.students;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import jp.blogspot.jusoncode.adverbialtwo.HashMapBitMap;
import jp.blogspot.jusoncode.adverbialtwo.HomeScreen;
import jp.blogspot.jusoncode.adverbialtwo.R;


public class ReportFragment extends Fragment {

    private BarChart barChart;

    private Button btn, shareButton;
    private TextView tv;
    private ImageView iv;
    private StringBuilder thisData;
    private String nameCap;
    private Spinner spinner;
    private String savedData = "Report will be displayed here.";
    double grammarAverage,fluencyAverage,listeningAverage,vocabAverage,commAverage;
    List<Float> floats = new ArrayList<Float>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.report_fragment, container, false);

        setRetainInstance(true);

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.here);
            frameLayout.setPadding(0, 0, 0, 0);
        }

        tv = (TextView) view.findViewById(R.id.textViewStudentInfo);
        spinner = (Spinner)view.findViewById(R.id.spinnerStudentReport);
        btn = (Button) view.findViewById(R.id.buttonReport);
        shareButton = (Button) view.findViewById(R.id.shareButton);
        iv = (ImageView) view.findViewById(R.id.imageView);


        //Todo implement this, method below...
        barChart = (BarChart)view.findViewById(R.id.chart);




        if(nameCap != null){
            if(HomeScreen.imagesHome.get(nameCap) != null) {
                HashMapBitMap hash = new HashMapBitMap(HomeScreen.imagesHome.get(nameCap).getBitmap());
                iv.setImageBitmap(hash.getBitmap());
            }
        }
        tv.setText(savedData);


        floats.add(new Float(grammarAverage));
        floats.add(new Float(listeningAverage));
        floats.add(new Float(vocabAverage));
        floats.add(new Float(fluencyAverage));
        floats.add(new Float(commAverage));

        loadGraph(floats);

        floats.clear();


        final SpinnerAdapterStudent spinnerAdapterStudent = new SpinnerAdapterStudent(getActivity(),HomeScreen.data.studentInMap);
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

                    //Clear any image from the image view......
                    iv.setImageBitmap(null);

                    thisData = new StringBuilder();

                    Student found = HomeScreen.data.studentMap.get(nameCap);

                    grammarAverage = parseTest(found,1);
                    fluencyAverage = parseTest(found,0);
                    listeningAverage = parseTest(found,2);
                    vocabAverage = parseTest(found,3);
                    commAverage = parseTest(found,4);

                    floats.add(new Float(grammarAverage));
                    floats.add(new Float(listeningAverage));
                    floats.add(new Float(vocabAverage));
                    floats.add(new Float(fluencyAverage));
                    floats.add(new Float(commAverage));

                   String average = "\n\nAverage Scores\nGrammar: " + grammarAverage +"\nListening: "+listeningAverage+
                            "\nVocabulary: "+ vocabAverage + "\nFluency: " + fluencyAverage + "\nCommunicative Ability: " + commAverage;
                    Log.d("DEBUG","string created " );

                    if (HomeScreen.imagesHome.containsKey(nameCap)) {
                        HomeScreen.imagesHome.get(nameCap);
                        HashMapBitMap hash = new HashMapBitMap(HomeScreen.imagesHome.get(nameCap).getBitmap());
                        iv.setImageBitmap(hash.getBitmap());
                    }
                    Log.d("DEBUG","string appended " );
                    thisData.append(found.name).append(found.comment).append(average).append("\n\nLesson Scores").append(found.grammar)
                            .append(found.listening).append(found.vocabulary).append(found.fluency).append(found.communicative);
                    tv.setText(thisData);

                    savedData = thisData.toString();


                    loadGraph(floats);

                    floats.clear();

                }else{
                    tv.setText(getResources().getString(R.string.noFile));
                }

            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(savedData);
            }
        });

        return view;
    }

    public double parseTest(Student student,int num){

        String a;
        String b = "";

        switch(num){
            case 0:
                a = student.fluency;
                if(a.length() > 10) {
                    b = a.substring(10, a.length() - 1);
                }
                break;
            case 1:
                a = student.grammar;
                if(a.length() > 10) {
                    b = a.substring(10, a.length() - 1);
                }
                break;
            case 2:
                a = student.listening;
                if(a.length() > 12) {
                    b = a.substring(12, a.length() - 1);
                }
                break;
            case 3:
                a = student.vocabulary;
                if(a.length() > 13) {
                    b = a.substring(13, a.length() - 1);
                }
                break;
            case 4:
                a = student.communicative;
                if(a.length() > 23) {
                    b = a.substring(23, a.length() - 1);
                }
                break;
        }

        String[] c = b.split(",");

        ArrayList<Integer> list = new ArrayList<>();

        if(c.length > 1) {
            for (String i : c) {
                int add = Integer.parseInt(i.trim());
                list.add(add);
            }

            int total = 0;

            for (int i : list) {
                total = total + i;
            }

            BigDecimal bigDecimal = new BigDecimal((double) total / list.size());
            return bigDecimal.setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
            return 0;
    }

    private void share(String text){
        Intent yo = ShareCompat.IntentBuilder.from(getActivity()).setType("text/plain").setText(text).getIntent();

        try {
            startActivity(Intent.createChooser(yo, "share"));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Failed to share, report to developer", Toast.LENGTH_SHORT).show();
        }
    }


    //GRAPH ADDED HERE
    private void loadGraph(List<Float> floats){

        int positionX = 0;

        List<BarEntry> entries = new ArrayList<>();

        for(Float f : floats){
            entries.add(new BarEntry(positionX + 1,f));
            positionX++;
        }


        Description description = new Description();
        description.setEnabled(false);


        YAxis yAxis = barChart.getAxisRight();
        yAxis.setEnabled(false);


        YAxis yAxisL = barChart.getAxisLeft();
        yAxisL.setEnabled(false);
        yAxisL.setAxisMinimum(0);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        BarDataSet dataSet = new BarDataSet(entries,"Label");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(dataSet);

        barChart.setDescription(description);
        barChart.animateY(2000);
        barChart.setData(barData);

    }
}
