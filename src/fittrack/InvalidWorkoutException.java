package fittrack;

//I want this to be for something like that would make the workout invalid structurally
//Like them not including a date for their workout or an exercise not having a name included
//Things that don't make sense when tracking your workout
public class InvalidWorkoutException extends Exception{

    private static final long serialVersionUID = 1L;

    public InvalidWorkoutException(String message){
        super(message);
    }

}
