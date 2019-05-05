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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class TrendsFragment extends Fragment {

    private LineChart chart, calChart, waterChart, sleepChart;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    /**
     * Shows Trends View. Pulls all health data from user and displays it in four line charts.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trends_test, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        //Steps Walked Chart
        chart = (LineChart) view.findViewById(R.id.stepsChart);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);

        //Creates array for Entries
        //We can use a for loop to pull data from firebase and then populate with the add() method
        //Can also format x-axis for date
        final ArrayList<Entry> StepsArray = new ArrayList<>();

        DatabaseReference referenceSteps = firebaseDatabase.getReference().child("users").child(mAuth.getUid()).child("steps");

        //DatabaseReference refSteps = referenceSteps.child("steps");

        referenceSteps.addValueEventListener(new ValueEventListener() {
            /**
             * Pulls steps data from currently logged in user and adds it to the data array.
             * Data array then is used to populate the steps line chart.
             * Chart is formatted to adhere to the app's design.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                StepsArray.clear();
                int i = 1;
                Iterable<DataSnapshot> stepsDS = dataSnapshot.getChildren();
                for (DataSnapshot stepsChildren : stepsDS)
                {
                    String stepsString = stepsChildren.child("stepsWalked").getValue().toString();
                    int steps = Integer.parseInt(stepsString);
                    StepsArray.add(new Entry(i, steps));
                    i++;
                }

                //used for each dataset to add to chart
                final LineDataSet StepsSet = new LineDataSet(StepsArray, "Number of Steps Walked");

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
                chart.notifyDataSetChanged();
                chart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


        //Calories Consumed Chart
        calChart = (LineChart) view.findViewById(R.id.calChart);

        calChart.setDragEnabled(true);
        calChart.setScaleEnabled(true);

        //Creates array for Entries
        //We can use a for loop to pull data from firebase and then populate with the add() method
        //Can also format x-axis for date
        final ArrayList<Entry> CalsArray = new ArrayList<>();

        DatabaseReference referenceCals = firebaseDatabase.getReference().child("users").child(mAuth.getUid()).child("calories");

        referenceCals.addValueEventListener(new ValueEventListener() {
            /**
             * Pulls Calories data from currently logged in user and adds it to the data array.
             * Data array then is used to populate the calories line chart.
             * Chart is formatted to adhere to the app's design.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                CalsArray.clear();
                int i = 1;
                Iterable<DataSnapshot> calsDS = dataSnapshot.getChildren();
                for (DataSnapshot calsChildren : calsDS)
                {
                    String calsString = calsChildren.child("caloriesConsumed").getValue().toString();
                    int cals = Integer.parseInt(calsString);
                    CalsArray.add(new Entry(i, cals));
                    i++;
                }

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
                calChart.notifyDataSetChanged();
                calChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


        //Cups of Water Chart
        waterChart = (LineChart) view.findViewById(R.id.waterChart);

        waterChart.setDragEnabled(true);
        waterChart.setScaleEnabled(true);

        //Creates array for Entries
        //We can use a for loop to pull data from firebase and then populate with the add() method
        //Can also format x-axis for date
        final ArrayList<Entry> WaterArray = new ArrayList<>();


        DatabaseReference referenceWater = firebaseDatabase.getReference().child("users").child(mAuth.getUid()).child("water");

        referenceWater.addValueEventListener(new ValueEventListener() {
            /**
             * Pulls water data from currently logged in user and adds it to the data array.
             * Data array then is used to populate the water line chart.
             * Chart is formatted to adhere to the app's design.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                WaterArray.clear();
                int i = 1;
                Iterable<DataSnapshot> waterDS = dataSnapshot.getChildren();
                for (DataSnapshot waterChildren : waterDS)
                {
                    String waterString = waterChildren.getValue().toString();
                    waterString = waterString.substring(waterString.indexOf('=')+1, waterString.length()-1);
                    int water = Integer.parseInt(waterString);
                    WaterArray.add(new Entry(i, water));
                    i++;
                }

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
                waterChart.notifyDataSetChanged();
                waterChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


        //Hours Slept Chart
        sleepChart = (LineChart) view.findViewById(R.id.sleepChart);

        sleepChart.setDragEnabled(true);
        sleepChart.setScaleEnabled(true);

        //Creates array for Entries
        //We can use a for loop to pull data from firebase and then populate with the add() method
        //Can also format x-axis for date
        final ArrayList<Entry> SleepArray = new ArrayList<>();


        DatabaseReference referenceSleep = firebaseDatabase.getReference().child("users").child(mAuth.getUid()).child("sleep");

        referenceSleep.addValueEventListener(new ValueEventListener() {
            /**
             * Pulls sleep data from currently logged in user and adds it to the data array.
             * Data array then is used to populate the sleep line chart.
             * Chart is formatted to adhere to the app's design.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                SleepArray.clear();
                int i = 1;
                Iterable<DataSnapshot> sleepDS = dataSnapshot.getChildren();
                for (DataSnapshot sleepChildren : sleepDS)
                {
                    String sleepString = sleepChildren.child("hoursSlept").getValue().toString();
                    int sleep = Integer.parseInt(sleepString);
                    SleepArray.add(new Entry(i, sleep));
                    i++;
                }

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
                sleepChart.notifyDataSetChanged();
                sleepChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        return view;
    }
}