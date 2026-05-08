package fittrack;


/**
 * Represents a stairmaster cardio session.
 * Extends CardioSession with the stairmaster specific attribute:
 * speed in steps per minute.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-7-2026
 */
public class StairmasterSession extends CardioSession{

    private static final long serialVersionUID = 1L;

    private int speed;


    /**
     * Constructs a StairmasterSession with a duration and speed.
     *
     * @param duration duration in minutes
     * @param speed    speed in steps per minute
     * @throws NegativeValueException if speed is zero or negative
     */
    public StairmasterSession(int duration, int speed) throws NegativeValueException{
        if(speed <= 0){
            //custom exception
            throw new NegativeValueException("Speed", speed);
        }
        super(duration);
        this.speed = speed;
    }


    /**
     * Returns the display name for this cardio type.
     *
     * @return "Stairmaster"
     */
    @Override
    public String getCardioType(){
        return "Stairmaster";
    }

    /**
     * Returns a formatted summary of stairmaster specific attributes.
     *
     * @return string in the format "65 steps/min"
     */
    @Override
    public String getCardioSummary(){
        return speed + " mph";
    }


    /**
     * Returns the speed of this stairmaster session in steps per minute.
     *
     * @return speed in steps per minute
     */
    public int getSpeed(){
        return speed;
    }


    /**
     * Updates the speed of this stairmaster session.
     *
     * @param speed new speed in steps per minute
     * @throws NegativeValueException if speed is zero or negative
     */
    public void setSpeed(int speed) throws NegativeValueException{
        if(speed <= 0){
            //custom exception
            throw new NegativeValueException("Speed", speed);
        }
        this.speed = speed;
    }
}
