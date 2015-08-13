package extensions.strategies;

import model.*;
import model.fw.ForwardingStrategy;
import model.pit.PitEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 13.08.2015.
 */
public class BroadcastStrategy extends ForwardingStrategy {

    public BroadcastStrategy() {
        super();
    }

    public BroadcastStrategy(Node node) {
        super(node);
    }

    @Override
    public void onInterest(Interest interest, Face inFace, PitEntry pitEntry) {
        List<Face> outFaces = new ArrayList<>();
        fib.getFaces().forEach(face -> {
            if(!pitEntry.getInRecords().keySet().contains(face)) {
                outFaces.add(face);
            }
        });
        outFaces.forEach(outFace -> node.sendInterest(interest, outFace));
    }

    @Override
    public void OnData(Data data, Face inFace) {

    }

    @Override
    public void onDroppedPacket(Packet packet, Face face) {

    }

    @Override
    public void onTimedOutInterest(Interest interest, PitEntry pitEntry) {

    }
}
