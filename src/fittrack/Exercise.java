package fittrack;

import java.io.Serializable;
import java.util.*;

public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<ExerciseSet> sets;

    private Map<String, Integer> setFrequency;

    //Optional notes about exercise
    private String notes;

    public Exercise(String name){
        this.name = name;
        this.sets = new ArrayList<>();
        this.setFrequency = new HashMap<>();
        this.notes = "";
    }

    public static Exercise create(String name) throws InvalidWorkoutException{
        if(name == null || name.trim().isEmpty()){
            //custom exception
            throw new InvalidWorkoutException("Exercise must have a name");
        }

        return new Exercise(name);
    }

    public void addSet(int reps, double weight){
        if(reps <= 0){
            //custom exception
        }
        if(weight < 0){
            //custom exception
        }

        sets.add(new ExerciseSet(reps, weight));

        String key = reps + " x " + weight;
        setFrequency.put(key, setFrequency.getOrDefault(key, 0) + 1);
    }

    public void removeSet(int index){
        if(index < 0 || index >= sets.size()){
            //custom exception
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

    public String getName() {

        return name;
    }

    public void setName(String name) throws InvalidWorkoutException{
        if(name != null || name.trim().isEmpty()){
            //custom exception
            throw new InvalidWorkoutException("Exercise must have a valid name");
        }
        this.name = name;
    }

    public List<ExerciseSet> getSets() {

        return Collections.unmodifiableList(sets);
    }

    public Map<String, Integer> getSetFrequency(){
        return Collections.unmodifiableMap(setFrequency);
    }

    public int getTotalSets(){
        return sets.size();
    }

    public double getMaxWeight() {

        return sets.stream()
                   .mapToDouble(ExerciseSet::getWeight)
                   .max()
                   .orElse(0.0);

    }

    public boolean hasSets(){
        return !sets.isEmpty();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private static double parseWeight(String key){
        return Double.parseDouble(key.split(" x ")[1]);
    }

    private static int parseReps(String key){
        return Integer.parseInt(key.split(" x ")[0]);
    }

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
