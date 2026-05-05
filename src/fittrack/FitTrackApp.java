package fittrack;

import javax.swing.*;
import java.awt.*;

public class FitTrackApp extends JFrame {

    private static final String TITLE = "FitTrack";

    private static final int APP_WIDTH = 900;
    private static final int APP_HEIGHT = 600;


    private final FitTrackManager manager;

    private final MainPanel mainPanel;

    private final LogWorkoutPanel logWorkoutPanel;

    private final CardLayout cardLayout;

    private final JPanel container;


    private static final String MAIN_PANEL = "MAIN";
    private static final String LOG_WORKOUT_PANEL = "LOG_WORKOUT";
}
