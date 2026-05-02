package fittrack;

//Because an exercise can have 2 sets with different reps or weight it made sense for their to
//be an exercise set class that would represent individual sets that would be a property of an
//exercise

import java.io.Serializable;


public class ExerciseSet implements Serializable{

    private static final long serialVersionUID = 1L;

    private int reps;
    private double weight;


    public ExerciseSet(int reps, double weight){
        if(reps <= 0){
            //custom exception
        }
        if(weight < 0){
            //custom exception
        }
        this.reps = reps;
        this.weight = weight;
    }

    public int getReps(){
        return reps;
    }

    public void setReps(int reps){
        if(reps <= 0){
            //custom exception
        }

        this.reps = reps;
    }


    public double getWeight(){
        return weight;
    }


    public void setWeight(double weight){
        if(weight < 0){
            //custom exception
        }
        this.weight = weight;
    }


    @Override
    public String toString(){
        //Zero weight usually means a bodyweight exercise
        if(weight > 0){
            return reps + " reps @ " + weight + " lbs";
        }
        return reps + " reps (bodyweight)";
    }

}
