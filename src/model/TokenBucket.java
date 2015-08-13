package model;

import sim.Scheduler;

/**
 * Created by florian on 12.08.2015.
 */
public class TokenBucket {

    private static final int TOKEN_GEN_INTERVAL = 11;

    private double tokenGenRate;
    private long tokens = 0L;

    public TokenBucket(int bandwidth) {
        tokenGenRate = (bandwidth + 0.0) * (TOKEN_GEN_INTERVAL / 1000.0);
        produceToken();
    }

    private void produceToken() {
        tokens += tokenGenRate;
        Scheduler.getInstance().scheduleEventIn(TOKEN_GEN_INTERVAL, this::produceToken);
    }

    public boolean consumeToken(Packet packet) {
        if (tokens - packet.getSize() > 0) {
            tokens -= packet.getSize();
            return true;
        } else {
            return false;
        }
    }
}
