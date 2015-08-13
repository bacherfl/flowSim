package model;

/**
 * Created by florian on 12.08.2015.
 */
public class Interest extends Packet {

    private long timeout;
    private int nonce;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }
}
