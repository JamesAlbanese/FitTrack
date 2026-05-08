package fittrack;

import java.io.Serializable;
import java.util.*;


/**
 * Represents a single exercise performed during a workout session.
 * Each exercise holds a name, an optional note, an ordered list of
 * ExerciseSet objects, and a frequency map that groups sets by their
 * reps and weight combination.
 *
 * Sets are added individually via addSet(). The frequency map automatically
 * tracks how many times each unique reps and weight combination has been performed.
 * When displayed, sets are sorted by weight ascending, then by reps ascending
 * when weight is the same.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-6-2026
 */
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<ExerciseSet> sets;

    /**
     * Frequency map grouping sets by their reps and weight combination.
     * Key format is "reps x weight" for example "8 x 185.0".
     * Value is the number of times that combination has been performed.
     */
    private Map<String, Integer> setFrequency;

    //Optional notes about exercise
    private String notes;


    /**
     * Private constructor. Use the static create() factory method
     * to instantiate Exercise objects.
     *
     * @param name the exercise name
     */
    private Exercise(String name){
        this.name = name;
        this.sets = new ArrayList<>();
        this.setFrequency = new HashMap<>();
        this.notes = "";
    }


    /**
     * Creates an Exercise with a name only.
     * Sets must be added afterwards via addSet().
     *
     * @param name the name of the exercise
     * @return a new Exercise instance
     * @throws InvalidWorkoutException if the name is null or blank
     */
    public static Exercise create(String name) throws InvalidWorkoutException{
        if(name == null || name.trim().isEmpty()){
            //custom exception
            throw new InvalidWorkoutException("Exercise must have a name");
        }

        return new Exercise(name);
    }


    /**
     * Adds a new ExerciseSet to this exercise and updates the frequency map.
     * If a set with the same reps and weight already exists its count is
     * incremented by 1. Otherwise a new entry is added with a count of 1.
     *
     * @param reps   number of reps for this set
     * @param weight weight used in lbs, 0 for bodyweight
     * @throws NegativeValueException if reps is zero or negative, or weight is negative
     */
    public void addSet(int reps, double weight) throws NegativeValueException{
        if(reps <= 0){
            //custom exception
            throw new NegativeValueException("Reps", reps);
        }
        if(weight < 0){
            //custom exception
            throw new NegativeValueException("Weight", weight);
        }

        sets.add(new ExerciseSet(reps, weight));

        String key = reps + " x " + weight;
        setFrequency.put(key, setFrequency.getOrDefault(key, 0) + 1);
    }


    /**
     * Removes the set at the given index and updates the frequency map.
     * If the frequency count for that set drops to zero the entry is removed.
     *
     * @param index the index of the set to remove
     * @throws InvalidWorkoutException if the index is out of bounds
     */
    public void removeSet(int index) throws InvalidWorkoutException,NegativeValueException{
        if(index < 0 || index >= sets.size()){
            //custom exception
            throw new InvalidWorkoutException("Index not in range. Index doesn't point to valid exercise.");
        }

        ExerciseSet removed = sets.get(index);
        sets.remove(index);

        String key = removed.getReps() + " x " + removed.getWeight();
        int current = setFrequency.getOrDefault(key, 0);

        if(current <= 1){
            setFrequency.remove(key);
        }
        else{
            setFrequency.put(key, current - 1);
        }
    }


    /**
     * Returns the name of this exercise.
     *
     * @return exercise name
     */
    public String getName() {

        return name;
    }


    /**
     * Sets the name of this exercise.
     *
     * @param name the new exercise name
     * @throws InvalidWorkoutException if the name is null or blank
     */
    public void setName(String name) throws InvalidWorkoutException{
        if(name == null || name.trim().isEmpty()){
            //custom exception
            throw new InvalidWorkoutException("Exercise must have a valid name");
        }
        this.name = name;
    }


    /**
     * Returns an unmodifiable view of all sets in this exercise.
     *
     * @return unmodifiable list of ExerciseSet objects
     */
    public List<ExerciseSet> getSets() {
        return Collections.unmodifiableList(sets);
    }

    /**
     * Returns an unmodifiable view of the frequency map.
     * Each key is a reps and weight combination and each value
     * is how many times that combination was performed.
     *
     * @return unmodifiable frequency map
     */
    public Map<String, Integer> getSetFrequency(){
        return Collections.unmodifiableMap(setFrequency);
    }


    /**
     * Returns the total number of sets logged for this exercise.
     *
     * @return set count
     */
    public int getTotalSets(){
        return sets.size();
    }


    /**
     * Returns the heaviest weight used across all sets.
     * Used for personal record tracking.
     * Returns 0.0 if no sets exist or all sets are bodyweight.
     *
     * @return max weight in lbs
     */
    public double getMaxWeight() {
        return sets.stream()
                   .mapToDouble(ExerciseSet::getWeight)
                   .max()
                   .orElse(0.0);
    }


    /**
     * Returns true if this exercise has at least one set logged.
     *
     * @return true if sets list is not empty
     */
    public boolean hasSets(){
        return !sets.isEmpty();
    }


    /**
     * Returns any notes attached to this exercise.
     *
     * @return notes string
     */
    public String getNotes() {
        return notes;
    }


    /**
     * Sets optional notes for this exercise.
     *
     * @param notes the notes to attach
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }


    /**
     * Parses the weight value from a frequency map key string.
     * Key format is "reps x weight" for example "8 x 185.0".
     *
     * @param key the frequency map key
     * @return the weight as a double
     */
    private static double parseWeight(String key){
        return Double.parseDouble(key.split(" x ")[1]);
    }


    /**
     * Parses the reps value from a frequency map key string.
     * Key format is "reps x weight" for example "8 x 185.0".
     *
     * @param key the frequency map key
     * @return the reps as an int
     */
    private static int parseReps(String key){
        return Integer.parseInt(key.split(" x ")[0]);
    }


    /**
     * Returns a formatted summary of this exercise and its sets.
     * Sets are sorted by weight ascending then by reps ascending
     * when weight is the same. Each unique reps and weight combination
     * is displayed with its frequency count.
     *
     * @return formatted exercise string
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(name);

        if(!setFrequency.isEmpty()) {
            setFrequency.entrySet()
                    .stream()
                    .sorted(Comparator.comparingDouble((Map.Entry<String, Integer> e) -> parseWeight(e.getKey()))
                            .thenComparingInt((Map.Entry<String, Integer> e) -> parseReps(e.getKey())))
                    .forEach(entry -> {
                        String key = entry.getKey();
                        int freq = entry.getValue();
                        int reps = parseReps(key);
                        double weight = parseWeight(key);
                        sb.append("\n   ").append(freq)
                                .append(" x ").append(reps)
                                .append("(").append(weight).append("lbs)");
                    });
        }

        if(!notes.isEmpty()){
            sb.append("\n Note: ").append(notes);
        }

        return sb.toString();
        
    }
}
