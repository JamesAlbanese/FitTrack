package fittrack;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

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
        add(buildHeader(), BorderLayout.CENTER);
        add(buildHeader(), BorderLayout.SOUTH);
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

    private JSplitPane buildCenter(){
        //Left side - list of sessions

        sessionListPanel = new JPanel();
        sessionListPanel.setLayout(new BoxLayout(sessionListPanel, BoxLayout.Y_AXIS));

        JScrollPane leftScroll = new JScrollPane(sessionListPanel);


    }
}
