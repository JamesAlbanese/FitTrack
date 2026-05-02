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
    
}
