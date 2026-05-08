package fittrack;


/**
 * Thrown when a WorkoutSession or Exercise is created or modified
 * with structurally invalid data.
 * Examples include a session with no date, an exercise with no name,
 * or a split day that does not belong to the selected split.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-7-2026
 */
public class InvalidWorkoutException extends Exception{

    private static final long serialVersionUID = 1L;


    /**
     * Constructs an InvalidWorkoutException with a detail message.
     *
     * @param message description of why the exception was thrown
     */
    public InvalidWorkoutException(String message){
        super(message);
    }

}
