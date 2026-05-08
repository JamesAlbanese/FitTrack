package fittrack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a training split program.
 * Each constant defines a named split and the ordered list of
 * SplitDay values that make up its training days.
 *
 * @author  James Albanese
 * @version 1.0
 * @since   5-6-2026
 */
public enum TrainingSplit {

    PPL("PPL (Push / Pull / Legs)", SplitDay.PUSH, SplitDay.PULL, SplitDay.LEGS),

    UL("UL (Upper / Lower)", SplitDay.UPPER, SplitDay.LOWER),

    FBEOD("FBEOD (Full Body Every Other Day)", SplitDay.FULL_BODY),

    ARNOLD("Arnold Split (Chest & Back / Shoulders & Arms / Legs", SplitDay.CHEST_BACK, SplitDay.SHOULDERS_ARMS, SplitDay.LEGS),

    ANTERIOR_POSTERIOR("Anterior / Posterior", SplitDay.ANTERIOR, SplitDay.POSTERIOR),

    PPLxUL("PPLxUL (Push / Pull / Legs / Upper / Lower)", SplitDay.PUSH, SplitDay.PULL, SplitDay.LEGS, SplitDay.UPPER, SplitDay.LOWER);

    private final String displayName;

    private final List<SplitDay> days;

    /**
     * Constructs a TrainingSplit with a display name and its associated days.
     *
     * @param displayName the label shown in the GUI
     * @param days        the ordered split days belonging to this split
     */
    TrainingSplit(String displayName, SplitDay... days){
        this.displayName = displayName;
        this.days = Collections.unmodifiableList(Arrays.asList(days));
    }

    /**
     * Returns the display name of this training split.
     *
     * @return display name string
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the ordered list of split days belonging to this split.
     *
     * @return unmodifiable list of split days
     */
    public List<SplitDay> getDays() {
        return days;
    }

    /**
     * Returns whether a given SplitDay belongs to this split.
     *
     * @param day the day to check
     * @return true if the day is part of this split, false otherwise
     */
    public boolean containsDay(SplitDay day){
        return days.contains(day);
    }

    /**
     * Returns the display name when this enum is converted to a String.
     *
     * @return display name string
     */
    @Override
    public String toString(){
        return displayName;
    }
}
