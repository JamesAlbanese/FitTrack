package fittrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    public List<WorkoutSession> sortByDateAscending(){
        return sessions.stream()
                       .sorted(Comparator.comparing(WorkoutSession::getDate).reversed())
                       .collect(Collectors.toList());
    }
}
