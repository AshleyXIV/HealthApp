package cs.uga.edu.healthapp;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment
{
    private ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    private ArrayList<String> mUsers;
    ArrayAdapter<String> adapter;

    /**
     * Lists all friends currently registered to firebase.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        listView = view.findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");
        mUsers = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getContext(), R.layout.friends_item, R.id.username, mUsers);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Replaces current fragment with the Friend Profile fragment.
             * Sends username of selected fragment to Friend Profile.
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String username = adapter.getItem(position);

                Fragment fragment = new FriendsProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
            }
        });

        ref.addValueEventListener(new ValueEventListener()
        {
            /**
             * Creates list of friends on listView.
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    User user = snapshot.getValue(User.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if(!user.getEmail().equals(firebaseUser.getEmail()))
                    {
                        mUsers.add(user.getName());

                    }
                }

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        return view;
    }

}