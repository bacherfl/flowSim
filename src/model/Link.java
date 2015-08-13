package model;

import sim.Scheduler;

/**
 * Created by florian on 12.08.2015.
 */
public class Link {

    public static final int DEFAULT_BANDWIDTH = 10000000;
    public static final int DEFAULT_DELAY = 10;
    public static final double DEFAULT_RELIABILITY = 1.0;

    private int bandwidth;
    private int delay;
    private double reliability;

    private TokenBucket tokenBucket;

    private Face f1;
    private Face f2;


    public Link(Face f1, Face f2, int bandwidth, int delay, double reliability) {
        this.bandwidth = bandwidth;
        this.delay = delay;
        this.reliability = reliability;
        this.f1 = f1;
        this.f2 = f2;

        this.f1.setLink(this);
        this.f2.setLink(this);
        initTokenBucket(bandwidth);
    }

    private void initTokenBucket(int bandwidth) {
        if (bandwidth >= 0)
            tokenBucket = new TokenBucket(bandwidth);
    }

    public Link(Face f1, Face f2) {
        this.bandwidth = DEFAULT_BANDWIDTH;
        this.delay = DEFAULT_DELAY;
        this.reliability = DEFAULT_RELIABILITY;
        this.f1 = f1;
        this.f2 = f2;
        this.f1.setLink(this);
        this.f2.setLink(this);
        initTokenBucket(bandwidth);
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public double getReliability() {
        return reliability;
    }

    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    public void transmitPacket(Packet packet, Face f) {
        if (Math.random() >= reliability)
            return;
        if (tokenBucket != null) {
            if (!tokenBucket.consumeToken(packet)) {
                f.droppedPacket(packet);
                return;
            }
        }
        Face target;
        if (f == f1) {
            target = f2;
        } else {
            target = f1;
        }
        final Face finalTarget = target;
        if (packet instanceof Data) {
            if (delay > 0)
                Scheduler.getInstance().scheduleEventIn(delay, () -> finalTarget.receiveData((Data) packet));
            else
                finalTarget.receiveData((Data) packet);
        }
        else if (packet instanceof Interest) {
            if (delay > 0)
                Scheduler.getInstance().scheduleEventIn(delay, () -> finalTarget.receiveInterest((Interest) packet));
            else
                finalTarget.receiveInterest((Interest) packet);
        }
    }
}
