package fittrack;

public class TreadmillSession extends CardioSession{

    private static final long serialVersionUID = 1L;

    private double speed;

    private double incline;

    public TreadmillSession(int duration, double speed, double incline){
        if(speed <= 0){
            //custom exception
        }
        if(incline < 0){
            //custom exception
        }
        super(duration);
        this.speed = speed;
        this.incline = incline;
    }
    
}
