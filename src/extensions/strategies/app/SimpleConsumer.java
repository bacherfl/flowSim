package extensions.strategies.app;

import model.Data;
import model.Interest;
import model.app.App;

/**
 * Created by florian on 13.08.2015.
 */
public class SimpleConsumer extends App {

    private boolean consume = true;

    @Override
    public void onStartApplication() {
        if (consume) {
            Interest interest = new Interest();
            interest.setNonce(0);
            interest.setName("/name");
            interest.setTimeout(5000);
            interest.setSize(50);
            System.out.println("Node " + node.getId() + " sending interest " + interest.getName());
            appFace.sendInterest(interest);
        }
    }

    @Override
    public void onInterest(Interest interest) {
        System.out.println("Node " + node.getId() + " received interest " + interest.getName());
        Data data = new Data();
        data.setSize(4096);
        data.setName(interest.getName());
        appFace.sendData(data);
    }

    @Override
    public void onData(Data data) {
        System.out.println("Node " + node.getId() + " received data " + data.getName());
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
