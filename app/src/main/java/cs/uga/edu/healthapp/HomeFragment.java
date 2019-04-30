package cs.uga.edu.healthapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private Button btnBMI, btnSteps, btnCalories, btnWater, btnSleep;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnBMI = view.findViewById(R.id.buttonBMI);
        btnSteps = view.findViewById(R.id.buttonSteps);
        btnCalories = view.findViewById(R.id.buttonCalories);
        btnWater = view.findViewById(R.id.buttonWater);
        btnSleep = view.findViewById(R.id.buttonSleep);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();

        //get date
        LocalDate localDate = LocalDate.now();
        final String currentDate = DateTimeFormatter.ofPattern("MM/dd/yyy").format(localDate);

        //reference the database with the specific user's unique id
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users").child(mAuth.getUid());
        //event listener for referencing the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  //whenever there is a change in the database or when the app just starts
                User user = dataSnapshot.getValue(User.class);  //retrieve all of the user's info from the database and place in user object

                if (user != null) {
                    String userHeight = user.getHeight();
                    String userWeight = user.getWeight();

                    String userBMI = calculateBMI(userHeight, userWeight);
                    Log.d(TAG, "onDataChange: USER BMI = " + userBMI);
                    String weightStatus = calcWeightStatus(userBMI);
                    btnBMI.setText("BMI: " + userBMI + "\nWeight Status: " + weightStatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { //if there is some database error, show toast error
                Toast.makeText(getActivity(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        //dialog box for entering steps
        view.findViewById(R.id.buttonSteps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Enter Steps Walked Today");

                // Set up the input
                final EditText input = (EditText) LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_input, null, false);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String stepsInput = input.getText().toString();
                        btnSteps.setText("Steps Walked: " + stepsInput);

                        Log.d(TAG, "onStepsClick: DATE = " + currentDate);
                        Map<String, Map<String, String>> stepsData = new HashMap<String, Map<String, String>>();
                        //stepsData.put("Date", currentDate, "Steps", stepsInput);

                        myRef.child("users").child(mAuth.getUid()).child("steps").setValue(stepsData);

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builder.show();
            }
        });     //dialog box for entering steps




        return view;
    }

    public String calculateBMI(String height, String weight){
        double doubleHeight = Integer.parseInt(height);
        double doubleWeight = Integer.parseInt(weight);
        Log.d(TAG, "calculateBMI: USER HEIGHT = " + doubleHeight);
        Log.d(TAG, "calculateBMI: USER WEIGHT = " + doubleWeight);
        double doubleBMI = (doubleWeight / (doubleHeight * doubleHeight)) * 703;
        doubleBMI = Math.round((doubleBMI * 10)) / 10.0;
        Log.d(TAG, "calculateBMI: USER BMI = " + doubleBMI);
        String BMI = Double.toString(doubleBMI);

        return BMI;
    }

    public String calcWeightStatus(String BMI){
        double doubleBMI = Double.parseDouble(BMI);
        String weightStatus = "";
        if(doubleBMI < 18.5){
            weightStatus = "Underweight";
        }
        else if(doubleBMI >= 18.5 && doubleBMI < 25){
            weightStatus = "Normal";
        }
        else if (doubleBMI >= 25 && doubleBMI < 30){
            weightStatus = "Overweight";
        }
        else{
            weightStatus = "Obese";
        }
        return weightStatus;
    }

}