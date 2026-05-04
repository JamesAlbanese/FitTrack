package fittrack;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FitTrackManager {

    private List<WorkoutSession> sessions;

    public FitTrackManager(){
        try{
            sessions = FileHandler.loadSessions();
        }catch(IOException | ClassNotFoundException e){
            System.err.println("Couldn't load saved sessions: "+ e.getMessage());
            sessions = new ArrayList<>();
        }
    }


    public void addSession(WorkoutSession session) throws InvalidWorkoutException, IOException{
        if(session == null){
            throw new InvalidWorkoutException("Can't have a null workout session");
        }
        if(!session.hasExercises() && !session.hasCardio()){
            throw new InvalidWorkoutException("Session must have at least one exercise or cardio logged");
        }
        sessions.add(session);
        save();

    }


    public void removeSession(int index) throws InvalidWorkoutException, IOException{
        if(index < 0 || index >= sessions.size()){
            throw new InvalidWorkoutException("Index out of range. Index must point to valid session.");
        }
        sessions.remove(index);
        save();
    }

    public void save() throws IOException{
        FileHandler.saveSessions(sessions);
    }

    public List<WorkoutSession> getAllSessions(){
        return Collections.unmodifiableList(sessions);
    }

    public int getSessionCount(){
        return sessions.size();
    }

    public List<WorkoutSession> sortByDateDescending(){
        return sessions.stream()
                       .sorted(Comparator.comparing(WorkoutSession::getDate).reversed())
                       .collect(Collectors.toList());
    }


    public List<WorkoutSession> sortByDateAscending(){
        return sessions.stream()
                       .sorted(Comparator.comparing(WorkoutSession::getDate))
                       .collect(Collectors.toList());

    }


    public List<WorkoutSession> sortByDurationDescending(){
        return sessions.stream()
                       .sorted(Comparator.comparing(WorkoutSession::getDurationMins).reversed())
                       .collect(Collectors.toList());
    }


    public List<WorkoutSession> filterBySplit(TrainingSplit split){
        return sessions.stream()
                       .filter(s -> s.getSplit() == split)
                       .collect(Collectors.toList());
    }


    public List<WorkoutSession> filterBySplitDay(SplitDay splitDay){
        return sessions.stream()
                       .filter(s -> s.getSplitDay() == splitDay)
                       .collect(Collectors.toList());
    }


    public List<WorkoutSession> filterByHasCardio(){
        return sessions.stream()
                       .filter(s -> s.hasCardio())
                       .collect(Collectors.toList());
    }

    public List<WorkoutSession> filterByDateRange(LocalDate from, LocalDate to){
        return sessions.stream()
                       .filter(s -> !s.getDate().isBefore(from) && !s.getDate().isAfter(to))
                       .collect(Collectors.toList());

    }


    public List<WorkoutSession> searchByExerciseName(String name){
        String search = name.toLowerCase().trim();
        return sessions.stream()
                .filter(s -> s.getExercises()
                                            .stream()
                                            .anyMatch(e -> e.getName()
                                                                    .toLowerCase()
                                                                    .contains(search)))
                .collect(Collectors.toList());
    }

    public int getTotalCardioMins(){
        return sessions.stream()
                       .filter(WorkoutSession::hasCardio)
                       .mapToInt(s -> s.getCardio().getDuration())
                       .sum();
    }


    public double getAverageDuration(){
        return sessions.stream()
                       .mapToInt(WorkoutSession::getDurationMins)
                       .average()
                       .orElse(0.0);
    }

    public double getPersonalRecord(String name){
        String search = name.toLowerCase().trim();
        return sessions.stream()
                       .flatMap(s -> s.getExercises().stream())
                       .filter(e -> e.getName().toLowerCase().contains(search))
                       .mapToDouble(Exercise::getMaxWeight)
                       .max()
                       .orElse(0.0);
    }

    public Map<SplitDay, Long> getSessionCountPerSplitDay(){
        return sessions.stream()
                .collect(Collectors.groupingBy(WorkoutSession::getSplitDay,
                                                Collectors.counting()));
    }

    public Map<String, Integer> getTotalCardioMinutesByType(){
        return sessions.stream()
                .filter(WorkoutSession::hasCardio)
                .collect(Collectors.groupingBy(s -> s.getCardio().getCardioType(),
                        Collectors.summingInt(s -> s.getCardio().getDuration())));
    }


    public List<String> getAllExerciseNames(){
        return sessions.stream()
                .flatMap(s -> s.getExercises().stream())
                .map(Exercise::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public Map<TrainingSplit, List<WorkoutSession>> groupSessionBySplit(){
        return sessions.stream()
                .collect(Collectors.groupingBy(WorkoutSession::getSplit));
    }



}
