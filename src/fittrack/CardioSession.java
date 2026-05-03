package fittrack;

import java.io.Serializable;

public abstract class CardioSession implements Serializable{

    private static final long serialVersionUID = 1L;

    private int duration;

    public CardioSession(int duration) throws NegativeValueException{
        if(duration <= 0){
            //custom exception
            throw new NegativeValueException("Duration", duration);

        }
        this.duration = duration;
    }

    public abstract String getCardioType();

    public abstract String getCardioSummary();

    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration) throws NegativeValueException{
        if(duration <= 0){
            //custom exception
            throw new NegativeValueException("Duration", duration);
        }

        this.duration = duration;
    }

    @Override
    public String toString(){
        return getCardioType() + " | " + duration + " min | " + getCardioSummary();
    }


}
