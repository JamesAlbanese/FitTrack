package fittrack;


/**
 * Represents a stationary bike cardio session.
 * Extends CardioSession with the bike specific attribute:
 * resistance level.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-7-2026
 */
public class BikeSession extends CardioSession{

    private static final long serialVersionUID = 1L;

    private double resistance;


    /**
     * Constructs a BikeSession with a duration and resistance level.
     *
     * @param duration   duration in minutes
     * @param resistance resistance level
     * @throws NegativeValueException if resistance is zero or negative
     */
    public BikeSession(int duration, double resistance) throws NegativeValueException{
        if(resistance <= 0){
            //custom exception
            throw new NegativeValueException("Resistance", resistance);
        }
        super(duration);
        this.resistance = resistance;
    }


    /**
     * Returns the display name for this cardio type.
     *
     * @return "Bike"
     */
    @Override
    public String getCardioType(){
        return "Bike";
    }


    /**
     * Returns a formatted summary of bike specific attributes.
     *
     * @return string in the format "12.0 resistance"
     */
    @Override
    public String getCardioSummary(){
        return resistance + " resistance";
    }


    /**
     * Returns the resistance level of this bike session.
     *
     * @return resistance level
     */
    public double getResistance(){
        return resistance;
    }


    /**
     * Updates the resistance level of this bike session.
     *
     * @param resistance new resistance level
     * @throws NegativeValueException if resistance is negative
     */
    public void setResistance(double resistance) throws NegativeValueException{
        if(resistance < 0){
            //custom exception
            throw new NegativeValueException("Resistance", resistance);
        }
        this.resistance = resistance;
    }
}
