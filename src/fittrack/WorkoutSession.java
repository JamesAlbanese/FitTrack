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

    public void addExercise(Exercise exercise) throws InvalidWorkoutException{
        if(exercise == null){
            throw new InvalidWorkoutException("Exercise can't be null");
        }
        exercises.add(exercise);
    }

    public void addExercise(String name) throws InvalidWorkoutException{
        if(name == null || name.trim().isEmpty()){
            throw new InvalidWorkoutException("Exercise name can't be null");
        }
        exercises.add(Exercise.create(name));
    }

    public void removeExercise(int index) throws InvalidWorkoutException{
        if(index < 0 || index >= exercises.size()){
            throw new InvalidWorkoutException("Index not in range. Index doesn't point to valid exercise.");
        }
        exercises.remove(index);
    }

    public boolean hasExercises(){
        return !exercises.isEmpty();
    }

    public boolean hasCardio(){
        return cardio != null;
    }

    public int getTotalSets(){
        return exercises.stream()
                        .mapToInt(Exercise::getTotalSets)
                        .sum();
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date) throws InvalidWorkoutException{
        if(date == null){
            throw new InvalidWorkoutException("Date can't be null");
        }
        this.date = date;
    }

    public int getDurationMins(){
        return durationMins;
    }

    public void setDurationMins(int durationMins) throws NegativeValueException{
        if(durationMins <= 0){
            throw new NegativeValueException("Duration in Mins", durationMins);
        }
        this.durationMins = durationMins;
    }

    public TrainingSplit getSplit(){
        return split;
    }


    public SplitDay getSplitDay(){
        return splitDay;
    }

    public void setSplitDay(SplitDay splitDay) throws InvalidWorkoutException{
        if(!split.containsDay(splitDay)){
            throw new InvalidWorkoutException("'"+splitDay.getDisplayName()+"' is not a valid day for the "
                    + split.getDisplayName()+ " split. Valid days are: "+ split.getDays().stream()
                    .map(SplitDay::getDisplayName)
                    .collect(Collectors.joining(", ")));
        }
        this.splitDay = splitDay;
    }

    public CardioSession getCardio(){
        return cardio;
    }

    public void setCard(CardioSession cardio){
        this.cardio = cardio;
    }

    public String getNotes(){
        return notes;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

}
