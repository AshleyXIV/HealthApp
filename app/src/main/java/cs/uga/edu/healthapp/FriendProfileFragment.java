package cs.uga.edu.healthapp;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendProfileFragment extends Fragment implements View.OnClickListener
{
    private TextView profileEmail, profileName, profileHeight, profileWeight, profileVerified;
    private Button profileTrends;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    String user;

    /**
     * Shows Friend Profile view. Uses username given by the bundle from the previous fragment to pull
     * the specified user's profile data and display it. Also allows the user to view the friend's
     * health data using the trends button.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_friend_profile, container, false);

        String username = getArguments().getString("username");
        user = username;

        profileEmail = view.findViewById(R.id.textViewEmail);
        profileName = view.findViewById(R.id.textViewName);
        profileHeight = view.findViewById(R.id.textViewHeight);
        profileWeight = view.findViewById(R.id.textViewWeight);
        profileVerified = view.findViewById(R.id.textViewVerified);
        profileTrends = view.findViewById(R.id.buttonTrends);

        profileTrends.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.orderByChild("name").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * Pulls friend data from database and displays it on the profile view.
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    String username = ds.child("name").getValue().toString();
                    String email = ds.child("email").getValue().toString();
                    String height = ds.child("height").getValue().toString();
                    String weight = ds.child("weight").getValue().toString();

                    profileEmail.setText("Email: " + email);
                    profileName.setText("Name: " + username);
                    profileHeight.setText("Height: " + height + " inches");
                    profileWeight.setText("Weight: " + weight + " lbs");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });



        return view;
    }

    /**
     * Lets currently logged in user view a selected friend's health data.
     * Pushes friend's username in a bundle so the new fragment can search and pull the required
     * data.
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        Fragment fragment = new FriendsTrendFragment();

        Bundle bundle = new Bundle();
        bundle.putString("username", user);
        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();

    }
}
