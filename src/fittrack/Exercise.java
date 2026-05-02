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

    public static Exercise create(String name){
        // I'm gonna create a custom Exception class so that if the
        // arguments passed to exercise are invalid it will catch and
        //it can be handled somehow
        if(name == null || name.trim().isEmpty()){
            //custom exception
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

    public void setName(String name) {
        if(name != null || name.trim().isEmpty()){
            //custom exception
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString(){
        //some stringbuilder stuff to nicely format the actual exercise
        return "";
        
    }
}
