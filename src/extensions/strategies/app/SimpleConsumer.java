package extensions.strategies.app;

import model.Data;
import model.Interest;
import model.app.App;
import sim.Simulator;

/**
 * Created by florian on 13.08.2015.
 */
public class SimpleConsumer extends App {

    private boolean consume = false;
    private String prefix;

    public SimpleConsumer() {
        prefix = "/name";
    }

    public SimpleConsumer(boolean consume, String prefix) {
        this.consume = consume;
        this.prefix = prefix;
    }

    @Override
    public void onStartApplication() {
        if (consume) {
            Interest interest = new Interest();
            interest.setNonce((int) (Math.random() * 1000));
            interest.setName(prefix);
            interest.setTimeout(5000);
            interest.setSize(50);
            System.out.println(Simulator.getInstance().getCurrentTime() + " [App] Node " + node.getId() + " sending interest " + interest.getName());
            appFace.sendInterest(interest);
        }
    }

    @Override
    public void onInterest(Interest interest) {
        System.out.println(Simulator.getInstance().getCurrentTime() + " [App] Node " + node.getId() + " received interest " + interest.getName());
        // Data data = new Data();
        // data.setSize(4096);
        // data.setName(interest.getName());
        // appFace.sendData(data);
    }

    @Override
    public void onData(Data data) {
        System.out.println(Simulator.getInstance().getCurrentTime() + " [App] Node " + node.getId() + " received data " + data.getName());
    }

    @Override
    public void onStopApplication() {

    }

    public boolean isConsume() {
        return consume;
    }

    public void setConsume(boolean consume) {
        this.consume = consume;
    }
}
