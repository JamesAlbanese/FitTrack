package fittrack;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Represents a single workout session logged by the user.
 * Contains a list of exercises, an optional cardio session,
 * a date, duration, training split, and split day.
 * Implements Serializable so sessions can be saved and loaded via FileHandler later.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-7-2026
 */
public class WorkoutSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDate date;
    private int durationMins;
    private TrainingSplit split;
    private SplitDay splitDay;
    private List<Exercise> exercises;
    private CardioSession cardio;
    private String notes;


    /**
     * Constructs a WorkoutSession with the required core fields.
     * Exercises and cardio can be added after construction.
     *
     * @param date         the date of the session
     * @param durationMins total session duration in minutes
     * @param split        the training split this session belongs to
     * @param splitDay     the specific day within the split
     * @throws InvalidWorkoutException if date is null or splitDay does not belong to the split
     * @throws NegativeValueException  if duration is zero or negative
     */
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


    /**
     * Adds a pre-built Exercise object to this session.
     *
     * @param exercise the exercise to add
     * @throws InvalidWorkoutException if the exercise is null
     */
    public void addExercise(Exercise exercise) throws InvalidWorkoutException{
        if(exercise == null){
            throw new InvalidWorkoutException("Exercise can't be null");
        }
        exercises.add(exercise);
    }


    /**
     * Creates and adds an exercise by name only.
     * Sets must be added to the exercise afterwards via addSet().
     *
     * @param name the name of the exercise
     * @throws InvalidWorkoutException if the name is null or blank
     */
    public void addExercise(String name) throws InvalidWorkoutException{
        if(name == null || name.trim().isEmpty()){
            throw new InvalidWorkoutException("Exercise name can't be null");
        }
        exercises.add(Exercise.create(name));
    }


    /**
     * Removes an exercise from this session by its index in the list.
     *
     * @param index the index of the exercise to remove
     * @throws InvalidWorkoutException if the index is out of bounds
     */
    public void removeExercise(int index) throws InvalidWorkoutException{
        if(index < 0 || index >= exercises.size()){
            throw new InvalidWorkoutException("Index not in range. Index doesn't point to valid exercise.");
        }
        exercises.remove(index);
    }


    /**
     * Returns true if this session has at least one exercise logged.
     *
     * @return true if exercises list is not empty
     */
    public boolean hasExercises(){
        return !exercises.isEmpty();
    }


    /**
     * Returns true if this session has a cardio component.
     *
     * @return true if cardio is not null
     */
    public boolean hasCardio(){
        return cardio != null;
    }


    /**
     * Returns the total number of sets performed across all exercises
     * in this session.
     *
     * @return total sets
     */
    public int getTotalSets(){
        return exercises.stream()
                        .mapToInt(Exercise::getTotalSets)
                        .sum();
    }


    /**
     * Returns the date of this session.
     *
     * @return session date
     */
    public LocalDate getDate(){
        return date;
    }


    /**
     * Sets the date of this session.
     *
     * @param date the new session date
     * @throws InvalidWorkoutException if the date is null
     */
    public void setDate(LocalDate date) throws InvalidWorkoutException{
        if(date == null){
            throw new InvalidWorkoutException("Date can't be null");
        }
        this.date = date;
    }


    /**
     * Returns the total duration of this session in minutes.
     *
     * @return duration in minutes
     */
    public int getDurationMins(){
        return durationMins;
    }


    /**
     * Updates the duration of this session.
     *
     * @param durationMins new duration in minutes
     * @throws NegativeValueException if duration is zero or negative
     */
    public void setDurationMins(int durationMins) throws NegativeValueException{
        if(durationMins <= 0){
            throw new NegativeValueException("Duration in Mins", durationMins);
        }
        this.durationMins = durationMins;
    }


    /**
     * Returns the training split associated with this session.
     *
     * @return training split
     */
    public TrainingSplit getSplit(){
        return split;
    }


    /**
     * Returns the specific split day for this session.
     *
     * @return split day
     */
    public SplitDay getSplitDay(){
        return splitDay;
    }


    /**
     * Updates the split day for this session.
     * The new day must belong to the session's current split.
     *
     * @param splitDay the new split day
     * @throws InvalidWorkoutException if the day does not belong to the split
     */
    public void setSplitDay(SplitDay splitDay) throws InvalidWorkoutException{
        if(!split.containsDay(splitDay)){
            throw new InvalidWorkoutException("'"+splitDay.getDisplayName()+"' is not a valid day for the "
                    + split.getDisplayName()+ " split. Valid days are: "+ split.getDays().stream()
                    .map(SplitDay::getDisplayName)
                    .collect(Collectors.joining(", ")));
        }
        this.splitDay = splitDay;
    }


    /**
     * Returns the cardio session or null if none was performed.
     *
     * @return cardio session or null
     */
    public CardioSession getCardio(){
        return cardio;
    }


    /**
     * Sets the cardio session for this workout.
     *
     * @param cardio the cardio session to attach, can be null to remove
     */
    public void setCardio(CardioSession cardio){
        this.cardio = cardio;
    }


    /**
     * Returns any notes attached to this session.
     *
     * @return notes string
     */
    public String getNotes(){
        return notes;
    }


    /**
     * Sets notes for this session.
     *
     * @param notes the notes to attach
     */
    public void setNotes(String notes){
        this.notes = notes;
    }


    /**
     * Returns a concise summary of this session including date, split day,
     * duration, exercise count, and cardio type if present.
     *
     * @return session summary string
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(date)
                .append(" | ").append(splitDay.getDisplayName())
                .append(" | ").append(durationMins).append(" min")
                .append(" | ").append(exercises.size()).append(" exercise(s)");
        if(hasCardio()){
            sb.append(" | ").append(cardio.getCardioType());
        }

        return sb.toString();
    }


    /**
     * Returns an unmodifiable view of the exercises in this session.
     *
     * @return unmodifiable list of exercises
     */
    public List<Exercise> getExercises() {
        return Collections.unmodifiableList(exercises);
    }

}
