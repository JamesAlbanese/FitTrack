package fittrack;

import javax.swing.*;
import java.time.format.DateTimeFormatter;

public class MainPanel extends JPanel {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM dd, yyyy");

    private final FitTrackApp app;
    private final FitTrackManager manager;

    private JPanel sessionListPanel;
    private JPanel statsPanel;
    private JComboBox<String> prCombo;

    public MainPanel(){}
    
}
