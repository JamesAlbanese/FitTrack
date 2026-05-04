package fittrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}
