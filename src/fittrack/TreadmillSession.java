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

    @Override
    public String getCardioType(){
        return "Treadmill";
    }

    @Override
    public String getCardioSummary(){
        return speed + " mph @ " + incline + "% incline";
    }

    public double getSpeed(){
        return speed;
    }

    public void setSpeed(double speed){
        if(speed <= 0){
            //custom exception
        }

        this.speed = speed;
    }

    public double getIncline(){
        return incline;
    }

    public void setIncline(double incline){
        if(incline < 0){
            //custom exception
        }

        this.incline = incline;
    }

}
