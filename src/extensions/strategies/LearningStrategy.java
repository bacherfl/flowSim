package extensions.strategies;

import model.Data;
import model.Face;
import model.Interest;
import model.fw.FibEntry;
import model.pit.PitEntry;

import java.util.List;

/**
 * Created by florian on 25.08.2015.
 */
public class LearningStrategy extends BroadcastStrategy {

    @Override
    public void onInterest(Interest interest, Face inFace, PitEntry pitEntry) {
        List<FibEntry> matchingFibEntries = fib.getMatchingFibEntries(interest.getName());
        if (matchingFibEntries != null) {
            FibEntry fibEntry = matchingFibEntries.get(0);
            node.sendInterest(interest, fibEntry.getFace());
        } else {
            super.onInterest(interest, inFace, pitEntry);
        }
    }

    @Override
    public void OnData(Data data, Face inFace, PitEntry pitEntry) {
        super.OnData(data, inFace, pitEntry);
        fib.addFibEntry(data.getPrefix(), inFace, 0.0);
    }
}
