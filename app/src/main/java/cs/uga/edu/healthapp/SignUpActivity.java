package cs.uga.edu.healthapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignUpActivity";
    EditText editTextEmail, editTextPassword, editTextName, editTextWeight, editTextHeight;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    String email, password, name, height, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        progressBar = findViewById(R.id.progressbar);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }   //onCreate

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null)
        if(mAuth.getCurrentUser() != null){
            //handle the already logged in user
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSignUp:
                registerUser();
                break;
            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }   //onClick

    private void registerUser() {
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        name = editTextName.getText().toString().trim();
        height = editTextHeight.getText().toString().trim();
        weight = editTextWeight.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();   //show the error, force focus to a specific view
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){    //if email format is not valid
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){  //if password is too short
            editTextPassword.setError("Minimum password length is 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if(height.isEmpty()){
            editTextHeight.setError("Height is required");
            editTextHeight.requestFocus();
            return;
        }

        if(weight.isEmpty()){
            editTextWeight.setError("Weight is required");
            editTextWeight.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            sendUserData(email, name, height, weight); //send the name, height, weight values to the database
                            mAuth.signOut();
                            Toast.makeText(SignUpActivity.this, "User Registration Successful!", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            //do this to clear all the activities on the top of the stack and will open the new activity;
                            // this will prevent the user from going back to the  login screen if they press back
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(SignUpActivity.this, "Account is already created", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }   //registerUser

    /**
     * creates a fireDatabase object with a reference to the unique user ID. Under the unique user ID, fill out the
     * name, height, and weight fields.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendUserData(String email, String name, String height, String weight){
        Log.d(TAG, "sendUserData:method called");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d(TAG, "sendUserData:database instance called");
        DatabaseReference myRef = firebaseDatabase.getReference(Objects.requireNonNull(mAuth.getUid()));    //retrieve the unique user ID from the authentication section
        Log.d(TAG, "sendUserData:get reference to database");
        User user = new User(email, name, height, weight);     //assign name/height/weight values to user instance
        Log.d(TAG, "sendUserData:user values assigned");
        myRef.setValue(user);
        Log.d(TAG, "sendUserData:myref values set");
    }   //sendUserData
}
