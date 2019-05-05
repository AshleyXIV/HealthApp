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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileUpdateFragment extends Fragment implements View.OnClickListener
{
    private String name, weight, height;
    private EditText editName, editWeight, editHeight;
    private Button updateProfile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    /**
     * Updates Profile of currently logged in user from user input.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        editName = view.findViewById(R.id.editTextName);
        editWeight = view.findViewById(R.id.editTextWeight);
        editHeight = view.findViewById(R.id.editTextHeight);
        updateProfile = view.findViewById(R.id.buttonProfileUpdate);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        updateProfile.setOnClickListener(this);


        return view;
    }

    /**
     * Updates user's name, weight, and height. Will not update a certain field if that field is empty.
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users").child(mAuth.getUid());

        name = editName.getText().toString().trim();
        weight = editWeight.getText().toString().trim();
        height = editHeight.getText().toString().trim();

        if(name.isEmpty() && weight.isEmpty() && height.isEmpty())
        {
            Toast.makeText(getContext(), "All Fields Are Empty", Toast.LENGTH_SHORT).show();

        }
        else {
            if (!name.isEmpty()) {
                databaseReference.child("name").setValue(name);
            }
            if (!weight.isEmpty()) {
                databaseReference.child("weight").setValue(weight);
            }
            if (!height.isEmpty()) {
                databaseReference.child("height").setValue(height);
            }

            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
