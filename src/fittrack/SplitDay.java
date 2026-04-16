package fittrack;

import java.io.Serializable;

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


    SplitDay(String displayName){
        this.displayName = displayName;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
