package fittrack;

import java.io.Serializable;


/**
 * Abstract base class representing a cardio session performed during a workout.
 * All cardio types extend this class and must implement getCardioType()
 * and getCardioSummary() to describe their specific attributes.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-7-2026
 */
public abstract class CardioSession implements Serializable{

    private static final long serialVersionUID = 1L;

    private int duration;


    /**
     * Constructs a CardioSession with the given duration.
     *
     * @param duration duration in minutes
     * @throws NegativeValueException if duration is zero or negative
     */
    public CardioSession(int duration) throws NegativeValueException{
        if(duration <= 0){
            //custom exception
            throw new NegativeValueException("Duration", duration);

        }
        this.duration = duration;
    }


    /**
     * Returns the display name of this cardio type.
     * Each subclass provides its own implementation.
     *
     * @return cardio type name such as Treadmill, Stairmaster, or Bike
     */
    public abstract String getCardioType();


    /**
     * Returns a formatted summary of this cardio session's specific attributes.
     * Each subclass provides its own implementation.
     *
     * @return formatted detail string such as speed, incline, or resistance
     */
    public abstract String getCardioSummary();


    /**
     * Returns the duration of this cardio session in minutes.
     *
     * @return duration in minutes
     */
    public int getDuration(){
        return duration;
    }


    /**
     * Updates the duration of this cardio session.
     *
     * @param duration new duration in minutes
     * @throws NegativeValueException if duration is zero or negative
     */
    public void setDuration(int duration) throws NegativeValueException{
        if(duration <= 0){
            //custom exception
            throw new NegativeValueException("Duration", duration);
        }

        this.duration = duration;
    }


    /**
     * Returns a general summary combining the cardio type, duration,
     * and the subclass specific detail string.
     *
     * @return full cardio session summary
     */
    @Override
    public String toString(){
        return getCardioType() + " | " + duration + " min | " + getCardioSummary();
    }


}
