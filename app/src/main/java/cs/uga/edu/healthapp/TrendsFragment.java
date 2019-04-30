package cs.uga.edu.healthapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;

public class TrendsFragment extends Fragment {

    private LineChart chart, calChart, waterChart, sleepChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trends_test, container, false);




        //Steps Walked Chart
        chart = (LineChart) view.findViewById(R.id.stepsChart);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);

        //Creates array for Entries
        //We can use a for loop to pull data from firebase and then populate with the add() method
        //Can also format x-axis for date
        ArrayList<Entry> StepsArray = new ArrayList<>();

        StepsArray.add(new Entry(0, 10));
        StepsArray.add(new Entry(1, 20));
        StepsArray.add(new Entry(2, 30));
        StepsArray.add(new Entry(3, 40));
        StepsArray.add(new Entry(4, 50));

        //used for each dataset to add to chart
        LineDataSet StepsSet = new LineDataSet(StepsArray, "Number of Steps Walked");

        StepsSet.setFillAlpha(255);
        StepsSet.setLineWidth(3f);
        StepsSet.setValueTextColor(Color.WHITE);
        StepsSet.setValueTextSize(14f);
        StepsSet.setColor(Color.CYAN);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(StepsSet);

        LineData data = new LineData(dataSets);


        //sets text color and size
        chart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        chart.getAxisRight().setTextColor(Color.WHITE); //right y-axis
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.getDescription().setTextColor(Color.WHITE);


        chart.getXAxis().setTextSize(18f);
        chart.getAxisLeft().setTextSize(18f);
        chart.getAxisRight().setTextSize(18f);
        chart.getLegend().setTextSize(18f);
        chart.getDescription().setTextSize(18f);

        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        //sets chart title and data
        chart.getDescription().setEnabled(false);
        chart.setData(data);





        //Calories Consumed Chart
        calChart = (LineChart) view.findViewById(R.id.calChart);

        calChart.setDragEnabled(true);
        calChart.setScaleEnabled(true);

        //Creates array for Entries
        //We can use a for loop to pull data from firebase and then populate with the add() method
        //Can also format x-axis for date
        ArrayList<Entry> CalsArray = new ArrayList<>();

        CalsArray.add(new Entry(0, 50));
        CalsArray.add(new Entry(1, 40));
        CalsArray.add(new Entry(2, 30));
        CalsArray.add(new Entry(3, 20));
        CalsArray.add(new Entry(4, 10));

        //used for each dataset to add to chart
        LineDataSet CalsSet = new LineDataSet(CalsArray, "Calories Consumed");

        CalsSet.setFillAlpha(255);
        CalsSet.setLineWidth(3f);
        CalsSet.setValueTextColor(Color.WHITE);
        CalsSet.setValueTextSize(14f);
        CalsSet.setColor(Color.CYAN);

        ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
        dataSets2.add(CalsSet);

        LineData data2 = new LineData(dataSets2);

        //Chart Labels Formatting
        calChart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        calChart.getAxisRight().setTextColor(Color.WHITE); //right y-axis
        calChart.getXAxis().setTextColor(Color.WHITE);
        calChart.getLegend().setTextColor(Color.WHITE);
        calChart.getDescription().setTextColor(Color.WHITE);


        calChart.getXAxis().setTextSize(18f);
        calChart.getAxisLeft().setTextSize(18f);
        calChart.getAxisRight().setTextSize(18f);
        calChart.getLegend().setTextSize(18f);
        calChart.getDescription().setTextSize(18f);

        calChart.getAxisRight().setEnabled(false);
        calChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        //sets chart title and data
        calChart.getDescription().setEnabled(false);
        calChart.setData(data2);





        //Cups of Water Chart
        waterChart = (LineChart) view.findViewById(R.id.waterChart);

        waterChart.setDragEnabled(true);
        waterChart.setScaleEnabled(true);

        //Creates array for Entries
        //We can use a for loop to pull data from firebase and then populate with the add() method
        //Can also format x-axis for date
        ArrayList<Entry> WaterArray = new ArrayList<>();

        WaterArray.add(new Entry(0, 20));
        WaterArray.add(new Entry(1, 20));
        WaterArray.add(new Entry(2, 20));
        WaterArray.add(new Entry(3, 20));
        WaterArray.add(new Entry(4, 20));

        //used for each dataset to add to chart
        LineDataSet WaterSet = new LineDataSet(WaterArray, "Cups of Water Drank");

        WaterSet.setFillAlpha(255);
        WaterSet.setLineWidth(3f);
        WaterSet.setValueTextColor(Color.WHITE);
        WaterSet.setValueTextSize(14f);
        WaterSet.setColor(Color.CYAN);

        ArrayList<ILineDataSet> dataSets3 = new ArrayList<>();
        dataSets3.add(WaterSet);

        LineData data3 = new LineData(dataSets3);

        //Chart Labels Formatting
        waterChart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        waterChart.getAxisRight().setTextColor(Color.WHITE); //right y-axis
        waterChart.getXAxis().setTextColor(Color.WHITE);
        waterChart.getLegend().setTextColor(Color.WHITE);
        waterChart.getDescription().setTextColor(Color.WHITE);


        waterChart.getXAxis().setTextSize(18f);
        waterChart.getAxisLeft().setTextSize(18f);
        waterChart.getAxisRight().setTextSize(18f);
        waterChart.getLegend().setTextSize(18f);
        waterChart.getDescription().setTextSize(18f);

        waterChart.getAxisRight().setEnabled(false);
        waterChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        //sets chart title and data
        waterChart.getDescription().setEnabled(false);
        waterChart.setData(data3);





        //Hours Slept Chart
        sleepChart = (LineChart) view.findViewById(R.id.sleepChart);

        sleepChart.setDragEnabled(true);
        sleepChart.setScaleEnabled(true);

        //Creates array for Entries
        //We can use a for loop to pull data from firebase and then populate with the add() method
        //Can also format x-axis for date
        ArrayList<Entry> SleepArray = new ArrayList<>();

        SleepArray.add(new Entry(0, 20));
        SleepArray.add(new Entry(1, 40));
        SleepArray.add(new Entry(2, 40));
        SleepArray.add(new Entry(3, 40));
        SleepArray.add(new Entry(4, 20));

        //used for each dataset to add to chart
        LineDataSet SleepSet = new LineDataSet(SleepArray, "Number of Hours Slept");

        SleepSet.setFillAlpha(255);
        SleepSet.setLineWidth(3f);
        SleepSet.setValueTextColor(Color.WHITE);
        SleepSet.setValueTextSize(14f);
        SleepSet.setColor(Color.CYAN);

        ArrayList<ILineDataSet> dataSets4 = new ArrayList<>();
        dataSets4.add(SleepSet);

        LineData data4 = new LineData(dataSets4);

        //Chart Labels Formatting
        sleepChart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        sleepChart.getAxisRight().setTextColor(Color.WHITE); //right y-axis
        sleepChart.getXAxis().setTextColor(Color.WHITE);
        sleepChart.getLegend().setTextColor(Color.WHITE);
        sleepChart.getDescription().setTextColor(Color.WHITE);


        sleepChart.getXAxis().setTextSize(18f);
        sleepChart.getAxisLeft().setTextSize(18f);
        sleepChart.getAxisRight().setTextSize(18f);
        sleepChart.getLegend().setTextSize(18f);
        sleepChart.getDescription().setTextSize(18f);

        sleepChart.getAxisRight().setEnabled(false);
        sleepChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        //sets chart title and data
        sleepChart.getDescription().setEnabled(false);
        sleepChart.setData(data4);


        return view;
    }
}
