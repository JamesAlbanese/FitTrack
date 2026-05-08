package fittrack;


/**
 * Represents a treadmill cardio session.
 * Extends CardioSession with treadmill specific attributes:
 * speed in mph and incline as a percentage.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-7-2026
 */
public class TreadmillSession extends CardioSession{

    private static final long serialVersionUID = 1L;

    private double speed;

    private double incline;


    /**
     * Constructs a TreadmillSession with duration, speed, and incline.
     *
     * @param duration duration in minutes
     * @param speed    speed in mph
     * @param incline  incline as a percentage, 0.0 means flat
     * @throws NegativeValueException if speed is zero or negative, or incline is negative
     */
    public TreadmillSession(int duration, double speed, double incline) throws NegativeValueException{
        if(speed <= 0){
            //custom exception
            throw new NegativeValueException("Speed", speed);
        }
        if(incline < 0){
            //custom exception
            throw new NegativeValueException("Incline", incline);
        }
        super(duration);
        this.speed = speed;
        this.incline = incline;
    }


    /**
     * Returns the display name for this cardio type.
     *
     * @return "Treadmill"
     */
    @Override
    public String getCardioType(){
        return "Treadmill";
    }


    /**
     * Returns a formatted summary of treadmill specific attributes.
     *
     * @return string in the format "6.5 mph @ 2.0% incline"
     */
    @Override
    public String getCardioSummary(){
        return speed + " mph @ " + incline + "% incline";
    }


    /**
     * Returns the speed of this treadmill session in mph.
     *
     * @return speed in mph
     */
    public double getSpeed(){
        return speed;
    }


    /**
     * Updates the speed of this treadmill session.
     *
     * @param speed new speed in mph
     * @throws NegativeValueException if speed is zero or negative
     */
    public void setSpeed(double speed) throws NegativeValueException{
        if(speed <= 0){
            //custom exception
            throw new NegativeValueException("Speed", speed);
        }

        this.speed = speed;
    }


    /**
     * Returns the incline of this treadmill session as a percentage.
     *
     * @return incline percentage
     */
    public double getIncline(){
        return incline;
    }


    /**
     * Updates the incline of this treadmill session.
     *
     * @param incline new incline percentage
     * @throws NegativeValueException if incline is negative
     */
    public void setIncline(double incline) throws NegativeValueException{
        if(incline < 0){
            //custom exception
            throw new NegativeValueException("Incline", incline);
        }

        this.incline = incline;
    }

}
