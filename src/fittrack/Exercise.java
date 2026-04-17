package fittrack;

import java.io.Serializable;

public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private int sets;

    private int reps;

    private double weight;

    //Optional notes about exercise
    private String notes;

    public Exercise(String name, int sets, int reps, double weight){
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.notes = "";

    }


    public static Exercise create(String name, int sets, int reps){
        // I'm gonna create a custom Exception class so that if the
        // arguments passed to exercise are invalid it will catch and
        //it can be handled somehow

        return new Exercise(name, sets, reps, 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString(){
        //some stringbuilder stuff to nicely format the actual exercise
        return "";
        
    }
}
