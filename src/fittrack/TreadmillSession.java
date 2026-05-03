package fittrack;

public class TreadmillSession extends CardioSession{

    private static final long serialVersionUID = 1L;

    private double speed;

    private double incline;

    public TreadmillSession(int duration, double speed, double incline) throws NegativeValueException{
        if(speed <= 0){
            //custom exception
            throw new NegativeValueException("Speed", speed);
        }
        if(incline < 0){
            //custom exception
            throw new NegativeValueException("Incline", incline);
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

    public void setSpeed(double speed) throws NegativeValueException{
        if(speed <= 0){
            //custom exception
            throw new NegativeValueException("Speed", speed);
        }

        this.speed = speed;
    }

    public double getIncline(){
        return incline;
    }

    public void setIncline(double incline) throws NegativeValueException{
        if(incline < 0){
            //custom exception
            throw new NegativeValueException("Incline", incline);
        }

        this.incline = incline;
    }

}
