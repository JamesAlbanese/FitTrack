package fittrack;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
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
        add(buildBody(), BorderLayout.CENTER);
        add(buildfooter(), BorderLayout.SOUTH);

    }

    private JPanel buildHeader(){
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JLabel title = new JLabel("Log Workout");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JButton backButton = new JButton("<- Back");
        backButton.addActionListener(e -> app.showDashboard());//going to create this in fittrackapp

        header.add(backButton, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        return header;
    }


    private JPanel buildBody(){
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        body.add(buildSessionInfoSection());//coming soon...
        body.add(Box.createVerticalStrut(10));
        body.add(buildExerciseSection());//coming soon...
        body.add(Box.createVerticalStrut(10));
        body.add(buildCardioSection());//coming soon...


        JScrollPane scroll = new JScrollPane(body);
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        scroll.setBorder(null);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }


    private JPanel buildSessionInfoSection(){
        JPanel card = buildSection("Session Info"); //helper coming like boldLabel in MainPanel

        splitCombo = new JComboBox<>(TrainingSplit.values());
        splitCombo.addActionListener(e -> refreshDayCombo());//helper comin soon

        dayCombo = new JComboBox<>();
        refreshDayCombo();

        dateField = new JTextField(LocalDate.now().toString());
        durationField = new JTextField("60");
        notesField = new JTextField();

        card.add(buildRow("Training Split", splitCombo));
        card.add(Box.createVerticalStrut(4));
        card.add("Day", dayCombo);
        card.add(Box.createVerticalStrut(4));
        card.add(buildRow("Date (YYYY-MM-DD)", dateField));
        card.add(Box.createVerticalStrut(4));
        card.add(buildRow("Duration (min)", durationField));
        card.add(Box.createVerticalStrut(4));
        card.add(buildRow("Notes", notesField));

        return card;
    }


    private void refreshDayCombo(){
        TrainingSplit selected = (TrainingSplit) splitCombo.getSelectedItem();
        dayCombo.removeAllItems();
        if(selected != null){
            for(SplitDay day: selected.getDays()){
                dayCombo.addItem(day);
            }
        }
    }

    private JPanel buildExerciseSection(){
        JPanel card = buildSection("Exercises");

        //first part(naming exercise)
        card.add(boldLabel("Step 1 - Name the Exercise"));
        card.add(Box.createVerticalStrut(4));

        exerciseNameField = new JTextField();
        card.add(buildRow("Exercise Name", exerciseNameField));
        card.add(Box.createVerticalStrut(4));

        JButton startButton = new JButton("Start Exercise");
        startButton.addActionListener(e -> handleStartExercise());
        card.add(startButton);
        card.add(Box.createVerticalStrut(10));

    }




}

