package fittrack;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutSession implements Serializable {

    private static final long serialVersioUID = 1L;

    private LocalDate date;
    private int durationMins;
    private TrainingSplit split;
    private SplitDay splitDay;
    private List<Exercise> exercises;
    private CardioSession cardio;
    private String notes;

    public WorkoutSession(LocalDate date, int durationMins, TrainingSplit split, SplitDay splitDay) throws InvalidWorkoutException, NegativeValueException{
        if(date == null){
            //custom exception
            throw new InvalidWorkoutException("Workout Session must have valid date");
        }
        if(durationMins <= 0){
            //custom exception
            throw new NegativeValueException("Duration in Minutes", durationMins);

        }
        if(!split.containsDay(splitDay)){
            //custom exception
            throw new InvalidWorkoutException("'"+splitDay.getDisplayName()+"' is not a valid day for the "
            + split.getDisplayName()+ " split. Valid days are: "+ split.getDays().stream()
                                                                       .map(SplitDay::getDisplayName)
                                                                       .collect(Collectors.joining(", ")));
        }

        this.date = date;
        this.durationMins = durationMins;
        this.split = split;
        this.splitDay = splitDay;
        this.exercises = new ArrayList<>();
        this.cardio = null;
        this.notes = "";

    }
}
