package fittrack;


/**
 * Thrown when a numeric field is assigned a negative or invalid value.
 * Examples include negative reps, negative weight, zero duration,
 * or zero speed on a cardio session.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-7-2026
 */
public class NegativeValueException extends Exception{

    private static final long serialVersionUID = 1L;

    private String fieldName;
    private double invalidValue;


    /**
     * Constructs a NegativeValueException with the field name and invalid value.
     * Automatically generates a detail message.
     *
     * @param fieldName    the name of the field that received an invalid value
     * @param invalidValue the value that was rejected
     */
    public NegativeValueException(String fieldName, double invalidValue){
        super(fieldName+ " cannot be negative or zero. Got: "+invalidValue);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    /**
     * Returns the name of the field that triggered this exception.
     *
     * @return field name string
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Returns the invalid value that was provided.
     *
     * @return the invalid value
     */
    public double getInvalidValue() {
        return invalidValue;
    }
}
