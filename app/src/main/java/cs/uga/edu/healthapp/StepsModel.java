package cs.uga.edu.healthapp;

public class StepsModel {

    public String dateLabel, date, stepsLabel, stepsWalked;

    StepsModel(){

    }

    StepsModel(String date, String stepsWalked){
        this.date = date;
        this.stepsWalked = stepsWalked;
    }
}