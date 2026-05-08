package fittrack;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Handles saving and loading of WorkoutSession objects to and from
 * a binary file using Java object serialization.
 * All sessions are stored in a single file defined by WORKOUT_BINARY.
 * The file is created automatically if it does not exist on first save.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-7-2026
 */
public class FileHandler {

    private static final String WORKOUT_BINARY = "fittrack_data.dat";


    /**
     * Private constructor to prevent instantiation.
     * All methods in this class are static.
     */
    private FileHandler(){}


    /**
     * Saves the full list of WorkoutSession objects to disk.
     * Overwrites any previously saved data.
     *
     * @param sessions the list of sessions to save
     * @throws IOException if the file cannot be written to
     */
    public static void saveSessions(List<WorkoutSession> sessions) throws IOException {
        try(FileOutputStream FOS = new FileOutputStream(WORKOUT_BINARY);
            ObjectOutputStream OOS = new ObjectOutputStream(FOS)){

            for(WorkoutSession session : sessions){
                OOS.writeObject(session);
            }
        }
    }


    /**
     * Loads all WorkoutSession objects from disk.
     * Returns an empty list if the data file does not exist yet.
     * Reads objects one at a time until EOFException signals the end of the file.
     *
     * @return list of loaded WorkoutSession objects, or empty list if no file exists
     * @throws IOException            if the file exists but cannot be read
     * @throws ClassNotFoundException if a deserialized object's class is not found
     */
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


    /**
     * Deletes the data file from disk.
     * Returns true if the file was successfully deleted or did not exist.
     *
     * @return true if deletion succeeded or file did not exist, false otherwise
     */
    public static boolean deleteData(){
        File file = new File(WORKOUT_BINARY);
        if(!file.exists()){
            return true;
        }
        return file.delete();
    }


    /**
     * Returns true if a save file currently exists on disk.
     *
     * @return true if the data file exists
     */
    public static boolean saveFileExists(){
        return new File(WORKOUT_BINARY).exists();
    }


    /**
     * Returns the name of the data file used for persistence.
     *
     * @return data file name string
     */
    public static String getDataFileName(){
        return WORKOUT_BINARY;
    }
}
