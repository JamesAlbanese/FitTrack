package fittrack;

public class BikeSession extends CardioSession{

    private static final long serialVersionUID = 1L;

    private double resistance;

    public BikeSession(int duration, double resistance){
        if(resistance <= 0){
            //custom exception
        }
        super(duration);
        this.resistance = resistance;
    }

    @Override
    public String getCardioType(){
        return "Bike";
    }


    @Override
    public String getCardioSummary(){
        return resistance + " resistance";
    }

    public double getResistance(){
        return resistance;
    }

    public void setResistance(double resistance){
        if(resistance < 0){
            //custom exception
        }
        this.resistance = resistance;
    }
}
