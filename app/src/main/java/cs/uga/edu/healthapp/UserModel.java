package cs.uga.edu.healthapp;

import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public String userKey, email, name;
    public List<StepsModel> steps;

    public UserModel() {
        steps = new ArrayList<>();
    }

    // etc

/*
    public void saveToFirebase(Firebase root) {
        Firebase userRef = root.child("users").child(userKey);
        userRef.setValue(this);
    }

    public void readFromFirebase(Firebase root) {
        // no clue how to do this lol, but you want to set this object's
        // fields to what's in the database for the user

        // Imagine that you're iterating through the steps entries in
        // the database
        for (IDontKnow stepEntry : iDoNotKnowWhatThisWouldBe) {
            StepsModel steps = new StepsModel(
                    // maybe something like this? This is a guess
                    stepEntry.get("date"),
                    stepEntry.get("timestamp"),
                    stepEntry.get("steps")
            );
            // remember, that constructor was a guess and almost
            // certainly isn't right
            steps.add(steps);
        }
    }
*/

}
