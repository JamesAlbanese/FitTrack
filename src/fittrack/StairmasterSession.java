package fittrack;

public class StairmasterSession extends CardioSession{

    private static final long serialVersionUID = 1L;

    private int speed;

    public StairmasterSession(int duration, int speed){
        if(speed <= 0){
            //custom exception
        }
        super(duration);
        this.speed = speed;
    }


    @Override
    public String getCardioType(){
        return "Stairmaster";
    }

    @Override
    public String getCardioSummary(){
        return speed + " mph";
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        if(speed <= 0){
            //custom exception
        }
        this.speed = speed;
    }
}
