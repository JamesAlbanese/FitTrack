package fittrack;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

//File handler class handles writing temporary workout sessions to be saved later
public class FileHandler {

    private static final String WORKOUT_BINARY = "fittrack_data.dat";

    //prevents instantiation of filehandler objects
    private FileHandler(){}

    public static void saveSessions(List<WorkoutSession> sessions) throws IOException {
        try(FileOutputStream FOS = new FileOutputStream(WORKOUT_BINARY);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS)){

            for(WorkoutSession session : sessions){
                OOS.writeObject(session);
            }
        }
    }


    public static List<WorkoutSession> loadSessions() throws IOException, ClassNotFoundException{

        List<WorkoutSession> sessions = new ArrayList<>();
        File file = new File(WORKOUT_BINARY);

        if(!file.exists()){
            return sessions;
        }

        try(FileInputStream FIS = new FileInputStream(file);
            ObjectInputStream OIS = new ObjectInputStream(FIS)){

            while(true){
                try{
                    WorkoutSession session = (WorkoutSession) OIS.readObject();
                    sessions.add(session);
                }catch(EOFException e){
                    break;
                }
            }
        }finally{
            System.out.println("Session load complete. Total loaded: "+ sessions.size());
        }

        return sessions;
    }
}
