package fittrack;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Panel for logging a new WorkoutSession.
 * Guides the user through three steps: naming an exercise, adding sets
 * one by one, and finalizing the exercise. Also supports an optional
 * cardio block and saves the completed session via FitTrackManager.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-8-2026
 */
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


    /**
     * Constructs the LogWorkoutPanel with references to the app and manager.
     *
     * @param app     the main application frame
     * @param manager the core logic manager
     */
    public LogWorkoutPanel(FitTrackApp app, FitTrackManager manager){
        this.app = app;
        this.manager = manager;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        buildUI();
    }


    /**
     * Builds and assembles all UI sections of the log workout panel.
     */
    private void buildUI(){
        add(buildHeader(), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);

    }


    /**
     * Builds the top header bar with the panel title and back button.
     *
     * @return header JPanel
     */
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


    /**
     * Builds the scrollable body containing all three form sections:
     * session info, exercise builder, and optional cardio.
     *
     * @return scrollable body JPanel
     */
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


    /**
     * Builds the session info section with split, day, date, duration, and notes fields.
     *
     * @return session info JPanel
     */
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


    /**
     * Repopulates the day dropdown based on the currently selected split.
     * Called automatically when the split selection changes.
     */
    private void refreshDayCombo(){
        TrainingSplit selected = (TrainingSplit) splitCombo.getSelectedItem();
        dayCombo.removeAllItems();
        if(selected != null){
            for(SplitDay day: selected.getDays()){
                dayCombo.addItem(day);
            }
        }
    }


    /**
     * Builds the exercise builder section with the three step flow.
     * Step 1 names the exercise, Step 2 adds sets, Step 3 finalizes it.
     * Also shows the list of all finalized exercises added to the session.
     *
     * @return exercise section JPanel
     */
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


    /**
     * Handles the Start Exercise button.
     * Creates a new Exercise with the entered name and enables
     * the set input fields and finalize button.
     */
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


    /**
     * Handles the Add Set button.
     * Reads reps and weight from the input fields and calls addSet()
     * on the current exercise. Refreshes the staged sets display after each add.
     */
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


    /**
     * Handles the Finalize Exercise button.
     * Attaches notes to the current exercise, adds it to the staged list,
     * refreshes the finalized exercise list, and resets the builder.
     */
    public void handleFinalizeExercise() {
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


    /**
     * Rebuilds the staged sets display for the current exercise in progress.
     * Shows each set on its own line with its number, reps, and weight.
     */
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


    /**
     * Clears the staged sets display and resets the sets label.
     * Called when starting a new exercise or resetting the form.
     */
    private void clearStagedSets(){
        stagedSetsPanel.removeAll();
        stagedSetsLabel.setText("No sets added yet");
        stagedSetsPanel.revalidate();
        stagedSetsPanel.repaint();
    }


    /**
     * Rebuilds the finalized exercise list showing all exercises
     * added to the session so far. Each row includes a remove button.
     */
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


    /**
     * Builds a single row displaying a finalized exercise with a remove button.
     * Uses the exercise toString() split by newline to show each set on its own line.
     *
     * @param index the index of the exercise in the staged exercises list
     * @return finalized exercise row JPanel
     */
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


    /**
     * Resets the exercise builder fields after an exercise is finalized.
     * Clears all input fields, wipes the staged sets display, and
     * disables the add set and finalize buttons.
     */
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


    /**
     * Builds the optional cardio section with a checkbox to enable it.
     * Shows fields for cardio type, duration, speed or resistance, and
     * incline for Treadmill only. All fields start disabled.
     *
     * @return cardio section JPanel
     */
    private JPanel buildCardioSection(){
        JPanel card = buildSection("Cardio (Optional)");//coming soon

        includeCardioCheck = new JCheckBox("Include a cardio block");
        includeCardioCheck.addActionListener(e -> toggleCardioFields());//coming soon
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


    /**
     * Enables or disables all cardio input fields based on the checkbox state.
     * Also refreshes the cardio labels when enabling.
     */
    private void toggleCardioFields(){
        boolean enabled = includeCardioCheck.isSelected();
        setCardioFieldsEnabled(enabled);
        if(enabled){
            refreshCardioLabels();
        }
    }


    /**
     * Enables or disables all four cardio input components at once.
     *
     * @param enabled true to enable, false to disable
     */
    private void setCardioFieldsEnabled(boolean enabled){
        cardioTypeCombo.setEnabled(enabled);
        cardioDurationField.setEnabled(enabled);
        cardioParam1Field.setEnabled(enabled);
        cardioParam2Field.setEnabled(enabled);
    }


    /**
     * Updates the first cardio parameter label and shows or hides the
     * incline row based on the selected cardio type.
     * Treadmill shows speed and incline. Stairmaster and Bike show speed or resistance only.
     */
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


    /**
     * Builds and returns a CardioSession from the current cardio input fields.
     * Returns null if the include cardio checkbox is not selected.
     *
     * @return CardioSession or null
     * @throws NegativeValueException  if any numeric value is invalid
     * @throws InvalidWorkoutException if session data is structurally invalid
     */
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


    /**
     * Builds the bottom footer containing the Save Session button.
     *
     * @return footer JPanel
     */
    private JPanel buildFooter(){
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        JButton saveButton = new JButton("Save Session");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        saveButton.addActionListener(e ->handleSaveSession());//coming soon

        footer.add(saveButton);
        return footer;
    }


    /**
     * Handles the Save Session button.
     * Reads all session info fields, builds the WorkoutSession, attaches
     * all staged exercises and optional cardio, then saves via FitTrackManager.
     * Shows a success popup on save or an error popup if anything is invalid.
     */
    private void handleSaveSession(){
        try{
            LocalDate date = LocalDate.parse(dateField.getText().trim());
            int duration = Integer.parseInt(durationField.getText().trim());
            TrainingSplit split = (TrainingSplit) splitCombo.getSelectedItem();
            SplitDay day = (SplitDay) dayCombo.getSelectedItem();

            WorkoutSession session = new WorkoutSession(date, duration, split, day);
            session.setNotes(notesField.getText().trim());

            for(Exercise exercise: stagedExercises){
                session.addExercise(exercise);
            }

            CardioSession cardio = buildCardioSession();
            if(cardio != null){
                session.setCardio(cardio);
            }
            manager.addSession(session);

            JOptionPane.showMessageDialog(this,
                    "Session saved successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            resetForm();
            app.showDashboard();//coming in FitTrackApp

        }catch(DateTimeParseException e){
            JOptionPane.showMessageDialog(this,
                    "Invalid date format. Use YYYY-MM-DD.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Duration and weight must be valid numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NegativeValueException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid value: " + e.getMessage(),
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        } catch (InvalidWorkoutException e) {
            JOptionPane.showMessageDialog(this,
                    "Workout error: " + e.getMessage(),
                    "Invalid Workout",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Could not save to file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            revalidate();
            repaint();
        }
    }


    /**
     * Resets all form fields back to their default state.
     * Clears exercises, cardio, and all input fields.
     * Called after a session is saved successfully.
     */
    private void resetForm(){
        dateField.setText(LocalDate.now().toString());
        durationField.setText("60");
        notesField.setText("");
        stagedExercises.clear();
        currentExercise = null;
        refreshExerciseList();
        clearStagedSets();
        addSetButton.setEnabled(false);
        finalizeButton.setEnabled(false);
        includeCardioCheck.setSelected(false);
        setCardioFieldsEnabled(false);
        cardioDurationField.setText("");
        cardioParam1Field.setText("");
        cardioParam2Field.setText("");
    }


    /**
     * Builds a titled section panel used as a container for each form section.
     * Stacks components vertically using BoxLayout.
     *
     * @param title the section title shown in the border
     * @return section JPanel
     */
    private JPanel buildSection(String title){
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        section.setBorder(BorderFactory.createTitledBorder(title));
        return section;
    }


    /**
     * Builds a labeled form row pairing a text label on the left
     * with an input component on the right.
     *
     * @param textLabel the label text
     * @param component the input component
     * @return form row JPanel
     */
    private JPanel buildRow(String textLabel, JComponent component){
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(textLabel);
        label.setPreferredSize(new Dimension(140, 24));

        row.add(label, BorderLayout.WEST);
        row.add(component, BorderLayout.CENTER);
        return row;
    }


    /**
     * Creates a bold JLabel used for step headings and section labels.
     *
     * @param text the label text
     * @return bold JLabel
     */
    private JLabel boldLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }



}

