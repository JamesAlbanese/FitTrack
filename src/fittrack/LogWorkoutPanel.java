package fittrack;

import javax.swing.*;

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




}

