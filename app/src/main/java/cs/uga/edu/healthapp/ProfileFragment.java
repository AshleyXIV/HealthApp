package cs.uga.edu.healthapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private TextView profileEmail, profileName, profileHeight, profileWeight;
    private Button profileUpdate;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileEmail = view.findViewById(R.id.textViewEmail);
        profileName = view.findViewById(R.id.textViewName);
        profileHeight = view.findViewById(R.id.textViewHeight);
        profileWeight = view.findViewById(R.id.textViewWeight);
        profileUpdate = view.findViewById(R.id.buttonProfileUpdate);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //reference the database with the specific user's unique id
        DatabaseReference databaseReference = firebaseDatabase.getReference(mAuth.getUid());
        //event listener for referencing the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  //whenever there is a change in the database or when the app just starts
                User user = dataSnapshot.getValue(User.class);  //retrieve all of the user's info from the database and place in user object

                    profileEmail.setText("Email: " + user.getEmail());
                    profileName.setText("Name: " + user.getName());
                    profileHeight.setText("Height: " + user.getHeight());
                    profileWeight.setText("Weight: " + user.getWeight());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { //if there is some database error, show toast error
                Toast.makeText(getActivity(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
