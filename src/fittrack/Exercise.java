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

    public Exercise(String name, int sets, int reps, double weight, String notes){
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        if(notes == null){
            this.notes = "";
        }
        this.notes = notes;
    }
}
