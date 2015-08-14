package sim;


import java.util.Observable;

/**
 * Created by florian on 12.08.2015.
 */
public class Simulator extends Observable {
    private long currentTime = 0L;
    private boolean running = false;
    private long simulationLength;  //1 tick := 0.1ms

    private static Simulator instance;

    private Simulator() {

    }

    public void start() {
        running = true;
        proceed();
    }

    public void stop() {
        running = false;
    }

    public void proceed() {
        do {
            currentTime++;
            setChanged();
            notifyObservers();
            if (simulationLength <= currentTime)
                stop();
        } while (running);
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public long getSimulationLengthInSeconds() {
        return simulationLength / 10000;
    }

    public void setSimulationLengthInSeconds(long simulationLengthInSeconds) {
        this.simulationLength = simulationLengthInSeconds * 10000;
    }

    public static Simulator getInstance() {
        if (instance == null) {
            instance = new Simulator();
            instance.addObserver(Scheduler.getInstance());
        }
        return instance;
    }

    public void setSimulationLengthInTenthMilliSeconds(Long simulationLength) {
        this.simulationLength = simulationLength;
    }
}
