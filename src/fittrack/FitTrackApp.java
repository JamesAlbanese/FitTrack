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


    public FitTrackApp(){
        super(TITLE);

        //initialize manager - load saved sessions from disk
        manager = new FitTrackManager();

        //initialize panels
        mainPanel = new MainPanel(this, manager);
        logWorkoutPanel = new LogWorkoutPanel(this, manager);

        //setup card layout container
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        container.add(mainPanel, MAIN_PANEL);
        container.add(logWorkoutPanel, LOG_WORKOUT_PANEL);

        //setup JFrame
        setContentPane(container);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(APP_WIDTH, APP_HEIGHT);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);

        //show MainPanel first
        showDashboard();
        setVisible(true);

    }

    public void showDashboard(){
        mainPanel.refresh();
        cardLayout.show(container, MAIN_PANEL);
    }

    public void showLogWorkout(){
        cardLayout.show(container, LOG_WORKOUT_PANEL);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(FitTrackApp::new);
    }
}
