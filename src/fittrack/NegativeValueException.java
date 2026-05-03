package fittrack;

public class NegativeValueException extends Exception{

    private static final long serialVersionUID = 1L;

    private String fieldName;
    private double invalidValue;

    public NegativeValueException(String fieldName, double invalidValue){
        super(fieldName+ " cannot be negative or zero. Got: "+invalidValue);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
}
