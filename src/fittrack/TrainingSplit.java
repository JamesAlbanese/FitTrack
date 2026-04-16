package fittrack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TrainingSplit {

    PPL("PPL (Push / Pull / Legs)", SplitDay.PUSH, SplitDay.PULL, SplitDay.LEGS),

    UL("UL (Upper / Lower)", SplitDay.UPPER, SplitDay.LOWER),

    FBEOD("FBEOD (Full Body Every Other Day)", SplitDay.FULL_BODY),

    ARNOLD("Arnold Split (Chest & Back / Shoulders & Arms / Legs", SplitDay.CHEST_BACK, SplitDay.SHOULDERS_ARMS, SplitDay.LEGS),

    ANTERIOR_POSTERIOR("Anterior / Posterior", SplitDay.ANTERIOR, SplitDay.POSTERIOR),

    PPLxUL("PPLxUL (Push / Pull / Legs / Upper / Lower)", SplitDay.PUSH, SplitDay.PULL, SplitDay.LEGS, SplitDay.UPPER, SplitDay.LOWER);

    private final String displayName;

    private final List<SplitDay> days;

    TrainingSplit(String displayName, SplitDay... days){
        this.displayName = displayname;
        this.days = Collections.unmodifiableList(Arrays.asList(days));
    }

    
}
