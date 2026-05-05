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

        //second part(adding sets)
        card.add(boldLabel("Step 2 - Add Sets"));
        card.add(Box.createVerticalStrut(10));

        setRepsField = new JTextField();
        setWeightField = new JTextField();
        card.add(buildRow("Reps", setRepsField));
        card.add(Box.createVerticalStrut(4));
        card.add(buildRow("Weight", setWeightField));
        card.add(Box.createVerticalStrut(4));

        addSetButton = new JButton("+ Add Set");
        addSetButton.addActionListener(e -> handleAddSet());
        addSetButton.setEnabled(false);
        card.add(addSetButton);
        card.add(Box.createVerticalStrut(4));

        stagedSetsLabel = new JLabel("No sets added yet");
        stagedSetsLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        stagedSetsLabel.setForeground(Color.GRAY);
        card.add(stagedSetsLabel);
        card.add(Box.createVerticalStrut(4));

        stagedSetsPanel = new JPanel();
        stagedSetsPanel.setLayout(new BoxLayout(stagedSetsPanel, BoxLayout.Y_AXIS));
        card.add(stagedSetsPanel);
        card.add(Box.createVerticalStrut(10));

        //third part(finalizing exercise)
        card.add(boldLabel("Step 3 - Finalize Exercise"));
        card.add(Box.createVerticalStrut(4));
        exerciseNotesField = new JTextField();
        card.add(buildRow("Exercise Notes", exerciseNotesField));
        card.add(Box.createVerticalStrut(4));

        finalizeButton  = new JButton("✔ Finalize Exercise");
        finalizeButton.addActionListener(e -> handleFinalizeExercise());
        finalizeButton.setEnabled(false);
        card.add(finalizeButton);
        card.add(Box.createVerticalStrut(10));

        //finalized list of exercises
        card.add(boldLabel("Exercises add to Session"));
        card.add(Box.createVerticalStrut(4));

        exerciseCountLabel = new JLabel("0 exercises");
        exerciseCountLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        exerciseCountLabel.setForeground(Color.GRAY);
        card.add(exerciseCountLabel);
        card.add(Box.createVerticalStrut(4));

        exerciseListPanel = new JPanel();
        exerciseListPanel.setLayout(new BoxLayout(exerciseListPanel, BoxLayout.Y_AXIS));
        card.add(exerciseListPanel);

        return card;

    }


    private void handleStartExercise(){
        String name = exerciseNameField.getText().trim();
        try{
            currentExercise = Exercise.create(name);
            clearStagedSets();//coming soon
            addSetButton.setEnabled(true);
            finalizeButton.setEnabled(true);

            JOptionPane.showMessageDialog(this,
                    "Exercise '"+ name +"' started. Now add sets.",
                    "Exercise started",
                    JOptionPane.INFORMATION_MESSAGE);
        }catch(InvalidWorkoutException e){
            JOptionPane.showMessageDialog(this,
                    "Exercise name cannot be blank",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAddSet(){
        if(currentExercise == null){
            JOptionPane.showMessageDialog(this,
                    "Start and Exrcise first",
                    "No Exercise",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            int reps = Integer.parseInt(setRepsField.getText().trim());
            double weight = Double.parseDouble(setWeightField.getText().trim());
            currentExercise.addSet(reps, weight);
            refreshStagedSets();
            setRepsField.setText("");
            setWeightField.setText("");
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,
                    "Reps and Weight must be valid numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }catch(NegativeValueException e){
            JOptionPane.showMessageDialog(this,
                    "Invalid Set: "+ e.getMessage(),
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);

        }
    }


    public handleFinalizeExercise() {
        if (currentExercise == null) {
            JOptionPane.showMessageDialog(this,
                    "No exercise in progress",
                    "No exercise",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if(!currentExercise.hasSets()){
            JOptionPane.showMessageDialog(this,
                    "Add at least one set before finalizing",
                    "No sets",
                    JOptionPane.ERROR_MESSAGE);
        }
        String notes = exerciseNotesField.getText().trim();

        if(!notes.isEmpty()){
            currentExercise.setNotes(notes);
        }

        stagedExercises.add(currentExercise);
        currentExercise = null;
        refreshExerciseList();//coming soon
        resetExerciseBuilder();//coming soon
    }


    private void refreshStagedSets(){
        stagedSetsPanel.removeAll();
        if(currentExercise != null){
            List<ExerciseSet> sets = currentExercise.getSets();
            for(int i = 0; i < sets.size(); i++){
                stagedSetsPanel.add(new JLabel(" Set "+ (i + 1)
                + ": "+ sets.get(i).toString()));
            }
            stagedSetsLabel.setText(sets.size()+ " set(s) added");
        }
        stagedSetsPanel.revalidate();
        stagedSetsPanel.repaint();
    }

    private void clearStagedSets(){
        stagedSetsPanel.removeAll();
        stagedSetsLabel.setText("No sets added yet");
        stagedSetsPanel.revalidate();
        stagedSetsPanel.repaint();
    }

    private void refreshExerciseList(){
        exerciseListPanel.removeAll();
        for(int i = 0; i < stagedExercises.size(); i++){
            exerciseListPanel.add(buildFinalizedExerciseRow(i));//coming soon
            exerciseListPanel.add(Box.createVerticalStrut(4));
        }
        exerciseCountLabel.setText(stagedExercises.size() + " exercise(s)");
        exerciseListPanel.revalidate();
        exerciseListPanel.repaint();
    }


    private JPanel buildFinalizedExerciseRow(int index){
        Exercise exercise = stagedExercises.get(index);

        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));


        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        for(String line : exercise.toString().split("\n")){
            JLabel label = new JLabel(line);
            label.setFont(new Font("SansSerif", Font.PLAIN, 12));
            info.add(label);
        }

        JButton removeButton = new JButton("X");
        removeButton.setFont(new Font("SansSerif", Font.BOLD, 11));
        removeButton.addActionListener(e -> {
            stagedExercises.remove(index);
            refreshExerciseList();
        });

        row.add(info, BorderLayout.CENTER);
        row.add(removeButton, BorderLayout.EAST);
        return row;
    }


    private void resetExerciseBuilder(){
        exerciseNameField.setText("");
        setRepsField.setText("");
        setWeightField.setText("");
        exerciseNotesField.setText("");
        clearStagedSets();
        addSetButton.setEnabled(false);
        finalizeButton.setEnabled(false);
        currentExercise = null;
    }


    private JPanel buildCardioSection(){
        JPanel card = buildSection("Cardio (Optional)");//coming soon

        includeCardioCheck = new JCheckBox("Include a cardio block");
        includeCardioCheck.addActionListener(e -> toggleCardioCheck());//coming soon
        card.add(includeCardioCheck);
        card.add(Box.createVerticalStrut(6));

        cardioTypeCombo = new JComboBox<>(
                new String[]{"Treadmill", "Stairmaster", "Bike"});
        cardioTypeCombo.addActionListener(e -> refreshCardioLabels());//coming soon

        cardioDurationField = new JTextField();
        cardioParam1Field = new JTextField();
        cardioParam2Field = new JTextField();
        cardioParam1Label = new JLabel("Speed (mph)");

        cardioParam2Row = buildRow("Incline (%)", cardioParam2Field);

        card.add(buildRow("Type", cardioTypeCombo));
        card.add(Box.createVerticalStrut(4));
        card.add(buildRow("Duration (min)", cardioDurationField));
        card.add(Box.createVerticalStrut(4));
        card.add(buildRow("Speed / Level", cardioParam1Field));
        card.add(Box.createVerticalStrut(4));
        card.add(cardioParam2Row);

        setCardioFieldsEnabled(false);
        return card;

    }

    private void toggleCardioFields(){
        boolean enabled = includeCardioCheck.isSelected();
        setCardioFieldsEnabled(enabled);
        if(enabled){
            refreshCardioLabels();
        }
    }

    private void setCardioFieldsEnabled(boolean enabled){
        cardioTypeCombo.setEnabled(enabled);
        cardioDurationField.setEnabled(enabled);
        cardioParam1Field.setEnabled(enabled);
        cardioParam2Field.setEnabled(enabled);
    }

    private void refreshCardioLabels(){
        String type = (String) cardioTypeCombo.getSelectedItem();
        if(type == null) {
            return;
        }
        switch (type){
            case "Treadmill":
                cardioParam1Label.setText("Speed (min)");
                cardioParam2Row.setVisible(true);
                break;
            case "Stairmaster":
                cardioParam1Label.setText("Speed (steps/min)");
                cardioParam2Row.setVisible(false);
                break;
            case "Bike":
                cardioParam1Label.setText("Resistance (1-20)");
                cardioParam2Row.setVisible(false);
                break;

        }
    }

    private CardioSession buildCardioSession() throws NegativeValueException, InvalidWorkoutException{
        if(!includeCardioCheck.isSelected()){
            return null;
        }
        int duration = Integer.parseInt(cardioDurationField.getText().trim());
        String type = (String) cardioTypeCombo.getSelectedItem();
        switch(type){
            case "Treadmill":
                double speed = Double.parseDouble(cardioParam1Field.getText().trim());
                double incline = Double.parseDouble(cardioParam2Field.getText().trim());
                return new TreadmillSession(duration, speed, incline);
            case "Stairmaster":
                int stairSpeed = Integer.parseInt(cardioParam1Field.getText().trim());
                return new StairmasterSession(duration, stairSpeed);
            case "Bike":
                int resistance = Integer.parseInt(cardioParam1Field.getText().trim());
                return new BikeSession(duration, resistance);
            default:
                return null;
        }
    }

    private JPanel buildFooter(){
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        JButton saveButton = new JButton("Save Session");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        saveButton.addActionListener(e ->handleSaveSession());//coming soon

        footer.add(saveButton);
        return footer;
    }



}

