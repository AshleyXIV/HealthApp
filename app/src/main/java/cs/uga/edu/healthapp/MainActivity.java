package cs.uga.edu.healthapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
    }   //onCreate

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textViewSignup:
                startActivity(new Intent(this, SignUpActivity.class));

                break;
        }
    }   //onClick
}
