package fittrack;

import com.sun.tools.javac.util.List;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class MainPanel extends JPanel {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    private final FitTrackApp app;
    private final FitTrackManager manager;

    private JPanel sessionListPanel;
    private JPanel statsPanel;
    private JComboBox<String> prCombo;

    public MainPanel(FitTrackApp app, FitTrackManager manager){
        this.app = app;
        this.manager = manager;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        buildUI();
    }

    private void buildUI(){
        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader(){
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JLabel title = new JLabel("FitTrack");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JButton logButton = new JButton("+ Log Workout");
        logButton.addActionListener(/*show workout log*/);

        header.add(title, BorderLayout.WEST);
        header.add(logButton, BorderLayout.EAST);
        return header;
    }

    private JSplitPane buildCenter() {
        //Left side - list of sessions

        sessionListPanel = new JPanel();
        sessionListPanel.setLayout(new BoxLayout(sessionListPanel, BoxLayout.Y_AXIS));

        JScrollPane leftScroll = new JScrollPane(sessionListPanel);
        leftScroll.setBorder(BorderFactory.createTitledBorder("Sessions"));
        leftScroll.getVerticalScrollBar().setUnitIncrement(10);

        //Right side - workout stats panel

        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

        JScrollPane rightScroll = new JScrollPane(statsPanel);
        rightScroll.setBorder(BorderFactory.createTitledBorder("Stats"));
        rightScroll.getVerticalScrollBar().setUnitIncrement(10);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, leftScroll, rightScroll);

        split.setDividerLocation(450);
        split.setResizeWeight(0.5);
        return split;
    }

    private JPanel buildFooter(){
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.add(new JLabel("FitTrack Version:1.0 - Data saved locally"));
        return footer;
    }

    private void refreshSessionList(){
        sessionListPanel.removeAll();

        List<WorkoutSession> sorted = manager.sortByDateDescending();

        if(sorted.isEmpty()){
            sessionListPanel.add(new JLabel(" No sessions yet. Hit '+ Log Workout' to start"));
        }
        else{
            List<WorkoutSession> all = manager.getAllSessions();
            for(WorkoutSession session : sorted){
                int originalIndex = all.indexOf(session);
                sessionListPanel.add(buildSessionRow(session, originalIndex));
                sessionListPanel.add(new JSeparator());
            }
        }

        sessionListPanel.revalidate();
        sessionListPanel.repaint();
    }


    private JPanel buildSessionRow(WorkoutSession session, int index){
        JPanel row = new JPanel(new BorderLayout(0, 8));
        row.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //Left info

        JPanel info = new JPanel(new BorderLayout(8, 0));
        JLabel dateLabel1 = new JLabel(session.getDate().format(DATE_FORMAT) +
                " | " + session.getSplitDay().getDisplayName());
        dateLabel1.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel detailLabel1 = new JLabel(session.getExercises().size() +
                " exercises | " + session.getDurationMins() + " min" +
                (session.hasCardio() ? " | " + session.getCardio().getCardioType() : ""));
        detailLabel1.setFont(new Font("SansSerif", Font.PLAIN, 11));
        detailLabel1.setForeground(Color.GRAY);

        info.add(dateLabel1);
        info.add(detailLabel1);

        row.add(info, BorderLayout.CENTER);

        row.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                showSessionDetailPopup(session, index);//will build this next probably
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e){
                row.setBackground(new Color(230, 230, 230));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e){
                row.setBackground(null);
            }
        });

        return row;
    }


    private void showSessionDetailPopup(WorkoutSession session, int index){
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        //Session header info
        content.add(new JLabel(session.getDate()
                .format(DateTimeFormatter.ofPattern("EEEE, MMMM dd yyyy"))));
        content.add(new JLabel(session.getSplit().getDisplayName() +
                " - " + session.getSplitDay().getDisplayName()));
        content.add(new JLabel("Duration " + session.getDurationMins() +
                " min"));
        content.add(new JSeparator());
        content.add(Box.createVerticalStrut(6));

        //Session exercises
        if(!session.getExercises().isEmpty()){
            content.add(boldLabel("Exercises:"));//coming later to make font bolder
            content.add(Box.createVerticalStrut(4));

            for(Exercise exercise : session.getExercises()){
                for (String line : exercise.toString().split("\n")) {
                    content.add(new JLabel(line));
                }
                content.add(Box.createVerticalStrut(4));
            }
        }

        //Cardio
        if(session.hasCardio()){
            content.add(new JSeparator());
            content.add(Box.createVerticalStrut(6));
            content.add(boldLabel("Cardio:"));

            CardioSession cardio = session.getCardio();
            content.add(new JLabel(" "+ cardio.getCardioType()+ " - "+
                    cardio.getCardioSummary()+ " - "+ cardio.getDuration()+ " min"));
            content.add(Box.createVerticalStrut(4));
        }

        //Notes
        if(!session.getNotes().isBlank()){
            content.add(new JSeparator());
            content.add(Box.createVerticalStrut(6));
            content.add(boldLabel("Notes:"));
            content.add(new JLabel(" "+ session.getNotes()));
        }

        //Scroll wrap
        JScrollPane scroll = new JScrollPane(content);
        scroll.setPreferredSize(new Dimension(420, 350));

        //Show popup
        String[] options = {"Close", "Delete Session"};
        int choice = JOptionPane.showOptionDialog(
                this, scroll, "Session Detail",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]
        );

        if(choice == 1){
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete this session? Action cannot be undone",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if(confirm == JOptionPane.YES_OPTION){
                try{
                    manager.removeSession(index);
                    refresh();//will refresh both session log and stats panel
                }catch(InvalidWorkoutException | IOException e){
                    JOptionPane.showMessageDialog(
                            this,
                            "Could not delete: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE

                    );
                }
            }
        }
    }

    private void refreshStatsPanel(){
        statsPanel.removeAll();

        if(manager.getSessionCount() == 0){
            statsPanel.add(new JLabel(" Log sessions to see stats"));
            statsPanel.revalidate();
            statsPanel.repaint();
            return;
        }

        statsPanel.add(boldLabel("Overview:"));
        statsPanel.add(new JLabel("Sessions: "+ manager.getSessionCount()));
        statsPanel.add(new JLabel("Avg Duration: "+ String.format("%.0f min",manager.getAverageDuration())));
        statsPanel.add(new JLabel("Total Cardio: " + manager.getTotalCardioMins()+ " min"));

        statsPanel.add(new JSeparator());
        statsPanel.add(Box.createVerticalStrut(6));


        //display split day breakdown
        Map<SplitDay, Long> splitCounts = manager.getSessionCountPerSplitDay();
        if(!splitCounts.isEmpty()){
            statsPanel.add(boldLabel("Sessions Per Split Day"));
            splitCounts.entrySet()
                      .stream()
                      .sorted(Map.Entry.<SplitDay, Long> comparingByValue().reversed())
                      .forEach(e -> statsPanel.add(new JLabel(" "+
                              e.getKey().getDisplayName() + ": "+ e.getValue())));

            statsPanel.add(new JSeparator());
            statsPanel.add(Box.createVerticalStrut(6));
        }

        //Cardio breakdown
        Map<String, Integer> cardioMap = manager.getTotalCardioMinutesByType();
        if(!cardioMap.isEmpty()){
            statsPanel.add(boldLabel("Cardio type by (min)"));
            cardioMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer> comparingByValue().reversed())
                    .forEach(e -> statsPanel.add(new JLabel(" "+
                            e.getKey()+ ": "+ e.getValue()+ " min")));
            statsPanel.add(new JSeparator());
            statsPanel.add(Box.createVerticalStrut(6));
        }

        //Exercise personal record lookup

    }

}
