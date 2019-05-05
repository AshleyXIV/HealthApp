package cs.uga.edu.healthapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    /**
     * initializes UI components and firebase and transitions to the homeFragment on creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //displays the home fragment right after activity is started
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    /**
     * Check if user is signed in (non-null) and update UI accordingly (go back to login activity if user is not signed in)
     */
    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) { //if user is NOT logged in
            finish();   //finish current activity
            startActivity(new Intent(this, MainActivity.class));    //go to login activity if not logged in
        }
    }

    /**
     * listener for the bottom navigation bar; changes to a different fragment depending on what item is selected
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch(menuItem.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();  //create home fragment b/c that is fragment we want to show
                            break;
                        case R.id.navigation_trends:
                            selectedFragment = new TrendsFragment();
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfileFragment();
                            break;

                    }
                    //update the fragment container with the selected fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    /**
     * inflate the top menu bar to the toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);    //inflate the menu bar to the toolbar

        return true;
    }

    /**
     * if the menu item (logout) is selected, logout and go back to the login activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogout:   //if logout button is clicked, sign out
                FirebaseAuth.getInstance().signOut();
                finish();   //finish this current activity
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

        return true;
    }
}
