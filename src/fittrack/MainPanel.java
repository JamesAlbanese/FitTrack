package fittrack;

import com.sun.tools.javac.util.List;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainPanel extends JPanel {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM dd, yyyy");

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


    }
}
