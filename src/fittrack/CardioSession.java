package fittrack;

import java.io.Serializable;

public abstract class CardioSession implements Serializable{

    private static final long serialVersionUID = 1L;

    private int duration;

    public CardioSession(int duration){
        if(duration <= 0){
            //custom exception
        }
        this.duration = duration;
    }

    public abstract String getCardioType();

    public abstract String getCardioSummary();

    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        if(duration <= 0){
            //custom exception
        }

        this.duration = duration;
    }

    

}
