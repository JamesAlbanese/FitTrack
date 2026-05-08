package fittrack;

/**
 * Represents a specific training day within a workout split.
 * Each constant corresponds to a named day used by one or more supported splits.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-6-2026
 */
public enum SplitDay{

    PUSH("Push"),
    PULL("Pull"),
    LEGS("Legs"),

    UPPER("Upper"),
    LOWER("Lower"),

    FULL_BODY("Full Body"),

    CHEST_BACK("Chest & Back"),
    SHOULDERS_ARMS("Shoulders & Arms"),

    ANTERIOR("Anterior"),
    POSTERIOR("Posterior");

    private String displayName;

    /**
     * Constructs a SplitDay with a display name.
     *
     * @param displayName the label shown in the GUI
     */
    SplitDay(String displayName){
        this.displayName = displayName;
    }

    /**
     * Returns the display name of this split day.
     *
     * @return display name string
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name of this split day.
     *
     * @param displayName the new display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name when this enum is converted to a String.
     *
     * @return display name string
     */
    public String toString(){
        return displayName;
    }
}
