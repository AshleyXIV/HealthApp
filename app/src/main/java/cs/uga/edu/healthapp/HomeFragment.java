package cs.uga.edu.healthapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private Button btnBMI, btnSteps, btnCalories, btnWater, btnSleep;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

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
        final FirebaseUser user = mAuth.getCurrentUser();




        return view;
    }
}
