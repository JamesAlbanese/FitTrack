package fittrack;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Core logic manager for the FitTrack application.
 * Holds the master list of WorkoutSession objects and provides all operations
 * for adding, removing, sorting, filtering, searching, and computing statistics.
 * On construction sessions are automatically loaded from disk via FileHandler.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-7-2026
 */
public class FitTrackManager {

    private List<WorkoutSession> sessions;


    /**
     * Constructs a FitTrackManager and loads any previously saved sessions
     * from disk. If no save file exists or loading fails, starts with an
     * empty list.
     */
    public FitTrackManager(){
        try{
            sessions = FileHandler.loadSessions();
        }catch(IOException | ClassNotFoundException e){
            System.err.println("Couldn't load saved sessions: "+ e.getMessage());
            sessions = new ArrayList<>();
        }
    }


    /**
     * Adds a new WorkoutSession to the manager and saves to disk.
     *
     * @param session the session to add
     * @throws InvalidWorkoutException if the session is null or has no exercises or cardio
     * @throws IOException             if saving to disk fails
     */
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


    /**
     * Removes the session at the given index from the manager and saves to disk.
     *
     * @param index the index of the session to remove
     * @throws InvalidWorkoutException if the index is out of bounds
     * @throws IOException             if saving to disk fails
     */
    public void removeSession(int index) throws InvalidWorkoutException, IOException{
        if(index < 0 || index >= sessions.size()){
            throw new InvalidWorkoutException("Index out of range. Index must point to valid session.");
        }
        sessions.remove(index);
        save();
    }


    /**
     * Saves all current sessions to disk via FileHandler.
     *
     * @throws IOException if the file cannot be written
     */
    public void save() throws IOException{
        FileHandler.saveSessions(sessions);
    }


    /**
     * Returns an unmodifiable view of all sessions.
     *
     * @return unmodifiable list of all workout sessions
     */
    public List<WorkoutSession> getAllSessions(){
        return Collections.unmodifiableList(sessions);
    }


    /**
     * Returns the total number of sessions logged.
     *
     * @return session count
     */
    public int getSessionCount(){
        return sessions.size();
    }


    /**
     * Returns a new list of all sessions sorted by date, most recent first.
     *
     * @return sorted list of sessions
     */
    public List<WorkoutSession> sortByDateDescending(){
        return sessions.stream()
                       .sorted(Comparator.comparing(WorkoutSession::getDate).reversed())
                       .collect(Collectors.toList());
    }


    /**
     * Returns a new list of all sessions sorted by date, oldest first.
     *
     * @return sorted list of sessions
     */
    public List<WorkoutSession> sortByDateAscending(){
        return sessions.stream()
                       .sorted(Comparator.comparing(WorkoutSession::getDate))
                       .collect(Collectors.toList());

    }


    /**
     * Returns a new list of all sessions sorted by duration, longest first.
     *
     * @return sorted list of sessions
     */
    public List<WorkoutSession> sortByDurationDescending(){
        return sessions.stream()
                       .sorted(Comparator.comparing(WorkoutSession::getDurationMins).reversed())
                       .collect(Collectors.toList());
    }


    /**
     * Returns all sessions that match the given TrainingSplit.
     *
     * @param split the split to filter by
     * @return list of matching sessions
     */
    public List<WorkoutSession> filterBySplit(TrainingSplit split){
        return sessions.stream()
                       .filter(s -> s.getSplit() == split)
                       .collect(Collectors.toList());
    }


    /**
     * Returns all sessions that match the given SplitDay.
     *
     * @param splitDay the split day to filter by
     * @return list of matching sessions
     */
    public List<WorkoutSession> filterBySplitDay(SplitDay splitDay){
        return sessions.stream()
                       .filter(s -> s.getSplitDay() == splitDay)
                       .collect(Collectors.toList());
    }


/**
 * Returns all sessions that contain a cardio component.
 *
 * @return list of sessions with cardio
 **/
    public List<WorkoutSession> filterByHasCardio(){
        return sessions.stream()
                       .filter(s -> s.hasCardio())
                       .collect(Collectors.toList());
    }


    /**
     * Returns all sessions that fall within the given date range inclusive.
     *
     * @param from start date inclusive
     * @param to   end date inclusive
     * @return list of sessions within the range
     */
    public List<WorkoutSession> filterByDateRange(LocalDate from, LocalDate to){
        return sessions.stream()
                       .filter(s -> !s.getDate().isBefore(from) && !s.getDate().isAfter(to))
                       .collect(Collectors.toList());

    }


    /**
     * Searches all sessions for any that contain an exercise matching
     * the given name. The search is case insensitive and supports partial matches.
     *
     * @param name the name or partial name to search for
     * @return list of sessions containing a matching exercise
     */
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


    /**
     * Returns the total number of minutes spent in cardio across all sessions.
     *
     * @return total cardio minutes
     */
    public int getTotalCardioMins(){
        return sessions.stream()
                       .filter(WorkoutSession::hasCardio)
                       .mapToInt(s -> s.getCardio().getDuration())
                       .sum();
    }


    /**
     * Returns the average session duration in minutes across all sessions.
     * Returns 0.0 if no sessions exist.
     *
     * @return average duration in minutes
     */
    public double getAverageDuration(){
        return sessions.stream()
                       .mapToInt(WorkoutSession::getDurationMins)
                       .average()
                       .orElse(0.0);
    }


    /**
     * Returns the personal record which is the heaviest weight logged
     * for a given exercise name across all sessions.
     * Returns 0.0 if the exercise has never been logged.
     *
     * @param name the exercise name to find the PR for
     * @return heaviest weight logged for that exercise
     */
    public double getPersonalRecord(String name){
        String search = name.toLowerCase().trim();
        return sessions.stream()
                       .flatMap(s -> s.getExercises().stream())
                       .filter(e -> e.getName().toLowerCase().contains(search))
                       .mapToDouble(Exercise::getMaxWeight)
                       .max()
                       .orElse(0.0);
    }


    /**
     * Returns a map of each SplitDay to the number of times
     * it has been trained across all sessions.
     *
     * @return map of SplitDay to session count
     */
    public Map<SplitDay, Long> getSessionCountPerSplitDay(){
        return sessions.stream()
                .collect(Collectors.groupingBy(WorkoutSession::getSplitDay,
                                                Collectors.counting()));
    }


    /**
     * Returns a map of each cardio type name to the total minutes
     * spent doing that cardio type across all sessions.
     *
     * @return map of cardio type name to total minutes
     */
    public Map<String, Integer> getTotalCardioMinutesByType(){
        return sessions.stream()
                .filter(WorkoutSession::hasCardio)
                .collect(Collectors.groupingBy(s -> s.getCardio().getCardioType(),
                        Collectors.summingInt(s -> s.getCardio().getDuration())));
    }


    /**
     * Returns a list of all unique exercise names logged across all sessions
     * sorted alphabetically. Used to populate the PR lookup dropdown.
     *
     * @return sorted list of unique exercise names
     */
    public List<String> getAllExerciseNames(){
        return sessions.stream()
                .flatMap(s -> s.getExercises().stream())
                .map(Exercise::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }


    /**
     * Returns a map of each TrainingSplit to the list of sessions
     * that used that split.
     *
     * @return map of TrainingSplit to session list
     */
    public Map<TrainingSplit, List<WorkoutSession>> groupSessionBySplit(){
        return sessions.stream()
                .collect(Collectors.groupingBy(WorkoutSession::getSplit));
    }



}
