package cs.uga.edu.healthapp;

/**
 * User class with constructors and getters and setters for email, name, height, and weight
 */
public class User {
    public String email, name, height, weight;

    public User(){
        //required default constructor
    }

    public User(String email, String name, String height, String weight){
        this.email = email;
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
