package fittrack;

//Because an exercise can have 2 sets with different reps or weight it made sense for their to
//be an exercise set class that would represent individual sets that would be a property of an
//exercise

import java.io.Serializable;


/**
 * Represents a single set performed within an Exercise.
 * Each set tracks its own rep count and weight independently,
 * allowing exercises with varying weights across sets to be recorded accurately.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-6-2026
 */
public class ExerciseSet implements Serializable{

    private static final long serialVersionUID = 1L;

    private int reps;
    private double weight;


    /**
     * Constructs an ExerciseSet with a rep count and weight.
     *
     * @param reps   number of reps performed
     * @param weight weight used in lbs, 0 for bodyweight
     * @throws NegativeValueException if reps is zero or negative, or weight is negative
     */
    public ExerciseSet(int reps, double weight)throws NegativeValueException{
        if(reps <= 0){
            //custom exception
            throw new NegativeValueException("Reps",reps);
        }
        if(weight < 0){
            //custom exception
            throw new NegativeValueException("Weight",weight);
        }
        this.reps = reps;
        this.weight = weight;
    }


    /**
     * Returns the number of reps in this set.
     *
     * @return rep count
     */
    public int getReps(){
        return reps;
    }


    /**
     * Updates the rep count for this set.
     *
     * @param reps new rep count
     * @throws NegativeValueException if reps is zero or negative
     */
    public void setReps(int reps)throws NegativeValueException{
        if(reps <= 0){
            //custom exception
            throw new NegativeValueException("Reps", reps);
        }

        this.reps = reps;
    }


    /**
     * Returns the weight used in this set in lbs.
     *
     * @return weight in lbs
     */
    public double getWeight(){
        return weight;
    }


    /**
     * Updates the weight for this set.
     *
     * @param weight new weight in lbs
     * @throws NegativeValueException if weight is negative
     */
    public void setWeight(double weight)throws NegativeValueException{
        if(weight < 0){
            //custom exception
            throw new NegativeValueException("Weight", weight);
        }
        this.weight = weight;
    }


    /**
     * Returns a formatted string representing this set.
     * Shows bodyweight if weight is zero.
     *
     * @return string in the format "8 reps @ 185.0 lbs" or "8 reps (bodyweight)"
     */
    @Override
    public String toString(){
        //Zero weight usually means a bodyweight exercise
        if(weight > 0){
            return reps + " reps @ " + weight + " lbs";
        }
        return reps + " reps (bodyweight)";
    }

}
