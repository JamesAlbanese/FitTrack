package fittrack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LogWorkoutPanel extends JPanel {

    //app and manager
    private final FitTrackApp app;
    private final FitTrackManager manager;


    //session info
    private JComboBox<TrainingSplit> splitCombo;
    private JComboBox<SplitDay> dayCombo;
    private JTextField dateField;
    private JTextField durationField;
    private JTextField notesField;


    //exercise builder fields
    private JTextField exerciseNameField;
    private JTextField setRepsField;
    private JTextField setWeightField;
    private JTextField exerciseNotesField;
    private JButton addSetButton;
    private JButton finalizeButton;
    private JPanel stagedSetsPanel;
    private JLabel stagedSetsLabel;
    private JPanel exerciseListPanel;
    private JLabel exerciseCountLabel;

    //exercise currently being built
    private Exercise currentExercise;

    //finalized exercise to be saved with the session
    private final List<Exercise> stagedExercises = new ArrayList<>();

    //cardio fields
    private JCheckBox includeCardioCheck;
    private JComboBox<String> cardioTypeCombo;
    private JTextField cardioDurationField;
    private JTextField cardioParam1Field;
    private JTextField cardioParam2Field;
    private JLabel cardioParam1Label;
    private JPanel cardioParam2Row;


    public LogWorkoutPanel(FitTrackApp app, FitTrackManager manager){
        this.app = app;
        this.manager = manager;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        buildUI();
    }

    private void buildUI(){
        add(buildHeader(), BorderLayout.NORTH);
        add(centerBuilder(), BorderLayout.CENTER);
        add(footerBuilder(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader(){
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JLabel title = new JLabel("Log Workout");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JButton backButton = new JButton("<- Back");
        backButton.addActionListener();
    }



}

